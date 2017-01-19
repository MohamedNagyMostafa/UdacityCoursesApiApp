package com.example.mohamednagy.udacity.Ui.ui_loaders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by mohamednagy on 12/3/2016.
 */
public class FetchDatabaseLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorLoaderCourses cursorLoaderCourses;

    public FetchDatabaseLoader (CursorLoaderCourses cursorLoaderCourses){
        this.cursorLoaderCourses = cursorLoaderCourses;
    }

    public void start(@Nullable Integer loaderId){
        if(loaderId != null)
            cursorLoaderCourses.initializeCursorLoader(this, loaderId);
        else
            cursorLoaderCourses.initializeCursorLoader(this,null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return cursorLoaderCourses.createCursorLoader(id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorLoaderCourses.CursorLoaderFinished(data,loader);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorLoaderCourses = null;
    }
}
