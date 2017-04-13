package com.example.android.inventorytracker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.R.attr.defaultHeight;
import static android.R.attr.id;
import static android.content.ContentValues.TAG;

/**
 * Created by TRAVIS on 2017-04-01.
 */

public class InventoryProvider extends ContentProvider{
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();
    private static final int ITEM = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private InventoryDatabaseHelper mOpenHelper;




    @Override
    public boolean onCreate() {
        mOpenHelper = new InventoryDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case ITEM:
                cursor = db.query(
                        InventoryContract.InventoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                break;
            case ITEM_ID:
                long _id = ContentUris.parseId(uri);
                cursor = db.query(
                        InventoryContract.InventoryEntry.TABLE_NAME,
                        projection,
                        InventoryContract.InventoryEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        null
                );
                break;
            default:
                throw new UnsupportedOperationException("ph uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case ITEM:
                return InventoryContract.InventoryEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return InventoryContract.InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);

        }
    }

    @Override
    public Uri insert( Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case ITEM:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertItem(Uri uri, ContentValues values){
        String name = values.getAsString(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME);
        if(name == null){
            throw new IllegalArgumentException("Item requires a name");
        }
        Integer price = values.getAsInteger(InventoryContract.InventoryEntry.COLUMN_PRICE);
        if(price == null || price <=0){
            throw new IllegalArgumentException("Item requires a price");
        }
        Integer quantity = values.getAsInteger(InventoryContract.InventoryEntry.COLUMN_QUANTITY);
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Item Requires a quantity");
        }
        String supplierEmail = values.getAsString(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL);
        if (supplierEmail == null){
            throw new IllegalArgumentException("Item requires a supplier email");
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long id = db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows;

        switch (sUriMatcher.match(uri)){
            case ITEM:
                rows = db.delete(InventoryContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rows = db.delete(InventoryContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for" + uri);
        }
        if (rows != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows;

        switch (sUriMatcher.match(uri)){
            case ITEM:
                rows = db.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rows = db.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("ph uri: " + uri);
        }
        if(rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    public static UriMatcher buildUriMatcher(){
        String content = InventoryContract.CONTENT_AUTHORITY;
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content,InventoryContract.PATH_INVENTORY, ITEM);
        matcher.addURI(content,InventoryContract.PATH_INVENTORY +"/#", ITEM_ID);
        return matcher;
    }
}
