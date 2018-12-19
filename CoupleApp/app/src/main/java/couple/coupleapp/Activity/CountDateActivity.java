package couple.coupleapp.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import couple.coupleapp.R;

public class CountDateActivity extends AppCompatActivity {

    ImageView myAvatar, ImageHeart;
    FrameLayout setting_layout, disconnect_layout;
    String firstday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_date);
        anhxa();
        Intent intent = getIntent();
        if (intent != null) {
            firstday = intent.getStringExtra("date");
        }
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
        firstday = "0";
    }
    public void selectedMenu(View v){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment=null;
        int id = v.getId();
        switch (id) {
            case R.id.home_menu:
                fragment=new CountdateFragment();
                break;
            case R.id.timline_menu:
                fragment=new TimelineFragment();
                break;
            case R.id.chat_menu:
                Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notification_menu:
                Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();
                break;
        }
        if(fragment!=null) {
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
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
                Toast.makeText(this, "goto backgroud", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_firstday:
                Toast.makeText(this, "go to firstday", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_password:
                Intent intent = new Intent(CountDateActivity.this, ChangePassword.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
