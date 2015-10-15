package com.example.jean_leman.multibutton;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.TextView;

import com.example.jean_leman.multibutton.utlity.Utilities;

/**
 * Created by Jean-Leman on 3/15/2015.
 */
public class Music extends ActionBarActivity implements OnPreparedListener{

    MediaPlayer mpButtonclick, mpButtonclick2, mpButtonclick3 ;
    private TextView mMediaTime;
    private SeekBar mpSeek;
    private boolean   running = true;
    private int duration = 0;
    private  int current = 0;
    private Handler handler = new Handler();
    private Utilities utils = new Utilities();


    private ToggleButton toggleButton;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);

        mpButtonclick = MediaPlayer.create(this,R.raw.diamonds_from_sierra_leone);
        mpButtonclick2 = MediaPlayer.create(this,R.raw.lost_in_the_world);
        mpButtonclick3 = MediaPlayer.create(this, R.raw.big_brother);
        toggleButton = (ToggleButton) findViewById(R.id.media_control);
        mMediaTime = (TextView)findViewById(R.id.mediaTime);
        mpSeek = (SeekBar) findViewById(R.id.seek_Bar1);
        mpButtonclick.setOnPreparedListener(this);
        //This button function starts the song 1
        TextView bSong1 = (TextView) findViewById(R.id.Song1);

        bSong1.setText("diamonds from sierra leone");

        mpSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mpButtonclick.seekTo(progress);
                    updateProgressBar();
                }

            }
        });


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mpButtonclick.start();
                    handler.postDelayed(mUpdateTimeTask, 1000);
                } else {
                    mpButtonclick.pause();
                }

            }

        });


        //This button function starts the song 2
        Button bSong2 = (Button) findViewById(R.id.Song2);
        bSong2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View V) {

                mpButtonclick2.start();

            }

        });
        //This button function starts the song 3
        Button bSong3 = (Button) findViewById(R.id.Song3);
        bSong3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View V) {

                mpButtonclick3.start();

            }

        });


        // this button pauses song 2
        Button bStop2 = (Button) findViewById(R.id.Song2a);
        bStop2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View V) {

                mpButtonclick2.pause();

            }

        });
        // this button pauses song 3
        Button bStop3 = (Button) findViewById(R.id.Song3a);
        bStop3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View V) {

                mpButtonclick3.pause();

            }

        });

    }
    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if(true == running) {
                if (mpSeek != null) {
                    mpSeek.setProgress(mpButtonclick.getCurrentPosition());
                }
            }
                // Running this thread after 100 milliseconds
            if(mpButtonclick.isPlaying()) {
                handler.postDelayed(mUpdateTimeTask, 1000);
                updateProgressBar();
            }
        }
    };


    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        do {
            current = mpButtonclick.getCurrentPosition();
            System.out.println("duration - " + duration + " current- "
                    + current);
            int dSeconds = (int) (duration / 1000) % 60 ;
            int dMinutes = (int) ((duration / (1000*60)) % 60);
            int dHours   = (int) ((duration / (1000*60*60)) % 24);

            int cSeconds = (int) (current / 1000) % 60 ;
            int cMinutes = (int) ((current / (1000*60)) % 60);
            int cHours   = (int) ((current / (1000*60*60)) % 24);

            if(dHours == 0){
                mMediaTime.setText(String.format("%02d:%02d / %02d:%02d", cMinutes, cSeconds, dMinutes, dSeconds));
            }else{
                mMediaTime.setText(String.format("%02d:%02d:%02d / %02d:%02d:%02d", cHours, cMinutes, cSeconds, dHours, dMinutes, dSeconds));
            }

            try{
                Log.d("Value: ", String.valueOf((int) (current * 100 / duration)));
                if(mpSeek.getProgress() >= 100){
                    break;
                }
            }catch (Exception e) {}
        }while (mpSeek.getProgress() <= 100);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mpButtonclick3.release();
        mpButtonclick2.release();
        mpButtonclick.release();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = mpButtonclick.getDuration();
        mpSeek.setMax(duration);
        mpSeek.postDelayed(mUpdateTimeTask, 1000);
    }
}
