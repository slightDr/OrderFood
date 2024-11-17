package com.example.orderfood.activity.shop;

import android.graphics.Bitmap;
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
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.R;
import com.example.orderfood.util.FileImgUtil;
import com.example.orderfood.util.PasswordUtil;

import java.util.UUID;

public class RegisterShopActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_register_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 默认头像
        Drawable sImgDrawableDefault = ContextCompat.getDrawable(this, R.drawable.upload_img);
        Bitmap sImgBitmapDefault = ((BitmapDrawable) sImgDrawableDefault).getBitmap();
        // 注册头像
        ImageView sImgView = findViewById(R.id.register_s_img);

        // 初始化文件选择器
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        sImgView.setImageURI(uri);
                        selectPicUri = uri;
                    }
                }
            }
        );

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.register_shop_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 注册内容
        EditText sNameText = findViewById(R.id.register_s_name);
        EditText sPwdText = findViewById(R.id.register_s_pwd);
        EditText sRepwdText = findViewById(R.id.register_s_repwd);
        EditText sDescText = findViewById(R.id.register_s_desc);
        EditText sTypeText = findViewById(R.id.register_s_type);

        // 实现选择头像
        sImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(RegisterShopActivity.this, "上传头像", Toast.LENGTH_SHORT).show();
                openGallery(view);
            }
        });

        // 注册按钮
        Button register_button = findViewById(R.id.register_register_shop_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取出内容
                Drawable sImg = sImgView.getDrawable(); // 获取图片
                String sName = sNameText.getText().toString();
                String sPwd = sPwdText.getText().toString();
                String sRepwd = sRepwdText.getText().toString();
                String sDesc = sDescText.getText().toString();
                String sType = sTypeText.getText().toString();

                // 判断图片文件格式
                if (!(sImg instanceof BitmapDrawable)) {
                    Toast.makeText(RegisterShopActivity.this, "图片格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 判断填入数据合法
                Bitmap sImgBitmap = ((BitmapDrawable) sImg).getBitmap(); // 获取图片二进制格式
                if (sImgBitmap.sameAs(sImgBitmapDefault)) { // 判断是否选择其他头像
                    Toast.makeText(RegisterShopActivity.this, "请添加店铺头像", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sName.isEmpty()) {
                    Toast.makeText(RegisterShopActivity.this, "请输入店铺名字", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sPwd.isEmpty() || sRepwd.isEmpty() || !sPwd.equals(sRepwd)) {
                    Toast.makeText(RegisterShopActivity.this, "请正确输入两次密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sDesc.isEmpty()) {
                    Toast.makeText(RegisterShopActivity.this, "请输入店铺描述", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sType.isEmpty()) {
                    Toast.makeText(RegisterShopActivity.this, "请输入店铺类型", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 保存到数据库
                String picPath = FileImgUtil.getPicAbsPath(); // 获取保存图片的绝对路径
                FileImgUtil.saveImageUriToFile(selectPicUri, RegisterShopActivity.this, picPath); // 保存图片
                sPwd = PasswordUtil.hashPassword(sPwd);
                int rtn = ShopDAO.saveShop(sName, sPwd, sDesc, sType, picPath);
                if (rtn == 0) {
                    Toast.makeText(RegisterShopActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(RegisterShopActivity.this, "注册失败，店铺名冲突", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}














