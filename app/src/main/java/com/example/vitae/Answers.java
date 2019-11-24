package com.example.vitae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Answers extends Entries {

    Intent intent;
    String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        intent = getIntent();
        TextView textView = findViewById(R.id.textView);

        question = intent.getStringExtra("question");
        String id = intent.getStringExtra("id");
        entryList = readFile(getBaseContext(), id + question);
        linearLayout = findViewById(R.id.answers);

        textView.setText(question);
    }

    @Override
    public void write(View view) {
        super.write(view);
    }
}
