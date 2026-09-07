package com.example.taskmaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "TaskMasterPrefs";
    public static final String KEY_USER_REGISTERED = "USER_REGISTERED";
    public static final String KEY_USER_NAME = "USER_NAME";
    public static final String KEY_USER_LAST_NAME = "USER_LAST_NAME";
    public static final String KEY_USER_GENDER = "USER_GENDER";
    public static final String KEY_USER_EMAIL = "USER_EMAIL";
    public static final String KEY_USER_PHONE = "USER_PHONE";
    public static final String KEY_USER_PASSWORD = "USER_PASSWORD";

    private EditText etRegistroNombre;
    private EditText etRegistroApellidos;
    private Spinner spinnerRegistroGenero;
    private EditText etRegistroCorreo;
    private EditText etRegistroTelefono;
    private EditText etRegistroPassword;
    private Button btnRegistrarUsuario;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegistroNombre = findViewById(R.id.etRegistroNombre);
        etRegistroApellidos = findViewById(R.id.etRegistroApellidos);
        spinnerRegistroGenero = findViewById(R.id.spinnerRegistroGenero);
        etRegistroCorreo = findViewById(R.id.etRegistroCorreo);
        etRegistroTelefono = findViewById(R.id.etRegistroTelefono);
        etRegistroPassword = findViewById(R.id.etRegistroPassword);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);

        // Text To Speech automatico
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.forLanguageTag("es"));
                hablar("Crear perfil local en TaskMaster");
            }
        });

        configurarCamposTexto();

        btnRegistrarUsuario.setOnClickListener(v -> procesarRegistro());
    }

    private void hablar(String texto) {
        if (textToSpeech != null) {
            textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "tts1");
        }
    }

    private void configurarCamposTexto() {
        if (etRegistroNombre != null) {
            etRegistroNombre.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    String contenido = etRegistroNombre.getText() != null ? etRegistroNombre.getText().toString().trim() : "";
                    if (contenido.isEmpty()) {
                        hablar("Campo: Nombre");
                    } else {
                        hablar("Nombre: " + contenido);
                    }
                }
            });
        }
        if (etRegistroApellidos != null) {
            etRegistroApellidos.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    String contenido = etRegistroApellidos.getText() != null ? etRegistroApellidos.getText().toString().trim() : "";
                    if (contenido.isEmpty()) {
                        hablar("Campo: Apellidos");
                    } else {
                        hablar("Apellidos: " + contenido);
                    }
                }
            });
        }
        if (etRegistroCorreo != null) {
            etRegistroCorreo.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    String contenido = etRegistroCorreo.getText() != null ? etRegistroCorreo.getText().toString().trim() : "";
                    if (contenido.isEmpty()) {
                        hablar("Campo: Correo electrónico");
                    } else {
                        hablar("Correo: " + contenido);
                    }
                }
            });
        }
        if (etRegistroTelefono != null) {
            etRegistroTelefono.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    String contenido = etRegistroTelefono.getText() != null ? etRegistroTelefono.getText().toString().trim() : "";
                    if (contenido.isEmpty()) {
                        hablar("Campo: Teléfono móvil");
                    } else {
                        hablar("Teléfono: " + contenido);
                    }
                }
            });
        }
        if (etRegistroPassword != null) {
            etRegistroPassword.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    hablar("Campo: Contraseña");
                }
            });
        }
    }

    private void procesarRegistro() {
        limpiarErrores();

        String nombre = etRegistroNombre.getText() != null ? etRegistroNombre.getText().toString().trim() : "";
        if (nombre.isEmpty()) {
            hablar("Ingresa tu nombre.");
            etRegistroNombre.setError("Ingresa tu nombre.");
            etRegistroNombre.requestFocus();
            return;
        }
        if (nombre.length() < 2 || nombre.length() > 50) {
            hablar("El nombre debe tener entre 2 y 50 caracteres.");
            etRegistroNombre.setError("El nombre debe tener entre 2 y 50 caracteres.");
            etRegistroNombre.requestFocus();
            return;
        }

        String apellidos = etRegistroApellidos.getText() != null ? etRegistroApellidos.getText().toString().trim() : "";
        if (apellidos.isEmpty()) {
            hablar("Ingresa tus apellidos.");
            etRegistroApellidos.setError("Ingresa tus apellidos.");
            etRegistroApellidos.requestFocus();
            return;
        }
        if (apellidos.length() < 2 || apellidos.length() > 80) {
            hablar("Los apellidos deben tener entre 2 y 80 caracteres.");
            etRegistroApellidos.setError("Los apellidos deben tener entre 2 y 80 caracteres.");
            etRegistroApellidos.requestFocus();
            return;
        }

        if (spinnerRegistroGenero.getSelectedItemPosition() == 0) {
            hablar("Selecciona tu género.");
            Toast.makeText(this, "Selecciona tu género.", Toast.LENGTH_SHORT).show();
            View selectedView = spinnerRegistroGenero.getSelectedView();
            if (selectedView instanceof TextView) {
                ((TextView) selectedView).setError("Selecciona tu género.");
            }
            spinnerRegistroGenero.requestFocus();
            return;
        }
        String genero = spinnerRegistroGenero.getSelectedItem().toString();

        String correoRaw = etRegistroCorreo.getText() != null ? etRegistroCorreo.getText().toString().trim() : "";
        if (correoRaw.isEmpty()) {
            hablar("Ingresa tu correo.");
            etRegistroCorreo.setError("Ingresa tu correo.");
            etRegistroCorreo.requestFocus();
            return;
        }
        String correo = correoRaw.toLowerCase();
        if (correo.contains(" ") || correo.length() > 254 || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            hablar("Ingresa un correo válido.");
            etRegistroCorreo.setError("Ingresa un correo válido.");
            etRegistroCorreo.requestFocus();
            return;
        }

        String telefonoRaw = etRegistroTelefono.getText() != null ? etRegistroTelefono.getText().toString().trim() : "";
        if (telefonoRaw.isEmpty()) {
            hablar("Ingresa tu teléfono móvil.");
            etRegistroTelefono.setError("Ingresa tu teléfono móvil.");
            etRegistroTelefono.requestFocus();
            return;
        }
        if (telefonoRaw.length() != 9 || !telefonoRaw.startsWith("9") || !telefonoRaw.matches("^9\\d{8}$")) {
            hablar("Ingresa un teléfono móvil chileno válido.");
            etRegistroTelefono.setError("Ingresa un teléfono móvil chileno válido.");
            etRegistroTelefono.requestFocus();
            return;
        }
        String telefonoNormalizado = "+56" + telefonoRaw;

        String password = etRegistroPassword.getText() != null ? etRegistroPassword.getText().toString() : "";
        if (password.isEmpty()) {
            hablar("Ingresa tu contraseña.");
            etRegistroPassword.setError("Ingresa tu contraseña.");
            etRegistroPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            hablar("La contraseña debe tener mínimo 8 caracteres.");
            etRegistroPassword.setError("La contraseña debe tener mínimo 8 caracteres.");
            etRegistroPassword.requestFocus();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean yaRegistrado = prefs.getBoolean(KEY_USER_REGISTERED, false);

        if (yaRegistrado) {
            hablar("Ya existe un usuario registrado.");
            new AlertDialog.Builder(this)
                    .setTitle("Reemplazar perfil")
                    .setMessage("Ya existe un usuario registrado. Al registrar un nuevo perfil se reemplazarán los datos locales actuales.")
                    .setPositiveButton("Reemplazar", (dialog, which) ->
                            guardarPerfil(prefs, nombre, apellidos, genero, correo, telefonoNormalizado, password))
                    .setNegativeButton("Cancelar", null)
                    .show();
        } else {
            guardarPerfil(prefs, nombre, apellidos, genero, correo, telefonoNormalizado, password);
        }
    }

    private void limpiarErrores() {
        etRegistroNombre.setError(null);
        etRegistroApellidos.setError(null);
        etRegistroCorreo.setError(null);
        etRegistroTelefono.setError(null);
        etRegistroPassword.setError(null);
        View selectedView = spinnerRegistroGenero.getSelectedView();
        if (selectedView instanceof TextView) {
            ((TextView) selectedView).setError(null);
        }
    }

    private void guardarPerfil(SharedPreferences prefs, String nombre, String apellidos,
                              String genero, String correo, String telefono, String password) {
        prefs.edit()
                .putBoolean(KEY_USER_REGISTERED, true)
                .putString(KEY_USER_NAME, nombre)
                .putString(KEY_USER_LAST_NAME, apellidos)
                .putString(KEY_USER_GENDER, genero)
                .putString(KEY_USER_EMAIL, correo)
                .putString(KEY_USER_PHONE, telefono)
                .putString(KEY_USER_PASSWORD, password)
                .apply();
        Toast.makeText(this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        finish();
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