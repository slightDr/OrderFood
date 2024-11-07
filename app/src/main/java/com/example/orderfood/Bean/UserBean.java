package com.example.orderfood.Bean;

public class UserBean {

    public UserBean() {
    }

    public UserBean(int u_id, String u_pwd, String u_name, String u_sex, String u_img) {
        this.u_id = u_id;
        this.u_pwd = u_pwd;
        this.u_name = u_name;
        this.u_sex = u_sex;
        this.u_img = u_img;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_pwd() {
        return u_pwd;
    }

    public void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "u_id=" + u_id +
                ", u_pwd='" + u_pwd + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_sex='" + u_sex + '\'' +
                ", u_img='" + u_img + '\'' +
                '}';
    }

    private int u_id;
    private String u_pwd;
    private String u_name;
    private String u_sex;
    private String u_img;
}
