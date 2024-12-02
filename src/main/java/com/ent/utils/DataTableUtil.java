package com.ent.utils;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class DataTableUtil <T> {
	
	private HttpServletRequest request;
	
	public DataTableUtil(HttpServletRequest request) {
		this.request = request;
	}
	
	public List<T> paginate(List<T> list) {
		request.setAttribute("numberOfElements", list.size());
		
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		request.setAttribute("pageSize", pageSize);
		
		Integer numberOfPages = (int) Math.max(Math.ceil((double) list.size() / pageSize), 1);
		request.setAttribute("numberOfPages", numberOfPages);
		
		Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		pageIndex = Math.max(1, Math.min(pageIndex, numberOfPages));
		request.setAttribute("pageIndex", pageIndex);
		
		int startIndex = pageSize * (pageIndex - 1);
        int endIndex = Math.min(startIndex + pageSize, list.size());

        if (startIndex >= list.size()) {
        	return new ArrayList<T>();
        }
        
        List<T> sublist = list.subList(startIndex, endIndex);
        
        return sublist;
	}
	
	// get sublist
	// set number of elements
	

}
