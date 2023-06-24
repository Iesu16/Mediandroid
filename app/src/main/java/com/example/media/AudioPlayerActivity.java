package com.example.media;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AudioPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private TextView timeTextView;
    private boolean isPlaying = false;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        playButton = findViewById(R.id.playButton);
        timeTextView = findViewById(R.id.timeTextView);

        // Inicializar el reproductor de audio con el archivo de audio en res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);

        handler = new Handler();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    // Pausar la reproducción si se está reproduciendo actualmente
                    mediaPlayer.pause();
                    playButton.setText("Play");
                } else {
                    // Iniciar la reproducción si está en pausa o se detuvo
                    mediaPlayer.start();
                    playButton.setText("Pause");
                    updateSeekBar();
                }
                isPlaying = !isPlaying;
            }
        });

        // Configurar el Runnable para actualizar el tiempo de avance
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
            }
        };
    }

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();

            // Actualizar el TextView con el tiempo de avance actualizado
            timeTextView.setText(formatTime(currentPosition));

            // Ejecutar la actualización del tiempo cada segundo
            handler.postDelayed(runnable, 1000);
        }
    }

    private String formatTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            updateSeekBar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar los recursos del reproductor de audio cuando la actividad se destruye
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        // Detener la actualización del tiempo
        handler.removeCallbacks(runnable);
    }
}