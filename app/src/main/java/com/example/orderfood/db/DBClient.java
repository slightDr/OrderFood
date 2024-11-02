package com.example.orderfood.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 链接数据库方法类
 */
public class DBClient extends SQLiteOpenHelper {

    private static final int ver = 2;  // 版本号，每次更改表结构都需要+1，否则不生效
    private static final String dbName = "db_orderfood.db";  // 数据库名称
    private Context context;

    public static SQLiteDatabase connection;  // 链接数据库的链接

    public DBClient(Context context) {
        super(context, dbName, null, ver, null);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建数据库
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = false"); // 外键失效

        sqLiteDatabase.execSQL("drop table if exists shops"); //如果这表存在则删
        // 商家ID，密码，名称，
        sqLiteDatabase.execSQL("create table shops(" +
            "s_id integer primary key autoincrement," +
            "s_pwd varchar(20)," +
            "s_name varchar(20) unique," +
            "s_desc varchar(200)," +
            "s_type varchar(20)," +
            "s_img varchar(255))"); // 图片路径

        // 初始商家
        sqLiteDatabase.execSQL("insert into shops values(?,?,?,?,?,?)",
                new Object[]{null, "123456", "test", "测试", "测试", ""});

        sqLiteDatabase.execSQL("drop table if exists users"); //如果这表存在则删
        // 用户信息
        sqLiteDatabase.execSQL("create table users(" +
                "u_id integer primary key autoincrement," +
                "u_pwd varchar(20)," +
                "u_name varchar(20) unique," +
                "u_sex varchar(20)," +
                "u_addr varchar(200)," +
                "u_tel varchar(20)," +
                "u_img varchar(255))"); // 图片路径

        // 初始用户
        sqLiteDatabase.execSQL("insert into users values(?,?,?,?,?,?,?)",
                new Object[]{null, "123456", "u_test", "男", "test address", "test tel", ""});

        sqLiteDatabase.execSQL("PRAGMA foreign_keys = true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
