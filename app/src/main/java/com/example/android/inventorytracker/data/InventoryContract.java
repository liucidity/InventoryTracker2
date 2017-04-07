package com.example.android.inventorytracker.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by TRAVIS on 2017-03-28.
 */

public class InventoryContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.inventorytracker";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +CONTENT_AUTHORITY);

    public static final String PATH_INVENTORY = "inventory";

    public static final class InventoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INVENTORY).build();


        //MIME type that specifies if URI returns a list or single item
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + CONTENT_URI + "/" +PATH_INVENTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + CONTENT_URI +"/" +PATH_INVENTORY;


        //Define database schema
        public static final String TABLE_NAME = "inventoryTable";
        public static final String COLUMN_ITEM_NAME = "itemName";
        public static final String COLUMN_QUANTITY = "itemQuantity";
        public static final String COLUMN_PRICE = "itemPrice";

        //builds URI to find specific inventory item by its identifier
        public static Uri buildItemUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
}
