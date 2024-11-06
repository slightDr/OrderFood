package com.example.orderfood.Bean;

public class CommentBean {
    private int commentId;
    private int uId;
    private int sId;
    private String commentTime;
    private String commentContent;
    private int commentScore;
    private String commentImg;

    public CommentBean() {}

    public CommentBean(int commentId, int uId, int sId, String commentTime, String commentContent, int commentScore, String commentImg) {
        this.commentId = commentId;
        this.uId = uId;
        this.sId = sId;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.commentScore = commentScore;
        this.commentImg = commentImg;
    }

    // Getter and Setter methods for each field
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(int commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "commentId=" + commentId +
                ", uId=" + uId +
                ", sId=" + sId +
                ", commentTime='" + commentTime + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentScore=" + commentScore +
                ", commentImg='" + commentImg + '\'' +
                '}';
    }
}
