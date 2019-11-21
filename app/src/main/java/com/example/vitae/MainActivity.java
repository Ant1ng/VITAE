package com.example.vitae;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity extends AppCompatActivity {

    public ArrayList<Video> currentVideos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentVideos = new ArrayList<>();

        try {
            fetchYoutube("Software+Engineer");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void fetchYoutube(String query) throws IOException {
        new getYoutubeData().execute(query);

    }

    void setCurrentVideos(ArrayList<Video> videoList) {
        this.currentVideos = videoList;
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
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
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
