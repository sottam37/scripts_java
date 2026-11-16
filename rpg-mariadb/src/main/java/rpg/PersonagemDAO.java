package rpg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PersonagemDAO {
    private final Cnx conexao;

    public PersonagemDAO(Cnx conexao) {
        this.conexao = Objects.requireNonNull(conexao);
    }

    public List<Raca> listarRacas() throws SQLException {
        return consultar("SELECT id, nome FROM racas ORDER BY id",
                rs -> new Raca(rs.getInt("id"), rs.getString("nome")));
    }

    public List<Classes> listarClasses() throws SQLException {
        return consultar("SELECT id, nome FROM classes ORDER BY id",
                rs -> new Classes(rs.getInt("id"), rs.getString("nome")));
    }

    public List<Reinos> listarReinos() throws SQLException {
        return consultar("SELECT id, nome FROM reinos ORDER BY id",
                rs -> new Reinos(rs.getInt("id"), rs.getString("nome")));
    }

    public List<Habilidades> listarHabilidades(Classes classe) throws SQLException {
        return consultar("""
                SELECT h.id, h.nome, h.efeito
                FROM classe_habilidades ch
                JOIN habilidades h ON h.id = ch.habilidade_id
                WHERE ch.classe_id = ?
                ORDER BY h.id
                """, rs -> new Habilidades(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("efeito")), classe.getId());
    }

    public List<Armas> listarArmas(Classes classe) throws SQLException {
        return consultar("""
                SELECT a.id, a.nome, a.dano
                FROM classe_armas ca
                JOIN armas a ON a.id = ca.arma_id
                WHERE ca.classe_id = ?
                ORDER BY a.id
                """, rs -> new Armas(rs.getInt("id"), rs.getString("nome"),
                        rs.getObject("dano", Integer.class)), classe.getId());
    }

    public long salvar(Personagem personagem) throws SQLException {
        String sql = """
                INSERT INTO personagens
                    (nome, raca_id, classe_id, habilidade_id, arma_id, reino_id,
                     nivel, experiencia, vida, mana, forca)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, personagem.getNome());
            stmt.setInt(2, personagem.getRaca().getId());
            stmt.setInt(3, personagem.getClasse().getId());
            stmt.setInt(4, personagem.getHabilidade().getId());
            stmt.setInt(5, personagem.getArma().getId());
            Reinos reino = personagem.getReino();
            if (reino == null) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, reino.getId());
            }
            stmt.setInt(7, personagem.getNivel());
            stmt.setInt(8, personagem.getExperiencia());
            stmt.setInt(9, personagem.getVida());
            stmt.setInt(10, personagem.getMana());
            stmt.setInt(11, personagem.getForca());
            stmt.executeUpdate();
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    return chaves.getLong(1);
                }
                throw new SQLException("O banco não retornou o ID da inserção.");
            }
        }
    }

    private <T> List<T> consultar(String sql, Leitor<T> leitor, int... parametros)
            throws SQLException {
        List<T> opcoes = new ArrayList<>();
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                stmt.setInt(i + 1, parametros[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    opcoes.add(leitor.ler(rs));
                }
            }
        }
        return opcoes;
    }

    @FunctionalInterface
    private interface Leitor<T> {
        T ler(ResultSet linha) throws SQLException;
    }
}
