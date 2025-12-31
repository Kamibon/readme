<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <div class="overlay"></div>

        <div class="login-box">
            <h1>📚 Login</h1>

            <form class="login-form" action="your-login-endpoint" method="POST">
                <div class="input-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="login-btn">Accedi</button>
            </form>

            <p class="register-link">
                Non hai un account?
                <a href="signup.html">Registrati</a>
            </p>
        </div>
    </div>
</body>
</html>
