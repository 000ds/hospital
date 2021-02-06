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

public class DownloadServlet extends HttpServlet {
    public DownloadServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("multipart/form-data; charset=utf-8");
        JSONObject job = OtherUtils.getJson(request);
        String id = (String)job.get("id");
        UserInfoDao uinfo = new UserInfoDao();
        String result = uinfo.Findziliao();
        PrintWriter out = response.getWriter();
        out.print(result);
    }
}