package couple.coupleapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import couple.coupleapp.Activity.CountDateActivity;
import couple.coupleapp.Activity.CreateMemoryActivity;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;

import java.util.ArrayList;

import couple.coupleapp.entity.MessageModel;
import couple.coupleapp.entity.TimeLine;
import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineAdapter extends ArrayAdapter<TimeLine> {
    private Context mcontext;
    private int ResID;
    private ArrayList<TimeLine> list;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNotificationReference;
    private ChildEventListener mChildEventListener;
    public TimelineAdapter(Context context, int resource, ArrayList<TimeLine> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.ResID = resource;
        this.list = objects;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mNotificationReference = mFirebaseDatabase.getReference().child("notification");
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MyViewHolder myViewHolder = new MyViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.ResID, null);
            myViewHolder.name = (TextView) view.findViewById(R.id.timeline_name);
            myViewHolder.date = (TextView) view.findViewById(R.id.timeline_datecreate);
            myViewHolder.caption = (TextView) view.findViewById(R.id.timeline_caption);
            myViewHolder.countCmt = (TextView) view.findViewById(R.id.timeline_countcmt);
            myViewHolder.more_btn=(ImageButton) view.findViewById(R.id.timeline_option) ;
            myViewHolder.name = (TextView) view.findViewById(R.id.timeline_name);
            myViewHolder.avatar = (CircleImageView) view.findViewById(R.id.timeline_avatar);
            myViewHolder.imagepost = (ImageView) view.findViewById(R.id.timeline_imagepost);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        myViewHolder.date.setText(list.get(position).getDate());

        //check xem ai la nguoi dang memory
        if(list.get(position).getUserid()==Constant.MY_USER_ID){
            myViewHolder.avatar.setImageDrawable(Constant.MYSELF.getAvatar());
            myViewHolder.name.setText(Constant.MYSELF.getName());
            myViewHolder.more_btn.setVisibility(View.VISIBLE);
        }else{
            myViewHolder.avatar.setImageDrawable(Constant.PARTNER.getAvatar());
            myViewHolder.name.setText(Constant.PARTNER.getName());
            myViewHolder.more_btn.setVisibility(View.GONE);
        }
        String linkimage=list.get(position).getImage();
        if(Constant.STATE_IMAGE_DEFAULT.equals(linkimage)){
            myViewHolder.imagepost.setVisibility(View.GONE);
        }else{
            myViewHolder.imagepost.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getImage()).into(myViewHolder.imagepost);
        }
        myViewHolder.caption.setText(list.get(position).getCaption());
        myViewHolder.countCmt.setText(list.get(position).getCountcommnet() + " Comments");
        ImageButton timeline_option_btn = (ImageButton) view.findViewById(R.id.timeline_option);
        timeline_option_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(mcontext, v);
                menu.inflate(R.menu.menu_layout);
                menu.setOnMenuItemClickListener(new MenuPopupMenu(position));
                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(mcontext, (MenuBuilder) menu.getMenu(),v);
//                menu.show();
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
            }
        });

        return view;
    }

    private static class MyViewHolder {
        TextView name, date, caption, countCmt;
        ImageView imagepost;
        ImageButton more_btn;
        CircleImageView avatar;

    }

    private class MenuPopupMenu implements PopupMenu.OnMenuItemClickListener {
        int position;

        public MenuPopupMenu(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(Constant.MYSELF.getUserid()== list.get(position).getUserid()){
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_edit:
                        Intent intent =new Intent(getContext(),CreateMemoryActivity.class);
                        intent.putExtra("memoryid",list.get(position).getMemoryId());
                        getContext().startActivity(intent);
                        break;
                    case R.id.menu_delete:
                        deleteMemory(position);
                        break;
                    default:
                        break;
                }
            }
            return false;
        }
    }
    private void deleteMemory(final int position){
        int memoryid=list.get(position).getMemoryId();
        String url=Constant.URL_HOSTING+Constant.URL_DEL_MEMORY+"/"+memoryid;
        Log.e("hello", "deleteMemory: "+url );
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Constant.RESULT_TRUE.equals(response)) {
                    list.remove(position);
//                    deleteNotificationByIdMemory(list.get(position).getMemoryId());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy ra,thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Có lỗi xảy ra,thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


//    //xoa thong bao khi xoa memory
//    private void deleteNotificationByIdMemory(int memoryid){
//        Query query=mNotificationReference.orderByChild("memoryid").equalTo(memoryid);
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                mNotificationReference.child(dataSnapshot.getKey()).removeValue();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        query.addChildEventListener(mChildEventListener);
//    }
}
