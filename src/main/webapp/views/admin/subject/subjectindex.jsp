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

    <%@ page import="java.util.List" %>
	<%@ page import="com.ent.entities.Course" %>

    <%@ include file="/views/admin/header.jsp" %>

    <div class="content-section-wrapper">
    
    	<div class="left-side-flex-box">
    		<a href="/ENT/adminportal">home page</a>
    	</div>
    	
    	<div class="left-side-flex-box">
    		<form id="filter-form" class="paginator">
                <input name="name_filter" placeholder="search for subjects">
                <select name="course_filter">
                	<option value="">filter by course</option>
                	<%
                		for(Course c : (List<Course>)request.getAttribute("courses") )
                		{
                			%>
                				<option value="<%= c.getUuid() %>"><%= c.getName() %></option>
                			<%
                		}
                	%>
                </select>
            </form>
    	</div>

        <div class="left-side-flex-box" id="table-dest-div"></div>
        
        <!-- course edit form -->
        <div class="left-side-flex-box">
        	<form id="subjectStoreForm" enctype="multipart/form-data" class="paginator">
	        	<h3>log new subject</h3>
        		<input type="text" name="name" placeholder="enter subject name">
        		<select name="course_id">
        			<%
                		for(Course c : (List<Course>)request.getAttribute("courses") )
                		{
                			%>
                				<option value="<%= c.getUuid() %>"><%= c.getName() %></option>
                			<%
                		}
                	%>
        		</select>
        	</form>
        	<button id="savebutton" onclick="storeSubject()">save</button>
        </div>

    </div>

</body>

<script type="module" src="/ENT/js/admin/subjects/subjectindex.js"></script>

</html>