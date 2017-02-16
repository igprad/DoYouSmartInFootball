package com.android.alz.doyousmartinfootball.controller;

import android.util.Log;

import com.android.alz.doyousmartinfootball.api.FootballData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ALz on 2/15/2017.
 */

public class ApiController {

    public void getDataCompetitions() throws IOException, JSONException {

        HttpClient client = new DefaultHttpClient();
        String getURL = FootballData.END_POINT+FootballData.COMPETITIONS;
        HttpGet httpGet = new HttpGet(getURL);
        httpGet.setHeader("X-Auth-Token",FootballData.KEY);
        HttpResponse response = client.execute(httpGet);
        HttpEntity resEntity = response.getEntity();
        if(resEntity!=null){
            Log.e("Response", EntityUtils.toString(resEntity));
        }
    }
}
