package com.ent.validators.student;

import java.util.HashMap;
import java.util.Map;
import com.ent.validation.RequestValidator;
import jakarta.servlet.http.HttpServletRequest;

public class StudentStoreValidator extends RequestValidator{

    public StudentStoreValidator(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Map<String, String> rules() {
        Map<String, String> rules = new HashMap<>();
        rules.put("surname", "required,min:2,max:25,alpha");
        rules.put("first_name", "required,min:2,max:25,alpha");
        rules.put("date_of_birth", "required,date");
        rules.put("email", "required,email,max:50,unique:User:email");
        rules.put("password", "required,min:5,max:50");
        rules.put("gender", "required");

        return rules;
    }

    
}
