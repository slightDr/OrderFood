package com.example.orderfood.activity.user.fragment;

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
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.ManageShopFinishOrderActivity;
import com.example.orderfood.activity.shop.ManageShopManageCommentActivity;
import com.example.orderfood.activity.shop.ManageShopShowFinishedOrderActivity;
import com.example.orderfood.activity.shop.ManageShopUpdateInfoActivity;
import com.example.orderfood.activity.shop.ManageShopUpdatePwdActivity;
import com.example.orderfood.activity.user.infoAct.ManageUserShowFinishedOrderActivity;
import com.example.orderfood.activity.user.infoAct.ManageUserUpdateInfoActivity;
import com.example.orderfood.activity.user.infoAct.ManageUserUpdatePwdActivity;

public class ManageUserMyFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_manage_user_my, container, false);

        /** 展示账号信息 */
        // 账号
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");
        UserBean user = UserDAO.getUserInfoByUid(u_id);
        // 头像
        ImageView imgView = rootView.findViewById(R.id.manage_user_my_avatar);
        imgView.setImageBitmap(BitmapFactory.decodeFile(user.getU_img()));
        // id
        TextView idText = rootView.findViewById(R.id.manage_user_my_id);
        idText.setText("id: "+user.getU_id());
        // name
        TextView nameText = rootView.findViewById(R.id.manage_user_my_name);
        nameText.setText(user.getU_name());
        // sex
        TextView descText = rootView.findViewById(R.id.manage_user_my_sex);
        descText.setText("性别："+user.getU_sex());

        FragmentActivity activity = getActivity();

        /** 退出账号功能 */
        TextView exitText = rootView.findViewById(R.id.manage_user_my_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        /** 注销账号功能 */
        TextView delAccountText = rootView.findViewById(R.id.manage_user_my_del_account);
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
                        int ret = UserDAO.delUserByUid(u_id);
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

        /** 修改密码功能 */
        TextView changePwdText = rootView.findViewById(R.id.manage_user_my_change_pwd);
        changePwdText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageUserUpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        /** 修改个人信息功能 */
        TextView editInfoText = rootView.findViewById(R.id.mange_user_edit_info);
        editInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageUserUpdateInfoActivity.class);
                startActivity(intent);
            }
        });

//        /** 收货信息管理 */
//        Button manageOrderButton = rootView.findViewById(R.id.manage_shop_my_manage_order);
//        manageOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ManageShopFinishOrderActivity.class);
//                startActivity(intent);
//            }
//        });
//
        /** 完成订单 */
        TextView finishedOrderButton = rootView.findViewById(R.id.mange_user_finished_order);
        finishedOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageUserShowFinishedOrderActivity.class);
                startActivity(intent);
            }
        });
//
//        /** 评论管理 */
//        Button manageCommentButton = rootView.findViewById(R.id.manage_shop_my_manage_comment);
//        manageCommentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ManageShopManageCommentActivity.class);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }
}
