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

public class ChangeuserinfoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String flag=(String) job.get("flag");
        String username = (String) job.get("username");
        String password = (String) job.get("password");
        String power = (String) job.get("power");
        String part = (String) job.get("part");
        UserInfoDao uinfo = new UserInfoDao();

        if(flag.equals("add"))
        {
            String result=uinfo.adduserinfo(username,password,part,power);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("delete"))
        {
            String result= uinfo.delete(username);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("update"))
        {
            String result= uinfo.updata(username,password,part,power);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("search"))
        {
            String result= uinfo.finduserinfo();
            PrintWriter out = response.getWriter();
            out.print(result);
        }
    }
}


