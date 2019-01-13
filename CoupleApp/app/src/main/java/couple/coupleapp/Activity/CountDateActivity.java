package couple.coupleapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;

public class CountDateActivity extends AppCompatActivity {
    private int lastItemSelected;
    private FrameLayout setting_layout, disconnect_layout;
    private ImageButton home_btn, timeline_btn;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_date);
        anhxa();
        init();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CountdateFragment fragmentC = new CountdateFragment();
        fragmentTransaction.replace(R.id.frame_content, fragmentC);
        fragmentTransaction.commit();
    }


    private void init() {
        lastItemSelected = R.id.home_menu;
        sharedPreferences = getSharedPreferences(Constant.SHARED_FILENAME_LOGIN, MODE_PRIVATE);
        Constant.MY_USER_ID = sharedPreferences.getInt(Constant.MY_USERID_SHARED, 0);
        Constant.MY_COUPLE_ID = sharedPreferences.getInt(Constant.COUPLE_ID_SHARED, 0);
        lastItemSelected = 0;
        Constant.CHECK_FIRST_COUNTDATE=0;
    }

    private void anhxa() {
        setting_layout = (FrameLayout) findViewById(R.id.setting_frame);
        disconnect_layout = (FrameLayout) findViewById(R.id.disconnect_frame);
        home_btn = (ImageButton) findViewById(R.id.home_menu);
        timeline_btn = (ImageButton) findViewById(R.id.timline_menu);
    }

    public void selectedMenu(View v) {
        int id = v.getId();
        if (lastItemSelected != id) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = null;
            switch (id) {
                case R.id.home_menu:
                    fragment = new CountdateFragment();
                    break;
                case R.id.timline_menu:
                    fragment = new TimelineFragment();
                    break;
                case R.id.chat_menu:
                    fragment = new ChatFragment();
                    break;
                case R.id.notification_menu:
                    fragment = new NotificationFragment();
                    break;
            }
            lastItemSelected = id;
            if (fragment != null) {
                fragmentTransaction.replace(R.id.frame_content, fragment);
                fragmentTransaction.commit();
            }
        }
    }

    public void choiseOption(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.count_setting:
                setting_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.disconnect_back:
                disconnect_layout.setVisibility(View.GONE);
                break;
            case R.id.disconnect_accept:
                disconnectPartner();
                break;
            case R.id.setting_disconnect:
                disconnect_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.setting_close:
                setting_layout.setVisibility(View.INVISIBLE);
                break;
            case R.id.setting_logout:
                logout();
                break;
            case R.id.setting_backgroundimg:
                intent = new Intent(this, UploadBackgroundActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_firstday:
                intent =new Intent(this,firstdayActivity.class);
                intent.putExtra("flag",Constant.CONSTANT_UPDATE);
                startActivity(intent);
                break;
            case R.id.setting_password:
                intent = new Intent(CountDateActivity.this, ChangePassword.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void disconnectPartner() {
        String url=Constant.URL_HOSTING+Constant.URL_DISCONNECT+"/"+Constant.MY_COUPLE_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Constant.RESULT_TRUE.equals(response)) {
                    logout();
                } else {
                    Toast.makeText(CountDateActivity.this, "Có lỗi xảy ra,thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountDateActivity.this, "Có lỗi xảy ra,thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
