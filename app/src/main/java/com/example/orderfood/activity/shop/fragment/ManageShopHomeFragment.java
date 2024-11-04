package com.example.orderfood.activity.shop.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageShopHomeFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_manage_shop_home, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        // 适配器
        ListView listView = rootView.findViewById(R.id.shop_home_food_listview);
        List<FoodBean> allFood = FoodDAO.getAllFood();
        if (allFood.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new ShopFoodListAdapter(getContext(), allFood));
        }

        SearchView searchView = rootView.findViewById(R.id.manage_shop_home_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { // 根据name查找
                String s_id = sharedPreferences.getString("s_id", "");
                List<FoodBean> foodBeanList = FoodDAO.getFoodBySidName(s_id, s);
                if (foodBeanList.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new ShopFoodListAdapter(getContext(), foodBeanList));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String s_id = sharedPreferences.getString("s_id", "");
                List<FoodBean> foodBeanList = FoodDAO.getFoodBySidName(s_id, s);
                if (foodBeanList.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new ShopFoodListAdapter(getContext(), foodBeanList));
                }
                return true;
            }
        });

        return rootView;
    }
}