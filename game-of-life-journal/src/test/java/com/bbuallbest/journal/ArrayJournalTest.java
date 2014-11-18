package com.bbuallbest.journal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by happy on 18/11/2014.
 */
public class ArrayJournalTest {

    @Test
    public void testRemoveByElement() {
        int deleteRecordPosition = 2;
        Record deleteRecord = new Record("1999-12-12 12-12-12 !!!!! src msg" + deleteRecordPosition);

        ArrayJournal expectedJournal = new ArrayJournal();
        ArrayJournal testJournal = new ArrayJournal();
        for (int i = 0; i < 5; i++) {
            testJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
            if (i != deleteRecordPosition)
                expectedJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
        }

        testJournal.remove(deleteRecord);
        Assert.assertArrayEquals(expectedJournal.getRecords(), testJournal.getRecords());
    }

    @Test
    public void testAddInFullJournal() {
        Record lastAdded = new Record("1999-12-12 12-12-12 !!!!! src msg15");

        ArrayJournal testJournal = new ArrayJournal();
        for (int i = 0; i < 16; i++) {
            testJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
        }

        Assert.assertTrue(testJournal.size() == 16);
        Assert.assertTrue(lastAdded.equals(testJournal.get(15)));
    }

    @Test
    public void testInsert() {
        int insertPosition = 2;
        Record forInsert = new Record("1999-12-12 12-12-12 !!!!! src msg" + insertPosition);

        ArrayJournal testJournal = new ArrayJournal();
        ArrayJournal expectedJournal = new ArrayJournal();
        for (int i = 0; i < 5; i++) {
            expectedJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
            if (i != insertPosition)
                testJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
        }

        testJournal.insert(insertPosition, forInsert);
        Assert.assertArrayEquals(expectedJournal.getRecords(), testJournal.getRecords());
    }

    @Test
    public void testRemoveSequence() {
        int startIndex = 2;
        int stopIndex = 4;

        ArrayJournal testJournal = new ArrayJournal();
        ArrayJournal expectedJournal = new ArrayJournal();
        for (int i = 0; i < 7; i++) {
            testJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
            if (i < startIndex || i >= stopIndex)
                expectedJournal.add(new Record("1999-12-12 12-12-12 !!!!! src msg" + i));
        }


        testJournal.remove(startIndex, stopIndex);
        Assert.assertArrayEquals(expectedJournal.getRecords(), testJournal.getRecords());
    }
}
