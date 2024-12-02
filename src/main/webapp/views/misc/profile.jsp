<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>

    <script src="https://kit.fontawesome.com/5c8983dbc9.js" crossorigin="anonymous"></script>
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap" rel="stylesheet">
    <script type="module" src="/ENT/js/tdatatable/tdatatable.js"></script>
    
    <link rel="stylesheet" href="/ENT/views/teacher/style.css">
</head>
<body>
	
	<%
	    String role = (String) session.getAttribute("role");
	
	    if ("student".equals(role)) {
	%>
	        <%@ include file="/views/student/header.jsp" %>
	<%
	    } else if ("admin".equals(role)) {
	%>
	        <%@ include file="/views/admin/header.jsp" %>
	<%
	    } else if ("teacher".equals(role)) {
	%>
	        <%@ include file="/views/teacher/misc/header.jsp" %>
	<%
	    }
	%>

    <div class="content-section-wrapper">

        <div class="left-side-flex-box">
			<%@ page import="com.ent.entities.User" %>
        	<% User user = (User) session.getAttribute("user"); %>
        	<table>
        		<tr>
        			<th>name</th>
        			<td><%= user.getFirstName() %></td>
        		</tr>
    			<tr>
        			<th>surname</th>
        			<td><%= user.getSurname() %></td>
        		</tr>
        		<tr>
        			<th>email</th>
        			<td><%= user.getEmail() %></td>
        		</tr>
        		<tr>
        			<th>date of birth</th>
        			<td><%= user.getDateOfBirth() %></td>
        		</tr>
        		<tr>
        			<th>gender</th>
        			<td><%= user.getGender() %></td>
        		</tr>
        	</table>
        </div>
        
        <div class="left-side-flex-box">
            <a href="/ENT/profile/edit">modify</a>
        </div>

    </div>

</body>
</html>