package com.example.orderfood.activity.user.infoAct;

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
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.DAO.ShopDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.ManageShopActivity;
import com.example.orderfood.activity.user.ManageUserActivity;
import com.example.orderfood.util.FileImgUtil;

public class ManageUserUpdateInfoActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_manage_user_update_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 实现返回功能
        Toolbar toolbar = findViewById(R.id.manage_user_update_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");
        UserBean user = UserDAO.getUserInfoByUid(u_id);
        // 头像
        ImageView imgView = findViewById(R.id.manage_user_update_img);
        imgView.setImageBitmap(BitmapFactory.decodeFile(user.getU_img()));
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
        EditText nameText = findViewById(R.id.manage_user_update_name);
        nameText.setText(user.getU_name());
        RadioButton userMale = findViewById(R.id.manage_user_update_sex_male);
        RadioButton userFemale = findViewById(R.id.manage_user_update_sex_female);
        if ("男".equals(user.getU_sex())) {
            userMale.setChecked(true);
        } else {
            userFemale.setChecked(true);
        }

        Button button = findViewById(R.id.manage_user_update_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable img = imgView.getDrawable(); // 获取图片
                String name = nameText.getText().toString();
                String sex = userMale.isChecked() ? "男" : "女";

                ManageUserUpdateInfoActivity context = ManageUserUpdateInfoActivity.this;

                // 合法性判断
                if (!(img instanceof BitmapDrawable)) {
                    Toast.makeText(context, "图片格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(context, "请输入账号名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 保存到数据库
                String picPath = selectPicUri == null ? user.getU_img() : FileImgUtil.getPicAbsPath(); // 获取保存图片的绝对路径
                if (selectPicUri != null) {
                    FileImgUtil.saveImageUriToFile(selectPicUri, context, picPath); // 保存图片
                }
                int res = UserDAO.updateUser(u_id, name, sex, picPath);
                if (res == 0) {
                    Toast.makeText(context, "修改账号信息成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ManageUserUpdateInfoActivity.this, ManageUserActivity.class);
                    intent.putExtra("state", "my");
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "修改账号信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}