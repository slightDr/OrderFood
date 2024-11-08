package com.example.orderfood.activity.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.R;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class ManageUserInfolListAdapter extends RecyclerView.Adapter<ManageUserInfolListAdapter.ManageUserInfoViewHolder> {

    private List<UserInfoBean> list;

    public ManageUserInfolListAdapter(List<UserInfoBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ManageUserInfolListAdapter.ManageUserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_manage_user_info_item, parent, false);
        return new ManageUserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageUserInfolListAdapter.ManageUserInfoViewHolder holder, int position) {
        UserInfoBean userInfo = list.get(position);
        // 加载订单详情信息
        holder.nameText.setText(userInfo.getIName());
        holder.addrText.setText(userInfo.getIAddr());
        holder.telText.setText(userInfo.getITel());
        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "修改信息", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ManageUserInfoViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView addrText;
        TextView telText;
        ImageView editImage;
        View itemView;
        public ManageUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.manage_user_info_name);
            addrText = itemView.findViewById(R.id.manage_user_info_addr);
            telText = itemView.findViewById(R.id.manage_user_info_tel);
            editImage = itemView.findViewById(R.id.manage_user_info_edit);
            this.itemView = itemView;
        }
    }
}
