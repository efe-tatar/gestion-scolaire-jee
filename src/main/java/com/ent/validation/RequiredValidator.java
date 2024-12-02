package com.ent.validation;

public class RequiredValidator implements Validator {
	
	@Override
	public boolean validate(Object attribute, String rule)
	{
		return attribute != null;
	}

}
