package com.example.orderfood.activity.user.infoAct;

import android.content.Intent;
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

import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;

public class ManageUserUpdateReceiveInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_update_receive_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_user_update_receive_info_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("user_info_id");
        String addr = intent.getStringExtra("user_info_addr");
        String name = intent.getStringExtra("user_info_name");
        String tel = intent.getStringExtra("user_info_tel");

        EditText addrText = findViewById(R.id.manage_user_update_receive_info_addr);
        addrText.setText(addr);
        EditText nameText = findViewById(R.id.manage_user_update_receive_info_name);
        nameText.setText(name);
        EditText telText = findViewById(R.id.manage_user_update_receive_info_tel);
        telText.setText(tel);

        Button editReceiveInfoBut = findViewById(R.id.manage_user_update_receive_info_button);
        editReceiveInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addr = addrText.getText().toString();
                String name = nameText.getText().toString();
                String tel = telText.getText().toString();
                if (addr.isEmpty()) {
                    Toast.makeText(ManageUserUpdateReceiveInfoActivity.this, "请输入收货地址", Toast.LENGTH_SHORT).show();
                    addrText.requestFocus();
                    return;
                } else if (name.isEmpty()) {
                    Toast.makeText(ManageUserUpdateReceiveInfoActivity.this, "请输入收货人昵称", Toast.LENGTH_SHORT).show();
                    nameText.requestFocus();
                    return;
                } else if (tel.isEmpty()) {
                    Toast.makeText(ManageUserUpdateReceiveInfoActivity.this, "请输入收货人联系方式", Toast.LENGTH_SHORT).show();
                    telText.requestFocus();
                    return;
                }

                int res = UserInfoDAO.setUserInfo(id, name, addr, tel);
                if (res != 0) {
                    Toast.makeText(ManageUserUpdateReceiveInfoActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ManageUserUpdateReceiveInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
//                Intent newIntent = new Intent();
//                newIntent.putExtra("user_info_addr", addr);
//                newIntent.putExtra("user_info_name", name);
//                newIntent.putExtra("user_info_tel", tel);
//                finish();
            }
        });
    }
}