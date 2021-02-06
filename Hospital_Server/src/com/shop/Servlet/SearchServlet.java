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

public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String flag=(String) job.get("flag");
        String content = (String) job.get("content");
        UserInfoDao uinfo = new UserInfoDao();
        if(flag.equals("医生姓名"))
        {
            String result=uinfo.find_Doctor_name(content);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("科室搜索"))
        {
            String result= uinfo.find_Doctor_class(content);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
    }
}

