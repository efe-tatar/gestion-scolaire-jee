package com.ent.validation;

import java.text.SimpleDateFormat;

public class DateValidator implements Validator{

	@Override
	public boolean validate(Object attribute, String rule) {
		
		if(attribute == null) return true;
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.parse((String) attribute);
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
	}

}
