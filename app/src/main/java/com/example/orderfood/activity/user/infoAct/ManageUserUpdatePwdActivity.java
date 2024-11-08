package com.example.orderfood.activity.user.infoAct;

import android.content.Context;
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

import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.R;

public class ManageUserUpdatePwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_update_pwd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_user_update_pwd_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        SharedPreferences sharedPreferences = ManageUserUpdatePwdActivity.this
                .getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");

        EditText pwdText = findViewById(R.id.manage_user_update_pwd);
        EditText repwdText = findViewById(R.id.manage_user_update_repwd);

        Button button = findViewById(R.id.manage_user_update_pwd_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = pwdText.getText().toString();
                String repwd = repwdText.getText().toString();
                
                if (pwd.isEmpty() || repwd.isEmpty() || !pwd.equals(repwd)) {
                    Toast.makeText(ManageUserUpdatePwdActivity.this, "请正确输入两次密码", Toast.LENGTH_SHORT).show();
                    pwdText.requestFocus();
                    return;
                }

                int res = UserDAO.updateUserPwd(u_id, pwd);
                if (res == 0) {
                    Toast.makeText(ManageUserUpdatePwdActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                    pwdText.setText("");
                    repwdText.setText("");
                    finish();
                } else {
                    Toast.makeText(ManageUserUpdatePwdActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}