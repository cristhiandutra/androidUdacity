package com.android.petropolistourguide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MuseumFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the localList layout and get the RootView
        View rootView = inflater.inflate(R.layout.local_list, container, false);

        // Configuration of Local list
        ArrayList<Local> locals = new ArrayList<>();
        locals.add(new Local(getString(R.string.museums_imperial), R.drawable.museums_imperial, getString(R.string.museums_imperial_address)));
        locals.add(new Local(getString(R.string.museums_santos_dumont), R.drawable.museums_santos_dumont, getString(R.string.museums_santos_dumont_address)));
        locals.add(new Local(getString(R.string.museums_casa_colono), R.drawable.museums_casa_colono, getString(R.string.museums_casa_colono_address)));
        locals.add(new Local(getString(R.string.museums_stefan_zweig), R.drawable.museums_stefan_zweig, getString(R.string.museums_stefan_zweig_address)));
        locals.add(new Local(getString(R.string.museums_cera), R.drawable.museums_cera, getString(R.string.museums_cera_address)));

        // Set the LocalAdapter
        ListView localListView = rootView.findViewById(R.id.local_list_view);
        localListView.setAdapter(new LocalAdapter(getContext(), locals));

        return rootView;
    }
}
