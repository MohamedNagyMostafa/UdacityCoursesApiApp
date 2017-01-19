package com.example.mohamednagy.udacity.helper_classes;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mohamednagy.udacity.R;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by mohamednagy on 12/5/2016.
 */
public class ViewAppHolder {

    public static class ViewMainCoursesHolder extends RecyclerView.ViewHolder{

        public final RecyclerView mainRecyclerViews;
        public final TextView coursesTitleImageView;

        public ViewMainCoursesHolder(View itemView) {
            super(itemView);
            mainRecyclerViews = (RecyclerView) itemView.findViewById(R.id.recycle_slides_course_view);
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(mainRecyclerViews);
            mainRecyclerViews.setItemAnimator(new SlideInRightAnimator());
            mainRecyclerViews.setItemAnimator(new SlideInLeftAnimator());
            coursesTitleImageView = (TextView) itemView.findViewById(R.id.main_courses_title_text_view);
        }
    }

    public static class ViewCoursesSlideHolder extends RecyclerView.ViewHolder{

        public final TextView titleCourseTextView;
        public final TextView subtitleCourseTextView;
        public final TextView shortSummaryTextView;
        public final TextView levelCourseTextView;
        public final ImageView coursesImageView;
        public final ListView affiliatesListView;

        public ViewCoursesSlideHolder(View itemView) {
            super(itemView);

            titleCourseTextView = (TextView) itemView.findViewById(R.id.title_courses_text_view);
            subtitleCourseTextView = (TextView) itemView.findViewById(R.id.subtitle_text_view);
            shortSummaryTextView = (TextView) itemView.findViewById(R.id.short_summary_text_view);
            levelCourseTextView = (TextView) itemView.findViewById(R.id.level_courses_text_view);
            coursesImageView = (ImageView) itemView.findViewById(R.id.image_courses_image_view);
            affiliatesListView = (ListView) itemView.findViewById(R.id.affiliates_list_view);

        }
    }

    public static class ViewAffiliatesHolder extends RecyclerView.ViewHolder{

        public final TextView AFFILIATES_NAME;
        public final ImageView AFFILIATES_IMAGE;

        public ViewAffiliatesHolder(View itemView) {
            super(itemView);
            AFFILIATES_NAME = (TextView) itemView.findViewById(R.id.affiliates_name_text_view);
            AFFILIATES_IMAGE = (ImageView) itemView.findViewById(R.id.affiliates_image_view);
        }
    }

    public static class ViewInstructorsHolder extends RecyclerView.ViewHolder{

        public final TextView INSTRUCTOR_NAME;
        public final TextView INSTRUCTOR_BIO;
        public final ImageView INSTRUCTOR_IMAGE;

        public ViewInstructorsHolder(View itemView) {
            super(itemView);
            INSTRUCTOR_NAME = (TextView) itemView.findViewById(R.id.instructor_name_text_view);
            INSTRUCTOR_BIO = (TextView) itemView.findViewById(R.id.instructor_bio_text_view);
            INSTRUCTOR_IMAGE = (ImageView) itemView.findViewById(R.id.instructor_image_view);
        }
    }

}
