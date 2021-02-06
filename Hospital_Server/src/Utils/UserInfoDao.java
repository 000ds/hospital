package Utils;

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
            String usernameSelect = "";
            String passwordSelect = "";
            while(rs.next()){

                usernameSelect = rs.getString("username");
                passwordSelect = rs.getString("password");


                if(username.equals(usernameSelect)&&password.equals(passwordSelect)){
                    result = "登录成功！";

                }
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
            result="注册成功";

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
    public String doForComitment(String comiment,String goodsname){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into comiments (comiment,kind) VALUES(?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,comiment);
            statement2.setString(2,goodsname);
            statement2.executeUpdate();
            result="评论成功";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    // 表操作
    public String doForComitment2(String goodsname){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into comiment (菠菜) VALUES(?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,goodsname);
            statement2.executeUpdate();
            result="评论成功";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //所有人查询商品信息 表操作
    public String getDoctor(String kind){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        //构建json数组，数组里面也是json
        JSONArray data =new JSONArray();

        try {
            int i=0;
            String sql = "select * from doctors where kind =?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,kind);
            //5.得到结果集
            rs = statement.executeQuery();
            while(rs.next()){
                JSONObject player1 = new JSONObject();
                i++;
                String nameSelect = rs.getString("name");
                String priceSelect = rs.getString("price");
                String introduceSelet = rs.getString("introduce");
                String goodsImgUrlSelet = rs.getString("ImgUrl");
                String rank = rs.getString("rank");
                player1.accumulate("kind",kind);
                player1.accumulate("name",nameSelect);
                player1.accumulate("rank", rank);
                player1.accumulate("price", priceSelect);
                player1.accumulate("introduce", introduceSelet);
                player1.accumulate("ImgUrl",goodsImgUrlSelet);
                data.add(player1);
            }
            result=data.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //所有人查询商品信息 表操作
    public String getuserinfo(String kind){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        //构建json数组，数组里面也是json
        JSONArray data =new JSONArray();

        try {
            int i=0;
            String sql = "select * from userinfo ";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();
            while(rs.next()){
                JSONObject player1 = new JSONObject();
                i++;
                String nameSelect = rs.getString("name");
                String priceSelect = rs.getString("price");
                String introduceSelet = rs.getString("introduce");
                String goodsImgUrlSelet = rs.getString("ImgUrl");
                String rank = rs.getString("rank");
                player1.accumulate("kind",kind);
                player1.accumulate("name",nameSelect);
                player1.accumulate("rank", rank);
                player1.accumulate("price", priceSelect);
                player1.accumulate("introduce", introduceSelet);
                player1.accumulate("ImgUrl",goodsImgUrlSelet);
                data.add(player1);
            }
            result=data.toString();
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
            String sql = "select * from goods where kind =?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            statement.setString(1,"面膜");
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
            goodsitem.accumulate("name","面膜");
            goodscatrgory.accumulate("goodscatrgory",goodsitem);

//            String str = String.valueOf (i);
//            result=str+result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int i=0;
            String sql = "select * from goods where kind =?";
            //4.得到statement对象执行sql
            statement1 = connection1.prepareStatement(sql);
            statement1.setString(1,"护肤品");

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
            goodsitem1.accumulate("name","护肤品");
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
    public String adduserinfo(String username, String password){
        String result="";

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into userinfo (username,password) VALUES(?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,username);
            statement2.setString(2,password);
            statement2.executeUpdate();
            result="添加成功！";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //管理员审核 表操作
    public String addgoods( String kind,String name, String rank,String price,String content,String goodsImgUrl){
        String result="";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql1 = "insert into doctors (kind,name,rank,price,content,ImgUrl) VALUES(?,?,?,?,?,?)";
            PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(sql1);
            statement2.setString(1,kind);
            statement2.setString(2,name);
            statement2.setString(3,rank);
            statement2.setString(4,price);
            statement2.setString(5,content);
            statement2.setString(6,goodsImgUrl);
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



    public void update(){
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "update test set username=?,password=? where id=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,"abc");
            statement.setString(2,"789");
            statement.setInt(3,1);
            statement.executeUpdate();
            System.out.println("修改成功！");
            //6.处理结果集
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,statement,connection);
        }
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
            String sql = "delete from doctors where name=?";
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
    public String deleteuser(String name){
        String result="";
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "delete from userinfo where name=?";
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
    public String updatagoods(String kind,String name, String rank,String price, String introduce,String goodsImgUrl){

        String result="";

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //3.写SQL
            String sql = "update doctors set rank=?,price=?,introduce=?,ImgUrl=?,kind=? where name=?";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            statement.setString(1,rank);
            statement.setString(2,price);
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


                JSONObject player1 = new JSONObject();

                player1.accumulate("username",usernameSelect);
                player1.accumulate("password", passwordSelect);


                userinformation.add(player1);
                String s = userinformation.toString();
                result=s;
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

            String sql = "select * from doctor";
            //4.得到statement对象执行sql
            statement = connection.prepareStatement(sql);
            //5.得到结果集
            rs = statement.executeQuery();
            JSONArray goodsinformation =new JSONArray();
            while(rs.next()){

                String nameSelect = rs.getString("name");
                String priceSelect = rs.getString("price");
                String rankSelect = rs.getString("rank");
                String introduceSelect = rs.getString("introduce");
                String goodsImgUrlSelect = rs.getString("ImgUrl");
                String kindSelect = rs.getString("kind");


                JSONObject player1 = new JSONObject();

                player1.accumulate("name",nameSelect);
                player1.accumulate("price", priceSelect);
                player1.accumulate("rank",rankSelect);
                player1.accumulate("introduce", introduceSelect);
                player1.accumulate("ImgUrl", goodsImgUrlSelect);
                player1.accumulate("kind", kindSelect);

                goodsinformation.add(player1);
                String s = goodsinformation.toString();
                result=s;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

