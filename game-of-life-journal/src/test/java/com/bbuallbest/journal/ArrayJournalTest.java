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

    @Test
    public void sortByImportanceSourceDate() {
        Record record1 = new Record("2015-11-17 09-48-58 !!!!! source1 some message1");
        Record record2 = new Record("2017-11-17 09-48-58 !!!   source2 some message2");
        Record record3 = new Record("2014-11-17 09-48-58 !!!   source3 some message3");
        Record record4 = new Record("2012-11-17 09-48-58 !!!!! source2 some message4");
        Record record5 = new Record("2013-11-17 09-48-58 !!!!! source4 some message2");
        Record record6 = new Record("2018-11-17 09-48-58 !!!   source3 some message3");

        Journal testJournal = new ArrayJournal();
        testJournal.add(record1);
        testJournal.add(record2);
        testJournal.add(record3);
        testJournal.add(record4);
        testJournal.add(record5);
        testJournal.add(record6);

        Journal expectedJournal = new ArrayJournal();
        expectedJournal.add(record2);
        expectedJournal.add(record3);
        expectedJournal.add(record6);
        expectedJournal.add(record1);
        expectedJournal.add(record4);
        expectedJournal.add(record5);

        testJournal.sortByImportanceSourceDate();
        System.out.println("-----");
        testJournal.printRecords();
        System.out.println("--------");
        expectedJournal.printRecords();
        Assert.assertTrue(expectedJournal.equals(testJournal));
    }

}
