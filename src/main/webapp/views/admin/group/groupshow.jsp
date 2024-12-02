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
    
    <link rel="stylesheet" href="/ENT/views/teacher/style.css">
</head>
<body>

	<%@ page import="com.ent.entities.Student" %>
	<%@ page import="com.ent.entities.User" %>
	<%@ page import="com.ent.entities.Group" %>
	<%@ page import="com.ent.entities.Subject" %>
	<%@ page import="com.ent.entities.Course" %>

    <%@ include file="/views/admin/header.jsp" %>

    <div class="content-section-wrapper">
    
	    <div class="left-side-flex-box">
	    	<a href="/ENT/adminportal/groups">return to groups</a>
	    </div>    

        <div>
            <form id="filter-form">
            </form>
        </div>
        <form id="emptyForm"></form>

		<!-- info -->
        <div class="left-side-flex-box" id="info-dest">
        	<% Group group = ((Group)request.getAttribute("group")); %>
        	<table>
        		<tr>
        			<th>name</th>
        			<td><%= group.getName() %></td>
        		</tr>
    			<tr>
        			<th>course</th>
        			<td><%= group.getCourse().getName() %></td>
        		</tr>
        		<tr>
        			<th>number of students</th>
        			<td><%= group.getStudents().size() %> students</td>
        		</tr>
        		<tr>
        			<th>number of subjects</th>
        			<td><%= group.getSubjects().size() %> subjects</td>
        		</tr>
        	</table>
        </div>
        
        <!-- students -->
        <div class="left-side-flex-box">
			<h4>Students</h4>
        
            <form id="student-table-filters" class="paginator">
                <input name="name_filter" placeholder="search for student">
                
                <input type="hidden" name="group_filter" value="<%= group.getUuid() %>">
                
                <select name="sort_order">
                	<option value="">sort by</option>
                	<option value="sur_desc">Surname - DESC</option>
                	<option value="sur_asc">Surname - ASC</option>
                </select>
                
                <select name="gender_filter">
                	<option value="">filter by gender</option>
                	<option value="Male">male</option>
                	<option value="Female">female</option>
                	<option value="Other">other</option>
                </select>
            </form>
            
        </div>
		<div class="left-side-flex-box" id="student-dest"></div>
		
		<!-- subjects -->
        <div class="left-side-flex-box">
			<h4>Subjects</h4>
        
            <form id="subject-table-filter" class="paginator">
                <input name="name_filter" placeholder="search for subjects">
                <input type="hidden" name="group_filter" value="<%= group.getUuid() %>">
            </form>
            
        </div>
		<div class="left-side-flex-box" id="subject-dest"></div>
		
		<%@ page import="java.util.List" %>
		<%@ page import="com.ent.entities.Teacher" %>
		<%@ page import="com.ent.entities.User" %>
		<%@ page import="com.ent.entities.Subject" %>
		
		<div class="left-side-flex-box">
			<h4>Add Subject</h4>
			<form class="paginator" id="teacherAppointmentForm">
			
				<input type="hidden" name="group" value="<%= group.getUuid() %>">
				
				<select name="subject">
					<%
						List<Subject> subjects = (List<Subject>)request.getAttribute("subjects");
					
						for(Subject subject : subjects) {
							%>
								<option value="<%= subject.getUuid() %>"><%= subject.getName() %></option>
							<%
						}
					%>
				</select>
				
				<select name="teacher">
					<%
						List<Teacher> teachers = (List<Teacher>)request.getAttribute("teachers");
					
						for(Teacher teacher : teachers) {
							%>
								<option value="<%= teacher.getUuid() %>"><%= teacher.getUser().getFirstName() + " " + teacher.getUser().getSurname() %></option>
							<%
						}
					%>
				</select>
			</form>
			<button onclick="assignTeacher()">save</button>
		</div>

    </div>

</body>

<script type="module" src="/ENT/js/admin/groups/groupstudents.js"></script>
<script type="module" src="/ENT/js/admin/groups/groupsubjects.js"></script>

</html>