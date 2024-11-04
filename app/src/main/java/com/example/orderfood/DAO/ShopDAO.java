package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.db.DBClient;

/**
 * 商家相关数据库操作
 */
public class ShopDAO {

    public static SQLiteDatabase conn = DBClient.connection;

    /**
     * 保存商家
     * @return 0为正确执行，1执行失败
     */
    public static int saveShop(String name, String pwd, String desc, String type, String img) {
        try {
            conn.execSQL(
                    "insert into shops values(?,?,?,?,?,?)",
                    new Object[]{null, pwd, name, desc, type, img}
            );
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int loginAsShop(String name, String pwd) {
        String[] args = {name, pwd};
        String sql = "select 1 from shops where s_name = ? and s_pwd = ? limit 1";

        try (Cursor cursor = conn.rawQuery(sql, args)) {
            if (cursor.moveToFirst()) {
                Log.d("mine", "login shop id: "+cursor.getInt(0));
                return cursor.getInt(0); // 登录成功
            }
        } catch (Exception e) {
            Log.e("mine", e.toString());
        }
        return -1; // 登录失败
    }

    public static ShopBean getShopInfoBySid(String s_id) {
        String data[] = {s_id};
        Cursor cursor = conn.rawQuery("select * from shops where s_id=?", data);
        if (cursor.moveToFirst()) {
            return new ShopBean(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }
        return null;
    }

    public static int delShopBySid(String s_id) {
        try {
            conn.execSQL("delete from shops where s_id=?", new String[]{s_id});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int updateShop(String s_id, String name, String desc, String type, String img) {
        String data[] = new String[]{name, desc, type, img, s_id};
        try {
            conn.execSQL("update shops set s_name=?,s_desc=?,s_type=?,s_img=? where s_id=?", data);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int updateShopPwd(String s_id, String pwd) {
        String data[] = new String[]{pwd, s_id};
        try {
            conn.execSQL("update shops set s_pwd=? where s_id=?", data);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
}
