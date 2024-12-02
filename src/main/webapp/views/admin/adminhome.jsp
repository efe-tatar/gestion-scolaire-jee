<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Admin Portal</title>
	
	<link rel="stylesheet" href="/ENT/views/teacher/style.css">
	<script src="https://kit.fontawesome.com/5c8983dbc9.js" crossorigin="anonymous"></script>
</head>

<body>

	<%@ include file="/views/admin/header.jsp" %>
	
	<div class="left-side-flex-box">
		<div class="group-and-subject-display-card" onclick="location.href='/ENT/adminportal/students';">
			<h2>Students</h2>
			<h1><i class="fa-solid fa-graduation-cap"></i></h1>
		</div>
		<div class="group-and-subject-display-card" onclick="location.href='/ENT/adminportal/courses';">
			<h2>Courses</h2>
			<h1><i class="fa-solid fa-signs-post"></i></h1>
		</div>
		<div class="group-and-subject-display-card" onclick="location.href='/ENT/adminportal/subjects';">
			<h2>Subjects</h2>
			<h1><i class="fa-solid fa-book"></i></h1>
		</div>
		<div class="group-and-subject-display-card" onclick="location.href='/ENT/adminportal/teachers';">
			<h2>Teachers</h2>
			<h1><i class="fa-solid fa-chalkboard-user"></i></h1>
		</div>
		<div class="group-and-subject-display-card" onclick="location.href='/ENT/adminportal/groups';">
			<h2>Groups</h2>
			<h1><i class="fa-solid fa-people-group"></i></h1>
		</div>
	</div>
	
</body>
</html>