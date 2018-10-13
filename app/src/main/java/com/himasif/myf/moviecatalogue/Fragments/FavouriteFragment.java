package com.himasif.myf.moviecatalogue.Fragments;



import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.himasif.myf.moviecatalogue.Adapters.ListFavouriteMovieAdapter;
import com.himasif.myf.moviecatalogue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;
import static com.himasif.myf.moviecatalogue.DB.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private ListFavouriteMovieAdapter listFavouriteMovieAdapter;
    @BindView(R.id.lv_favourite)
    ListView lvFavourite;
    private static final int LOAD_MOVIES_ID = 10;

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

        getActivity().getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, null, this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(LOAD_MOVIES_ID, null, this);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: cursor data : " + data.getCount());
        listFavouriteMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        listFavouriteMovieAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
