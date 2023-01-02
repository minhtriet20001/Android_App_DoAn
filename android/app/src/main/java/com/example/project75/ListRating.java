package com.example.project75;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListRating extends AppCompatActivity {

    ListView list_rating;
    ArrayAdapter<Obj_Rating> adapter;
    ArrayList<Obj_Rating> list;
    String responseBody, email;
    private RequestBody fromBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rating);
        list_rating = findViewById(R.id.list_rating);
        list = new ArrayList<>();
        email = getIntent().getStringExtra("email");
        loadData();

        adapter = new ArrayAdapter<Obj_Rating>(
                this,
                R.layout.item_rating,
                R.id.tv_nameCustomer,
                list
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                TextView nameCustomer = itemView.findViewById(R.id.tv_nameCustomer);
                TextView rate = itemView.findViewById(R.id.tv_rate);
                TextView comment = itemView.findViewById(R.id.tv_comment);
                TextView time = itemView.findViewById(R.id.tv_time);

                nameCustomer.setText("Tên khách hàng: "+list.get(position).getName());
                rate.setText("Đánh giá: "+list.get(position).getRate());
                comment.setText("Ý kiến: "+list.get(position).getComment());
                time.setText(list.get(position).getTime());
                return  itemView;
            }
        };
        list_rating.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tat:
                Intent intent = new Intent(ListRating.this, MainActivity.class);
                intent.putExtra("re_email", email);
                startActivity(intent);
        }
        return true;
    }

    private void loadData() {
        OkHttpClient client = new OkHttpClient();
        fromBody = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder().url("http://10.0.2.2/app/get_rating.php").post(fromBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);
                    JSONArray jsonArray = json.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name, rate, comment, time;

                        name = jsonObject.getString("name");
                        rate = jsonObject.getString("rate");
                        comment = jsonObject.getString("comment");
                        time = jsonObject.getString("time");
                        list.add(new Obj_Rating(name,rate,comment,time));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}