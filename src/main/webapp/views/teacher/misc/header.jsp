
<header class="main-header">
    <nav class="header-nav">
        <ul>
        	<li><h3>Dashboard</h3></li>
            <li><a href="/ENT/teacherportal">Home</a></li>
        </ul>
    </nav>
    <div class="header-logo header-nav">
    	<%@ page import="com.ent.entities.User" %>
    	<%
    		User user = (User)request.getSession().getAttribute("user");
    	%>
    	<ul>
    		<li>
    			<p><%= user.getSurname() + " " + user.getFirstName() %></p>
    		</li>
    		<li>
		        <a href="/ENT/profile"><img src="/ENT/images/logo.avif" alt="logo"></a>
    		</li>
    		<li>
		        <a href="/ENT/signout"><i class="fa-solid fa-arrow-right-from-bracket"></i></a>
    		</li>
    	</ul>
    </div>
</header>

<link rel="stylesheet" href="/ENT/views/css/header.css">