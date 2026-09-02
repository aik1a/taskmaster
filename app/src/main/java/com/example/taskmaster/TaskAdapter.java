package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> listaTareas;

    public TaskAdapter(ArrayList<TaskModel> listaTareas) {
        this.listaTareas = listaTareas;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel tarea = listaTareas.get(position);
        holder.tvItemNombre.setText(tarea.getNombre());
        holder.tvItemCategoria.setText("Categoría: " + tarea.getCategoria());
        holder.tvItemPrioridad.setText("Prioridad: " + tarea.getPrioridad());
        holder.tvItemDificultad.setText("Dificultad: " + (int) tarea.getDificultad() + "/5");
        holder.tvItemEstado.setText("Estado: " + (tarea.isCompletada() ? "Completada" : "Pendiente"));
    }

    @Override
    public int getItemCount() {
        return listaTareas != null ? listaTareas.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemNombre;
        TextView tvItemCategoria;
        TextView tvItemPrioridad;
        TextView tvItemDificultad;
        TextView tvItemEstado;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemNombre = itemView.findViewById(R.id.tvItemNombre);
            tvItemCategoria = itemView.findViewById(R.id.tvItemCategoria);
            tvItemPrioridad = itemView.findViewById(R.id.tvItemPrioridad);
            tvItemDificultad = itemView.findViewById(R.id.tvItemDificultad);
            tvItemEstado = itemView.findViewById(R.id.tvItemEstado);
        }
    }
}
