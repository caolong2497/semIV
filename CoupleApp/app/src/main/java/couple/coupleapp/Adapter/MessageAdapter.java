package couple.coupleapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import couple.coupleapp.entity.MessageModel;

public class MessageAdapter extends ArrayAdapter<MessageModel> {
    Context mcontext;
    int ResId;
    List<MessageModel> list;

    public MessageAdapter(Context context, int resource, List<MessageModel> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.ResId=resource;
        this.list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MyViewHolder myViewHolder = new MyViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.ResId, null);
            myViewHolder.layout_item_received = (ConstraintLayout) view.findViewById(R.id.layout_received);
            myViewHolder.layout_item_send = (ConstraintLayout) view.findViewById(R.id.layout_send);
            myViewHolder.name_received = (TextView) view.findViewById(R.id.text_message_name_received);
            myViewHolder.name_send=(TextView) view.findViewById(R.id.text_message_name_send);
            myViewHolder.message_received = (TextView) view.findViewById(R.id.text_message_body_received);
            myViewHolder.time_received = (TextView) view.findViewById(R.id.text_message_time_received);
            myViewHolder.image_received = (ImageView) view.findViewById(R.id.image_message_received);
            myViewHolder.message_send = (TextView) view.findViewById(R.id.text_message_body_send);
            myViewHolder.time_send = (TextView) view.findViewById(R.id.text_message_time_send);
            myViewHolder.image_send = (ImageView) view.findViewById(R.id.image_message_send);
            myViewHolder.time_group=(TextView) view.findViewById(R.id.time_group_chat);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }

        MessageModel message = getItem(position);

        boolean isPhoto = message.getPhotoUrl() != null;

        if (message.getUserid() == Constant.MY_USER_ID) {
            myViewHolder.layout_item_received.setVisibility(View.GONE);
            myViewHolder.layout_item_send.setVisibility(View.VISIBLE);
            if (isPhoto) {
                myViewHolder.message_send.setVisibility(View.GONE);
                myViewHolder.image_send.setVisibility(View.VISIBLE);
                Picasso.get().load(message.getPhotoUrl()).into(myViewHolder.image_send);
                myViewHolder.image_send.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                myViewHolder.message_send.setVisibility(View.VISIBLE);
                myViewHolder.image_send.setVisibility(View.GONE);
                myViewHolder.message_send.setText(message.getText());
            }
            myViewHolder.name_send.setText(Constant.MYSELF.getName());
            myViewHolder.time_send.setText(Utils.formatLongTimeToHHmm(message.getTime()));
        } else {
            myViewHolder.layout_item_received.setVisibility(View.VISIBLE);
            myViewHolder.layout_item_send.setVisibility(View.GONE);
            if (isPhoto) {
                myViewHolder.message_received.setVisibility(View.GONE);
                myViewHolder.image_received.setVisibility(View.VISIBLE);
                Picasso.get().load(message.getPhotoUrl()).into(myViewHolder.image_received);
                myViewHolder.image_received.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                myViewHolder.message_received.setVisibility(View.VISIBLE);
                myViewHolder.image_received.setVisibility(View.GONE);
                myViewHolder.message_received.setText(message.getText());
            }
            myViewHolder.name_received.setText(Constant.PARTNER.getName());
            myViewHolder.time_received.setText(Utils.formatLongTimeToHHmm(message.getTime()));
        }
        long previousTs = 0;
        if(position>1){
            MessageModel pm = list.get(position-1);
            previousTs = pm.getTime();
        }
        setTimeTextVisibility(message.getTime(), previousTs, myViewHolder.time_group);
        return view;
    }

    private class MyViewHolder {
        ConstraintLayout layout_item_received, layout_item_send;
        TextView name_received,name_send, message_received, time_received, message_send, time_send;
        ImageView image_received, image_send;
        TextView time_group;
    }
    private void setTimeTextVisibility(long ts1, long ts2, TextView timeText){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String time1=simpleDateFormat.format(ts1);
        String time2=simpleDateFormat.format(ts2);
        if(ts2==0){
            timeText.setVisibility(View.VISIBLE);
            timeText.setText(time1);
        }else {
            if(time1.equals(time2)){
                timeText.setVisibility(View.GONE);
                timeText.setText("");
            }else{
                timeText.setVisibility(View.VISIBLE);
                timeText.setText(time1);
            }
        }
    }

}
