package com.cyansoft.maodou_manger.Service;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class Config {

    static public String BASE_URL="http://192.168.0.13:8080/Hospital_Server_war_exploded";

    static public String REGISTER_URL=BASE_URL+"/RegisterServlet";

    static public String LOGIN_URL=BASE_URL+"/LoginServlet";

    static public String DOCTOR_URL=BASE_URL+"/ChangegoodsServlet";

    static public String SEARCH_URL=BASE_URL+"/SearchServlet";

    static public String COMMENT_URL=BASE_URL+"/ComitmentServlet";

    static public String GETORDER_URL=BASE_URL+"/GetOrderServlet";

    static public String BAR_URL=BASE_URL+"/BarServlet";


}
