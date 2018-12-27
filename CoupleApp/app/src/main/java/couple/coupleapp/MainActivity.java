package couple.coupleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import couple.coupleapp.Activity.CountDateActivity;
import couple.coupleapp.Activity.LoginActivity;
import couple.coupleapp.Common.Constant;

public class MainActivity extends AppCompatActivity {
    SharedPreferences login;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=getSharedPreferences(Constant.SHARED_FILENAME_LOGIN,MODE_PRIVATE);
        //check tồn tại session
        int coupleid=login.getInt(Constant.MY_USERID_SHARED,0);

        Log.e("Long", "couple: "+coupleid );
        if(coupleid==0){
            intent= new Intent(this, LoginActivity.class);
        }else{
            intent=new Intent(this,CountDateActivity.class);
        }
        startActivity(intent);

    }
}
