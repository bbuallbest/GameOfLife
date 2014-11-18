package com.bbuallbest.journal;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by happy on 18/11/2014.
 */
public class CollectionJournalTest {

    @Test
    public void testFilterByString() {
        String searchedString = "source2";
        Record record1 = new Record("2015-11-17 09-48-58 !!!!! source1 some message1");
        Record record2 = new Record("2015-11-17 09-48-58 !!!!! source2 some message2");
        Record record3 = new Record("2015-11-17 09-48-58 !!!!! source3 some message3");
        Record record4 = new Record("2015-11-17 09-48-58 !!!!! source2 some message4");

        Journal testJournal = new CollectionJournal();
        testJournal.add(record1);
        testJournal.add(record2);
        testJournal.add(record3);
        testJournal.add(record4);

        Journal expectedJournal = new CollectionJournal();
        expectedJournal.add(record2);
        expectedJournal.add(record4);

        testJournal = testJournal.filter(searchedString);

        Assert.assertTrue(expectedJournal.equals(testJournal));

    }

    @Test
    public void testFilterByDate() {
        String searchedString = "source2";
        Record record1 = new Record("2015-11-17 09-48-58 !!!!! source1 some message1");
        Record record2 = new Record("2017-11-17 09-48-58 !!!!! source2 some message2");
        Record record3 = new Record("2014-11-17 09-48-58 !!!!! source3 some message3");
        Record record4 = new Record("2012-11-17 09-48-58 !!!!! source2 some message4");

        Journal testJournal = new CollectionJournal();
        testJournal.add(record1);
        testJournal.add(record2);
        testJournal.add(record3);
        testJournal.add(record4);

        Journal expectedJournal = new CollectionJournal();
        expectedJournal.add(record1);
        expectedJournal.add(record3);

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Record.DATE_TIME_PATTERN);
        DateTime from = formatter.parseDateTime("2013-11-17 09-48-58");
        DateTime to = formatter.parseDateTime("2016-11-17 09-48-58");

        testJournal = testJournal.filter(from, to);

        Assert.assertTrue(expectedJournal.equals(testJournal));

    }

    @Test
    public void sortByImportanceDate() {
        Record record1 = new Record("2015-11-17 09-48-58 !!!!! source1 some message1");
        Record record2 = new Record("2017-11-17 09-48-58 !!!   source2 some message2");
        Record record3 = new Record("2014-11-17 09-48-58 !!!   source3 some message3");
        Record record4 = new Record("2012-11-17 09-48-58 !!!!! source2 some message4");

        Journal testJournal = new CollectionJournal();
        testJournal.add(record1);
        testJournal.add(record2);
        testJournal.add(record3);
        testJournal.add(record4);

        Journal expectedJournal = new CollectionJournal();
        expectedJournal.add(record3);
        expectedJournal.add(record2);
        expectedJournal.add(record4);
        expectedJournal.add(record1);

        testJournal.sortByImportanceDate();

        Assert.assertTrue(expectedJournal.equals(testJournal));
    }

    @Test
    public void sortByImportanceSourceDate() {
        Record record1 = new Record("2015-11-17 09-48-58 !!!!! source1 some message1");
        Record record2 = new Record("2017-11-17 09-48-58 !!!   source2 some message2");
        Record record3 = new Record("2014-11-17 09-48-58 !!!   source3 some message3");
        Record record4 = new Record("2012-11-17 09-48-58 !!!!! source2 some message4");
        Record record5 = new Record("2013-11-17 09-48-58 !!!!! source4 some message2");
        Record record6 = new Record("2018-11-17 09-48-58 !!!   source3 some message3");

        Journal testJournal = new CollectionJournal();
        testJournal.add(record1);
        testJournal.add(record2);
        testJournal.add(record3);
        testJournal.add(record4);
        testJournal.add(record5);
        testJournal.add(record6);

        Journal expectedJournal = new CollectionJournal();
        expectedJournal.add(record2);
        expectedJournal.add(record3);
        expectedJournal.add(record6);
        expectedJournal.add(record1);
        expectedJournal.add(record4);
        expectedJournal.add(record5);

        testJournal.sortByImportanceSourceDate();

        Assert.assertTrue(expectedJournal.equals(testJournal));
    }
}
