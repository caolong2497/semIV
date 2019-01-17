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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


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
                String message_error=Validate.validateLogin(email,password);
                if("".equals(message_error)){
                    password=Utils.encodeText(password);
                    Login(email,password);
                }else{
                    Toast.makeText(LoginActivity.this, message_error, Toast.LENGTH_SHORT).show();
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
    private void Login(String email,String password) {
        String url = Constant.URL_HOSTING + Constant.URL_LOGIN ;
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("gmail", email);
            postparams.put("password", password);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        int userId = response.getInt("userID");
                        int coupleid = response.getInt("coupleID");
                        String code=null;
                        Intent intent;
                        if(Constant.DEFEAULT_COUPLEID==coupleid) {
                            code = response.getString("code");
                            // người dùng mơi đi đến màn hình ghép cặp
                            intent= new Intent(LoginActivity.this, PairingActivity.class);
                            intent.putExtra("code",code);
                            intent.putExtra("userId",userId);
                        }else{
                            //người dùng cũ đi đến màn hình chính(countDate)

                            //Lưu thông tin coupleid vào file Shared
                            SharedPreferences.Editor editor = login.edit();
                            editor.putInt(Constant.COUPLE_ID_SHARED, coupleid);
                            editor.putInt(Constant.MY_USERID_SHARED, userId);
                            editor.commit();
                            //chuyển màn hình
                             intent = new Intent(LoginActivity.this, CountDateActivity.class);
                        }
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Sai Tên Đăng Nhập Hoặc Mật Khẩu", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}


