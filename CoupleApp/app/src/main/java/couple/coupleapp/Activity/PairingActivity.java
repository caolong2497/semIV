package couple.coupleapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;
public class PairingActivity extends AppCompatActivity {
    private ImageButton btn_next;
    private SharedPreferences login;
    private ImageButton back_btn;
    private TextView logout,code_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        anhxa();
        action();
        Intent intent=getIntent();
        String code=intent.getStringExtra("code");
        code_edittext.setText(code);
    }
    private void anhxa(){
        btn_next=(ImageButton) findViewById(R.id.pariring_next);
        login= getSharedPreferences(Constant.SHARED_FILENAME_LOGIN,MODE_PRIVATE);
        back_btn=(ImageButton) findViewById(R.id.pairing_back);
        logout=(TextView) findViewById(R.id.pairing_logout);
        code_edittext=(TextView) findViewById(R.id.code_edittext);
    }
    private void action(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=login.edit();
                editor.putInt(Constant.COUPLE_ID_SHARED,1); //set giá trị coupleID cho user sau khi ghép cặp thành công

                editor.commit();
                Intent intent=new Intent(PairingActivity.this,firstdayActivity.class);
                intent.putExtra("flag",Constant.CONSTANT_CREATE);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
