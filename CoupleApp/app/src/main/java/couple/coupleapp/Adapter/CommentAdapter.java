package couple.coupleapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import couple.coupleapp.R;
import couple.coupleapp.entity.Comment;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Context mcontext;
    int ResId;
    ArrayList<Comment> list;
    public CommentAdapter(Context context, int resource,ArrayList<Comment> objects) {
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
            myViewHolder.username = (TextView) view.findViewById(R.id.comment_name);
            myViewHolder.date = (TextView) view.findViewById(R.id.comment_dateccreate);
            myViewHolder.content = (TextView) view.findViewById(R.id.comment_content);
            myViewHolder.avatar = (CircleImageView) view.findViewById(R.id.comment_avatar);

            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        myViewHolder.avatar.setImageResource(R.drawable.avatar_boy);
        myViewHolder.username.setText(list.get(position).getUsername());
        myViewHolder.date.setText(list.get(position).getDate());
        myViewHolder.content.setText(list.get(position).getContent());
        return view;
    }
    private class MyViewHolder{
        TextView username,content,date;
        CircleImageView avatar;
    }
}
