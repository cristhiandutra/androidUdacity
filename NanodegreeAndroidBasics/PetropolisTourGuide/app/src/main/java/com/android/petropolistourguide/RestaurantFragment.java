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

public class RestaurantFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the localList layout and get the RootView
        View rootView = inflater.inflate(R.layout.local_list, container, false);

        // Configuration of Local list
        ArrayList<Local> locals = new ArrayList<>();
        locals.add(new Local(getString(R.string.restaurants_lago_sul), R.drawable.restaurants_lago_sul, getString(R.string.restaurants_lago_sul_address)));
        locals.add(new Local(getString(R.string.restaurants_bohemia), R.drawable.restaurants_bohemia, getString(R.string.restaurants_bohemia_address)));
        locals.add(new Local(getString(R.string.restaurants_casa_alemao), R.drawable.restaurants_casa_alemao, getString(R.string.restaurants_casa_alemao_address)));
        locals.add(new Local(getString(R.string.restaurants_duettos), R.drawable.restaurants_duettos, getString(R.string.restaurants_duettos_address)));
        locals.add(new Local(getString(R.string.restaurants_casa_dangelo), R.drawable.restaurants_casa_dangelo, getString(R.string.restaurants_casa_dangelo_address)));
        locals.add(new Local(getString(R.string.restaurants_tonis), R.drawable.restaurants_tonis, getString(R.string.restaurants_tonis_address)));
        locals.add(new Local(getString(R.string.restaurants_willemsen), R.drawable.restaurants_willemsen, getString(R.string.restaurants_willemsen_address)));

        // Set the LocalAdapter
        ListView localListView = rootView.findViewById(R.id.local_list_view);
        localListView.setAdapter(new LocalAdapter(getContext(), locals));

        return rootView;
    }
}
