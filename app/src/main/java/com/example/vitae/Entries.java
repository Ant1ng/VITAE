package com.example.vitae;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

public class Entries extends AppCompatActivity {
    private ArraySet<String> entryList;
    private ListView listView;
    private String filename;
    private Context context;
    protected FAQAdapter adapter;

    protected void init(String filename, ListView listView, Context context) {
        this.filename = filename;
        this.listView = listView;
        this.context = context;
        entryList = readFile(getBaseContext(), filename);
        this.adapter = new FAQAdapter(filename, this, context);
        listView.setAdapter(adapter);

        final EditText questionView = findViewById(R.id.editQuestion);
        questionView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String question;
                    if ((question = questionView.getText().toString()).trim().length() > 0) {
                        entryList.add(question);
                        writeFile();
                    }
                    return false;
                }
                return false;
            }
        });
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
