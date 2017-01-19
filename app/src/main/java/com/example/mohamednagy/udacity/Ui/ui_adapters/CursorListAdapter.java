package com.example.mohamednagy.udacity.Ui.ui_adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by mohamednagy on 12/5/2016.
 */
public class CursorListAdapter extends CursorAdapter {

    private AdapterConnection adapterConnection;

    public CursorListAdapter(Context context, Cursor c, int flags,AdapterConnection adapterConnection) {
        super(context, c, flags);
        this.adapterConnection = adapterConnection;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return adapterConnection.newListView(viewGroup,cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        adapterConnection.bindListView(view,cursor);
    }

}
