
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonagemDAO {

    public Map<Integer, String> listar(String tabela)
            throws SQLException {

        // Evita que nomes arbitrários de tabelas sejam utilizados.
        if (!tabela.matches("racas|classes|habilidades")) {
            throw new IllegalArgumentException("Tabela inválida");
        }

        Map<Integer, String> opcoes = new LinkedHashMap<>();

        String sql = "SELECT id, nome FROM " + tabela + " ORDER BY id";

        try (Connection con = Cnx.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                opcoes.put(
                    rs.getInt("id"),
                    rs.getString("nome")
                );
            }
        }

        return opcoes;
    }

    public void salvar(Personagem personagem) throws SQLException {

        String sql = """
            INSERT INTO personagens
            (nome, raca_id, classe_id, habilidade_id)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = Cnx.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, personagem.getNome());
            stmt.setInt(2, personagem.getRacaId());
            stmt.setInt(3, personagem.getIdClasse());
            stmt.setInt(4, personagem.getHabilidadeId());

            stmt.executeUpdate();
        }
    }
}
