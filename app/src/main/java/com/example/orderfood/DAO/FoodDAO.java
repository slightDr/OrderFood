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
     * 用id获取指定食物
     * @param id
     * @return
     */
    public static FoodBean getFoodByFid(String id) {
        Cursor cursor = conn.rawQuery("select * from foods where f_id = ?",
                new String[]{id});
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

    /**
     * 用fid获取月详情单
     * @return
     */
    public static List<Integer> getMonthOrderDetailIds(int f_id) {
        Cursor cursor = conn.rawQuery("select * from orders " +
                "where strftime('%Y-%m', o_time) = strftime('%Y-%m', 'now') and f_id = ?;",
                new String[]{""+f_id});
        List<Integer> o_detail_ids = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex("o_detail_id");
            o_detail_ids.add(cursor.getInt(idx));
        }
        return o_detail_ids;
    }

    /**
     * 用fid获取食物月销量
     * @param f_id
     * @return
     */
    public static int getMonthSale(int f_id) {
        Integer ret = 0;
        // fid相等的当月的订单详情ids
        List<Integer> o_detail_ids = getMonthOrderDetailIds(f_id);
        Log.d("mine", "f_id: "+f_id);
        for (Integer id : o_detail_ids) {
            Log.d("mine", ""+id);
            Cursor cursor = conn.rawQuery("select * from order_details " +
                    "where o_detail_id = ?", new String[]{""+id});
            while (cursor.moveToNext()) { // 遍历，总数求和
                int idx = cursor.getColumnIndex("o_num");
                ret += cursor.getInt(idx);
            }
        }
        return ret;
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
}
