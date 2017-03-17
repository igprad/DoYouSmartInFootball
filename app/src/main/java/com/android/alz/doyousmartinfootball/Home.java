package com.android.alz.doyousmartinfootball;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import br.com.bloder.magic.view.MagicButton;

public class Home extends AppCompatActivity {

    private MagicButton btnQuiz,btnInformation,btnShareFacebbok,btnShareTwitter;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btnQuiz = (MagicButton) findViewById(R.id.btnQuiz);
        btnInformation = (MagicButton) findViewById(R.id.btnFyi);
        btnShareFacebbok = (MagicButton) findViewById(R.id.btnFB);
        btnShareTwitter = (MagicButton) findViewById(R.id.btnTwit);
        btnQuiz.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(intent);
            }
        });
        btnInformation.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InformationHome.class);
                startActivity(intent);
            }
        });
        btnShareFacebbok.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Do You Smart in Football?")
                            .setContentDescription(
                                    "Do you dare to challenge your knowledge about football/soccer?")
                            .setContentUrl(Uri.parse("Link Play Store"))
                            .build();

                    shareDialog.show(linkContent);

                }
            }
        });
        btnShareTwitter.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTwit = new Intent(Home.this,TweetFootball.class);
                startActivity(goTwit);
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(Home.this, "Shared to your Facebook", Toast.LENGTH_SHORT).show();
    }
}
