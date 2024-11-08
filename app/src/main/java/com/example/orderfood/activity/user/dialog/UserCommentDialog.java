package com.example.orderfood.activity.user.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.Bean.UserInfoBean;
import com.example.orderfood.DAO.CommentDAO;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.DAO.OrderDAO;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.DAO.UserInfoDAO;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.RegisterUserActivity;
import com.example.orderfood.activity.user.adapter.UserBuyFoodlListAdapter;
import com.example.orderfood.activity.user.adapter.UserInfolListAdapter;
import com.example.orderfood.activity.user.foodAct.ManageUserBuyActivity;
import com.example.orderfood.util.FileImgUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UserCommentDialog {

    private Context context;
    private ManageUserBuyActivity activity;
    private ActivityResultLauncher<String> getContentLauncher;
    private Uri selectPicUri = null;
    private String u_id;
    private String s_id;

    public UserCommentDialog() {}

    public UserCommentDialog(Context context, ActivityResultLauncher<String> getContentLauncher) {
        this.activity = (ManageUserBuyActivity) context;
        this.context = context;
        this.getContentLauncher = getContentLauncher;
    }

    /**
     * 打开文件选择器
     */
    private void openGallery() {
        getContentLauncher.launch("image/*");
    }

    public void init() {
        View buyDialog = activity.getLayoutInflater().inflate(R.layout.dialog_user_comment, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(buyDialog);
        bottomSheetDialog.show();

        // 评论内容
        EditText contentText = buyDialog.findViewById(R.id.user_comment_content);
        // 修改评论星级
        String strs[] = {"非常差","差","一般","满意","非常满意"};
        int starIds[] = new int[]{
                R.id.user_comment_star_1,
                R.id.user_comment_star_2,
                R.id.user_comment_star_3,
                R.id.user_comment_star_4,
                R.id.user_comment_star_5,
        };
        TextView scoreDescView = buyDialog.findViewById(R.id.user_comment_score_desc);

        for (int starId : starIds) {
            ImageView starView = buyDialog.findViewById(starId);
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
                        ImageView starView = buyDialog.findViewById(starIds[i - 1]);
                        starView.setImageResource(R.drawable.yellow_star);
                    }
                    for (int i = score + 1; i <= 5; ++ i) {
                        ImageView starView = buyDialog.findViewById(starIds[i - 1]);
                        starView.setImageResource(R.drawable.white_star);
                    }
                    scoreDescView.setText(str);
                }
            });
        }

        // 设置上传图片
        Drawable sImgDrawableDefault = ContextCompat.getDrawable(activity, R.drawable.upload_img);
        Bitmap sImgBitmapDefault = ((BitmapDrawable) sImgDrawableDefault).getBitmap();
        ImageView imgView = buyDialog.findViewById(R.id.user_comment_upload_img);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        Button commentButton = buyDialog.findViewById(R.id.user_comment_but);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("comment", selectPicUri.toString());
                // 获取内容
                String content = contentText.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(activity, "请输入评论内容", Toast.LENGTH_SHORT).show();
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

                bottomSheetDialog.cancel();
            }
        });
    }

    public void setUri(Uri uri) {
        this.selectPicUri = uri;
    }

    public void setUserCommentDialog(Context context, ActivityResultLauncher<String> getContentLauncher,
                                     String u_id, String s_id) {
        this.activity = (ManageUserBuyActivity) context;
        this.context = context;
        this.getContentLauncher = getContentLauncher;
        this.u_id = u_id;
        this.s_id = s_id;
    }
}
