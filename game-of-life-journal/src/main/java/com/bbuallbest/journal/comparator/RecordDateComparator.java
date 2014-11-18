package com.bbuallbest.journal.comparator;

import com.bbuallbest.journal.Record;
import org.joda.time.format.DateTimeFormat;

import java.util.Comparator;

/**
 * Created by happy on 18/11/2014.
 */
public class RecordDateComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        String dateTime1 = DateTimeFormat.forPattern(Record.DATE_TIME_PATTERN).print(o1.getTime());
        String dateTime2 = DateTimeFormat.forPattern(Record.DATE_TIME_PATTERN).print(o2.getTime());
        return dateTime1.compareTo(dateTime2);
    }
}
