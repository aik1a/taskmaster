package com.example.taskmaster;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity {

    private ArrayList<TaskModel> listaTareas;
    private TaskAdapter taskAdapter;

    private EditText etNombreTarea;
    private Spinner spinnerCategoria;
    private RadioGroup rgPrioridad;
    private RatingBar ratingDificultad;
    private Button btnAgregarTarea;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        etNombreTarea = findViewById(R.id.etNombreTarea);
        rgPrioridad = findViewById(R.id.rgPrioridad);
        ratingDificultad = findViewById(R.id.ratingDificultad);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);

        // Text To Speech automatico
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.forLanguageTag("es"));
                hablar("Gestión de tareas. Completa los datos y agrega tu tarea");
            }
        });

        configurarCamposTexto();
        configurarSpinner();
        configurarRecyclerView();

        if (btnAgregarTarea != null) {
            btnAgregarTarea.setOnClickListener(v -> agregarTarea());
        }
    }

    private void hablar(String texto) {
        if (textToSpeech != null) {
            textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "tts1");
        }
    }

    private void configurarCamposTexto() {
        if (etNombreTarea != null) {
            etNombreTarea.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    String contenido = etNombreTarea.getText() != null ? etNombreTarea.getText().toString().trim() : "";
                    if (contenido.isEmpty()) {
                        hablar("Campo: Nombre de la tarea");
                    } else {
                        hablar("Nombre de la tarea: " + contenido);
                    }
                }
            });
        }
    }

    private void configurarSpinner() {
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        if (spinnerCategoria != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.task_categories,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapter);
        }
    }

    private void configurarRecyclerView() {
        listaTareas = new ArrayList<>();
        taskAdapter = new TaskAdapter(listaTareas, this::actualizarResumen);
        RecyclerView rvTareas = findViewById(R.id.rvTareas);
        if (rvTareas != null) {
            rvTareas.setLayoutManager(new LinearLayoutManager(this));
            rvTareas.setAdapter(taskAdapter);
        }
    }

    private void agregarTarea() {
        String nombre = etNombreTarea != null && etNombreTarea.getText() != null
                ? etNombreTarea.getText().toString().trim() : "";
        if (nombre.isEmpty()) {
            hablar("Ingresa una tarea");
            Toast.makeText(this, "Ingresa una tarea", Toast.LENGTH_SHORT).show();
            if (etNombreTarea != null) {
                etNombreTarea.setError("Ingresa una tarea");
                etNombreTarea.requestFocus();
            }
            return;
        }
        if (etNombreTarea != null) {
            etNombreTarea.setError(null);
        }

        int selectedPrioridadId = rgPrioridad != null ? rgPrioridad.getCheckedRadioButtonId() : -1;
        if (selectedPrioridadId == -1) {
            hablar("Selecciona una prioridad");
            Toast.makeText(this, "Selecciona una prioridad", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rbSeleccionado = findViewById(selectedPrioridadId);
        String prioridad = rbSeleccionado != null ? rbSeleccionado.getText().toString() : "";

        String categoria = (spinnerCategoria != null && spinnerCategoria.getSelectedItem() != null)
                ? spinnerCategoria.getSelectedItem().toString() : "";

        float dificultad = ratingDificultad != null ? ratingDificultad.getRating() : 0.0f;

        TaskModel tarea = new TaskModel(nombre, categoria, prioridad, dificultad);
        listaTareas.add(tarea);
        taskAdapter.notifyDataSetChanged();

        actualizarResumen();
        limpiarFormulario();

        hablar("Tarea " + nombre + " agregada con éxito");
        Toast.makeText(this, "Tarea agregada", Toast.LENGTH_SHORT).show();
    }

    private void actualizarResumen() {
        int totalTareas = listaTareas.size();
        int tareasCompletadas = 0;
        for (TaskModel tarea : listaTareas) {
            if (tarea.isCompletada()) {
                tareasCompletadas++;
            }
        }
        int tareasPendientes = totalTareas - tareasCompletadas;
        int progreso = totalTareas == 0 ? 0 : tareasCompletadas * 100 / totalTareas;

        TextView tvTotalValor = findViewById(R.id.tvTotalValor);
        TextView tvCompletadasValor = findViewById(R.id.tvCompletadasValor);
        TextView tvPendientesValor = findViewById(R.id.tvPendientesValor);
        ProgressBar progressCompletadas = findViewById(R.id.progressCompletadas);

        if (tvTotalValor != null) tvTotalValor.setText(String.valueOf(totalTareas));
        if (tvCompletadasValor != null) tvCompletadasValor.setText(String.valueOf(tareasCompletadas));
        if (tvPendientesValor != null) tvPendientesValor.setText(String.valueOf(tareasPendientes));
        if (progressCompletadas != null) progressCompletadas.setProgress(progreso);
    }

    private void limpiarFormulario() {
        if (etNombreTarea != null) {
            etNombreTarea.setText("");
            etNombreTarea.setError(null);
        }
        if (spinnerCategoria != null) {
            spinnerCategoria.setSelection(0);
        }
        if (rgPrioridad != null) {
            rgPrioridad.clearCheck();
        }
        if (ratingDificultad != null) {
            ratingDificultad.setRating(0.0f);
        }
        if (etNombreTarea != null) {
            etNombreTarea.requestFocus();
        }
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