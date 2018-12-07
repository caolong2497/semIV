package client.doansemiv.coupleclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.net.CookieManager;

public class MainActivity extends AppCompatActivity {
    TextView content;
    EditText  fname;
    String text = "";
    private final String hosting = "http://10.0.3.2:8889/";
    CookieManager cookieManager;
    String getall=hosting+"user/getall";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = (TextView) findViewById(R.id.content);
        fname = (EditText) findViewById(R.id.name);

        Button regGet = (Button) findViewById(R.id.btnRegisterGet);
        regGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.GET, getall,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                content.setText(response);
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }





}
