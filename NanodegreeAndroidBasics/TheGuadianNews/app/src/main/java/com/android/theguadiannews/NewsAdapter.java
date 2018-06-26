package com.android.theguadiannews;

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

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is a listViewItem that we can reuse,
        // otherwise, if convertView is null, then inflate a new listViewItem layout.
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_news_item, parent, false);
        }

        // Find a news at given position int from news list
        News news = getItem(position);

        // Find the TextView with id title and set title news if not null
        TextView title = listViewItem.findViewById(R.id.title);
        if (news.getTitle() != null) {
            title.setText(news.getTitle());
        }

        // Find the TextView with id section and set section news if not null
        TextView section = listViewItem.findViewById(R.id.section);
        if (news.getSection() != null) {
            section.setText(news.getSection());
        }

        // Find the TextView with id publication and set news publication if not null
        TextView publication = listViewItem.findViewById(R.id.publication);
        if (news.getPublication() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            publication.setText(sdf.format(news.getPublication().getTime()));
        }

        // Find the ImageView with id thumbnail and set news url thumbnail using Picasso Library
        ImageView thumbnail = listViewItem.findViewById(R.id.thumbnail);
        if (news.getUrlThumbnail() != null) {
            Picasso.get().load(Uri.parse(news.getUrlThumbnail()))
                    .resize((int) getContext().getResources().getDimension(R.dimen.news_item_image_width),
                            (int) getContext().getResources().getDimension(R.dimen.news_item_image_height))
                    .centerCrop()
                    .into(thumbnail);
        }

        return listViewItem;
    }
}
