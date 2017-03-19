package com.android.alz.doyousmartinfootball;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.api.TwitterApi;
import com.android.alz.doyousmartinfootball.controller.TwitterApiController;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class TweetFootball extends AppCompatActivity {
    TwitterApiController twitterApiController;
    ArrayList<HashMap<String, String>> twittwrfeedsarray;
    TextView text;
    ListView listView;
    ArrayList<String> pesanTwit;
    ArrayList<String> urlTwit;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_football);
        getSupportActionBar().setTitle("News on Twitter - ESPN Soccer");
        listView = (ListView) findViewById(R.id.ViewListResult);
        pesanTwit = new ArrayList<>();
        urlTwit = new ArrayList<>();
        try {
            twitterApiController = new TwitterApiController();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        twittwrfeedsarray = new ArrayList<>();
        new TaskGetFootballTweet().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (urlTwit.get(position).equalsIgnoreCase("")) {
                    Toast.makeText(TweetFootball.this, "Sorry, there are no url can be get", Toast.LENGTH_SHORT).show();
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(urlTwit.get(position)));
                    startActivity(browserIntent);
                }
            }
        });
    }


    private class TaskGetFootballTweet extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TweetFootball.this,
                    ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
            pDialog.setTitle("Please wait");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Loading data...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                setTweetFootball();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1, pesanTwit);

            listView.setAdapter(adapter);
            pDialog.dismiss();
        }


    }


    private void setTweetFootball() throws IOException, JSONException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(TwitterApi.ENDPOINT);
        String receivedToken = twitterApiController.getBearer();
        httpget.setHeader("Authorization", "Bearer " + receivedToken);
        HttpResponse response;

        response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            String twitter_response = convertStreamToString(instream);
            instream.close();
            JSONObject status = new JSONObject(twitter_response);
            JSONArray value = status.getJSONArray("statuses");
            HashMap<String, String> getData = new HashMap<>();
            for (int i = 0; i < value.length(); i++) {
                JSONObject valueIndex = value.getJSONObject(i);
                getData.put("pesan", valueIndex.getString("text"));
                getData.put("tgl", valueIndex.getString("created_at"));
                getData.put("user", valueIndex.getJSONObject("user").getString("name"));
                JSONArray valueOfUrl = valueIndex.getJSONObject("entities").getJSONArray("urls");
                for (int j = 0; j < valueOfUrl.length(); j++) {
                    JSONObject objUrl = valueOfUrl.getJSONObject(j);
                    getData.put("url", objUrl.getString("url"));
                    urlTwit.add(objUrl.getString("url"));
                    break;
                }
                twittwrfeedsarray.add(getData);
                Log.e("created at", twittwrfeedsarray.get(i).get("tgl"));
                Log.e("user", twittwrfeedsarray.get(i).get("user"));
                Log.e("pesan", twittwrfeedsarray.get(i).get("pesan"));
                pesanTwit.add(twittwrfeedsarray.get(i).get("pesan"));
            }

        }
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
