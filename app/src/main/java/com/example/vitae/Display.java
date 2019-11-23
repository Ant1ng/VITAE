package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Display extends AppCompatActivity {

    private Set<String> text;
    private LinearLayout faq;
    private final String filename = "lol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        text = new HashSet<>();
        faq = findViewById(R.id.FAQ);
        initFAQ();
    }

    protected void initFAQ() {
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                text.add(line);
            }
        }
        catch (FileNotFoundException e) {
            text.add("Rip");
        }
        catch (IOException e) {
            text.add("Ripperino");
        }
        restartQuestions();
    }

    public void write(View view) {
        TextView questionView = findViewById(R.id.editQuestion);
        String question;
        if ((question = questionView.getText().toString()).trim().length() > 0) {
            text.add(question);
            restartQuestions();
            writeFile();
        }
    }

    private void writeFile() {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            for (String i : text) {
                outputStream.write(i.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeQuestion(final String question) {
        final LinearLayout questionEntry = new LinearLayout(getBaseContext());

        questionEntry.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textView.setText(question);
        questionEntry.addView(textView);
        questionEntry.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Answers.class);
                intent.putExtra("question", question);
                startActivity(intent);
             }
         });

        TextView blank = new TextView(this);
        questionEntry.addView(blank);
        blank.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                1.0f));

        Button delete = new Button(getBaseContext());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.remove(question);
                faq.removeView(questionEntry);
                writeFile();
            }
        });
        questionEntry.addView(delete);

        faq.addView(questionEntry);
    }

    private void restartQuestions() {
        faq.removeAllViews();
        for (String s : text) {
            writeQuestion(s);
        }
    }
}
