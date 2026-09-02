package com.example.taskmaster;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class TaskActivity extends AppCompatActivity {

    private ArrayList<TaskModel> listaTareas;
    private TaskAdapter taskAdapter;
    private int totalTareas = 0;
    private int tareasCompletadas = 0;

    private EditText etNombreTarea;
    private Spinner spinnerCategoria;
    private RadioGroup rgPrioridad;
    private RatingBar ratingDificultad;
    private CheckBox cbCompletada;
    private Button btnAgregarTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        etNombreTarea = findViewById(R.id.etNombreTarea);
        rgPrioridad = findViewById(R.id.rgPrioridad);
        ratingDificultad = findViewById(R.id.ratingDificultad);
        cbCompletada = findViewById(R.id.cbCompletada);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);

        configurarSpinner();
        configurarRecyclerView();

        if (btnAgregarTarea != null) {
            btnAgregarTarea.setOnClickListener(v -> agregarTarea());
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
        taskAdapter = new TaskAdapter(listaTareas);
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
            Toast.makeText(this, "Selecciona una prioridad", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rbSeleccionado = findViewById(selectedPrioridadId);
        String prioridad = rbSeleccionado != null ? rbSeleccionado.getText().toString() : "";

        String categoria = (spinnerCategoria != null && spinnerCategoria.getSelectedItem() != null)
                ? spinnerCategoria.getSelectedItem().toString() : "";

        float dificultad = ratingDificultad != null ? ratingDificultad.getRating() : 0.0f;
        boolean completada = cbCompletada != null && cbCompletada.isChecked();

        TaskModel tarea = new TaskModel(nombre, categoria, prioridad, dificultad, completada);
        listaTareas.add(tarea);
        taskAdapter.notifyDataSetChanged();

        totalTareas++;
        if (completada) {
            tareasCompletadas++;
        }

        actualizarResumen();
        limpiarFormulario();

        Toast.makeText(this, "Tarea agregada", Toast.LENGTH_SHORT).show();
    }

    private void actualizarResumen() {
        int tareasPendientes = totalTareas - tareasCompletadas;
        int progreso = totalTareas == 0 ? 0 : tareasCompletadas * 100 / totalTareas;

        TextView tvTotalValor = findViewById(R.id.tvTotalValor);
        TextView tvCompletadasValor = findViewById(R.id.tvCompletadasValor);
        TextView tvPendientesValor = findViewById(R.id.tvPendientesValor);
        ProgressBar progressCompletadas = findViewById(R.id.progressCompletadas);

        tvTotalValor.setText(String.valueOf(totalTareas));
        tvCompletadasValor.setText(String.valueOf(tareasCompletadas));
        tvPendientesValor.setText(String.valueOf(tareasPendientes));
        progressCompletadas.setProgress(progreso);
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
        if (cbCompletada != null) {
            cbCompletada.setChecked(false);
        }
        if (etNombreTarea != null) {
            etNombreTarea.requestFocus();
        }
    }
}
