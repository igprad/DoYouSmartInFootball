package com.android.alz.doyousmartinfootball;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.controller.ApiController;

import ru.katso.livebutton.LiveButton;

public class InformationHome extends AppCompatActivity {
    private Spinner spinner;
    private LiveButton btnCompetitions,btnOK;
    private ArrayAdapter<String> stringArrayAdapter;
    private ApiController apiController = new ApiController();
    private TextView txtView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_home);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnCompetitions = (LiveButton) findViewById(R.id.btnCompetitions);
        btnOK = (LiveButton) findViewById(R.id.btnOK);
        txtView1 = (TextView) findViewById(R.id.txtData);
        setAdapterSpinnerCategory();

        btnCompetitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdapterSpinnerCategory();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem().toString().equalsIgnoreCase("Competitions")){
                    //Untuk data competitions
                    new GetData(FootballData.COMPETITIONS).execute();
                }
            }
        });
    }

    public void setAdapterSpinnerCategory(){
        stringArrayAdapter
                = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.array_for_competitions));
        spinner.setAdapter(stringArrayAdapter);
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
