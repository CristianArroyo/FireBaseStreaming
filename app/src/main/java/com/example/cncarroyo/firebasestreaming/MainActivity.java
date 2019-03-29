package com.example.cncarroyo.firebasestreaming;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;




public class MainActivity extends AppCompatActivity {

    private VideoView mainvideoView;
    private Uri videoUri;
    private ProgressBar currentProgress;
    private ProgressBar bufferProgress;
    private ImageButton btnPlay;
    private TextView currentTimaer;
    private TextView durationTimer;

    private int current =0;
    private int duration =0;
    private static final String POSITION= " POSITION ";

    private boolean isPlaying;

    private int mPosition =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


           EstadoConexion();

        isPlaying= false;

        mainvideoView=(VideoView)findViewById(R.id.mainvideoView);

        bufferProgress=(ProgressBar)findViewById(R.id.bufferProgress);
        videoUri= Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-streaming-669c4.appspot.com/o/video%20kotlin.mp4?alt=media&token=f5ac31cb-9931-44b8-a132-f0ee7abe01bc");
        currentProgress =(ProgressBar) findViewById(R.id.idVideoProgress);
        currentProgress.setMax(100);
        currentTimaer = (TextView)findViewById(R.id.idCurrentTimer);
        durationTimer = (TextView)findViewById(R.id.idDurationTimer);
        btnPlay=(ImageButton)findViewById(R.id.btnPlay);
        btnPlay.setImageResource(R.mipmap.boton_pausa);
        mainvideoView.setVideoURI(videoUri);
        mainvideoView.requestFocus();



        mainvideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {



               if (what ==mp.MEDIA_INFO_BUFFERING_START){

                   bufferProgress.setVisibility(View.VISIBLE);

               }else if (what == mp.MEDIA_INFO_BUFFERING_END) {

                  bufferProgress.setVisibility(View.INVISIBLE);

               }


                return false;
            }
        });

        mainvideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                duration=mp.getDuration()/1000;
                String durationString = String.format("%02d:%02d",duration/60,duration%60);

                durationTimer.setText(durationString);

            }
        });


        mainvideoView.start();
        isPlaying = true;

        new VideoProgress().execute();


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                if (isPlaying){

                    mainvideoView.pause();
                    isPlaying = false;
                    btnPlay.setImageResource(R.mipmap.flecha_reproducir);

                } else{

                    mainvideoView.start();
                    btnPlay.setImageResource(R.mipmap.boton_pausa);
                    isPlaying= true;
                }



            }
        });




    }


    @Override
    public void onStop() {
        super.onStop();

        isPlaying=false;
    }

    public class VideoProgress extends AsyncTask<Void,Integer,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            do {

                if (isPlaying) {

                    current = mainvideoView.getCurrentPosition() / 1000;
                    publishProgress(current);

                }

                //   int currentPercent = current * 100 / duration;

                //   publishProgress(currentPercent);


            }while (currentProgress.getProgress()<=100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            try {

                int currentPercent = values[0] * 100 / duration;
                currentProgress.setProgress(currentPercent);

                String currentString = String.format("%02d:%02d",values[0] /60,values[0] % 60);
                currentTimaer.setText(currentString);


            }catch (Exception e){



            }
        }

    }


    public void EstadoConexion(){

        ConnectivityManager connectivityManager =(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){

          //  Toast.makeText(getApplicationContext(),"Conectado a red de Internet",Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(getApplicationContext(), "No se encuentra conectado a Internet", Toast.LENGTH_SHORT).show();
        }


    }
}
