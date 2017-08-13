package com.thundergodsoftware.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.thundergodsoftware.todolistapp.TodoItem;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Marcus on 1/14/2017.
 */

public class DataSourceTodoItem {

    // Courses Table
    private static final String TABLE_NAME = "todoItems";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RECURRENCE = "recurrence";
    private static final String KEY_RECURRENCE_DAYS= "recurrence_days";
    private static final String KEY_RECURRENCE_MONTHS= "recurrence_months";
    private static final String KEY_NEXT_OCCURRENCE = "nextOccurence";
    private static final String KEY_LAST_OCCURRENCE = "lastOccurrence";

    private static DatabaseHandler dbHelper;
    private static String[] allColumns =  { KEY_ID, KEY_NAME,
                                            KEY_RECURRENCE, KEY_RECURRENCE_DAYS, KEY_RECURRENCE_MONTHS,
                                            KEY_NEXT_OCCURRENCE, KEY_LAST_OCCURRENCE };

    public DataSourceTodoItem(Context context ) {
        dbHelper = new DatabaseHandler(context);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" ( " +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " STRING, " +
                KEY_RECURRENCE + " STRING, " +
                KEY_RECURRENCE_DAYS + " INTEGER, " +
                KEY_RECURRENCE_MONTHS + " INTEGER, " +
                KEY_NEXT_OCCURRENCE + " STRING, " +
                KEY_LAST_OCCURRENCE + " STRING " +
                ")";
    }

    public long createTodoItem( TodoItem todoItem) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = ConvertTodoItemToContentValues(todoItem);

        long newId = db.insert(TABLE_NAME, null, values);
        todoItem.id = newId;

        db.close();

        return newId;
    }

    public TodoItem getTodoItem( int todoItemId )  {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                                 allColumns,
                                 KEY_ID + "=?",
                                 new String[] { String.valueOf(todoItemId) },
                                 null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TodoItem newTodoItem = ConvertCursorToTodoItem(cursor);

        db.close();

        return newTodoItem;
    }

    public ArrayList<TodoItem> getAllTodoItems() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();

        try {

            Cursor cursor = db.query( TABLE_NAME,
                    allColumns,
                    null, null, null, null, null );

            if ( cursor.moveToFirst()) {
                do {
                    todoItems.add( ConvertCursorToTodoItem(cursor) );
                } while (cursor.moveToNext());
            }
        }
        catch ( Exception ex ) {
            String s = ex.getMessage();
        }

        db.close();

        return todoItems;
    }

    private TodoItem ConvertCursorToTodoItem( Cursor cursor ) {
        int id = Integer.parseInt(cursor.getString(0));
        String name  = cursor.getString(1);
        TodoItem.Recurrence recurrence = TodoItem.Recurrence.valueOf(cursor.getString(2));
        int recurrenceDays = Integer.parseInt(cursor.getString(3));
        int recurrenceMonths = Integer.parseInt(cursor.getString(4));
        int dateInMinutes = Integer.parseInt(cursor.getString(5));
        Date nextOccurrence = Helpers.MinutesToDate(dateInMinutes);
        Date lastOccurrence = Helpers.MinutesToDate(dateInMinutes);

        TodoItem newTodoItem = new TodoItem( id,
                name,
                recurrence,
                recurrenceDays,
                recurrenceMonths,
                nextOccurrence,
                lastOccurrence);

        return newTodoItem;
    }

    public ContentValues ConvertTodoItemToContentValues( TodoItem todoItem ) {
        ContentValues values = new ContentValues();

        values.put(KEY_ID, todoItem.id );
        values.put(KEY_NAME, todoItem.name);
        values.put(KEY_RECURRENCE, todoItem.recurrence.name() );
        values.put(KEY_RECURRENCE_DAYS, todoItem.recurrenceDays);
        values.put(KEY_RECURRENCE_MONTHS, todoItem.recurrenceMonths);
        values.put(KEY_NEXT_OCCURRENCE, Helpers.DateToMinutes(todoItem.nextOccurrence));
        values.put(KEY_LAST_OCCURRENCE, Helpers.DateToMinutes(todoItem.lastOccurrence));

        return values;
    }

    public void saveCourse( TodoItem todoItem ) {
        if ( todoItem.id == -1 ) {
            createTodoItem(todoItem );
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // update record
        ContentValues values = ConvertTodoItemToContentValues(todoItem);

        String selection = KEY_ID + " = ? ";
        String[] selectionArgs ={ Long.toString(todoItem.id) };

        db.update( TABLE_NAME, values, selection, selectionArgs );

        db.close();
    }

    public void deleteCourse( TodoItem todoItem ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = KEY_ID + " = ? ";
        String[] selectionArgs ={ Long.toString(todoItem.id) };
        db.delete( TABLE_NAME, selection, selectionArgs );

        db.close();
    }


    public int getTodoItemCount() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String countQuery = "SELECT " + KEY_ID + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }
}
