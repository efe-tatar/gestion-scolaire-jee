<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription</title>
    <link rel="stylesheet" href="/ENT/views/misc/connexion.css">
</head>
<body>
    <div class="login-container">
        <div class="logo">
            <img src="/ENT/images/logo.avif" alt="Logo ENT">
        </div>
        <h2>Inscription</h2>
        <br>
        <form action="/ENT/signup" method="post">
            <div class="input-box">
                <input name="first_name" type="text" placeholder="First Name" required>
            </div>
            <div class="input-box">
                <input name="surname" type="text" placeholder="Surname" required>
            </div>
            <div class="input-box">
                <input name="email" placeholder="Adresse e-mail" required>
            </div>
            <div class="input-box">
                <input name="date_of_birth" type="date" required>
            </div>
            <div class="input-box">
                <input name="password" type="password" placeholder="Mot de passe" required>
            </div>
            <div class="input-box">
                <select name="gender">
                	<option value="Male">Male</option>
                	<option value="Female">Female</option>
                	<option value="Other">Other</option>
                </select>
            </div>
            <%
            	if(request.getAttribute("errors") != null) {
            		%>
            			<p style="color: red"><%= request.getAttribute("errors") %></p>
            		<%
            	}
            %>
            <button type="submit" class="login-button">S'inscrire</button>
        </form>
        <a class="change1" href="signin">se connecter</a>
        <p class="welcome">Créez votre compte</p>
    </div>
</body>
</html>
