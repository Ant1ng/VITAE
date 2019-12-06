package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity {

    final String TAG = "SEARCH";
    private final String API_KEY = "AIzaSyDJZjhbPr9BSwMoYFkzwinteDYsipAvKXs";

    public ArrayList<Video> currentVideos;
    public ArrayList<Video> localVideos;

    boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + "called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        currentVideos = new ArrayList<>();
        localVideos = new ArrayList<>();

        final EditText searchBar = (EditText) findViewById(R.id.search);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    String text = searchBar.getText().toString();
                    text = text.replace(" ", "+");
                    try {
                        fetchYoutube(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    showResults();
                    return true;
                }
                return false;
            }
        });
        try {
            fetchYoutube("Software Engineer");
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchBar.setText("Software Engineer");
        showResults();
    }

    protected void showResults() {
        ListView listView = findViewById(R.id.listView);
        VideoAdapter adapter = new VideoAdapter(currentVideos, currentVideos, getBaseContext());
        listView.setAdapter(adapter);
    }

    void fetchYoutube(String query) throws IOException, ExecutionException, InterruptedException {
        Log.d(TAG, "fetchYoutube: " + "called");
        new getYoutubeData().execute(query).get();
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
            JSONObject defaultThumbnail = thumbnails.getJSONObject("high");

            this.thumbnailURL = defaultThumbnail.getString("url");
            this.publishedAt = snippet.getString("publishedAt").substring(0, 10);
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
                urlObj = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=A+Day+in+the+life+of+a+" + query + "&type=video&key=" + API_KEY);
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