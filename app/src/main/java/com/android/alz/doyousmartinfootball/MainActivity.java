package com.android.alz.doyousmartinfootball;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.controller.ApiController;


public class MainActivity extends AppCompatActivity{

    private ApiController apiController = new ApiController();
    private TextView txtView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView1 = (TextView) findViewById(R.id.textData);

        new GetData(FootballData.TEAMS+
                FootballData.MANCHESTER_UNITED).execute();

    }


    private class GetData extends AsyncTask<Void,Void,Void>{
        private String resource,result ;

        public GetData(String resourceInput){resource=resourceInput;}

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this,"Json Data is " +
                    "downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result = apiController.getStringJSON(resource);
            Log.e("Result JSON :",result);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txtView1.setText(result);
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
        }
    }
}
