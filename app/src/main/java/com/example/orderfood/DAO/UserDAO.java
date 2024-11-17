package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.db.DBClient;
import com.example.orderfood.util.PasswordUtil;

/**
 * 用户相关数据库操作
 */
public class UserDAO {
    public static SQLiteDatabase conn = DBClient.connection;

    public static int saveUser(String name, String pwd, String sex, String addr, String tel, String img) {
        try {
            conn.execSQL(
                    "insert into users values(?,?,?,?,?)",
                    new Object[]{null, pwd, name, sex, img}
            );
            Cursor cursor = conn.rawQuery("select last_insert_rowid()", null);
            cursor.moveToFirst();
            conn.execSQL("insert into user_infos values(?,?,?,?,?)",
                    new Object[]{null, cursor.getInt(0), name, addr, tel});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int loginAsUser(String name, String pwd) {
        pwd = PasswordUtil.hashPassword(pwd);
        String[] args = {name, pwd};
        String sql = "select * from users where u_name = ? and u_pwd = ? limit 1";

        try (Cursor cursor = conn.rawQuery(sql, args)) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0); // 登录成功
            }
        } catch (Exception e) {
            Log.e("mine", e.toString());
        }
        return -1; // 登录失败
    }

    /**
     * 查询用户信息
     */
    public static UserBean getUserInfoByUid(String u_id) {
        String data[] = {u_id};
        Cursor cursor = conn.rawQuery("select * from users where u_id=?", data);
        if (cursor.moveToFirst()) {
            return new UserBean(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }
        return null;
    }

    public static int delUserByUid(String uId) {
        try {
            conn.execSQL("delete from users where u_id=?", new String[]{uId});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int updateUserPwd(String uId, String pwd) {
        String data[] = new String[]{pwd, uId};
        try {
            conn.execSQL("update users set u_pwd=? where u_id=?", data);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int updateUser(String uId, String name, String sex, String picPath) {
        try {
            conn.execSQL("update users set u_name=?, u_sex=?, u_img=? where u_id=?",
                    new String[]{name, sex, picPath, uId});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
}
