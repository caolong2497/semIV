package couple.coupleapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

public class DetailMemoryActivity extends AppCompatActivity {
    ImageButton detail_back_btn,post_comment_btn;
    ScrollView scrollView;
    EditText content_comment_edt;
    CommentAdapter commentAdapter;
    private ArrayList<Comment_Model> list = new ArrayList<>();
    ExpandedListView listView;

    //    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_memory);
        anhxa();

        //set scrollview bắt đầu từ vị trí 0,0
//        scrollView.smoothScrollTo(0,0);
        //set độ cao cho listview
//        Utils.setListViewHeightBasedOnChildren(listView);
        Intent intent = getIntent();
        final int memoryid = intent.getIntExtra("memoryId", 0);
        if (memoryid == 0) {
            Toast.makeText(this, "Có Lỗi xảy ra", Toast.LENGTH_SHORT).show();
        } else {
            loadData(memoryid);
        }
        commentAdapter = new CommentAdapter(this, R.layout.comment_layout, list);
        listView.setAdapter(commentAdapter);
        post_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComment(memoryid);
            }
        });
        detail_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadData(int memoryid) {
        String url = Constant.URL_HOSTING + Constant.URL_GET_DETAIL_MEMORY + "/" + memoryid;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailMemoryActivity.this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res_caption = response.getString("caption");
                    String res_imagelink = response.getString("image");
                    String res_time = response.getString("time");
                    String res_userId = response.getString("userId");

                    //lấy mảng comment trong đối tượng jsoon
                    JSONArray listComment = response.getJSONArray("Comment");
                    //lấy tổng số comment
                    int countComment = listComment.length();
                    //check số lượng comment nếu >0 thì add vào list
                    if (countComment > 0) {
                        for (int i = 0; i < listComment.length(); i++) {
                            JSONObject object = listComment.getJSONObject(i);
                            Comment_Model comment = new Comment_Model(object.getInt("commentId"), object.getString("content"), object.getInt("userId"), object.getString("time"));
                            list.add(comment);
                        }
                        commentAdapter.notifyDataSetChanged();
                    }

                    //set nội dung hiển thị lên màn hình
                    //....
                    Log.e("load detail memory", "onResponse: " + res_time + "\t" + res_caption + "\t" + res_imagelink + "\t" + res_userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("load detail memory", "onResponse: error response" );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void createComment(int memoryid){
        String url = Constant.URL_HOSTING + Constant.URL_UPDATE_MEMORY;
        String content=content_comment_edt.getText().toString();
        JSONObject postparams = new JSONObject();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        try {
            //set variable to post
            postparams.put("memoryId", memoryid);
            postparams.put("content", content);
            postparams.put("time",timeStamp);
            postparams.put("userId",Constant.MY_USER_ID);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String result = response.getString("result");
                        if (Constant.RESULT_TRUE.equals(result)) {
                            Log.e("createComment", "onResponse: success" );
                        } else {
                            Log.e("createComment", "onResponse: fail" );
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("createComment", "onResponse: exception parse json" );
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("createComment", "onResponse: error response" );

                }
            });
            //đẩy request
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void anhxa() {
        content_comment_edt=(EditText) findViewById(R.id.content_comment);
        scrollView = (ScrollView) findViewById(R.id.memory_scroll);
        listView = (ExpandedListView) findViewById(R.id.detail_listview_comment);
        detail_back_btn = (ImageButton) findViewById(R.id.detail_back);
        post_comment_btn =(ImageButton) findViewById(R.id.post_comment);
//        listView=(ListView) findViewById(R.id.detail_listview_comment);
    }
}
