package couple.coupleapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.entity.Notification_Model;
import couple.coupleapp.R;

public class NotificationAdapter extends ArrayAdapter<Notification_Model> {
    Context mcontext;
    int ResId;
    List<Notification_Model> list;
    DatabaseReference notificationReference;
    public NotificationAdapter(Context context, int resource, List<Notification_Model> objects, DatabaseReference mNotificationReference) {
        super(context, resource, objects);
        this.mcontext = context;
        this.ResId = resource;
        this.list = objects;
        this.notificationReference=mNotificationReference;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MyViewHolder myViewHolder = new MyViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.ResId, null);
            myViewHolder.content = (TextView) view.findViewById(R.id.content_notification);
            myViewHolder.action = (TextView) view.findViewById(R.id.type_action);
            myViewHolder.time = (TextView) view.findViewById(R.id.time_notification);
            myViewHolder.delete_btn =(ImageButton) view.findViewById(R.id.delete_notification);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        final Notification_Model notification_model = getItem(position);
        String action_str = "New Timeline";
        String content_Str = " just added new memory on timeline:\n";
        if (notification_model.getType_action() == Constant.NOTIFICATION_ACTION_COMMENT) {
            action_str = "Comment";
            content_Str = " commented: ";
        }
        content_Str = Constant.PARTNER.getName() + content_Str + notification_model.getContent();
        myViewHolder.action.setText(action_str);
        myViewHolder.content.setText(content_Str);
        myViewHolder.time.setText(Utils.formatLongTimeToddMMyyyyHHmmaa(notification_model.getTime()) + "");
        myViewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "click delete id"+notification_model.getContent(), Toast.LENGTH_SHORT).show();
                notificationReference.child(notification_model.getNotificationId()).removeValue();
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    private class MyViewHolder {
        TextView content, action, time;
        ImageButton delete_btn;
    }
}
