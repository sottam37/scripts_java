
package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Configuração do banco de dados
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private Connection conectarBanco() throws Exception {

        Class.forName("org.mariadb.jdbc.Driver");

        String url = "jdbc:mariadb://" + DB_HOST + ":"
                + DB_PORT + "/" + DB_NAME;

        return DriverManager.getConnection(
                url,
                DB_USER,
                DB_PASSWORD
        );
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String erro = request.getParameter("erro");

        out.println("""
            <!DOCTYPE html>
            <html lang="pt-br">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Login | Biblioteca Virtual</title>

                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">

                <style>
                    body {
                        background-color: #f0f2f5;
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                    }

                    .card {
                        border-radius: 12px;
                        box-shadow: 0 6px 15px rgba(0,0,0,0.1);
                        border: none;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-md-5 col-lg-4">

                            <div class="card p-4 bg-white">

                                <h5 class="text-center mb-4">
                                    <i class="fas fa-book-reader text-primary"></i>
                                    Biblioteca Virtual
                                </h5>
            """);

        if ("credenciais".equals(erro)) {
            out.println("""
                <div class="alert alert-danger text-center">
                    E-mail ou senha inválidos.
                </div>
                """);
        } else if ("sistema".equals(erro)) {
            out.println("""
                <div class="alert alert-danger text-center">
                    Erro ao processar o login. Tente novamente.
                </div>
                """);
        }

        out.println("""
                                <form method="POST" action="login">

                                    <div class="mb-3">
                                        <label class="form-label">E-mail</label>

                                        <input type="email"
                                            class="form-control"
                                            name="email"
                                            placeholder="Digite seu e-mail"
                                            required autofocus>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Senha</label>

                                        <div class="input-group">
                                            <input type="password"
                                                class="form-control"
                                                id="senha"
                                                name="senha"
                                                placeholder="Digite sua senha"
                                                required>

                                            <button type="button"
                                                class="btn btn-outline-secondary"
                                                onclick="alternarSenha()">
                                                <i class="fas fa-eye" id="iconeSenha"></i>
                                            </button>
                                        </div>
                                    </div>

                                    <button type="submit"
                                        class="btn btn-primary w-100">
                                        <i class="fas fa-sign-in-alt me-2"></i>
                                        Entrar
                                    </button>

                                    <a href="cadastro"
                                        class="btn btn-link w-100 mt-2">
                                        Quero me cadastrar
                                    </a>

                                </form>

                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    function alternarSenha() {
                        const campo = document.getElementById("senha");
                        const icone = document.getElementById("iconeSenha");

                        if (campo.type === "password") {
                            campo.type = "text";
                            icone.classList.replace("fa-eye", "fa-eye-slash");
                        } else {
                            campo.type = "password";
                            icone.classList.replace("fa-eye-slash", "fa-eye");
                        }
                    }
                </script>
            </body>
            </html>
            """);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (email == null || senha == null ||
                email.isBlank() || senha.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/login?erro=credenciais"
            );
            return;
        }

        String sql = """
            SELECT id, email, senha
            FROM usuarios
            WHERE email = ?
            LIMIT 1
            """;

        try (
            Connection conn = conectarBanco();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, email.trim());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String senhaBanco = rs.getString("senha");

                    boolean autenticado = senhaBanco != null
                            && BCrypt.checkpw(senha, senhaBanco);

                    if (autenticado) {

                        HttpSession session = request.getSession(true);

                        request.changeSessionId();

                        session.setAttribute(
                                "usuarioId",
                                rs.getInt("id")
                        );

                        session.setAttribute(
                                "usuarioEmail",
                                rs.getString("email")
                        );

                        session.setMaxInactiveInterval(30 * 60);

                        response.sendRedirect(
                                request.getContextPath() + "/dashboard.jsp"
                        );

                        return;
                    }
                }
            }

            response.sendRedirect(
                    request.getContextPath() + "/login?erro=credenciais"
            );

        } catch (Exception e) {

            getServletContext().log("Erro na autenticação da Biblioteca", e);

            response.sendRedirect(
                    request.getContextPath() + "/login?erro=sistema"
            );
        }
    }
}
```