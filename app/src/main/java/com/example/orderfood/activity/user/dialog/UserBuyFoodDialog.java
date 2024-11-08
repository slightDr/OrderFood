package com.example.orderfood.activity.user.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.adapter.UserBuyFoodlListAdapter;
import com.example.orderfood.activity.user.adapter.UserInfolListAdapter;
import com.example.orderfood.activity.user.foodAct.ManageUserBuyActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UserBuyFoodDialog {

    private Context context;
    private ManageUserBuyActivity activity;
    private String s_id;
    private JSONObject foodJson;

    public UserBuyFoodDialog(Context context, String s_id, JSONObject foodJson) {
        this.activity = (ManageUserBuyActivity) context;
        this.context = context;
        this.s_id = s_id;
        this.foodJson = foodJson;
        init();
    }

    private void init() {
        View buyDialog = activity.getLayoutInflater().inflate(R.layout.dialog_user_buy_shop_food, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(buyDialog);
        bottomSheetDialog.show();

        // 实现加载信息
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");
        UserBean user = UserDAO.getUserInfoByUid(u_id);

        ImageView uImgView = buyDialog.findViewById(R.id.buy_user_img);
        uImgView.setImageBitmap(BitmapFactory.decodeFile(user.getU_img()));
        TextView nameText = buyDialog.findViewById(R.id.buy_user_name);
        nameText.setText(user.getU_name());
        // 设置时间
        TextView timeText = buyDialog.findViewById(R.id.buy_order_time);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String format = dateFormat.format(date);
        timeText.setText(format);
        TextView receiveNameText = buyDialog.findViewById(R.id.buy_user_receive_name);
        receiveNameText.setText("");
        TextView receiveaddrText = buyDialog.findViewById(R.id.buy_user_receive_addr);
        receiveaddrText.setText("");
        TextView receivetelText = buyDialog.findViewById(R.id.buy_user_receive_tel);
        receivetelText.setText("");

        // 收货信息
        RecyclerView infoListView = buyDialog.findViewById(R.id.buy_user_info_list);
        infoListView.setLayoutManager(new LinearLayoutManager(context));
        List<UserInfoBean> userInfoList = UserInfoDAO.getAllUserInfosByUid(u_id);
        if (userInfoList.isEmpty()) {
            infoListView.setAdapter(null);
        } else {
            infoListView.setAdapter(new UserInfolListAdapter(buyDialog, userInfoList));
        }

        // 订单商品信息
        RecyclerView listView = buyDialog.findViewById(R.id.buy_foods_list);
        listView.setLayoutManager(new LinearLayoutManager(context));
        List<FoodBean> foods = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        // 获取所有商品信息(外部往内传)
        for (Iterator<String> it = foodJson.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                if (foodJson.getInt(key) != 0) {
                    foods.add(FoodDAO.getFoodByFid(key));
                    nums.add(foodJson.getInt(key));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        if (foods.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new UserBuyFoodlListAdapter(foods, nums));
        }

        // 显示总价
        TextView priceView = activity.findViewById(R.id.user_buy_shop_food_total_price);
        TextView currPriceView = buyDialog.findViewById(R.id.buy_order_totprice);
        currPriceView.setText(priceView.getText().toString());

        // 取消支付
        Button cancelButton = buyDialog.findViewById(R.id.buy_order_cancel_pay_but);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.cancel();
            }
        });

        // 支付
        Button payButton = buyDialog.findViewById(R.id.buy_order_pay_but);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView iIdText = buyDialog.findViewById(R.id.chosen_i_id);
                String iId = iIdText.getText().toString();
                if (iId.isEmpty()) {
                    Toast.makeText(context, "请选择收货信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                String time = timeText.getText().toString();
                // 插入订单
                int o_id = OrderDAO.insertOrder(time, s_id, u_id, "1", iId);
                if (o_id == -1) {
                    Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 插入详情
                for (int i = 0; i < foods.size(); ++ i) {
                    FoodBean food = foods.get(i);
                    int num = nums.get(i);
                    OrderDAO.insertOrderDetail(""+o_id, ""+food.getF_id(), food.getF_name(),
                           food.getF_desc(), ""+food.getF_price(), food.getF_img(), ""+num);
                }
                Toast.makeText(context, "下单成功", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.cancel();
            }
        });
    }

}
