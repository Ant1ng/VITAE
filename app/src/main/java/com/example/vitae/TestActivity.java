package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.os.Bundle;
import android.widget.ListView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //generate list
        ArraySet<String> list = new ArraySet<>();
        list.add("item1");
        list.add("item2");

        //instantiate custom adapter
        Adapter adapter = new Adapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.my_listview);
        lView.setAdapter(adapter);
    }
}
