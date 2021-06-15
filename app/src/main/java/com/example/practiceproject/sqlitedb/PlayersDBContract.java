package com.example.practiceproject.sqlitedb;

import android.net.Uri;
import android.provider.BaseColumns;

public class PlayersDBContract {

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.practiceproject";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static final String PATH = PlayersEntry.TABLE_NAME;



    private PlayersDBContract() { }

    public static final class PlayersEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME = "playersTable";
        public static final String COLUMN_IMAGE_URL = "imageUrl";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_JERSEY_NUMBER = "jerseyNumber";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WEIGHT = "weight";
    }
}
