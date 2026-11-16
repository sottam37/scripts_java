
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%
    if (session.getAttribute("autenticado") == null ||
        !Boolean.TRUE.equals(session.getAttribute("autenticado"))) {

        response.sendRedirect(
            request.getContextPath() + "/login.html"
        );

        return;
    }

    String usuarioEmail =
        (String) session.getAttribute("usuarioEmail");

    String mensagemLogin =
        (String) session.getAttribute("mensagemLogin");
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Biblioteca Virtual - Painel</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f9;
        }

        header {
            background: #243b55;
            color: white;
            padding: 20px;
        }

        main {
            max-width: 1000px;
            margin: 40px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
        }

        .sucesso {
            background: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .botao {
            display: inline-block;
            padding: 10px 20px;
            background: #243b55;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<header>
    <h2>Biblioteca Virtual</h2>
</header>

<main>

    <% if (mensagemLogin != null) { %>

        <div class="sucesso">
            <%= mensagemLogin %>
        </div>

        <% session.removeAttribute("mensagemLogin"); %>

    <% } %>

    <h1>Bem-vindo ao sistema!</h1>

    <p>
        Login realizado com sucesso!
    </p>

    <p>
        Usuário autenticado:
        <strong><%= usuarioEmail %></strong>
    </p>

    <a href="<%= request.getContextPath() %>/logout"
       class="botao">
        Sair do sistema
    </a>

</main>

</body>
</html>
