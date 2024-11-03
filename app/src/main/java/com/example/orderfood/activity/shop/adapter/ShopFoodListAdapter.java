package com.example.orderfood.activity.shop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.R;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class ShopFoodListAdapter extends ArrayAdapter<FoodBean> {

    private List<FoodBean> list;
    private Context context;

    public ShopFoodListAdapter(@NonNull Context context, List<FoodBean> list) {
        super(context, R.layout.list_shop_home_food, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_shop_home_food, viewGroup, false);
        }
        FoodBean food = list.get(position);

        ImageView imageView = convertView.findViewById(R.id.shop_food_list_foodImg);
        TextView nameText = convertView.findViewById(R.id.shop_food_list_foodName);
        TextView saleText = convertView.findViewById(R.id.shop_food_list_foodSale);
        TextView priceText = convertView.findViewById(R.id.shop_food_list_foodPrice);
        TextView descText = convertView.findViewById(R.id.shop_food_list_foodDesc);

        Bitmap bitmap = BitmapFactory.decodeFile(food.getF_img());
        imageView.setImageBitmap(bitmap);
        nameText.setText(food.getF_name());
        saleText.setText("月销售：" + FoodDAO.getMonthSale(food.getF_id()));
        priceText.setText("价格：" + food.getF_price());
        descText.setText(food.getF_desc());

        return convertView;
    }


}
