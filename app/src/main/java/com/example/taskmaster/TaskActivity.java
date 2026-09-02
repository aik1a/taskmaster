package com.example.taskmaster;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    private ArrayList<TaskModel> listaTareas;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        configurarSpinner();
        configurarRecyclerView();
    }

    private void configurarSpinner() {
        Spinner spinnerCategoria = findViewById(R.id.spinnerCategoria);
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
}
