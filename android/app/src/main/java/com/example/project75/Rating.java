package com.example.project75;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Rating extends AppCompatActivity {
    EditText et_name, et_comment;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnSkip, btnSend;

    String name, comment, rate, strJson, email;
    private OkHttpClient myClient;
    private Response response;
    private RequestBody fromBody;
    private Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        et_name = findViewById(R.id.et_Name);
        et_comment = findViewById(R.id.et_Comment);
        radioGroup = findViewById(R.id.radio_group);
        email = getIntent().getStringExtra("email");
        myClient = new OkHttpClient();

        btnSkip = findViewById(R.id.btnSkip);
        btnSend = findViewById(R.id.btnSend);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rating.this, MainActivity.class);
                intent.putExtra("re_email", email);
                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radio_id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radio_id);
                new getValueRequest().execute();;
            }
        });
    }

    private class getValueRequest extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                fromBody = new FormBody.Builder()
                        .add("name", et_name.getText().toString())
                        .add("rate", radioButton.getText().toString())
                        .add("comment", et_comment.getText().toString())
                        .add("email", email)
                        .build();
                request = new Request.Builder().url("http://10.0.2.2/app/add_rating.php").post(fromBody).build();
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
                    Toast.makeText(Rating.this, "Rating failed, Please Try Again", Toast.LENGTH_SHORT).show();

                } else if (strJson.equalsIgnoreCase("Done")){
                    Toast.makeText(Rating.this, "Đánh Giá Đã Được Lưu Lại", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Rating.this, MainActivity.class);
                    intent.putExtra("re_email", email);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Rating.this, strJson, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "error 2: "+e.getMessage().toString() );
            }
        }
    }
}