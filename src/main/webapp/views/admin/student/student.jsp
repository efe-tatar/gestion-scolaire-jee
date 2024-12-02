<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Portal</title>

    <script src="https://kit.fontawesome.com/5c8983dbc9.js" crossorigin="anonymous"></script>
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap" rel="stylesheet">
    <script type="module" src="/ENT/js/tdatatable/tdatatable.js"></script>
    <script type="module" src="/ENT/js/admin/students/studentshow.js"></script>
    <script type="module" src="/ENT/js/admin/students/studentenrollment.js"></script>
	<script type="module" src="/ENT/js/admin/students/studentgroup.js"></script>
    
    <link rel="stylesheet" href="/ENT/views/teacher/style.css">
</head>
<body>

    <%@ include file="/views/admin/header.jsp" %>

    <div class="content-section-wrapper">
    
		<div class="left-side-flex-box">
	    	<a href="/ENT/adminportal/students">return to students</a>
		</div>

        <div>
            <form id="filter-form">
            </form>
        </div>
        <form id="emptyForm"></form>

		<!-- info -->
        <div class="left-side-flex-box" id="info-dest">
        	<%@ page import="com.ent.entities.Student" %>
			<%@ page import="com.ent.entities.User" %>
        	<% User studentUser = ((Student)request.getAttribute("student")).getUser(); %>
        	<table>
        		<tr>
        			<th>name</th>
        			<td><%= studentUser.getFirstName() %></td>
        		</tr>
    			<tr>
        			<th>surname</th>
        			<td><%= studentUser.getSurname() %></td>
        		</tr>
        		<tr>
        			<th>email</th>
        			<td><%= studentUser.getEmail() %></td>
        		</tr>
        		<tr>
        			<th>date of birth</th>
        			<td><%= studentUser.getDateOfBirth() %></td>
        		</tr>
        		<tr>
        			<th>gender</th>
        			<td><%= studentUser.getGender() %></td>
        		</tr>
        	</table>
        </div>
        
        <form id="enrollments-form">
        	<input type="hidden" name="studentId" value="<%= ((Student)request.getAttribute("student")).getUuid() %>">
        </form>
        <!-- inscriptions -->
        <div class="left-side-flex-box" id="enrollment-list-dest">
        </div>
        
        <!-- groups -->
        <%@ page import="java.util.List" %>
		<%@ page import="com.ent.entities.Group" %>
        <div class="left-side-flex-box">
	        <form id="studentaddform" class="paginator">
		        <h3>Student Groups</h3>
	        	<input type="hidden" name="student" value="<%= ((Student)request.getAttribute("student")).getUuid() %>">
	        	<select name="group">
	        		<%
						List<Group> potentialGroups = (List<Group>)request.getAttribute("potentialGroups");
					
						for(Group g : potentialGroups) {
							%>
								<option value="<%= g.getUuid() %>"><%= g.getName() %></option>
							<%
						}
					%>
	        	</select>
	        </form>
	        <button onclick="addStudent()">save</button>
	        <form id="groups-filter-form">
	        	<input type="hidden" name="student_filter" value="<%= ((Student)request.getAttribute("student")).getUuid() %>">
	        </form>
        </div>
        <div class="left-side-flex-box" id="groups-list-dest">
        </div>
        
        <h4>Grades</h4>
        <!-- grades -->
        <div class="left-side-flex-box" id="grades-list-dest">
        </div>

    </div>

</body>
</html>