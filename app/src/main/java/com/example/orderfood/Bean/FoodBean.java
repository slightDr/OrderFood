package com.example.orderfood.Bean;

public class FoodBean {

    public FoodBean() {
    }

    public FoodBean(int f_id, int s_id, String f_name, String f_desc, float f_price, String f_img) {
        this.f_id = f_id;
        this.s_id = s_id;
        this.f_name = f_name;
        this.f_desc = f_desc;
        this.f_price = f_price;
        this.f_img = f_img;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_desc() {
        return f_desc;
    }

    public void setF_desc(String f_desc) {
        this.f_desc = f_desc;
    }

    public float getF_price() {
        return f_price;
    }

    public void setF_price(float f_price) {
        this.f_price = f_price;
    }

    public String getF_img() {
        return f_img;
    }

    public void setF_img(String f_img) {
        this.f_img = f_img;
    }

    @Override
    public String toString() {
        return "FoodBean{" +
                "f_id=" + f_id +
                ", s_id=" + s_id +
                ", f_name='" + f_name + '\'' +
                ", f_desc='" + f_desc + '\'' +
                ", f_price=" + f_price +
                ", f_img='" + f_img + '\'' +
                '}';
    }

    private int f_id; // 食物ID
    private int s_id; // 商家ID
    private String f_name; // 食物名称
    private String f_desc; // 食物描述
    private float f_price; // 食物价格
    private String f_img; // 食物图片路径
}
