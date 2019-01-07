package couple.coupleapp.entity;

public class Login_Model {
    String gmail;
    String password;

    public Login_Model() {
    }

    public Login_Model(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

}
