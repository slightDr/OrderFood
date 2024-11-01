package com.example.orderfood.DAO;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.orderfood.db.DBClient;

/**
 * 数据库数据结构
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
}
