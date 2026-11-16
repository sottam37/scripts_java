<!doctype html>
<html lang="pt-br">
<head>
  <meta charset="utf-8">
  <title>Login | Biblioteca Virtual</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f0f2f5;
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .card {
      border-radius: 12px;
      box-shadow: 0 6px 15px rgba(0,0,0,0.1);
    }
  </style>
</head>
<body>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-md-5">
      <div class="card p-4 bg-white">
        <h6 class="text-center mb-4">
          <i class="fas fa-book-reader text-primary"></i>Biblioteca Virtual
        </h6>



        <form method="POST" novalidate>
          <div class="mb-3">
            <label for="email" class="form-label">E-mail</label>
            <input type="email" class="form-control" id="email" name="email" required autofocus>
          </div>

          <div class="mb-3 position-relative">
            <label for="senha" class="form-label">Senha</label>
            <div class="input-group">
              <input type="password" class="form-control" id="senha" name="senha" required>
              <button type="button" class="btn btn-outline-secondary" id="toggleSenha">
                <i class="fas fa-eye"></i>
              </button>
            </div>
          </div>

          <button type="submit" class="btn btn-primary w-100">Entrar</button>
          <a href="register.jsp" class="btn btn-link w-100 mt-2">Quero me Cadastrar</a>
        </form>
      </div>
    </div>
  </div>
</div>

<script>
document.getElementById("toggleSenha").addEventListener("click", function () {
    const senhaInput = document.getElementById("senha");
    const icon = this.querySelector("i");
    if (senhaInput.type === "password") {
        senhaInput.type = "text";
        icon.classList.replace("fa-eye", "fa-eye-slash");
    } else {
        senhaInput.type = "password";
        icon.classList.replace("fa-eye-slash", "fa-eye");
    }
});
</script>

</body>
</html>
