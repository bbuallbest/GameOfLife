package com.bbuallbest.journal.comparator;

import com.bbuallbest.journal.Record;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by happy on 18/11/2014.
 */
public class JournalComparatorContainer implements Comparator<Record> {

    private List<Comparator<Record>> comparatorList = new ArrayList<Comparator<Record>>();

    @Override
    public int compare(Record o1, Record o2) {
        if (comparatorList.size() == 0)
            return 0;
        int comparatorLevel = 0;
        int result;
        do {
            result = comparatorList.get(comparatorLevel++).compare(o1, o2);
        } while (result == 0 && comparatorLevel < comparatorList.size());
        return result;
    }

    public void addNextLevelComparator(Comparator<Record> comparator) {
        comparatorList.add(comparator);
    }
}
