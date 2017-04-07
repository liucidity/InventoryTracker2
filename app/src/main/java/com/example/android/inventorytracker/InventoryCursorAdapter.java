package com.example.android.inventorytracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventorytracker.R;

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
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemTextView = (TextView) view.findViewById(R.id.item_name);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        String item = cursor.getString(cursor.getColumnIndexOrThrow("itemName"));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("itemQuantity"));
        int price = cursor.getInt(cursor.getColumnIndexOrThrow("itemPrice"));

        itemTextView.setText(item);
        quantityTextView.setText(String.valueOf(quantity));
        priceTextView.setText(String.valueOf(price));

    }
}
