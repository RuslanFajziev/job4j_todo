package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.DbStore;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToDoServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> items;
        Boolean allFlag = req.getParameter("flag").equals("true");

        if (allFlag) {
            items = DbStore.instOf().findAll();
        } else {
            items = DbStore.instOf().findAllNotCompleted();
        }

        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(items);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore db = DbStore.instOf();
        Boolean updateFlag = req.getParameter("updateFlag").equals("true");
        String description = req.getParameter("description");

        if (updateFlag) {
            int id = Integer.valueOf(req.getParameter("id"));
            Item item = new Item(id, description, updateFlag);
            db.replace(item);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        } else {
            Item item = new Item();
            item.setDescription(description);
            db.add(item);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
    }
}