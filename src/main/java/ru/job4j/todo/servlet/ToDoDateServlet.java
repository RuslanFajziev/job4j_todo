package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToDoDateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore db = DbStore.instOf();
        String description = req.getParameter("description");
        Item item = new Item();
        item.setDescription(description);
        db.add(item);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore db = DbStore.instOf();
        String description = req.getParameter("description");
        int id = Integer.valueOf(req.getParameter("id"));
        Item item = new Item(id, description, true);
        db.replace(item);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}