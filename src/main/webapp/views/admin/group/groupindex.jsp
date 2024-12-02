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

    <%@ include file="/views/admin/header.jsp" %>
    
    <%@ page import="java.util.List" %>
	<%@ page import="com.ent.entities.Course" %>

    <div class="content-section-wrapper">
    
	    <div class="left-side-flex-box">
        	<a href="/ENT/adminportal">home page</a>
	    </div>

        <div class="left-side-flex-box">
        
            <form id="filter-form" class="paginator">
            	<h3>Filter Groups</h3>
                <input name="name_filter" placeholder="search for courses">
                <select name="course_filter">
                	<option value="">-</option>
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
        
        <!-- course create form -->
        <div class="left-side-flex-box">
        	<form id="groupStoreForm" class="paginator">
	        	<h3>log new group</h3>
        		<input type="text" name="name" placeholder="enter group name">
        		<select name="course">
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
        	<button id="savebutton" onclick="storeGroup()">save</button>
        </div>

    </div>

</body>

<script type="module" src="/ENT/js/admin/groups/groupindex.js"></script>

</html>