package couple.coupleapp.entity;

public class Notification_Model implements  Comparable<Notification_Model> {
    String notificationId;
    int userid;
    int type_action;
    String content;
    long time;
    int memoryid;

    public Notification_Model() {
    }

    public Notification_Model(String notificationId, int userid, int type_action, String content, long time, int memoryid) {
        this.notificationId = notificationId;
        this.userid = userid;
        this.type_action = type_action;
        this.content = content;
        this.time = time;
        this.memoryid = memoryid;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getType_action() {
        return type_action;
    }

    public void setType_action(int type_action) {
        this.type_action = type_action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMemoryid() {
        return memoryid;
    }

    public void setMemoryid(int memoryid) {
        this.memoryid = memoryid;
    }

    @Override
    public int compareTo(Notification_Model o) {
        if(time==o.getTime()){
            return 0;
        }else if(time>o.getTime()){
            return -1;
        }
            return 1;
    }
}
