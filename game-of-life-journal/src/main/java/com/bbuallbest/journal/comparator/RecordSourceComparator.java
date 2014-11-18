package com.bbuallbest.journal.comparator;

import com.bbuallbest.journal.Record;

import java.util.Comparator;

/**
 * Created by happy on 18/11/2014.
 */
public class RecordSourceComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return o1.getSource().compareTo(o2.getSource());
    }
}
