/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Common.Constant;
import Common.Utils;
import com.google.gson.Gson;
import dao.CommentDAO;
import entity.Comment;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Comment_Post_Model;
import model.Result_model;

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
    public String AddNewComment(Comment_Post_Model comment_Post_Model) {
        java.sql.Date createDate = Utils.StringToSQLDate(comment_Post_Model.getTime());
        Comment comment = new Comment(0, comment_Post_Model.getContent(), comment_Post_Model.getMemoryId(), comment_Post_Model.getUserId(), createDate);
        Result_model result_model = new Result_model();
        String message = Constant.FALSE;
        Boolean bl = new CommentDAO().addComment(comment);
        if (bl) {
            message = Constant.TRUE;
        }
        result_model.setResult(message);
        Gson son = new Gson();
        String result = son.toJson(result_model);
        return result;
    }
//    

    @POST
    @Path(value = "/updateComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateComment(Comment comment) {
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
        Comment c = new CommentDAO().getCommentById(commentID);
        Gson son = new Gson();
        String result = son.toJson(c);
        return result;
    }
}
