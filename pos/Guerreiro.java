public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome);
    }

    @Override
    public void atacar() {

        vida -= 10;
        dano += 50;

        System.out.println(getNome() + " atacou!");
        System.out.println("Vida: " + vida);
        System.out.println("Dano: " + dano);
    }
}