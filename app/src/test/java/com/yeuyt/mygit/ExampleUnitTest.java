package com.yeuyt.mygit;


import android.util.Base64;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;




import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void t1() {
        String s =  "Basic " + new String(Base64.encode("luoxianwei:luo12345".getBytes(), Base64.DEFAULT));
        System.out.println(s);
    }


}