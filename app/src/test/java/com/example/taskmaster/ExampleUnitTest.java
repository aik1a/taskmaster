package com.example.taskmaster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;


public class ExampleUnitTest {
    @Test
    public void packageName_isCanonical() {
        assertEquals("com.example.taskmaster", LoginActivity.class.getPackage().getName());
    }

    @Test
    public void normalizarTelefono_nueveDigitosDirecto() {
        assertEquals("912345678", LoginActivity.normalizarTelefonoChileno("912345678"));
    }

    @Test
    public void normalizarTelefono_conPrefijoMas56() {
        assertEquals("912345678", LoginActivity.normalizarTelefonoChileno("+56912345678"));
    }

    @Test
    public void normalizarTelefono_conEspaciosYFormato() {
        assertEquals("912345678", LoginActivity.normalizarTelefonoChileno("+56 9 1234 5678"));
        assertEquals("912345678", LoginActivity.normalizarTelefonoChileno("(9) 1234-5678"));
    }

    @Test
    public void normalizarTelefono_invalidoConLetras() {
        assertNull(LoginActivity.normalizarTelefonoChileno("9123abc"));
    }

    @Test
    public void normalizarTelefono_invalidoNoComienzaEn9() {
        assertNull(LoginActivity.normalizarTelefonoChileno("812345678"));
        assertNull(LoginActivity.normalizarTelefonoChileno("+56812345678"));
    }

    @Test
    public void normalizarTelefono_invalidoLongitud() {
        assertNull(LoginActivity.normalizarTelefonoChileno("91234567"));
        assertNull(LoginActivity.normalizarTelefonoChileno("9123456789"));
        assertNull(LoginActivity.normalizarTelefonoChileno(""));
        assertNull(LoginActivity.normalizarTelefonoChileno(null));
    }

    @Test
    public void constantesSharedPreferences_cumplenCanon() {
        assertEquals("TaskMasterPrefs", RegisterActivity.PREFS_NAME);
        assertEquals("USER_REGISTERED", RegisterActivity.KEY_USER_REGISTERED);
        assertEquals("USER_NAME", RegisterActivity.KEY_USER_NAME);
        assertEquals("USER_LAST_NAME", RegisterActivity.KEY_USER_LAST_NAME);
        assertEquals("USER_GENDER", RegisterActivity.KEY_USER_GENDER);
        assertEquals("USER_EMAIL", RegisterActivity.KEY_USER_EMAIL);
        assertEquals("USER_PHONE", RegisterActivity.KEY_USER_PHONE);
        assertEquals("USER_PASSWORD", RegisterActivity.KEY_USER_PASSWORD);
    }

    @Test
    public void normalizarTelefono_invalidoPrefijo56SinMas() {
        assertNull(LoginActivity.normalizarTelefonoChileno("56912345678"));
    }

    @Test
    public void constanteExtraNombre_cumpleCanon() {
        assertEquals("EXTRA_NOMBRE", MainActivity.EXTRA_NOMBRE);
    }

    @Test
    public void mainActivity_packageName_isCanonical() {
        assertEquals("com.example.taskmaster", MainActivity.class.getPackage().getName());
    }

    @Test
    public void taskActivity_packageName_isCanonical() {
        assertEquals("com.example.taskmaster", TaskActivity.class.getPackage().getName());
    }

    @Test
    public void taskModel_packageName_isCanonical() {
        assertEquals("com.example.taskmaster", TaskModel.class.getPackage().getName());
    }

    @Test
    public void taskAdapter_packageName_isCanonical() {
        assertEquals("com.example.taskmaster", TaskAdapter.class.getPackage().getName());
    }

    @Test
    public void taskModel_construccionYGetters_cumplenCanon() {
        TaskModel tarea = new TaskModel("Comprar insumos", "Personal", "Media", 3.0f, true);
        assertEquals("Comprar insumos", tarea.getNombre());
        assertEquals("Personal", tarea.getCategoria());
        assertEquals("Media", tarea.getPrioridad());
        assertEquals(3.0f, tarea.getDificultad(), 0.001f);
        assertTrue(tarea.isCompletada());
    }

    @Test
    public void taskModel_tareaPendiente_cumpleCanon() {
        TaskModel tarea = new TaskModel("Estudiar Android", "Estudio", "Alta", 5.0f, false);
        assertEquals("Estudiar Android", tarea.getNombre());
        assertEquals("Estudio", tarea.getCategoria());
        assertEquals("Alta", tarea.getPrioridad());
        assertEquals(5.0f, tarea.getDificultad(), 0.001f);
        assertFalse(tarea.isCompletada());
    }

    @Test
    public void taskAdapter_itemCount_reflejaDataset() {
        ArrayList<TaskModel> lista = new ArrayList<>();
        TaskAdapter adapter = new TaskAdapter(lista);
        assertEquals(0, adapter.getItemCount());

        lista.add(new TaskModel("Tarea 1", "Personal", "Baja", 1.0f, false));
        lista.add(new TaskModel("Tarea 2", "Trabajo", "Alta", 4.0f, true));
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void taskActivity_camposCanonicos_existen() throws NoSuchFieldException {
        assertEquals(ArrayList.class, TaskActivity.class.getDeclaredField("listaTareas").getType());
        assertEquals(TaskAdapter.class, TaskActivity.class.getDeclaredField("taskAdapter").getType());
        assertEquals(int.class, TaskActivity.class.getDeclaredField("totalTareas").getType());
        assertEquals(int.class, TaskActivity.class.getDeclaredField("tareasCompletadas").getType());
    }

    @Test
    public void taskActivity_metodosCanonicos_existen() throws NoSuchMethodException {
        assertNotNull(TaskActivity.class.getDeclaredMethod("configurarSpinner"));
        assertNotNull(TaskActivity.class.getDeclaredMethod("configurarRecyclerView"));
        assertNotNull(TaskActivity.class.getDeclaredMethod("agregarTarea"));
        assertNotNull(TaskActivity.class.getDeclaredMethod("actualizarResumen"));
        assertNotNull(TaskActivity.class.getDeclaredMethod("limpiarFormulario"));
    }
}
