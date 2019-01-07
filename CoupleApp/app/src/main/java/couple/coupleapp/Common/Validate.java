package couple.coupleapp.Common;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Validate {
    /**
     * kiểm tra thông tin nhập
     *
     * @param email tên đăng nhập
     * @param password mật khẩu đăng nhập
     * @return Thông tin lỗi ,không lỗi trả về rỗng;
     */
    public static String validateLogin(String email, String password) {
            String message="";
        if (checkIsEmpty(email)||checkIsEmpty(password)) {
            message="Email và Mật Khẩu không được để trống";
        }else if (!checkLength(password,6,20)){
            message="Mật khẩu không được nhỏ hơn 6 và lớn hơn 20 kí tự";
        }else if (!checkLength(email,6,100)){
            message="Email không được nhỏ hơn 6 và lớn hơn 50 kí tự";
        }else if(!checkFormat(email,Constant.REGEX_EMAIL)){
            message="Email không đúng định dạng";
        }

        return message;
    }

    /**
     * check độ dài
     * @param value chuỗi cần check độ dài
     * @param minlength độ dài tối tiểu
     * @param maxlength độ dài tối đa
     * @return true ,false
     */
    public static boolean checkLength(String value,int minlength,int maxlength){
        int length=value.length();
        if(minlength<=length&&length<=maxlength){
            return true;
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
