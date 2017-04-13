package com.example.android.inventorytracker;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventorytracker.data.InventoryContract;


public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int INVENTORY_LOADER = 0;
    InventoryCursorAdapter mInventoryAdapter;
    String[] projection = {
            InventoryContract.InventoryEntry._ID,
            InventoryContract.InventoryEntry.COLUMN_ITEM_NAME,
            InventoryContract.InventoryEntry.COLUMN_PRICE,
            InventoryContract.InventoryEntry.COLUMN_QUANTITY,
            InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });


        final ListView itemListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        mInventoryAdapter = new InventoryCursorAdapter(this, null);
        itemListView.setAdapter(mInventoryAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent EditorActivityIntent = new Intent(view.getContext(), EditorActivity.class);
                Uri itemUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI,id);
                EditorActivityIntent.putExtra("position", id);
                EditorActivityIntent.setData(itemUri);
                startActivity(EditorActivityIntent);
            }
        });
        getSupportLoaderManager().initLoader(INVENTORY_LOADER,null,this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertItem();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPets(){
        int rowsDeleted = getContentResolver().delete(InventoryContract.InventoryEntry.CONTENT_URI,null,null);
        Log.d("TAG", "deleteAllPets: "+rowsDeleted);
    }


    private void insertItem() {

        ContentValues values = new ContentValues();

        values.put(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME, "Toto");
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, "2");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, "599");
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL,"travisliuadp@gmail.com");


        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, InventoryContract.InventoryEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mInventoryAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mInventoryAdapter.swapCursor(null);


    }
}
