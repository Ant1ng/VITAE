package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Answers extends AppCompatActivity {

    Intent intent;
    String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        intent = getIntent();
        TextView textView = findViewById(R.id.textView);
        question = intent.getStringExtra("question");
        textView.setText(question);
    }


}
