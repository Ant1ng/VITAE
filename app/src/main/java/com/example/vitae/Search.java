package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    final String TAG = "SEARCH";

    public ArrayList<Video> currentVideos;
    boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + "called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        currentVideos = new ArrayList<>();

        try {
            fetchYoutube("Software+Engineer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!loaded) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onCreate: " + "wait");
        }
        Log.d(TAG, "onCreate: " + "show");
        showResults();
    }

    protected void showResults() {
        Video vid1 = currentVideos.get(0);
        ImageView vid1_img = this.findViewById(R.id.vid1_img);
        Picasso.get().load(vid1.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid1_img);
        vid1_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v);
                Toast.makeText(Search.this, "vid1_img", Toast.LENGTH_SHORT).show();
            }
        });

        Video vid2 = currentVideos.get(1);
        ImageView vid2_img = this.findViewById(R.id.vid2_img);
        Picasso.get().load(vid2.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid2_img);
        vid2_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v);
                Toast.makeText(Search.this, "vid2_img", Toast.LENGTH_SHORT).show();
            }
        });

        Video vid3 = currentVideos.get(2);
        ImageView vid3_img = this.findViewById(R.id.vid3_img);
        Picasso.get().load(vid3.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid3_img);
        vid3_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v);
                Toast.makeText(Search.this, "vid3_img", Toast.LENGTH_SHORT).show();
            }
        });

        Video vid4 = currentVideos.get(3);
        ImageView vid4_img = this.findViewById(R.id.vid4_img);
        Picasso.get().load(vid4.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid4_img);
        vid4_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v);
                Toast.makeText(Search.this, "vid4_img", Toast.LENGTH_SHORT).show();
            }
        });

        Video vid5 = currentVideos.get(3);
        ImageView vid5_img = this.findViewById(R.id.vid5_img);
        Picasso.get().load(vid5.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid5_img);
        vid5_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v);
                Toast.makeText(Search.this, "vid5_img", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /** Called when the user taps the Video button */
    public void goToDisplay(View view) {
        Intent intent = new Intent(this, Display.class);
        startActivity(intent);
    }

    void fetchYoutube(String query) throws IOException {
        Log.d(TAG, "fetchYoutube: " + "called");
        new getYoutubeData().execute(query);

    }

    void setCurrentVideos(ArrayList<Video> videoList) {
        Log.d(TAG, "setCurrentVideos: " + "called");
        this.currentVideos = videoList;
        loaded = true;
    }

    class Video {
        String videoID;
        String title;
        String description;
        String thumbnailURL;
        String publishedAt;

        Video(String videoID, String title, String description, String thumbnailURL, String publishedAt) {
            this.videoID = videoID;
            this.title = title;
            this.description = description;
            this.thumbnailURL = thumbnailURL;
            this.publishedAt = publishedAt;
        }

        Video(JSONObject data) throws JSONException {
            JSONObject id = data.getJSONObject("id");
            JSONObject snippet = data.getJSONObject("snippet");
            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            JSONObject defaultThumbnail = thumbnails.getJSONObject("default");

            this.thumbnailURL = defaultThumbnail.getString("url");
            this.publishedAt = snippet.getString("publishedAt");
            this.title = snippet.getString("title");
            this.description = snippet.getString("description");
            this.videoID = id.getString("videoId");

            System.out.println();
        }

    }

    class getYoutubeData extends AsyncTask<String, String, ArrayList<Video>> {

        @SuppressLint("WrongThread")
        @Override
        protected ArrayList<Video> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + "called");
            String query = strings[0];
            JSONObject jObj = makeQuery(query);
            JSONArray items = null;

            try {
                items = jObj.getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Video> videoList =  getVideoList(items);
            onPostExecute(videoList);

            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<Video> videoLists) {
            Log.d(TAG, "onPostExecute: " + "called");
            super.onPostExecute(videoLists);
            setCurrentVideos(videoLists);

        }

        ArrayList<Video> getVideoList(JSONArray items) {
            ArrayList<Video> videoList = new ArrayList<>();

            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject item = items.getJSONObject(i);
                    Video video = new Video(item);
                    videoList.add(video);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return videoList;
        }
        JSONObject makeQuery(String query) {
            HttpURLConnection urlConnection = null;
            URL urlObj = null;
            try {
                urlObj = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=A+Day+in+the+life+of+a+" + query + "&type=video&key=AIzaSyBce8QFxGpNrYiO2w8OCRbSp7cTBmcE9JM");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                urlConnection = (HttpURLConnection) urlObj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                urlConnection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            try {
                urlConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is = null;
            try {
                is = urlConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = "";
            String json = "";

            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line + "\n");
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            json = sb.toString();
            //Convert the response to JSON Object

            JSONObject jObj = null;

            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jObj;
        }
    }

}
