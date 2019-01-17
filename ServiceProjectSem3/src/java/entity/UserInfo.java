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
@Table(name = "tbl_userInfor")
public class UserInfo implements Serializable {
    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;
    @Column(name = "name")
    private String name;
    @Column(name = "gmail")
    private String gmail;
    @Column(name = "password")
    private String password;
    @Column (name = "gender")
    private boolean gender;
    @Column(name = "birthday")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    @Column (name = "coupleID")
    private int coupleID;
    @Column (name = "avatar")
    private String avatar;
    @Column(name = "code")
    private String code;

    public UserInfo(int userID, String name, String gmail, String password, boolean gender, Date birthday, int coupleID, String avatar, String code) {
        this.userID = userID;
        this.name = name;
        this.gmail = gmail;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.coupleID = coupleID;
        this.avatar = avatar;
        this.code = code;
    }

    public UserInfo() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getCoupleID() {
        return coupleID;
    }

    public void setCoupleID(int coupleID) {
        this.coupleID = coupleID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
}
