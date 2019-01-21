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
import dao.MemoryDAO;
import entity.Memory;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Memory_Model;
import model.Result_model;

/**
 *
 * @author Long
 */
@Path(value = "/memory")
public class MemoryService {

    //Process for tbl_memory
    @GET
    @Path(value = "/memoris")
    @Consumes(MediaType.APPLICATION_JSON)
    public String listMemoris() {
        List<Memory> listU = new MemoryDAO().getMemorys();
        Gson son = new Gson();
        String data = son.toJson(listU);
        return data;
    }
//    

    /**
     * create memory
     *
     * @param model Memory hạng mục time là chuỗi định dạng dd/MM/yyyy
     * @return "0" nếu thất bại,!= "0" neu thanh cong
     */
    @POST
    @Path(value = "/addMemory")
    @Consumes(MediaType.APPLICATION_JSON)
    public String AddNewMemory(Memory_Model model) {
        java.sql.Date createDate = Utils.StringToSQLDate(model.getTime());
        MemoryDAO memorydao=new MemoryDAO();
        Memory memory = new Memory(0, model.getImage(), createDate, model.getCaption(), model.getUserId());
        Result_model result_ob = new Result_model();
        String message = "1";
        Boolean bl = memorydao.addMemory(memory);
        if (bl) {
            int lastid=memorydao.getLastId(); 
            message = lastid+"";
        }
        result_ob.setResult(message);
        Gson son = new Gson();
        String result = son.toJson(result_ob);
        return result;
    }
//    

    @POST
    @Path(value = "/updateMemory")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateMemory(Memory_Model memory) {
        Result_model result_model = new Result_model();
        String message = Constant.FALSE;
        java.sql.Date sqlDate = Utils.StringToSQLDate(memory.getTime());
        Boolean bl = new MemoryDAO().updateMemory(memory.getMemoryId(), memory.getCaption(), sqlDate);
        if (bl) {
            message = Constant.TRUE;
        }
        result_model.setResult(message);
        Gson son = new Gson();
        String result = son.toJson(result_model);
        return result;
    }

    @GET
    @Path(value = "/deleteMemory/{memoryID}")
    public String deleteMemory(@PathParam("memoryID") Integer memoryId) {
        Boolean c = new MemoryDAO().deleteMemory(memoryId);
        String message = Constant.FALSE;
        if (c) {
            message = Constant.TRUE;
        }
        return message;
    }

    @GET
    @Path(value = "/getMemoryById/{memoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMemoryById(@PathParam("memoryId") Integer memoryId) {
        Memory memory = new MemoryDAO().getMemoryById(memoryId);
        Gson son = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        String result = son.toJson(memory);
        return result;
    }
    
    @GET
    @Path(value = "/checkExistMemoryId/{memoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String checkExistMemoryId(@PathParam("memoryId") Integer memoryId) {
        Memory memory = new MemoryDAO().getMemoryById(memoryId);
        if(memory==null){
            return Constant.FALSE;
        }
        return Constant.TRUE;
    }
//    @GET
//    @Path(value = "/getDetailMemory/{memonryId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String getDetailMemory(@PathParam("memonryId") Integer memoryId) {
//        Memory memory = new MemoryDAO().getMemoryById(memoryId);
//        List<Comment> list = new CommentDAO().getCommentByMemoryId(memoryId);
//        List<Comment_Model> list_Comment = new ArrayList<>();
//        if (!list.isEmpty()) {
//            for (int i = 0; i < list.size(); i++) {
//                list_Comment.add(new Comment_Model(list.get(i).getCommentId(), list.get(i).getContent(), list.get(i).getUserId(), list.get(i).getTime()));
//            }
//        }
//        Detail_Memory_Model detailMemory = new Detail_Memory_Model(memory.getMemoryId(), memory.getImage(), memory.getTime(), memory.getCaption(), list_Comment, memory.getUserId());
//        Gson son = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
//        String result = son.toJson(detailMemory);
//        return result;
//    }
    @GET
    @Path(value = "/getMemoryByCoupleId/{coupleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMemoryByCoupleId(@PathParam("coupleId") Integer coupleId) {
        List<Memory_Model> list = new MemoryDAO().getMemoryByCoupleId(coupleId);
        Gson son = new Gson();
        String result = son.toJson(list);
        return result;
    }


}
