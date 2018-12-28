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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import couple.coupleapp.R;

public class firstdayActivity extends AppCompatActivity {
    int year_lastchoise, month_lastchoise, day_lastchoise;
    long firstday = 0;
    EditText firstday_edit;
    Calendar cal;
    ImageButton next_btn;
    SimpleDateFormat simpleDateFormat;
    long timenow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstday);
        anhxa();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        firstday_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("date", "onCreate2: " + firstday);
                if (firstday != 0) {
                    //lưu hạng mục startDate cho bảng couple
                    //lưu thành công thì chuyển sang màn hình chính
                    Intent intent = new Intent(firstdayActivity.this, CountDateActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(firstdayActivity.this, "Hãy chọn ngày bắt đầu yêu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDatePicker() {

        if (year_lastchoise == 0) {
            cal = Calendar.getInstance();
            year_lastchoise = cal.get(Calendar.YEAR);
            month_lastchoise = cal.get(Calendar.MONTH);
            day_lastchoise = cal.get(Calendar.DAY_OF_MONTH);
            timenow = cal.getTimeInMillis();
            Log.e("Firstday", "Now: " + timenow + "->" + simpleDateFormat.format(timenow));
        }
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    cal.set(year, month, dayOfMonth);
                    year_lastchoise = year;
                    month_lastchoise = month;
                    day_lastchoise = dayOfMonth;
                    firstday = cal.getTimeInMillis();
                    firstday_edit.setText(simpleDateFormat.format(cal.getTime()));
                Log.e("Firstday", "choise: " + firstday + "->" + simpleDateFormat.format(firstday));
            }
        }, year_lastchoise, month_lastchoise, day_lastchoise);

        pickerDialog.getDatePicker().setMaxDate(timenow);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    private void anhxa() {

        firstday_edit = (EditText) findViewById(R.id.firstday_started);
        next_btn = (ImageButton) findViewById(R.id.firstday_next);
    }
}
