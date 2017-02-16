package com.android.alz.doyousmartinfootball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.alz.doyousmartinfootball.controller.ApiController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ApiController apiController = new ApiController();



            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        apiController.getDataCompetitions();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

    }
}
