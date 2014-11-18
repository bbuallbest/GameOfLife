package com.bbuallbest.journal;

import com.bbuallbest.journal.comparator.JournalComparatorContainer;
import com.bbuallbest.journal.comparator.RecordDateComparator;
import com.bbuallbest.journal.comparator.RecordImportanceComparator;
import com.bbuallbest.journal.comparator.RecordSourceComparator;
import org.joda.time.DateTime;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by happy on 18/11/2014.
 */
public class CollectionJournal implements Journal{

    private Comparator<Record> dateComparator = new RecordDateComparator();
    private Comparator<Record> importanceComparator = new RecordImportanceComparator();
    private Comparator<Record> sourceComparator = new RecordSourceComparator();
    private List<Record> recordlList;

    public CollectionJournal() {
        recordlList = new ArrayList<Record>();
    }

    public CollectionJournal(Journal journal) {
        this.recordlList = new ArrayList<Record>(journal.size());
        addAllRecordsFromJournal(journal);
    }

    @Override
    public void add(Record record) {
        recordlList.add(record);
    }

    @Override
    public void add(Journal journal) {
        addAllRecordsFromJournal(journal);
    }

    @Override
    public void remove(Record record) {
        recordlList.remove(record);
    }

    @Override
    public Record get(int index) {
        return recordlList.get(index);
    }

    @Override
    public void set(int index, Record record) {
        recordlList.set(index, record);
    }

    @Override
    public void insert(int index, Record record) {
        List<Record> tail = recordlList.subList(index, recordlList.size());
        recordlList.set(index, record);
        for (int i = 0, j = index + 1; i < tail.size(); i++, j++) {
            recordlList.set(j, tail.get(i));  
        }
    }

    @Override
    public void remove(int index) {
        recordlList.remove(index);
    }

    @Override
    public void remove(int fromIndex, int toIndex) {
        for (int i = fromIndex, j = toIndex; j < recordlList.size(); i++, j++) {
            recordlList.set(i, recordlList.get(j));
        }
        for (int i = recordlList.size() - 1; i >= toIndex; i--) {
            recordlList.remove(i);
        }
    }

    @Override
    public void removeAll() {
        for (int i = recordlList.size() - 1; i >= 0 ; i--) {
            recordlList.remove(i);
        }
    }

    @Override
    public int size() {
        return recordlList.size();
    }

    @Override
    public Journal filter(String regex) {
        Journal filteredJournal = new CollectionJournal();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        Record record;
        for (int i = 0; i < recordlList.size(); i++) {
            record = recordlList.get(i);
            matcher = pattern.matcher(record.toString());
            if (matcher.find())
                filteredJournal.add(record);
        }
        return filteredJournal;
    }

    @Override
    public Journal filter(DateTime fromDate, DateTime toDate) {
        Journal filteredJournal = new CollectionJournal();
        Record record;
        for (int i = 0; i < recordlList.size(); i++) {
            record = recordlList.get(i);
            if (isDateInRange(record.getTime(), fromDate, toDate))
                filteredJournal.add(record);
        }
        return filteredJournal;
    }

    @Override
    public void sortByDate() {
        Collections.sort(recordlList, dateComparator);
    }

    @Override
    public void sortByImportanceDate() {
        JournalComparatorContainer comparatorContainer = new JournalComparatorContainer();
        comparatorContainer.addNextLevelComparator(importanceComparator);
        comparatorContainer.addNextLevelComparator(dateComparator);
        Collections.sort(recordlList, comparatorContainer);
    }

    @Override
    public void sortByImportanceSourceDate() {
        JournalComparatorContainer comparatorContainer = new JournalComparatorContainer();
        comparatorContainer.addNextLevelComparator(importanceComparator);
        comparatorContainer.addNextLevelComparator(sourceComparator);
        comparatorContainer.addNextLevelComparator(dateComparator);
        Collections.sort(recordlList, comparatorContainer);
    }

    @Override
    public void sortBySourceDate() {
        JournalComparatorContainer comparatorContainer = new JournalComparatorContainer();
        comparatorContainer.addNextLevelComparator(sourceComparator);
        comparatorContainer.addNextLevelComparator(dateComparator);
        Collections.sort(recordlList, comparatorContainer);
    }

    @Override
    public void printRecords() {
        for (int i = 0; i < recordlList.size(); i++) {
            System.out.println(recordlList.get(i));
        }
    }
    
    private void addAllRecordsFromJournal(Journal journal) {
        for (int i = 0; i < journal.size(); i++)
            recordlList.add(journal.get(i));
    }

    private boolean isDateInRange(DateTime date, DateTime left, DateTime right) {
        if(left.compareTo(date) <= 0 && right.compareTo(date) >= 0)
            return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionJournal that = (CollectionJournal) o;

        if (!recordlList.equals(that.recordlList)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return recordlList.hashCode();
    }
}
