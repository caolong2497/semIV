package app.dto;

public class UserLoginDTO {
	private Integer userid;
	private String name;
	private String gmail;
	private String password;
	
	public UserLoginDTO() {
		super();
	}

	public UserLoginDTO(Integer userid, String name, String gmail, String password) {
		super();
		this.userid = userid;
		this.name = name;
		this.gmail = gmail;
		this.password = password;
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
	
}
