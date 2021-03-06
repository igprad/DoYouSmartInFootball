package com.android.alz.doyousmartinfootball.controller;

import android.util.Log;

import com.android.alz.doyousmartinfootball.api.FootballData;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ALz on 2/15/2017.
 */

public class ApiController {

    public String getStringJSON(String resourceUrlRequest){

        String getURL = FootballData.END_POINT+resourceUrlRequest;
        try {
            URL url = new URL(getURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Auth-Token", FootballData.KEY);
            InputStream in = new BufferedInputStream(conn.getInputStream());

            return convertStreamToString(in);

        }catch(IOException ex){
            Log.e("Errors: ",ex.toString());
        }
        return null;
    }

    public String getStringJSON(String resourceUrlRequest,int version){

        String getURL = resourceUrlRequest;
        try {
            URL url = new URL(getURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Auth-Token", FootballData.KEY);
            InputStream in = new BufferedInputStream(conn.getInputStream());

            return convertStreamToString(in);

        }catch(IOException ex){
            Log.e("Errors: ",ex.toString());
        }
        return null;
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


}
