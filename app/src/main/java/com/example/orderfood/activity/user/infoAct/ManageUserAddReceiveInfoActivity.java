package com.example.orderfood.activity.user.infoAct;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;

public class ManageUserAddReceiveInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_add_receive_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_user_add_receive_info_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        Intent intent = getIntent();

        EditText addrText = findViewById(R.id.manage_user_add_receive_info_addr);
        EditText nameText = findViewById(R.id.manage_user_add_receive_info_name);
        EditText telText = findViewById(R.id.manage_user_add_receive_info_tel);

        Button editReceiveInfoBut = findViewById(R.id.manage_user_add_receive_info_button);
        editReceiveInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addr = addrText.getText().toString();
                String name = nameText.getText().toString();
                String tel = telText.getText().toString();
                if (addr.isEmpty()) {
                    Toast.makeText(ManageUserAddReceiveInfoActivity.this, "请输入收货地址", Toast.LENGTH_SHORT).show();
                    addrText.requestFocus();
                    return;
                } else if (name.isEmpty()) {
                    Toast.makeText(ManageUserAddReceiveInfoActivity.this, "请输入收货人昵称", Toast.LENGTH_SHORT).show();
                    nameText.requestFocus();
                    return;
                } else if (tel.isEmpty()) {
                    Toast.makeText(ManageUserAddReceiveInfoActivity.this, "请输入收货人联系方式", Toast.LENGTH_SHORT).show();
                    telText.requestFocus();
                    return;
                }

                int res = UserInfoDAO.addUserInfo(u_id, name, addr, tel);
                if (res != 0) {
                    Toast.makeText(ManageUserAddReceiveInfoActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ManageUserAddReceiveInfoActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}