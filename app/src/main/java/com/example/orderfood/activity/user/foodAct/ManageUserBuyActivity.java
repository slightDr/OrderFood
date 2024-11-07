package com.example.orderfood.activity.user.foodAct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.fragment.ManageShopHomeFragment;
import com.example.orderfood.activity.shop.fragment.ManageShopMyFragment;
import com.example.orderfood.activity.user.RegisterUserActivity;
import com.example.orderfood.activity.user.dialog.UserBuyFoodDialog;
import com.example.orderfood.activity.user.fragment.ManageUserBuyShopFoodFragment;
import com.example.orderfood.activity.user.fragment.ManageUserCommentFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;

public class ManageUserBuyActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    public JSONObject foodJson;  // 购买商品和对应数量

    public ManageUserBuyActivity() {
        super();
        // 存储选择的商品和数量
        if (foodJson == null) {
            foodJson = new JSONObject();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_buy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        ShopBean shop = (ShopBean)intent.getSerializableExtra("shop");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.user_buy_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });
        // 商店信息
        ImageView imageView = findViewById(R.id.user_buy_shop_img);
        imageView.setImageBitmap(BitmapFactory.decodeFile(shop.getS_img()));
        TextView nameText = findViewById(R.id.user_buy_shop_name);
        nameText.setText(shop.getS_name());
        TextView descText = findViewById(R.id.user_buy_shop_desc);
        descText.setText(shop.getS_desc());

        // 实现显示总价
        TextView priceView = findViewById(R.id.user_buy_shop_food_total_price);
        priceView.setText("0.00");

        // 选项卡
        TabLayout tabLayout = findViewById(R.id.user_buy_tab);
        ViewPager2 viewPager = findViewById(R.id.user_buy_pager);
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return new ManageUserBuyShopFoodFragment(""+shop.getS_id(), priceView);
                } else {
                    return new ManageUserCommentFragment(""+shop.getS_id());
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            if (position == 0) {
                tab.setText("点餐");
            } else {
                tab.setText("评论");
            }
        })).attach();

        // 点击结算按钮
        Button buyButton = findViewById(R.id.user_buy_shop_food_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean has_bought = false;
                for (Iterator<String> it = foodJson.keys(); it.hasNext(); ) {
                    String key = it.next();
                    try {
                        if (foodJson.getInt(key) != 0) {
                            has_bought = true;
                            break;
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!has_bought) {
                    Toast.makeText(ManageUserBuyActivity.this, "未选择任何商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserBuyFoodDialog userBuyFoodDialog = new UserBuyFoodDialog(ManageUserBuyActivity.this,
                        ""+shop.getS_id(), foodJson, priceView.getText().toString());
                Log.d("user", foodJson.toString());
            }
        });
    }
}