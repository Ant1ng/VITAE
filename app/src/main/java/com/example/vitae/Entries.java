package com.example.vitae;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Entries extends AppCompatActivity {
    private ArraySet<String> entryList;
    private ListView listView;
    private String filename;
    private Context context;
    protected Adapter adapter;

    protected void init(String filename, ListView listView, Context context) {
        this.filename = filename;
        this.listView = listView;
        this.context = context;
        entryList = readFile(getBaseContext(), filename);
        this.adapter = new Adapter(filename, this, context);
        listView.setAdapter(adapter);
    }

    public void write(View view) {
        TextView questionView = findViewById(R.id.editQuestion);
        String question;
        if ((question = questionView.getText().toString()).trim().length() > 0) {
            entryList.add(question);
            writeFile();
        }
    }

    public void writeFile() {
        try {
            FileOutputStream outputStream =
                    context.openFileOutput(filename, Context.MODE_PRIVATE);
            for (String i : entryList) {
                outputStream.write(i.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArraySet<String> readFile(Context context, String filename) {
        ArraySet<String> lines = new ArraySet<>();
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (FileNotFoundException e) {
            return lines;
        }
        catch (IOException e) {
            lines.add("Ripperino");
        }
        return lines;
    }

    public ArraySet<String> getEntryList() {
        return entryList;
    }
}
