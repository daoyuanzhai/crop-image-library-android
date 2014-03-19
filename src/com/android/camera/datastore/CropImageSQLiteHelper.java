package com.android.camera.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CropImageSQLiteHelper extends SQLiteOpenHelper {
	private static final String TAG = CropImageSQLiteHelper.class.getSimpleName();

	private static final String DATABASE = "innercircle_images";
	private static final int DATABASE_VERSION = 1;

	public static final String DATABASE_PROFILE_TABLE = "profile_pics";

	public static final String COLUMN_ID = "_id";
	public static final String UID = "uid";
	public static final String PICTURE_DATA = "data";
	
	private static final String DATABASE_PROFILE_CREATE = "create table " + DATABASE_PROFILE_TABLE + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + UID + " text not null unique, "
            + PICTURE_DATA + " text);";

	private static final String DATABASE_PROFILE_DROP = "DROP TABLE IF EXISTS " + DATABASE_PROFILE_TABLE;

	protected CropImageSQLiteHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_PROFILE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion +
                ", which will destroy all old data");
        db.execSQL(DATABASE_PROFILE_DROP);
        onCreate(db);
    }
}
