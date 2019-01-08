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
public class Memory_Model {
    
    private int memoryId;
    private String image;
    private String time;
    private String caption;
    private int countComment;
    private int userId;

    public Memory_Model() {
    }

    public Memory_Model(int memoryId, String image, String time, String caption, int coutComment, int userId) {
        this.memoryId = memoryId;
        this.image = image;
        this.time = time;
        this.caption = caption;
        this.countComment = coutComment;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }
    
    
}
