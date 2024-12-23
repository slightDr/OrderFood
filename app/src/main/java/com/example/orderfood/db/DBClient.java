package com.example.orderfood.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.orderfood.R;
import com.example.orderfood.util.FileImgUtil;
import com.example.orderfood.util.PasswordUtil;

/**
 * 链接数据库方法类
 */
public class DBClient extends SQLiteOpenHelper {

    private static final int ver = 28;  // 版本号，每次更改表结构都需要+1，否则不生效
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
        String foodPath1 = FileImgUtil.getPicAbsPath();
        FileImgUtil.saveDefaultImgToPath(context, R.drawable.mlt, foodPath1);
        String foodPath2 = FileImgUtil.getPicAbsPath();
        FileImgUtil.saveDefaultImgToPath(context, R.drawable.upload_img, foodPath2);

        /** 存储商家 */
        sqLiteDatabase.execSQL("drop table if exists shops"); //如果该表存在则删除
        // 商家ID，密码，名称，
        sqLiteDatabase.execSQL("create table shops(" +
            "s_id integer primary key autoincrement," +
            "s_pwd varchar(20)," +
            "s_name varchar(20) unique," +
            "s_desc varchar(200)," +
            "s_type varchar(20)," +
            "s_img varchar(255))"); // 图片路径
        // 初始商家
        String pwd = PasswordUtil.hashPassword("123456");
        sqLiteDatabase.execSQL("insert into shops values(?,?,?,?,?,?)",
                new Object[]{null, pwd, "test", "测试", "测试", foodPath1});

        /** 存储用户 */
        sqLiteDatabase.execSQL("drop table if exists users"); //如果这表存在则删
        // 用户信息
        sqLiteDatabase.execSQL("create table users(" +
                "u_id integer primary key autoincrement," +
                "u_pwd varchar(20)," +
                "u_name varchar(20) unique," +  // 账号名
                "u_sex varchar(20)," +
                "u_img varchar(255))"); // 图片路径
        // 初始用户
        String userPath = FileImgUtil.getPicAbsPath();
        FileImgUtil.saveDefaultImgToPath(context, R.drawable.upload_img, userPath);
        sqLiteDatabase.execSQL("insert into users values(?,?,?,?,?)",
                new Object[]{null, pwd, "test", "男", userPath});

        /** 存储用户收货信息 */
        sqLiteDatabase.execSQL("drop table if exists user_infos"); //如果这表存在则删
        // 收货信息
        sqLiteDatabase.execSQL("create table user_infos(" +
                "i_id integer primary key autoincrement," +
                "u_id integer references users(u_id)," +
                "i_name varchar(20)," +  // 收货人姓名
                "i_addr varchar(200)," +
                "i_tel varchar(20))");
        sqLiteDatabase.execSQL("insert into user_infos values(?,?,?,?,?)",
                new Object[]{null, 1, "u_test1", "test address1", "test tel1"});
        sqLiteDatabase.execSQL("insert into user_infos values(?,?,?,?,?)",
                new Object[]{null, 1, "u_test2", "test address2", "test tel2"});
        sqLiteDatabase.execSQL("insert into user_infos values(?,?,?,?,?)",
                new Object[]{null, 1, "u_test3", "test address3", "test tel3"});

        /** 存储商品 */
        sqLiteDatabase.execSQL("drop table if exists foods"); //如果这表存在则删
        sqLiteDatabase.execSQL("create table foods(" +
                "f_id integer primary key autoincrement," + // 食物id
                "s_id integer references shops(s_id) on delete cascade," + // 商家id
                "f_name varchar(20)," +
                "f_desc varchar(200)," +
                "f_price float," +
                "f_img varchar(255))"); // 图片路径
        // 初始食品
        sqLiteDatabase.execSQL("insert into foods values(?,?,?,?,?,?)",
                new Object[]{null, 1, "f_test1", "f_test1", 0.01, foodPath1});
        sqLiteDatabase.execSQL("insert into foods values(?,?,?,?,?,?)",
                new Object[]{null, 1, "f_test2", "f_test2", 0.02, foodPath2});

