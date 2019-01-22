package couple.coupleapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import couple.coupleapp.Adapter.NotificationAdapter;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;
import couple.coupleapp.entity.Notification_Model;

public class NotificationFragment extends Fragment {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNotificationReference;
    private ChildEventListener mChildEventListener;
    private NotificationAdapter notificationAdapter;
    private ListView Notification_listview;
    private TextView content_no_Notification;
    List<Notification_Model> list_notification;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        list_notification = new ArrayList<>();
        Notification_listview = (ListView) view.findViewById(R.id.notification_listview);
        content_no_Notification=(TextView) view.findViewById(R.id.content_hide);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mNotificationReference = mFirebaseDatabase.getReference().child("notification");
        notificationAdapter = new NotificationAdapter(getActivity(), R.layout.item_notification, list_notification, mNotificationReference);
        Notification_listview.setAdapter(notificationAdapter);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Notification_Model notification = dataSnapshot.getValue(Notification_Model.class);
                list_notification.add(notification);
                Collections.sort(list_notification);
                notificationAdapter.notifyDataSetChanged();
                checkListNotification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                Notification_Model notification = dataSnapshot.getValue(Notification_Model.class);
//                list_notification.remove(notification);
//                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query data = mNotificationReference.orderByChild("userid").equalTo(Constant.PARTNER.getUserid()).limitToLast(20);
        data.addChildEventListener(mChildEventListener);
        Notification_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int memoryId=list_notification.get(position).getMemoryid();
                checkNotification(memoryId,position);
            }
        });

        return view;
    }

    private void checkNotification(final int memoryid,final int position) {
        String url = Constant.URL_HOSTING + Constant.URL_CHECK_NOTIFICATION + "/" + memoryid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Constant.RESULT_FALSE.equals(response)) {
                    Toast.makeText(getActivity(), "Kỉ niệm này đã bị xóa", Toast.LENGTH_SHORT).show();
                    deleteNotification(position);
                } else {
                    Intent intent = new Intent(getActivity(), DetailMemoryActivity.class);
                    intent.putExtra("memoryId", memoryid);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "lỗi hệ thống thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void deleteNotification(final int position){
        Notification_Model model=list_notification.get(position);
        mNotificationReference.child(model.getNotificationId()).removeValue();
        list_notification.remove(position);
        notificationAdapter.notifyDataSetChanged();
        checkListNotification();
    }
    private void checkListNotification(){
        if(list_notification.size()==0){
            content_no_Notification.setVisibility(View.VISIBLE);
        }else{
            content_no_Notification.setVisibility(View.GONE);
        }
    }
}
