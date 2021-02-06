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

public class ChangegoodsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String flag=(String) job.get("flag");
        String name = (String) job.get("name");
        String zhicheng = (String) job.get("zhicheng");
        String classes = (String) job.get("classes");
        String special = (String) job.get("special");
        String phone = (String) job.get("phone");
        String resume = (String) job.get("resume");

        String money = (String) job.get("money");
        String photo = (String) job.get("photo");
        UserInfoDao uinfo = new UserInfoDao();
        if(flag.equals("add"))
        {
            String result=uinfo.addgoods(name,zhicheng,classes,special,phone,resume,money,photo);
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
            String result= uinfo.find_Doctor();
            PrintWriter out = response.getWriter();
            out.print(result);
        }
    }
}

