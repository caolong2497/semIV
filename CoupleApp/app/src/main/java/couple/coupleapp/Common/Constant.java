package couple.coupleapp.Common;

import android.graphics.drawable.Drawable;

import couple.coupleapp.entity.UserComon;
import couple.coupleapp.entity.UserInfor;

public class Constant {
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_PASS="^[\\\\p{ASCII}\\\\s]*$";
    public static final String REGEX_NAME="[a-zA-Z][a-zA-Z0-9_ ]*";
        //home:15247
        //cty:12794
    public static final String URL_HOSTING = "http://10.0.3.2:15247/ServiceProjectSem3/rest/"; //lưu hosting service

    // URL COUPLE API
    public static final String URL_GETCOUPLEBYID = "couple/getCouple/"; //url getcouplebyid
    public static final String URL_GET_DETAIL_COUPLE = "couple/getdetailcouple/"; //url getdetailcouple
    public static final String URL_UPDATE_STARTDATE="couple/updateStartDate/";
    public static final String URL_UPDATE_IMG_COUPLE="couple/updatecoupleimage/";
    //------------------------------

    //URL USER API
    public static final String URL_UPDATE_PASS="user/changepass";
    public static final String URL_DISCONNECT ="user/disconnect"; //url hủy cặp đôi disconnect/{coupleid}
    public static final String URL_LOGIN = "user/login";             //url login
    //--------------------------

    //URL MEMORY API
    public static final String URL_CREATE_MEMORY="memory/addMemory";
    public static final String URL_GETMEMORY_ONCOUPLE="memory/getMemoryByCoupleId";   //link lấy memory theo coupleid
    public static final String URL_DEL_MEMORY="memory/deleteMemory";
    public static final String URL_GET_MEMORY_BYID="memory/getMemoryById";
    public static final String URL_UPDATE_MEMORY="memory/updateMemory";
//    public static final String URL_GET_DETAIL_MEMORY="memory/getDetailMemory";

    //URL COMMENT API
    public static final String URL_CREATE_COMMENT="comment/addComment";
    public static final String URL_GET_COMMENT="comment/getCommentByMemory";
    public static final String URL_DEL_COMMENT="comment/deleteComment";
    public static final String URL_UPDATE_COMMENT = "comment/updateComment";

    public static String STARTDATE = "0";
    public static int PARTNER_ID = 0;
    public static int MY_USER_ID = 0;
    public static int MY_COUPLE_ID = 0;
    public static final int DEFEAULT_COUPLEID = 6;
    public static final String SHARED_FILENAME_LOGIN = "logininfor"; //file name SHAREDPREFERENCE
    public static final String COUPLE_ID_SHARED = "coupleid"; //tên tag lưu giá trị cho coupleid
    public static final String MY_USERID_SHARED = "myuser";//tên tag lưu giá trị cho user_id

    // biến check màn hình sửa ngày đi đến activity hay ...
    public static final int CONSTANT_CREATE=0;
    public static final int CONSTANT_UPDATE=1;
    //----------------------------------------------------

    public static final String RESULT_TRUE="0";
    public static final String RESULT_FALSE="1";
    public static final String STATE_IMAGE_DEFAULT="default";
    //------------------------
    public static UserComon MYSELF;
    public static UserComon PARTNER;
    public static Drawable IMG_BACKGROUND;
    // code fix bug tạm thời lỗi load lần đầu getdrawable null

    public static int CHECK_FIRST_COUNTDATE=0;

}
