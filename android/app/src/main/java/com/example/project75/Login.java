package com.example.project75;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {

    private ImageView btnGo;
    private EditText etEmail, etPassword;
    private TextView goSignup,forgotPassword;
    private String email,password,strJson, URL = "http://10.0.2.2/app/login.php";

    private OkHttpClient myClient;
    private Response response;
    private RequestBody fromBody;
    private Request request;
    private ProgressDialog progressDialog;
    private Context context = Login.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myClient = new OkHttpClient();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        goSignup = findViewById(R.id.goSignup);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnGo = findViewById(R.id.btnGo);
        forgotPassword = findViewById(R.id.forgotPassword);

        goSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,ForgotEmail.class);
                startActivity(intent);
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if (email.isEmpty()){
                    Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.isEmpty()){
                    Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 5){
                    Toast.makeText(context, "Please enter more than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    new getValueRequest().execute();
                }

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
                        .add("email", email)
                        .add("password", password)
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
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                strJson = response.body().string();
                if (strJson.equalsIgnoreCase("Failed")){
                    progressDialog.cancel();
                    Toast.makeText(Login.this, "Login Failed, Please Try Again", Toast.LENGTH_SHORT).show();

                } else if (strJson.equalsIgnoreCase("Login Success")){
                    etEmail.setText("");
                    etPassword.setText("");
                    progressDialog.cancel();
                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("re_email", email);
                    startActivity(intent);
                }
                else if(strJson.equalsIgnoreCase("Password changed")){
                    Intent intent = new Intent(Login.this, ChangePassword.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
                else{
                    progressDialog.cancel();
                    Toast.makeText(Login.this, strJson, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "error 2: "+e.getMessage().toString() );
                progressDialog.cancel();
            }
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }
}