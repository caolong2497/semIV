package couple.coupleapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import couple.coupleapp.Adapter.TimelineAdapter;
import couple.coupleapp.R;
import couple.coupleapp.entity.TimeLine;

public class TimeLineActivity extends AppCompatActivity {
    public ArrayList<TimeLine> list = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        init();
        listView = (ListView) findViewById(R.id.listview_timeline);
        TimelineAdapter adapter = new TimelineAdapter(this, R.layout.timeline_layout, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TimeLineActivity.this,DetailMemoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init() {
        list.add(new TimeLine(1, "123", "Long", "2018-01-01", "kaka", "chao em co gai thang muo1i", 123));
        list.add(new TimeLine(1, "1243", "Long123", "2018-01-01", "kaka", "chao em co gai thang muoi2", 123));
        list.add(new TimeLine(1, "1243", "Long1", "2018-01-01", "kaka", "chao em co gai thang muoi3", 123));
        list.add(new TimeLine(1, "123", "Long1", "2018-01-01", "kaka", "chao em co gai thang muoi4", 123));
        list.add(new TimeLine(1, "123", "Lon1g", "2018-01-01", "kaka", "chao em co gai thang muoi5", 123));

    }
}
