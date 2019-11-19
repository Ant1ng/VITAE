package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageButton ib = (ImageButton) this.findViewById(R.id.thumb1);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v);
                Toast.makeText(Search.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Called when the user taps the Video button */
    public void goToDisplay(View view) {
        Intent intent = new Intent(this, Display.class);
        startActivity(intent);
    }
}
