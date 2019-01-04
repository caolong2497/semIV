package couple.coupleapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.Common.Validate;
import couple.coupleapp.R;


public class LoginActivity extends AppCompatActivity {
    EditText Ed_Email, Ed_Password;
    TextView txt_regis;
    Button btn_login;
    String email, password;

    SharedPreferences login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Ed_Email.getText().toString().trim();
                password = Ed_Password.getText().toString();
                if(Validate.validateLogin(email,password)){
                    password=Utils.encodeText(password);
                    Log.e("login", "password "+password );
                    String url = Constant.URL_HOSTING + Constant.URL_LOGIN + "/" + email + "/" + password;
                    Login(url);
                }else{
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });
        txt_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void anhxa() {
        login = getSharedPreferences(Constant.SHARED_FILENAME_LOGIN, MODE_PRIVATE);
        Ed_Email = (EditText) findViewById(R.id.login_email);
        Ed_Password = (EditText) findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_regis = (TextView) findViewById(R.id.txt_register);
    }


    private void Login(String url) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            int userId = response.getInt("userID");
                            int coupleid = response.getInt("coupleID");
                                //check xem là người dùng mới hay cũ,người dùng mới sẽ chưa có coupleid => coupleid= default
                                if (coupleid == Constant.DEFEAULT_COUPLEID) {
                                    // người dùng mơi đi đến màn hình ghép cặp
                                    Intent intent = new Intent(LoginActivity.this, PairingActivity.class);
                                    startActivity(intent);
                                } else {
                                    //người dùng cũ đi đến màn hình chính(countDate)

                                    //Lưu thông tin coupleid vào file Shared
                                    SharedPreferences.Editor editor = login.edit();
                                    editor.putInt(Constant.COUPLE_ID_SHARED, coupleid);
                                    editor.putInt(Constant.MY_USERID_SHARED, userId);
                                    editor.commit();

                                    //chuyển màn hình
                                    Intent intent = new Intent(LoginActivity.this, CountDateActivity.class);
                                    startActivity(intent);
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

//    private void Login(String url) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                            Toast.makeText(LoginActivity.this, "login thanh cong", Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(LoginActivity.this, "login that bai", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params=new HashMap<String,String>();
//                params.put("gmail",email);
//                params.put("password",password);
//                return params;
//            }
//        };
//                RequestQueue requestQueue=Volley.newRequestQueue(this);
//            requestQueue.add(stringRequest);
//    }


//    Button regGet = (Button) findViewById(R.id.btnRegisterGet);
//        regGet.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            StringRequest stringRequest=new StringRequest(Request.Method.GET, getall,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            content.setText(response);
//                        }
//                    },new Response.ErrorListener(){
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//                }
//            });
//            RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
//            requestQueue.add(stringRequest);
//        }
//    });


