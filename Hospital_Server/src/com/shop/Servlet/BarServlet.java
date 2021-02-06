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

public class BarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("multipart/form-data; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String flag = (String) job.get("flag");
        String name = (String) job.get("name");
        String introduce = (String) job.get("comment");


        UserInfoDao uinfo = new UserInfoDao();

        if(flag.equals("add"))
        {
            String result=uinfo.addbar(name,introduce);
            PrintWriter out = response.getWriter();
            out.print(result);

        }
        else if(flag.equals("search"))
        {
            String result=uinfo.findbar();
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("update"))
        {
            String result=uinfo.updatabar(name,introduce);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
    }
}