        /** 订单表 */
        sqLiteDatabase.execSQL("drop table if exists orders"); //如果这表存在则删
        sqLiteDatabase.execSQL("create table orders(" +
                "o_id integer primary key autoincrement," + // 订单id
                "o_time varchar(50)," + // 订单时间
                "s_id integer references shops(s_id)," +
                "u_id integer references users(u_id)," +
                "o_status integer," + // 订单状态: 1未处理订单 2取消订单 3完成订单
                "i_id integer references user_infos(i_id))"); // 收货信息id
        // 初始订单
        sqLiteDatabase.execSQL("insert into orders values(?,?,?,?,?,?)",
                new Object[]{null, "2024-11-01 12:34:56", 1, 1, 3, 1});
        sqLiteDatabase.execSQL("insert into orders values(?,?,?,?,?,?)",
                new Object[]{null, "2024-11-02 12:34:56", 1, 1, 1, 2});
        sqLiteDatabase.execSQL("insert into orders values(?,?,?,?,?,?)",
                new Object[]{null, "2024-11-03 12:34:56", 1, 1, 2, 3});

        /** 订单详情表 */
        sqLiteDatabase.execSQL("drop table if exists order_details"); //如果这表存在则删
        sqLiteDatabase.execSQL("create table order_details(" +
                "o_detail_id integer primary key autoincrement," + // 订单详情id
                "o_id integer references orders(o_id)," + // 订单id
                "f_id integer references foods(f_id)," +
                "f_name varchar(20)," +
                "f_desc varchar(200)," +
                "f_price float," +
                "f_img varchar(255)," + // 图片路径
                "o_num integer)"); // 数量
        // 初始订单详情
        sqLiteDatabase.execSQL("insert into order_details values(?,?,?,?,?,?,?,?)",
                new Object[]{null, 2, 2, "f_test2", "f_test2", .02, foodPath2, 1});
        sqLiteDatabase.execSQL("insert into order_details values(?,?,?,?,?,?,?,?)",
                new Object[]{null, 1, 1, "f_test1", "f_test1", .01, foodPath1, 22});
        sqLiteDatabase.execSQL("insert into order_details values(?,?,?,?,?,?,?,?)",
                new Object[]{null, 2, 2, "f_test2", "f_test2", .02, foodPath2, 22});
        sqLiteDatabase.execSQL("insert into order_details values(?,?,?,?,?,?,?,?)",
                new Object[]{null, 3, 1, "f_test1", "f_test1", .01, foodPath1, 333});

        /** 评论表 */
        sqLiteDatabase.execSQL("drop table if exists comments"); //如果这表存在则删
        sqLiteDatabase.execSQL("create table comments(" +
                "comment_id integer primary key autoincrement," +
                "u_id integer references users(u_id)," +
                "s_id integer references shops(s_id)," +
                "comment_time varchar(50)," +
                "comment_content varchar(200)," +
                "comment_score integer," +
                "comment_img varchar(255))");
        sqLiteDatabase.execSQL("insert into comments values(?,?,?,?,?,?,?)",
                new Object[]{null, 1, 1, "2024-11-01 12:34:56", "一级棒", 4, foodPath1});
        sqLiteDatabase.execSQL("insert into comments values(?,?,?,?,?,?,?)",
                new Object[]{null, 1, 1, "2024-11-02 12:34:56", "一般", 3, foodPath2});
        sqLiteDatabase.execSQL("insert into comments values(?,?,?,?,?,?,?)",
                new Object[]{null, 1, 1, "2024-11-03 12:34:56", "垃圾", 1, foodPath1});
        sqLiteDatabase.execSQL("insert into comments values(?,?,?,?,?,?,?)",
                new Object[]{null, 1, 1, "2024-11-04 12:34:56", "好吃", 5, foodPath2});

        sqLiteDatabase.execSQL("PRAGMA foreign_keys = true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
