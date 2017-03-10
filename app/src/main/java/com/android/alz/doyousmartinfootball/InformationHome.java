package com.android.alz.doyousmartinfootball;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.controller.ApiController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.katso.livebutton.LiveButton;

public class InformationHome extends AppCompatActivity {
//    private ArrayList<HashMap<String,String>> resultJson;
    private ArrayList<String> tempData;
    private String tempStringHasilJSON="";
    private Spinner spinner;
    private LiveButton btnCompetitions,btnOK;
    private ArrayAdapter<String> stringArrayAdapter;
    private ApiController apiController = new ApiController();
    private TextView txtView1;
    private ArrayList<String> temp;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        resultJson = new ArrayList<HashMap<String, String>>();
        tempData = new ArrayList<String>();
        setContentView(R.layout.activity_information_home);

        btnCompetitions = (LiveButton) findViewById(R.id.btnCompetitions);

        listView = (ListView) findViewById(R.id.ViewListResult);

        btnCompetitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetData(FootballData.COMPETITIONS).execute();
            }
        });

    }
    private class GetData extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetData(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            Toast.makeText(InformationHome.this,"Json Data is " +
                    "downloading",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result = apiController.getStringJSON(resource);
            if(!result.isEmpty()){
                try {
                    JSONArray data = new JSONArray(result);
                    for (int i = 0; i <data.length() ; i++) {
//                        HashMap<String,String> tempData = new HashMap<String,String>();
//                        JSONObject object = data.getJSONObject(i);
//                        tempData.put("id",object.getString("id"));
//                            Log.e("ID : ",object.getString("id"));
//                        tempData.put("caption",object.getString("caption"));
//                            Log.e("ID : ",object.getString("caption"));
//                        tempData.put("league",object.getString("league"));
//                        Log.e("ID : ",object.getString("league"));
//                        tempData.put("year",object.getString("year"));
//                            Log.e("ID : ",object.getString("year"));
//                        tempData.put("currentMatchday",object.getString("currentMatchday"));
//                            Log.e("ID : ",object.getString("currentMatchday"));
//                        tempData.put("numberOfMatchdays",object.getString("numberOfMatchdays"));
//                            Log.e("ID : ",object.getString("numberOfMatchdays"));
//                        tempData.put("numberOfGames",object.getString("numberOfGames"));
//                        Log.e("ID : ",object.getString("numberOfGames"));
//                        tempData.put("lastUpdated",object.getString("lastUpdated"));
//                            Log.e("ID : ",object.getString("lastUpdated"));
//                        resultJson.add(tempData);


                        JSONObject object = data.getJSONObject(i);
                        tempData.add(object.getString("caption"));
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

            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
        }
    }


}
