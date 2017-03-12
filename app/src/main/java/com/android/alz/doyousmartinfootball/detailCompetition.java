package com.android.alz.doyousmartinfootball;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.controller.ApiController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class detailCompetition extends AppCompatActivity {
    ApiController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_competition);

        apiController = new ApiController();
    }


    private class GetDataTeam extends AsyncTask<Void,Void,Void> {
        private String resource,result ;

        public GetDataTeam(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            result = apiController.getStringJSON(resource);
            if(!result.isEmpty()){
                try {
                    JSONArray data = new JSONArray(result);
                    for (int i = 0; i <data.length() ; i++) {

                        JSONObject object = data.getJSONObject(i);

//                        Log.e("error",tempData.get(i).toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
//                    android.R.layout.simple_list_item_1,
//                    android.R.id.text1,tempData);
//
//            listView.setAdapter(adapter);

            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
        }
    }

}


