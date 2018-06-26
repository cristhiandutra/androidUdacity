package com.android.booklist;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book>{

    public BookAdapter(@NonNull Context context, @NonNull List books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup root) {
        // Check if there is an listViewItem (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new listViewItem layout.
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_book_item, root, false);
        }

        // Find book at the given position int the book list
        Book book = getItem(position);

        // Find the TextView with id title and set book title
        TextView titleTextView = listViewItem.findViewById(R.id.title);
        titleTextView.setText(book.getTitle());

        // Find the TextView with id author and set book author
        TextView authorTextView = listViewItem.findViewById(R.id.author);
        StringBuilder sb = new StringBuilder();
        for (String author : book.getAuthor()) {
            if (sb.length() > 0) {
                sb.append(" / ");
            }
            sb.append(author);
        }
        authorTextView.setText(sb.toString());

        // Find the ImageView with id thumbnail and set book thumbnail
        ImageView thumbnailImageView = listViewItem.findViewById(R.id.thumbnail);

        // Use Picasso Library to get thumbnail from url and show in ImageView
        if (book.getThumbnailUrl() != null) {
            Picasso.get().load(Uri.parse(book.getThumbnailUrl())).into(thumbnailImageView);
        }

        return listViewItem;
    }
}
