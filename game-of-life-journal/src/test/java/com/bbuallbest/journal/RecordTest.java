package com.bbuallbest.journal;

import org.junit.Test;

/**
 * Created by happy on 17/11/2014.
 */
public class RecordTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowIAExceptionOnIllegalStringFormat() {
        String test = "2015-11-17 21-48-58 !!!! source some message ";
        new Record(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowIAExceptionOnIllegalDateFormat() {
        String test = "2015/11/17 21/48/58 !!!!! source some message";
        new Record(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowIAExceptionOnIllegalImportanceFormat() {
        String test = "2015-11-17 21-48-58 !! source some message";
        new Record(test);
    }
}
