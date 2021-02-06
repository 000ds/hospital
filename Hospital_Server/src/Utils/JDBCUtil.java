package Utils;

import java.sql.*;

public class JDBCUtil {
    private static ResultSet rs = null;
    private static PreparedStatement statement = null;
    private static Connection connection = null;

    private JDBCUtil(){}
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/math";
        String user = "math";
        String psd = "123456";
        try {
            Class.forName(driver);   //加载jdbc驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,psd);  //获取数据库连接

        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(ResultSet rs, Statement stat, Connection conn){
        try {
            if(rs != null) rs.close();
            if(stat != null) stat.close();
            if(conn != null) conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}