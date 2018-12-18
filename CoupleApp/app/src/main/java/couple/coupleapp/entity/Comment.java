package couple.coupleapp.entity;

public class Comment {
    private int commentId;
    private String avatar;
    private String username;
    private String content;
    private String date;

    public Comment() {
    }

    public Comment(int commentId, String avatar, String username, String content, String date) {
        this.commentId = commentId;
        this.avatar = avatar;
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
