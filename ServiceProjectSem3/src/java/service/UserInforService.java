/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Common.Constant;
import Common.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import model.Userinfor_Model;

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
     * @return String 0:update mật khẩu thành công 1:Mật khẩu hiện tại không
     * đúng 2:update mật khẩu thất bại
     */
    @POST
    @Path(value = "/changepass")
    @Consumes(MediaType.APPLICATION_JSON)
    public String changePassword(ChangePass_Model model) {
        Result_model result_object = new Result_model();
        UserInfoDAO uidao = new UserInfoDAO();
        String kq = Constant.FALSE;  //1
        UserInfo ui = uidao.getUserInfoByIDAndPassword(model.getUserid(), model.getCurrentpass());
        if (ui != null) {
            ui.setPassword(model.getNewpass());
            if (uidao.updateUserInfo(ui)) {
                kq = Constant.TRUE; //0
            } else {
                kq = Constant.RESULT_FAIL; //2
            }
        }
        result_object.setResult(kq);
        Gson son = new Gson();
        String result = son.toJson(result_object);
        return result; //1
    }

    /**
     *
     * @param user
     * @return "0" thành công "1" thất bại "2" email đã tồn tại
     */
    @POST
    @Path(value = "/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewUser(Userinfor_Model user) {
        Result_model result_object = new Result_model();
        String kq = Constant.FALSE;
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        UserInfo u = new UserInfo(0, user.getName(), user.getGmail(), user.getPassword(), user.isGender(), Utils.StringToSQLDate(user.getBirthday()), user.getCoupleID(), user.getAvatar(), Utils.codeDefault());
        if (userInfoDAO.getUserInforByEmail(user.getGmail()) == null) {

            if (userInfoDAO.addUserInfo(u)) {
                kq = Constant.TRUE;
            }
        } else {
            kq = "2";
        }
        result_object.setResult(kq);
        Gson son = new Gson();
        String result = son.toJson(result_object);
        return result;
    }
//    

    @POST
    @Path(value = "/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(Userinfor_Model userinfor_Model) {
        UserInfo user = new UserInfo();
        user.setUserID(userinfor_Model.getUserID());
        user.setName(userinfor_Model.getName());
        user.setGender(userinfor_Model.isGender());
        user.setAvatar(userinfor_Model.getAvatar());
        user.setBirthday(Utils.StringToSQLDate(userinfor_Model.getBirthday()));
        Result_model result_model = new Result_model();
        String message = Constant.FALSE;
        Boolean bl = new UserInfoDAO().updateUserCustom(user);
        if (bl) {
            message = Constant.TRUE;
        }
        result_model.setResult(message);
        Gson son = new Gson();
        String result = son.toJson(result_model);
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
        Gson son = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
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
     *
     * @param coupleid mã cặp đôi
     * @return "0" nếu hủy thành công,"1" nếu thất bại
     */
    @GET
    @Path(value = "/disconnect/{coupleid}")
    public String DisconnectPartner(@PathParam("coupleid") String coupleid) {
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        if (userInfoDAO.disconnectPartner(Integer.parseInt(coupleid))) {
            return Constant.TRUE;
        } else {
            return Constant.FALSE;
        }
    }

    /**
     *
     * @param userid ma user
     * @param code ma ghep cap
     * @return newcoupleid neu ghep thanh cong ,6 neu that bai
     */
    @GET
    @Path(value = "/pairingUser/{userid}/{code}")
    public String pairingUser(@PathParam("userid") int userid, @PathParam("code") String code) {
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        int idPartner = userInfoDAO.getUserIdByCode(userid, code);
        int newCoupleId = Constant.DEFEAULT_COUPLEID;
        if (idPartner != 0) {
            newCoupleId = userInfoDAO.createCouple(userid, idPartner);
        }
        return newCoupleId + "";

    }

    /**
     * check email đã được đăng kí chưa
     *
     * @param gmail
     * @return "1" nếu chưa tồn tại,"0" nếu đã tồn tại
     */
    @GET
    @Path(value = "/checkmail/{gmail}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String checkMail(@PathParam("gmail") String gmail) {
        String kq = Constant.FALSE;
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        UserInfo user = userInfoDAO.getUserInforByEmail(gmail);
        if (user != null) {
            String code = Utils.codeDefault();
            user.setCode(code);
            if (userInfoDAO.updateUserInfo(user)) {
                if (Utils.sendMail(gmail, code)) {
                    kq = Constant.TRUE;
                }
            }
        }
        return kq;
    }

    /**
     * check code đã được đăng kí chưa
     *
     * @param gmail
     * @return "1" nếu chưa tồn tại,"0" nếu đã tồn tại
     */
    @GET
    @Path(value = "/checkcode/{gmail}/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String checkCode(@PathParam("gmail") String gmail, @PathParam("code") String code) {
        String kq = Constant.FALSE;
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        UserInfo user = userInfoDAO.checkCode(gmail, code);
        if (user != null) {
            kq = Constant.TRUE;
        }
        return kq;
    }

    @POST
    @Path(value = "/newPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public String checkCode(Login_Model login) {
        String kq = Constant.FALSE;
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        UserInfo user = userInfoDAO.getUserInforByEmail(login.getGmail());

        if (user != null) {
            user.setPassword(login.getPassword());
            if (userInfoDAO.updateUserInfo(user)) {
                kq = Constant.TRUE;
            };
        }
        Result_model result_model = new Result_model();
        result_model.setResult(kq);
        Gson son = new Gson();
        String result = son.toJson(result_model);
        return result;
    }

}
