package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

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
        text = FileUtils.readFile(getBaseContext(), filename);
        restartQuestions();
    }

    public void write(View view) {
        TextView questionView = findViewById(R.id.editQuestion);
        String question;
        if ((question = questionView.getText().toString()).trim().length() > 0) {
            text.add(question);
            restartQuestions();
            FileUtils.writeFile(getBaseContext(), filename, text);
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
                deleteFile(question);
                FileUtils.writeFile(getBaseContext(), filename, text);
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
