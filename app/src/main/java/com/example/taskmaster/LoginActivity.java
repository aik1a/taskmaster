package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "TaskMasterPrefs";
    public static final String KEY_USER_REGISTERED = "USER_REGISTERED";
    public static final String KEY_USER_NAME = "USER_NAME";
    public static final String KEY_USER_LAST_NAME = "USER_LAST_NAME";
    public static final String KEY_USER_GENDER = "USER_GENDER";
    public static final String KEY_USER_EMAIL = "USER_EMAIL";
    public static final String KEY_USER_PHONE = "USER_PHONE";
    public static final String KEY_USER_PASSWORD = "USER_PASSWORD";

    private EditText etLoginIdentificador;
    private EditText etLoginPassword;
    private Button btnIngresar;
    private Button btnIrRegistro;
    private ProgressBar progressLogin;

    private boolean isValidating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginIdentificador = findViewById(R.id.etLoginIdentificador);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);
        progressLogin = findViewById(R.id.progressLogin);

        btnIrRegistro.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        btnIngresar.setOnClickListener(view -> procesarLogin());
    }

    private void procesarLogin() {
        if (isValidating) {
            return;
        }

        limpiarErrores();

        String identRaw = etLoginIdentificador.getText() != null ? etLoginIdentificador.getText().toString().trim() : "";
        String password = etLoginPassword.getText() != null ? etLoginPassword.getText().toString() : "";

        boolean esEmail = identRaw.contains("@");
        final String idNormalizado;

        if (esEmail) {
            String email = identRaw.toLowerCase();
            if (email.isEmpty() || email.contains(" ") || email.length() > 254 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etLoginIdentificador.setError("Ingresa un correo válido.");
                etLoginIdentificador.requestFocus();
                return;
            }
            idNormalizado = email;
        } else {
            if (identRaw.isEmpty()) {
                etLoginIdentificador.setError("Ingresa un correo o teléfono válido.");
                etLoginIdentificador.requestFocus();
                return;
            }
            String digitosNacionales = normalizarTelefonoChileno(identRaw);
            if (digitosNacionales == null) {
                etLoginIdentificador.setError("Ingresa un correo o teléfono válido.");
                etLoginIdentificador.requestFocus();
                return;
            }
            idNormalizado = "+56" + digitosNacionales;
        }

        if (password.isEmpty()) {
            etLoginPassword.setError("Ingresa tu contraseña.");
            etLoginPassword.requestFocus();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean registrado = prefs.getBoolean(KEY_USER_REGISTERED, false);
        if (!registrado) {
            Toast.makeText(this, "No hay un usuario registrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        isValidating = true;
        progressLogin.setVisibility(View.VISIBLE);
        btnIngresar.setEnabled(false);

        final boolean esEmailFinal = esEmail;
        final String passwordFinal = password;

        progressLogin.postDelayed(() -> {
            isValidating = false;
            progressLogin.setVisibility(View.GONE);
            btnIngresar.setEnabled(true);

            String savedEmail = prefs.getString(KEY_USER_EMAIL, "");
            String savedPhone = prefs.getString(KEY_USER_PHONE, "");
            String savedPassword = prefs.getString(KEY_USER_PASSWORD, "");

            boolean idCoincide = esEmailFinal ? idNormalizado.equals(savedEmail) : idNormalizado.equals(savedPhone);
            boolean passCoincide = passwordFinal.equals(savedPassword);

            if (idCoincide && passCoincide) {
                String savedName = prefs.getString(KEY_USER_NAME, "");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_NOMBRE, savedName);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Correo/teléfono o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
            }
        }, 500);
    }

    private void limpiarErrores() {
        etLoginIdentificador.setError(null);
        etLoginPassword.setError(null);
    }

    public static String normalizarTelefonoChileno(String input) {
        if (input == null) {
            return null;
        }
        String cleaned = input.replaceAll("[\\s\\-\\(\\)]", "");
        String digits;
        if (cleaned.startsWith("+56")) {
            digits = cleaned.substring(3);
        } else {
            digits = cleaned;
        }
        if (digits.length() == 9 && digits.startsWith("9") && digits.matches("^9\\d{8}$")) {
            return digits;
        }
        return null;
    }
}
