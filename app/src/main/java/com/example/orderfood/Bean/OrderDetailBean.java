package com.example.orderfood.Bean;

public class OrderDetailBean {

    public OrderDetailBean() {
    }

    public OrderDetailBean(int o_detail_id, int o_id, int f_id, String f_name, String f_desc, float f_price, String f_img, int o_num) {
        this.o_detail_id = o_detail_id;
        this.o_id = o_id;
        this.f_id = f_id;
        this.f_name = f_name;
        this.f_desc = f_desc;
        this.f_price = f_price;
        this.f_img = f_img;
        this.o_num = o_num;
    }

    public int getO_detail_id() {
        return o_detail_id;
    }

    public void setO_detail_id(int o_detail_id) {
        this.o_detail_id = o_detail_id;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
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

    public int getO_num() {
        return o_num;
    }

    public void setO_num(int o_num) {
        this.o_num = o_num;
    }

    @Override
    public String toString() {
        return "OrderDetailBean{" +
                "o_detail_id=" + o_detail_id +
                "o_id=" + o_id +
                "f_id=" + f_id +
                ", f_name='" + f_name + '\'' +
                ", f_desc='" + f_desc + '\'' +
                ", f_price=" + f_price +
                ", f_img='" + f_img + '\'' +
                ", o_num=" + o_num +
                '}';
    }

    private int o_detail_id;
    private int o_id;
    private int f_id;
    private String f_name;
    private String f_desc;
    private float f_price;
    private String f_img;
    private int o_num;
}
