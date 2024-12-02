
<%@ page import="java.util.List" %>
<%@ page import="com.ent.entities.Grade" %>
<%@ page import="com.ent.entities.Student" %>
<%@ page import="com.ent.entities.User" %>

<link rel="stylesheet" href="/ENT/views/css/datatable.css">

<table class="datatable">
	
	<tr>
		<th>Name</th>
		<th>Surname</th>
		<th>Session 1</th>
		<th>Session 2</th>
	</tr>

	<%
		List<Object[]> studentGrades = (List<Object[]>)request.getAttribute("studentGrades");
	
		for(Object[] studentGrade : studentGrades) {
			%>
				<tr>
					<td><%= ((Student)studentGrade[0]).getUser().getFirstName() %></td>
					
					<td><%= ((Student)studentGrade[0]).getUser().getSurname() %></td>
					
					<% Float s1 = studentGrade[1] != null ? ((Grade)studentGrade[1]).getSession1() : null; %>
					<td><%= s1 != null ? s1 : "" %></td>
					
					<% Float s2 = studentGrade[1] != null ? ((Grade)studentGrade[1]).getSession2() : null; %>
					<td><%= s2 != null ? s2 : "" %></td>
				</tr>
			<%
		}
	%>
</table>
<br>
<form id="studentsPaginator" onchange="processStudentTable()" class="paginator">
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