package com.shop.Servlet;

import com.shop.Utils.UserInfoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Web_LoginServlet")
public class Web_LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserInfoDao uinfo = new UserInfoDao();
        String result=uinfo.doForLogin(username,password);
        if(result != null){
            request.getRequestDispatcher("success.jsp").forward(request,response);
        }
        else {
            request.getRequestDispatcher("defeat.jsp").forward(request,response);
        }
    }
}