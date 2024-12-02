package com.ent.validators.admin;

import java.util.Map;
import java.util.HashMap;
import com.ent.validation.RequestValidator;
import jakarta.servlet.http.HttpServletRequest;

public class CourseStoreValidator extends RequestValidator{

	public CourseStoreValidator(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Map<String, String> rules() {

		Map<String, String> rules = new HashMap<>();
		rules.put("name", "required,min:5,max:50,unique:Course:name");
		
		return rules;
	}
	
	

}
