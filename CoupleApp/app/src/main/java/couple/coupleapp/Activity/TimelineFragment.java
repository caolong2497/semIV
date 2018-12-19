package couple.coupleapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import couple.coupleapp.Adapter.TimelineAdapter;
import couple.coupleapp.R;
import couple.coupleapp.entity.TimeLine;

public class TimelineFragment extends Fragment {
    public ArrayList<TimeLine> list = new ArrayList<>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        init();
        listView = (ListView) view.findViewById(R.id.listview_timeline);
        TimelineAdapter adapter = new TimelineAdapter(getContext(), R.layout.timeline_layout, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),DetailMemoryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void init() {
        list.add(new TimeLine(1, "123", "Long", "2018-01-01", "kaka", "chao em co gai thang muo1i", 123));
        list.add(new TimeLine(1, "1243", "Long123", "2018-01-01", "kaka", "chao em co gai thang muoi2", 123));
        list.add(new TimeLine(1, "1243", "Long1", "2018-01-01", "kaka", "chao em co gai thang muoi3", 123));
        list.add(new TimeLine(1, "123", "Long1", "2018-01-01", "kaka", "chao em co gai thang muoi4", 123));
        list.add(new TimeLine(1, "123", "Lon1g", "2018-01-01", "kaka", "chao em co gai thang muoi5", 123));

    }
}