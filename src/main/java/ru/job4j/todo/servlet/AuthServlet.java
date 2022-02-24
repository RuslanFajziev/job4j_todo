package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        var dbStore = DbStore.instOf();
        var rsl = dbStore.findUser(email);
        if (rsl != null && rsl.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", rsl);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        req.setAttribute("error", "Не верный email или пароль");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        var dbStore = DbStore.instOf();
        var rsl = dbStore.findUser(email);
        if (rsl != null) {
            req.setAttribute("error", "Данный email уже зарегистрирован");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        User user = new User(email, password);
        dbStore.addUser(user);
        HttpSession sc = req.getSession();
        sc.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}