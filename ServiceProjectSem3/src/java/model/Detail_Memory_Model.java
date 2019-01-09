/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author vucaolong
 */
public class Detail_Memory_Model {

    private int memoryId;
    private String image;
    private Date time;
    private String caption;
    private List<Comment_Model> Comment;
    private int userId;

    public Detail_Memory_Model() {
    }

    public Detail_Memory_Model(int memoryId, String image, Date time, String caption, List<Comment_Model> Comment, int userId) {
        this.memoryId = memoryId;
        this.image = image;
        this.time = time;
        this.caption = caption;
        this.Comment = Comment;
        this.userId = userId;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Comment_Model> getComment() {
        return Comment;
    }

    public void setComment(List<Comment_Model> Comment) {
        this.Comment = Comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
