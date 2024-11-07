package com.example.orderfood.Bean;

public class OrderBean {

    public OrderBean() {
    }

    public OrderBean(int o_id, String o_time, int s_id, int u_id, int o_status, int i_id) {
        this.o_id = o_id;
        this.o_time = o_time;
        this.s_id = s_id;
        this.u_id = u_id;
        this.o_status = o_status;
        this.i_id = i_id;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public String getO_time() {
        return o_time;
    }

    public void setO_time(String o_time) {
        this.o_time = o_time;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getO_status() {
        return o_status;
    }

    public void setO_status(int o_status) {
        this.o_status = o_status;
    }

    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "o_id=" + o_id +
                ", o_time='" + o_time + '\'' +
                ", s_id=" + s_id +
                ", u_id=" + u_id +
                ", o_status=" + o_status +
                ", i_id='" + i_id + '\'' +
                '}';
    }

    private int o_id;
    private String o_time;
    private int s_id;
    private int u_id;
    private int o_status;
    private int i_id;
}
