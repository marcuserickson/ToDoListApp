package com.thundergodsoftware.todolistapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marcus on 1/14/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "ThunderTodoList";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DataSourceTodoItem.getCreateTableSql());
        }
        catch ( SQLException ex ) {
            String s = ex.getMessage();
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DataSourceTodoItem.getTableName());

        // Create tables again
        onCreate(db);
    }

    public static void deleteDatabase(Context context ) {
        context.deleteDatabase(DATABASE_NAME);
    }
}