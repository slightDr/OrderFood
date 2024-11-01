package com.example.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.activity.shop.RegisterShopActivity;
import com.example.orderfood.activity.user.RegisterUserActivity;
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 登陆界面单选默认选择商家
        RadioButton shop_radio = findViewById(R.id.login_shop);
        shop_radio.setChecked(true);

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