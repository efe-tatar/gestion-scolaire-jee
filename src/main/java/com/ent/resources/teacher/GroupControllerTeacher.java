package com.ent.resources.teacher;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

import com.ent.entities.GroupSubject;
import com.ent.entities.Student;
import com.ent.entities.Teacher;
import com.ent.entities.Enrollment;
import com.ent.entities.Grade;
import com.ent.entities.User;
import com.ent.services.GroupSubjectService;
import com.ent.services.MailService;
import com.ent.services.TeacherService;
import com.ent.services.GradeService;
import com.ent.services.StudentService;
import com.ent.utils.ConstantsUtil;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class GroupControllerTeacher {
	
	/*
	 * 
	 */
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		EntityManager manager = HibernateUtil.getEntityManager();

		User user = (User) request.getSession().getAttribute("user");
		Query q = HibernateUtil.deriveQuery("SELECT t FROM Teacher t WHERE t.user = :user", manager);
		q.setParameter("user", user);
		Teacher teacher = (Teacher) q.getSingleResult();
        
        String queryString = "SELECT u FROM GroupSubject u where u.teacher = :teacher";
        
        String nameFilter = request.getParameter("name_filter");
        if(nameFilter != null) {
        	queryString += " AND (u.subject.name like :namefilter OR u.group.name like :namefilter)";
        }
        
        TypedQuery<GroupSubject> query = HibernateUtil.getEntityManagerFactory().createEntityManager()
				.createQuery(queryString, GroupSubject.class);
        
        query.setParameter("teacher", teacher);
		if(nameFilter != null)
			query.setParameter("namefilter", "%"+nameFilter+"%");
		
		List<GroupSubject> groupSubjects = query.getResultList();
		request.setAttribute("groupSubjects", groupSubjects);
		
		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/teacher/tables/teachercourses.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
	}
	
	/*
	 * 
	 */
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		GroupSubject gs = new GroupSubjectService(manager).getGroupSubjectByUuid(request.getParameter("id"));
		
		String nameFilter = request.getParameter("name_filter");
		
		String queryString = "SELECT s, g"
				+ " FROM Student s"
				+ " LEFT JOIN Grade g ON g.student.id = s.id"
				+ " AND g.subject = :idSubject"
				+ " AND g.enrollment.course = :idCourse"
				+ " WHERE :idGroup MEMBER OF s.groups";
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			queryString += " AND (s.user.firstName like :nameFilter OR s.user.surname like :nameFilter)";
		}
		
		Query query = HibernateUtil.getEntityManagerFactory().createEntityManager()
				.createQuery(queryString);
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			query.setParameter("nameFilter", "%" + nameFilter + "%");
		}
        
		query.setParameter("idSubject", gs.getSubject());
		query.setParameter("idCourse", gs.getGroup().getCourse());
		query.setParameter("idGroup", gs.getGroup());
        
        List<Object[]> studentGrades = query.getResultList();
		
		DataTableUtil<Object[]> tableManager = new DataTableUtil<Object[]>(request);
		
		List<Object[]> sublist = tableManager.paginate(studentGrades);
		request.setAttribute("studentGrades", sublist);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/teacher/tables/teachergroup.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
	}
	
	/*
	 * 
	 */
	public static void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		GroupSubject gs = new GroupSubjectService(manager).getGroupSubjectByUuid(request.getParameter("id"));
		
		String queryString = "SELECT s, g"
				+ " FROM Student s"
				+ " LEFT JOIN Grade g ON g.student.id = s.id"
				+ " AND g.subject = :idSubject"
				+ " AND g.enrollment.course = :idCourse"
				+ " WHERE :idGroup MEMBER OF s.groups";
		
		Query query = HibernateUtil.getEntityManagerFactory().createEntityManager()
				.createQuery(queryString);
        
		query.setParameter("idSubject", gs.getSubject());
		query.setParameter("idCourse", gs.getGroup().getCourse());
		query.setParameter("idGroup", gs.getGroup());
        
        List<Object[]> studentGrades = query.getResultList();
		request.setAttribute("studentGrades", studentGrades);
		request.setAttribute("groupSubject", gs);
		
		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/teacher/tables/groupgradesedit.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
	}
	
	/*
	 * 
	 */
	public static void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println("grade controller update method");
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		GroupSubject gs = new GroupSubjectService(manager).getGroupSubjectByUuid(request.getParameter("test"));
		
		String queryString =
				"SELECT g"
				+ " FROM Grade g"
				+ " WHERE g.subject = :subjectInstance"
				+ " AND g.enrollment.course = :courseInstance";
		
		Query query = HibernateUtil.deriveQuery(queryString, manager);
		query.setParameter("subjectInstance", gs.getSubject());
		query.setParameter("courseInstance", gs.getGroup().getCourse());
		
		List<Grade> grades = query.getResultList();
		Map<String, Grade> gradesMap = new HashMap<>();
		for(Grade g : grades) gradesMap.put(g.getStudent().getUuid(), g);
		
		GradeService gradeService = new GradeService(manager);
		
		// EntityTransaction transaction = manager.getTransaction();
		// transaction.begin();
		
		MailService mailService = new MailService(ConstantsUtil.username, ConstantsUtil.password);
		
		int i = 0;
		while (request.getParameter("grades["+ i +"][0]") != null) {
			System.out.println("loop");
			String uuid = request.getParameter("grades["+ i +"][0]");
			String s1 = request.getParameter("grades["+ i +"][1]");
			String s2 = request.getParameter("grades["+ i +"][2]");
			
			try {
				Grade studentGrade;
				
				if (gradesMap.containsKey(uuid)) {
					studentGrade = gradesMap.get(uuid);
				} else {
					studentGrade = new Grade();
					studentGrade.setUuid(UUID.randomUUID().toString());
					Student student = new StudentService(manager).getStudentByUuid(uuid);	
					studentGrade.setStudent(student);
					studentGrade.setSubject(gs.getSubject());
					TypedQuery<Enrollment> enrollmentQuery =
							manager.createQuery("SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course", Enrollment.class);
					enrollmentQuery.setParameter("student", student);
					enrollmentQuery.setParameter("course", gs.getGroup().getCourse());
					Enrollment enrollment = enrollmentQuery.getSingleResult();
					studentGrade.setEnrollment(enrollment);
				}
				studentGrade.setSession1(s1 != null && !s1.isEmpty() ? Float.parseFloat(s1) : null);
				studentGrade.setSession2(s2 != null && !s2.isEmpty() ? Float.parseFloat(s2) : null);
				
				gradeService.saveGrade(studentGrade);
				
				User user = studentGrade.getStudent().getUser();
				System.out.println(user.getEmail());
				mailService.sendEmail(ConstantsUtil.username, user.getEmail(), "Nouvelles notes disponibles",
						"Bonjour " + user.getFirstName() + ", de nouvelles notes sont disponibles sur ton ENT !");
				
			} catch (NumberFormatException e) {
				// transaction.rollback();
				//response.getWriter().write(ServerSideRenderingHelper.getJsonFormattedTable(false, "grades must be numeric !"));
				System.out.println("grades must be numeric " + e.getMessage());
				response.getWriter().write(ServerSideRenderingHelper.getJsonFormattedError("Grades must be numeric"));
				return;
			} catch (Exception e) {
				// transaction.rollback();
				// response.getWriter().write(ServerSideRenderingHelper.getJsonFormattedTable(false, "internal error"));
				System.out.println("internal error : " + e.getMessage());
				response.getWriter().write(ServerSideRenderingHelper.getJsonFormattedError("Internal error"));
				return;
			}
			
			i++;
		}
		
		// transaction.commit();
		response.getWriter().write(ServerSideRenderingHelper.getJsonFormattedTable(true, "update successful"));
		
	}
	
}
