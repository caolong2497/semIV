package couple.coupleapp.entity;

import java.util.Date;

public class Couple {

    private int coupleID;
    private Date start;
    private String image;

    public Couple() {
    }

    public Couple(int coupleID, Date start, String image) {
        this.coupleID = coupleID;
        this.start = start;
        this.image = image;
    }

    public int getCoupleID() {
        return coupleID;
    }

    public void setCoupleID(int coupleID) {
        this.coupleID = coupleID;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
