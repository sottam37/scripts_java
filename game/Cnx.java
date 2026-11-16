
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cnx {

    private static final String URL =
        "jdbc:mariadb://localhost:3306/rpg";

    private static final String USUARIO = "root";
    private static final String SENHA = "12345678";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
            URL,
            USUARIO,
            SENHA
        );
    }
}
