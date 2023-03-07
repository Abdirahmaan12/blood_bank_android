package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class pageone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageone);
        Button letsgo=findViewById(R.id.btngo);

        letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent letsgo=new Intent(pageone.this, login.class);
                startActivity(letsgo);
            }
        });
    }
}