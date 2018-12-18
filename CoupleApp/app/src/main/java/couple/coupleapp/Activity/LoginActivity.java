package couple.coupleapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import couple.coupleapp.R;


public class LoginActivity extends AppCompatActivity {
    EditText Ed_Email, Ed_Password;
    TextView txt_regis;
    Button btn_login;
    String email, password;
//    String url_login = Constant.HOSTING + "user/login/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Ed_Email.getText().toString();
                password = Ed_Password.getText().toString();
                if("caolong".equals(email)&&"123".equals(password)){
                    Intent intent=new Intent(LoginActivity.this,PairingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
               // register();
//                String url = url_login + email + "/" + password;
//                Login(url);
            }
        });
        txt_regis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void anhxa() {
        Ed_Email = (EditText) findViewById(R.id.login_email);
        Ed_Password = (EditText) findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_regis=(TextView) findViewById(R.id.txt_register);
    }


//    private void Login(String url) {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(LoginActivity.this, "Login thành công", Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(LoginActivity.this, "login that bai", Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
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


