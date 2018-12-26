/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import dao.CommentDAO;
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
@Path(value = "/comment")
public class CommentService {
    
    //Process for tbl_comment
    @GET
    @Path(value = "/getall")
    @Consumes(MediaType.APPLICATION_JSON)
    public String listComments() {
        List<entity.Comment> listC = new CommentDAO().getComments();
        Gson son = new Gson();
        String data = son.toJson(listC);
        return data;
    }
//    

    @POST
    @Path(value = "/addComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String AddNewComment(entity.Comment comment) {
        Boolean bl = new CommentDAO().addComment(comment);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }
//    

    @POST
    @Path(value = "/updateComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateComment(entity.Comment comment) {
        Boolean bl = new CommentDAO().updateComment(comment);
        Gson son = new Gson();
        String result = son.toJson(bl);
        return result;
    }

    @GET
    @Path(value = "/deleteComment/{commentID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteComment(@PathParam("commentID") Integer commentID) {
        Boolean c = new CommentDAO().deleteComment(commentID);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
//    

    @GET
    @Path(value = "/getComment/{commentID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getCommentById(@PathParam("commentID") Integer commentID) {
        entity.Comment c = new CommentDAO().getCommentById(commentID);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
}
