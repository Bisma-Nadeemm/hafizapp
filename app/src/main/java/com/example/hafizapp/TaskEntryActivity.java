package com.example.hafizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GeneralTaskEntryActivity extends AppCompatActivity {
    private Button buttonSabaq;
    private Button buttonSabaqi;
    private Button buttonManzil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_entry);

        buttonSabaq = findViewById(R.id.buttonSabaq);
        buttonSabaqi = findViewById(R.id.buttonSabaqi);
        buttonManzil = findViewById(R.id.buttonManzil);

        buttonSabaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralTaskEntryActivity.this, SabaqEntryActivity.class));
            }
        });

        buttonSabaqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralTaskEntryActivity.this, SabaqiEntryActivity.class));
            }
        });

        buttonManzil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralTaskEntryActivity.this, ManzilEntryActivity.class));
            }
        });
    }
}