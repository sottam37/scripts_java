public class Main {

    public static void main(String[] args) {

        Guerreiro guerreiro = new Guerreiro("Akai");

        guerreiro.atacar();

        
        Monstro monstro = new Monstro("Ogro") {
            @Override
            public void atacar() {
                super.atacar();
            }
        };
        monstro.atacar();

        System.out.println("VENCEDOR: " + guerreiro.getNome());
    }
}