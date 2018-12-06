package app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_userinfor")
public class UserInfor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userid;
	private String name;
	private String gmail;
	private String password;
	private boolean gender;
	private Date birthday;
	private int coupleId;
	private String avatar;
	private int code;
	public UserInfor() {

	}
	
	public UserInfor(Integer userid, String name, String gmail, String password, boolean gender, Date birthday,
			int coupleId, String avatar, int code) {

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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
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
