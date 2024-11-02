package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.db.DBClient;

/**
 * 用户相关数据库操作
 */
public class UserDAO {
    public static SQLiteDatabase conn = DBClient.connection;

    public static int saveUser(String name, String pwd, String sex, String addr, String tel, String img) {
        try {
            conn.execSQL(
                    "insert into users values(?,?,?,?,?,?,?)",
                    new Object[]{null, pwd, name, sex, addr, tel, img}
            );
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int loginAsUser(String name, String pwd) {
        String[] args = {name, pwd};
        String sql = "select 1 from users where u_name = ? and u_pwd = ? limit 1";

        try (Cursor cursor = conn.rawQuery(sql, args)) {
            if (cursor.moveToFirst()) {
                return 0; // 登录成功
            }
        } catch (Exception e) {
            Log.e("mine", e.toString());
        }
        return 1; // 登录失败
    }
}
