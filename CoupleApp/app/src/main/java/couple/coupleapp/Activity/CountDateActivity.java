package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import couple.coupleapp.R;

public class CountDateActivity extends AppCompatActivity {
    TextView countDate;
    ImageView myAvatar, ImageHeart;
    //    ImageButton btn_setting_open, btn_setting_back,btn_disconnect_back;
    FrameLayout setting_layout, disconnect_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_date);
        anhxa();
        Intent intent = getIntent();
        String time_str = intent.getStringExtra("date");
        long timefirst = Long.parseLong(time_str);
        Calendar calendar = Calendar.getInstance();
        long timenow = calendar.getTimeInMillis();
        long day = (timenow - timefirst) / (1000 * 60 * 60 * 24);
        countDate.setText(day + "");
//        Toast.makeText(this, "time:"+time_str, Toast.LENGTH_SHORT).show();

//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");;
//        Log.e("Count", "timenow: "+timenow+"\t format:"+simpleDateFormat.format(timenow) );
//        Log.e("Count", "choise: "+timefirst+"\t format:"+simpleDateFormat.format(timefirst) );
//        Log.e("Count", "ket qua: "+(timenow-timefirst)/(1000*60*60*24) );
//        Log.e("Count", "ket qua2: "+day );

        ImageHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDateActivity.this, TimeLineActivity.class);
                startActivity(intent);
            }
        });

        myAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUser();

            }
        });
    }

    private void anhxa() {
//        btn_setting_back = (ImageButton) findViewById(R.id.setting_close);
//        btn_disconnect_back=(ImageButton) findViewById(R.id.disconnect_back);
//        btn_setting_open = (ImageButton) findViewById(R.id.count_setting);
        myAvatar = (ImageView) findViewById(R.id.count_myimg);
        countDate = (TextView) findViewById(R.id.count_date);
        ImageHeart = (ImageView) findViewById(R.id.heart_img);
        setting_layout = (FrameLayout) findViewById(R.id.setting_frame);
        disconnect_layout = (FrameLayout) findViewById(R.id.disconnect_frame);
    }

    private void showDialogUser() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_userinfor);
        dialog.show();
    }

    public void choiseOption(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.count_setting:
                setting_layout.setVisibility(View.VISIBLE);
                Toast.makeText(this, "setting touch", Toast.LENGTH_SHORT).show();
                break;
            case R.id.disconnect_back:
                disconnect_layout.setVisibility(View.GONE);
                Toast.makeText(this, "back disconnect", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_disconnect:
                disconnect_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.setting_close:
                setting_layout.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "touch close", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_logout:
                Toast.makeText(this, "goto logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_backgroundimg:
                Toast.makeText(this, "goto backgroud", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_firstday:
                Toast.makeText(this, "go to firstday", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_password:
                Intent intent=new Intent(CountDateActivity.this,ChangePassword.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
