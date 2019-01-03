package couple.coupleapp.Common;

import java.util.ArrayList;
import java.util.List;

public class Validate {
    /**
     * kiểm tra thông tin nhập
     *
     * @param email tên đăng nhập
     * @param password mật khẩu đăng nhập
     * @return Danh sách lỗi ,nếu không có lỗi trả về mảng rỗng
     */
    public static boolean validateLogin(String email, String password) {

        if (!checkIsEmpty(email) && !checkIsEmpty(password)) {
            if(checkFormat(email,Constant.REGEX_EMAIL)) {
                return true;
            }
        }

        return false;

    }

    /**
     * kiểm tra format của value với biểu thức chính quy regex
     *
     * @param value chuỗi muốn check format
     * @param regex chuỗi biểu thức check
     * @return true nếu đúng format,false nếu sai format
     */
    public static boolean checkFormat(String value, String regex) {
        return value.matches(regex);
    }

    /**
     * Kiểm tra 1 chuỗi có rỗng không
     *
     * @param value giá trị cần kiểm tra
     * @return true nếu rỗng,false nếu khác rỗng
     */
    public static boolean checkIsEmpty(String value) {
        if ("".equals(value)) {
            return true;
        } else {
            return false;
        }
    }

//    public static void main(String[] args) {
//        if(checkFormat("caolong2497@gmail.com",Constant.REGEX_EMAIL)){
//            System.out.println("đúng");
//        }else{
//            System.out.println("sai");
//        }
//        System.out.println("hello");
//    }
}
