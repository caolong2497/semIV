package couple.coupleapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

import couple.coupleapp.Adapter.CommentAdapter;
import couple.coupleapp.Common.ExpandedListView;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;
import couple.coupleapp.entity.Comment;

public class DetailMemoryActivity extends AppCompatActivity {
    ImageButton detail_back_btn;
    ScrollView scrollView;
    CommentAdapter commentAdapter;
    private ArrayList<Comment> list = new ArrayList<>();
    ExpandedListView listView;
//    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_memory);
        anhxa();
        init();
//        scrollView.smoothScrollTo(0,0);

         commentAdapter=new CommentAdapter(this,R.layout.comment_layout,list);
        listView.setAdapter(commentAdapter);
//        Utils.setListViewHeightBasedOnChildren(listView);

        detail_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onBackPressed();
            }
        });
    }
    private void init(){
        list.add(new Comment(1,"123","Cao Long","Anh nay nhin cung dep do","2018/01/01"));
        list.add(new Comment(1,"123","Cao Long123","Anh nay nhin cung dep do","2018/01/01"));
        list.add(new Comment(1,"123","Cao Long3","Anhkkkk","2018/01/01"));
        list.add(new Comment(1,"123","Cao Long4","Anh 23213213","2018/01/01"));
        list.add(new Comment(1,"123","Cao Long5","Anh nay 3333","2018/01/01"));
    }
    private void anhxa()
    {
        scrollView=(ScrollView) findViewById(R.id.memory_scroll);
        listView=(ExpandedListView) findViewById(R.id.detail_listview_comment);
        detail_back_btn=(ImageButton) findViewById(R.id.detail_back);
//        listView=(ListView) findViewById(R.id.detail_listview_comment);
    }
}
