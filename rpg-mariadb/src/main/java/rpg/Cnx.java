package rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Cnx {
    private final String url;
    private final String usuario;
    private final String senha;

    private Cnx(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    public static Cnx doAmbiente() {
        String url = System.getenv().getOrDefault(
                "RPG_DB_URL", "jdbc:mariadb://127.0.0.1:3306/rpg?connectTimeout=5000");
        if (!url.startsWith("jdbc:mariadb://")) {
            throw new IllegalArgumentException("Use uma URL jdbc:mariadb:// em RPG_DB_URL.");
        }
        return new Cnx(url, exigirVariavel("RPG_DB_USER"), exigirVariavel("RPG_DB_PASSWORD"));
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }

    private static String exigirVariavel(String nome) {
        String valor = System.getenv(nome);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Defina a variável " + nome + ".");
        }
        return valor;
    }
}
