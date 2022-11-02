package com.mohammed.mosa.eg.egsim;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void sups_isCorrect(){
        assertEquals(1, 5 - 4);
    }

    @Test
    public void clear_isOk(){
        assertEquals("01121523362", clearString("+20 11 2152 3362"));
        assertEquals("01551073362", clearString("+20 15 5107 3362"));
    }

    private String clearString(String string){
        if(string.startsWith("+2")){
            return string.substring(2).replaceAll("\\s+", "");
        }
        return string.replaceAll("\\s+", "");
    }
}