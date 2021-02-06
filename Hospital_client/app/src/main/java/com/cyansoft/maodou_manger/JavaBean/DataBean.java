package com.cyansoft.maodou_manger.JavaBean;

/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class DataBean {
    private String name;
    private  String s_name;
    private  String mprice;
    private   String mAddress;
    private  String mPhone;
    public  DataBean(String s_name,String mprice,String mAddress,String mPhone){
        this.s_name = s_name;
        this.mprice = mprice;
        this.mAddress = mAddress;
        this.mPhone = mPhone;

    }
    public  DataBean(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}

