package com.yeuyt.mygit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;


import org.junit.Test;

import static org.junit.Assert.*;



public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yeuyt.mygit", appContext.getPackageName());
    }
}
