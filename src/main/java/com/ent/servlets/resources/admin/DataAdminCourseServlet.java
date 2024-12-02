package com.ent.servlets.resources.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ent.resources.admin.CourseControllerAdmin;

/**
 * Servlet implementation class DataAdminCourseServlet
 */
@WebServlet("/data/admin/courses")
@MultipartConfig
public class DataAdminCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataAdminCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("id") == null) {
			CourseControllerAdmin.index(request, response);
			return;
		}
		
		if(request.getParameter("type") != null && request.getParameter("type").equals("edit")) {
			CourseControllerAdmin.edit(request, response);
			return;
		}
		
		CourseControllerAdmin.show(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// check admin
		
		if (request.getParameter("type") == null) {
			CourseControllerAdmin.store(request, response);
		}
		else if (request.getParameter("type").equals("delete")) {
			
		}
		else if (request.getParameter("type").equals("patch")) {
			
		}
		
	}

}