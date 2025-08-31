package com.darwinruiz.uspglocalgallerylab.controllers;

import com.darwinruiz.uspglocalgallerylab.repositories.LocalFileRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
    private LocalFileRepository repo;

    @Override
    public void init() {
        repo = LocalFileRepository.createDefault();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        List<String> all = repo.listByExtensionsRecursive("imagenes", ".png", ".jpg", ".jpeg", ".gif", ".webp");

        int page = 1, size = 12;
        try {
            String pageParam = req.getParameter("page");
            String sizeParam = req.getParameter("size");
            
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
            
            if (sizeParam != null && !sizeParam.isEmpty()) {
                size = Integer.parseInt(sizeParam);
                if (size < 1) size = 12;
            }
        } catch (NumberFormatException e) {
        }

        int total = all.size();
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, total);
        
        if (fromIndex >= total) {
            fromIndex = Math.max(0, total - size);
            toIndex = total;
            page = fromIndex / size + 1;
        }
        
        List<String> pageItems = all.subList(fromIndex, toIndex);
        
        int totalPages = (int) Math.ceil((double) total / size);
        if (totalPages == 0) totalPages = 1;
        
        req.setAttribute("localImages", pageItems);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/gallery.jsp").forward(req, resp);
    }
}
