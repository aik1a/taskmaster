package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> listaTareas;
    private OnTaskCompletionChangedListener completionChangedListener;

    public TaskAdapter(ArrayList<TaskModel> listaTareas,
                       OnTaskCompletionChangedListener completionChangedListener) {
        this.listaTareas = listaTareas;
        this.completionChangedListener = completionChangedListener;
    }

    public interface OnTaskCompletionChangedListener {
        void onTaskCompletionChanged();
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
        holder.cbItemTarea.setOnCheckedChangeListener(null);
        holder.cbItemTarea.setText(tarea.getNombre());
        holder.cbItemTarea.setChecked(tarea.isCompletada());
        holder.cbItemTarea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarea.setCompletada(isChecked);
            if (completionChangedListener != null) {
                completionChangedListener.onTaskCompletionChanged();
            }
        });
        holder.tvItemCategoria.setText("Categoría: " + tarea.getCategoria());
        holder.tvItemPrioridad.setText("Prioridad: " + tarea.getPrioridad());
        holder.tvItemDificultad.setText("Dificultad: " + (int) tarea.getDificultad() + "/5");
    }

    @Override
    public int getItemCount() {
        return listaTareas != null ? listaTareas.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbItemTarea;
        TextView tvItemCategoria;
        TextView tvItemPrioridad;
        TextView tvItemDificultad;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cbItemTarea = itemView.findViewById(R.id.cbItemTarea);
            tvItemCategoria = itemView.findViewById(R.id.tvItemCategoria);
            tvItemPrioridad = itemView.findViewById(R.id.tvItemPrioridad);
            tvItemDificultad = itemView.findViewById(R.id.tvItemDificultad);
        }
    }
}
