/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author chinam
 */
@Entity
@Table(name = "tbl_chat")
public class Chat implements Serializable {
    @Id
    @Column(name = "chatID")
    private int chatId;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date time;
    @Column(name = "userId")
    private int userId;

    public Chat(int chatId, String content, Date time, int userId) {
        this.chatId = chatId;
        this.content = content;
        this.time = time;
        this.userId = userId;
    }

    public Chat() {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
