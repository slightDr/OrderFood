package com.example.orderfood.activity.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.R;
import com.example.orderfood.activity.shop.fragment.ManageShopAddFragment;
import com.example.orderfood.activity.shop.fragment.ManageShopHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ManageShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 界面打开时，打开主界面
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.manage_shop_frame, new ManageShopHomeFragment());
        fragmentTransaction.commit();

        // 点击底部导航栏
        BottomNavigationView view = findViewById(R.id.shop_bottom_navi);
        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                int id = item.getItemId();
//                Log.d("mine", "item id: "+id);
                if (id == R.id.shop_bottom_navi_home) {
                    fragmentTransaction.replace(R.id.manage_shop_frame, new ManageShopHomeFragment());
                } else if (id == R.id.shop_bottom_navi_add) {
                    fragmentTransaction.replace(R.id.manage_shop_frame, new ManageShopAddFragment());
                } else {
                    fragmentTransaction.replace(R.id.manage_shop_frame, new ManageShopHomeFragment());
                }
                fragmentTransaction.commit();

                return true;
            }
        });
    }








}