package com.example.orderfood.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.db.DBClient;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public static SQLiteDatabase conn = DBClient.connection;

    public static List<CommentBean> getCommentsBySid(String s_id) {
        List<CommentBean> ret = new ArrayList<>();
        Cursor cursor = conn.rawQuery("select * from comments where s_id=?",
                new String[]{s_id});
        while (cursor.moveToNext()) {
            ret.add(new CommentBean(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6)
            ));
        }
        return ret;
    }

    public static float getAvgScoreBySid(String s_id) {
        Cursor cursor = conn.rawQuery("select avg(comment_score) as score from comments where s_id=?",
                new String[]{s_id});
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return 0;
    }

    public static int insertComment(String u_id, String s_id, String time, String content, String score, String img) {
        try {
            conn.execSQL("insert into comments values(?,?,?,?,?,?,?)",
                    new String[]{null, u_id, s_id, time, content, score, img});
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
}
