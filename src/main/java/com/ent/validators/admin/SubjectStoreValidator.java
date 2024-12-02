package com.ent.validators.admin;

import java.util.HashMap;
import java.util.Map;

import com.ent.validation.RequestValidator;

import jakarta.servlet.http.HttpServletRequest;

public class SubjectStoreValidator extends RequestValidator{

	public SubjectStoreValidator(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Map<String, String> rules() {

		Map<String, String> rules = new HashMap<>();
		rules.put("name", "required,min:5,max:50,unique:Subject:name");
		rules.put("course_id", "required,exists:Course:uuid");
		
		return rules;
	}


}
