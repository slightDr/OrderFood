package com.example.orderfood.activity.user.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.R;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UserInfolListAdapter extends RecyclerView.Adapter<UserInfolListAdapter.UserInfoViewHolder> {

    private List<UserInfoBean> list;

    public UserInfolListAdapter(List<UserInfoBean> list) {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UserInfoViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView addrText;
        TextView telText;
        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.user_info_name);
            addrText = itemView.findViewById(R.id.uuser_info_addr);
            telText = itemView.findViewById(R.id.user_info_tel);
        }
    }
}
