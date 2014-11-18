package com.bbuallbest.journal;

import org.joda.time.DateTime;

/**
 * Created by happy on 17/11/2014.
 */
public interface Journal {
    void add(Record record);
    void add(Journal journal);
    void remove(Record record);
    Record get(int index);
    void set(int index, Record record);
    void insert(int index, Record record);
    void remove(int index);
    void remove(int fromIndex, int toIndex);
    void removeAll();
    int size();
    Journal filter(String string);
    Journal filter(DateTime fromDate, DateTime toDate);
    void sortByDate();
    void sortByImportanceDate();
    void sortByImportanceSourceDate();
    void sortBySourceDate();
    void printRecords();
}
