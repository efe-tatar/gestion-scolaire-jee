
<%@ page import="java.util.List" %>
<%@ page import="com.ent.entities.Course" %>

<link rel="stylesheet" href="/ENT/views/css/datatable.css">

<table class="datatable">
	
	<tr>
		<td>Name</td>
	</tr>

	<%
		List<Course> courses = (List<Course>)request.getAttribute("courses");
	
		for(Course course : courses) {
			%>
				<tr>
					<td><%= course.getName() %></td>
					<td><a href="/ENT/adminportal/courses?id=<%= course.getUuid() %>"><i class="fa-solid fa-eye"></i></a></td>
				</tr>
			<%
		}
	%>
	
</table>
<br><br>
<form id="studentsPaginator" onchange="processTable()" class="paginator">
	<p>total of <%= request.getAttribute("numberOfElements") %> elements</p>
	<p>page number: </p>
	<select name="pageIndex">
		<option value="1" <%= (Integer)request.getAttribute("pageIndex") == 1 ? "selected" : "" %> >1</option>
		<%
			for(int i = 2 ; i <= (Integer)request.getAttribute("numberOfPages") ; i++) {
				%>
				<option value="<%= i %>" <%= (Integer)request.getAttribute("pageIndex") == i ? "selected" : "" %> ><%= i %></option>
				<%
			}
		%>
	</select>
	<p>page size: </p>
	<select name="pageSize">
		<option value="5" <%= (Integer)request.getAttribute("pageSize") == 5 ? "selected" : "" %>>5</option>
		<option value="10" <%= (Integer)request.getAttribute("pageSize") == 10 ? "selected" : "" %>>10</option>
		<option value="25" <%= (Integer)request.getAttribute("pageSize") == 25 ? "selected" : "" %>>25</option>
		<option value="50" <%= (Integer)request.getAttribute("pageSize") == 50 ? "selected" : "" %>>50</option>
	</select>
</form>