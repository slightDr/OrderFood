package com.example.orderfood.activity.user.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.dialog.UserBuyFoodDialog;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UserInfolListAdapter extends RecyclerView.Adapter<UserInfolListAdapter.UserInfoViewHolder> {

    private List<UserInfoBean> list;
    private View parent;

    public UserInfolListAdapter(View parent, List<UserInfoBean> list) {
        this.parent = parent;
        this.list = list;
    }

    @NonNull
    @Override
    public UserInfolListAdapter.UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_info, parent, false);
        return new UserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfolListAdapter.UserInfoViewHolder holder, int position) {
        UserInfoBean userInfo = list.get(position);
        // 加载订单详情信息
        holder.nameText.setText(userInfo.getIName());
        holder.addrText.setText(userInfo.getIAddr());
        holder.telText.setText(userInfo.getITel());

        // 加载上级父亲的收货信息
        TextView receiveNameText = parent.findViewById(R.id.buy_user_receive_name);
        TextView receiveAddrText = parent.findViewById(R.id.buy_user_receive_addr);
        TextView receiveTelText = parent.findViewById(R.id.buy_user_receive_tel);
        TextView chosenIdText = parent.findViewById(R.id.chosen_i_id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiveNameText.setText(userInfo.getIName());
                receiveAddrText.setText(userInfo.getIAddr());
                receiveTelText.setText(userInfo.getITel());
                chosenIdText.setText(""+userInfo.getIId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UserInfoViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView addrText;
        TextView telText;
        View itemView;
        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.user_info_name);
            addrText = itemView.findViewById(R.id.uuser_info_addr);
            telText = itemView.findViewById(R.id.user_info_tel);
            this.itemView = itemView;
        }
    }
}
