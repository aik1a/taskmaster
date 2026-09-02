package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnIrRegistro).setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}
