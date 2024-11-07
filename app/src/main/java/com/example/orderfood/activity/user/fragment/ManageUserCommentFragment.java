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

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.DAO.CommentDAO;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserBuyShopCommentListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageUserCommentFragment extends Fragment {

    View rootView;
    String s_id;

    public ManageUserCommentFragment(String s_id) {
        super();
        this.s_id = s_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_manage_user_comment, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        // 适配器
        ListView listView = rootView.findViewById(R.id.user_buy_comment_listview);
        List<CommentBean> allComments = CommentDAO.getCommentsBySid(s_id);
        if (allComments.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UserBuyShopCommentListAdapter(getContext(), allComments));
        }

        return rootView;
    }
}