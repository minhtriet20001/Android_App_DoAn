package com.example.project75;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Table extends AppCompatActivity {
    ArrayList<String> data;
    RecyclerView recyclerView;
    MyAdapter adapter;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        recyclerView = findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        email = getIntent().getStringExtra("email");
        for(int i = 1 ; i<=30; i++){
            data.add("Bàn "+i);
        }
        adapter = new MyAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(Table.this, 3));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(Table.this).inflate(
                    R.layout.item_table,
                    parent,
                    false
            );
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String pos = String.valueOf(position+1);
            holder.textView.setText(data.get(position));
            holder.toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Table.this, Menu.class);
                    intent.putExtra("Table", pos + "-" + email);
                    startActivityForResult(intent, 1234);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button toggleButton;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.textView4);
            toggleButton=itemView.findViewById(R.id.toggleButton);
        }
    }
}