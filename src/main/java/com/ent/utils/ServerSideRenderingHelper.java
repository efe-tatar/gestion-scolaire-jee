package com.ent.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.ent.entities.GroupSubject;

import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class ServerSideRenderingHelper {
	
	public static HttpServletResponseWrapper getResponseWrapper(HttpServletResponse response) {
		
		return new HttpServletResponseWrapper(response)
		{	
			private final StringWriter stringWriter = new StringWriter();

            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(stringWriter);
            }

            @Override
            public String toString() {
                return stringWriter.toString();
            }
		};
		
	}
	
	public static String getJsonFormattedTable(boolean success, String table) {
		
		// escape to validate json
		String escapedTable = table.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
        String jsonResponse = "{\"success\": " + success + ", \"table\": \"" + escapedTable + "\"}";
        
        return jsonResponse;
	}
	
	public static String getJsonFormattedError(Map<String, String> errorMessage) {
		
		String errors = "";
		
		for (Map.Entry<String, String> entry : errorMessage.entrySet()) {
		    errors += entry.getKey() + ": " + entry.getValue() + "\n";
		}
		
		String escapedTable = errors.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
        String jsonResponse = "{\"success\": " + false + ", \"errors\": \"" + escapedTable + "\"}";
        
        return jsonResponse;
	}
	
	public static String getJsonFormattedError(String error) {
		
		String escapedTable = error.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
        String jsonResponse = "{\"success\": " + false + ", \"errors\": \"" + escapedTable + "\"}";
        
        return jsonResponse;
	}
	
	public static String MapToStringErrors(Map<String, String> errorMessage) {
		String errors = "";
		
		for (Map.Entry<String, String> entry : errorMessage.entrySet()) {
		    errors += entry.getKey() + ": " + entry.getValue() + "\n";
		}
		
		return errors;
	}

}
