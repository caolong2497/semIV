package couple.coupleapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import couple.coupleapp.R;
public class ChangePassword extends AppCompatActivity {
    ImageButton password_back_btn,password_save_btn;
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
    }
    private void anhxa(){
        password_back_btn =(ImageButton) findViewById(R.id.password_back);
        password_save_btn =(ImageButton) findViewById(R.id.password_save);
    }
}

