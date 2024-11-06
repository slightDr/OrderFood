package com.example.orderfood.activity.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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
import com.example.orderfood.activity.shop.adapter.FinishedOrderListAdapter;
import com.example.orderfood.activity.shop.adapter.UnfinishOrderListAdapter;

import java.util.List;

public class ManageShopShowFinishedOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_shop_show_finished_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences shared = ManageShopShowFinishedOrderActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String s_id = shared.getString("s_id", "");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_shop_finished_order_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 列出订单
        ListView listView = findViewById(R.id.manage_shop_finished_order_listview);
        List<OrderBean> allOrders = OrderDAO.getAllOrdersBySidStatus(s_id, "2");  // 未完成订单
        allOrders.addAll(OrderDAO.getAllOrdersBySidStatus(s_id, "3"));
        if (allOrders.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new FinishedOrderListAdapter(this, allOrders));
        }

        // 搜索订单功能
        SearchView searchView = findViewById(R.id.manage_shop_finished_order_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<OrderBean> orders = OrderDAO.getFromOrdersByStr(allOrders, s);
                // Log.d("mine", ""+orders.size());
                if (orders.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new UnfinishOrderListAdapter(ManageShopShowFinishedOrderActivity.this, orders));
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
                    listView.setAdapter(new UnfinishOrderListAdapter(ManageShopShowFinishedOrderActivity.this, orders));
                }
                return true;
            }
        });
    }
}