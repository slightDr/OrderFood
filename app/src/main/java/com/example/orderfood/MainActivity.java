package com.example.orderfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.activity.shop.ManageShopActivity;
import com.example.orderfood.activity.shop.RegisterShopActivity;
import com.example.orderfood.activity.user.ManageUserActivity;
import com.example.orderfood.activity.user.RegisterUserActivity;
import com.example.orderfood.activity.user.infoAct.ManageUserInfoActivity;
import com.example.orderfood.db.DBClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 链接数据库
        DBClient dbClient = new DBClient(this);
        dbClient.connection = dbClient.getWritableDatabase();

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** 调试用 */
//        Intent intent = new Intent(MainActivity.this, ManageUserInfoActivity.class);
//        startActivity(intent);

        // 登陆界面单选默认选择商家
        RadioButton shop_radio = findViewById(R.id.login_as_shop);
        RadioButton user_radio = findViewById(R.id.login_as_user);
        String identity = getIntent().getStringExtra("identity");
        if ("user".equals(identity)) {
            user_radio.setChecked(true);
        } else {
            shop_radio.setChecked(true);
        }

        // 登录功能
        Button login = findViewById(R.id.login_button);
        // 拿到账号、密码、登录选择
        EditText loginNameText = findViewById(R.id.login_name);
        // 默认账号名
        String name = getIntent().getStringExtra("account");
        if (name != null && !name.isEmpty()) {
            loginNameText.setText(name);
        }
        EditText loginPwdText = findViewById(R.id.login_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginName = loginNameText.getText().toString();
                String loginPwd = loginPwdText.getText().toString();
                if (loginName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (loginPwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 登录
                if (shop_radio.isChecked()) { // 商家登录
                    int result = ShopDAO.loginAsShop(loginName, loginPwd);
                    if (result == -1) {
                        Toast.makeText(MainActivity.this, "商家账号或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "商家登录成功", Toast.LENGTH_SHORT).show();
                        editor.putString("s_id", ""+result);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, ManageShopActivity.class);
                        startActivity(intent);
//                        loginNameText.setText(""); // 清空输入栏
                        loginPwdText.setText("");
                    }
                } else { // 用户登录
                    int result = UserDAO.loginAsUser(loginName, loginPwd);
                    if (result == -1) {
                        Toast.makeText(MainActivity.this, "用户账号或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putString("u_id", ""+result);
                        editor.apply();
                        Toast.makeText(MainActivity.this, "用户登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ManageUserActivity.class);
                        startActivity(intent);
                        loginPwdText.setText("");
                    }
                }
            }
        });

        // 注册商家按钮
        Button register_shop = findViewById(R.id.login_register_shop_button);
        register_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转商家注册页面
                Intent intent = new Intent(MainActivity.this, RegisterShopActivity.class);
                startActivity(intent);
            }
        });

        // 注册用户按钮
        Button register_user = findViewById(R.id.login_register_user_button);
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转用户注册页面
                Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }
}