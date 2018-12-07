package client.doansemiv.coupleclient.entity;

import com.google.gson.annotations.SerializedName;
//https://github.com/google/gson/blob/master/UserGuide.md
public class UserInfor {
    private Integer userid;
    private String name;
    private String gmail;
    private String password;
    private boolean gender;
    @SerializedName("birthday")
    private String birthday;
    private int coupleId;
    private String avatar;
    private int code;

    public UserInfor() {
    }

    public UserInfor(Integer userid, String name, String gmail, String password, boolean gender, String birthday, int coupleId, String avatar, int code) {
        this.userid = userid;
        this.name = name;
        this.gmail = gmail;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.coupleId = coupleId;
        this.avatar = avatar;
        this.code = code;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public int getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(int coupleId) {
        this.coupleId = coupleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
