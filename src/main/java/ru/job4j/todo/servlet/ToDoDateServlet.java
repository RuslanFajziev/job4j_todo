package ru.job4j.todo.servlet;

import ru.job4j.todo.model.CategoryItem;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class ToDoDateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore db = DbStore.instOf();
        String description = req.getParameter("description");
        String[] ids = req.getParameterValues("categoryItem");
        HttpSession sc = req.getSession();
        User user = (User) sc.getAttribute("user");
        Item item = Item.of(description, user);

        db.add(item, ids);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore db = DbStore.instOf();
        String description = req.getParameter("description");
        int id = Integer.valueOf(req.getParameter("id"));
        HttpSession sc = req.getSession();
        User user = (User) sc.getAttribute("user");
        Item item = Item.of(description, user);
        item.setId(id);
        Set<CategoryItem> categoryItems = db.findItem(id).getCategoryItems();
        categoryItems.forEach(item::addCategory);
        item.setDone(true);
        db.replace(item);
    }
}