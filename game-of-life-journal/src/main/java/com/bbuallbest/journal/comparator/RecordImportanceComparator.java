package com.bbuallbest.journal.comparator;

import com.bbuallbest.journal.Record;

import java.util.Comparator;

/**
 * Created by happy on 18/11/2014.
 */
public class RecordImportanceComparator implements Comparator<Record> {
    @Override
    public int compare(Record o1, Record o2) {
        int importance1 = o1.getImportance().getValue();
        int importance2 = o2.getImportance().getValue();
        return importance1 - importance2;
    }
}
