package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String url = String.format("jdbc:mariadb://%s:%s/%s",
                System.getenv("DB_HOST"), System.getenv("DB_PORT"), System.getenv("DB_NAME"));

        try (Connection c = DriverManager.getConnection(url,
                System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION(), NOW()")) {

            rs.next();
            out.println("Jetty conectado ao MariaDB!");
            out.println("Versao do banco: " + rs.getString(1));
            out.println("Hora do banco:   " + rs.getString(2));
        } catch (Exception e) {
            resp.setStatus(500);
            out.println("Erro ao conectar no banco: " + e.getMessage());
        }
    }
}
