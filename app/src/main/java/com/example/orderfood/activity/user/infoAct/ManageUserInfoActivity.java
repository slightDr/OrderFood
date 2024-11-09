package com.example.orderfood.activity.user.infoAct;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.adapter.ManageUserInfolListAdapter;

import java.util.List;

public class ManageUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_user_info_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 加载信息列表
        RecyclerView userInfoList = findViewById(R.id.manage_user_info_list);
        userInfoList.setLayoutManager(new LinearLayoutManager(this));
        List<UserInfoBean> userInfos = UserInfoDAO.getAllUserInfosByUid(u_id);
        if (userInfos.isEmpty()) {
            userInfoList.setAdapter(null);
        } else {
            userInfoList.setAdapter(new ManageUserInfolListAdapter(this, userInfos));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_receive_info_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.user_manage_receive_info_add) {
            Intent intent = new Intent(this, ManageUserAddReceiveInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.user_manage_receive_info_flush) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}