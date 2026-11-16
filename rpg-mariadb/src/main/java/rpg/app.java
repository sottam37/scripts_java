package rpg;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

public final class app {
    private final Scanner entrada;
    private final PersonagemDAO dao;

    private app(Scanner entrada, PersonagemDAO dao) {
        this.entrada = entrada;
        this.dao = dao;
    }

    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8)) {
            new app(entrada, new PersonagemDAO(Cnx.doAmbiente())).criarPersonagem();
        } catch (NoSuchElementException e) {
            System.out.println("\nCriação cancelada. A entrada foi encerrada.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.err.printf("Falha ao acessar o MariaDB (SQLState %s, código %d).%n",
                    e.getSQLState(), e.getErrorCode());
            System.exit(1);
        }
    }

    private void criarPersonagem() throws SQLException {
        System.out.println("Bem-vindo ao criador de RPG!");
        Raca raca = escolher("Escolha a raça", dao.listarRacas(), Raca::getNome, false);
        Classes classe = escolher("Escolha a classe", dao.listarClasses(), Classes::getNome, false);
        Habilidades habilidade = escolher("Escolha uma habilidade de " + classe.getNome(),
                dao.listarHabilidades(classe), app::descrever, false);
        Armas arma = escolher("Escolha uma arma de " + classe.getNome(),
                dao.listarArmas(classe), app::descrever, false);
        Reinos reino = escolher("Escolha o reino", dao.listarReinos(), Reinos::getNome, true);
        Personagem personagem = new Personagem(lerNome(), raca, classe, habilidade, arma, reino);
        long id = dao.salvar(personagem);
        exibirFicha(id, personagem);
    }

    private <T> T escolher(String titulo, List<T> opcoes, Function<T, String> rotulo,
                           boolean opcional) {
        if (opcoes.isEmpty() && !opcional) {
            throw new IllegalStateException("Catálogo sem opções. Confira o esquema e os vínculos da classe.");
        }

        System.out.println("\n" + titulo);
        int minimo = opcional ? 0 : 1;
        if (opcional) {
            System.out.println("  0 - Sem reino");
        }
        for (int i = 0; i < opcoes.size(); i++) {
            System.out.printf("  %d - %s%n", i + 1, rotulo.apply(opcoes.get(i)));
        }

        while (true) {
            System.out.print("Opção: ");
            String linha = entrada.nextLine().strip();
            try {
                int escolha = Integer.parseInt(linha);
                if (escolha >= minimo && escolha <= opcoes.size()) {
                    return escolha == 0 ? null : opcoes.get(escolha - 1);
                }
            } catch (NumberFormatException e) {
                // Entradas não numéricas recebem a mesma orientação.
            }
            System.out.printf("Digite um número de %d a %d.%n", minimo, opcoes.size());
        }
    }

    private String lerNome() {
        while (true) {
            System.out.print("\nDigite o nome do personagem: ");
            try {
                return Personagem.validarNome(entrada.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String descrever(Habilidades habilidade) {
        return habilidade.getEfeito() == null ? habilidade.getNome()
                : habilidade.getNome() + " — " + habilidade.getEfeito();
    }

    private static String descrever(Armas arma) {
        return arma.getDano() == null ? arma.getNome() : arma.getNome() + " — Dano " + arma.getDano();
    }

    private static void exibirFicha(long id, Personagem personagem) {
        System.out.printf("%nFicha do personagem #%d%n", id);
        System.out.println("Nome: " + personagem.getNome());
        System.out.println("Raça: " + personagem.getRaca().getNome());
        System.out.println("Classe: " + personagem.getClasse().getNome());
        System.out.println("Habilidade: " + descrever(personagem.getHabilidade()));
        System.out.println("Arma: " + descrever(personagem.getArma()));
        Reinos reino = personagem.getReino();
        System.out.println("Reino: " + (reino == null ? "Sem reino" : reino.getNome()));
        System.out.printf("Nível: %d | Experiência: %d | Vida: %d | Mana: %d | Força: %d%n",
                personagem.getNivel(), personagem.getExperiencia(), personagem.getVida(),
                personagem.getMana(), personagem.getForca());
    }
}
