package com.example.mohamednagy.udacity.Ui.ui_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.mohamednagy.udacity.helper_classes.ViewAppHolder;

/**
 * Created by mohamednagy on 12/9/2016.
 */
public class MainRecycleViewAdapter extends RecyclerView.Adapter<ViewAppHolder.ViewMainCoursesHolder> {

    public static final int SLIDES_COURSES_NUMBER = 9;
    private MainCoursesSlidesCallback mainCoursesSlidesCallback;

    public MainRecycleViewAdapter(MainCoursesSlidesCallback mainCoursesSlidesCallback){
        this.mainCoursesSlidesCallback = mainCoursesSlidesCallback;
    }

    @Override
    public ViewAppHolder.ViewMainCoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mainCoursesSlidesCallback.newMainSlidesView(parent);
    }

    @Override
    public void onBindViewHolder(ViewAppHolder.ViewMainCoursesHolder holder, int position) {
        mainCoursesSlidesCallback.bindMainSlidesView(holder,position);
    }

    @Override
    public int getItemCount() {
        return SLIDES_COURSES_NUMBER;
    }

}
