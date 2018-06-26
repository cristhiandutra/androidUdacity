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

public class ParkFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the localList layout and get the RootView
        View rootView = inflater.inflate(R.layout.local_list, container, false);

        // Configuration of Local list
        ArrayList<Local> locals = new ArrayList<>();
        locals.add(new Local(getString(R.string.parks_municipal), R.drawable.parks_municipal, getString(R.string.parks_municipal_address)));
        locals.add(new Local(getString(R.string.parks_cremerie), R.drawable.parks_cremerie, getString(R.string.parks_cremerie_address)));
        locals.add(new Local(getString(R.string.parks_serra_dos_orgaos), R.drawable.parks_serra_dos_orgaos, getString(R.string.parks_serra_dos_orgaos_address)));
        locals.add(new Local(getString(R.string.parks_vale_do_amor), R.drawable.parks_vale_do_amor, getString(R.string.parks_vale_do_amor_address)));

        // Set the LocalAdapter
        ListView localListView = rootView.findViewById(R.id.local_list_view);
        localListView.setAdapter(new LocalAdapter(getContext(), locals));

        return rootView;
    }
}
