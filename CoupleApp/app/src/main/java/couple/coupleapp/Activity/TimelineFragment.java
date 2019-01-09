package couple.coupleapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import couple.coupleapp.Adapter.TimelineAdapter;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.Common.Utils;
import couple.coupleapp.R;
import couple.coupleapp.entity.TimeLine;

public class TimelineFragment extends Fragment {
    public ArrayList<TimeLine> list = new ArrayList<>();
    ListView listView;
    ImageButton add_memory_btn;
    Intent intent;
    View view;
    TimelineAdapter adapter;
    String url_getTimeline;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        anhxa();
        getDate(url_getTimeline);
        add_memory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), CreateMemoryActivity.class);
                startActivity(intent);
            }
        });
        adapter = new TimelineAdapter(getContext(), R.layout.timeline_layout, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getContext(), DetailMemoryActivity.class);
                intent.putExtra("memoryId",list.get(position).getMemoryId());
                startActivity(intent);
            }
        });
        return view;
    }

    private void anhxa() {
        url_getTimeline=Constant.URL_HOSTING+Constant.URL_GETMEMORY_ONCOUPLE+"/"+Constant.MY_COUPLE_ID;
        listView = (ListView) view.findViewById(R.id.listview_timeline);
        add_memory_btn = (ImageButton) view.findViewById(R.id.add_memory);

    }

    private void getDate(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String Memory_image = object.getString("image");
                        String Memory_time = object.getString("time");
                        String Memory_caption = object.getString("caption");
                        int MemoryId = object.getInt("memoryId");
                        int Memory_coutComment = object.getInt("countComment");
                        int Memory_userId = object.getInt("userId");
                        TimeLine timeLine = new TimeLine(MemoryId, Memory_time, Memory_image, Memory_caption, Memory_coutComment, Memory_userId);
                        list.add(timeLine);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi khởi tạo timeline", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
