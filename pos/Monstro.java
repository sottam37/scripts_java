public abstract class Monstro extends Personagem {

    public Monstro(String nome) {
        super(nome);
    }

    @Override
    public void atacar() {

        vida -= 50;
        dano += 10;

        System.out.println(getNome() + " atacou!");
        System.out.println("Vida: " + vida);
        System.out.println("Dano: " + dano);
    }
}