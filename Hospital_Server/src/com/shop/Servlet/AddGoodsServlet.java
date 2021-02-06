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

public class AddGoodsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("multipart/form-data; charset=utf-8");

        JSONObject job= OtherUtils.getJson(request);
        String flag = (String) job.get("flag");
        String name = (String) job.get("name");
        String introduce = (String) job.get("introduce");
        String price = (String) job.get("price");
        String price_order = (String) job.get("price_order");
        String goodsImgUrl = (String) job.get("goodsImgUrl");

        UserInfoDao uinfo = new UserInfoDao();

//            String result=uinfo.addgoods(name,price,price_order,introduce,goodsImgUrl,flag);
//            PrintWriter out = response.getWriter();
//            out.print(result);

    }
}