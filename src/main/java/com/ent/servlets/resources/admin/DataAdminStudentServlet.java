package com.ent.servlets.resources.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ent.resources.admin.StudentControllerAdmin;

/**
 * Servlet implementation class DataAdminStudentServlet
 */
@WebServlet("/data/admin/students")
@MultipartConfig
public class DataAdminStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataAdminStudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("id") == null) {
			StudentControllerAdmin.index(request, response);
			return;
		}
		
		if(request.getParameter("type") != null && request.getParameter("type").equals("edit")) {
			StudentControllerAdmin.edit(request, response);
			return;
		}
		
		StudentControllerAdmin.show(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
