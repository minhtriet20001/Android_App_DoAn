package com.example.project75;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class payment extends AppCompatActivity {
    TextView total, table;
    ListView listView;
    Button btn_payment;
    Calendar calendar;
    ArrayList<ListMenu> list;
    ArrayAdapter<ListMenu> adapter;
    String data, total_table, list_food, strJson, email;
    int sum;

    private OkHttpClient myClient;
    private Response response;
    private RequestBody fromBody;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        listView = findViewById(R.id.list_total);
        myClient = new OkHttpClient();
        calendar = Calendar.getInstance();
        total = findViewById(R.id.total_payment);
        table = findViewById(R.id.table);
        list_food = "";
        btn_payment = findViewById(R.id.payment);
        list = new ArrayList<>();
        loadData();

        adapter = new ArrayAdapter<ListMenu>(
                this,
                R.layout.list_payment,
                R.id.name_food,
                list
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View Itemview = super.getView(position, convertView, parent);

                ImageView image = Itemview.findViewById(R.id.image_food);
                TextView price = Itemview.findViewById(R.id.price);
                TextView name = Itemview.findViewById(R.id.name_food);
                TextView total_food = Itemview.findViewById(R.id.Total_food);
                TextView money = Itemview.findViewById(R.id.money);

                Glide.with(payment.this).load(list.get(position).getImage()).into(image);
                price.setText("Giá tiền: " + list.get(position).getPrice() + " VND");
                total_food.setText("Số lượng: " + list.get(position).getTotal());
                name.setText(list.get(position).getName());
                int a = Integer.parseInt(list.get(position).getPrice());
                int b = Integer.parseInt(list.get(position).getTotal());
                int c = a*b;
                money.setText("Tổng tiền: " + String.valueOf(c) + " VND");

                return Itemview;
            }
        };
        listView.setAdapter(adapter);
        for(int i = 0; i < list.size(); i++){
            int soluong = Integer.parseInt(list.get(i).getTotal());
            int gia = Integer.parseInt(list.get(i).getPrice());
            sum = sum + soluong*gia;
            list_food = list_food + list.get(i).getName() + ": " + list.get(i).getTotal() + ", ";
        }
        list_food = list_food.substring(0, list_food.length()-2);
        total.setText("Số tiền tổng: " + sum);
        table.setText("Số bàn: " + total_table);
        
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogShow();
            }
        });
    }

    private void loadData() {
        data = getIntent().getStringExtra("list");
        String s[] = data.split("-");
        for(int i = 0; i < s.length - 2; i++){
            String array[] = s[i].split("__");
            list.add(new ListMenu(array[0], array[1], array[2], array[3]));
        }
        total_table = s[s.length-2];
        email = s[s.length-1];
    }

    private void DialogShow()  {
        Dialog dialog = new Dialog(payment.this);
        dialog.setContentView(R.layout.confirm);
        dialog.setCanceledOnTouchOutside(false);
        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getValueRequest().execute();
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private class getValueRequest extends AsyncTask<Void,Void,Void> {
        
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                fromBody = new FormBody.Builder()
                        .add("list_food", list_food)
                        .add("table", total_table)
                        .add("Sum_money", String.valueOf(sum))
                        .add("date", simpleDateFormat.format(calendar.getTime()))
                        .add("email", email)
                        .build();
                request = new Request.Builder().url("http://10.0.2.2/app/history.php").post(fromBody).build();
                response = myClient.newCall(request).execute();
            }
            catch (Exception e){
                e.printStackTrace();
                Log.e("TAG", "doInBackground error 1: "+e.getMessage().toString() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                strJson = response.body().string();
                if (strJson.equalsIgnoreCase("Fail")){
                    Toast.makeText(payment.this, "Payment failed, Please Try Again", Toast.LENGTH_SHORT).show();

                } else if (strJson.equalsIgnoreCase("Done")){
                    Dialog_Success();
                }
                else{
                    Toast.makeText(payment.this, strJson, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "error 2: "+e.getMessage().toString() );
            }
        }
    }

    private void Dialog_Success() {
        Dialog dialog = new Dialog(payment.this);
        dialog.setContentView(R.layout.success);
        dialog.setCanceledOnTouchOutside(false);
        Button back = dialog.findViewById(R.id.back);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(payment.this, Rating.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}