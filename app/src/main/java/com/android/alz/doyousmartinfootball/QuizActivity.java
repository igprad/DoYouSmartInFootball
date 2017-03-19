package com.android.alz.doyousmartinfootball;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.controller.ApiController;
import com.android.alz.doyousmartinfootball.svg.SvgDecoder;
import com.android.alz.doyousmartinfootball.svg.SvgDrawableTranscoder;
import com.android.alz.doyousmartinfootball.svg.SvgSoftwareLayerSetter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.facebook.share.widget.ShareButton;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ru.katso.livebutton.LiveButton;

public class QuizActivity extends AppCompatActivity {
    static int point=0;
    static int counter=0;
    final static int max=10;
    ImageView imageView ;
    TextView textView;
    EditText editText;
    LiveButton btnAnswer,btnReset;
    TextView poinTextView;
    ApiController apiController;
    ArrayList<HashMap<String,String>> dataKuiz;
    HashMap<String,String> selectedRandomData;
    GenericRequestBuilder<Uri, InputStream, com.caverock.androidsvg.SVG, PictureDrawable> requestBuilder;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setTitle("Quiz Mode");
        imageView = (ImageView) findViewById(R.id.gambarPertanyaan);
        textView = (TextView) findViewById(R.id.tvPertanyaan);
        poinTextView = (TextView) findViewById(R.id.score);
        editText = (EditText) findViewById(R.id.inputJawaban);
        btnAnswer = (LiveButton) findViewById(R.id.btnAnswer);
        btnReset = (LiveButton) findViewById(R.id.btnReset);
        requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(com.caverock.androidsvg.SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<com.caverock.androidsvg.SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.error_circle)
                .error(R.drawable.error_center_x)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        btnAnswer.setHeight(70);
        btnReset.setHeight(70);
        apiController=new ApiController();
        dataKuiz = new ArrayList<>();
        selectedRandomData = new HashMap<>();
        btnAnswer.setText("Selanjutnya");
        poinTextView.setText("Point : "+point);
        if(counter<max){

            generateQuizCompetition();

            textView.setText("Apa nama dari tim ini ?");
            textView.setGravity(Gravity.CENTER);
            btnAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String rsltMssg="";
                    int errType=0;
                    if(editText.getText().toString().equalsIgnoreCase(selectedRandomData.get("team"))) {
                        point++;
                        rsltMssg="Jawaban Anda Benar";
                        errType = SweetAlertDialog.SUCCESS_TYPE;
                    }
                    else{
                        rsltMssg="Jawaban Anda Salah";
                        errType = SweetAlertDialog.ERROR_TYPE;
                    }
                    counter++;
                    new SweetAlertDialog(QuizActivity.this, errType)
                            .setTitleText("Notification")
                            .setContentText(rsltMssg)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent refresh = new Intent(getApplicationContext(), QuizActivity.class);
                                    startActivity(refresh);
                                    finish();
                                }
                            })
                            .show();
                }
            });
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateQuizCompetition();
                }
            });
        }
        else{
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Hasil Quiz")
                    .setContentText("Selamat Poin Anda : "+point)
                    .setConfirmText("Ok !")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            counter=point=0;
                            sDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Apakah anda ingin keluar dari kuis mode ?")
                .setConfirmText("Iya")
                .setCancelText("Tidak")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        counter=point=0;
                        sDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),Home.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void generateQuizCompetition(){
        int min = FootballData.COMP_EUROPEAN_CHAMPIONSHIPS_FRANCE;
        int max = FootballData.COMP_CHAMPIONS_LEAGUE;

        Random r = new Random();
        int i = r.nextInt(max - min + 1) + min;

        if((i==FootballData.COMP_EUROPEAN_CHAMPIONSHIPS_FRANCE||i==FootballData.COMP_CHAMPIONS_LEAGUE)&&i!=429&&i!=432){
                new GetDataTeam(FootballData.COMPETITIONS+""+i+"/leagueTable").execute();
            }
            else if(i!=429&&i!=432){//429 FA CUP tidak ada data dan 432 juga tidak ada data
                new GetDataTeam2(FootballData.COMPETITIONS+""+i+"/leagueTable").execute();
          }
    }

    private class GetDataTeam extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetDataTeam(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(QuizActivity.this,
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
            result = apiController.getStringJSON(resource);
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
                        dataKuiz.add(tempData);
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
                        dataKuiz.add(tempData);
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
                        dataKuiz.add(tempData);
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
                        dataKuiz.add(tempData);
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
                        dataKuiz.add(tempData);
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
                        dataKuiz.add(tempData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Random rand = new Random();
            int n = rand.nextInt(dataKuiz.size());
            selectedRandomData=dataKuiz.get(n);
            Picasso.with(getApplicationContext()).setDebugging(true);
            String url = selectedRandomData.get("crestURI");

            Uri uri = Uri.parse(url);
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(uri)
                    .override(50,50)
                    .into(imageView);
            Log.e("Cek Data Ada gak : ",selectedRandomData.get("crestURI"));
            Log.e("Cek Data Ada gak : ",selectedRandomData.get("team"));
            pDialog.dismiss();
        }
    }

    private class GetDataTeam2 extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetDataTeam2(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(QuizActivity.this,
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
            result = apiController.getStringJSON(resource);
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
                        tempData.put("crestURI",data.getString("crestURI"));
                        tempData.put("win",data.getString("wins"));
                        tempData.put("playedGames",data.getString("playedGames"));
                        tempData.put("points",data.getString("points"));
                        tempData.put("goals",data.getString("goals"));
                        tempData.put("draw",data.getString("draws"));
                        tempData.put("losse",data.getString("losses"));
                        dataKuiz.add(tempData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Random rand = new Random();
            int n = rand.nextInt(dataKuiz.size());
            selectedRandomData=dataKuiz.get(n);
            Picasso.with(getApplicationContext()).setDebugging(true);
            String url = selectedRandomData.get("crestURI");

            Uri uri = Uri.parse(url);
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(uri)
                    .override(100,100)
                    .into(imageView);
            Log.e("Cek Data Ada gak : ",selectedRandomData.get("crestURI"));
            Log.e("Cek Data Ada gak : ",selectedRandomData.get("team"));
            pDialog.dismiss();
        }
    }


}
