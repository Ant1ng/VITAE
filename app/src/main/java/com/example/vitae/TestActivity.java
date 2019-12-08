package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> list = new ArrayList<>();

        list.add("Hello");
        list.add("Goodbye");

        //VideoAdapter adapter = new VideoAdapter(list, getBaseContext());

        //listView.setAdapter(adapter);
    }
}
