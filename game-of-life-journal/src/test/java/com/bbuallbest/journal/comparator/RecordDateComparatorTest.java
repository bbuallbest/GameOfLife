package com.bbuallbest.journal.comparator;

import com.bbuallbest.journal.Record;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by happy on 18/11/2014.
 */
public class RecordDateComparatorTest {

    @Test
    public void testCompare() {
        Record lessRecord = new Record("1999-12-12 10-12-12 !!!!! src msg");
        Record greaterRecord = new Record("1999-12-12 09-12-13 !!!!! src msg");

        RecordDateComparator comparator = new RecordDateComparator();

        Assert.assertTrue(comparator.compare(lessRecord, greaterRecord) > 0);
    }
}
