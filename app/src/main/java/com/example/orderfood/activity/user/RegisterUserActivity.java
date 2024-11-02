package com.example.orderfood.activity.user;
/**
 * 注册界面活动
 */

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.RegisterUserActivity;
import com.example.orderfood.util.FileImgUtil;

public class RegisterUserActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 默认性别
        RadioButton regUserMale = findViewById(R.id.register_u_sex_male);
        // RadioButton regUserFemale = findViewById(R.id.register_u_sex_female);
        regUserMale.setChecked(true);

        // 默认头像
        Drawable sImgDrawableDefault = ContextCompat.getDrawable(this, R.drawable.upload_img);
        Bitmap sImgBitmapDefault = ((BitmapDrawable) sImgDrawableDefault).getBitmap();
        // 注册头像
        ImageView uImgView = findViewById(R.id.register_u_img);

        // 初始化文件选择器
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            uImgView.setImageURI(uri);
                            selectPicUri = uri;
                        }
                    }
                }
        );

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.register_user_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        // 注册内容
        EditText uNameText = findViewById(R.id.register_u_name);
        EditText uPwdText = findViewById(R.id.register_u_pwd);
        EditText uRepwdText = findViewById(R.id.register_u_repwd);
        EditText uAddrText = findViewById(R.id.register_u_addr);
        EditText uTelText = findViewById(R.id.register_u_tel);

        // 实现选择头像
        uImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(RegisterUserActivity.this, "上传头像", Toast.LENGTH_SHORT).show();
                openGallery(view);
            }
        });

        // 注册按钮
        Button register_button = findViewById(R.id.register_register_user_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取出内容
                Drawable uImg = uImgView.getDrawable(); // 获取图片
                String uName = uNameText.getText().toString();
                String uPwd = uPwdText.getText().toString();
                String uRepwd = uRepwdText.getText().toString();
                String uSex = regUserMale.isChecked() ? "男" : "女";
                String uAddr = uAddrText.getText().toString();
                String uTel = uTelText.getText().toString();

                // 判断图片文件格式
                if (!(uImg instanceof  BitmapDrawable)) {
                    Toast.makeText(RegisterUserActivity.this, "图片格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 判断填入数据合法
                Bitmap sImgBitmap = ((BitmapDrawable) uImg).getBitmap(); // 获取图片二进制格式
                if (sImgBitmap.sameAs(sImgBitmapDefault)) { // 判断是否选择其他头像
                    Toast.makeText(RegisterUserActivity.this, "请添加用户头像", Toast.LENGTH_SHORT).show();
                    return;
                } else if (uName.isEmpty()) {
                    Toast.makeText(RegisterUserActivity.this, "请输入用户名字", Toast.LENGTH_SHORT).show();
                    return;
                } else if (uPwd.isEmpty() || uRepwd.isEmpty() || !uPwd.equals(uRepwd)) {
                    Toast.makeText(RegisterUserActivity.this, "请正确输入两次密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (uAddr.isEmpty()) {
                    Toast.makeText(RegisterUserActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                    return;
                } else if (uTel.isEmpty()) {
                    Toast.makeText(RegisterUserActivity.this, "请输入联系方式", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 保存到数据库
                String picPath = FileImgUtil.getPicAbsPath(); // 获取保存图片的绝对路径
                FileImgUtil.saveImageUriToFile(selectPicUri, RegisterUserActivity.this, picPath); // 保存图片
                int rtn = UserDAO.saveUser(uName, uPwd, uSex, uAddr, uTel, picPath);
                if (rtn == 0) {
                    Toast.makeText(RegisterUserActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(RegisterUserActivity.this, "注册失败，用户名冲突", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}