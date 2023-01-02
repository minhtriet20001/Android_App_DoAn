package com.example.project75;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class History extends AppCompatActivity {
    ListView list_history;
    ArrayAdapter<Obj_history> adapter;
    ArrayList<Obj_history> list;
    String mess, email;
    private RequestBody fromBody;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tat:
                Intent intent = new Intent(History.this,MainActivity.class);
                intent.putExtra("re_email", email);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list_history = findViewById(R.id.list_history);
        email = getIntent().getStringExtra("email");
        list = new ArrayList<>();
        loadData();

        adapter = new ArrayAdapter<Obj_history>(
                this,
                R.layout.item_history,
                R.id.m_table,
                list
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View Itemview = super.getView(position, convertView, parent);
                TextView list_name = Itemview.findViewById(R.id.list_f);
                TextView table = Itemview.findViewById(R.id.m_table);
                TextView total = Itemview.findViewById(R.id.sum_food);
                TextView date = Itemview.findViewById(R.id.date);

                list_name.setText("Tên các món ăn: " + list.get(position).getList_name());
                table.setText("Mã bàn: " + list.get(position).getCode_table());
                total.setText("Tổng tiền: " + list.get(position).getTotal());
                date.setText("Ngày thanh toán: " + list.get(position).getDate());

                return Itemview;
            }
        };

        list_history.setAdapter(adapter);
    }

    private void loadData() {
        OkHttpClient client = new OkHttpClient();
        fromBody = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder().url("http://10.0.2.2/app/get_history.php").post(fromBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    mess = response.body().string();
                    JSONObject json = new JSONObject(mess);
                    JSONArray jsonArray = json.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String list_name, table, total, date;

                        list_name = jsonObject.getString("list_name");
                        table = jsonObject.getString("code_table");
                        total = jsonObject.getString("total");
                        date = jsonObject.getString("date");
                        list.add(new Obj_history(list_name,table,total,date));
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