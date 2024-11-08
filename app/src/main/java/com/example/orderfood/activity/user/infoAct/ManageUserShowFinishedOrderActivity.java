package com.example.orderfood.activity.user.infoAct;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.adapter.UserFinishedOrderListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ManageUserShowFinishedOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_show_finished_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences shared = ManageUserShowFinishedOrderActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = shared.getString("u_id", "1");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_user_finished_order_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 列出订单
        ListView listView = findViewById(R.id.manage_user_finished_order_listview);
        List<OrderBean> allOrders = OrderDAO.getAllOrdersByUidStatus(u_id, "2");
        allOrders.addAll(OrderDAO.getAllOrdersByUidStatus(u_id, "3"));
        // 按照 o_time 排序
        Collections.sort(allOrders, new Comparator<OrderBean>() {
            @Override
            public int compare(OrderBean o1, OrderBean o2) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = format.parse(o1.getO_time());
                    Date date2 = format.parse(o2.getO_time());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        if (allOrders.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UserFinishedOrderListAdapter(this, allOrders));
        }

        // 搜索订单功能
        SearchView searchView = findViewById(R.id.manage_user_finished_order_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<OrderBean> orders = OrderDAO.getFromOrdersByStr(allOrders, s);
                // Log.d("mine", ""+orders.size());
                if (orders.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new UserFinishedOrderListAdapter(ManageUserShowFinishedOrderActivity.this, orders));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<OrderBean> orders = OrderDAO.getFromOrdersByStr(allOrders, s);
                // Log.d("mine", ""+orders.size());
                if (orders.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new UserFinishedOrderListAdapter(ManageUserShowFinishedOrderActivity.this, orders));
                }
                return true;
            }
        });
    }
}