package com.android.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.inventory.data.InventoryContract.ProductEntry;

import java.text.NumberFormat;

public class ProductCursorAdapter extends CursorAdapter {


    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c The cursor from which to get data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        ImageView photoImageView = view.findViewById(R.id.photo);

        final long id = cursor.getLong(cursor.getColumnIndex(ProductEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));
        final int quantity = cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PHOTO));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        Double currentPrice = Double.valueOf(price);

        nameTextView.setText(name);
        priceTextView.setText(numberFormat.format(currentPrice));
        quantityTextView.setText(context.getString(R.string.editor_category_quantity) + ": " + String.valueOf(quantity));
        photoImageView.setImageBitmap(ImageUtils.byteToBitmap(photo));

        // Find a reference to saleImageView
        ImageView saleImageView = view.findViewById(R.id.image_view_sale);
        // If saleImageView is selected call sell method
        saleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct(id, quantity, v);
            }
        });
    }

    /**
     * Sell the Product decreasing quantity
     *
     * @param idProduct Id from Product to build Uri
     * @param currentQuantity Current product quantity
     * @param view View that contains the context to call content resolver
     */
    private void sellProduct(long idProduct, Integer currentQuantity, View view) {

        // Get quantity increase from numbers xml
        int productQuantitySaleDecrease = view.getContext().getResources().getInteger(R.integer.product_quantity_sale_decrease);

        // Calculates the quantity should be decreased
        Integer quantityDecrease = currentQuantity - productQuantitySaleDecrease;

        // Quantity can't be less then 0
        if (quantityDecrease == 0 || quantityDecrease > 0) {

            // Setup content values
            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantityDecrease);

            // Setup Uri with id
            Uri uriUpdate = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, idProduct);

            // Call update from content provider
            view.getContext().getContentResolver().update(uriUpdate, values, null, null);
        } else {
            Toast.makeText(view.getContext(), R.string.validation_message_stock, Toast.LENGTH_SHORT).show();
        }

    }
}
