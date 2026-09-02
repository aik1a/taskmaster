package com.example.taskmaster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

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
}
