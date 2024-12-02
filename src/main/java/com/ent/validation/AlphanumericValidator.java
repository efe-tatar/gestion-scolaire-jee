package com.ent.validation;

public class AlphanumericValidator implements Validator {

	@Override
	public boolean validate(Object attribute, String rule) {
		
		if( !(attribute instanceof String) )
		{
			return false;
		}
		
		String att = (String) attribute;
		
		for(int i = 0 ; i < att.length() ; i++)
			if( ! Character.isDigit(att.charAt(i)) && ! Character.isAlphabetic(att.charAt(i)) )
				return false;
		
		return true;
	}

}
