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
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageUserHomeFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_manage_user_home, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        // 适配器
        ListView listView = rootView.findViewById(R.id.user_home_food_listview);
        List<FoodBean> allFoods = FoodDAO.getAllFood();
        if (allFoods.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UserFoodListAdapter(getContext(), allFoods));
        }

        // 搜索商品
        SearchView searchView = rootView.findViewById(R.id.manage_shop_home_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { // 根据name查找
                List<FoodBean> foodBeanList = FoodDAO.getFoodByName(s);
                if (foodBeanList.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new UserFoodListAdapter(getContext(), foodBeanList));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<FoodBean> foodBeanList = FoodDAO.getFoodByName(s);
                if (foodBeanList.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new UserFoodListAdapter(getContext(), foodBeanList));
                }
                return true;
            }
        });

        return rootView;
    }
}