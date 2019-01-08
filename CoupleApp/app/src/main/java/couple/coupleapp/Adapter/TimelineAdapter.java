package couple.coupleapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;

import java.util.ArrayList;

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
            myViewHolder.name = (TextView) view.findViewById(R.id.timeline_name);
            myViewHolder.avatar = (CircleImageView) view.findViewById(R.id.timeline_avatar);
            myViewHolder.imagepost = (ImageView) view.findViewById(R.id.timeline_imagepost);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        myViewHolder.date.setText(list.get(position).getDate());

        //check xem ai la nguoi dang memory
        if(list.get(position).getUserid()==Constant.MYSELF.getUserid()){
            myViewHolder.avatar.setImageDrawable(Constant.MYSELF.getAvatar());
            myViewHolder.name.setText(Constant.MYSELF.getName());
        }else{
            myViewHolder.avatar.setImageDrawable(Constant.PARTNER.getAvatar());
            myViewHolder.name.setText(Constant.PARTNER.getName());
        }
        String linkimage=list.get(position).getImage();
        if(Constant.STATE_IMAGE_DEFAULT.equals(linkimage)){
            myViewHolder.imagepost.setVisibility(View.GONE);
        }else{
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

        CircleImageView avatar;

    }

    private class MenuPopupMenu implements PopupMenu.OnMenuItemClickListener {
        int position;

        public MenuPopupMenu(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.menu_edit:
                    //code something
                    break;
                case R.id.menu_delete:
                    //code somthing
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
