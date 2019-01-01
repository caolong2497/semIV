package couple.coupleapp.entity;

import java.util.Date;

public class DetailCouple {

    private int myuserID;
    private String myname;
    private String mygmail;
    private String myimage;
    private int partneruserID;
    private String partnername;
    private String partnergmail;
    private String partnerimage;
    private int coupleId;
    private String coupleImage;
    private Date start;

    public DetailCouple() {
    }

    public DetailCouple(int myuserID, String myname, String mygmail, String myimage, int partneruserID, String partnername, String partnergmail, String partnerimage, int coupleId, String coupleImage, Date start) {
        this.myuserID = myuserID;
        this.myname = myname;
        this.mygmail = mygmail;
        this.myimage = myimage;
        this.partneruserID = partneruserID;
        this.partnername = partnername;
        this.partnergmail = partnergmail;
        this.partnerimage = partnerimage;
        this.coupleId = coupleId;
        this.coupleImage = coupleImage;
        this.start = start;
    }

    public int getMyuserID() {
        return myuserID;
    }

    public void setMyuserID(int myuserID) {
        this.myuserID = myuserID;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getMygmail() {
        return mygmail;
    }

    public void setMygmail(String mygmail) {
        this.mygmail = mygmail;
    }

    public String getMyimage() {
        return myimage;
    }

    public void setMyimage(String myimage) {
        this.myimage = myimage;
    }

    public int getPartneruserID() {
        return partneruserID;
    }

    public void setPartneruserID(int partneruserID) {
        this.partneruserID = partneruserID;
    }

    public String getPartnername() {
        return partnername;
    }

    public void setPartnername(String partnername) {
        this.partnername = partnername;
    }

    public String getPartnergmail() {
        return partnergmail;
    }

    public void setPartnergmail(String partnergmail) {
        this.partnergmail = partnergmail;
    }

    public String getPartnerimage() {
        return partnerimage;
    }

    public void setPartnerimage(String partnerimage) {
        this.partnerimage = partnerimage;
    }

    public int getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(int coupleId) {
        this.coupleId = coupleId;
    }

    public String getCoupleImage() {
        return coupleImage;
    }

    public void setCoupleImage(String coupleImage) {
        this.coupleImage = coupleImage;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
