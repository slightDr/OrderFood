package com.example.orderfood.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.R;
import com.example.orderfood.activity.user.fragment.ManageUserHomeFragment;
import com.example.orderfood.activity.user.fragment.ManageUserMyFragment;
import com.example.orderfood.activity.user.fragment.ManageUserUnfinishedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ManageUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BottomNavigationView naviView = findViewById(R.id.user_bottom_navi); // 底部导航栏

        // 界面打开时，根据state打开界面
        if (state == null || state.isEmpty()) {
            fragmentTransaction.replace(R.id.manage_user_frame, new ManageUserHomeFragment());
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.replace(R.id.manage_user_frame, new ManageUserHomeFragment());
            fragmentTransaction.commit();
        }

        // 点击底部导航栏
        naviView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                int id = item.getItemId();
//                Log.d("mine", "item id: "+id);
                if (id == R.id.user_bottom_navi_home) {
                    fragmentTransaction.replace(R.id.manage_user_frame, new ManageUserHomeFragment());
                } else if (id == R.id.user_bottom_navi_waiting) {
                    fragmentTransaction.replace(R.id.manage_user_frame, new ManageUserUnfinishedFragment());
                } else {
                    fragmentTransaction.replace(R.id.manage_user_frame, new ManageUserMyFragment());
                }
                fragmentTransaction.commit();

                return true;
            }
        });
    }
}