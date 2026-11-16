package rpg;

public final class Personagem {
    private String nome;
    private Raca raca;
    private Classes classe;
    private Habilidades habilidade;
    private Armas arma;
    private Reinos reino;
    private int nivel = 1;
    private int experiencia = 0;
    private int vida = 100;
    private int mana = 100;
    private int forca = 10;

    public Personagem(String nome, Raca raca, Classes classe, Habilidades habilidade,
                      Armas arma, Reinos reino) {
        setNome(nome);
        setRaca(raca);
        setClasse(classe);
        setHabilidade(habilidade);
        setArma(arma);
        setReino(reino);
    }

    public static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Digite um nome para o personagem.");
        }
        String normalizado = nome.strip();
        if (normalizado.codePointCount(0, normalizado.length()) > 80
                || normalizado.codePoints().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Use até 80 caracteres e evite caracteres de controle.");
        }
        return normalizado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = exigirOpcao(raca, "raça");
    }

    public Classes getClasse() {
        return classe;
    }

    public void setClasse(Classes classe) {
        this.classe = exigirOpcao(classe, "classe");
    }

    public Habilidades getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidades habilidade) {
        this.habilidade = exigirOpcao(habilidade, "habilidade");
    }

    public Armas getArma() {
        return arma;
    }

    public void setArma(Armas arma) {
        this.arma = exigirOpcao(arma, "arma");
    }

    public Reinos getReino() {
        return reino;
    }

    public void setReino(Reinos reino) {
        this.reino = reino;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = exigirMinimo(nivel, 1, "nível");
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = exigirMinimo(experiencia, 0, "experiência");
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = exigirMinimo(vida, 0, "vida");
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = exigirMinimo(mana, 0, "mana");
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = exigirMinimo(forca, 0, "força");
    }

    private static <T> T exigirOpcao(T valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("Escolha uma opção para " + campo + ".");
        }
        return valor;
    }

    private static int exigirMinimo(int valor, int minimo, String campo) {
        if (valor < minimo) {
            throw new IllegalArgumentException("O valor de " + campo + " deve ser pelo menos " + minimo + ".");
        }
        return valor;
    }
}
