package couple.coupleapp.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;
import java.util.Collection;
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
    List<Notification_Model> list_notification;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
//        init();
        list_notification= new ArrayList<>();
        Notification_listview=(ListView) view.findViewById(R.id.notification_listview);

        notificationAdapter = new NotificationAdapter(getActivity(), R.layout.item_notification, list_notification);
        Notification_listview.setAdapter(notificationAdapter);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mNotificationReference = mFirebaseDatabase.getReference().child("notification");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Notification_Model notification = dataSnapshot.getValue(Notification_Model.class);
                list_notification.add(notification);
                Collections.sort(list_notification);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query data = mNotificationReference.orderByChild("userid").equalTo(Constant.PARTNER.getUserid()).limitToLast(15);
        data.addChildEventListener(mChildEventListener);

        return view;
    }

//    private void init(){
//        list_notification.add(new Notification_Model(1, 1, "helloword",1547313537656L, 1));
//        list_notification.add(new Notification_Model(1, 2, "helloword",1547313537656L, 1));
//        list_notification.add(new Notification_Model(1, 2, "helloword",1547313537656L, 1));
//        list_notification.add(new Notification_Model(1, 1, "helloword",1547313537656L, 1));
//        list_notification.add(new Notification_Model(1, 2, "chao em",1547313537656L, 1));
//        list_notification.add(new Notification_Model(1, 1, "helloword",1547313537656L, 1));
//    }
}
