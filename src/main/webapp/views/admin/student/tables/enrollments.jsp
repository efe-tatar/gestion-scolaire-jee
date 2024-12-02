
<%@ page import="java.util.List" %>
<%@ page import="com.ent.entities.Enrollment" %>
<%@ page import="com.ent.entities.Course" %>
<%@ page import="com.ent.entities.Student" %>

<link rel="stylesheet" href="/ENT/views/css/datatable.css">

<form id="enrollment-save-form" class="paginator">
	<h3>Enroll student</h3>
	<input type="hidden" name="studentId" value="<%= (String)request.getAttribute("studentId") %>">
	<select name="course">
		<%
			List<Course> courses = (List<Course>)request.getAttribute("courses");
			
			for(Course c : courses) {
				%>
					<option value="<%= c.getUuid() %>"><%= c.getName() %></option>
				<%
			}
		%>
	</select>
	<input type="date" name="startDate">
	<input type="date" name="endDate">
</form>
<button onclick="storeEnrollment()">save</button>

<table class="datatable">

	<tr>
		<th>Course</th>
		<th>start</th>
		<th>end</th>
	</tr>
	
	<%
		List<Enrollment> eList = (List<Enrollment>)request.getAttribute("enrollmentList");
	
		for(Enrollment e : eList) {
			%>
				<tr>
					<td><%= e.getCourse().getName() %></td>
					<td><%= e.getStartDate() %></td>
					<td><%= e.getEndDate() %></td>
					<td><i class="fa-solid fa-trash"></i></td>
				</tr>
			<%
		}
	%>

</table>