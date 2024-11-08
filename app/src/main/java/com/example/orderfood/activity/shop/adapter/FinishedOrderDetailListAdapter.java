package com.example.orderfood.activity.shop.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.R;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class FinishedOrderDetailListAdapter extends RecyclerView.Adapter<FinishedOrderDetailListAdapter.OrderViewHolder> {

    private List<OrderDetailBean> list;

    public FinishedOrderDetailListAdapter(List<OrderDetailBean> list) {
//        super(context, R.layout.list_finished_order_detail, list);
        this.list = list;
    }

    @NonNull
    @Override
    public FinishedOrderDetailListAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_finished_order_detail, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedOrderDetailListAdapter.OrderViewHolder holder, int position) {
        OrderDetailBean orderDetail = list.get(position);
        // 加载订单详情信息
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(orderDetail.getF_img()));
        holder.nameText.setText(orderDetail.getF_name());
        holder.numText.setText(orderDetail.getO_num()+"份");
        holder.priceText.setText(
                "￥ "+Math.round(orderDetail.getF_price()*orderDetail.getO_num()*100.0)/100.0
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText;
        TextView numText;
        TextView priceText;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.finished_order_detail_list_img);
            nameText = itemView.findViewById(R.id.finished_order_detail_list_name);
            numText = itemView.findViewById(R.id.finished_order_detail_list_num);
            priceText = itemView.findViewById(R.id.finished_order_detail_list_totprice);
        }
    }
}
