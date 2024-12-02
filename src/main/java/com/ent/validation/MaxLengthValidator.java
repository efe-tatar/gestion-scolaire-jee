package com.ent.validation;

public class MaxLengthValidator implements Validator {
	
	@Override
	public boolean validate(Object attribute, String rule)
	{
		//if( attribute == null || !(attribute instanceof String) ) return false;
		
		if(attribute == null) return true;
		
		try {
			String[] rules = rule.split(":");		
			int len = Integer.parseInt(rules[1]);
			return ((String)attribute).length() <= len;
		}
		catch(Exception e) {
			System.err.print(e);
			return false;
		}
	}

}