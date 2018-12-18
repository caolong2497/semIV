package couple.coupleapp.Activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import couple.coupleapp.R;
public class RegisterActivity extends AppCompatActivity {
    RadioGroup radio_gender;
    RadioButton check_male,check_female;
    Button register;
    TextView txt_birthday;

    String birthday_infor;
    int year_lc =0;
    int month_lc = 0;
    int day_lc = 0;

    boolean gender_infor=true;//true là nam false là nữ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    anhxa();
    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //validate email
            //kiem tra email ton tai

            //validate cac hang muc khac
            //lay gioi tinh
            if(check_female.isChecked()){
                gender_infor=false;
                Toast.makeText(RegisterActivity.this, "Nữ", Toast.LENGTH_SHORT).show();
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
    private void anhxa(){
        radio_gender=(RadioGroup) findViewById(R.id.rd_gender);
        check_male=(RadioButton) findViewById(R.id.rd_male);
        check_female=(RadioButton) findViewById(R.id.rd_female);
        register=(Button) findViewById(R.id.btn_register);
        txt_birthday=(TextView) findViewById(R.id.regis_birthday);
    }

    private void getDatePicker() {
        if (year_lc == 0) {
            Calendar cal = Calendar.getInstance();
            year_lc = cal.get(Calendar.YEAR);
            month_lc = cal.get(Calendar.MONTH);
            day_lc = cal.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog pickerDialog=new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_lc=year;
                month_lc=month;
                day_lc=dayOfMonth;
            }
        },year_lc,month_lc,day_lc);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }
}
