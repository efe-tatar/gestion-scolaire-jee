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

import com.ent.entities.Student;
import com.ent.entities.Subject;
import com.ent.entities.Group;
import com.ent.services.StudentService;
import com.ent.services.GroupService;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;

/**
 * Servlet implementation class DataGroupStudentAdmin
 */
@WebServlet("/data/admin/groupstudents")
@MultipartConfig
public class DataGroupStudentAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataGroupStudentAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager manager = HibernateUtil.getEntityManager();
		
		Student student = new StudentService(manager).getStudentByUuid(request.getParameter("student"));
		Group group = new GroupService(manager).getGroupByUuid(request.getParameter("group"));
		
		for(Student s : group.getStudents()) {
			if(s.getUuid().equals(student.getUuid())) {
				String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError("Student already in group.");
				response.getWriter().write(jsonFormattedResponse);
				return;
			}
		}
		
		manager.getTransaction().begin();
		
		try {
			String query = "INSERT INTO group_students (uuid, id_group, id_student) VALUES (:uuid, :idGroup, :idStudent)";
			manager.createNativeQuery(query)
			       .setParameter("uuid", UUID.randomUUID().toString())
			       .setParameter("idGroup", group.getId())
			       .setParameter("idStudent", student.getId())
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
