package com.ent.resources;

import com.ent.validators.student.StudentStoreValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentController extends Resource {

    @Override
    public void index(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void store(HttpServletRequest request, HttpServletResponse response) {

        boolean valid = new StudentStoreValidator(request).validate();
        if(! valid) return; // return with errors

        try {
            response.getWriter().append("student controller store");
        } catch (Exception e) {
            
        }

    }
    
}
