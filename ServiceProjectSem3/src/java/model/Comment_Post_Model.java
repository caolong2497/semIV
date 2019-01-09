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
public class Comment_Post_Model {
    String content;
    int memoryId;
    int userId;
    String time;

    public Comment_Post_Model() {
    }

    public Comment_Post_Model(String content, int memoryId, int userId, String time) {
        this.content = content;
        this.memoryId = memoryId;
        this.userId = userId;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
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
