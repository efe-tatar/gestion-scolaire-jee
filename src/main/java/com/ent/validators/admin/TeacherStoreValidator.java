package com.ent.validators.admin;

import java.util.HashMap;
import java.util.Map;

import com.ent.validation.RequestValidator;

import jakarta.servlet.http.HttpServletRequest;

public class TeacherStoreValidator extends RequestValidator{

	public TeacherStoreValidator(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Map<String, String> rules() {
		
		Map<String, String> rules = new HashMap<>();
		rules.put("surname", "required,min:1,max:50,alpha");
		rules.put("name", "required,min:1,max:50,alpha");
		rules.put("dateOfBirth", "required,date");
		rules.put("email", "required,email,unique:User:email");
		// rules.put("password", "required,min:5,max:20");
		rules.put("gender", "required,alpha");
		
		return rules;
	}

}
