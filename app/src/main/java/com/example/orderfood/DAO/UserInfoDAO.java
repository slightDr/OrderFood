package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.db.DBClient;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDAO {

    public static SQLiteDatabase conn = DBClient.connection;

    public static List<UserInfoBean> getAllUserInfosByUid(String u_id) {
        List<UserInfoBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from user_infos where u_id=?",
                new String[]{u_id});
        while (cursor.moveToNext()) {
            ret.add(new UserInfoBean(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
        return ret;
    }

    public static UserInfoBean getUserInfoByIid(String i_id) {
        Cursor cursor = conn.rawQuery("select * from user_infos where i_id=?",
                new String[]{i_id});
        if (cursor.moveToFirst()) {
            return new UserInfoBean(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }
        return null;
    }

    public static int setUserInfo(String id, String name, String addr, String tel) {
        try {
            conn.execSQL("update user_infos set i_name=?, i_addr=?, i_tel=? where i_id=?",
                    new String[]{name, addr, tel, id});
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int delById(String i_id) {
        try {
            conn.execSQL("delete from user_infos where i_id=?",
                    new String[]{i_id});
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int addUserInfo(String uId, String name, String addr, String tel) {
        try {
            conn.execSQL("insert into user_infos values(?,?,?,?,?)",
                    new String[]{null, uId, name, addr, tel});
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
