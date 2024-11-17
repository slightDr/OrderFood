package com.example.orderfood.activity.user.foodAct;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import com.example.orderfood.DAO.CommentDAO;
import com.example.orderfood.R;
import com.example.orderfood.util.FileImgUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UserCommentActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_user_comment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String u_id = sharedPreferences.getString("u_id", "1");
        String s_id = getIntent().getStringExtra("s_id");

        // 实现返回功能
        Toolbar toolbar = this.findViewById(R.id.user_comment_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                onBackPressed(); // 跳转
                // finish(); // 关闭
            }
        });

        UserCommentActivity activity = UserCommentActivity.this;
        // 评论内容
        EditText contentText = this.findViewById(R.id.user_comment_content);
        // 修改评论星级
        String strs[] = {"非常差","差","一般","满意","非常满意"};
        int starIds[] = new int[]{
                R.id.user_comment_star_1,
                R.id.user_comment_star_2,
                R.id.user_comment_star_3,
                R.id.user_comment_star_4,
                R.id.user_comment_star_5,
        };
        TextView scoreDescView = this.findViewById(R.id.user_comment_score_desc);

        for (int starId : starIds) {
            ImageView starView = this.findViewById(starId);
            starView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickId = view.getId();
                    int score = 1;
                    String str = "非常差";
                    for (int i = 0; i < 5; ++ i) {
                        if (clickId == starIds[i]) {
                            score = i + 1;
                            str = strs[i];
                            break;
                        }
                    }
                    for (int i = 1; i <= score; ++ i) {
                        ImageView starView = activity.findViewById(starIds[i - 1]);
                        starView.setImageResource(R.drawable.yellow_star);
                    }
                    for (int i = score + 1; i <= 5; ++ i) {
                        ImageView starView = activity.findViewById(starIds[i - 1]);
                        starView.setImageResource(R.drawable.white_star);
                    }
                    scoreDescView.setText(str);
                }
            });
        }

        // 默认图像
        Drawable sImgDrawableDefault = ContextCompat.getDrawable(activity, R.drawable.upload_img);
        Bitmap sImgBitmapDefault = ((BitmapDrawable) sImgDrawableDefault).getBitmap();
        // 初始化文件选择器
        ImageView imgView = this.findViewById(R.id.user_comment_upload_img);
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
        // 设置上传图片
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });

        Button commentButton = this.findViewById(R.id.user_comment_but);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("comment", selectPicUri.toString());
                // 获取内容
                String content = contentText.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(activity, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 获取图片
                Drawable cImg = imgView.getDrawable();
                if (!(cImg instanceof BitmapDrawable)) {
                    Toast.makeText(activity, "图片格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                String picPath = FileImgUtil.getPicAbsPath();
                Bitmap sImgBitmap = ((BitmapDrawable) cImg).getBitmap(); // 获取图片二进制格式
                if (sImgBitmap.sameAs(sImgBitmapDefault)) { // 判断是否选择其他图片
                    Log.d("mine", "select uri "+selectPicUri.toString());
                    Log.d("mine", "default comment img");
                    picPath = "";
                }
                // 获取时间
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                String format = dateFormat.format(date);
                // 获取分数
                String scoreDesc = scoreDescView.getText().toString();
                String score = "5";
                if ("非常满意".equals(scoreDesc)) {
                    score = "5";
                } else if ("满意".equals(scoreDesc)) {
                    score = "4";
                } else if ("一般".equals(scoreDesc)) {
                    score = "3";
                } else if ("差".equals(scoreDesc)) {
                    score = "2";
                } else {
                    score = "1";
                }

                if (!picPath.isEmpty()) {
                    FileImgUtil.saveImageUriToFile(selectPicUri, activity, picPath); // 保存图片
                }
                CommentDAO.insertComment(s_id, u_id, format, content, score, picPath);
                finish();
            }
        });
    }
}