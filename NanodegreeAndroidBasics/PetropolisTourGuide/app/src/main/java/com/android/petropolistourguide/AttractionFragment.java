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

public class AttractionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the localList layout and get the RootView
        View rootView = inflater.inflate(R.layout.local_list, container, false);

        // Configuration of Local list
        ArrayList<Local> locals = new ArrayList<>();
        locals.add(new Local(getString(R.string.attractions_catedral_sao_pedro), R.drawable.attractions_catedral_sao_pedro, getString(R.string.attractions_catedral_sao_pedro_address)));
        locals.add(new Local(getString(R.string.attractions_palacio_cristal), R.drawable.attractions_palacio_cristal, getString(R.string.attractions_palacio_cristal_address)));
        locals.add(new Local(getString(R.string.attractions_obelisco), R.drawable.attractions_obelisco, getString(R.string.attractions_obelisco_address)));
        locals.add(new Local(getString(R.string.attractions_palacio_quitandinha), R.drawable.attractions_palacio_quitandinha, getString(R.string.attractions_palacio_quitandinha_address)));
        locals.add(new Local(getString(R.string.attractions_praca_liberdade), R.drawable.attractions_praca_liberdade, getString(R.string.attractions_praca_liberdade_address)));
        locals.add(new Local(getString(R.string.attractions_quatorze_bis), R.drawable.attractions_quatorze_bis, getString(R.string.attractions_quatorze_bis_address)));
        locals.add(new Local(getString(R.string.attractions_trono_fatima), R.drawable.attractions_trono_fatima, getString(R.string.attractions_trono_fatima_address)));

        // Set the LocalAdapter
        ListView localListView = rootView.findViewById(R.id.local_list_view);
        localListView.setAdapter(new LocalAdapter(getContext(), locals));

        return rootView;
    }
}
