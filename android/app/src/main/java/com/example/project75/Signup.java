package com.example.project75;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Signup extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private TextView goBack;
    private ImageView btnRegister;
    private String StrJson, URL = "http://10.0.2.2/app/register.php";

    private OkHttpClient myClient;
    private Response response;
    private RequestBody fromBody;
    private Request request;
    private ProgressDialog progressDialog;
    private String name, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myClient = new OkHttpClient();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        goBack = findViewById(R.id.goBack);
        btnRegister = findViewById(R.id.btnRegister);


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etName.getText().toString();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(Signup.this, "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(Signup.this, "Enter your email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(Signup.this, "Enter your password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(Signup.this, "Please enter more than 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signup.this, "Processing....", Toast.LENGTH_SHORT).show();
                    new getValueRequest().execute();
                }
            }
        });
    }

    private void Dialog_Success () {
        Dialog dialog = new Dialog(Signup.this);
        dialog.setContentView(R.layout.success_update);
        dialog.setCanceledOnTouchOutside(false);
        Button back = dialog.findViewById(R.id.Re_login);
        TextView infor = dialog.findViewById(R.id.text_success);
        infor.setText("Đăng ký thành công");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private class getValueRequest extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                fromBody = new FormBody.Builder()
                        .add("name",name)
                        .add("email",email)
                        .add("password",password)
                        .build();
                request = new Request.Builder().url(URL).post(fromBody).build();
                response = myClient.newCall(request).execute();
            }
            catch (Exception e){
                e.printStackTrace();
                Log.e("TAG", "doInBackground error 1: "+e.getMessage().toString() );
                progressDialog.cancel();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            try {
                StrJson = response.body().string();
                if (StrJson.equalsIgnoreCase("Something Wrong")) {
                    progressDialog.cancel();
                    Toast.makeText(Signup.this, StrJson + "Oh no", Toast.LENGTH_SHORT).show();
                }else if (StrJson.equalsIgnoreCase("Data Inserted")){
                    progressDialog.cancel();
                    Dialog_Success();
                }
                else{
                    progressDialog.cancel();
                    Toast.makeText(Signup.this, StrJson, Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Log.e("TAG", "error 2: "+e.getMessage().toString() );
                progressDialog.cancel();
            }
        }
    }
}
