/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author vucaolong
 */
public class Userinfor_Model {
    private int userID;
    private String name;
    private String gmail;
    private String password;
    private boolean gender;
    private String birthday;
    private int coupleID;
    private String avatar;
    private String code;

    public Userinfor_Model() {
    }

    public Userinfor_Model(int userID, String name, String gmail, String password, boolean gender, String birthday, int coupleID, String avatar, String code) {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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
