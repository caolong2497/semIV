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
import dao.CoupleDAO;
import dao.UserInfoDAO;
import entity.Couple;
import entity.UserInfo;
import java.text.ParseException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.core.MediaType;

import model.DetailCoupleInfor_Model;
import model.Result_model;

/**
 *
 * @author Long
 */
@Path(value = "/couple")
public class CoupleService {

    @GET
    @Path(value = "/getall")
    @Consumes(MediaType.APPLICATION_JSON)
    public String listCouples() {
        List<Couple> listC = new CoupleDAO().getCouples();
        Gson son = new Gson();
        String data = son.toJson(listC);
        return data;
    }
//    

    @POST
    @Path(value = "/addCouple")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewCouple(Couple couple) {
        Boolean bl = new CoupleDAO().addCouple(couple);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }

    /**
     *
     * @param couple
     * @return "0" thành công "1" thất bại
     */
    @POST
    @Path(value = "/updatecoupleimage")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCoupleImage(Couple couple) {
        Result_model result_object = new Result_model();
        String message = Constant.FALSE;
        if (new CoupleDAO().updateCoupleImage(couple)) {
            message = Constant.TRUE;
        }
        result_object.setResult(message);
        Gson son = new Gson();
        String result = son.toJson(result_object);
        return result;
    }

    @GET
    @Path(value = "/deleteCouple/{coupleID}")
    public String deleteCouple(@PathParam("coupleID") Integer coupleID) {
        Boolean c = new CoupleDAO().deleteCouple(coupleID);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
//    

    @GET
    @Path(value = "/getCouple/{coupleID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getCoupleById(@PathParam("coupleID") Integer coupleID) {
        Couple c = new CoupleDAO().getCoupleById(coupleID);
        Gson son = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        String result = son.toJson(c);
        return result;
    }

    @GET
    @Path(value = "/getdetailcouple/{userid}/{coupleid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getdetailcouple(@PathParam("userid") String userid, @PathParam("coupleid") String coupleid) {
        int myuserid = Integer.parseInt(userid);
        List<UserInfo> list = new UserInfoDAO().getUserInfosByCoupleID(Integer.parseInt(coupleid));
        Couple couple = new CoupleDAO().getCoupleById(Integer.parseInt(coupleid));
        DetailCoupleInfor_Model detailCoupleInfor_Model = null;
        if (list != null && couple != null) {
            detailCoupleInfor_Model = new DetailCoupleInfor_Model();
            for (UserInfo u : list) {
                if (u.getUserID() == myuserid) {
                    detailCoupleInfor_Model.setMyuserID(u.getUserID());
                    detailCoupleInfor_Model.setMygmail(u.getGmail());
                    detailCoupleInfor_Model.setMyname(u.getName());
                    detailCoupleInfor_Model.setMyimage(u.getAvatar());
                } else {
                    detailCoupleInfor_Model.setPartneruserID(u.getUserID());
                    detailCoupleInfor_Model.setPartnergmail(u.getGmail());
                    detailCoupleInfor_Model.setPartnername(u.getName());
                    detailCoupleInfor_Model.setPartnerimage(u.getAvatar());
                }
            }
            detailCoupleInfor_Model.setCoupleId(couple.getCoupleID());
            detailCoupleInfor_Model.setStart(couple.getStart());
            detailCoupleInfor_Model.setCoupleImage(couple.getImage());
        }
        Gson son = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        String result = son.toJson(detailCoupleInfor_Model);
        return result;
    }

    /**
     *
     * @param coupleid mã couple muốn cập nhật
     * @param startDate ngày start mới
     * @return "0" update thành công "1" update thất bại
     */
    @GET
    @Path(value = "/updateStartDate/{coupleid}/{startdate}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateStartDate(@PathParam("coupleid") String coupleid, @PathParam("startdate") String startDate) {
        java.sql.Date sqlDate = Utils.StringToSQLDateFormat2(startDate);
        boolean result = new CoupleDAO().updateStartDate(Integer.parseInt(coupleid), sqlDate);
        if (result) {
            return Constant.TRUE;
        }
        return Constant.FALSE;
    }
}
