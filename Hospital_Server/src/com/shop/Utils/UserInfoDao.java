package com.shop.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDao {
    public static void main(String[] args){
        UserInfoDao userdao = new UserInfoDao();
        List<userInfo> list = userdao.findALL();

    }

    //登录表操作
    public String doForLogin(String username, String password) {
        String result="登陆失败，邮箱或密码错误！";

        if("".equals(password)){
            return "密码不能为空！";
        }
        if("".equals(username)){
            return "账号不能为空！";
        }
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql = "select * from userinfo";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();
            String idSelect = "";
            String usernameSelect = "";
            String passwordSelect = "";
            String resultPower = "";
            String powerSelect = "";
            while(rs.next()){
                idSelect = rs.getString("Id");
                usernameSelect = rs.getString("username");
                passwordSelect = rs.getString("password");

                if(username.equals(usernameSelect)&&password.equals(passwordSelect)){
                    result = "登录成功！";
                    resultPower = powerSelect;
                }
            }
            if("登录成功！".equals(result)){
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    //注册 表操作
    public String doForRegister(String username, String password){
        String result="";

        if("".equals(username)){
            return "用户名不能为空！";
        }

        if("".equals(password)){
            return "密码不能为空！";
        }
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {
            String sql = "select * from userinfo";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                String usernameSelect = rs.getString("username");
                String passwordSelect = rs.getString("password");
                if(username.equals(usernameSelect)){
                    result = "此邮箱已经被注册！";
                }
            }
            if("此账号已经被注册！".equals(result)){
                return result;
            }
            String sql1 = "insert into userinfo (username,password) VALUES(?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,username);
            statement2.setString(2,password);
            statement2.executeUpdate();
            result="注册成功！";

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }
    //订单 表操作
    public String doForOrder(String username, String information,String price, String adress,String time){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {
            String sql1 = "insert into dingdan (username,information,price,adress,time) VALUES(?,?,?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,username);
            statement2.setString(2,information);
            statement2.setString(3,price);
            statement2.setString(4,adress);
            statement2.setString(5,time);
            statement2.executeUpdate();
            result="生成订单成功";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    // 表操作
    public String doForComitment(String name,String doctorname,String comment){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into comments (name,doctorname,comment) VALUES(?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,name);
            statement2.setString(2,doctorname);
            statement2.setString(3,comment);
            statement2.executeUpdate();
            result="评论成功";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //所有人查询商品信息 表操作
    public String Findgoods(){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement statement1 = null;
        ResultSet rs1 = null;
        Connection connection1 = JDBCUtil.getConnection();
        JSONObject jsonObject =new JSONObject();
        JSONObject goodscatrgory =new JSONObject();
        JSONObject goodsitem =new JSONObject();
        JSONObject goodscatrgory1 =new JSONObject();
        JSONObject goodsitem1 =new JSONObject();

        //构建json数组，数组里面也是json
        JSONArray goods =new JSONArray();
        //构建json数组，数组里面也是json
        JSONArray fruits =new JSONArray();
        //构建json数组，数组里面也是json
        JSONArray vegetables =new JSONArray();
        try {
            int i=0;
            String sql = "select * from doctors where kind =?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,"骨科");
            //5.得到结果集
            rs = statement.executeQuery();
            while(rs.next()){
                i++;
                String nameSelect = rs.getString("name");
                String priceSelect = rs.getString("price");
                String priceorderSelet = rs.getString("price_order");
                String introduceSelet = rs.getString("introduce");
                String goodsImgUrlSelet = rs.getString("goodsImgUrl");
                String moreStandardSelet = rs.getString("moreStandard");
                //构建json数组中的对象
                JSONObject player1 = new JSONObject();
                player1.accumulate("name",nameSelect);
                player1.accumulate("price", priceSelect);
                player1.accumulate("price_order",priceorderSelet);
                player1.accumulate("introduce", introduceSelet);
                player1.accumulate("goodsImgUrl",goodsImgUrlSelet);
                player1.accumulate("moreStandard", moreStandardSelet);

                fruits.add(player1);
//                result ="-"+nameSelect+"-"+priceSelect+"-"+priceorderSelet+"-"+introduceSelet+"-"+goodsImgUrlSelet+"-"+moreStandardSelet;
            }
            goodsitem.accumulate("goodsitem",fruits);
            goodsitem.accumulate("name","骨科");
            goodscatrgory.accumulate("goodscatrgory",goodsitem);

//            String str = String.valueOf (i);
//            result=str+result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int i=0;
            String sql = "select * from doctors where kind =?";
            //4.得到statement对象执行sql
            statement1 = connection1.prepareStatement(sql);
            statement1.setString(1,"内科");

            //5.得到结果集
            rs1 = statement1.executeQuery();

            while(rs1.next()){
                i++;
                String nameSelect = rs1.getString("name");
                String priceSelect = rs1.getString("price");
                String priceorderSelet = rs1.getString("price_order");
                String introduceSelet = rs1.getString("introduce");
                String goodsImgUrlSelet = rs1.getString("goodsImgUrl");
                String moreStandardSelet = rs1.getString("moreStandard");

                //构建json数组中的对象
                JSONObject player1 = new JSONObject();

                player1.accumulate("name",nameSelect);
                player1.accumulate("price", priceSelect);
                player1.accumulate("price_order",priceorderSelet);
                player1.accumulate("introduce", introduceSelet);
                player1.accumulate("goodsImgUrl",goodsImgUrlSelet);
                player1.accumulate("moreStandard", moreStandardSelet);

                vegetables.add(player1);
            }
            goodsitem1.accumulate("goodsitem",vegetables);
            goodsitem1.accumulate("name","内科");
            goodscatrgory1.accumulate("goodscatrgory",goodsitem1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        goods.add(goodscatrgory);
        goods.add(goodscatrgory1);

        jsonObject.accumulate("data",goods);
        String s = jsonObject.toString();
        result=s;
        return result;
    }
    //管理员审核 表操作
    public String adduserinfo(String username, String password,String part, String power){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into userinfo (username,password,part,power) VALUES(?,?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,username);
            statement2.setString(2,password);
            statement2.setString(3,part);
            statement2.setString(4,power);
            statement2.executeUpdate();
            result="添加成功！";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //管理员审核 表操作
    public String addgoods( String name, String zhicheng,String classes,String special,String phone,String resume,String money,String photo){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into doctor (name,zhicheng,class,special,phone,resume,money,photo) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,name);
            statement2.setString(2,zhicheng);
            statement2.setString(3,classes);
            statement2.setString(4,special);
            statement2.setString(5,phone);
            statement2.setString(6,resume);
            statement2.setString(7,money);
            statement2.setString(8,photo);
            statement2.executeUpdate();
            result="添加成功！";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //管理员审核 表操作
    public String addbar( String name, String introduce){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into bar (name,comment) VALUES(?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,name);
            statement2.setString(2,introduce);
            statement2.executeUpdate();
            result="添加成功！";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //管理员审核 表操作
    public String adddingdan( String name,String doctorname,String time,String money,String cla,String url){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into dingdan_order (name,doctorname,time,money,cla,url) VALUES(?,?,?,?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,name);
            statement2.setString(2,doctorname);
            statement2.setString(3,time);
            statement2.setString(4,money);
            statement2.setString(5,cla);
            statement2.setString(6,url);
            statement2.executeUpdate();
            result="添加成功！";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String Findziliao() {
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {
            int i=0;
            String sql = "select * from ziliao";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                i++;
                String usernameSelect = rs.getString("subject");
                String passwordSelect = rs.getString("introduce");
                String flagSelet = rs.getString("url");
                result = result+"-"+usernameSelect+"-"+passwordSelect+"-"+flagSelet;
            }
            String str = String.valueOf (i);
            result=str+result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String doForadd(String subject, String introduce, String url) {
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql = "insert into ziliao (subject,introduce,url) VALUES(?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql);
            statement2.setString(1, subject);
            statement2.setString(2, introduce);
            statement2.setString(3, url);
            statement2.executeUpdate();
            result="添加成功！";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //管理员查询申请信息 表操作
    public String FindApplication(){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {
            int i=0;
            String sql = "select * from Application";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                i++;
                String usernameSelect = rs.getString("username");
                String passwordSelect = rs.getString("password");
                String flagSelet = rs.getString("flag");
                String partSelet = rs.getString("part");

                result = result+"-"+usernameSelect+"-"+passwordSelect+"-"+flagSelet+"-"+partSelet;
            }
            String str = String.valueOf (i);
            result=str+result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //管理员查询申请信息 表操作
    public String GetorderServlet(){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {
            int i=0;
            String sql = "select * from dingdan";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                i++;
                String usernameSelect = rs.getString("username");
                String passwordSelect = rs.getString("information");
                String flagSelet = rs.getString("price");
                String partSelet = rs.getString("adress");
                String timeSelet = rs.getString("time");

                result = result+"-"+usernameSelect+"-"+passwordSelect+"-"+flagSelet+"-"+partSelet+"-"+timeSelet;
            }
            String str = String.valueOf (i);
            result=str+result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String FindComiment(String goodsname){
        String result=" ";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {
            int i=0;
            String sql = "select * from comiments where kind=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,goodsname);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                i++;
                String usernameSelect = rs.getString("comiment");

                result = result+"-"+usernameSelect;
            }
            result = i+result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<userInfo> findALL(){
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        List<userInfo> list = new ArrayList<>();
        try {
            String sql = "select * from application";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            //6.处理结果集
            while (rs.next()) {
                userInfo uinfo = new userInfo();
                uinfo.setId(rs.getInt(1));
                uinfo.setUsername(rs.getString(2));
                uinfo.setPassword(rs.getString(3));
                // System.out.println(uinfo);
                list.add(uinfo);
            }
            //7.关闭资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null,statement,connection);
        }
        return list;
    }


    public String updatabar(String username, String comment){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "update bar set comment=? where name=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,comment);
            statement.setString(2,username);
            statement.executeUpdate();
            result="修改成功！";
            System.out.println("修改成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
        return result;
    }
    public String updatauser(String username, String password){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "update userinfo set password=? where username=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,password);
            statement.setString(2,username);
            statement.executeUpdate();
            result="修改成功！";
            System.out.println("修改成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
        return result;
    }
    public String delete(String username){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "delete from userinfo where username=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,username);
            statement.executeUpdate();
            result="删除成功！";
            System.out.println("删除成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
        return result;
    }
    public String deletegoods(String name){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "delete from goods where name=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,name);
            statement.executeUpdate();
            result="删除成功！";
            System.out.println("删除成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
        return result;
    }
    public String updata(String username, String password,String flag, String part){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "update userinfo set password=?,part=?,power=? where username=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,password);
            statement.setString(2,part);
            statement.setString(3,flag);
            statement.setString(4,username);
            statement.executeUpdate();
            result="修改成功！";
            System.out.println("修改成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
        return result;
    }
    public String updatagoods(String name, String price,String price_order, String introduce,String goodsImgUrl,String kind){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "update goods set price=?,price_order=?,introduce=?,goodsImgUrl=?,kind=? where name=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,price);
            statement.setString(2,price_order);
            statement.setString(3,introduce);
            statement.setString(4,goodsImgUrl);
            statement.setString(5,kind);
            statement.setString(6,name);
            statement.executeUpdate();
            result="修改成功！";
            System.out.println("修改成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
        return result;
    }
    public String finduserinfo(){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        JSONArray userinformation =new JSONArray();
        try {

            String sql = "select * from userinfo ";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                String usernameSelect = rs.getString("username");
                String passwordSelect = rs.getString("password");
                String partSelect = rs.getString("part");
                String powerSelect = rs.getString("power");

                JSONObject player1 = new JSONObject();

                player1.accumulate("username",usernameSelect);
                player1.accumulate("password", passwordSelect);
                player1.accumulate("part",partSelect);
                player1.accumulate("power", powerSelect);

                userinformation.add(player1);
                String s = userinformation.toString();
                result=s;
//                result =usernameSelect+"-"+passwordSelect+"-"+partSelect+"-"+powerSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String findbar(){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        JSONArray userinformation =new JSONArray();
        try {

            String sql = "select * from bar ";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                String usernameSelect = rs.getString("name");
                String passwordSelect = rs.getString("comment");

                JSONObject player1 = new JSONObject();

                player1.accumulate("name",usernameSelect);
                player1.accumulate("comment", passwordSelect);

                userinformation.add(player1);
                String s = userinformation.toString();
                result=s;
//                result =usernameSelect+"-"+passwordSelect+"-"+partSelect+"-"+powerSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String findcomment(String doctorname){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        JSONArray userinformation =new JSONArray();
        try {

            String sql = "select * from comments where doctorname = ?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,doctorname);
            //5.得到结果集
            rs = statement.executeQuery();

            while(rs.next()){
                String usernameSelect = rs.getString("name");
                String passwordSelect = rs.getString("comment");

                JSONObject player1 = new JSONObject();

                player1.accumulate("name",usernameSelect);
                player1.accumulate("comment", passwordSelect);

                userinformation.add(player1);
                String s = userinformation.toString();
                result=s;
//                result =usernameSelect+"-"+passwordSelect+"-"+partSelect+"-"+powerSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String findgood(){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {

            String sql = "select * from goods";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();
            JSONArray goodsinformation =new JSONArray();
            while(rs.next()){

                String nameSelect = rs.getString("name");
                String priceSelect = rs.getString("price");
                String price_orderSelect = rs.getString("price_order");
                String introduceSelect = rs.getString("introduce");
                String goodsImgUrlSelect = rs.getString("goodsImgUrl");
                String kindSelect = rs.getString("kind");


                JSONObject player1 = new JSONObject();

                player1.accumulate("name",nameSelect);
                player1.accumulate("price", priceSelect);
                player1.accumulate("price_order",price_orderSelect);
                player1.accumulate("introduce", introduceSelect);
                player1.accumulate("goodsImgUrl", goodsImgUrlSelect);
                player1.accumulate("kind", kindSelect);

                goodsinformation.add(player1);
                String s = goodsinformation.toString();
                result=s;
                //result =nameSelect+"-"+priceSelect+"-"+price_orderSelect+"-"+introduceSelect+"-"+goodsImgUrlSelect+"-"+kindSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String find_Doctor(){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {

            String sql = "select * from doctor";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();
            JSONArray goodsinformation =new JSONArray();
            while(rs.next()){

                String nameSelect = rs.getString("name");
                String classSelect = rs.getString("class");
                String phoneSelect = rs.getString("phone");
                String zhichengelect = rs.getString("zhicheng");
                String resumeSelect = rs.getString("resume");
                String infoSelect = rs.getString("info");
                String specialSelect = rs.getString("special");
                String photoSelect = rs.getString("photo");
                String moneySelect = rs.getString("money");
                String stateSelect = rs.getString("state");
                String totalUrlSelect = rs.getString("total");
                JSONObject player1 = new JSONObject();
                player1.accumulate("name",nameSelect);
                player1.accumulate("classes", classSelect);
                player1.accumulate("phone",phoneSelect);
                player1.accumulate("zhicheng", zhichengelect);
                player1.accumulate("resume", resumeSelect);
                player1.accumulate("info", infoSelect);
                player1.accumulate("special",specialSelect);
                player1.accumulate("photo", photoSelect);
                player1.accumulate("money",moneySelect);
                player1.accumulate("state", stateSelect);
                player1.accumulate("total", totalUrlSelect);


                goodsinformation.add(player1);
                String s = goodsinformation.toString();
                result=s;
                //result =nameSelect+"-"+priceSelect+"-"+price_orderSelect+"-"+introduceSelect+"-"+goodsImgUrlSelect+"-"+kindSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String find_order(String name){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {

            String sql = "select * from dingdan_order where name = ?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            //5.得到结果集
            rs = statement.executeQuery();
            JSONArray goodsinformation =new JSONArray();
            while(rs.next()){

                String nameSelect = rs.getString("name");
                String doctorname = rs.getString("doctorname");
                String time = rs.getString("time");
                String money = rs.getString("money");
                String cla = rs.getString("cla");
                String url = rs.getString("url");
                JSONObject player1 = new JSONObject();
                player1.accumulate("name",nameSelect);
                player1.accumulate("doctorname", doctorname);
                player1.accumulate("time",time);
                player1.accumulate("cla", cla);
                player1.accumulate("money", money);
                player1.accumulate("url", url);
                goodsinformation.add(player1);
                String s = goodsinformation.toString();
                result=s;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String find_Doctor_name(String content){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {

            String sql = "select * from doctor where name=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,content);
            //5.得到结果集
            rs = statement.executeQuery();
            JSONArray goodsinformation =new JSONArray();
            while(rs.next()){

                String nameSelect = rs.getString("name");
                String classSelect = rs.getString("class");
                String phoneSelect = rs.getString("phone");
                String zhichengelect = rs.getString("zhicheng");
                String resumeSelect = rs.getString("resume");
                String infoSelect = rs.getString("info");
                String specialSelect = rs.getString("special");
                String photoSelect = rs.getString("photo");
                String moneySelect = rs.getString("money");
                String stateSelect = rs.getString("state");
                String totalUrlSelect = rs.getString("total");
                JSONObject player1 = new JSONObject();
                player1.accumulate("name",nameSelect);
                player1.accumulate("classes", classSelect);
                player1.accumulate("phone",phoneSelect);
                player1.accumulate("zhicheng", zhichengelect);
                player1.accumulate("resume", resumeSelect);
                player1.accumulate("info", infoSelect);
                player1.accumulate("special",specialSelect);
                player1.accumulate("photo", photoSelect);
                player1.accumulate("money",moneySelect);
                player1.accumulate("state", stateSelect);
                player1.accumulate("total", totalUrlSelect);


                goodsinformation.add(player1);
                String s = goodsinformation.toString();
                result=s;
                //result =nameSelect+"-"+priceSelect+"-"+price_orderSelect+"-"+introduceSelect+"-"+goodsImgUrlSelect+"-"+kindSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String find_Doctor_class(String content){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();

        try {

            String sql = "select * from doctor where class=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,content);
            //5.得到结果集
            rs = statement.executeQuery();
            JSONArray goodsinformation =new JSONArray();
            while(rs.next()){

                String nameSelect = rs.getString("name");
                String classSelect = rs.getString("class");
                String phoneSelect = rs.getString("phone");
                String zhichengelect = rs.getString("zhicheng");
                String resumeSelect = rs.getString("resume");
                String infoSelect = rs.getString("info");
                String specialSelect = rs.getString("special");
                String photoSelect = rs.getString("photo");
                String moneySelect = rs.getString("money");
                String stateSelect = rs.getString("state");
                String totalUrlSelect = rs.getString("total");
                JSONObject player1 = new JSONObject();
                player1.accumulate("name",nameSelect);
                player1.accumulate("classes", classSelect);
                player1.accumulate("phone",phoneSelect);
                player1.accumulate("zhicheng", zhichengelect);
                player1.accumulate("resume", resumeSelect);
                player1.accumulate("info", infoSelect);
                player1.accumulate("special",specialSelect);
                player1.accumulate("photo", photoSelect);
                player1.accumulate("money",moneySelect);
                player1.accumulate("state", stateSelect);
                player1.accumulate("total", totalUrlSelect);


                goodsinformation.add(player1);
                String s = goodsinformation.toString();
                result=s;
                //result =nameSelect+"-"+priceSelect+"-"+price_orderSelect+"-"+introduceSelect+"-"+goodsImgUrlSelect+"-"+kindSelect;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

