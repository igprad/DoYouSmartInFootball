package com.android.alz.doyousmartinfootball;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.controller.ApiController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

public class InformationHome extends AppCompatActivity {
    private ArrayList<String> tempData;
    private ArrayList<String> tempLinkLeagueTable;
    private ArrayList<String> tempJenis;
    private String tempStringHasilJSON="";
    private Spinner spinner;
    private LiveButton btnCompetitions,btnOK;
    private ArrayAdapter<String> stringArrayAdapter;
    private ApiController apiController = new ApiController();
    private TextView txtView1;
    private ArrayList<String> temp;
    private ListView listView;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Information");

        tempLinkLeagueTable = new ArrayList<>();
        tempData = new ArrayList<>();
        tempJenis = new ArrayList<>();

        setContentView(R.layout.activity_information_home);

        btnCompetitions = (LiveButton) findViewById(R.id.btnCompetitions);

        listView = (ListView) findViewById(R.id.ViewListResult);

        btnCompetitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetDataCompetition(FootballData.COMPETITIONS).execute();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToNextActivity = new Intent(getApplicationContext(),detailCompetition.class);
                Bundle data = new Bundle();
                data.putString("endpoint",tempLinkLeagueTable.get(position));
                data.putString("jenis",tempJenis.get(position));
                goToNextActivity.putExtras(data);
                startActivity(goToNextActivity);
            }
        });
    }
    private class GetDataCompetition extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetDataCompetition(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            tempData.clear();
            pDialog = new ProgressDialog(InformationHome.this,
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
            if(!result.isEmpty()){
                try {
                    JSONArray data = new JSONArray(result);
                    for (int i = 0; i <data.length() ; i++) {

                        JSONObject object = data.getJSONObject(i);
                        JSONObject objectLinks = object.getJSONObject("_links").getJSONObject("leagueTable");
                        tempLinkLeagueTable.add(objectLinks.getString("href"));

                        JSONObject object2 = data.getJSONObject(i);
                        tempData.add(object2.getString("caption"));
                        tempJenis.add(object2.getString("league"));

                        Log.e("error",tempData.get(i).toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,tempData);

            listView.setAdapter(adapter);
            pDialog.dismiss();
        }
    }

}
