package com.android.petropolistourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LocalAdapter extends ArrayAdapter<Local> {

    public LocalAdapter(@NonNull Context context, @NonNull ArrayList<Local> locals) {
        super(context, 0, locals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.local_item, parent, false);
        }

        Local local = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.name_text_view);
        nameTextView.setText(local.getName());

        TextView addressTextView = listItemView.findViewById(R.id.address_text_view);
        addressTextView.setText(local.getAddress());

        ImageView thumbnailImageView = listItemView.findViewById(R.id.thumbnail_image_view);
        thumbnailImageView.setImageResource(local.getImageResourceId());

        return listItemView;
    }
}
