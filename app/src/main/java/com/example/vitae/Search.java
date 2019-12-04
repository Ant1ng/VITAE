package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity {

    final String TAG = "SEARCH";
    private final String API_KEY = "AIzaSyDJZjhbPr9BSwMoYFkzwinteDYsipAvKXs";

    public ArrayList<Video> currentVideos;
    boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + "called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        currentVideos = new ArrayList<>();

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


//        while(!loaded) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d(TAG, "onCreate: " + "wait");
//        }
//        Log.d(TAG, "onCreate: " + "show");
//        showResults();
    }

    protected void showResults() {
        if (currentVideos.size() < 5) {
            return;
        }

        final Video vid1 = currentVideos.get(0);
        ImageView vid1_img = this.findViewById(R.id.vid1_img);
        TextView vid1_title = this.findViewById(R.id.vid1_title);
        TextView vid1_user = this.findViewById(R.id.vid1_user);
        TextView vid1_views = this.findViewById(R.id.vid1_views);

        String trimmed_title = "";

        try {
            trimmed_title = vid1.title.substring(0,50);
        } catch (IndexOutOfBoundsException e) {
            trimmed_title = vid1.title;
        }
        vid1_title.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")));
        vid1_user.setVisibility(TextView.INVISIBLE);
        vid1_views.setVisibility(TextView.INVISIBLE);
        Picasso.get().load(vid1.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid1_img);
        vid1_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v, vid1.videoID);
                // Toast.makeText(Search.this, "vid1_img", Toast.LENGTH_SHORT).show();
            }
        });

        final Video vid2 = currentVideos.get(1);
        ImageView vid2_img = this.findViewById(R.id.vid2_img);
        TextView vid2_title = this.findViewById(R.id.vid2_title);
        TextView vid2_user = this.findViewById(R.id.vid2_user);
        TextView vid2_views = this.findViewById(R.id.vid2_views);

        try {
            trimmed_title = vid2.title.substring(0,30);
        } catch(IndexOutOfBoundsException e) {
            trimmed_title = vid2.title;
        }

        vid2_title.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")) + " ...");
        vid2_user.setVisibility(TextView.INVISIBLE);
        vid2_views.setVisibility(TextView.INVISIBLE);
        Picasso.get().load(vid2.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid2_img);
        vid2_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v, vid2.videoID);
                // Toast.makeText(Search.this, "vid2_img", Toast.LENGTH_SHORT).show();
            }
        });

        final Video vid3 = currentVideos.get(2);
        ImageView vid3_img = this.findViewById(R.id.vid3_img);
        TextView vid3_title = this.findViewById(R.id.vid3_title);
        TextView vid3_user = this.findViewById(R.id.vid3_user);
        TextView vid3_views = this.findViewById(R.id.vid3_views);

        try {
            trimmed_title = vid3.title.substring(0,30);
        } catch (IndexOutOfBoundsException e) {
            trimmed_title = vid3.title;
        }
        vid3_title.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")) + " ...");
        vid3_user.setVisibility(TextView.INVISIBLE);
        vid3_views.setVisibility(TextView.INVISIBLE);
        Picasso.get().load(vid3.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid3_img);
        vid3_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v, vid3.videoID);
                // Toast.makeText(Search.this, "vid3_img", Toast.LENGTH_SHORT).show();
            }
        });

        final Video vid4 = currentVideos.get(3);
        ImageView vid4_img = this.findViewById(R.id.vid4_img);
        TextView vid4_title = this.findViewById(R.id.vid4_title);
        TextView vid4_user = this.findViewById(R.id.vid4_user);
        TextView vid4_views = this.findViewById(R.id.vid4_views);
        try {
            trimmed_title = vid4.title.substring(0,30);
        } catch (IndexOutOfBoundsException e) {
            trimmed_title = vid4.title;
        }
        vid4_title.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")) + " ...");
        vid4_user.setVisibility(TextView.INVISIBLE);
        vid4_views.setVisibility(TextView.INVISIBLE);
        Picasso.get().load(vid4.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid4_img);
        vid4_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v, vid4.videoID);
                // Toast.makeText(Search.this, "vid4_img", Toast.LENGTH_SHORT).show();
            }
        });

        final Video vid5 = currentVideos.get(4);
        ImageView vid5_img = this.findViewById(R.id.vid5_img);
        TextView vid5_title = this.findViewById(R.id.vid5_title);
        TextView vid5_user = this.findViewById(R.id.vid5_user);
        TextView vid5_views = this.findViewById(R.id.vid5_views);
        trimmed_title = vid5.title.substring(0,30);
        vid5_title.setText(trimmed_title.substring(0, trimmed_title.lastIndexOf(" ")) + " ...");
        vid5_user.setVisibility(TextView.INVISIBLE);
        vid5_views.setVisibility(TextView.INVISIBLE);
        Picasso.get().load(vid5.thumbnailURL).placeholder(R.drawable.loop_foreground).fit().centerCrop().into(vid5_img);
        vid5_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisplay(v, vid5.videoID);
                // Toast.makeText(Search.this, "vid5_img", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /** Called when the user taps the Video button */
    public void goToDisplay(View view, String videoID) {
        Log.d(TAG, "goToDisplay: " + "called with " + videoID);
        Intent intent = new Intent(this, Display.class);
        intent.putExtra("videoID", videoID);
        startActivity(intent);
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