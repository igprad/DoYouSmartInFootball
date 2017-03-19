package com.android.alz.doyousmartinfootball.controller;

import android.util.Base64;
import android.util.Log;

import com.android.alz.doyousmartinfootball.api.FootballData;
import com.android.alz.doyousmartinfootball.api.TwitterApi;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALz on 3/18/2017.
 */

public class TwitterApiController {

    String encodedConsumerKey = URLEncoder.encode("k3VgnulSE8VXtkUXimEcs0pcv","UTF-8");

    String encodedConsumerSecret = URLEncoder.encode("QHgpLt8lnWSXdcfCtZ2TrmYCV26axkP59Tb645ZpBQGPWzQ8Da","UTF-8");

    String authString = encodedConsumerKey +":"+encodedConsumerSecret;

    String base64Encoded = Base64.encodeToString(authString.getBytes("UTF-8"), Base64.NO_WRAP);

    String receivedToken="";

    public TwitterApiController() throws UnsupportedEncodingException {
    }

    public String getBearer(){

        String getURL = TwitterApi.TWITTER_OAUTH_TOKEN;
        try {
//            URL url = new URL(getURL);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(String.valueOf(getURL));
            HttpParams httpParams = httppost.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams, 15000);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.setHeader("Authorization", "Basic " +base64Encoded.toString());
            HttpResponse response  = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            response = httpclient.execute(httppost);
            String returnedJsonStr = EntityUtils.toString(response.getEntity());

            int statusCode = response.getStatusLine().getStatusCode();
            Log.v("response",returnedJsonStr);
            Log.v("response",Integer.toString(statusCode));
            JSONObject jsonObject = new JSONObject(returnedJsonStr);
            //Receive Bearer token here.
            receivedToken = jsonObject.getString("access_token");
            Log.e("access_token",receivedToken);
            return receivedToken;

        }catch(IOException ex){
            Log.e("Errors: ",ex.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
