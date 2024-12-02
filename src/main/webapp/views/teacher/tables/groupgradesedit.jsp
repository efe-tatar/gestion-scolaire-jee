
<%@ page import="java.util.List" %>
<%@ page import="com.ent.entities.Grade" %>
<%@ page import="com.ent.entities.Student" %>
<%@ page import="com.ent.entities.User" %>
<%@ page import="com.ent.entities.GroupSubject" %>

<link rel="stylesheet" href="/ENT/views/css/datatable.css">

<form id="gradeForm">

	<input type="hidden" name="test" value="<%= ((GroupSubject)request.getAttribute("groupSubject")).getUuid() %>">

	<table class="datatable">
	
		<tr>
			<th>Surname</th>
			<th>Name</th>
			<th>Session 1</th>
			<th>Session 2</th>
		</tr>
		
		<%
			List<Object[]> studentGrades = (List<Object[]>)request.getAttribute("studentGrades");
			int i = 0;
			for(Object[] studentGrade : studentGrades) {
				%>
					<tr>
						<td><%= ((Student)studentGrade[0]).getUser().getFirstName() %>
							<input type="hidden" name="grades[<%= i %>][0]" value="<%= ((Student)studentGrade[0]).getUuid() %>">
						</td>
						
						<td><%= ((Student)studentGrade[0]).getUser().getSurname() %></td>
						
						<% Float s1 = studentGrade[1] != null ? ((Grade)studentGrade[1]).getSession1() : null; %>
						<% Float s2 = studentGrade[1] != null ? ((Grade)studentGrade[1]).getSession2() : null; %>
						
						<td><input type="text" name="grades[<%= i %>][1]"
							value="<%= s1 != null ? s1 : "" %>"></td>
						
						<td><input type="text" name="grades[<%= i %>][2]"
							value="<%= s2 != null ? s2 : "" %>"></td>
					</tr>
				<%
				i++;
			}
		%>
	
	</table>

</form>
<br>
<br>
<div>
	<button id="savebutton" onclick="saveButtonOnClick()">save</button>
</div>




