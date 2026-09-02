package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "EXTRA_NOMBRE";

    private ImageView ivLogo;
    private TextView tvTitulo;
    private TextView tvDescripcion;
    private Button btnComenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivLogo = findViewById(R.id.ivLogo);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        btnComenzar = findViewById(R.id.btnComenzar);

        String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        if (nombre != null && !nombre.trim().isEmpty()) {
            tvDescripcion.setText("Hola, " + nombre.trim());
        } else {
            tvDescripcion.setText("Hola, ");
        }

        btnComenzar.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, TaskActivity.class)));
    }
}
