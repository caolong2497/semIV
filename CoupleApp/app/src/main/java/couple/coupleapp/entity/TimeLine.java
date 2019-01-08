package couple.coupleapp.entity;


public class TimeLine {
    private int memoryId;
    private String date;
    private String image;
    private String caption;
    private int countcommnet;
    private int userid;
    public TimeLine() {
    }

    public TimeLine(int memoryId, String date, String image, String caption, int countcommnet, int userid) {
        this.memoryId = memoryId;
        this.date = date;
        this.image = image;
        this.caption = caption;
        this.countcommnet = countcommnet;
        this.userid = userid;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getCountcommnet() {
        return countcommnet;
    }

    public void setCountcommnet(int countcommnet) {
        this.countcommnet = countcommnet;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
