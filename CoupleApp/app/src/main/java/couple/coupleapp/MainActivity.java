package couple.coupleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import couple.coupleapp.Activity.CountDateActivity;
import couple.coupleapp.Activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences login;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=getSharedPreferences("logininfor",MODE_PRIVATE);

        String coupleid=login.getString("coupleid","");
        Boolean status=login.getBoolean("status",false);
        Log.e("Long", "couple: "+coupleid );
        Log.e("Long", "status: "+status );
        if("".equals(coupleid)||status==false) {
            intent= new Intent(this, LoginActivity.class);
        }else{
            intent=new Intent(this,CountDateActivity.class);
        }
        startActivity(intent);

    }
}
