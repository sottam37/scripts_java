# Criador de personagens RPG

Aplicação de terminal em Java com persistência no MariaDB. Escolha raça, classe, habilidade, arma e, se quiser, um reino. O programa grava a ficha e exibe o ID retornado pelo banco.

## Pré-requisitos

- JDK 17 ou superior.
- Maven 3.9 ou superior.
- MariaDB 10.11 ou superior e acesso administrativo para a instalação inicial.
- Acesso ao Maven Central no primeiro build.

## 1. Preparar o banco

Os exemplos abaixo usam Bash e um MariaDB local. Abra o cliente como administrador. Em instalações Linux nas quais o administrador se autentica pelo socket, use `sudo mariadb`.

```bash
MYSQL_HISTFILE=/dev/null mariadb -u root -p
```

Crie um banco vazio e uma conta exclusiva para a aplicação. Substitua `SUBSTITUA_POR_UMA_SENHA_FORTE` pela sua senha. Se usou `sudo mariadb`, desative o histórico com `sudo env MYSQL_HISTFILE=/dev/null mariadb` ao abrir o cliente.

```sql
CREATE DATABASE rpg CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'rpg_app'@'127.0.0.1' IDENTIFIED BY 'SUBSTITUA_POR_UMA_SENHA_FORTE';
```

Execute o esquema como administrador na pasta que contém o `pom.xml`:

```bash
mariadb -u root -p rpg < sql/schema.sql
```

Para autenticação administrativa pelo socket, use `sudo mariadb rpg < sql/schema.sql`. O script carrega os catálogos e pode ser reaplicado ao mesmo esquema. Se você já tem um banco do RPG com outra estrutura, instale em um banco separado e planeje a migração dos registros conforme o esquema antigo.

Reabra o cliente como administrador e conceda as permissões da aplicação:

```sql
GRANT SELECT ON rpg.racas TO 'rpg_app'@'127.0.0.1';
GRANT SELECT ON rpg.classes TO 'rpg_app'@'127.0.0.1';
GRANT SELECT ON rpg.reinos TO 'rpg_app'@'127.0.0.1';
GRANT SELECT ON rpg.armas TO 'rpg_app'@'127.0.0.1';
GRANT SELECT ON rpg.habilidades TO 'rpg_app'@'127.0.0.1';
GRANT SELECT ON rpg.classe_armas TO 'rpg_app'@'127.0.0.1';
GRANT SELECT ON rpg.classe_habilidades TO 'rpg_app'@'127.0.0.1';
GRANT INSERT ON rpg.personagens TO 'rpg_app'@'127.0.0.1';
```

## 2. Compilar e executar

```bash
mvn clean package

export RPG_DB_URL='jdbc:mariadb://127.0.0.1:3306/rpg?connectTimeout=5000'
export RPG_DB_USER='rpg_app'
IFS= read -rsp 'Senha do MariaDB: ' RPG_DB_PASSWORD
printf '\n'
export RPG_DB_PASSWORD

java -jar target/rpg-mariadb-1.0.0.jar
unset RPG_DB_PASSWORD
```

A URL do exemplo é o padrão da aplicação. Informe usuário e senha nas variáveis próprias. Em um servidor remoto, ajuste a conta do MariaDB para o host de origem da aplicação e use `sslMode=verify-full` na URL com um certificado confiável. [Configuração TLS do MariaDB Connector/J](https://mariadb.com/docs/connectors/mariadb-connector-j/using-tls-ssl-with-mariadb-java-connector)

## 3. Conferir a gravação

No cliente MariaDB, entre como administrador ou use uma conta com permissão de leitura e consulte:

```sql
SELECT p.id, p.nome, r.nome AS raca, c.nome AS classe,
       h.nome AS habilidade, a.nome AS arma, re.nome AS reino,
       p.nivel, p.experiencia, p.vida, p.mana, p.forca
FROM rpg.personagens p
JOIN rpg.racas r ON r.id = p.raca_id
JOIN rpg.classes c ON c.id = p.classe_id
JOIN rpg.habilidades h ON h.id = p.habilidade_id
JOIN rpg.armas a ON a.id = p.arma_id
LEFT JOIN rpg.reinos re ON re.id = p.reino_id
ORDER BY p.id DESC
LIMIT 10;
```

## Organização e decisões

- `app.java` mantém o fluxo do criador. Trata opções inválidas, valida o nome e aceita `0` para criar sem reino.
- `Personagem.java` encapsula os campos e valida alterações. Os atributos iniciais são nível 1, experiência 0, vida 100, mana 100 e força 10.
- `Raca.java`, `Classes.java`, `Habilidades.java`, `Armas.java` e `Reinos.java` representam registros imutáveis do catálogo com construtores e getters.
- `Cnx.java` lê a configuração do ambiente. `PersonagemDAO.java` consulta os catálogos, usa parâmetros JDBC e grava a ficha completa com retorno do ID.
- `sql/schema.sql` preserva as opções e combinações do menu principal e incorpora as dos menus avulsos. Os vínculos das opções adicionais foram definidos neste projeto. Você pode ajustá-los nas tabelas `classe_armas` e `classe_habilidades`. As chaves estrangeiras compostas também validam esses vínculos em inserções feitas diretamente no banco.
- Efeitos e danos que apareciam nos menus originais são exibidos na seleção. Habilidades com o mesmo nome e durações distintas mantêm entradas próprias. O projeto continua focado na criação de fichas.
