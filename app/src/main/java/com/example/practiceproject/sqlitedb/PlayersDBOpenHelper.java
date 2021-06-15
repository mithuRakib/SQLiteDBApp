package com.example.practiceproject.sqlitedb;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlayersDBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "playersDB";
    private static final int DB_VERSION = 1;

    public PlayersDBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DB = "CREATE TABLE " + PlayersDBContract.PlayersEntry.TABLE_NAME + "("+
                    PlayersDBContract.PlayersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PlayersDBContract.PlayersEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                    PlayersDBContract.PlayersEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER + " INTEGER NOT NULL UNIQUE, " +
                    PlayersDBContract.PlayersEntry.COLUMN_HEIGHT + " TEXT, " +
                    PlayersDBContract.PlayersEntry.COLUMN_WEIGHT + " TEXT" +
                ");";
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ PlayersDBContract.PlayersEntry.TABLE_NAME);
    }
}
