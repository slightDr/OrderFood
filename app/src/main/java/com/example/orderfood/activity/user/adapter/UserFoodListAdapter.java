package com.example.orderfood.activity.user.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.DAO.CommentDAO;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.fragment.ManageShopUpdateFoodFragment;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UserFoodListAdapter extends ArrayAdapter<FoodBean> {

    private List<FoodBean> list;
    private Context context;

    public UserFoodListAdapter(@NonNull Context context, List<FoodBean> list) {
        super(context, R.layout.list_user_home_food, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_user_home_food, viewGroup, false);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");
        FoodBean food = list.get(position);
        ShopBean shopBean = ShopDAO.getShopInfoBySid(""+food.getS_id());

        // 商品信息
        ImageView imageView = convertView.findViewById(R.id.user_food_list_foodImg);
        TextView nameText = convertView.findViewById(R.id.user_food_list_foodName);
        TextView saleText = convertView.findViewById(R.id.user_food_list_foodSale);
        TextView priceText = convertView.findViewById(R.id.user_food_list_foodPrice);
        TextView descText = convertView.findViewById(R.id.user_food_list_foodDesc);

        Bitmap bitmap = BitmapFactory.decodeFile(food.getF_img());
        imageView.setImageBitmap(bitmap);
        nameText.setText(food.getF_name());
        saleText.setText("月销售：" + FoodDAO.getMonthSale(""+food.getF_id(), ""+shopBean.getS_id()));
        priceText.setText("价格：" + food.getF_price());
        descText.setText(food.getF_desc());

        // 店铺信息
        ImageView sImgView = convertView.findViewById(R.id.user_food_list_shopImg);
        Log.d("user", shopBean.getS_img());
        sImgView.setImageBitmap(BitmapFactory.decodeFile(shopBean.getS_img()));
        TextView sNameView = convertView.findViewById(R.id.user_food_list_shopName);
        sNameView.setText(shopBean.getS_name());
        // 计算分数
        TextView scoreView = convertView.findViewById(R.id.user_food_list_score);
        scoreView.setText(CommentDAO.getAvgScoreBySid(""+shopBean.getS_id())+"分");


//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 创建 Bundle 来封装数据
//                Bundle bundle = new Bundle();
//                bundle.putString("f_id", ""+food.getF_id());
//                bundle.putString("f_img", food.getF_img());
//                bundle.putString("f_name", food.getF_name());
//                bundle.putString("f_price", ""+food.getF_price());
//                bundle.putString("f_desc", food.getF_desc());
//
//                // 创建新的 Fragment 并设置 Bundle
//                ManageShopUpdateFoodFragment fragment = new ManageShopUpdateFoodFragment();
//                fragment.setArguments(bundle);
//
//                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.manage_shop_frame, fragment);
//                fragmentTransaction.commit();
//            }
//        });

        return convertView;
    }


}
