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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;

public class CountDateActivity extends AppCompatActivity {
    int lastItemSelected;
    FrameLayout setting_layout, disconnect_layout;
    ImageButton home_btn, timeline_btn;
    String firstday;
    Intent intent;
    String url;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_date);
        anhxa();
        init();
        lastItemSelected = R.id.home_menu;
        getData(url);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        CountdateFragment fragmentC = new CountdateFragment();
//        fragmentTransaction.replace(R.id.frame_content, fragmentC);
//        fragmentTransaction.commit();
    }

    private void init() {
        sharedPreferences = getSharedPreferences(Constant.SHARED_FILENAME_LOGIN, MODE_PRIVATE);
        Constant.MY_USER_ID = sharedPreferences.getInt(Constant.MY_USERID_SHARED, 0);
        Constant.MY_COUPLE_ID = sharedPreferences.getInt(Constant.COUPLE_ID_SHARED, 0);
        url = Constant.URL_HOSTING + Constant.URL_GETCOUPLEBYID + Constant.MY_COUPLE_ID;
        firstday = "0";
        lastItemSelected = 0;
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
                    Log.e("Long", "selectedMenu: home");
                    break;
                case R.id.timline_menu:
                    fragment = new TimelineFragment();
                    Log.e("Long", "selectedMenu: timeline");
                    break;
                case R.id.chat_menu:
                    Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.notification_menu:
                    Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "goto logout", Toast.LENGTH_SHORT).show();

                break;
            case R.id.setting_backgroundimg:
                intent = new Intent(this, UploadBackgroundActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_firstday:
                Toast.makeText(this, "go to firstday", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_password:
                intent = new Intent(CountDateActivity.this, ChangePassword.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Constant.STARTDATE=response.getString("start");
                            Log.e("Long", "call service: " + Constant.STARTDATE);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            CountdateFragment fragmentC = new CountdateFragment();
                            fragmentTransaction.replace(R.id.frame_content, fragmentC);
                            fragmentTransaction.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        intent = new Intent(CountDateActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Log.e("request", "onResponse: lỗi");
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

}
