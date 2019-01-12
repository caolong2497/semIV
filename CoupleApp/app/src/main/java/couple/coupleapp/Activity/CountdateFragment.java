package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;
import couple.coupleapp.entity.UserComon;
import de.hdodenhof.circleimageview.CircleImageView;

public class CountdateFragment extends Fragment {
    private TextView countDate, myName, partnerName;
    private CircleImageView myAvatar, partnerAvatar;
    private int partner_id;
    private View view;
    private long result_date;
    private ImageButton close_dialog_btn;
    private Button update_btn;
    private Dialog dialog;
    private String str_name, str_partnername, link_myAvatar, link_partnerAvatar;
    private String link_image_background;
    private RelativeLayout countdate_background;
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_countdate, container, false);
        anhxa();
        init();

        //khi click vào ảnh đại diện thì hiển thị dialog
        myAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUser();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        //check xem ứng dụng được bật do start ứng dụng hay do chuyển qua lại màn hình
        if (Constant.CHECK_FIRST_COUNTDATE!=0) {
            //nếu ứng dụng được bật do chuyển qua lại màn hình thì khởi tạo giao diện bằng dữ liệu static trước
            //để người dùng chuyển qua lại không bị hiển thị giao diện mặc định
            intialUI();
        }
        //sau đó lấy dữ liệu trên service về trả lại vào giao diện
        //nếu có update data thì gán lại vào biến static
        getData();
        super.onStart();
    }

    private void init() {

        str_name = "";
        str_partnername = "";
        partner_id = 0;
        link_image_background = "";
        link_myAvatar = "";
        link_partnerAvatar = "";
    }
    //khởi tạo hiển thị giao diện
    private void intialUI() {
        //set hiển thị tên
        myName.setText(Constant.MYSELF.getName());
        //hiển thị tên partner lên activity
        partnerName.setText(Constant.PARTNER.getName());
        //Lấy tổng số ngày yêu
        result_date = Utils.countDate(Constant.STARTDATE);
        //Hiển thị tổng số ngày lên acitivity
        countDate.setText(result_date + "");
        //set anh
        myAvatar.setImageDrawable(Constant.MYSELF.getAvatar());
        partnerAvatar.setImageDrawable(Constant.PARTNER.getAvatar());

        //set anh bia
        countdate_background.setBackgroundDrawable(Constant.IMG_BACKGROUND);
    }

    private void anhxa() {
        result_date = 0;
        countDate = (TextView) view.findViewById(R.id.count_date);
        myAvatar = (CircleImageView) view.findViewById(R.id.count_myimg);
        partnerAvatar = (CircleImageView) view.findViewById(R.id.count_partnerimg);
        myName = (TextView) view.findViewById(R.id.count_myname);
        partnerName = (TextView) view.findViewById(R.id.count_partnername);
        countdate_background = (RelativeLayout) view.findViewById(R.id.background_countdate);
        image = (ImageView) view.findViewById(R.id.image_holder);
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

    private void getData() {
        String url = Constant.URL_HOSTING + Constant.URL_GET_DETAIL_COUPLE + Constant.MY_USER_ID + "/" + Constant.MY_COUPLE_ID;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //Lấy thông tin từ response
                            Constant.STARTDATE = response.getString("start");
                            link_image_background = response.getString("coupleImage");
                            str_name = response.getString("myname");
                            str_partnername = response.getString("partnername");
                            partner_id = response.getInt("partneruserID");
                            link_myAvatar = response.getString("myimage");
                            link_partnerAvatar = response.getString("partnerimage");

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
                            if (!Constant.STATE_IMAGE_DEFAULT.equals(link_myAvatar)) {
                                Picasso.get().load(link_myAvatar).into(myAvatar);
                            }

                            if (!Constant.STATE_IMAGE_DEFAULT.equals(link_partnerAvatar)) {
                                Picasso.get().load(link_partnerAvatar).into(partnerAvatar);
                            }




                            if (!Constant.STATE_IMAGE_DEFAULT.equals(link_image_background)) {

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
                            //khởi tạo đối tượng User static
                            Constant.MYSELF = new UserComon(Constant.MY_USER_ID, str_name, myAvatar.getDrawable());
                            Constant.PARTNER = new UserComon(partner_id, str_partnername, partnerAvatar.getDrawable());
                            Log.e("haha", "My image: "+myAvatar.getDrawable() );
                            Log.e("haha", "Partner image: "+partnerAvatar.getDrawable() );
                            //set image vào biến static
                            Constant.IMG_BACKGROUND = image.getDrawable();
                            Log.e("haha", "background image: "+image.getDrawable() );


                            //code fix tạm thời lỗi load màn hình lần đầu,drawable null
                            if(Constant.CHECK_FIRST_COUNTDATE==0){
                                Constant.CHECK_FIRST_COUNTDATE=1;
                                CountdateFragment fragmentC = new CountdateFragment();
                                getFragmentManager().beginTransaction().replace(R.id.frame_content, fragmentC).commit();
                            }
                        } catch (JSONException e) {
                            Log.e("countdate", "exceptions");
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
