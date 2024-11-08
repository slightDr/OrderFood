package com.example.orderfood.activity.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.FinishedOrderDetailListAdapter;
import com.example.orderfood.activity.user.foodAct.ManageUserBuyActivity;

import java.util.List;

/**
 * 显示商家商品列表
 */
public class UserFinishedOrderListAdapter extends ArrayAdapter<OrderBean> {

    private List<OrderBean> list;
    private Context context;

    public UserFinishedOrderListAdapter(@NonNull Context context, List<OrderBean> list) {
        super(context, R.layout.list_manage_user_finished_order, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_manage_user_finished_order, viewGroup, false);
        }
        OrderBean order = list.get(position);
        ShopBean shop = ShopDAO.getShopInfoBySid(""+order.getS_id());
        UserInfoBean userInfo = UserInfoDAO.getUserInfoByIid(""+order.getI_id());

        // 加载订单信息
        ImageView imageView = convertView.findViewById(R.id.user_finished_order_shop_img);
        imageView.setImageBitmap(BitmapFactory.decodeFile(shop.getS_img()));
        TextView oidText = convertView.findViewById(R.id.user_finished_order_id);
        oidText.setText("订单编号："+order.getO_id());
        TextView timeText = convertView.findViewById(R.id.user_finished_order_time);
        timeText.setText(order.getO_time());
        TextView nameText = convertView.findViewById(R.id.user_finished_order_user_name);
        nameText.setText(userInfo.getIName());
        TextView addrText = convertView.findViewById(R.id.user_finished_order_user_addr);
        addrText.setText(userInfo.getIAddr());
        TextView telText = convertView.findViewById(R.id.user_finished_order_user_tel);
        telText.setText(userInfo.getITel());

        // 加载订单详细信息
        RecyclerView detailList = convertView.findViewById(R.id.user_finished_order_detail_list);
        detailList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<OrderDetailBean> detailBeans = OrderDAO.getOrderDetailsByOid(""+order.getO_id());
        FinishedOrderDetailListAdapter detailListAdapter = new FinishedOrderDetailListAdapter(detailBeans);
        if (detailBeans.isEmpty()) {
            detailList.setAdapter(null);
        } else {
            detailList.setAdapter(detailListAdapter);
        }
        detailListAdapter.notifyDataSetChanged();

        // 订单状态
        TextView statusText = convertView.findViewById(R.id.user_finished_order_status);
        statusText.setText(
                order.getO_status() == 2 ? "订单已取消" : "订单已完成"
        );

        // 计算总价
        float totPrice = 0.0f;
        for (OrderDetailBean detailBean : detailBeans) {
            totPrice += detailBean.getF_price() * detailBean.getO_num();
        }
        TextView totPriceView = convertView.findViewById(R.id.user_finished_order_list_totprice);
        totPriceView.setText("￥ "+totPrice);

        // 评论按钮
        Button commentBut = convertView.findViewById(R.id.finished_order_comment_but);
        commentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageUserBuyActivity.class);
                intent.putExtra("state", "comment");
                intent.putExtra("shop", shop);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


}
