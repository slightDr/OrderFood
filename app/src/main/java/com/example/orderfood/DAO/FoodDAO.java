package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.db.DBClient;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    public static SQLiteDatabase conn = DBClient.connection;

    /**
     * 通过s_id获取食物列表
     * @return
     */
    public static List<FoodBean> getAllFoodBySid(String sid) {
        List<FoodBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from foods where s_id=?",
                new String[]{sid});
        while (cursor.moveToNext()) {
            Integer f_id = cursor.getInt(0);
            Integer s_id = cursor.getInt(1);
            String f_name = cursor.getString(2);
            String f_desc = cursor.getString(3);
            float f_price = cursor.getFloat(4);
            String f_img = cursor.getString(5);
            FoodBean foodBean = new FoodBean(
                    f_id, s_id, f_name, f_desc, f_price, f_img
            );
            ret.add(foodBean);
        }
        return ret;
    }

    /**
     * 获取食物列表
     * @return
     */
    public static List<FoodBean> getAllFood() {
        List<FoodBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from foods", null);
        while (cursor.moveToNext()) {
            Integer f_id = cursor.getInt(0);
            Integer s_id = cursor.getInt(1);
            String f_name = cursor.getString(2);
            String f_desc = cursor.getString(3);
            float f_price = cursor.getFloat(4);
            String f_img = cursor.getString(5);
            FoodBean foodBean = new FoodBean(
                 f_id, s_id, f_name, f_desc, f_price, f_img
            );
            ret.add(foodBean);
        }
        return ret;
    }

    /**
     * 用shop id和food name获取指定食物
     */
    public static List<FoodBean> getFoodBySidName(String id, String name) {
        List<FoodBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from foods where s_id=? and f_name like ?",
                new String[]{id, "%"+name+"%"});
        while (cursor.moveToNext()) {
            Integer f_id = cursor.getInt(0);
            Integer s_id = cursor.getInt(1);
            String f_name = cursor.getString(2);
            String f_desc = cursor.getString(3);
            float f_price = cursor.getFloat(4);
            String f_img = cursor.getString(5);
            FoodBean foodBean = new FoodBean(
                    f_id, s_id, f_name, f_desc, f_price, f_img
            );
            ret.add(foodBean);
        }
        return ret;
    }

    /**
     * 用fid获取月详情单
     * @return
     */
    public static List<String> getMonthOrderIds(String s_id) {
        Cursor cursor = conn.rawQuery("select * from orders " +
                "where strftime('%Y-%m', o_time) = strftime('%Y-%m', 'now') and s_id=? and o_status=3;",
                new String[]{s_id});
        List<String> o_ids = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex("o_id");
            o_ids.add(""+cursor.getInt(idx));
        }
        return o_ids;
    }

    /**
     * 用fid和sid获取食物月销量
     */
    public static int getMonthSale(String f_id, String s_id) {
        Integer ret = 0;
        // fid相等的当月的订单详情ids
        List<String> o_ids = getMonthOrderIds(s_id);
        Log.d("mine", "f_id: "+f_id);
        for (String id : o_ids) {
            Log.d("mine", id);
            Cursor cursor = conn.rawQuery("select * from order_details " +
                    "where o_id=? and f_id=?", new String[]{id, f_id});
            while (cursor.moveToNext()) { // 遍历，总数求和
                int idx = cursor.getColumnIndex("o_num");
                ret += cursor.getInt(idx);
            }
        }
        return ret;
    }

    public static FoodBean getFoodByFid(String fid) {
        Cursor cursor = conn.rawQuery("select * from foods where f_id=?",
                new String[]{fid});
        if (cursor.moveToFirst()) {
            Integer f_id = cursor.getInt(0);
            Integer s_id = cursor.getInt(1);
            String f_name = cursor.getString(2);
            String f_desc = cursor.getString(3);
            float f_price = cursor.getFloat(4);
            String f_img = cursor.getString(5);
            return new FoodBean(
                    f_id, s_id, f_name, f_desc, f_price, f_img
            );
        }
        return null;
    }

    public static int addFood(String s_id, String name, String price, String desc, String img) {
        String data[] = {null, s_id, name, desc, price, img};
        try {
            conn.execSQL("insert into foods values(?,?,?,?,?,?);", data);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int updateFood(String f_id, String name, String price, String desc, String img) {
        String data[] = {name, desc, price, img, f_id};
        try {
            conn.execSQL("update foods set f_name=?, f_desc=?, f_price=?, f_img=? where f_id=?;", data);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 删除商品
     * @param f_id
     * @return
     */
    public static int delFood(String f_id) {
        try {
            conn.execSQL("delete from foods where f_id=?;", new String[]{f_id});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 通过字段查询食物
     */
    public static List<FoodBean> getFoodByName(String name) {
        List<FoodBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from foods where f_name like ?",
                new String[]{"%"+name+"%"});
        while (cursor.moveToNext()) {
            Integer f_id = cursor.getInt(0);
            Integer s_id = cursor.getInt(1);
            String f_name = cursor.getString(2);
            String f_desc = cursor.getString(3);
            float f_price = cursor.getFloat(4);
            String f_img = cursor.getString(5);
            FoodBean foodBean = new FoodBean(
                    f_id, s_id, f_name, f_desc, f_price, f_img
            );
            ret.add(foodBean);
        }
        return ret;
    }
}
