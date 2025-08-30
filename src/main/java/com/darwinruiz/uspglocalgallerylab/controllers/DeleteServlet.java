package com.darwinruiz.uspglocalgallerylab.controllers;

import com.darwinruiz.uspglocalgallerylab.repositories.LocalFileRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private LocalFileRepository repo;

    @Override
    public void init() {
        repo = LocalFileRepository.createDefault();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rel = req.getParameter("path");
        if (rel == null || rel.isBlank() || rel.contains("..")) {
            resp.sendError(400, "path inválido");
            return;
        }
        
        try {
            // Delete the file
            repo.delete(rel);
            
            // Get current page and size parameters for redirection
            String page = req.getParameter("page");
            String size = req.getParameter("size");
            
            // Build the redirect URL with the same pagination parameters
            String redirectUrl = req.getContextPath() + "/list";
            if (page != null || size != null) {
                redirectUrl += "?";
                if (page != null) {
                    redirectUrl += "page=" + page;
                }
                if (size != null) {
                    if (page != null) {
                        redirectUrl += "&";
                    }
                    redirectUrl += "size=" + size;
                }
            }
            
            // Redirect back to the list with the same pagination
            resp.sendRedirect(redirectUrl);
            
        } catch (Exception e) {
            // If deletion fails, redirect to list with error parameter
            resp.sendRedirect(req.getContextPath() + "/list?error=delete_failed");
        }
    }
}