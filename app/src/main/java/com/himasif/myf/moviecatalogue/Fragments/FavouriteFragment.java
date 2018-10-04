package com.himasif.myf.moviecatalogue.Fragments;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.himasif.myf.moviecatalogue.Adapters.ListFavouriteMovieAdapter;
import com.himasif.myf.moviecatalogue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.himasif.myf.moviecatalogue.DB.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private ListFavouriteMovieAdapter listFavouriteMovieAdapter;
    @BindView(R.id.lv_favourite)
    ListView lvFavourite;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        listFavouriteMovieAdapter = new ListFavouriteMovieAdapter(getContext(), null, true);
        lvFavourite.setAdapter(listFavouriteMovieAdapter);
        lvFavourite.setOnItemClickListener(this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getContext(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        listFavouriteMovieAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listFavouriteMovieAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
