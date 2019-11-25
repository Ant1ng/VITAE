package com.example.vitae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Answers extends Entries {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.textView);

        String question = intent.getStringExtra("question");
        String id = intent.getStringExtra("id");
        textView.setText(question);

        init(id + question, (LinearLayout) findViewById(R.id.answers));
    }

    @Override
    public void write(View view) {
        super.write(view);
    }
}
