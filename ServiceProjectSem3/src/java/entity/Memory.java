/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author chinam
 */
@Entity
@Table(name = "tbl_memory")
public class Memory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memoryId")
    private int memoryId;
    @Column(name = "image")
    private String image;
    @Column(name = "time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date time;
    @Column(name = "caption")
    private String caption;
    @Column(name = "userId")
    private int userId;

    public Memory(int memoryId, String image, Date time, String caption, int userId) {
        this.memoryId = memoryId;
        this.image = image;
        this.time = time;
        this.caption = caption;
        this.userId = userId;
    }

    public Memory() {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
