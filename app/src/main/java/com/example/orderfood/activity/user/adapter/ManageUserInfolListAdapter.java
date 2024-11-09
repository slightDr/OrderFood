package com.example.orderfood.activity.user.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.infoAct.ManageUserInfoActivity;
import com.example.orderfood.activity.user.infoAct.ManageUserUpdateReceiveInfoActivity;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class ManageUserInfolListAdapter extends RecyclerView.Adapter<ManageUserInfolListAdapter.ManageUserInfoViewHolder> {

    private List<UserInfoBean> list;
    ManageUserInfoActivity activity;

    public ManageUserInfolListAdapter(ManageUserInfoActivity activity, List<UserInfoBean> list) {
        this.list = list;
        this.activity = activity;
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
                Intent intent = new Intent(activity, ManageUserUpdateReceiveInfoActivity.class);
                intent.putExtra("user_info_id", ""+userInfo.getIId());
                intent.putExtra("user_info_addr", userInfo.getIAddr());
                intent.putExtra("user_info_name", userInfo.getIName());
                intent.putExtra("user_info_tel", userInfo.getITel());

                activity.startActivity(intent);
            }
        });
        holder.delImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("删除信息");
                builder.setMessage("您确定删除信息吗?");
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int ret = UserInfoDAO.delById(""+userInfo.getIId());
                        if (ret == 0) {
                            Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
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
        ImageView delImage;
        View itemView;
        public ManageUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.manage_user_info_name);
            addrText = itemView.findViewById(R.id.manage_user_info_addr);
            telText = itemView.findViewById(R.id.manage_user_info_tel);
            editImage = itemView.findViewById(R.id.manage_user_info_edit);
            delImage = itemView.findViewById(R.id.manage_user_info_delete);
            this.itemView = itemView;
        }
    }
}
