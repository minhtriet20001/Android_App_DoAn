package com.example.project75;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = getIntent().getStringExtra("re_email");

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,History.class);
                intent2.putExtra("email", email);
                startActivity(intent2);
            }
        });

        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(MainActivity.this,ListRating.class);
                intent5.putExtra("email", email);
                startActivity(intent5);
            }
        });

        findViewById(R.id.booking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Table.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        findViewById(R.id.Map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,MapPOS.class);
                intent1.putExtra("email", email);
                startActivity(intent1);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this,Login.class);
                startActivity(intent4);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        return;
    }
}