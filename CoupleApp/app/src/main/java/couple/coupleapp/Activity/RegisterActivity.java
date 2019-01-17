package couple.coupleapp.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Random;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.EmailValidator;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.Common.Validate;
import couple.coupleapp.R;

public class RegisterActivity extends AppCompatActivity {
    RadioGroup radio_gender;
    RadioButton check_male, check_female;
    Button register, reset;
    TextView txt_birthday;
    TextView txt_mail;
    TextView txt_name;
    TextView noti_email;
    TextView txt_pass;
    int year_lastchoise, month_lastchoise, day_lastchoise;
    ImageButton back_btn;
    String birthday_infor;
    String email, fullname, pass;

    SimpleDateFormat simpleDateFormat;
    long timenow;
    Calendar cal;

    EmailValidator emailValidator;

    boolean gender_infor = true;//true là nam false là nữ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        action();
    }

    private void action() {
        emailValidator = new EmailValidator();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_mail.setText("");
                txt_name.setText("");
                txt_birthday.setText("");
                txt_pass.setText("");
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get info
                email = convertString(txt_mail.getText().toString());
                fullname = convertString(txt_name.getText().toString());
                birthday_infor = txt_birthday.getText().toString();
                pass = convertString(txt_pass.getText().toString());
                //validate email
                if (email.equals("") || fullname.equals("") || birthday_infor.equals("") || pass.equals("")) {
                    noti_email.setText("Thiếu thông tin đăng ký");
                } else if (!emailValidator.validateEmail(email)) {
                    noti_email.setText("Email không đúng định dạng.");
                } else if (!Validate.checkLength(pass, 6, 20)) {
                    noti_email.setText("Mật khẩu phải lớn hơn 6 kí tự và nhỏ hơn 20 kí tự");
                } else {
                    String message = Validate.validateLogin(email, pass);
                    if ("".equals(message)) {
                        noti_email.setText("");
                        //lay gioi tinh
                        if (check_female.isChecked()) {
                            gender_infor = false;
                        }
                        String code = (10000+new Random().nextInt(999999))+"";
                        createUser(Constant.URL_HOSTING + Constant.URL_CREATE_USER, fullname, email, pass, gender_infor, birthday_infor, code);
                    }

                }


            }
        });

        txt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker();


            }
        });
    }

    private void createUser(String url, String fullname, String email, String pass, boolean gender_infor, String birthday_infor, String code) {

        JSONObject postparams = new JSONObject();
        try {
            //set variable to post
            postparams.put("name", fullname);
            postparams.put("gmail", email);
            postparams.put("password", Utils.encodeText(pass));
            postparams.put("gender", gender_infor);
            postparams.put("birthday", birthday_infor);
            postparams.put("coupleID", Constant.DEFEAULT_COUPLEID);
            postparams.put("avatar", Constant.STATE_IMAGE_DEFAULT);
            postparams.put("code", code);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String result = response.getString("result");
                        if (Constant.RESULT_TRUE.equals(result)) {
                            Toast.makeText(RegisterActivity.this, "Tạo người dùng thành công", Toast.LENGTH_SHORT).show();
                            // nếu tạo người dùng thành công thì quay về trang đăng nhập
//                            onBackPressed();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (Constant.RESULT_FALSE.equals(result)) {
                            Toast.makeText(RegisterActivity.this, "Có lỗi xảy ra, thử lại sau", Toast.LENGTH_SHORT).show();
                        } else {
                            noti_email.setText("Địa chỉ email đã tồn tại");
                            Log.e("hi", "onResponse:dia chi email da ton tai ");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("memmory", "onResponse: Lỗi json");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, "Lỗi server thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
            //đẩy request
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void anhxa() {
        back_btn = (ImageButton) findViewById(R.id.register_back_btn);
        txt_mail = (TextView) findViewById(R.id.regis_email);
        txt_name = (TextView) findViewById(R.id.regis_name);
        noti_email = (TextView) findViewById(R.id.noti_email);
        txt_pass = (TextView) findViewById(R.id.regis_password);
        radio_gender = (RadioGroup) findViewById(R.id.rd_gender);
        check_male = (RadioButton) findViewById(R.id.rd_male);
        check_female = (RadioButton) findViewById(R.id.rd_female);
        register = (Button) findViewById(R.id.btn_register);
        txt_birthday = (TextView) findViewById(R.id.regis_birthday);
        reset = (Button) findViewById(R.id.btn_reset);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        cal = Calendar.getInstance();
        year_lastchoise = cal.get(Calendar.YEAR);
        month_lastchoise = cal.get(Calendar.MONTH);
        day_lastchoise = cal.get(Calendar.DAY_OF_MONTH);
        //lấy thời gian hiện tại
        timenow = cal.getTimeInMillis();
    }

    private void getDatePicker() {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_lastchoise = year;
                month_lastchoise = month;
                day_lastchoise = dayOfMonth;
                txt_birthday.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year_lastchoise, month_lastchoise, day_lastchoise);
        pickerDialog.getDatePicker().setMaxDate(timenow);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    private String convertString(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }
}
