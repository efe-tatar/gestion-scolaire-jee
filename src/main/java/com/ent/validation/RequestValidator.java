package com.ent.validation;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public abstract class RequestValidator {
	
	private final HttpServletRequest request;
	
	private static final  Map<String, Validator> validatorMap;
	
	public RequestValidator(HttpServletRequest request)
	{
		this.request = request;
	}

	static {
		validatorMap = new HashMap<>();
		
		validatorMap.put("required", new RequiredValidator());
		validatorMap.put("min", new MinLengthValidator());
		validatorMap.put("numeric", new NumericValidator());
		validatorMap.put("alpha", new AlphabeticValidator());
		validatorMap.put("alphanum", new AlphanumericValidator());
		validatorMap.put("max", new MaxLengthValidator());
		validatorMap.put("unique", new UniqueValidator());
		validatorMap.put("exists", new ResourceExistsValidator());
		validatorMap.put("date", new DateValidator());
		validatorMap.put("email", new EmailValidator());
	}
	
	/*
	 * 
	 */
	public abstract Map<String, String> rules();
	
	/*
	 * 
	 */
	public boolean validate()
	{
		Map<String, String> rules = this.rules();
		boolean allAttributesValid = true;
		
		for(Map.Entry<String, String> entry : rules.entrySet())
		{
			String[] constraints = entry.getValue().split(",");
			String attributeName = entry.getKey();
			
			for(String constraint : constraints)
			{
				String attribute = request.getParameter(attributeName);
				String validatorName = constraint.split(":")[0];

				boolean attributeValid = validatorMap.get(validatorName).validate(attribute, constraint);
				
				if(attributeValid) {
					continue;
				}
				
				allAttributesValid = false;
				
				Map<String, String> errorMessages = getErrorMessages();
				
				String message = "";
				if(errorMessages.containsKey(attributeName)) 
					message += errorMessages.get(attributeName);
				message += "does not fit the following constraint: " + constraint;
				
				errorMessages.put(attributeName, message);
			}
		}
		
		return allAttributesValid;
	}
	
	private Map<String, String> getErrorMessages()
	{
		Object map = request.getAttribute("errorMessages");
		
		if(map == null || !(map instanceof Map<?, ?>))
		{
			map = new HashMap<String, String>();
			request.setAttribute("errorMessages", map);
		}
		
		return (Map<String, String>) map;
	}
	
	
}
