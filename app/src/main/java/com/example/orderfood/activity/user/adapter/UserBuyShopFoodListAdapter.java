package com.example.orderfood.activity.user.adapter;

import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.DAO.CommentDAO;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.foodAct.ManageUserBuyActivity;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UserBuyShopFoodListAdapter extends ArrayAdapter<FoodBean> {

    private List<FoodBean> list;
    private Context context;
    private TextView priceView;

    public UserBuyShopFoodListAdapter(@NonNull Context context, List<FoodBean> list, TextView priceView) {
        super(context, R.layout.list_user_home_food, list);
        this.context = context;
        this.list = list;
        this.priceView = priceView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_user_buy_food_item, viewGroup, false);
        }
//        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
//        String u_id = sharedPreferences.getString("u_id", "1");
        FoodBean food = list.get(position);
//        ShopBean shopBean = ShopDAO.getShopInfoBySid(""+food.getS_id());

        // 商品信息
        ImageView imageView = convertView.findViewById(R.id.user_buy_shop_food_item_img);
        TextView nameText = convertView.findViewById(R.id.user_buy_shop_food_item_name);
        TextView saleText = convertView.findViewById(R.id.user_buy_shop_food_item_monthsale);
        TextView priceText = convertView.findViewById(R.id.user_buy_shop_food_item_price);
        TextView descText = convertView.findViewById(R.id.user_buy_shop_food_item_desc);

        Bitmap bitmap = BitmapFactory.decodeFile(food.getF_img());
        imageView.setImageBitmap(bitmap);
        nameText.setText(food.getF_name());
        saleText.setText("月销售：" + FoodDAO.getMonthSale(""+food.getF_id(), ""+food.getS_id()));
        priceText.setText("价格：" + food.getF_price());
        descText.setText(food.getF_desc());

        // 加减
        ImageView addView = convertView.findViewById(R.id.user_buy_shop_food_item_add);
        ImageView minusView = convertView.findViewById(R.id.user_buy_shop_food_item_minus);
        TextView numView = convertView.findViewById(R.id.user_buy_shop_food_item_num);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = numView.getText().toString();
                int numI = Integer.valueOf(num);
                ++ numI;
                numView.setText(""+numI);

                String price = priceView.getText().toString();
                float priceF = Float.valueOf(price);
                priceF += food.getF_price();
                priceView.setText(""+Math.round(priceF*100.0)/100.0);
            }
        });
        minusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = numView.getText().toString();
                int numI = Integer.valueOf(num);
                numI = numI > 0 ? numI - 1 : numI;
                numView.setText(""+numI);

                String price = priceView.getText().toString();
                float priceF = Float.valueOf(price);
                priceF -= food.getF_price();
                priceView.setText(""+Math.round(priceF*100.0)/100.0);
            }
        });

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ManageUserBuyActivity.class);
//                intent.putExtra("shop", shopBean);
//                getContext().startActivity(intent);
//            }
//        });

        return convertView;
    }


}
