package com.shop.Servlet;

import com.shop.Utils.OtherUtils;
import com.shop.Utils.UserInfoDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FindApplicationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("multipart/form-data; charset=utf-8");
        JSONObject job= OtherUtils.getJson(request);

        UserInfoDao uinfo = new UserInfoDao();
        String result=uinfo.FindApplication();
        PrintWriter out = response.getWriter();
        out.print(result);
    }

}