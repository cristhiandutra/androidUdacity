package com.android.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.inventory.data.InventoryContract.ProductEntry;

import java.io.IOException;
import java.text.NumberFormat;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = EditorActivity.class.getSimpleName();

    private final int EXISTING_PRODUCT_LOADER = 0;

    private boolean mProductHasChange = false;

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierEditText;
    private ImageView mProductPhotoImageView;

    private Uri mCurrentProductUri;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap mProductPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ImageButton quantityRemoveImageButton = findViewById(R.id.image_button_quantity_remove);
        ImageButton quantityAddImageButton = findViewById(R.id.image_button_quantity_add);

        mNameEditText = findViewById(R.id.edit_product_name);
        mPriceEditText = findViewById(R.id.edit_product_price);
        mQuantityEditText = findViewById(R.id.edit_product_quantity);
        mSupplierEditText = findViewById(R.id.edit_product_supplier);
        mProductPhotoImageView = findViewById(R.id.image_view_product_photo);

        // Listener to add a product photo
        ImageView addProductPhotoImageButton = findViewById(R.id.image_button_product_photo_add);
        addProductPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        mNameEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        mSupplierEditText.setOnTouchListener(mTouchListener);
        quantityAddImageButton.setOnTouchListener(mTouchListener);
        quantityRemoveImageButton.setOnTouchListener(mTouchListener);
        addProductPhotoImageButton.setOnTouchListener(mTouchListener);

        // Listener to remove quantity
        quantityRemoveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verify if quantity is filled
                if (!TextUtils.isEmpty(mQuantityEditText.getText())) {
                    // Get the current quantity
                    int currentQuantity = Integer.valueOf(mQuantityEditText.getText().toString());
                    // if quantity is more than 0
                    if (currentQuantity > 0) {
                        int quantity = currentQuantity - 1;
                        mQuantityEditText.setText(String.valueOf(quantity));
                    }

                }
            }
        });

        // Listener to add quantity
        quantityAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity;
                // Verify if quantity is no filled and set 1
                if (TextUtils.isEmpty(mQuantityEditText.getText())) {
                    quantity = 1;
                } else {
                    // Otherwise increment 1
                    int currentQuantity = Integer.valueOf(mQuantityEditText.getText().toString());
                    quantity = currentQuantity + 1;
                }

                mQuantityEditText.setText(String.valueOf(quantity));
            }
        });

        // Get Uri product
        mCurrentProductUri = getIntent().getData();

        // If product Uri is null means tha is a new product
        if (mCurrentProductUri == null) {
            setTitle(getString(R.string.editor_activity_title_new_product));

            // Invalidate the options menu, so is possible Delete option be hide;
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.editor_activity_title_edit_product));

            // Init the loader that load product
            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                // Get photo data uri
                Uri uri = data.getData();

                // Get Bitmap from gallery using uri
                mProductPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                // Set thumbnail photo
                mProductPhotoImageView.setImageBitmap(mProductPhoto);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error with add photo gallery. ", e);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Create a CursoLoader that load Product from URI with ID
        CursorLoader cursorLoader = new CursorLoader(this, mCurrentProductUri, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Move to first position of cursor if the exists
        if (cursor.moveToFirst()) {

            // Get the index columns
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER);
            int photoColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PHOTO);

            // Get the values
            String name = cursor.getString(nameColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            Double price = cursor.getDouble(priceColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);
            byte[] photo = cursor.getBlob(photoColumnIndex);

            // Fill fields with values
            mNameEditText.setText(name);
            mQuantityEditText.setText(String.valueOf(quantity));
            mPriceEditText.setText(String.valueOf(price));
            mProductPhoto = ImageUtils.byteToBitmap(photo);
            mSupplierEditText.setText(supplier);
            mProductPhotoImageView.setImageBitmap(mProductPhoto);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText(null);
        mQuantityEditText.setText(null);
        mPriceEditText.setText(null);
    }

    @Override
    public void onBackPressed() {
        // If product has no changes back to IventoryActivity
        if (!mProductHasChange) {
            super.onBackPressed();
            return;
        }

        // Listener to discardButton changes
        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Finish if user discard changes
                finish();
            }
        };

        // Show the discardUnsavedChangesDialog
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new Product, hide the Delete and Order More menu item
        if (mCurrentProductUri == null) {
            MenuItem deleteMenuItem = menu.findItem(R.id.action_delete);
            deleteMenuItem.setVisible(false);

            MenuItem orderMoreMenuItem = menu.findItem(R.id.action_order_more);
            orderMoreMenuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                boolean isProductSaved = saveProduct();
                if (isProductSaved) {
                    finish();
                }
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_order_more:

                sendEmail();

                return true;
            case android.R.id.home:
                // If product has no changes, navigate to InventoryActivity
                if (!mProductHasChange) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Listener to discardButton changes
                DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Navigate to IventoryActivity if user discard changes
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup and start activity do send email to order
     */
    private void sendEmail() {
        // Get product name
        String nameString = mNameEditText.getText().toString().trim();
        String supplierEmail = mSupplierEditText.getText().toString().trim();

        // Body text
        StringBuilder body = new StringBuilder();
        body.append("\n").append(getString(R.string.product)).append(" ").append(nameString);
        body.append("\n").append(getString(R.string.editor_category_quantity)).append(": ");

        // Setup and send intent email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{supplierEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, body.toString());

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    /**
     * Touch Listenter that define product changes.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mProductHasChange = true;
            return false;
        }
    };

    /**
     * Setup and show the discardUnsavedChangesDialog
     * @param discardButtonClickListener
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user click on KeepEditingButton close UnsavedChangesDialog
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Create a Delete Alert Dialog to confirm
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If confirm delete product
                deleteProduct();
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If cancel is clicked close the dialog
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Save product if the fields required are filled
     * @return {@link Boolean} True if product is saved
     */
    private boolean saveProduct() {

        String nameString = mNameEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String supplierString = mSupplierEditText.getText().toString().trim();

        // Name Validation
        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, getString(R.string.product_name_validation), Toast.LENGTH_SHORT).show();
            return false;
        }

        // Supplier Email Validation
        if (TextUtils.isEmpty(supplierString)) {
            Toast.makeText(this, getString(R.string.product_supplier_validation), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // Use Android Patterns to verify email
            boolean isSuppllierEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(supplierString).matches();
            if (!isSuppllierEmailValid) {
                Toast.makeText(this, "Invalid Supplier E-mail", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // Photo Validation
        if (mProductPhoto == null) {
            Toast.makeText(this, getString(R.string.product_photo_validation), Toast.LENGTH_SHORT).show();
            return false;
        }

        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }

        Double price = 0.0;
        if (!TextUtils.isEmpty(priceString)) {
            price = Double.valueOf(priceString);
        }

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.format(price);

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, supplierString);
        values.put(ProductEntry.COLUMN_PRODUCT_PHOTO, ImageUtils.bitmapToByte(mProductPhoto));

        if (mCurrentProductUri == null) {
            getContentResolver().insert(ProductEntry.CONTENT_URI, values);
            Toast.makeText(this, getString(R.string.editor_insert_product_successful), Toast.LENGTH_SHORT).show();
        } else {
            int rowsUpdated = getContentResolver().update(mCurrentProductUri, values, null, null);

            if (rowsUpdated == 0) {
                Toast.makeText(this, getString(R.string.editor_update_product_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_update_product_successful), Toast.LENGTH_SHORT).show();
            }
        }

        return true;
    }

    /**
     * Delete the product
     */
    private void deleteProduct() {
        // If there is a product to delete
        if (mCurrentProductUri != null) {

            // Delete the product from URI
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);

            // Verify if product was deleted
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_product_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_product_successful), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
