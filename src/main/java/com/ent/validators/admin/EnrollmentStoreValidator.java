package com.ent.validators.admin;

import java.util.HashMap;
import java.util.Map;

import com.ent.validation.RequestValidator;

import jakarta.servlet.http.HttpServletRequest;

public class EnrollmentStoreValidator extends RequestValidator {

	public EnrollmentStoreValidator(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Map<String, String> rules() {
		
		Map<String, String> rules = new HashMap<>();
		rules.put("course", "required,exists:Course:uuid");
		rules.put("studentId", "required,exists:Student:uuid");
		rules.put("startDate", "required,date");
		rules.put("endDate", "required,date");
		
		return rules;
	}
	
	

}
