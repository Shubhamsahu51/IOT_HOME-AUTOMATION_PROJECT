package com.example.iot_fan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView txt_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ON Button
        Button on_btn = findViewById(R.id.on);
        //OFF Button
        Button off_btn = findViewById(R.id.off);
        //Status Button
        Button status_btn = findViewById(R.id.status);
        //Status Text View
        txt_status = findViewById(R.id.fan_status);

        //On BTN is clicked
        on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the field1 option as 1
                String url = "https://api.thingspeak.com/update?api_key=WSGR7XG5YSB0USSP&field1=1";
                set_field1(url);
            }
        });

        //OFF BTN is clicked
        off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the field1 option as 0
                String url = "https://api.thingspeak.com/update?api_key=WSGR7XG5YSB0USSP&field1=0";
                set_field1(url);
            }
        });

        //Fan_status is clicked
        status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the data from APi given
                String url = "https://api.thingspeak.com/channels/1304000/feeds.json?api_key=ILN7TDNK4S14X9C0&results=2";
                get_field1(url);
            }
        });
    }
public void set_field1(String url)
{
    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
    StringRequest stringRequest = new StringRequest(Request.Method.GET,
            url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    make_toast("Fan is On!");
                }}, new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error){
                    make_toast("Error");
        }
    });
    queue.add(stringRequest);
}
public void get_field1(String url)
{
    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
    StringRequest stringRequest = new StringRequest(Request.Method.GET,
            url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray feeds  = obj.getJSONArray("feeds");
                        JSONObject obj2 = feeds.getJSONObject(feeds.length()-1);
                        String field1 = obj2.getString("field1");
                        if (field1.equals("0"))
                        {
                            txt_status.setText("FAN is OFF!");
                        }else
                        {
                            txt_status.setText("FAN is ONN!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }}, new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error){
            make_toast("Error");
        }
    });
    queue.add(stringRequest);
}
public void make_toast(String resp)
{
    Toast.makeText(this,resp,Toast.LENGTH_SHORT).show();
}
}
