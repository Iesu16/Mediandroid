package com.example.media;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        // Establecer el controlador de medios para el VideoView
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Obtener la URI del video ubicado en res/raw
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);

        // Establecer la URI del video en el VideoView
        videoView.setVideoURI(videoUri);

        // Iniciar la reproducción del video
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar la reproducción del video cuando la actividad se pausa
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reanudar la reproducción del video cuando la actividad se reanuda
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar los recursos del reproductor de video cuando la actividad se destruye
        videoView.stopPlayback();
    }

    public void Audio (View view) {
        Intent i =new Intent( this, AudioPlayerActivity.class);
        startActivity(i);
    }
}