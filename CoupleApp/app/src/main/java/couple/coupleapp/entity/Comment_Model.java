package couple.coupleapp.entity;

public class Comment_Model {
    private int commentId;
    private String content;
    private int userId;
    private String time;

    public Comment_Model() {
    }

    public Comment_Model(int commentId, String content, int userId, String time) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.time = time;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
