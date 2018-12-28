/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import dao.MemoryDAO;
import entity.Memory;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

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

    @POST
    @Path(value = "/addMemory")
    @Consumes(MediaType.APPLICATION_JSON)
    public String AddNewMemory(Memory memory) {
        Boolean bl = new MemoryDAO().addMemory(memory);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }
//    

    @POST
    @Path(value = "/updateMemory")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateMemory(Memory memory) {
        Boolean bl = new MemoryDAO().updateMemory(memory);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }

    @GET
    @Path(value = "/deleteMemory/{memoryID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteMemory(@PathParam("memoryID") Integer memoryId) {
        Boolean c = new MemoryDAO().deleteMemory(memoryId);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
//    

    @GET
    @Path(value = "/getMemory/{memoryID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMemoryById(@PathParam("memoryID") Integer memoryId) {
        Memory c = new MemoryDAO().getMemoryById(memoryId);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
}