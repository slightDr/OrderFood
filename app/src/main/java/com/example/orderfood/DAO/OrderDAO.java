package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserBean;
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
                    cursor.getString(5)
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
                    cursor.getString(5)
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
        // Log.d("mine", "getFromOrdersByStr");
        for (OrderBean orderBean : orderBeans) {
            if ((""+orderBean.getO_id()).contains(str)) {
                // Log.d("mine", "id, "+orderBean.toString());
                ret.add(orderBean);
                continue;
            }
            int u_id = orderBean.getU_id();
            UserBean userBean = UserDAO.getUserInfoByUid("" + u_id);
            if (userBean.getU_tel().contains(str)) {
                // Log.d("mine", "tel, "+orderBean.toString());
                ret.add(orderBean);
            }
        }
        return ret;
    }
}
