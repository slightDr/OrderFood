package com.example.orderfood.activity.shop.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.DAO.UserDAO;
import com.example.orderfood.R;

import java.util.List;

public class CommentListAdapter extends ArrayAdapter<CommentBean> {

    private List<CommentBean> list;
    private Context context;

    public CommentListAdapter(@NonNull Context context, List<CommentBean> list) {
        super(context, R.layout.list_manage_shop_comment, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_manage_shop_comment, viewGroup, false);
        }

        CommentBean commentBean = list.get(position);
        UserBean userBean = UserDAO.getUserInfoByUid(""+commentBean.getUId());

        ImageView userImageView = convertView.findViewById(R.id.manage_shop_comment_user_img);
        userImageView.setImageBitmap(BitmapFactory.decodeFile(userBean.getU_img()));
        TextView nameText = convertView.findViewById(R.id.manage_shop_comment_user_name);
        nameText.setText(userBean.getU_name());
        TextView timeText = convertView.findViewById(R.id.manage_shop_comment_time);
        timeText.setText(commentBean.getCommentTime());
        // 设置评论星级
        String strs[] = {"非常差","差","一般","满意","非常满意"};
        int starIds[] = new int[]{
                R.id.manage_shop_comment_star_1,
                R.id.manage_shop_comment_star_2,
                R.id.manage_shop_comment_star_3,
                R.id.manage_shop_comment_star_4,
                R.id.manage_shop_comment_star_5,
        };
        int score = commentBean.getCommentScore();
        TextView scoreDescView = convertView.findViewById(R.id.manage_shop_comment_score_desc);
        scoreDescView.setText(strs[score - 1]);
        for (int i = 1; i <= score; ++ i) {
            ImageView starView = convertView.findViewById(starIds[i - 1]);
            starView.setImageResource(R.drawable.yellow_star);
        }
        for (int i = score + 1; i <= 5; ++ i) {
            ImageView starView = convertView.findViewById(starIds[i - 1]);
            starView.setImageResource(R.drawable.white_star);
        }
        // 修改评论星级
//        View finalConvertView = convertView;
//        for (int starId : starIds) {
//            ImageView starView = finalConvertView.findViewById(starId);
//            starView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int clickId = view.getId();
//                    int score = 1;
//                    String str = "非常差";
//                    for (int i = 0; i < 5; ++ i) {
//                        if (clickId == starIds[i]) {
//                            score = i + 1;
//                            str = strs[i];
//                            break;
//                        }
//                    }
//                    for (int i = 1; i <= score; ++ i) {
//                        ImageView starView = finalConvertView.findViewById(starIds[i - 1]);
//                        starView.setImageResource(R.drawable.yellow_star);
//                    }
//                    for (int i = score + 1; i <= 5; ++ i) {
//                        ImageView starView = finalConvertView.findViewById(starIds[i - 1]);
//                        starView.setImageResource(R.drawable.white_star);
//                    }
//                    scoreDescView.setText(str);
//                }
//            });
//        }

        TextView contentText = convertView.findViewById(R.id.manage_shop_comment_content);
        contentText.setText(commentBean.getCommentContent());
        ImageView commentImageView = convertView.findViewById(R.id.manage_shop_comment_img);
        commentImageView.setImageBitmap(BitmapFactory.decodeFile(commentBean.getCommentImg()));

        return convertView;
    }
}
