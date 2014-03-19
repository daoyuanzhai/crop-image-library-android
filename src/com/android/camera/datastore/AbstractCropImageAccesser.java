package com.android.camera.datastore;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractCropImageAccesser {
    protected SQLiteDatabase database;
    protected SQLiteOpenHelper dbHelper;

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
