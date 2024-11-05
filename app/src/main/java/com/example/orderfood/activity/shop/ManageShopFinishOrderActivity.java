package com.example.orderfood.activity.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;
import com.example.orderfood.activity.shop.adapter.UnfinishOrderListAdapter;

import java.util.List;

public class ManageShopFinishOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_shop_finish_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences shared = ManageShopFinishOrderActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String s_id = shared.getString("s_id", "");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_shop_finish_order_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 列出订单
        ListView listView = findViewById(R.id.manage_shop_finish_order_listview);
        List<OrderBean> allOrders = OrderDAO.getAllOrdersBySidStatus(s_id, "1");  // 未完成订单
        if (allOrders.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UnfinishOrderListAdapter(this, allOrders));
        }

        // 搜索订单功能
//        SearchView searchView = findViewById(R.id.manage_shop_finish_order_search);
//        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) { // 根据name查找
//                String s_id = sharedPreferences.getString("s_id", "");
//                List<FoodBean> foodBeanList = FoodDAO.getFoodBySidName(s_id, s);
//                if (foodBeanList.isEmpty()) {
//                    listView.setAdapter(null);
//                } else {
//                    listView.setAdapter(new ShopFoodListAdapter(ManageShopFinishOrderActivity.this, foodBeanList));
//                }
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                String s_id = sharedPreferences.getString("s_id", "");
//                List<FoodBean> foodBeanList = FoodDAO.getFoodBySidName(s_id, s);
//                if (foodBeanList.isEmpty()) {
//                    listView.setAdapter(null);
//                } else {
//                    listView.setAdapter(new ShopFoodListAdapter(ManageShopFinishOrderActivity.this, foodBeanList));
//                }
//                return true;
//            }
//        });
    }
}