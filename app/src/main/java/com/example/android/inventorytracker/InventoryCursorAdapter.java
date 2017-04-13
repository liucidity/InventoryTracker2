package com.example.android.inventorytracker;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventorytracker.data.InventoryContract;

import java.sql.Blob;

import static android.content.ContentValues.TAG;

/**
 * Created by TRAVIS on 2017-03-28.
 */

public class InventoryCursorAdapter extends CursorAdapter{

    public InventoryCursorAdapter(Context context, Cursor cursor){
        super(context, cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        final TextView itemTextView = (TextView) view.findViewById(R.id.item_name);
        TextView quantityTextView = (TextView) view.findViewById(R.id.edit_quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.edit_price);
        ImageView imageView = (ImageView) view.findViewById(R.id.list_image_view);
        Button itemSoldButton = (Button) view.findViewById(R.id.list_item_btn);

        final String id = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry._ID));
        final String item = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME));
        final int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_QUANTITY));
        final int price = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_PRICE));

        itemTextView.setText(item);
        quantityTextView.setText(String.valueOf(quantity));
        priceTextView.setText(String.valueOf(price));


        //sell button logic
        itemSoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity == 0){
                    Log.d(TAG, "onClick: "+ item);
                    Toast.makeText(context,R.string.toast_sold_out, Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    int quantityAfter = quantity -1;
                    Toast.makeText(context, R.string.toast_item_sold, Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();

                    values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantityAfter);

                    Uri uri = Uri.withAppendedPath(InventoryContract.InventoryEntry.CONTENT_URI, id);
                    context.getContentResolver().update(uri, values, null, null);
                }
            }
        });



    }
}
