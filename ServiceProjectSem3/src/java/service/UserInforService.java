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
import model.ChangePass_Model;
import model.Login_Model;
import model.Result_model;

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
     * @param userid mã user
     * @param current_password mật khẩu cũ
     * @param new_password mật khẩu mới
     * @return String 0:update mật khẩu thành công
     *                1:Mật khẩu hiện tại không đúng
     *                2:update mật khẩu thất bại
     */
    @POST
    @Path(value = "/changepass")
    @Consumes(MediaType.APPLICATION_JSON)
    public String changePassword(ChangePass_Model model) {
        Result_model result_object=new Result_model();
        UserInfoDAO uidao = new UserInfoDAO();
        String kq=Constant.FALSE;  //1
        UserInfo ui = uidao.getUserInfoByIDAndPassword(model.getUserid(), model.getCurrentpass());
        if (ui != null) {
            ui.setPassword(model.getNewpass());
            if (uidao.updateUserInfo(ui)) {
               kq=Constant.TRUE; //0
            } else {
               kq=Constant.RESULT_FAIL; //2
            }
        }
        result_object.setResult(kq);
        Gson son = new Gson();
        String result = son.toJson(result_object);
        return result; //1
    }

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
