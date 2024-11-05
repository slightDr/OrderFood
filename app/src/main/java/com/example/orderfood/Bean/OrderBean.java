package com.example.orderfood.Bean;

public class OrderBean {

    public OrderBean() {
    }

    public OrderBean(int o_id, String o_time, int s_id, int u_id, int o_status, String o_addr) {
        this.o_id = o_id;
        this.o_time = o_time;
        this.s_id = s_id;
        this.u_id = u_id;
        this.o_status = o_status;
        this.o_addr = o_addr;
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

    public String getO_addr() {
        return o_addr;
    }

    public void setO_addr(String o_addr) {
        this.o_addr = o_addr;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "o_id=" + o_id +
                ", o_time='" + o_time + '\'' +
                ", s_id=" + s_id +
                ", u_id=" + u_id +
                ", o_status=" + o_status +
                ", o_addr='" + o_addr + '\'' +
                '}';
    }

    private int o_id;
    private String o_time;
    private int s_id;
    private int u_id;
    private int o_status;
    private String o_addr;
}
