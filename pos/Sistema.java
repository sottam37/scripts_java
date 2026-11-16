
import java.util.Scanner;
import java.io.IOException;

public final class Sistema {

    private static final int PORTA_SSH = 22443;

    private String usuario;
    private String servidor;

    // OPÇÃO 1 - Terminal SSH remoto
    public void iniciar(Scanner scanner) {

        System.out.print("Digite o usuário SSH: ");
        usuario = scanner.nextLine();

        System.out.print("Digite o servidor SSH: ");
        servidor = scanner.nextLine();

        System.out.println(
            "\nConectando ao servidor SSH " + servidor +
            " com o usuário " + usuario + "..."
        );

        try {

            ProcessBuilder comando = new ProcessBuilder(
                "ssh",
                "-tt",
                "-p", String.valueOf(PORTA_SSH),
                usuario + "@" + servidor
            );

            comando.inheritIO();

            Process processo = comando.start();

            int codigo = processo.waitFor();

            System.out.println(
                "\nSessão SSH encerrada. Código: " + codigo
            );

        } catch (IOException e) {

            System.out.println(
                "Erro ao executar SSH: " + e.getMessage()
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println("Conexão SSH interrompida.");
        }
    }

    // OPÇÃO 2 - Executar BTOP remotamente
    public void executarBtop() {

        executarComandoRemoto("btop");
    }

    // OPÇÃO 3 - Executar HTOP remotamente
    public void executarHtop() {

        executarComandoRemoto("htop");
    }

    // Método responsável pela execução remota
    private void executarComandoRemoto(String ferramenta) {

        if (usuario == null || servidor == null) {

            System.out.println(
                "Configure a conexão SSH primeiro utilizando a opção 1."
            );

            return;
        }

        System.out.println(
            "\nExecutando " + ferramenta +
            " no servidor " + servidor + "..."
        );

        try {

            ProcessBuilder comando = new ProcessBuilder(
                "ssh",
                "-tt",
                "-p", String.valueOf(PORTA_SSH),
                usuario + "@" + servidor,
                ferramenta
            );

            comando.inheritIO();

            Process processo = comando.start();

            int codigo = processo.waitFor();

            System.out.println(
                "\n" + ferramenta +
                " encerrado. Código: " + codigo
            );

        } catch (IOException e) {

            System.out.println(
                "Erro ao executar " + ferramenta + ": " + e.getMessage()
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                "Execução de " + ferramenta + " interrompida."
            );
        }
    }

    // OPÇÃO 4 - Finalizar aplicação
    public void finalizar() {

        System.out.println("\nSistema finalizado!");
    }

    // MENU PRINCIPAL
    public static void main(String[] args) {

        Sistema sistema = new Sistema();


        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {

                System.out.println("\n========== MENU SSH ==========");
                System.out.println("1 - Terminal SSH remoto");
                System.out.println("2 - BTOP");
                System.out.println("3 - HTOP");
                System.out.println("4 - Sair");

                System.out.print("\nEscolha uma opção: ");

                String entrada = scanner.nextLine();

                int opcao;

                try {

                    opcao = Integer.parseInt(entrada);

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Entrada inválida! Digite um número."
                    );

                    continue;
                }

                switch (opcao) {

                    case 1:
                        sistema.iniciar(scanner);
                        break;

                    case 2:
                        sistema.executarBtop();
                        break;

                    case 3:
                        sistema.executarHtop();
                        break;

                    case 4:
                        sistema.finalizar();
                        return;

                    default:
                        System.out.println(
                            "Opção inválida! Escolha de 1 a 4."
                        );
                }
            }
        }
    }
}
