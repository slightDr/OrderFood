package com.example.orderfood.activity.shop.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        rootView = inflater.inflate(R.layout.fragment_manage_shop_home, container, false);
        // 适配器
        ListView listView = rootView.findViewById(R.id.shop_home_food_listview);
        List<FoodBean> allFood = FoodDAO.getAllFood();
        if (allFood.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new ShopFoodListAdapter(getContext(), allFood));
        }
        return rootView;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
}