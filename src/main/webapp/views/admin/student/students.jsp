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
    <script type="module" src="/ENT/js/admin/students/studentsindex.js"></script>
    
    <link rel="stylesheet" href="/ENT/views/teacher/style.css">
</head>
<body>

    <%@ include file="/views/admin/header.jsp" %>

    <div class="content-section-wrapper">
       	
       	<div class="left-side-flex-box">
	       	<a href="/ENT/adminportal">home page</a>
       	</div>

        <div class="left-side-flex-box">
        
            <form id="filter-form" class="paginator">
	        	<h2>Students</h2>
	        	
                <input name="name_filter" placeholder="search for student">
                
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
		
		<div class="left-side-flex-box" id="table-dest-div"></div>

    </div>

</body>
</html>