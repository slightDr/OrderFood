package com.example.orderfood.activity.shop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.fragment.ManageShopUpdateFoodFragment;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UnfinishOrderListAdapter extends ArrayAdapter<OrderBean> {

    private List<OrderBean> list;
    private Context context;

    public UnfinishOrderListAdapter(@NonNull Context context, List<OrderBean> list) {
        super(context, R.layout.list_manage_shop_unfinish_order, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_manage_shop_unfinish_order, viewGroup, false);
        }
        OrderBean order = list.get(position);
        UserBean user = UserDAO.getUserInfoByUid(""+order.getU_id());
        UserInfoBean userInfo = UserInfoDAO.getUserInfoByIid(""+order.getI_id());

        // 加载订单信息
        ImageView imageView = convertView.findViewById(R.id.unfinish_order_user_img);
        imageView.setImageBitmap(BitmapFactory.decodeFile(user.getU_img()));
        TextView oidText = convertView.findViewById(R.id.unfinish_order_id);
        oidText.setText("订单编号："+order.getO_id());
        TextView timeText = convertView.findViewById(R.id.unfinish_order_time);
        timeText.setText(order.getO_time());
        TextView nameText = convertView.findViewById(R.id.unfinish_order_user_name);
        nameText.setText(userInfo.getIName());
        TextView addrText = convertView.findViewById(R.id.unfinish_order_user_addr);
        addrText.setText(userInfo.getIAddr());
        TextView telText = convertView.findViewById(R.id.unfinish_order_user_tel);
        telText.setText(userInfo.getITel());

        // 加载订单详细信息
        RecyclerView detailList = convertView.findViewById(R.id.unfinish_order_detail_list);
        detailList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<OrderDetailBean> detailBeans = OrderDAO.getOrderDetailsByOid(""+order.getO_id());
        UnfinishOrderDetailListAdapter detailListAdapter = new UnfinishOrderDetailListAdapter(detailBeans);
        if (detailBeans.isEmpty()) {
            detailList.setAdapter(null);
        } else {
            detailList.setAdapter(detailListAdapter);
        }
        detailListAdapter.notifyDataSetChanged();

        // 计算总价
        float totPrice = 0.0f;
        for (OrderDetailBean detailBean : detailBeans) {
            totPrice += detailBean.getF_price() * detailBean.getO_num();
        }
        TextView totPriceView = convertView.findViewById(R.id.unfinish_order_list_totprice);
        totPriceView.setText("￥ "+totPrice);

        // 取消订单
        Button cancelButton = convertView.findViewById(R.id.unfinish_order_list_cancel_but);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = OrderDAO.updateOrderStatus(""+order.getO_id(), "2");
                if (res == 0) {
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "取消成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "取消失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 完成订单
        Button finishButton = convertView.findViewById(R.id.unfinish_order_list_finish_but);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = OrderDAO.updateOrderStatus(""+order.getO_id(), "3");
                if (res == 0) {
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "完成成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "完成失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }


}
