package com.example.vitae;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.collection.ArraySet;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VideoAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Search.Video> list;
    private String youtubeTitle = "Results from YouTube";
    private Context context;

    public VideoAdapter(ArrayList list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int pos) {
        if (pos == 0) {
            return youtubeTitle;
        }
        return list.get(pos - 1);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if (position == 0) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.title_entry, null);
            TextView title = view.findViewById(R.id.title);
            title.setText(this.youtubeTitle);
            return view;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.video_entry, null);

        TextView titleText = view.findViewById(R.id.title);
        titleText.setText("Title");

        TextView publishedText = view.findViewById(R.id.publishedAt);
        publishedText.setText("");

        ImageView thumbnail = view.findViewById(R.id.thumbnail);

        final Search.Video video = list.get(position - 1);

        String trimmed_title = "";
        try {
            trimmed_title = video.title.substring(0, 50);
        } catch (IndexOutOfBoundsException e) {
            trimmed_title = video.title;
        }

        titleText.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        try {
            Date date = formatter.parse(video.publishedAt);
            SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM dd yyyy");
            publishedText.setText(formatter2.format(date));
        } catch (Exception e) {
            Log.d("VIDEO", "DateParse failed");
        }

        publishedText.setText(video.publishedAt);
        Picasso.get().load(video.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(thumbnail);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Display.class);
                intent.putExtra("videoID", video.videoID);
                v.getContext().startActivity(intent);
            }
        });;

        return view;
    }
}