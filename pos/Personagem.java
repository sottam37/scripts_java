public abstract class Personagem {

    private String nome;

    protected int vida = 100;
    protected int dano = 10;

    public Personagem(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract void atacar();
}
