package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;
import couple.coupleapp.entity.UserComon;
import de.hdodenhof.circleimageview.CircleImageView;

public class CountdateFragment extends Fragment {
    TextView countDate,myName,partnerName;
    CircleImageView myAvatar,partnerAvatar;
    int partner_id;
    View view;
    long result_date;
    ImageButton close_dialog_btn;
    Button update_btn;
    Dialog dialog;
    String url;
    String str_name,str_partnername,link_myAvatar,link_partnerAvatar;
    String link_image_background;
    RelativeLayout countdate_background;
    ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_countdate, container, false);
        anhxa();
        init();
        getData(url);
        //khi click vào ảnh đại diện thì hiển thị dialog
        myAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUser();
            }
        });
        return view;
    }
    private void init(){
        url = Constant.URL_HOSTING + Constant.URL_GET_DETAIL_COUPLE + Constant.MY_USER_ID+"/"+Constant.MY_COUPLE_ID;
        str_name="";
        str_partnername="";
        partner_id=0;
        link_image_background="";
        link_myAvatar="";
        link_partnerAvatar="";
    }



    private void anhxa() {
        result_date = 0;
        countDate = (TextView) view.findViewById(R.id.count_date);
        myAvatar = (CircleImageView) view.findViewById(R.id.count_myimg);
        partnerAvatar=(CircleImageView) view.findViewById(R.id.count_partnerimg);
        myName=(TextView) view.findViewById(R.id.count_myname);
        partnerName=(TextView) view.findViewById(R.id.count_partnername);
        countdate_background=(RelativeLayout) view.findViewById(R.id.background_countdate);
        image=(ImageView) view.findViewById(R.id.image_holder);
    }
    // hiển thị dialog thông tin người dùng
    private void showDialogUser() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_userinfor);
        close_dialog_btn = (ImageButton) dialog.findViewById(R.id.dialog_close);
        update_btn = (Button) dialog.findViewById(R.id.dialog_updateinfor);
        //đóng dialog
        close_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        //mở activity cập nhật thông tin người dùng
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateUserActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //Lấy thông tin từ response
                            Constant.STARTDATE = response.getString("start");
                            link_image_background=response.getString("coupleImage");
                            str_name=response.getString("myname");
                            str_partnername=response.getString("partnername");
                            partner_id=response.getInt("partneruserID");
                            link_myAvatar=response.getString("myimage");
                            link_partnerAvatar=response.getString("partnerimage");

                            //set hiển thị tên
                            myName.setText(str_name);
                            //hiển thị tên partner lên activity
                            partnerName.setText(str_partnername);
                            //Lấy tổng số ngày yêu
                            result_date = Utils.countDate(Constant.STARTDATE);
                            //Hiển thị tổng số ngày lên acitivity
                            countDate.setText(result_date + "");
                            //check xem link ảnh trên db là mặc định hay đã được người dùng thay đổi
                            //nếu link ảnh đã được update thì set ảnh là ảnh load từ link
                            if(!Constant.STATE_IMAGE_DEFAULT.equals(link_myAvatar)){
                                Picasso.get().load(link_myAvatar).into(myAvatar);
                            }

                            if(!Constant.STATE_IMAGE_DEFAULT.equals(link_partnerAvatar)){
                                Picasso.get().load(link_partnerAvatar).into(partnerAvatar);
                            }

                            //khởi tạo đối tượng User static
                            Constant.MYSELF=new UserComon(Constant.MY_USER_ID,str_name,myAvatar.getDrawable());
                            Constant.PARTNER=new UserComon(partner_id,str_partnername,partnerAvatar.getDrawable());


                            if(!Constant.STATE_IMAGE_DEFAULT.equals(link_image_background)){

                                Picasso.get().load(link_image_background).into(image, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        //nếu thành công thì set source cho background từ imageview
                                        countdate_background.setBackgroundDrawable(image.getDrawable());

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        //nếu thất bại thì báo lên màn hình
                                        Toast.makeText(getActivity(), "có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        } catch (JSONException e) {
                            Log.e("countdate", "exceptions" );
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("request", "onResponse: lỗi");
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

}
