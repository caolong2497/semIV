package couple.coupleapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;
import couple.coupleapp.entity.Comment_Model;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends ArrayAdapter<Comment_Model> {
    Context mcontext;
    int ResId;
    List<Comment_Model> list;
    public CommentAdapter(Context context, int resource,ArrayList<Comment_Model> objects) {
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

        //modify nội dung hiển thị thông tin user
        Drawable avatar=Constant.MYSELF.getAvatar();
        String name=Constant.MYSELF.getName();
        int id=list.get(position).getUserId();
        if(id!=Constant.MY_USER_ID){
            avatar=Constant.PARTNER.getAvatar();
            name=Constant.PARTNER.getName();
        }
        //set nội dung lấy được lên màn hình
        myViewHolder.avatar.setImageDrawable(avatar);
        myViewHolder.username.setText(name);
        myViewHolder.date.setText(list.get(position).getTime());
        myViewHolder.content.setText(list.get(position).getContent());
        return view;
    }
    private class MyViewHolder{
        TextView username,content,date;
        CircleImageView avatar;
    }
}
