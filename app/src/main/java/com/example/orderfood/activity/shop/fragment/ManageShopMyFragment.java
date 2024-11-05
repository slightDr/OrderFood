package com.example.orderfood.activity.shop.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.ManageShopFinishOrderActivity;
import com.example.orderfood.activity.shop.ManageShopUpdateInfoActivity;
import com.example.orderfood.activity.shop.ManageShopUpdatePwdActivity;

public class ManageShopMyFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_manage_shop_my, container, false);

        /** 展示账号信息 */
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

        FragmentActivity activity = getActivity();

        /** 退出账号功能 */
        TextView exitText = rootView.findViewById(R.id.manage_shop_my_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        /** 注销账号功能 */
        TextView delAccountText = rootView.findViewById(R.id.manage_shop_my_del_account);
        delAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("注销账号");
                builder.setMessage("您确定注销账号吗?这会删除账号相关数据");
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
                        int ret = ShopDAO.delShopBySid(s_id);
                        if (ret == 0) {
                            Toast.makeText(activity, "注销成功", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else {
                            Toast.makeText(activity, "注销失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });

        /** 修改店铺信息功能 */
        TextView editInfoText = rootView.findViewById(R.id.mange_shop_edit_info);
        editInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageShopUpdateInfoActivity.class);
                startActivity(intent);
            }
        });

        /** 修改密码功能 */
        TextView changePwdText = rootView.findViewById(R.id.manage_shop_my_change_pwd);
        changePwdText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageShopUpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        /** 订单管理 */
        Button manageOrderButton = rootView.findViewById(R.id.manage_shop_my_manage_order);
        manageOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageShopFinishOrderActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
