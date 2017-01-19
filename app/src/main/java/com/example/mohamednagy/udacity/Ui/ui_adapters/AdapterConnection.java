package com.example.mohamednagy.udacity.Ui.ui_adapters;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mohamednagy on 12/5/2016.
 */
public interface AdapterConnection {

    View newListView(ViewGroup parent, Cursor cursor);
    void bindListView(View view, Cursor cursor);

}
