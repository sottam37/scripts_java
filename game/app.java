import java.util.Scanner;

public class app {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("   BEM-VINDO AO CRIADOR DE RPG!");
        System.out.println("===================================\n");

        // 1. Raça
        String[] racas = {"Humano", "Elfo", "Anão", "Orc"};
        String raca = escolher("Escolha a raça do seu personagem:", racas);

        // 2. Classe
        String[] classes = {"Guerreiro", "Mago", "Arqueiro", "Ladino"};
        String classe = escolher("Escolha a classe do seu personagem:", classes);

        // 3. Habilidade (depende da classe)
        String[] habilidades = habilidadesDaClasse(classe);
        String habilidade = escolher("Escolha uma habilidade de " + classe + ":", habilidades);

        // 4. Arma (depende da classe)
        String[] armas = armasDaClasse(classe);
        String arma = escolher("Escolha uma arma de " + classe + ":", armas);

        // 5. Nome
        String nome = "";
        while (nome.isBlank()) {
            System.out.print("\nDigite o nome do seu personagem: ");
            nome = scanner.nextLine().trim();
            if (nome.isBlank()) {
                System.out.println("O nome não pode ficar vazio!");
            }
        }

        // Ficha final
        System.out.println("\n===================================");
        System.out.println("        FICHA DO PERSONAGEM");
        System.out.println("===================================");
        System.out.println("Nome       : " + nome);
        System.out.println("Raça       : " + raca);
        System.out.println("Classe     : " + classe);
        System.out.println("Habilidade : " + habilidade);
        System.out.println("Arma       : " + arma);
        System.out.println("===================================");
        System.out.println(nome + " está pronto para a aventura!");
    }

    private static String[] habilidadesDaClasse(String classe) {
        switch (classe) {
            case "Guerreiro":
                return new String[]{"Golpe Poderoso", "Escudo Protetor", "Grito de Guerra"};
            case "Mago":
                return new String[]{"Bola de Fogo", "Raio de Gelo", "Teletransporte"};
            case "Arqueiro":
                return new String[]{"Tiro Certeiro", "Chuva de Flechas", "Olhar de Águia"};
            case "Ladino":
                return new String[]{"Ataque Furtivo", "Invisibilidade", "Roubo Rápido"};
            default:
                return new String[]{"Ataque Básico"};
        }
    }

    private static String[] armasDaClasse(String classe) {
        switch (classe) {
            case "Guerreiro":
                return new String[]{"Espada Longa", "Machado de Batalha", "Martelo de Guerra"};
            case "Mago":
                return new String[]{"Cajado Arcano", "Varinha Mágica", "Grimório"};
            case "Arqueiro":
                return new String[]{"Arco Longo", "Besta", "Arco Curto"};
            case "Ladino":
                return new String[]{"Adagas Gêmeas", "Punhal Envenenado", "Espada Curta"};
            default:
                return new String[]{"Punhos"};
        }
    }

    // Mostra um menu numerado e retorna a opção escolhida (repete até ser válida)
    private static String escolher(String titulo, String[] opcoes) {
        System.out.println("\n" + titulo);
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println("  " + (i + 1) + " - " + opcoes[i]);
        }

        while (true) {
            System.out.print("Opção: ");
            String entrada = scanner.nextLine().trim();
            try {
                int escolha = Integer.parseInt(entrada);
                if (escolha >= 1 && escolha <= opcoes.length) {
                    return opcoes[escolha - 1];
                }
            } catch (NumberFormatException e) {
                // cai na mensagem abaixo
            }
            System.out.println("Opção inválida! Digite um número de 1 a " + opcoes.length + ".");
        }
    }
}