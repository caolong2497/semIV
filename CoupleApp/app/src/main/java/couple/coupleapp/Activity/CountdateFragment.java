package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private TextView countDate, myName, partnerName, dialog_name;
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
    private ImageView image, dialog_background, dialog_avatar;

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
                showDialogUser(0);
            }
        });
        partnerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUser(1);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        //check biến hình ảnh trong
        if (Constant.MYSELF!=null) {
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
        //Lấy tổng số ngày yêu
        result_date = Utils.countDate(Constant.STARTDATE);

        //set hiển thị lên màn hình
        myName.setText(Constant.MYSELF.getName());
        partnerName.setText(Constant.PARTNER.getName());
        countDate.setText(result_date + "");
        myAvatar.setImageDrawable(Constant.MYSELF.getAvatar());
        partnerAvatar.setImageDrawable(Constant.PARTNER.getAvatar());
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
        myAvatar.setImageResource(R.drawable.no_avatar);
        partnerAvatar.setImageResource(R.drawable.no_avatar);
        countdate_background.setBackgroundResource(R.drawable.backgroundlove);
    }

    // hiển thị dialog thông tin người dùng
    private void showDialogUser(final int flag) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_userinfor);
        close_dialog_btn = (ImageButton) dialog.findViewById(R.id.dialog_close);
        update_btn = (Button) dialog.findViewById(R.id.dialog_updateinfor);
        dialog_background = (ImageView) dialog.findViewById(R.id.dialog_user_background);
        dialog_avatar = (ImageView) dialog.findViewById(R.id.dialog_user_avatar);
        dialog_name = (TextView) dialog.findViewById(R.id.dialog_user_name);
        dialog_background.setImageDrawable(Constant.IMG_BACKGROUND);
        dialog_background.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (flag == 0) {
            dialog_avatar.setImageDrawable(Constant.MYSELF.getAvatar());
            dialog_avatar.setScaleType(ImageView.ScaleType.FIT_XY);
            dialog_name.setText(Constant.MYSELF.getName());
            update_btn.setText("Chỉnh sửa thông tin");
        } else {
            dialog_avatar.setImageDrawable(Constant.PARTNER.getAvatar());
            dialog_avatar.setScaleType(ImageView.ScaleType.FIT_XY);
            dialog_name.setText(Constant.PARTNER.getName());
            update_btn.setText("Thông tin chi tiết");
        }

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
                int userid = Constant.MY_USER_ID;
                if (flag != 0) {
                    userid = Constant.PARTNER.getUserid();
                }
                dialog.hide();
                Intent intent = new Intent(getContext(), UpdateUserActivity.class);
                intent.putExtra("userId", userid);
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
                            partnerName.setText(str_partnername);
                            result_date = Utils.countDate(Constant.STARTDATE);
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
                            //set image vào biến static
                            Constant.IMG_BACKGROUND = image.getDrawable();
                            Log.e("haha", "background image: " + image.getDrawable());


                            //code fix tạm thời lỗi load màn hình lần đầu,drawable null
                            if (Constant.MYSELF.getAvatar()==null||Constant.PARTNER.getAvatar()==null) {
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
