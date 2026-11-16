
package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init() throws ServletException {

        dbHost = System.getenv("DB_HOST");
        dbPort = System.getenv("DB_PORT");
        dbName = System.getenv("DB_NAME");
        dbUser = System.getenv("DB_USER");
        dbPassword = System.getenv("DB_PASSWORD");

        if (dbHost == null || dbPort == null ||
            dbName == null || dbUser == null ||
            dbPassword == null) {

            throw new ServletException(
                "Variáveis de conexão com o banco não configuradas."
            );
        }

        getServletContext().log(
            "LoginServlet inicializado."
        );
    }

    // =====================================================
    // CONEXÃO COM O BANCO DE DADOS
    // =====================================================

    private Connection conectarBanco() throws SQLException {

        String url = "jdbc:mariadb://" +
                     dbHost + ":" +
                     dbPort + "/" +
                     dbName;

        return DriverManager.getConnection(
            url,
            dbUser,
            dbPassword
        );
    }

    // =====================================================
    // VALIDAÇÃO DA SENHA BCRYPT
    // =====================================================

    private boolean validarSenha(
            String senhaDigitada,
            String senhaBanco) {

        if (senhaDigitada == null ||
            senhaBanco == null ||
            senhaBanco.length() != 60) {

            return false;
        }

        try {

            // Compatibilidade com BCrypt gerado pelo PHP
            // O jBCrypt utiliza o prefixo $2a$.

            if (senhaBanco.startsWith("$2y$")) {

                senhaBanco =
                    "$2a$" + senhaBanco.substring(4);

            } else if (senhaBanco.startsWith("$2b$")) {

                senhaBanco =
                    "$2a$" + senhaBanco.substring(4);
            }

            if (!senhaBanco.startsWith("$2a$")) {
                return false;
            }

            return BCrypt.checkpw(
                senhaDigitada,
                senhaBanco
            );

        } catch (IllegalArgumentException e) {

            getServletContext().log(
                "Erro na validação do hash BCrypt.", e
            );

            return false;
        }
    }

    // =====================================================
    // MÉTODO GET
    // =====================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
            request.getContextPath() + "/login.html"
        );
    }

    // =====================================================
    // MÉTODO POST - AUTENTICAÇÃO
    // =====================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        String contexto = request.getContextPath();

        // -------------------------------------------------
        // VALIDAÇÃO DOS CAMPOS
        // -------------------------------------------------

        if (email == null || senha == null ||
            email.trim().isEmpty() || senha.isEmpty()) {

            response.sendRedirect(
                contexto + "/login.html?erro=campos"
            );

            return;
        }

        email = email.trim();

        // -------------------------------------------------
        // CONSULTA AO BANCO
        // -------------------------------------------------

        String sql =
            "SELECT id, email, senha " +
            "FROM usuarios " +
            "WHERE email = ? " +
            "LIMIT 1";

        try (
            Connection conexao = conectarBanco();

            PreparedStatement stmt =
                conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {

                // -----------------------------------------
                // USUÁRIO ENCONTRADO
                // -----------------------------------------

                if (rs.next()) {

                    int usuarioId = rs.getInt("id");

                    String usuarioEmail =
                        rs.getString("email");

                    String senhaBanco =
                        rs.getString("senha");

                    // -------------------------------------
                    // VALIDAÇÃO DA SENHA
                    // -------------------------------------

                    boolean autenticado =
                        validarSenha(senha, senhaBanco);

                    if (autenticado) {

                        // Cria sessão autenticada
                        HttpSession session =
                            request.getSession(true);

                        // Proteção contra session fixation
                        request.changeSessionId();

                        session.setAttribute(
                            "usuarioId",
                            usuarioId
                        );

                        session.setAttribute(
                            "usuarioEmail",
                            usuarioEmail
                        );

                        session.setAttribute(
                            "autenticado",
                            Boolean.TRUE
                        );

                        session.setAttribute(
                            "mensagemLogin",
                            "Login realizado com sucesso!"
                        );

                        // Expiração após 30 minutos de inatividade
                        session.setMaxInactiveInterval(1800);

                        getServletContext().log(
                            "Login realizado para: " + usuarioEmail
                        );

                        // Redireciona para o painel
                        response.sendRedirect(
                            contexto + "/dashboard.jsp"
                        );

                        return;
                    }
                }
            }

            // -------------------------------------------------
            // USUÁRIO OU SENHA INCORRETOS
            // -------------------------------------------------

            response.sendRedirect(
                contexto + "/login.html?erro=credenciais"
            );

        } catch (SQLException e) {

            getServletContext().log(
                "Erro SQL durante autenticação.", e
            );

            response.sendRedirect(
                contexto + "/login.html?erro=sistema"
            );

        } catch (Exception e) {

            getServletContext().log(
                "Erro inesperado durante autenticação.", e
            );

            response.sendRedirect(
                contexto + "/login.html?erro=sistema"
            );
        }
    }
}
