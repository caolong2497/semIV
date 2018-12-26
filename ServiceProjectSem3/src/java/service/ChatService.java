/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import dao.ChatDAO;
import entity.Chat;
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
@Path(value = "/chat")
public class ChatService {
    

    //Process for tbl_chat
    @GET
    @Path(value = "/getall")
    @Consumes(MediaType.APPLICATION_JSON)
    public String listChats() {
        List<Chat> listU = new ChatDAO().getChats();
        Gson son = new Gson();
        String data = son.toJson(listU);
        return data;
    }
//    

    @POST
    @Path(value = "/addChat")
    @Consumes(MediaType.APPLICATION_JSON)
    public String AddNewChat(Chat chat) {
        Boolean bl = new ChatDAO().addChat(chat);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }
//    

    @POST
    @Path(value = "/updateChat")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateChat(Chat chat) {
        Boolean bl = new ChatDAO().updateChat(chat);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }
//    

    @GET
    @Path(value = "/deleteChat/{chatID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteChat(@PathParam("chatID") Integer chatID) {
        Boolean c = new ChatDAO().deleteChat(chatID);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
//    

    @GET
    @Path(value = "/getChat/{chatID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getChatById(@PathParam("chatID") Integer chatID) {
        Chat c = new ChatDAO().getChatById(chatID);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
}
