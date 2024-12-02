package com.ent.validation;

import java.util.regex.Pattern;

public class EmailValidator implements Validator{
	
	private static final String emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";

	@Override
	public boolean validate(Object attribute, String rule) {

		if(attribute == null) return true;
		
		return Pattern.compile(emailRegex).matcher((String)attribute).matches();
	}
	
	

}
