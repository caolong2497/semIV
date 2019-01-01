package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;
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
    String str_name,str_partnername;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_countdate, container, false);
        anhxa();
        init();

        countDate.setText(result_date + "");
        getData(url);
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
    }
    private void anhxa() {
        result_date = 0;
        countDate = (TextView) view.findViewById(R.id.count_date);
        myAvatar = (CircleImageView) view.findViewById(R.id.count_myimg);
        partnerAvatar=(CircleImageView) view.findViewById(R.id.count_partnerimg);
        myName=(TextView) view.findViewById(R.id.count_myname);
        partnerName=(TextView) view.findViewById(R.id.count_partnername);
    }

    private void showDialogUser() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_userinfor);
        close_dialog_btn = (ImageButton) dialog.findViewById(R.id.dialog_close);
        update_btn = (Button) dialog.findViewById(R.id.dialog_updateinfor);
        close_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
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
                            Constant.STARTDATE = response.getString("start");
                            str_name=response.getString("myname");
                            str_partnername=response.getString("partnername");
                            partner_id=response.getInt("partneruserID");
                            myName.setText(str_name);
                            partnerName.setText(str_partnername);

                            result_date = Utils.countDate(Constant.STARTDATE);
                            countDate.setText(result_date + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
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
