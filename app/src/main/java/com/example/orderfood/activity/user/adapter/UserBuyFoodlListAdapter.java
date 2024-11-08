package com.example.orderfood.activity.user.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.R;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UserBuyFoodlListAdapter extends RecyclerView.Adapter<UserBuyFoodlListAdapter.BuyFoodViewHolder> {

    private List<FoodBean> list;
    private List<Integer> num_list;

    public UserBuyFoodlListAdapter(List<FoodBean> list, List<Integer> num_list) {
        this.list = list;
        this.num_list = num_list;
    }

    @NonNull
    @Override
    public UserBuyFoodlListAdapter.BuyFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_buy_food, parent, false);
        return new BuyFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserBuyFoodlListAdapter.BuyFoodViewHolder holder, int position) {
        FoodBean food = list.get(position);
        Integer num = num_list.get(position);
        // 加载订单详情信息
        holder.imgView.setImageBitmap(BitmapFactory.decodeFile(food.getF_img()));
        holder.nameText.setText(food.getF_name());
        holder.numText.setText(num+"份");
        holder.priceText.setText(
                "￥ " + Math.round(num * food.getF_price() * 100.0) / 100.0
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class BuyFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView nameText;
        TextView numText;
        TextView priceText;
        public BuyFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.user_buy_dialog_food_img);
            nameText = itemView.findViewById(R.id.user_buy_dialog_food_name);
            numText = itemView.findViewById(R.id.user_buy_dialog_food_num);
            priceText = itemView.findViewById(R.id.user_buy_dialog_food_totprice);
        }
    }
}
