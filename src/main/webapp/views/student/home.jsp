<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Student Portal</title>
	
	<link rel="stylesheet" href="/ENT/views/teacher/style.css">
	<link rel="stylesheet" href="/ENT/views/css/header.css">
	<link rel="stylesheet" href="/ENT/views/css/datatable.css">
	<script src="https://kit.fontawesome.com/5c8983dbc9.js" crossorigin="anonymous"></script>
</head>

<body>

	<%@ include file="/views/student/header.jsp" %>
	
	<div class="content-section-wrapper">

		<%@ page import="java.util.List" %>
		<%@ page import="com.ent.entities.Course" %>
		<%@ page import="com.ent.entities.Enrollment" %>

		<!-- ENROLLMENTS -->
		<div class="left-side-flex-box">
			<table class="datatable">
				<tr>
					<th>Course</th>
					<th>start</th>
					<th>end</th>
				</tr>
			
				<%
					List<Enrollment> enrollments = (List<Enrollment>)request.getAttribute("enrollments");
				
					for(Enrollment e : enrollments) {
						%>
							<tr>
								<td><%= e.getCourse().getName() %></td>
								<td><%= e.getStartDate() %></td>
								<td><%= e.getEndDate() %></td>
								<td><a href="/ENT/data/student/review?course=<%= e.getCourse().getUuid() %>"><i class="fa-solid fa-file-export"></i></a></td>
							</tr>
						<%
					}
				%>
				
			</table>
		</div>
		
		<!-- GRADES -->
		<div class="left-side-flex-box">
			<form id="gradeFilter" class="paginator">
				<h3>grades</h3>
				<select name="enrollment">
					<option value="">-</option>
					<%
						List<Enrollment> enrolls = (List<Enrollment>)request.getAttribute("enrollments");
					
						for(Enrollment e : enrolls) {
							%>
								<option value="<%= e.getUuid() %>"><%= e.getCourse().getName() %></option>
							<%
						}
					%>
				</select>
			</form>
		</div>
		<div class="left-side-flex-box" id="gradesDest">
		</div>
	
	</div>

</body>

<script type="module" src="/ENT/js/student/grades.js"></script>

</html>