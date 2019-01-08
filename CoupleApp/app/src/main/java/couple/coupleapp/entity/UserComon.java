package couple.coupleapp.entity;

import android.graphics.drawable.Drawable;

public class UserComon {
    private Integer userid;
    private String name;
    private Drawable avatar;

    public UserComon() {
    }

    public UserComon(Integer userid, String name, Drawable avatar) {
        this.userid = userid;
        this.name = name;
        this.avatar = avatar;
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

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }
}
