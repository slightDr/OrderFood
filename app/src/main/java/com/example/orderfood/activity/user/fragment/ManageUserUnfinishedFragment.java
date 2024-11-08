package com.example.orderfood.activity.user.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.ManageShopFinishOrderActivity;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;
import com.example.orderfood.activity.shop.adapter.UnfinishOrderListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserUnfinishedListAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageUserUnfinishedFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_manage_user_unfinished, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        // 适配器
        ListView listView = rootView.findViewById(R.id.user_unfinished_listview);
//        List<FoodBean> allFoods = FoodDAO.getAllFood();
        List<OrderBean> allOrders = OrderDAO.getAllOrdersByUidStatus(u_id, "1");
        if (allOrders.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UserUnfinishedListAdapter(getContext(), allOrders));
        }

        // 搜索商品
        SearchView searchView = rootView.findViewById(R.id.manage_user_unfinished_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<OrderBean> orders = OrderDAO.getFromOrdersByStr(allOrders, s);
                // Log.d("mine", ""+orders.size());
                if (orders.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new UserUnfinishedListAdapter(getContext(), orders));
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
                    listView.setAdapter(new UserUnfinishedListAdapter(getContext(), orders));
                }
                return true;
            }
        });

        return rootView;
    }
}