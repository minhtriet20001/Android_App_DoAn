package com.example.project75;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotEmail extends AppCompatActivity {
    private TextView goBackLogin;
    private EditText ForgotEmail, OTP;
    private ImageView btnOTP;
    private ProgressDialog progressDialog;
    private String StrJson, URL = "http://10.0.2.2/app/forgot_email.php", email, URL1 = "http://10.0.2.2/app/code_otp.php";
    private OkHttpClient myClient;
    private Response response, response1;
    private RequestBody fromBody;
    private Request request;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_email);

        goBackLogin = findViewById(R.id.goBackLogin);
        ForgotEmail = findViewById(R.id.etEmailForgot);
        btnOTP = findViewById(R.id.btnGoOTP);
        myClient = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        goBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotEmail.this,Login.class);
                startActivity(intent);
            }
        });

        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = ForgotEmail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(ForgotEmail.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }
                else {
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
                        .add("email",email)
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
                StrJson = response.body().string();
                if (StrJson.equalsIgnoreCase("Done")) {
                    progressDialog.cancel();
                    DialogShow();
                }else if (StrJson.equalsIgnoreCase("Fail")){
                    progressDialog.cancel();
                    Toast.makeText(ForgotEmail.this,"Send mail is fail", Toast.LENGTH_SHORT).show();
                }
                else if (StrJson.equalsIgnoreCase("Fail update")){
                    progressDialog.cancel();
                    Toast.makeText(ForgotEmail.this, "Code is not update", Toast.LENGTH_SHORT).show();
                }
                else if (StrJson.equalsIgnoreCase("Email is not exist")){
                    progressDialog.cancel();
                    Toast.makeText(ForgotEmail.this, "Please enter correct email ", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.cancel();
                    Toast.makeText(ForgotEmail.this, StrJson, Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Log.e("TAG", "error 2: "+e.getMessage().toString() );
                progressDialog.cancel();
            }
        }
    }

    private void DialogShow()  {
        Dialog dialog = new Dialog(ForgotEmail.this);
        dialog.setContentView(R.layout.activity_otp);
        OTP = dialog.findViewById(R.id.OTP);
        Button btnSubmit = dialog.findViewById(R.id.submit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OTP.getText().toString().isEmpty()){
                    Toast.makeText(ForgotEmail.this, "Please enter your otp", Toast.LENGTH_SHORT).show();
                }
                else {
                    new Enter_OTP().execute();
                }
            }
        });
    }

    private class Enter_OTP extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody1 = new FormBody.Builder()
                        .add("code_otp", OTP.getText().toString().trim())
                        .add("email", email)
                        .build();
                Request request1 = new Request.Builder().url(URL1).post(formBody1).build();
                response1 = client.newCall(request1).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                String data1 = response1.body().string();
                if (data1.equalsIgnoreCase("Done")) {
                    Intent intent = new Intent(ForgotEmail.this, ChangePassword.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else if (data1.equalsIgnoreCase("Fail")){
                    Toast.makeText(ForgotEmail.this,"Verification failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ForgotEmail.this, data1, Toast.LENGTH_SHORT).show();
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

