package rpg;

public final class Armas {
    private final int id;
    private final String nome;
    private final Integer dano;

    public Armas(int id, String nome, Integer dano) {
        if (id < 1 || nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O catálogo exige ID positivo e nome.");
        }
        if (dano != null && dano < 0) {
            throw new IllegalArgumentException("O dano deve ser não negativo.");
        }
        this.id = id;
        this.nome = nome;
        this.dano = dano;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getDano() {
        return dano;
    }
}
