package com.thundergodsoftware.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Marcus on 1/14/2017.
 */

public class DataSourceCategory {

    // Categories Table
    private static final String TABLE_NAME = "categories";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    private static DatabaseHandler dbHelper;
    private static String[] allColumns =  { KEY_ID, KEY_NAME } ;

    public DataSourceCategory(Context context ) {
        dbHelper = new DatabaseHandler(context);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" ( " +
                KEY_ID + " LONG PRIMARY KEY, " +
                KEY_NAME + " STRING " +
                ")";
    }

    public long createCategory( Category category) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = ConvertCategoryToContentValues(category);

        long newId = db.insert(TABLE_NAME, null, values);
        category.id = newId;

        db.close();

        return newId;
    }

    public Category getCategory( long categoryId )  {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                                 allColumns,
                                 KEY_ID + "=?",
                                 new String[] { String.valueOf(categoryId) },
                                 null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category newCategory = ConvertCursorToCategory(cursor);

        db.close();

        return newCategory;
    }

    public long getCategoryId( String name )  {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                allColumns,
                KEY_NAME + "=?",
                new String[] { name },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category newCategory = ConvertCursorToCategory(cursor);

        db.close();

        return newCategory.id;
    }


    public ArrayList<Category> getAllCategories() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Category> categories = new ArrayList<Category>();

        try {

            Cursor cursor = db.query( TABLE_NAME,
                    allColumns,
                    null, null, null, null, null );

            if ( cursor.moveToFirst()) {
                do {
                    categories.add( ConvertCursorToCategory(cursor) );
                } while (cursor.moveToNext());
            }
        }
        catch ( Exception ex ) {
            String s = ex.getMessage();
        }

        db.close();

        return categories;
    }

    private Category ConvertCursorToCategory( Cursor cursor ) {
        long id = Long.parseLong(cursor.getString(0));
        String name  = cursor.getString(1);

        Category newCategory = new Category( id, name );

        return newCategory;
    }

    public ContentValues ConvertCategoryToContentValues( Category category ) {
        ContentValues values = new ContentValues();

        values.put(KEY_ID, category.id );
        values.put(KEY_NAME, category.name);

        return values;
    }

    public void saveCategory( Category category ) {
        if ( category.id == -1 ) {
            createCategory(category );
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // update record
        ContentValues values = ConvertCategoryToContentValues(category);

        String selection = KEY_ID + " = ? ";
        String[] selectionArgs ={ Long.toString(category.id) };

        db.update( TABLE_NAME, values, selection, selectionArgs );

        db.close();
    }

    public void deleteCategory( Category category ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = KEY_ID + " = ? ";
        String[] selectionArgs ={ Long.toString(category.id) };
        db.delete( TABLE_NAME, selection, selectionArgs );

        db.close();
    }


    public int getCategoryCount() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String countQuery = "SELECT " + KEY_ID + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }
}
