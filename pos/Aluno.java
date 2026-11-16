public class Aluno {
    private String nome; 
    public void inserirNome(String nn) {
        nome = nn;
    }
    public String recuperarNome() {
        return nome;
    }
    public static void main(String args[]){
        Aluno a = new Aluno(); 
        a.inserirNome ("João");
        System.out.println("Nome: "+a.recuperarNome());
    }
} 