
import java.util.Scanner;

public class Classes {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("========== PERSONAGENS ==========");
        System.out.println("1  - Guerrreiro");
        System.out.println("2  - Mago");
        System.out.println("3  - Paladino");
        System.out.println("4  - DRUIDA");
        System.out.println("5  - Clerigo");
    

        System.out.print("\nEscolha sua classe: ");

        int opcao = scanner.nextInt();

        switch (opcao) {

            case 1 -> System.out.println("Você escolheu GUERREIRO!");
            case 2 -> System.out.println("Você escolheu MAGO!");
            case 3 -> System.out.println("Você escolheu PALADINO!");
            case 4 -> System.out.println("Você escolheu DRUIDA!");
            case 5 -> System.out.println("Você escolheu CLERIGO!");
            

            default -> System.out.println("Opção inválida!");

        }

        scanner.close();
    }
}
