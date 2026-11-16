
public class Personagem {

    // Identificação
    String nome;
    String raca;
    String classe;
    String habilidade;
    String arma;

    // Atributos do personagem
    int nivel = 1;
    int experiencia = 0;
    int vida = 100;
    int mana = 100;
    int forca = 10;

    // Construtor
    public Personagem(
        String nome,
        String raca,
        String classe,
        String habilidade,
        String arma
    ) {

        this.nome = nome;
        this.raca = raca;
        this.classe = classe;
        this.habilidade = habilidade;
        this.arma = arma;

    }

    // Exibir informações do personagem
    public void exibirPersonagem() {

        System.out.println("\n========== PERSONAGEM ==========");

        System.out.println("Nome: " + nome);
        System.out.println("Raça: " + raca);
        System.out.println("Classe: " + classe);
        System.out.println("Habilidade: " + habilidade);
        System.out.println("Arma: " + arma);

        System.out.println("\n========== ATRIBUTOS ==========");

        System.out.println("Nível: " + nivel);
        System.out.println("Experiência: " + experiencia);
        System.out.println("Vida: " + vida);
        System.out.println("Mana: " + mana);
        System.out.println("Força: " + forca);

        System.out.println("================================");

    }

    // Método principal
    public static void main(String[] args) {

        Personagem personagem = new Personagem(
            "Akai",
            "Troll",
            "Mage",
            "Bola de Fogo",
            "Cajado"
        );

        personagem.exibirPersonagem();

    }

    public String getNome() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNome'");
    }

    public int getRacaId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRacaId'");
    }

    public int getHabilidadeId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHabilidadeId'");
    }

    public int getIdClasse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdClasse'");
    }
}
