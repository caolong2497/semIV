package couple.coupleapp.Activity;

import android.content.Intent;
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

import couple.coupleapp.R;

public class CountDateActivity extends AppCompatActivity {
    int lastItemSelected;
    FrameLayout setting_layout, disconnect_layout;
    ImageButton home_btn, timeline_btn;
    String firstday;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_date);
        anhxa();
        intent = getIntent();
        if (intent != null) {
            firstday = intent.getStringExtra("date");
        }
        lastItemSelected = R.id.home_menu;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CountdateFragment fragmentC = new CountdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", firstday);
        fragmentC.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_content, fragmentC);
        fragmentTransaction.commit();

    }

    private void anhxa() {
        setting_layout = (FrameLayout) findViewById(R.id.setting_frame);
        disconnect_layout = (FrameLayout) findViewById(R.id.disconnect_frame);
        home_btn = (ImageButton) findViewById(R.id.home_menu);
        timeline_btn = (ImageButton) findViewById(R.id.timline_menu);
        firstday = "0";
        lastItemSelected = 0;
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
}
