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

public class GetOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("multipart/form-data; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String flag=(String) job.get("flag");
        String name = (String) job.get("name");
        String doctorname = (String) job.get("doctorname");
        String time = (String) job.get("time");
        String money = (String) job.get("money");
        String cla = (String) job.get("cla");
        String url = (String) job.get("url");
        UserInfoDao uinfo = new UserInfoDao();
        if(flag.equals("add"))
        {
            String result=uinfo.adddingdan(name,doctorname,time,money,cla,url);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("delete"))
        {
            String result= uinfo.deletegoods(name);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        else if(flag.equals("update"))
        {
//            String result= uinfo.updatagoods(name,price,price_order,introduce,goodsImgUrl,kind);
//            PrintWriter out = response.getWriter();
//            out.print(result);
        }
        else if(flag.equals("search"))
        {
            String result= uinfo.find_order(name);
            PrintWriter out = response.getWriter();
            out.print(result);
        }
    }

}
