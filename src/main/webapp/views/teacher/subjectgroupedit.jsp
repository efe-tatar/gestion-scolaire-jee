<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Portal</title>

    <script src="https://kit.fontawesome.com/5c8983dbc9.js" crossorigin="anonymous"></script>
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap" rel="stylesheet">
    <script type="module" src="/ENT/js/tdatatable/tdatatable.js"></script>
    
    <link rel="stylesheet" href="/ENT/views/teacher/style.css">
</head>
<body>

    <%@ include file="/views/teacher/misc/header.jsp" %>

    <div class="content-section-wrapper">

		<div class="left-side-flex-box">
        	<a href="/ENT/teacherportal/group?id=<%= request.getParameter("id") %>">return to class</a>
		</div>

        <div class="left-side-flex-box" id="table-dest-div"></div>
        
        <form id="filter-form"></form>

    </div>

</body>

<script type="module" src="/ENT/js/teacher/teacherportalgradeedit.js"></script>

</html>