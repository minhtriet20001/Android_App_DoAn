package com.example.project75;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {
    private EditText new_pass, comfirm_pass;
    private ImageView save;
    private String new_p, comfirm_p, URL = "http://10.0.2.2/app/comfirm_password.php", getEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        new_pass = findViewById(R.id.EdChangePassword);
        comfirm_pass = findViewById(R.id.EdConfirmPassword);
        save = findViewById(R.id.btnGo_Login);
        getEmail = getIntent().getStringExtra("email");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_p = new_pass.getText().toString().trim();
                comfirm_p = comfirm_pass.getText().toString().trim();

                if (new_p.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Please enter your new password", Toast.LENGTH_SHORT).show();
                } else if (comfirm_p.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Please enter your comfirm password", Toast.LENGTH_SHORT).show();
                } else if (new_p.length() <= 6) {
                    Toast.makeText(ChangePassword.this, "Please enter more than 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!new_p.equals(comfirm_p)) {
                    Toast.makeText(ChangePassword.this, "Comfirm password does not correct ", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody1 = new FormBody.Builder()
                            .add("new_password", comfirm_p)
                            .add("email", getEmail)
                            .build();
                    Request request1 = new Request.Builder().url(URL).post(formBody1).build();
                    client.newCall(request1).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.d("onFailure", e.getMessage());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            try {
                                String responseData = response.body().string();
                                if (responseData.equalsIgnoreCase("Done")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Dialog_Success();
                                        }
                                    });
                                }else {
                                    Toast.makeText(ChangePassword.this, responseData, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d("onResponse", e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void Dialog_Success () {
        Dialog dialog = new Dialog(ChangePassword.this);
        dialog.setContentView(R.layout.success_update);
        dialog.setCanceledOnTouchOutside(false);
        Button back = dialog.findViewById(R.id.Re_login);
        TextView infor = dialog.findViewById(R.id.text_success);
        infor.setText("Đổi mật khẩu thành công");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, Login.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}