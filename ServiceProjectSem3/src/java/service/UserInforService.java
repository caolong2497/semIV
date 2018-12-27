/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

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
//    

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
    public String Dangnhap(@PathParam("gmail") String gmail,@PathParam("password") String password) {
        UserInfo u = new UserInfoDAO().getUserInfoByGmailAndPassword(gmail, password);
        Gson son = new Gson();
        String result = son.toJson(u);
        return result;
    }
//    public static void main(String[] args) {
//        System.out.println("value:"+new UserInforService().Dangnhap("caolong2497@gmail.com", "123"));
//    }
    
}
