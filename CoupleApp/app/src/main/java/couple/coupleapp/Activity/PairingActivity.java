package couple.coupleapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;

public class PairingActivity extends AppCompatActivity {
    private ImageButton btn_next;
    private SharedPreferences login;
    private ImageButton back_btn;
    private TextView logout, mycode, partnercode;
    private RequestQueue requestQueue;

    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        anhxa();
        action();
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        userid = intent.getIntExtra("userId", 0);
        mycode.setText(code);
    }

    private void anhxa() {
        btn_next = (ImageButton) findViewById(R.id.pariring_next);
        login = getSharedPreferences(Constant.SHARED_FILENAME_LOGIN, MODE_PRIVATE);
        back_btn = (ImageButton) findViewById(R.id.pairing_back);
        logout = (TextView) findViewById(R.id.pairing_logout);
        mycode = (TextView) findViewById(R.id.my_code);
        partnercode = (TextView) findViewById(R.id.partner_code);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void action() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = partnercode.getText().toString();
                callPairing(code);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callPairing(String code) {
        String url = Constant.URL_HOSTING + Constant.URL_PAIRING + "/" + userid + "/" + code;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int coupleid = Integer.parseInt(response);
                if (coupleid == Constant.DEFEAULT_COUPLEID) {
                    Toast.makeText(PairingActivity.this, "Mã không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PairingActivity.this, "Ghép cặp thành công,chúc bạn vui vẻ", Toast.LENGTH_SHORT).show();
                    goToCoundate(coupleid);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PairingActivity.this, "Lỗi hệ thống,thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(stringRequest);

    }

//    private void checkUser() {
//        String url = Constant.URL_HOSTING + Constant.URL_GETUSERINFOR + "/" + userid;
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    int coupleid = response.getInt("coupleID");
//                    if (coupleid == Constant.DEFEAULT_COUPLEID) {
//                        Toast.makeText(PairingActivity.this, "Bạn chưa có cặp đôi", Toast.LENGTH_SHORT).show();
//                    } else {
//                        goToCoundate(coupleid);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(PairingActivity.this, "Lỗi hệ thống,thử lại sau", Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
//        requestQueue.add(stringRequest);
//
//    }

    private void goToCoundate(int coupleid) {
        SharedPreferences.Editor editor = login.edit();
        editor.putInt(Constant.MY_USERID_SHARED, userid);
        editor.putInt(Constant.COUPLE_ID_SHARED, coupleid); //set giá trị coupleID cho user sau khi ghép cặp thành công
        Constant.MY_COUPLE_ID = coupleid;
        editor.commit();
        Intent intent = new Intent(PairingActivity.this, firstdayActivity.class);
        intent.putExtra("flag", Constant.CONSTANT_CREATE);
        startActivity(intent);
    }
}
