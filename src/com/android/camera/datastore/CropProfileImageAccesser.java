package com.android.camera.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class CropProfileImageAccesser extends AbstractCropImageAccesser {
    private final static String TAG = CropProfileImageAccesser.class.getSimpleName();

    private String[] allColumns = {CropImageSQLiteHelper.COLUMN_ID,
            CropImageSQLiteHelper.UID,
            CropImageSQLiteHelper.PICTURE_DATA};

    public CropProfileImageAccesser(final Context context) {
        this.dbHelper = new CropImageSQLiteHelper(context);
    }

    public byte[] getImageByUID(final String uid) {
        final String whereClause = CropImageSQLiteHelper.UID + " = ?";
        final String[] whereArgs = new String[] {uid};
        final String groupBy = null;
        final String having = null;
        final String order = null;

        final Cursor cursor = this.database.query(CropImageSQLiteHelper.DATABASE_PROFILE_TABLE, allColumns,
                whereClause, whereArgs, groupBy, having, order);

        final int dataIndex = cursor.getColumnIndex(CropImageSQLiteHelper.PICTURE_DATA);

        byte[] data = null;
        if (cursor.moveToNext()) {
            data = cursor.getBlob(dataIndex);
        }
        cursor.close();

        return data;
    }

    public void addImageByUID (final String uid, final byte[] byteData) {
        final ContentValues values = new ContentValues();
        values.put(CropImageSQLiteHelper.UID, uid);
        values.put(CropImageSQLiteHelper.PICTURE_DATA, byteData);

        this.database.beginTransaction();
        try {
            this.deleteImageByUID(uid);
            this.database.insert(CropImageSQLiteHelper.DATABASE_PROFILE_TABLE, null, values);
            this.database.setTransactionSuccessful();
            Log.v(TAG, "image added for uid: " + uid);
        } catch (Exception e) {
            Log.e(TAG, "error deleting (if exists) and then inserting image: " + e.toString());
        } finally {
            this.database.endTransaction();
        }
    }

    private void deleteImageByUID(final String uid) {
        final String whereClause = CropImageSQLiteHelper.UID + " = ?";
        final String[] whereArgs = new String[] {uid};

        final int result = this.database.delete(CropImageSQLiteHelper.DATABASE_PROFILE_TABLE, whereClause, whereArgs);
        Log.v(TAG, String.valueOf(result) + " image(s) deleted for uid: " + uid);
    }

    public void updateImageByUid (final String uid, final byte[] byteData) {
        final ContentValues values = new ContentValues();
        values.put(CropImageSQLiteHelper.PICTURE_DATA, byteData);

        final int result = this.database
                .update(CropImageSQLiteHelper.DATABASE_PROFILE_TABLE, values, CropImageSQLiteHelper.UID + " = ?", new String[]{uid});
        Log.v(TAG, String.valueOf(result) + " image(s) updated for uid: " + uid);
    }
}
