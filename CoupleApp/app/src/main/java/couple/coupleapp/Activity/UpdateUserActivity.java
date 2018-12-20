package couple.coupleapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import couple.coupleapp.R;
public class UpdateUserActivity extends AppCompatActivity {
    ImageButton updateuser_back_btn,updateuser_save_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        anhxa();
        updateuser_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void anhxa(){
        updateuser_back_btn=(ImageButton) findViewById(R.id.userinfor_back);
        updateuser_save_btn=(ImageButton) findViewById(R.id.userinfor_save);
    }
}
