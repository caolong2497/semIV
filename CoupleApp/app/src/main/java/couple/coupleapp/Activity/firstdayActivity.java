package couple.coupleapp.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;

public class firstdayActivity extends AppCompatActivity {
    int year_lastchoise, month_lastchoise, day_lastchoise;
    String firstday;
    EditText firstday_edit;
    Calendar cal;
    ImageButton next_btn, back_btn;
    SimpleDateFormat simpleDateFormat;
    long timenow;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstday);
        anhxa();
        init();
        //check xem màn hình ở trạng thái chỉnh sửa hay tạo mới
        if (Constant.FLAG_STARTDATE == Constant.CONSTANT_CREATE) {
            //chỉnh sửa thì hiện nút back
            back_btn.setVisibility(View.GONE);
        }
        //bắt sự kiện click vào chọn ngày
        firstday_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker();
            }
        });
        //bắt sự kiện click nút back
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //bắt sự kiện submit
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy giá trị từ edittext trên màn hình
                firstday = firstday_edit.getText().toString();
                //gán giá trị cho url
                url = Constant.URL_HOSTING + Constant.URL_UPDATE_STARTDATE + Constant.MY_COUPLE_ID + "/" + firstday;
                //gọi service
                updateStartDate(url);
                Log.e("long", "onClick: "+url );

            }
        });
    }

    private void init() {
        //khởi tạo format cho date
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //khởi tạo đối tượng calendar
        cal = Calendar.getInstance();
        //lấy thời gian hiện tại
        getCurrentTime();
        //check trạng thái màn hình
        if (Constant.FLAG_STARTDATE == Constant.CONSTANT_UPDATE) {
            //trạng thái update thì lấy ngày bắt đầu là ngày trong DB
            firstday_edit.setText(Constant.STARTDATE);
        } else {
            //trạng thái create thì lấy ngày bắt đầu là ngày hiện tại
            firstday_edit.setText(simpleDateFormat.format(cal.getTime()));
        }

    }

    private void getDatePicker() {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(year, month, dayOfMonth);
                year_lastchoise = year;
                month_lastchoise = month;
                day_lastchoise = dayOfMonth;
                firstday_edit.setText(simpleDateFormat.format(cal.getTime()));
            }
        }, year_lastchoise, month_lastchoise, day_lastchoise);
        //giới hạn lớn nhất có thể chọn là ngày hiện tại
        pickerDialog.getDatePicker().setMaxDate(timenow);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    private void anhxa() {
        firstday_edit = (EditText) findViewById(R.id.firstday_started);
        next_btn = (ImageButton) findViewById(R.id.firstday_next);
        back_btn = (ImageButton) findViewById(R.id.firstday_back);
    }

    /**
     * update ngày bắt đầu
     *
     * @param url đường dẫn gọi service
     */
    public void updateStartDate(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Constant.RESULT_TRUE.equals(response)) {
                    Toast.makeText(firstdayActivity.this, "Update ngày bắt đầu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(firstdayActivity.this, CountDateActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(firstdayActivity.this, "Update thất bại,thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(firstdayActivity.this, "Update thất bại,thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * lấy ngày hiện tại
     */
    private void getCurrentTime() {
        year_lastchoise = cal.get(Calendar.YEAR);
        month_lastchoise = cal.get(Calendar.MONTH);
        day_lastchoise = cal.get(Calendar.DAY_OF_MONTH);
        timenow = cal.getTimeInMillis();
    }


}
