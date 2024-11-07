package com.example.orderfood.activity.user.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserBuyShopFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;
import com.example.orderfood.activity.user.foodAct.ManageUserBuyActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageUserBuyShopFoodFragment extends Fragment {

    View rootView;
    String s_id;
    TextView priceView;
    ManageUserBuyActivity activity;

    public ManageUserBuyShopFoodFragment(String s_id, TextView priceView) {
        super();
        this.s_id = s_id;
        this.priceView = priceView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_manage_user_buy_shop_food, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        // 适配器
        activity = (ManageUserBuyActivity) getActivity();
        ListView listView = rootView.findViewById(R.id.user_buy_shop_food_listview);
        List<FoodBean> allFoods = FoodDAO.getAllFoodBySid(s_id);
        if (allFoods.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UserBuyShopFoodListAdapter(getContext(), allFoods, priceView, activity.foodJson));
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.foodJson = new JSONObject();
    }
}