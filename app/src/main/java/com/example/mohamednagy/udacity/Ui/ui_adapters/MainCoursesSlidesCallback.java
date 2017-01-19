package com.example.mohamednagy.udacity.Ui.ui_adapters;

import android.view.ViewGroup;

import com.example.mohamednagy.udacity.helper_classes.ViewAppHolder;

/**
 * Created by mohamednagy on 12/9/2016.
 */
public interface MainCoursesSlidesCallback {

    ViewAppHolder.ViewMainCoursesHolder newMainSlidesView(ViewGroup parent);
    void bindMainSlidesView(ViewAppHolder.ViewMainCoursesHolder view, int position);

}
