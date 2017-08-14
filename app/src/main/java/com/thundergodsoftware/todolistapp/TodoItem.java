package com.thundergodsoftware.todolistapp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Marcus on 1/3/2017.
 */

public class TodoItem {

    // Event list view columns
    public static final String EVENTLIST_NAME_COLUMN="Name";
    public static final String EVENTLIST_DATE_COLUMN="Date";

    public enum Recurrence {
        ONCE,
        REPEATED;
    }

    public long id;
    public long categoryId;
    public String name = "";
    public Recurrence recurrence = Recurrence.ONCE;
    public Date lastOccurrence = new Date();
    public Date nextOccurrence = new Date();
    public int recurrenceDays = 0;
    public int recurrenceMonths = 0;

    public TodoItem(long id, long categoryId, String name, Recurrence recurrence, int recurrenceDays, int recurrenceMonths, Date nextOccurrence, Date lastOccurrence) {
        this.id = id;
        this.name = name;
        this.recurrence = recurrence;
        this.recurrenceDays = recurrenceDays;
        this.recurrenceMonths = recurrenceMonths;
        this.nextOccurrence = nextOccurrence;
        this.lastOccurrence = nextOccurrence;
    }

    public TodoItem(long categoryId, String name, Recurrence recurrence, int recurrenceDays, int recurrenceMonths) {
        this.id = -1;
        this.categoryId = categoryId;
        this.name = name;
        this.recurrence = recurrence;
        this.recurrenceDays = recurrenceDays;
        this.recurrenceMonths = recurrenceMonths;

        nextOccurrence = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if ( recurrenceDays != 0 ) {
            c.add(Calendar.DATE,recurrenceDays);

        } else if ( recurrenceMonths != 0 )
        {
            c.add(Calendar.MONTH,recurrenceMonths);
        }
        nextOccurrence = c.getTime();
        lastOccurrence = null;
    }

    public TodoItem(long categoryId, String name, Date occurrence) {
        this.id = -1;
        this.categoryId = categoryId;
        this.name = name;
        this.recurrence = Recurrence.ONCE;
        nextOccurrence = occurrence;
        lastOccurrence = null;
    }

    public TodoItem(String name) {
        this.id = -1;
        this.categoryId = -1;
        this.name = name;
        this.recurrence = Recurrence.ONCE;
        nextOccurrence = new Date();
        lastOccurrence = null;
    }
}
