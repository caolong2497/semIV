package couple.coupleapp.Common;

public class Constant {
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_PASS="^[\\\\p{ASCII}\\\\s]*$";
    public static final String REGEX_NAME="[a-zA-Z][a-zA-Z0-9_ ]*";
    public static final String URL_UPDATE_PASS="user/changepass/";
    public static final String URL_HOSTING = "http://10.0.3.2:8080/ServiceProjectSem3/rest/"; //lưu hosting service
    public static final String URL_GETCOUPLEBYID = "couple/getCouple/"; //url getcouplebyid
    public static final String URL_DISCONNECT ="user/disconnect"; //url hủy cặp đôi disconnect/{coupleid}
    public static final String URL_GET_DETAIL_COUPLE = "couple/getdetailcouple/"; //url getdetailcouple
    public static final String URL_LOGIN = "user/dangnhap";             //url login
    public static final String URL_UPDATE_STARTDATE="couple/updateStartDate/";
    public static String STARTDATE = "0";
    public static int PARTNER_ID = 0;
    public static int MY_USER_ID = 0;
    public static int MY_COUPLE_ID = 0;
    public static final int DEFEAULT_COUPLEID = 6;
    public static final String SHARED_FILENAME_LOGIN = "logininfor"; //file name SHAREDPREFERENCE
    public static final String COUPLE_ID_SHARED = "coupleid"; //tên tag lưu giá trị cho coupleid
    public static final String MY_USERID_SHARED = "myuser";//tên tag lưu giá trị cho user_id
    public static final int CONSTANT_CREATE=0;
    public static int FLAG_STARTDATE=0;
    public static final int CONSTANT_UPDATE=1;
    public static final String RESULT_TRUE="0";
    public static final String RESULT_FALSE="1";

}
