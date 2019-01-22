package couple.coupleapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.Common.Validate;
import couple.coupleapp.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText email, code, newpass;
    private ImageButton email_next, email_back, code_next, code_back, newpass_save, newpass_back;
    private TextView email_error_msg, code_error_msg, newpass_error_msg;
    private FrameLayout checkCode_layout, newPass_layout;
    private String email_str, code_str;
    RequestQueue requestQueue;
    ProgressBar mProcessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        anhxa();
        email_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProcessBar.getVisibility() == View.INVISIBLE) {
                    email_str = email.getText().toString().trim();
                    if ("".equals(email_str)) {
                        email_error_msg.setText("Email không được để trống");
                    } else if (!Validate.checkFormat(email_str, Constant.REGEX_EMAIL)) {
                        email_error_msg.setText("Email không đúng định dạng");
                    } else {
                        email_error_msg.setText("");
                        checkEmail(email_str);
                    }
                }
            }
        });

        code_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_str = code.getText().toString().trim();
                if ("".equals(code_str)) {
                    code_error_msg.setText("Hãy điền đầy đủ thông tin");
                } else if (!Validate.checkLength(code_str, 4, 10)) {
                    code_error_msg.setText("Mã Không đúng");
                } else {
                    checkCode(email_str, code_str);
                }
            }
        });
        newpass_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = newpass.getText().toString().trim();
                if ("".equals(password)) {
                    newpass_error_msg.setText("Hãy điền đầy đủ thông tin");
                } else if (!Validate.checkLength(password, 6, 20)) {
                    newpass_error_msg.setText("Mật khẩu phải lớn hơn 6 kí tự và nhỏ hơn 20 kí tự");
                } else {
                    newpass_error_msg.setText("");
                    password = Utils.encodeText(password);
                    newPassword(email_str, password);
                }
            }
        });
        mProcessBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(checkCode_layout.getVisibility()==View.VISIBLE){
            checkCode_layout.setVisibility(View.GONE);
        }else if(newPass_layout.getVisibility()==View.VISIBLE){
            newPass_layout.setVisibility(View.GONE);
            checkCode_layout.setVisibility(View.VISIBLE);
        }else {
            super.onBackPressed();
        }
    }

    private void anhxa() {
        email = (EditText) findViewById(R.id.forgetPass_email);
        code = (EditText) findViewById(R.id.forgetPass_code);
        newpass = (EditText) findViewById(R.id.forgetPass_newpass);
        email_next = (ImageButton) findViewById(R.id.forgetPass_checkmail_next);
        code_next = (ImageButton) findViewById(R.id.forgetPass_checkcode_next);
        newpass_save = (ImageButton) findViewById(R.id.forgetPass_newpass_next);
        email_error_msg = (TextView) findViewById(R.id.error_email);
        code_error_msg = (TextView) findViewById(R.id.error_code);
        newpass_error_msg = (TextView) findViewById(R.id.error_newpass);
        checkCode_layout = (FrameLayout) findViewById(R.id.frame_checkcode);
        newPass_layout = (FrameLayout) findViewById(R.id.frame_newpass);
        mProcessBar = (ProgressBar) findViewById(R.id.progressBar_email);
        requestQueue = Volley.newRequestQueue(this);
        email_back = (ImageButton) findViewById(R.id.forgetPass_back);
        code_back = (ImageButton) findViewById(R.id.checkCode_back);
        newpass_back = (ImageButton) findViewById(R.id.newpass_back);
    }

    public void onClickBackBtn(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.forgetPass_back:
                gotoLogin();
                break;
            case R.id.checkCode_back:
                checkCode_layout.setVisibility(View.GONE);
                break;
            case R.id.newpass_back:
                newPass_layout.setVisibility(View.GONE);
                checkCode_layout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void checkEmail(String gmail) {
        mProcessBar.setVisibility(View.VISIBLE);
        String url = Constant.URL_HOSTING + Constant.URL_CHECKEMAIL + "/" + gmail;
        email_error_msg.setText("");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProcessBar.setVisibility(View.INVISIBLE);

                if (Constant.RESULT_TRUE.equals(response)) {
                    checkCode_layout.setVisibility(View.VISIBLE);
                } else {
                    email_error_msg.setText("Email không tồn tại");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProcessBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ForgetPasswordActivity.this, "Lỗi hệ thống,thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void checkCode(String gmail, String code) {
        mProcessBar.setVisibility(View.VISIBLE);
        String url = Constant.URL_HOSTING + Constant.URL_CHECKCODE + "/" + gmail + "/" + code;
        code_error_msg.setText("");
        StringRequest checkCode = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProcessBar.setVisibility(View.INVISIBLE);
                if (Constant.RESULT_TRUE.equals(response)) {
                    checkCode_layout.setVisibility(View.GONE);
                    newPass_layout.setVisibility(View.VISIBLE);
                } else {
                    code_error_msg.setText("Mã không đúng");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProcessBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ForgetPasswordActivity.this, "Lỗi hệ thống, thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(checkCode);
    }

    private void newPassword(String gmail, String password) {
        String url = Constant.URL_HOSTING + Constant.URL_NEWPASS;
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("gmail", gmail);
            postparams.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest createNewPass = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("result");
                    if (Constant.RESULT_TRUE.equals(result)) {
                        Toast.makeText(ForgetPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        gotoLogin();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPasswordActivity.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(createNewPass);
    }

    private void gotoLogin() {
        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
