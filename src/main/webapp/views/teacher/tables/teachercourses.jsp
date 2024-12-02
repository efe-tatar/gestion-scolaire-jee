

<%@ page import="java.util.List" %>
<%@ page import="com.ent.entities.GroupSubject" %>

<%
    List<GroupSubject> groupSubjects = (List<GroupSubject>)request.getAttribute("groupSubjects");
    
    for(GroupSubject gs : groupSubjects)
    {
        %>
            <div class="group-and-subject-display-card"
                onclick="location.href='/ENT/teacherportal/group?id=<%= gs.getUuid() %>';" style="cursor:pointer;">
                <h2><%= gs.getSubject().getName() %></h2>
                <h3><%= gs.getGroup().getName() %></h3>
            </div>
        <%
    }
%>