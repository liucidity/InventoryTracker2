package com.example.android.inventorytracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TRAVIS on 2017-03-28.
 */

public class InventoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventoryDatabase.db";
    private static final int DATABASE_VERSION = 1;


    public InventoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addInventoryTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private void addInventoryTable(SQLiteDatabase db){
        db.execSQL(
                "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME + " (" +
                        InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        InventoryContract.InventoryEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                        InventoryContract.InventoryEntry.COLUMN_QUANTITY +" INTEGER DEFAULT 0, " +
                        InventoryContract.InventoryEntry.COLUMN_PRICE +" INTEGER, " +
                        InventoryContract.InventoryEntry.COLUMN_IMAGE + " BLOB, " +
                        InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL + " TEXT);"


        );
    }
}
