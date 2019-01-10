/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author vucaolong
 */
public class Update_comment_model {
    int commentId;
    String content;

    public Update_comment_model() {
    }

    public Update_comment_model(int commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setMemoryId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
