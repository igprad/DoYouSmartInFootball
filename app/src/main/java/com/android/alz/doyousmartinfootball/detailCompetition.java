package com.android.alz.doyousmartinfootball;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.alz.doyousmartinfootball.controller.ApiController;
import com.android.alz.doyousmartinfootball.svg.SvgDecoder;
import com.android.alz.doyousmartinfootball.svg.SvgDrawableTranscoder;
import com.android.alz.doyousmartinfootball.svg.SvgSoftwareLayerSetter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class detailCompetition extends AppCompatActivity {
    ApiController apiController;
    String endpointCompetition;
    ArrayList<ImageView> imgView;
    ArrayList<HashMap<String,String>> dataTimCompetition;
    SweetAlertDialog pDialog ;
    GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Competition");
        setContentView(R.layout.activity_detail_competition);
        Intent intent = getIntent();
        dataTimCompetition = new ArrayList<>();
        imgView = new ArrayList<>();
        endpointCompetition = intent.getStringExtra("endpoint");
        apiController = new ApiController();

        requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(com.caverock.androidsvg.SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.error_circle)
                .error(R.drawable.error_center_x)
                .listener(new SvgSoftwareLayerSetter<Uri>());


        String jenis = intent.getStringExtra("jenis");
        if(jenis.equalsIgnoreCase("EC")||jenis.equalsIgnoreCase("CL")) {
            new GetDataTeam(endpointCompetition).execute();
        }
        else{
            new GetDataTeam2(endpointCompetition).execute();
        }
    }

    public void showTableLeague(){

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" Position ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" TeamName ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Logo ");
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Played Games ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" goals ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" win ");
        tv5.setTextColor(Color.WHITE);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" losses ");
        tv6.setTextColor(Color.WHITE);
        tbrow0.addView(tv6);

        TextView tv7 = new TextView(this);
        tv7.setText(" Point ");
        tv7.setTextColor(Color.WHITE);
        tbrow0.addView(tv7);

        stk.addView(tbrow0);
        for (int i = 0; i < dataTimCompetition.size(); i++) {
            TableRow tbrow = new TableRow(this);

            TextView t1v = new TextView(this);
            t1v.setText("" + dataTimCompetition.get(i).get("position"));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(" " + dataTimCompetition.get(i).get("team"));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            ImageView tempImage = new ImageView(this);
            String url = dataTimCompetition.get(i).get("photo");
            Uri uri = Uri.parse(url);
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .override(50,50)
                    .into(tempImage);
            tempImage.setMaxHeight(50);
            tempImage.setMaxWidth(50);
            tempImage.setMinimumHeight(50);
            tempImage.setMinimumWidth(50);
            tbrow.addView(tempImage);

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
            t6v.setText(" " + dataTimCompetition.get(i).get("win"));
            t6v.setTextColor(Color.WHITE);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this);
            t7v.setText(" " + dataTimCompetition.get(i).get("losse"));
            t7v.setTextColor(Color.WHITE);
            t7v.setGravity(Gravity.CENTER);
            tbrow.addView(t7v);

            TextView t8v = new TextView(this);
            t8v.setText(" " + dataTimCompetition.get(i).get("points"));
            t8v.setTextColor(Color.WHITE);
            t8v.setGravity(Gravity.CENTER);
            tbrow.addView(t8v);

            stk.addView(tbrow);
        }
    }

    public void showTableChampionLeague(){

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

        TextView txt = new TextView(this);
        txt.setText(" Flag ");
        txt.setTextColor(Color.WHITE);
        txt.setGravity(Gravity.CENTER);
        tbrow0.addView(txt);

        TextView tv2 = new TextView(this);
        tv2.setText(" team ");
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
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
            TableRow tbrow = new TableRow(this);
            if(dataTimCompetition.get(i).get("group").equalsIgnoreCase("A")){
                tbrow.setBackgroundColor(Color.DKGRAY);
            }
            if(dataTimCompetition.get(i).get("group").equalsIgnoreCase("B")){
                tbrow.setBackgroundColor(Color.GRAY);
            }
            if(dataTimCompetition.get(i).get("group").equalsIgnoreCase("C")){

            }
            if(dataTimCompetition.get(i).get("group").equalsIgnoreCase("D")){
                tbrow.setBackgroundColor(Color.GRAY);
            }
            if(dataTimCompetition.get(i).get("group").equalsIgnoreCase("E")){

            }
            if(dataTimCompetition.get(i).get("group").equalsIgnoreCase("F")){
                tbrow.setBackgroundColor(Color.DKGRAY);
            }
            Log.e("CEK ERROR APA SIH : ",dataTimCompetition.get(i).get("group"));

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

            ImageView tempImage = new ImageView(this);
            String url = dataTimCompetition.get(i).get("crestURI").replace(" ","%20");
//            Picasso.with(getApplicationContext())
//                    .load(url)
//                    .resize(50,50)
//                    .into(tempImage);
//            tbrow.addView(tempImage);
            Uri uri = Uri.parse(url);
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .override(50,50)
                    .into(tempImage);
            tempImage.setMaxHeight(50);
            tempImage.setMaxWidth(50);
            tempImage.setMinimumHeight(50);
            tempImage.setMinimumWidth(50);
            tbrow.addView(tempImage);

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
//            Toast.makeText(getApplicationContext(),"Downloading Json",Toast.LENGTH_SHORT).show();
//            pDialog = new SweetAlertDialog(getApplicationContext());
//            pDialog.setTitleText("Loading");
//            pDialog.setCancelable(false);
//            pDialog.show();
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
                        Log.e("Cek data ",tempData.get("team"));//VALID COY
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
//            pDialog.dismiss();
            showTableChampionLeague();
        }
    }

    // Data untuk Team Beda JSON
    private class GetDataTeam2 extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetDataTeam2(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
//            Toast.makeText(getApplicationContext(),"Downloading Json",Toast.LENGTH_SHORT).show();
//            pDialog = new SweetAlertDialog(getApplicationContext());
//            pDialog.setTitleText("Loading");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            result = apiController.getStringJSON(resource,2);
            int indeksData=0;
            JSONObject data ;
            JSONArray arrayOfDivisi;
            if(!result.isEmpty()){
                try {
                    JSONObject team = new JSONObject(result);
                    arrayOfDivisi = team.getJSONArray("standing");
                    for (int i = 0; i < arrayOfDivisi.length() ; i++) {
                        HashMap<String,String> tempData;
                        tempData = new HashMap<>();
                        data = arrayOfDivisi.getJSONObject(i);
                        tempData.put("position",data.getString("position"));
                        tempData.put("team",data.getString("teamName"));
                        tempData.put("photo",data.getString("crestURI"));
//                        Log.e("Cek data ",tempData.get("team"));//VALID COY
                        tempData.put("win",data.getString("wins"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("draw",data.getString("draws"));
                        tempData.put("losse",data.getString("losses"));
                        dataTimCompetition.add(tempData);
//                        ImageView tempImage = null;
//                        Picasso.with(getApplicationContext())
//                                .load(dataTimCompetition.get(i).get("photo"))
//                                .resize(50,50)
//                                .into(tempImage);
//                        imgView.add(tempImage);
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
                Log.e("Tes Habis Selesai  : ",dataTimCompetition.get(i).get("team"));
            }
//            pDialog.dismiss();
            showTableLeague();
        }
    }

}
