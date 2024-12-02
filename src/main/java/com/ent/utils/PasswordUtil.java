package com.ent.utils;

import java.util.Random;

public class PasswordUtil {
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public static String generatePassword() {
		
		Random random = new Random();
        StringBuilder password = new StringBuilder(10);
        
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        
        return password.toString();
	}

}
