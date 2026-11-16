<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Usuário</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
            border-radius: 15px;
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
        }
        .card-header {
            background: none;
            border-bottom: none;
            text-align: center;
            font-weight: bold;
            color: #343a40;
        }
        .card-header i {
            color: #0d6efd;
            margin-right: 8px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="col-md-6 col-lg-5 mx-auto">
        <div class="card p-4">
            <div class="card-header">
                <h5><i class="fas fa-user-plus"></i> Cadastro de Usuário</h5>
            </div>



            <form method="POST" novalidate>
                <div class="mb-3">
                    <label class="form-label">E-mail:</label>
                    <input type="email" name="email" class="form-control" placeholder="Digite seu e-mail" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Senha:</label>
                    <div class="input-group">
                        <input type="password" name="password" id="senha" class="form-control" placeholder="Digite sua senha" required>
                        <button type="button" class="btn btn-outline-secondary" id="toggleSenha">
                            <i class="fas fa-eye"></i>
                        </button>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary w-100">Cadastrar</button>
                <a href="../index.jsp" class="btn btn-link w-100 mt-2">Já tenho conta</a>
            </form>
        </div>
    </div>
</div>

<script>
document.getElementById("toggleSenha").addEventListener("click", function () {
    const senhaInput = document.getElementById("senha");
    const icon = this.querySelector("i");
    senhaInput.type = senhaInput.type === "password" ? "text" : "password";
    icon.classList.toggle("fa-eye");
    icon.classList.toggle("fa-eye-slash");
});
</script>

</body>
</html>

