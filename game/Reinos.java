public class Reinos {
    public static void main(String[] args) {
        System.out.println("-----------------------REINOS----------------------------");
        System.out.println("1 - REINO DO FOGO");
        System.out.println("2 - REINO DO GELO");
        System.out.println("3 - REINO DO VENTO");
        System.out.println("4 - REINO DA TERRA");
        System.out.println("5 - REINO DA LUZ");
        System.out.println("6 - REINO DAS TREVAS");
        System.out.println("7 - REINO DO MAR");
        System.out.println("8 - REINO DO DESERTO");
        
    }
    public static void escolherReino(int reino) {
        switch (reino) {
            case 1:
                System.out.println("Você escolheu o REINO DO FOGO!");
                break;
            case 2:
                System.out.println("Você escolheu o REINO DO GELO!");
                break;
            case 3:
                System.out.println("Você escolheu o REINO DO VENTO!");
                break;
            case 4:
                System.out.println("Você escolheu o REINO DA TERRA!");
                break;
            case 5:
                System.out.println("Você escolheu o REINO DA LUZ!");
                break;
            case 6:
                System.out.println("Você escolheu o REINO DAS TREVAS!");
                break;
            case 7:
                System.out.println("Você escolheu o REINO DO MAR!");
                break;
            case 8:
                System.out.println("Você escolheu o REINO DO DESERTO!");
                break;
            default:
                System.out.println("Opção inválida! Por favor, escolha um reino válido.");
        }
    }
}
