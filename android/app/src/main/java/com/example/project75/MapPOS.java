package com.example.project75;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapPOS extends AppCompatActivity  {

    private MyMapFragment myMapFragment;
    String email;

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
                Intent intent = new Intent(MapPOS.this,MainActivity.class);
                intent.putExtra("re_email", email);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pos);

        email = getIntent().getStringExtra("email");


        FragmentManager fragmentManager = this.getSupportFragmentManager();
        this.myMapFragment = (MyMapFragment) fragmentManager.findFragmentById(R.id.mapApp);
    }
}