package com.bbuallbest.journal;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by happy on 17/11/2014.
 */
public class ArrayJournal implements Journal{

    private static final int INITIAL_CAPACITY = 15;

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
    public Journal filter(String string) {
        return null;
    }

    @Override
    public Journal filter(DateTime fromDate, DateTime toDate) {
        return null;
    }

    @Override
    public void sortByDate() {

    }

    @Override
    public void sortByImportanceDate() {

    }

    @Override
    public void sortByImportanceSourceDate() {

    }

    @Override
    public void sortBySourceDate() {

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
}
