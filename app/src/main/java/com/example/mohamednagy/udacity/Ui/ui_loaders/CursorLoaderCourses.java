package com.example.mohamednagy.udacity.Ui.ui_loaders;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by mohamednagy on 12/3/2016.
 */
public interface CursorLoaderCourses {
    void initializeCursorLoader(LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks, @Nullable Integer id);

    void CursorLoaderFinished(Cursor cursor,Loader<Cursor> loader);
    Loader<Cursor> createCursorLoader(int id);
}
