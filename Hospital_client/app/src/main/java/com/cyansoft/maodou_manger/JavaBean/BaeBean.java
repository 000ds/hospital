package com.cyansoft.maodou_manger.JavaBean;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class BaeBean {

    private String name;
    private  String comment;

    public  BaeBean(String name,String comment){
        this.name = name;
        this.comment = comment;

    }
    public  BaeBean(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
