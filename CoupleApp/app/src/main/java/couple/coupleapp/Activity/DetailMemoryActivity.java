package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import couple.coupleapp.Adapter.CommentAdapter;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.ExpandedListView;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;
import couple.coupleapp.entity.Comment;
import couple.coupleapp.entity.Comment_Model;
import couple.coupleapp.entity.TimeLine;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailMemoryActivity extends AppCompatActivity {
    ImageButton detail_back_btn, post_comment_btn;
    ScrollView scrollView;
    EditText content_comment_edt, old_content_comment;
    CommentAdapter commentAdapter;
    private ArrayList<Comment_Model> list ;
    CircleImageView avatar;
    TextView name, createDate, caption_memory, count_comment_memory;
    ImageView image_memory;
    Dialog dialog;
    ImageButton close_dialog_btn;
    Button update_btn;
    int MemoryId ;
    RequestQueue requestQueue;
//    ExpandedListView listView;

        ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_memory);
        anhxa();

        //set scrollview bắt đầu từ vị trí 0,0
        scrollView.smoothScrollTo(0,0);
        //set độ cao cho listview
//        Utils.setListViewHeightBasedOnChildren(listView);
//        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listView.getLayoutParams();
//        lp.height = 1000;
//            listView.setLayoutParams(lp);

        //Lấy thông tin từ màn hình trước gửi sang
        Intent intent = getIntent();
        final int memoryid = intent.getIntExtra("memoryId", 0);

        //chẹck xem có láy được giá trị memoryid không
        if (memoryid == 0) {
            Toast.makeText(this, "Có Lỗi xảy ra", Toast.LENGTH_SHORT).show();
        } else {
            //lấy được thì thực hiện load data
            loadData(memoryid);
            MemoryId = memoryid;
        }

        //tạo adapter hiển thị comment lên list view
        commentAdapter = new CommentAdapter(this, R.layout.comment_layout, list);
        listView.setAdapter(commentAdapter);

        //bắt sự kiên post comment
        post_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = content_comment_edt.getText().toString();
                if (!"".equals(comment)) {
                    createComment(memoryid);
                }
            }
        });
        detail_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //đăg
        registerForContextMenu(listView);
    }

    private void loadData(final int memoryid) {
        String url = Constant.URL_HOSTING + Constant.URL_GET_MEMORY_BYID + "/" + memoryid;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //get response
                    String res_caption = response.getString("caption");
                    String res_imagelink = response.getString("image");
                    String res_time = response.getString("time");
                    int res_userId = response.getInt("userId");


                    //set nội dung hiển thị lên màn hình
                    if (res_userId == Constant.MY_USER_ID) {
                        avatar.setImageDrawable(Constant.MYSELF.getAvatar());
                        name.setText(Constant.MYSELF.getName());
                    } else {
                        avatar.setImageDrawable(Constant.PARTNER.getAvatar());
                        name.setText(Constant.PARTNER.getName());
                    }
                    createDate.setText(res_time);
                    caption_memory.setText(res_caption);


                    //check link ảnh
                    if (!Constant.STATE_IMAGE_DEFAULT.equals(res_imagelink)) {
                        image_memory.setVisibility(View.VISIBLE);
                        Picasso.get().load(res_imagelink).into(image_memory);
                        image_memory.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        image_memory.setVisibility(View.GONE);
                    }

                    //load Comment
                    loadComment(memoryid);
                    Log.e("loaddetailmemory", "onResponse: " + res_time + "\t" + res_caption + "\t" + res_imagelink + "\t" + res_userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("load detail memory", "onResponse: error response");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void createComment(final int memoryid) {
        String url = Constant.URL_HOSTING + Constant.URL_CREATE_COMMENT;
        String content = content_comment_edt.getText().toString().trim();
        JSONObject postparams = new JSONObject();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        try {
            //set variable to post
            postparams.put("memoryId", memoryid);
            postparams.put("content", content);
            postparams.put("time", timeStamp);
            postparams.put("userId", Constant.MY_USER_ID);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String result = response.getString("result");
                        if (Constant.RESULT_TRUE.equals(result)) {
                            Log.e("createComment", "onResponse: success");
                            loadComment(memoryid);
                            //reset form
                            content_comment_edt.setText("");
                        } else {
                            Log.e("createComment", "onResponse: fail");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("createComment", "onResponse: exception parse json");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("createComment", "onResponse: error response");

                }
            });
            //đẩy request
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void anhxa() {
        requestQueue = Volley.newRequestQueue(this);
        avatar = (CircleImageView) findViewById(R.id.detail_avatar);
        name = (TextView) findViewById(R.id.detail_name);
        createDate = (TextView) findViewById(R.id.detail_datecreate);
        caption_memory = (TextView) findViewById(R.id.detail_caption);
        count_comment_memory = (TextView) findViewById(R.id.detail_countcmt);
        image_memory = (ImageView) findViewById(R.id.detail_imagepost);
        content_comment_edt = (EditText) findViewById(R.id.content_comment);
        scrollView = (ScrollView) findViewById(R.id.memory_scroll);
//        listView = (ExpandedListView) findViewById(R.id.detail_listview_comment);
        detail_back_btn = (ImageButton) findViewById(R.id.detail_back);
        post_comment_btn = (ImageButton) findViewById(R.id.post_comment);
        list = new ArrayList<>();
        listView=(ListView) findViewById(R.id.detail_listview_comment);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        menu.setHeaderTitle(null);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    //bắt sự kiện cho menuContext
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //lấy id của button người dùng đã chọn
        int id = item.getItemId();

        //lấy dối tượng info
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //lấy thông tin id và nội dung từ đối tượng info
        int commentId = list.get(info.position).getCommentId();
        String content = list.get(info.position).getContent();

        //bắt sự kiện click menu
        switch (id) {
            case R.id.menu_edit:
                showDialogUpdateComment(commentId, content);
                break;
            case R.id.menu_delete:
                deleteComment(commentId);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    // hiển thị dialog thông tin người dùng
    private void showDialogUpdateComment(final int commentid, String content) {
        dialog = new Dialog(DetailMemoryActivity.this);
        dialog.setContentView(R.layout.dialog_edit_content);
        close_dialog_btn = (ImageButton) dialog.findViewById(R.id.dialog_close);
        update_btn = (Button) dialog.findViewById(R.id.dialog_updateinfor);
        old_content_comment = (EditText) dialog.findViewById(R.id.old_content_comment_edt);
        old_content_comment.setText(content);

        //bắt sự kiện cho các button trong dialog
        //đóng dialog
        close_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        //mở activity cập nhật thông tin người dùng
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gọi service update nội dung
                updateComment(commentid);
                //đóng dialog
                dialog.hide();
            }
        });

        //hiển thị dialog
        dialog.show();
    }


    /**
     * delete Comment
     *
     * @param commentId
     */
    private void deleteComment(final int commentId) {
        String url = Constant.URL_HOSTING + Constant.URL_DEL_COMMENT + "/" + commentId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Constant.RESULT_TRUE.equals(response)) {
                    loadComment(MemoryId);
                } else {
                    Toast.makeText(DetailMemoryActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailMemoryActivity.this, "Có lỗi xảy ra,thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(stringRequest);
    }


    /**
     * update nội dung comment
     *
     * @param commentId
     */
    private void updateComment(int commentId) {
        String url = Constant.URL_HOSTING + Constant.URL_UPDATE_COMMENT;
        String content = old_content_comment.getText().toString().trim();
        //set object json để post lên server
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("commentId", commentId);
            jsonBody.put("content", content);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String kq = response.getString("result");
                        if (Constant.RESULT_TRUE.equals(kq)) {
                            loadComment(MemoryId);
                        } else {
                            Log.e("comment", "onResponse: update comment false");
                        }
                    } catch (JSONException e) {
                        Log.e("comment", "onResponse: get json fail");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("comment", "onErrorResponse:error server ");
                }
            });

            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadComment(MemoryId);
    }

    /**
     * load comment trong memory
     *
     * @param memoryid
     */
    private void loadComment(int memoryid) {
        //khởi tạo link api
        String url = Constant.URL_HOSTING + Constant.URL_GET_COMMENT + "/" + memoryid;
        Log.e("comment", "loadComment: "+url );
        //tạo đối tượng để yêu cầu nhận 1 jsonarrayobject
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int countComment = response.length();
                //hiển thị số lượng comment của memory lên màn hình
                count_comment_memory.setText(countComment + " Comments");

                //reset lại list
                list.clear();

                //check lại số lượng comment
                if (countComment > 0) {
                    //nếu lớn hơn 0 thì lặp để gán lại đối tượng vào list
                    for (int i = 0; i < countComment; i++) {
                        try {
                            //khởi tạo 1 jsonobject gán từ jsonAraay ở phần tử thứ i
                            JSONObject object = response.getJSONObject(i);
                            //gán json vào Comment Model
                            Comment_Model comment = new Comment_Model(object.getInt("commentId"), object.getString("content"), object.getInt("userId"), object.getString("time"));
                            //add vào list
                            list.add(comment);
                            Log.e("loaddcmt ", "onResponse: " + object.getString("content"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("getcomment", "onResponse: lỗi parse json");
                        }
                    }
                }
                //reset lại adapter
                commentAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("load detail memory", "onResponse: error response");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
//    private void loadData(int memoryid) {
//        String url = Constant.URL_HOSTING + Constant.URL_GET_DETAIL_MEMORY + "/" + memoryid;
//        RequestQueue requestQueue = Volley.newRequestQueue(DetailMemoryActivity.this);
//        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String res_caption = response.getString("caption");
//                    String res_imagelink = response.getString("image");
//                    String res_time = response.getString("time");
//                    int res_userId = response.getInt("userId");
//
//                    //lấy mảng comment trong đối tượng jsoon
//                    JSONArray listComment = response.getJSONArray("Comment");
//                    //lấy tổng số comment
//                    int countComment = listComment.length();
//                    //check số lượng comment nếu >0 thì add vào list
//                    if (countComment > 0) {
//                        list.clear();
//                        for (int i = 0; i < listComment.length(); i++) {
//                            JSONObject object = listComment.getJSONObject(i);
//                            Comment_Model comment = new Comment_Model(object.getInt("commentId"), object.getString("content"), object.getInt("userId"), object.getString("time"));
//                            list.add(comment);
//                            Log.e("loaddetailmemory ", "onResponse: " + object.getString("content"));
//                        }
//                        commentAdapter.notifyDataSetChanged();
//                    }
//
//                    //set nội dung hiển thị lên màn hình
//                    if (res_userId == Constant.MY_USER_ID) {
//                        avatar.setImageDrawable(Constant.MYSELF.getAvatar());
//                        name.setText(Constant.MYSELF.getName());
//                    } else {
//                        avatar.setImageDrawable(Constant.PARTNER.getAvatar());
//                        name.setText(Constant.PARTNER.getName());
//                    }
//                    createDate.setText(res_time);
//                    caption_memory.setText(res_caption);
//                    count_comment_memory.setText(countComment + "Comments");
//                    if (!Constant.STATE_IMAGE_DEFAULT.equals(res_imagelink)) {
//                        image_memory.setVisibility(View.VISIBLE);
//                        Picasso.get().load(res_imagelink).into(image_memory);
//                        image_memory.setScaleType(ImageView.ScaleType.FIT_XY);
//                    } else {
//                        image_memory.setVisibility(View.GONE);
//                    }
//                    Log.e("loaddetailmemory", "onResponse: " + res_time + "\t" + res_caption + "\t" + res_imagelink + "\t" + res_userId);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("load detail memory", "onResponse: error response");
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }
}
