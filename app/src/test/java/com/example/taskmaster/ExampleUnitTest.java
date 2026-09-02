package com.example.taskmaster;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void packageName_isCanonical() {
        assertEquals("com.example.taskmaster", LoginActivity.class.getPackage().getName());
    }
}

