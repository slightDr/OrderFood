package com.example.orderfood.Bean;

public class UserInfoBean {
    private int iId;      // 主键，自增
    private int uId;      // 外键，引用 users 表的 u_id
    private String iName; // 姓名，唯一
    private String iAddr; // 地址
    private String iTel;  // 电话

    // 默认构造函数
    public UserInfoBean() {
    }

    // 带参数的构造函数
    public UserInfoBean(int iId, int uId, String iName, String iAddr, String iTel) {
        this.iId = iId;
        this.uId = uId;
        this.iName = iName;
        this.iAddr = iAddr;
        this.iTel = iTel;
    }

    // Getter 和 Setter 方法
    public int getIId() {
        return iId;
    }

    public void setIId(int iId) {
        this.iId = iId;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getIName() {
        return iName;
    }

    public void setIName(String iName) {
        this.iName = iName;
    }

    public String getIAddr() {
        return iAddr;
    }

    public void setIAddr(String iAddr) {
        this.iAddr = iAddr;
    }

    public String getITel() {
        return iTel;
    }

    public void setITel(String iTel) {
        this.iTel = iTel;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "iId=" + iId +
                ", uId=" + uId +
                ", iName='" + iName + '\'' +
                ", iAddr='" + iAddr + '\'' +
                ", iTel='" + iTel + '\'' +
                '}';
    }
}
