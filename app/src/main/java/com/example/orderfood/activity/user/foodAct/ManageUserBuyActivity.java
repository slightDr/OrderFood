package com.example.orderfood.activity.user.foodAct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.orderfood.activity.user.fragment.ManageUserBuyShopFoodFragment;
import com.example.orderfood.activity.user.fragment.ManageUserCommentFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;

public class ManageUserBuyActivity extends AppCompatActivity {

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
    }
}