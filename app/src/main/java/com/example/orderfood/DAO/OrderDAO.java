package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.db.DBClient;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public static SQLiteDatabase conn = DBClient.connection;

    /**
     * 查看s_id所有订单
     */
    public static List<OrderBean> getAllOrders(String s_id) {
        List<OrderBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from orders where s_id=?",
                new String[]{s_id});
        while (cursor.moveToNext()) {
            ret.add(new OrderBean(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        return ret;
    }

    /**
     * 查看s_id所有对应状态的订单
     */
    public static List<OrderBean> getAllOrdersBySidStatus(String s_id, String status) {
        List<OrderBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from orders where s_id=? and o_status=? " +
                        "order by strftime('%Y-%m-%d %H:%M:%S', o_time) desc",
                new String[]{s_id, status});
        while (cursor.moveToNext()) {
            ret.add(new OrderBean(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        return ret;
    }

    /**
     * 查看u_id所有对应状态的订单
     */
    public static List<OrderBean> getAllOrdersByUidStatus(String u_id, String status) {
        List<OrderBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from orders where u_id=? and o_status=? " +
                        "order by strftime('%Y-%m-%d %H:%M:%S', o_time) desc",
                new String[]{u_id, status});
        while (cursor.moveToNext()) {
            ret.add(new OrderBean(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        return ret;
    }

    /**
     * 查看o_id所有订单详情
     */
    public static List<OrderDetailBean> getOrderDetailsByOid(String o_id) {
        List<OrderDetailBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from order_details where o_id=?",
                new String[]{o_id});
        while (cursor.moveToNext()) {  // 查未完成的order_detail_id
            ret.add(new OrderDetailBean(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getFloat(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            ));
        }
        return ret;
    }

    /**
     * 更改订单状态
     */
    public static int updateOrderStatus(String o_id, String status) {
        try {
            conn.execSQL("update orders set o_status=? where o_id=?",
                    new String[]{status, o_id});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 在订单中列表中通过用户联系方式或订单号查询指定订单
     */
    public static List<OrderBean> getFromOrdersByStr(List<OrderBean> orderBeans, String str) {
        List<OrderBean> ret = new ArrayList<>();
        for (OrderBean orderBean : orderBeans) {
            // 如果搜索str是oid的一部分，加入返回列表中
            if ((""+orderBean.getO_id()).contains(str)) {
                ret.add(orderBean);
                continue;
            }
            // 如果搜索str是联系方式的一部分，加入返回列表中
            int i_id = orderBean.getI_id();
            UserInfoBean userInfo = UserInfoDAO.getUserInfoByIid(""+i_id);
            if (userInfo.getITel().contains(str)) {
                ret.add(orderBean);
            }
        }
        return ret;
    }

    /**
     * 增加订单
     */
    public static int insertOrder(String o_time, String s_id, String u_id, String o_status, String i_id) {
//        Log.d("insert order", o_time+s_id+u_id+o_status+i_id);
        try {
            conn.execSQL("insert into orders values(?,?,?,?,?,?)",
                    new String[]{null, o_time, s_id, u_id, o_status, i_id});
            // 获取自增字段的值
            Cursor cursor = conn.rawQuery("SELECT last_insert_rowid()", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 增加订单详情
     */
    public static int insertOrderDetail(String o_id, String f_id, String f_name, String f_desc, String f_price, String f_img, String o_num) {
        try {
            conn.execSQL("insert into order_details values(?,?,?,?,?,?,?,?)",
                    new String[]{null, o_id, f_id, f_name, f_desc, f_price, f_img, o_num});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
}
