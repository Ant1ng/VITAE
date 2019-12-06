package com.example.vitae;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VideoAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Search.Video> youtubeList;
    private ArrayList<Search.Video> VITAEList;

    private String youtubeTitle = "Results from YouTube";
    private String VITAETitle = "Results from VITAE";
    private Context context;

    public VideoAdapter(ArrayList VITAEList, ArrayList youtubeList, Context context) {
        this.youtubeList = youtubeList;
        this.VITAEList = VITAEList;
        this.context = context;
    }

    public int getVITAECount() {
        int length = VITAEList.size();
        if (VITAEList.size() > 0) {
            length += 1;
        }
        return length;
    }

    public int getYouTubeCount() {
        int length = youtubeList.size();
        if (youtubeList.size() > 0) {
            length += 1;
        }
        return length;
    }

    @Override
    public int getCount() {
        return getVITAECount() + getYouTubeCount();
    }

    @Override
    public Object getItem(int pos) {
        if (pos == getVITAECount()) {
            return youtubeTitle;
        } else if (pos == 0) {
            return VITAETitle;
        } else if (pos > getVITAECount()) {
            return youtubeList.get(pos - getVITAECount() - 1);
        }
        return VITAEList.get(pos - 1);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your youtubeList items do not have an Id variable.
    }

    private void youtubeEntry(final int position, View view) {
        TextView titleText = view.findViewById(R.id.title);
        titleText.setText("Title");

        TextView publishedText = view.findViewById(R.id.publishedAt);
        publishedText.setText("");

        ImageView thumbnail = view.findViewById(R.id.thumbnail);

        final Search.Video video = (Search.Video) getItem(position);

        String trimmed_title = "";
        try {
            trimmed_title = video.title.substring(0, 50);
        } catch (IndexOutOfBoundsException e) {
            trimmed_title = video.title;
        }

        titleText.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(video.publishedAt);
            SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM dd yyyy");
            publishedText.setText(formatter2.format(date));
        } catch (Exception e) {
            Log.d("VIDEO", "DateParse failed");
        }

        //publishedText.setText(video.publishedAt);
        Picasso.get().load(video.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(thumbnail);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Display.class);
                intent.putExtra("videoID", video.videoID);
                v.getContext().startActivity(intent);
            }
        });;
    }

    private void VITAEEntry(final int position, View view) {
        TextView titleText = view.findViewById(R.id.title);
        titleText.setText("Title");

        TextView publishedText = view.findViewById(R.id.publishedAt);
        publishedText.setText("");

        ImageView thumbnail = view.findViewById(R.id.thumbnail);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        //Check if title, include it into the ListView
        if (position == 0 || position == getVITAECount()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.entry_title, null);
            TextView title = view.findViewById(R.id.title);
            title.setText((String) getItem(position));
            return view;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.entry_video, null);

        if (position > getVITAECount() || true) {
            youtubeEntry(position, view);
        }

        return view;
    }
}