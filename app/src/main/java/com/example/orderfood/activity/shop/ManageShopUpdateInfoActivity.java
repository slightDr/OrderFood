package com.example.orderfood.activity.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.ShopBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.R;
import com.example.orderfood.util.FileImgUtil;

public class ManageShopUpdateInfoActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;
    Uri selectPicUri = null;

    /**
     * 打开文件选择器
     * @param v
     */
    private void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_shop_update_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_shop_update_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String s_id = sharedPreferences.getString("s_id", "");
        ShopBean shopBean = ShopDAO.getShopInfoBySid(s_id);
        // 头像
        ImageView imgView = findViewById(R.id.manage_shop_update_img);
        Drawable sImgDrawableDefault = ContextCompat.getDrawable(this, R.drawable.upload_img);
        Bitmap sImgBitmapDefault = ((BitmapDrawable) sImgDrawableDefault).getBitmap();
        imgView.setImageBitmap(
                shopBean.getS_img().isEmpty() ? sImgBitmapDefault : BitmapFactory.decodeFile(shopBean.getS_img())
        );
        // 初始化文件选择器
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            imgView.setImageURI(uri);
                            selectPicUri = uri;
                        }
                    }
                }
        );
        // 实现选择头像
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });
        EditText nameText = findViewById(R.id.manage_shop_update_name);
        nameText.setText(shopBean.getS_name());
        EditText descText = findViewById(R.id.manage_shop_update_desc);
        descText.setText(shopBean.getS_desc());
        EditText typeText = findViewById(R.id.manage_shop_update_type);
        typeText.setText(shopBean.getS_type());

        Button button = findViewById(R.id.manage_shop_update_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable img = imgView.getDrawable(); // 获取图片
                String name = nameText.getText().toString();
                String type = typeText.getText().toString();
                String desc = descText.getText().toString();
                ManageShopUpdateInfoActivity context = ManageShopUpdateInfoActivity.this;

                // 合法性判断
                if (!(img instanceof BitmapDrawable)) {
                    Toast.makeText(context, "图片格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(context, "请输入店铺名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (desc.isEmpty()) {
                    Toast.makeText(context, "请输入店铺描述", Toast.LENGTH_SHORT).show();
                    return;
                } else if (type.isEmpty()) {
                    Toast.makeText(context, "请输入店铺类型", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 保存到数据库
                String picPath = selectPicUri == null ? shopBean.getS_img() : FileImgUtil.getPicAbsPath(); // 获取保存图片的绝对路径
                if (selectPicUri != null) {
                    FileImgUtil.saveImageUriToFile(selectPicUri, context, picPath); // 保存图片
                }
                int res = ShopDAO.updateShop(s_id, name, desc, type, picPath);
                if (res == 0) {
                    Toast.makeText(context, "修改店铺信息成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ManageShopUpdateInfoActivity.this, ManageShopActivity.class);
                    intent.putExtra("state", "my");
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "修改店铺信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}