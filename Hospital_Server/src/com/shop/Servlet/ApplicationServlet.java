package com.shop.Servlet;

import com.shop.Utils.OtherUtils;
import com.shop.Utils.UserInfoDao;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApplicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("multipart/form-data; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String username = (String) job.get("username");
        String password = (String) job.get("password");
        String power = (String) job.get("power");
        String part = (String) job.get("part");
        UserInfoDao uinfo = new UserInfoDao();

        String result=uinfo.adduserinfo(username,password,part,power);
        uinfo.delete(username);

        PrintWriter out = response.getWriter();
        out.print(result);
    }
}
