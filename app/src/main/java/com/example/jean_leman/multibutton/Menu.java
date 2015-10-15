package com.example.jean_leman.multibutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jean-Leman on 3/14/2015.
 */
public class Menu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testing the commit aspect of github
        Button bGoogle = (Button) findViewById(R.id.google_button);
        bGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {

                startActivity(new Intent("com.example.jean_leman.multibutton.Web"));
            }
        });
        Button bMusic = (Button) findViewById(R.id.music_button);
        bMusic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View V) {

                startActivity(new Intent("com.example.jean_leman.multibutton.Music"));
            }
        });
    }
}
