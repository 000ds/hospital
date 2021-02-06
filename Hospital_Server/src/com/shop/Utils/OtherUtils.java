package com.shop.Utils;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class OtherUtils {

    //验证邮箱
    public static boolean testRegex(String str){

        //包括下划线，相当于"[A-Za-z0-9_]"
        String pattern = "^\\w+@(\\w+\\.){1,2}\\w+$";

        return  Pattern.matches(pattern, str);
    }


    //数字
    public static final String REG_NUMBER = ".*\\d+.*";
    //小写字母
    public static final String REG_UPPERCASE = ".*[A-Z]+.*";
    //大写字母
    public static final String REG_LOWERCASE = ".*[a-z]+.*";
    //特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
    public static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    //验证密码强度
    public static boolean checkPasswordRule(String password, String username){

        //密码为空及长度大于8位小于16位判断
        if (password == null || password.length() <8 || password.length()>16) return false;

        int i = 0;

        if (password.matches(REG_NUMBER)) i++;
        if (password.matches(REG_LOWERCASE))i++;
        if (password.matches(REG_UPPERCASE)) i++;
        if (password.matches(REG_SYMBOL)) i++;

        boolean contains = password.contains(username);

        if (i  < 2 || contains)  return false;

        return true;
    }

    //通过请求数据获取json
    public static JSONObject getJson(HttpServletRequest request) {
        JSONObject job=new JSONObject();
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder respomseStrBuilder = new StringBuilder();
            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null) {
                respomseStrBuilder.append(inputStr);
            }
            String pram = respomseStrBuilder.toString();

            job = JSONObject.fromObject(pram);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return job;
    }


}
