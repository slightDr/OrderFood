package com.example.orderfood.activity.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.DAO.CommentDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.adapter.CommentListAdapter;

import java.util.List;

public class ManageShopManageCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_shop_manage_comment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String s_id = sharedPreferences.getString("s_id", "1");

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_shop_manage_comment_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 适配器
        ListView listView = findViewById(R.id.manage_shop_comment_listview);
        List<CommentBean> comments = CommentDAO.getCommentsBySid(s_id);
        if (comments.isEmpty()) {
            listView.setAdapter(null);
        } else {
            listView.setAdapter(new CommentListAdapter(this, comments));
        }
    }
}