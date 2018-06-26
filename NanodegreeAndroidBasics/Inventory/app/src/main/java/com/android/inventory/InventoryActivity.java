package com.android.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.inventory.data.InventoryContract.ProductEntry;

public class InventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = InventoryActivity.class.getSimpleName();

    private static final int PRODUCT_LOADER_ID = 0;

    private ProductCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Find a referente to EditorFloatingActionButton
        FloatingActionButton editorFloatingActionButton = findViewById(R.id.floating_action_button_editor);
        editorFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(InventoryActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });

        // Find a reference to EmptyTextView
        TextView emptyTextView = findViewById(R.id.text_view_empty);

        // Find a referente to ProductListView and set EmptyViewText
        ListView productListView = findViewById(R.id.list_view_product);
        productListView.setEmptyView(emptyTextView);

        // Creates a ProductCursorAdapter
        mAdapter = new ProductCursorAdapter(this, null);

        // Set adapter to productListView
        productListView.setAdapter(mAdapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editorIntent = new Intent(InventoryActivity.this, EditorActivity.class);
                editorIntent.setData(Uri.withAppendedPath(ProductEntry.CONTENT_URI, String.valueOf(id)));
                startActivity(editorIntent);
            }
        });

        // Init loader
        getLoaderManager().initLoader(PRODUCT_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Creates a projection to get product
        String[] projection = new String[]{
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_PHOTO};

        // Creates a CursoLoader with Product Content URI and return
        return new CursorLoader(this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertDummyData();
                return true;
            case R.id.action_delete_all_products:
                deleteAllProducts();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Delete all products from database
     */
    private void deleteAllProducts() {
        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);
        Log.v(LOG_TAG, rowsDeleted + " rows deleted from product database");

    }

    /**
     * Insert a Product to test app
     */
    private void insertDummyData() {

        // Dummy Data Setup
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "Halo 4");
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, 20);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, 50);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, "teste@mail.com");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_photo);
        values.put(ProductEntry.COLUMN_PRODUCT_PHOTO, ImageUtils.bitmapToByte(bitmap));

        // Insert a dummy data and return URI content
        Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);

        // If newUri is null means error saving product
        if (newUri == null) {
            Toast.makeText(this, R.string.editor_insert_product_failed, Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise means success
            Toast.makeText(this, R.string.editor_insert_product_successful, Toast.LENGTH_SHORT).show();
        }
    }
}
