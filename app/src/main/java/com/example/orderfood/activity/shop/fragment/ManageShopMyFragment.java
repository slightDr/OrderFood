package com.example.orderfood.activity.shop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.R;

public class ManageShopMyFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_manage_shop_my, container, false);

        // 账号
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String s_id = sharedPreferences.getString("s_id", "");
        ShopBean shop = ShopDAO.getShopInfoBySid(s_id);
        // 头像
        ImageView imgView = rootView.findViewById(R.id.manage_shop_my_avatar);
        Drawable sImgDrawableDefault = ContextCompat.getDrawable(requireContext(), R.drawable.upload_img);
        Bitmap sImgBitmapDefault = ((BitmapDrawable) sImgDrawableDefault).getBitmap();
        imgView.setImageBitmap(
                shop.getS_img().isEmpty() ? sImgBitmapDefault : BitmapFactory.decodeFile(shop.getS_img())
        );
        // id
        TextView idText = rootView.findViewById(R.id.manage_shop_my_id);
        idText.setText("id: "+shop.getS_id());
        // name
        TextView nameText = rootView.findViewById(R.id.manage_shop_my_name);
        nameText.setText(shop.getS_name());
        // desc
        TextView descText = rootView.findViewById(R.id.manage_shop_my_desc);
        descText.setText("店铺简介："+shop.getS_desc());

        return rootView;
    }
}
