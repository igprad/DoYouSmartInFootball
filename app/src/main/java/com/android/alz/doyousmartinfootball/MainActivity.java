package com.android.alz.doyousmartinfootball;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.controller.ApiController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity{
    private ArrayList<HashMap<String,String>> resultJson;
    private ApiController apiController = new ApiController();
    private TextView txtView1;
    private String temp ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView1 = (TextView) findViewById(R.id.textData);

        new GetData(FootballData.TEAMS+
                FootballData.MANCHESTER_UNITED).execute();

        resultJson = new ArrayList<HashMap<String,String>>();

    }


    private class GetData extends AsyncTask<Void,Void,Void>{
        private String resource,result ;

        public GetData(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this,"Json Data is " +
                    "downloading",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result = apiController.getStringJSON(resource);
            if(result!=null){
                try {
                    JSONArray data = new JSONArray(result);
                    for (int i = 0; i <data.length() ; i++) {
                        HashMap<String,String> tempData = new HashMap<>();
                        JSONObject object = data.getJSONObject(i);
                        tempData.put("id",object.getString("id"));
                        tempData.put("caption",object.getString("caption"));
                        tempData.put("league",object.getString("league"));
                        tempData.put("year",object.getString("year"));
                        tempData.put("currentMatchDay",object.getString("currentMatchday"));
                        tempData.put("numberOfMatchDays",object.getString("numberOfMatchdays"));
                        tempData.put("numberOfGames",object.getString("numberOfGames"));
                        tempData.put("lastUpdated",object.getString("lastUpdated"));

                        resultJson.add(tempData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i <resultJson.size() ; i++) {
                temp+=resultJson.get(i).get("id")+"\n";
                temp+=resultJson.get(i).get("caption")+"\n";
                temp+=resultJson.get(i).get("league")+"\n";
                temp+=resultJson.get(i).get("year")+"\n";
                temp+=resultJson.get(i).get("currentMatchDay")+"\n";
                temp+=resultJson.get(i).get("numberOfMatchdays")+"\n";
                temp+=resultJson.get(i).get("numberOfGames")+"\n";
                temp+=resultJson.get(i).get("lastUpdated")+"\n";
                temp+="\n";
            }
            Log.e("Result JSON :",temp);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txtView1.setText(temp);
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this,resultJson,
//                    new String[""]
//            )
            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
        }
    }
}
