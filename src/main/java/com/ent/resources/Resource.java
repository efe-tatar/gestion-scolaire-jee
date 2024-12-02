package com.ent.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Resource {
    
    public void index(HttpServletRequest request, HttpServletResponse response) {}

    public void create(HttpServletRequest request, HttpServletResponse response) {}

    public void store(HttpServletRequest request, HttpServletResponse response) {}

    public void show(HttpServletRequest request, HttpServletResponse response) {}

    public void edit(HttpServletRequest request, HttpServletResponse response) {}

    public void update(HttpServletRequest request, HttpServletResponse response) {}

    public void destroy(HttpServletRequest request, HttpServletResponse response) {}

}
