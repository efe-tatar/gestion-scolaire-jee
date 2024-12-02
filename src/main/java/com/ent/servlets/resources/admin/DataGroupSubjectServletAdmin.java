package com.ent.servlets.resources.admin;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import com.ent.entities.Group;
import com.ent.entities.Subject;
import com.ent.entities.Teacher;
import com.ent.services.GroupService;
import com.ent.services.SubjectService;
import com.ent.services.TeacherService;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;

/**
 * Servlet implementation class DataGroupSubjectServletAdmin
 */
@WebServlet("/data/admin/groupsubjects")
@MultipartConfig
public class DataGroupSubjectServletAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataGroupSubjectServletAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManager manager = HibernateUtil.getEntityManager();

		Group group = new GroupService(manager).getGroupByUuid(request.getParameter("group"));
		Teacher teacher = new TeacherService(manager).getTeacherByUuid(request.getParameter("teacher"));
		Subject subject = new SubjectService(manager).getSubjectByUuid(request.getParameter("subject"));
		
		for(Subject s : group.getSubjects()) {
			if(s.getUuid().equals(subject.getUuid())) {
				String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError("A teacher has alreay been assigned to this subject.");
				response.getWriter().write(jsonFormattedResponse);
				return;
			}
		}
		
		manager.getTransaction().begin();
		
		try {
			String query = "INSERT INTO group_subjects (uuid, id_group, id_subject, id_teacher) VALUES (:uuid, :idGroup, :idSubject, :idTeacher)";
			manager.createNativeQuery(query)
			       .setParameter("uuid", UUID.randomUUID().toString())
			       .setParameter("idGroup", group.getId())
			       .setParameter("idSubject", subject.getId())
			       .setParameter("idTeacher", teacher.getId())
			       .executeUpdate();
		}
		catch(Exception e) {
			manager.getTransaction().rollback();
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError("Internal Error.");
			response.getWriter().write(jsonFormattedResponse);
			return;
		}

		manager.getTransaction().commit();
		
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, "");
		response.getWriter().write(jsonFormattedResponse);
		
	}

}
