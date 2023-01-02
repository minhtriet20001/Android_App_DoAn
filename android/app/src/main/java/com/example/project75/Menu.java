package com.example.project75;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Menu extends AppCompatActivity {

    ListView LvMenu;
    ArrayList<ListMenu> listMenu;
    ArrayAdapter<ListMenu> adapter;
    ArrayList<Integer> list;
    Button xacnhan;
    String mess, table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LvMenu = findViewById(R.id.LvMenu);
        xacnhan = findViewById(R.id.save);
        table = getIntent().getStringExtra("Table");
        listMenu = new ArrayList<>();
        list = new ArrayList<>();
        loadListMenu();

        adapter = new ArrayAdapter<ListMenu>(
                this,
                R.layout.layout_menu,
                R.id.tvName,
                listMenu
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View Item = super.getView(position, convertView, parent);

                ImageView imageView = Item.findViewById(R.id.imageFood);
                TextView tvName = Item.findViewById(R.id.tvName);
                TextView tvPrice = Item.findViewById(R.id.tvPrice);
                TextView tvTotal = Item.findViewById(R.id.tvTotal);
                Button add = Item.findViewById(R.id.btnAdd);
                Button sub = Item.findViewById(R.id.btnSub);

                Glide.with(Menu.this).load(listMenu.get(position).getImage()).into(imageView);
                tvName.setText(listMenu.get(position).getName());
                tvPrice.setText("Giá tiền: " + listMenu.get(position).getPrice() + " VND");
                tvTotal.setText(String.valueOf(list.get(position)));

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.set(position,list.get(position) + 1);
                        adapter.notifyDataSetChanged();
                    }
                });

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.get(position) > 0) {
                            list.set(position, list.get(position) - 1);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                return  Item;
            }
        };
        LvMenu.setAdapter(adapter);

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                for(int i = 0; i < list.size() ;i++){
                    if(list.get(i) > 0){
                        data = data + listMenu.get(i).getImage() + "__" + listMenu.get(i).getName() + "__" + listMenu.get(i).getPrice() + "__" + list.get(i) + "-";
                    }
                }
                if(data.equals("")){
                    Toast.makeText(Menu.this, "Vui lòng chọn món ăn", Toast.LENGTH_SHORT).show();
                }else {
                    data = data + table;
                    Intent intent = new Intent(Menu.this, payment.class);
                    intent.putExtra("list", data);
                    startActivity(intent);
                }
            }
        });
    }


    private void loadListMenu() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2/app/listmenu.php").build();

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
                        String image,name,price;

                        name = jsonObject.getString("name");
                        price = jsonObject.getString("price");
                        image = jsonObject.getString("thumbnail");
                        listMenu.add(new ListMenu(name,price,image));
                        list.add(0);
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