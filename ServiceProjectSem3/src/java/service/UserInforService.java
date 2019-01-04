/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Common.Constant;
import com.google.gson.Gson;
import dao.UserInfoDAO;
import entity.UserInfo;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Login_Model;

/**
 *
 * @author Long
 */
@Path(value = "/user")
public class UserInforService {
    //Process for UserInfo

    @GET
    @Path(value = "/getall")
    @Consumes(MediaType.APPLICATION_JSON)
    public String listUsers() {
        List<UserInfo> listU = new UserInfoDAO().getUserInfos();
        Gson son = new Gson();
        String data = son.toJson(listU);
        return data;
    }
    
    
    
    /**
     * 
     * @param userid 
     * @param current_password
     * @param new_password
     * @return String 0:update mật khẩu thành công
     *                1:Mật khẩu hiện tại không đúng
     *                2:update mật khẩu thất bại
     */
    @GET
    @Path(value = "/changepass/{userid}/{currentpass}/{newpass}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String changePassword(@PathParam("userid") String userid, @PathParam("currentpass") String current_password, @PathParam("newpass") String new_password) {
        
        Boolean result = false;
        UserInfoDAO uidao = new UserInfoDAO();
        String kq=Constant.FALSE;  //1
        UserInfo ui = uidao.getUserInfoByIDAndPassword(userid, current_password);
        if (ui != null) {
            ui.setPassword(new_password);
            result = uidao.updateUserInfo(ui);
            if (result) {
               kq=Constant.TRUE; //0
            } else {
               kq=Constant.RESULT_FAIL; //2
            }
        }
        return kq; //1
    }
//    @POST
//    @Path(value = "/changepass")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String changePassword(@PathParam("gmail") String gmail, @PathParam("currentpass") String current_password, @PathParam("newpass") String new_password) {
//        String message = "";
//        Boolean result = false;
//        UserInfoDAO uidao = new UserInfoDAO();
//        UserInfo ui = uidao.getUserInfoByGmailAndPassword(gmail, current_password);
//        if (ui == null) {
//            message = "Mật khẩu hiện tại không đúng";
//        } else {
//            ui.setPassword(new_password);
//            result = uidao.updateUserInfo(ui);
//            if (result) {
//                message = "Update mật khẩu thành công";
//            } else {
//                message = "Update mật khẩu thất bại";
//            }
//
//        }
//        return message;
//    }

    @POST
    @Path(value = "/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewUser(UserInfo user) {
        Boolean bl = new UserInfoDAO().addUserInfo(user);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }
//    

    @POST
    @Path(value = "/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(UserInfo user) {
        Boolean bl = new UserInfoDAO().updateUserInfo(user);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }

    @GET
    @Path(value = "/deleteUser/{userID}")
    public String deleteUser(@PathParam("userID") Integer userId) {
        Boolean c = new UserInfoDAO().deleteUserInfo(userId);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
//    

    @GET
    @Path(value = "/getUser/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getUserById(@PathParam("userId") Integer userId) {
        UserInfo u = new UserInfoDAO().getUserInfoById(userId);
        Gson son = new Gson();
        String result = son.toJson(u);
        return result;
    }

    @POST
    @Path(value = "/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String Login(Login_Model login) {
        UserInfo u = new UserInfoDAO().getUserInfoByGmailAndPassword(login.getGmail(), login.getPassword());
        Gson son = new Gson();
        String result = son.toJson(u);
        return result;
    }

    @GET
    @Path(value = "/dangnhap/{gmail}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String Dangnhap(@PathParam("gmail") String gmail, @PathParam("password") String password) {
        UserInfo u = new UserInfoDAO().getUserInfoByGmailAndPassword(gmail, password);
        Gson son = new Gson();
        String result = son.toJson(u);
        return result;
    }
    /**
     * hủy kết nối cặp đôi
     * @param coupleid mã cặp đôi
     * @return "0" nếu hủy thành công,"1" nếu thất bại
     */
    @GET
    @Path(value = "/disconnect/{coupleid}")
    public String DisconnectPartner(@PathParam("coupleid") String coupleid) {
        UserInfoDAO userInfoDAO=new UserInfoDAO();
        if(userInfoDAO.disconnectPartner(Integer.parseInt(coupleid))){
            return Constant.TRUE;
        }else{
            return Constant.FALSE;
        }
    }

}
