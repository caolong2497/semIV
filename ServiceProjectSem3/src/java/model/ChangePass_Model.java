/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Long
 */
public class ChangePass_Model {
    int userid;
    String currentpass;
    String newpass;

    public ChangePass_Model() {
    }

    public ChangePass_Model(int userid, String currentpass, String newpass) {
        this.userid = userid;
        this.currentpass = currentpass;
        this.newpass = newpass;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCurrentpass() {
        return currentpass;
    }

    public void setCurrentpass(String currentpass) {
        this.currentpass = currentpass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
    
}
