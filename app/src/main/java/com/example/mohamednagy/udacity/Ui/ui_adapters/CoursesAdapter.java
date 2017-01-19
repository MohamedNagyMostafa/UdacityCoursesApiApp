package com.example.mohamednagy.udacity.Ui.ui_adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamednagy.udacity.R;
import com.example.mohamednagy.udacity.Ui.UiHelper;
import com.example.mohamednagy.udacity.helper_classes.DatabaseController;
import com.example.mohamednagy.udacity.helper_classes.ViewAppHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by mohamednagy on 12/3/2016.
 */
public class CoursesAdapter extends RecyclerView.Adapter<ViewAppHolder.ViewCoursesSlideHolder>
        implements AdapterConnection {

    private Cursor cursor;
    private Context context;
    private OnClickRecycleViewListener onClickRecycleViewListener;


    public CoursesAdapter(Cursor cursor,Context context, OnClickRecycleViewListener onClickRecycleViewListener){
        this.cursor = cursor;
        this.context = context;
        this.onClickRecycleViewListener = onClickRecycleViewListener;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewAppHolder.ViewCoursesSlideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolde = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_courses,parent,false);

        // Set onClickListener for course view which recycled .
        viewHolde.setOnClickListener(onClickRecycleViewListener);
        Log.e("position is ","created");
        return new ViewAppHolder.ViewCoursesSlideHolder(viewHolde);
    }


    @Override
    public void onBindViewHolder(ViewAppHolder.ViewCoursesSlideHolder holder, int position) {
        Log.e("position", String.valueOf(position));

        if(cursor.moveToPosition(position)){

            final String TITLE_COURSE = cursor.getString(DatabaseController.TITLE_COLUMN);
            final String SUBTITLE_COURSE = cursor.getString(DatabaseController.SUBTITLE_COLUMN);
            final String SHORT_SUMMARY = cursor.getString(DatabaseController.SHORT_SUMMARY_COLUMN);
            final String COURSE_LEVEL = cursor.getString(DatabaseController.COURSE_LEVEL_COLUMN);
            final String COURSE_IMAGE = cursor.getString(DatabaseController.COURSE_IMAGE_COLUMN);
            final int COURSE_ID = cursor.getInt(DatabaseController.COURSE_ID_COLUMN);

                holder.titleCourseTextView.setText(TITLE_COURSE);
                holder.subtitleCourseTextView.setText(SUBTITLE_COURSE);
                holder.shortSummaryTextView.setText(Html.fromHtml(SHORT_SUMMARY));
                holder.levelCourseTextView.setText(COURSE_LEVEL);

                if (!COURSE_IMAGE.isEmpty())
                    Picasso.with(context).load(COURSE_IMAGE).into(holder.coursesImageView);

            // Load affiliates logo
            holder.affiliatesListView.setAdapter(new CursorListAdapter(
                    context, UiHelper.getAffiliateCursorFromCourseId(COURSE_ID,context),0,this));

        }
    }

    @Override
    public View newListView(ViewGroup parent, Cursor cursor) {
        return UiHelper.affiliatesView(parent,context);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {

        UiHelper.affiliatesBindView(view,cursor,context);
    }

    @Override
    public int getItemCount() {
        if(cursor != null)
            return cursor.getCount();
        return 0;
    }

}
