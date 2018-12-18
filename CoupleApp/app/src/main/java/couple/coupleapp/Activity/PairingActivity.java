package couple.coupleapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import couple.coupleapp.R;
public class PairingActivity extends AppCompatActivity {
    private ImageButton btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        anhxa();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PairingActivity.this,firstdayActivity.class);
                startActivity(intent);
            }
        });
    }
    private void anhxa(){
        btn_next=(ImageButton) findViewById(R.id.pariring_next);
    }
}
