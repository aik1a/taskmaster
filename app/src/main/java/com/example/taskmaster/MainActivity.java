package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "EXTRA_NOMBRE";

    private ImageView ivLogo;
    private TextView tvTitulo;
    private TextView tvDescripcion;
    private Button btnComenzar;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivLogo = findViewById(R.id.ivLogo);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        btnComenzar = findViewById(R.id.btnComenzar);

        String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        String texto;
        if (nombre != null && !nombre.trim().isEmpty()) {
            texto = "Hola, " + nombre.trim();
        } else {
            texto = "Hola";
        }
        tvDescripcion.setText(texto);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.forLanguageTag("es"));
                textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            }
        });

        btnComenzar.setOnClickListener(view -> {
            if (textToSpeech != null) {
                textToSpeech.stop();
            }
            startActivity(new Intent(MainActivity.this, TaskActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
