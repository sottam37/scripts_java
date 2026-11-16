package rpg;

public final class Habilidades {
    private final int id;
    private final String nome;
    private final String efeito;

    public Habilidades(int id, String nome, String efeito) {
        if (id < 1 || nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O catálogo exige ID positivo e nome.");
        }
        this.id = id;
        this.nome = nome;
        this.efeito = efeito;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEfeito() {
        return efeito;
    }
}
