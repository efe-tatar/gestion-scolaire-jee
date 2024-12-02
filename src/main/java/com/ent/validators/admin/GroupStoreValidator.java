package com.ent.validators.admin;

import java.util.HashMap;
import java.util.Map;

import com.ent.validation.RequestValidator;

import jakarta.servlet.http.HttpServletRequest;

public class GroupStoreValidator extends RequestValidator {

	public GroupStoreValidator(HttpServletRequest request) {
		super(request);	
	}

	@Override
	public Map<String, String> rules() {
		
		Map<String, String> rules = new HashMap<>();
		rules.put("name", "required,min:5,max:50,unique:Group:name");
		rules.put("course", "required,exists:Course:uuid");
		
		return rules;
	}

}
