package couple.coupleapp.entity;

public class TimeLine {
    private int id;
    private String avatar;
    private String name;
    private String date;
    private String image;
    private String caption;
    private int countcommnet;

    public TimeLine() {
    }

    public TimeLine(int id, String avatar, String name, String date, String image, String caption, int countcommnet) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.date = date;
        this.image = image;
        this.caption = caption;
        this.countcommnet = countcommnet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
