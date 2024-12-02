<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>Teacher Portal</title>
	
	<script src="https://kit.fontawesome.com/5c8983dbc9.js" crossorigin="anonymous"></script>
	
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap" rel="stylesheet">
    <script type="module" src="/ENT/js/tdatatable/tdatatable.js"></script>
    <script type="module" src="/ENT/js/teacher/teacherportalgroup.js"></script>
    
    <link rel="stylesheet" href="/ENT/views/teacher/style.css">
</head>
<body>

	<%@ include file="/views/teacher/misc/header.jsp" %>
	
	<div class="content-section-wrapper">
	
		<div class="left-side-flex-box">
        	<a href="/ENT/teacherportal">return to classes</a>
		</div>
		
		<div class="left-side-flex-box">
			<%@ page import="com.ent.entities.GroupSubject" %>
			<%@ page import="com.ent.entities.Group" %>
			<%@ page import="com.ent.entities.Subject" %>
        	<% GroupSubject gs = ((GroupSubject)request.getAttribute("gs")); %>
        	<table>
        		<tr>
        			<th>group</th>
        			<td><%= gs.getGroup().getName() %></td>
        		</tr>
    			<tr>
        			<th>subject</th>
        			<td><%= gs.getSubject().getName() %></td>
        		</tr>
        	</table>
		</div>

        <div class="left-side-flex-box"> 
            <form id="filter-form">
                <input name="name_filter" placeholder="filter by name or surname">
            </form>
        </div>

        <div class="left-side-flex-box" id="table-dest-div"></div>

		<div class="left-side-flex-box">
			<a href="/ENT/teacherportal/group/edit?id=<%= request.getParameter("id") %>">modify</a>
		</div>

    </div>

</body>
</html>