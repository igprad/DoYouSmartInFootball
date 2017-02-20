package com.android.alz.doyousmartinfootball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ru.katso.livebutton.LiveButton;

public class InformationHome extends AppCompatActivity {
//    private Spinner spinner;
    private LiveButton btnPlayers,btnTeams,btnCompetitions,btnLeagues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_home);
//        spinner = (Spinner) findViewById(R.id.spinner);
        btnPlayers = (LiveButton) findViewById(R.id.btnPlayers);
        btnPlayers = (LiveButton) findViewById(R.id.btnPlayers);
        btnPlayers = (LiveButton) findViewById(R.id.btnPlayers);
        btnPlayers = (LiveButton) findViewById(R.id.btnPlayers);
//        ArrayAdapter<String> stringArrayAdapter
//                = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.array_category));
//        spinner.setAdapter(stringArrayAdapter);
    }
}
