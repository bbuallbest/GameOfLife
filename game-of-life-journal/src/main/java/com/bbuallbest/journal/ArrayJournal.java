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
 * Created by happy on 17/11/2014.
 */
public class ArrayJournal implements Journal{

    private static final int INITIAL_CAPACITY = 15;

    private Comparator<Record> dateComparator = new RecordDateComparator();
    private Comparator<Record> importanceComparator = new RecordImportanceComparator();
    private Comparator<Record> sourceComparator = new RecordSourceComparator();
    private Record[] records;
    private int size = 0;

    public ArrayJournal() {
        records = new Record[INITIAL_CAPACITY];
    }

    public ArrayJournal(Record[] records) {
        this.records = new Record[records.length];
        size = records.length;
        System.arraycopy(records, 0, this.records, 0, size);
    }

    @Override
    public void add(Record record) {
        if(isRecordsFull())
            increaseRecordsSize(generateNewSize(records.length));
        records[size++] = record;
    }

    @Override
    public void add(Journal journal) {
        if(size + journal.size() > records.length)
            increaseRecordsSize(generateNewSize(size + journal.size()));
        System.arraycopy(journal, 0, records, size, journal.size());
        size+= journal.size();
    }

    @Override
    public void remove(Record record) {
        int index = brutSearch(record);
        if (index >= 0) {
            System.arraycopy(records, index + 1, records, index, records.length - index - 1);
            size--;
        }
    }

    @Override
    public Record get(int index) {
        validateIngexValue(index);
        return records[index];
    }

    @Override
    public void set(int index, Record record) {
        validateIngexValue(index);
        records[index] = record;
    }

    @Override
    public void insert(int index, Record record) {
        validateIngexValue(index);
        if(size + 1 > records.length)
            increaseRecordsSize(generateNewSize(size + 1));
        Record[] temp = new Record[size - index];
        System.arraycopy(records, index, temp, 0, temp.length);
        set(index, record);
        System.arraycopy(temp, 0, records, index + 1, temp.length);
        size++;
    }

    @Override
    public void remove(int index) {
        validateIngexValue(index);
        System.arraycopy(records, index + 1, records, index, records.length - index - 1);
        size--;
    }

    @Override
    public void remove(int fromIndex, int toIndex) {
        validateIngexValue(fromIndex);
        validateIngexValue(toIndex);
        System.arraycopy(records, toIndex, records, fromIndex, size - toIndex);
        size-= toIndex - fromIndex;
        setNull(size, records.length);
    }

    @Override
    public void removeAll() {
        size = 0;
        setNull(size, records.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Journal filter(String regex) {
        Journal filteredJournal = new ArrayJournal();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        Record record;
        for (int i = 0; i < size; i++) {
            record = records[i];
            matcher = pattern.matcher(record.toString());
            if (matcher.find())
                filteredJournal.add(record);
        }
        return filteredJournal;
    }

    @Override
    public Journal filter(DateTime fromDate, DateTime toDate) {
        Journal filteredJournal = new ArrayJournal();
        Record record;
        for (int i = 0; i < size; i++) {
            record = records[i];
            if (isDateInRange(record.getTime(), fromDate, toDate))
                filteredJournal.add(record);
        }
        return filteredJournal;
    }

    @Override
    public void sortByDate() {
        Collections.sort(Arrays.asList(records), dateComparator);
    }

    @Override
    public void sortByImportanceDate() {
        JournalComparatorContainer comparatorContainer = new JournalComparatorContainer();
        comparatorContainer.addNextLevelComparator(importanceComparator);
        comparatorContainer.addNextLevelComparator(dateComparator);
        Collections.sort(Arrays.asList(records), comparatorContainer);
    }

    @Override
    public void sortByImportanceSourceDate() {
        JournalComparatorContainer comparatorContainer = new JournalComparatorContainer();
        comparatorContainer.addNextLevelComparator(importanceComparator);
        comparatorContainer.addNextLevelComparator(sourceComparator);
        comparatorContainer.addNextLevelComparator(dateComparator);
        List<Record> temp = recordsArrayToList();
        Collections.sort(temp, comparatorContainer);
        temp.toString();
        records = toArray(temp);
    }

    @Override
    public void sortBySourceDate() {
        JournalComparatorContainer comparatorContainer = new JournalComparatorContainer();
        comparatorContainer.addNextLevelComparator(sourceComparator);
        comparatorContainer.addNextLevelComparator(dateComparator);
        Collections.sort(Arrays.asList(records), comparatorContainer);
    }

    @Override
    public void printRecords() {
        for (int i = 0; i < size; i++) {
            System.out.println(records[i]);
        }
    }

    private boolean isRecordsFull() {
        return size == records.length;
    }

    private void increaseRecordsSize(int newSize) {
        records = Arrays.copyOf(records, newSize);
    }

    private int defaultIncreaseSizeStep() {
        return records.length * 3 / 2;
    }

    private int generateNewSize(int oldSize) {
        return oldSize * 3 / 2;
    }

    private int brutSearch(Record record) {
        for (int i = 0; i < size; i++) {
            if (records[i].equals(record))
                return i;
        }
        return -1;
    }

    private void validateIngexValue(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException("Index = " + index + ".");
    }

    public Record[] getRecords() {
        return records;
    }

    private void setNull(int fromPosition, int toPosition) {
        for (int i = fromPosition; i < toPosition; i++) {
            records[i] = null;
        }
    }

    private boolean isDateInRange(DateTime date, DateTime left, DateTime right) {
        if(left.compareTo(date) <= 0 && right.compareTo(date) >= 0)
            return true;
        return false;
    }

    private List<Record> recordsArrayToList() {
        List<Record> recordList = new ArrayList<Record>();
        for (int i = 0; i < size; i++) {
            recordList.add(records[i]);
        }
        return recordList;
    }

    private Record[] toArray(List<Record> recordList) {
        Record[] records = new Record[recordList.size()];
        for (int i = 0; i < records.length; i++) {
            records[i] = recordList.get(i);
        }
        return records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayJournal journal = (ArrayJournal) o;

        if (size != journal.size) return false;
        if (!Arrays.equals(records, journal.records)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(records);
        result = 31 * result + size;
        return result;
    }
}
