package com.android.alz.doyousmartinfootball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import br.com.bloder.magic.view.MagicButton;

public class Home extends AppCompatActivity {

    private MagicButton btnQuiz,btnInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        btnQuiz = (MagicButton) findViewById(R.id.btnQuiz);
        btnInformation = (MagicButton) findViewById(R.id.btnFyi);

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
    }
}
