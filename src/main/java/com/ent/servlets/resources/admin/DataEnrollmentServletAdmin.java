package com.ent.servlets.resources.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ent.resources.admin.CourseControllerAdmin;
import com.ent.resources.admin.StudentEnrollmentControllerAdmin;

/**
 * Servlet implementation class DataEnrollmentServletAdmin
 */
@WebServlet("/data/admin/enrollments")
@MultipartConfig
public class DataEnrollmentServletAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataEnrollmentServletAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StudentEnrollmentControllerAdmin.index(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("type") == null) {
			StudentEnrollmentControllerAdmin.store(request, response);
		}
		else if (request.getParameter("type").equals("delete")) {
			
		}
		else if (request.getParameter("type").equals("patch")) {
			
		}
		

	}

}
