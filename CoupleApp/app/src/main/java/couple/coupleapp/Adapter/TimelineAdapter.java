package couple.coupleapp.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import couple.coupleapp.R;

import java.util.ArrayList;
import java.util.List;

import couple.coupleapp.entity.TimeLine;
import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineAdapter extends ArrayAdapter<TimeLine> {
    private Context mcontext;
    private int ResID;
    private ArrayList<TimeLine> list;

    public TimelineAdapter(Context context, int resource, ArrayList<TimeLine> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.ResID = resource;
        this.list = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MyViewHolder myViewHolder = new MyViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.ResID, null);
            myViewHolder.name = (TextView) view.findViewById(R.id.timeline_name);
            myViewHolder.date = (TextView) view.findViewById(R.id.timeline_datecreate);
            myViewHolder.caption = (TextView) view.findViewById(R.id.timeline_caption);
            myViewHolder.countCmt = (TextView) view.findViewById(R.id.timeline_countcmt);
            myViewHolder.name = (TextView) view.findViewById(R.id.timeline_name);
            myViewHolder.avatar = (CircleImageView) view.findViewById(R.id.timeline_avatar);
            myViewHolder.imagepost = (ImageView) view.findViewById(R.id.timeline_imagepost);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        myViewHolder.avatar.setImageResource(R.drawable.avatar_boy);
        myViewHolder.name.setText(list.get(position).getName());
        myViewHolder.date.setText(list.get(position).getDate());
        myViewHolder.imagepost.setImageResource(R.drawable.backgroundlove);
        myViewHolder.caption.setText(list.get(position).getCaption());
        myViewHolder.countCmt.setText(list.get(position).getCountcommnet()+"");
        return view;
    }

    private static class MyViewHolder {
        TextView name, date, caption, countCmt;
        ImageView  imagepost;
        CircleImageView avatar;

    }
}
