package com.android.alz.doyousmartinfootball;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.controller.ApiController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class detailCompetition extends AppCompatActivity {
    ApiController apiController;
    String endpointCompetition;
    ArrayList<HashMap<String,String>> dataTimCompetition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Competition");
        setContentView(R.layout.activity_detail_competition);
        Intent intent = getIntent();
        dataTimCompetition = new ArrayList<>();
        endpointCompetition = intent.getStringExtra("endpoint");
        apiController = new ApiController();
        new GetDataTeam(endpointCompetition).execute();

    }


    public void showTable(){


        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" group ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" rank ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" team ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" playedGames ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" goals ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" goalsAgainst ");
        tv5.setTextColor(Color.WHITE);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" points ");
        tv6.setTextColor(Color.WHITE);
        tbrow0.addView(tv6);

        stk.addView(tbrow0);
        for (int i = 0; i < dataTimCompetition.size(); i++) {
            Log.e("CEK ERROR APA SIH : ",dataTimCompetition.get(i).get("group"));
            TableRow tbrow = new TableRow(this);

            TextView t1v = new TextView(this);
            t1v.setText("" + dataTimCompetition.get(i).get("group"));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(" " + dataTimCompetition.get(i).get("rank"));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(" " + dataTimCompetition.get(i).get("team"));
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(" " + dataTimCompetition.get(i).get("playedGames"));
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(" " + dataTimCompetition.get(i).get("goals"));
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this);
            t6v.setText(" " + dataTimCompetition.get(i).get("goalsAgainst"));
            t6v.setTextColor(Color.WHITE);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this);
            t7v.setText(" " + dataTimCompetition.get(i).get("points"));
            t7v.setTextColor(Color.WHITE);
            t7v.setGravity(Gravity.CENTER);
            tbrow.addView(t7v);

            stk.addView(tbrow);
        }

    }


    private class GetDataTeam extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetDataTeam(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Downloading Json",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result = apiController.getStringJSON(resource,2);
            int indeksData=0;
            JSONObject data ;
            JSONArray arrayOfDivisi;
            if(!result.isEmpty()){
                try {
                   JSONObject team = new JSONObject(result).getJSONObject("standings");
                    arrayOfDivisi = team.getJSONArray("A");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("group",data.getString("group"));
                        tempData.put("rank",data.getString("rank"));
                        tempData.put("team",data.getString("team"));
                        Log.e("COK TES MUNCUL ORA",tempData.get("team"));//VALID COY
                        tempData.put("teamId",data.getString("teamId"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("goalsAgainst",data.getString("goalsAgainst"));
                        tempData.put("goalDifference",data.getString("goalDifference"));
                        dataTimCompetition.add(tempData);
                    }

                    arrayOfDivisi = team.getJSONArray("B");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("group",data.getString("group"));
                        tempData.put("rank",data.getString("rank"));
                        tempData.put("team",data.getString("team"));
                        tempData.put("teamId",data.getString("teamId"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("goalsAgainst",data.getString("goalsAgainst"));
                        tempData.put("goalDifference",data.getString("goalDifference"));
                        dataTimCompetition.add(tempData);
                    }
                    arrayOfDivisi = team.getJSONArray("C");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("group",data.getString("group"));
                        tempData.put("rank",data.getString("rank"));
                        tempData.put("team",data.getString("team"));
                        tempData.put("teamId",data.getString("teamId"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("goalsAgainst",data.getString("goalsAgainst"));
                        tempData.put("goalDifference",data.getString("goalDifference"));
                        dataTimCompetition.add(tempData);
                    }
                    arrayOfDivisi = team.getJSONArray("D");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("group",data.getString("group"));
                        tempData.put("rank",data.getString("rank"));
                        tempData.put("team",data.getString("team"));
                        tempData.put("teamId",data.getString("teamId"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("goalsAgainst",data.getString("goalsAgainst"));
                        tempData.put("goalDifference",data.getString("goalDifference"));
                        dataTimCompetition.add(tempData);
                    }
                    arrayOfDivisi = team.getJSONArray("E");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("group",data.getString("group"));
                        tempData.put("rank",data.getString("rank"));
                        tempData.put("team",data.getString("team"));
                        tempData.put("teamId",data.getString("teamId"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("goalsAgainst",data.getString("goalsAgainst"));
                        tempData.put("goalDifference",data.getString("goalDifference"));
                        dataTimCompetition.add(tempData);
                    }
                    arrayOfDivisi = team.getJSONArray("F");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("group",data.getString("group"));
                        tempData.put("rank",data.getString("rank"));
                        tempData.put("team",data.getString("team"));
                        tempData.put("teamId",data.getString("teamId"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("goalsAgainst",data.getString("goalsAgainst"));
                        tempData.put("goalDifference",data.getString("goalDifference"));
                        dataTimCompetition.add(tempData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < dataTimCompetition.size(); i++) {
                Log.e("Tes Habis Selesai  : ",dataTimCompetition.get(i).get("group"));
            }
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            showTable();
        }
    }

}


