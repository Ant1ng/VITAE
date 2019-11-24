package com.example.vitae;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Entries extends AppCompatActivity {
    public Collection<String> entryList;
    public LinearLayout linearLayout;
    public String filename;

    protected void init() {
        entryList = readFile(getBaseContext(), filename);
        restartEntries();
    }

    public void write(View view) {
        TextView questionView = findViewById(R.id.editQuestion);
        String question;
        if ((question = questionView.getText().toString()).trim().length() > 0) {
            entryList.add(question);
            restartEntries();
            writeFile(getBaseContext(), filename, entryList);
        }
    }

    private void writeEntry(final String entry) {
        final LinearLayout questionEntry = new LinearLayout(getBaseContext());

        questionEntry.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(entry);
        questionEntry.addView(textView);
        questionEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Answers.class);
                intent.putExtra("question", entry);
                intent.putExtra("id", filename);
                startActivity(intent);
            }
        });

        TextView blank = new TextView(this);
        questionEntry.addView(blank);
        blank.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        Button delete = new Button(getBaseContext());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryList.remove(entry);
                linearLayout.removeView(questionEntry);
                deleteFile(entry);
                writeFile(getBaseContext(), filename, entryList);
            }
        });
        questionEntry.addView(delete);

        linearLayout.addView(questionEntry);
    }

    private void restartEntries() {
        linearLayout.removeAllViews();
        for (String s : entryList) {
            writeEntry(s);
        }
    }

    public static void writeFile(Context context, String filename, Collection<String> text) {
        try {
            FileOutputStream outputStream =
                    context.openFileOutput(filename, Context.MODE_PRIVATE);
            for (String i : text) {
                outputStream.write(i.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<String> readFile(Context context, String filename) {
        Set<String> lines = new HashSet<>();
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
}
