package rpg;

public final class Reinos {
    private final int id;
    private final String nome;

    public Reinos(int id, String nome) {
        if (id < 1 || nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O catálogo exige ID positivo e nome.");
        }
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
