package couple.coupleapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ChangePassword extends AppCompatActivity {
    private ImageButton password_back_btn, password_save_btn;
    private EditText current_pass_edittext, new_pass_edittext;
    private String currentpass, newpass, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        anhxa();

        password_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        password_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpass = current_pass_edittext.getText().toString();
                newpass = new_pass_edittext.getText().toString();

                String message="";
                if (Validate.checkIsEmpty(currentpass) || Validate.checkIsEmpty(newpass)) {
                    message="Hãy Điền Đầy Đủ Thông Tin";
                } else if (!Validate.checkLength(currentpass,6,20)||!Validate.checkLength(newpass,6,20)) {
                   message="Mật khẩu không được nhỏ hơn 6 kí tự và lớn hơn 20 kí tự";
                } else if (newpass.equals(currentpass)) {
                    message="Mật khẩu hiện tại và mật khẩu mới đang giống nhau";
                }
                if("".equals(message)){
                    currentpass = Utils.encodeText(currentpass);
                    newpass = Utils.encodeText(newpass);
                    url = Constant.URL_HOSTING + Constant.URL_UPDATE_PASS;
                    updatePassword(url, Constant.MY_USER_ID, currentpass, newpass);
                }else{
                    Toast.makeText(ChangePassword.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhxa() {
        current_pass_edittext = (EditText) findViewById(R.id.change_current_pass);
        new_pass_edittext = (EditText) findViewById(R.id.change_new_pass);
        password_back_btn = (ImageButton) findViewById(R.id.password_back);
        password_save_btn = (ImageButton) findViewById(R.id.password_save);
    }

    private void updatePassword(String url, int userid, String currentpass, String newpass) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userid", userid);
            jsonBody.put("currentpass", currentpass);
            jsonBody.put("newpass", newpass);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String kq = response.getString("result");
                        if (Constant.RESULT_FALSE.equals(kq)) {
                            Toast.makeText(ChangePassword.this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                        } else if (Constant.RESULT_TRUE.equals(kq)) {
                            Toast.makeText(ChangePassword.this, "Update Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(ChangePassword.this, "Update thất bại,thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ChangePassword.this, "Update thất bại,thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

