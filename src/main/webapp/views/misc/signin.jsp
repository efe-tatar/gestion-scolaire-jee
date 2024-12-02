<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
    <link rel="stylesheet" href="/ENT/views/misc/connexion.css">
</head>
<body>
    <div class="login-container">
        <div class="logo">
            <img src="/ENT/images/logo.avif" alt="Logo ENT">
        </div>
        <h2>Connexion</h2>
        <br>
        <form action="/ENT/signin" method="post">
            <div class="input-box">
                <input type="text" name="email" placeholder="Nom d'utilisateur" required>
            </div>
            <div class="input-box">
                <input type="password" name="password" placeholder="Mot de passe" required>
            </div>
            <%
            	if(request.getAttribute("errorMessage") != null) {
            		%>
            			<p style="color: red"><%= request.getAttribute("errorMessage") %></p>
            		<%
            	}
            %>
            <button type="submit" class="login-button">Se connecter</button>
        </form>
        <a class="change" href="signup">s'inscrire</a>
        <p class="welcome">Connectez Vous</p>
    </div>
</body>
</html>