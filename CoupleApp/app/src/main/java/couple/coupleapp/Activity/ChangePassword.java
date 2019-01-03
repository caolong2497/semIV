package couple.coupleapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.Common.Validate;
import couple.coupleapp.R;

public class ChangePassword extends AppCompatActivity {
    ImageButton password_back_btn, password_save_btn;
    EditText current_pass, new_pass;
    String currentpass, newpass, url;

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
                currentpass = current_pass.getText().toString();
                newpass = new_pass.getText().toString();

                if (Validate.checkIsEmpty(currentpass) || Validate.checkIsEmpty(newpass)) {
                    Toast.makeText(ChangePassword.this, "Hãy Điền Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                } else {
                    currentpass = Utils.encodeText(currentpass);
                    newpass = Utils.encodeText(newpass);
                    url = Constant.URL_HOSTING + Constant.URL_UPDATE_PASS + Constant.MY_USER_ID + "/" + currentpass + "/" + newpass;
                    updatePassword(url);
                }
            }
        });
    }

    private void anhxa() {
        current_pass = (EditText) findViewById(R.id.change_current_pass);
        current_pass = (EditText) findViewById(R.id.change_current_pass);
        current_pass = (EditText) findViewById(R.id.change_current_pass);
        current_pass = (EditText) findViewById(R.id.change_current_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        new_pass = (EditText) findViewById(R.id.change_new_pass);
        password_back_btn = (ImageButton) findViewById(R.id.password_back);
        password_save_btn = (ImageButton) findViewById(R.id.password_save);
    }

    private void updatePassword(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if ("0".equals(response)) {
                    Toast.makeText(ChangePassword.this, "Mật khẩu hiện tại không chính xác", Toast.LENGTH_SHORT).show();
                } else if ("1".equals(response)) {
                    Toast.makeText(ChangePassword.this, "Update Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ChangePassword.this, "Update thất bại,thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePassword.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

