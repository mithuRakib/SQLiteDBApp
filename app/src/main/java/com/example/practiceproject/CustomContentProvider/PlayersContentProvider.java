package com.example.practiceproject.CustomContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.practiceproject.sqlitedb.PlayersDBContract;
import com.example.practiceproject.sqlitedb.PlayersDBOpenHelper;

public class PlayersContentProvider extends ContentProvider {
    private PlayersDBOpenHelper dbOpenHelper;

    public static final int PLAYERS_DIRECTORY = 100;
    public static final int PLAYER_WITH_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PlayersDBContract.AUTHORITY, PlayersDBContract.PATH, PLAYERS_DIRECTORY);
        uriMatcher.addURI(PlayersDBContract.AUTHORITY, PlayersDBContract.PATH + "/#", PLAYER_WITH_ID);
        return uriMatcher;
    }

    public PlayersContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Context context = getContext();
        dbOpenHelper = new PlayersDBOpenHelper(context);
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        final SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case (PLAYERS_DIRECTORY):
                long id = database.insert(PlayersDBContract.PlayersEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(PlayersDBContract.PlayersEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row of data into " +uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        final SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        int uriMatch = uriMatcher.match(uri);
        Cursor retCursor;
        switch (uriMatch) {
            case (PLAYERS_DIRECTORY):
                retCursor = database.query(PlayersDBContract.PlayersEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case (PLAYER_WITH_ID):
                String id = uri.getPathSegments().get(1);
                String mSelection = PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER+"=?";
                String[] mSelectionArgs = new String[]{id, };
                retCursor = database.query(PlayersDBContract.PlayersEntry.TABLE_NAME, projection, mSelection, mSelectionArgs,
                        null, null, PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int playerDataUpdated;
        int match = uriMatcher.match(uri);

        switch (match) {
            case PLAYER_WITH_ID:
                String id = uri.getPathSegments().get(1);
                playerDataUpdated = dbOpenHelper.getWritableDatabase().update(PlayersDBContract.PlayersEntry.TABLE_NAME,
                        values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
        if (playerDataUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return playerDataUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int playersDeleted;
        int match = uriMatcher.match(uri);
        if (selection == null) selection = "1";
        switch (match) {
            case PLAYER_WITH_ID:
                playersDeleted = dbOpenHelper.getWritableDatabase().delete(
                        PlayersDBContract.PlayersEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if (playersDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return playersDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        int match = uriMatcher.match(uri);

        switch (match) {
            case PLAYERS_DIRECTORY:
                // directory
                return "vnd.android.cursor.dir" + "/" + PlayersDBContract.AUTHORITY + "/" + PlayersDBContract.PlayersEntry.CONTENT_URI;
            case PLAYER_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + PlayersDBContract.AUTHORITY + "/" + PlayersDBContract.PlayersEntry.CONTENT_URI;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

}
