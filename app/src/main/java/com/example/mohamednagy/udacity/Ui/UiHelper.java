package com.example.mohamednagy.udacity.Ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamednagy.udacity.R;
import com.example.mohamednagy.udacity.database.CoursesContract;
import com.example.mohamednagy.udacity.helper_classes.DatabaseController;
import com.example.mohamednagy.udacity.helper_classes.ViewAppHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by mohamednagy on 12/4/2016.
 */
public class UiHelper {

    public static final String COURSE_EXTRA = "title";

    public static final String ALL_COURSES = "All Courses";
    public static final String NEW_RELEASES = "New Release";
    public static final String ANDROID_COURSES = "Android";
    public static final String DATA_SCIENCE_COURSES = "Data Science";
    public static final String GEORGIA_TECH_MASTERS_COURSES = "Georgia Tech Masters in CS";
    public static final String IOS_COURSES = "iOS";
    public static final String NON_TECH_COURSES = "Non-Tech";
    public static final String SOFTWARE_ENGINEERING_COURSES = "Software Engineering";
    public static final String WEB_DEVELOPMENT_COURSES = "Web Development";

    public static final int LOADER_CURSOR_ALL_COURSES = 0;
    public static final int LOADER_CURSOR_NEW_RELEASE_COURSES = 1;
    public static final int LOADER_CURSOR_ANDROID_COURSES = 2;
    public static final int LOADER_CURSOR_DATA_SCIENCE_COURSES = 3;
    public static final int LOADER_CURSOR_WEB_DEVELOPMENT_COURSES = 4;
    public static final int LOADER_CURSOR_NON_TOUCH_COURSES = 5;
    public static final int LOADER_CURSOR_IOS_COURSES = 6;
    public static final int LOADER_CURSOR_GEORGIA_TECH_MASTERS_COURSES = 7;
    public static final int LOADER_CURSOR_SOFTWARE_ENGINEERING = 8;
    public static final int LOADER_CURSOR_DETAIL_COURSE = 9;
    public static final int LOADER_CURSOR_INSTRUCTOR_COURSE = 10;
    public static final int LOADER_CURSOR_AFFILIATES_COURSE = 11;

    public static CursorLoader createCursorLoader(int currentMode, Context context){
        switch (currentMode){

            case LOADER_CURSOR_NEW_RELEASE_COURSES :
                return getCursorLoaderNewRelease(context);

            case LOADER_CURSOR_ALL_COURSES :
                return new CursorLoader(
                        context,
                        CoursesContract.CoursesTable.CONTENT_URI,
                        DatabaseController.COURSES_PROJECTION,
                        null,
                        null,
                        null
                );

            case LOADER_CURSOR_ANDROID_COURSES :
                return getCursorLoaderSpecificCourses(context,ANDROID_COURSES);

            case LOADER_CURSOR_DATA_SCIENCE_COURSES :
                return getCursorLoaderSpecificCourses(context,DATA_SCIENCE_COURSES);

            case LOADER_CURSOR_WEB_DEVELOPMENT_COURSES :
                return getCursorLoaderSpecificCourses(context,WEB_DEVELOPMENT_COURSES);

            case LOADER_CURSOR_GEORGIA_TECH_MASTERS_COURSES :
                return getCursorLoaderSpecificCourses(context,GEORGIA_TECH_MASTERS_COURSES);

            case LOADER_CURSOR_IOS_COURSES :
                return getCursorLoaderSpecificCourses(context,IOS_COURSES);

            case LOADER_CURSOR_NON_TOUCH_COURSES :
                return getCursorLoaderSpecificCourses(context,NON_TECH_COURSES);

            case LOADER_CURSOR_SOFTWARE_ENGINEERING :
            return getCursorLoaderSpecificCourses(context,SOFTWARE_ENGINEERING_COURSES);

            default:
                return null;
        }
    }

    public static CursorLoader createCursorLoader(String courseTitle, Context context, int currentCursor){

        int courseId = getCourseIdFromCourseTitle(courseTitle,context);

        String selection = CoursesContract.CoursesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(courseId)};

        switch (currentCursor){

            case LOADER_CURSOR_DETAIL_COURSE :
                 return new CursorLoader(
                    context,
                    CoursesContract.CoursesTable.CONTENT_URI,
                    DatabaseController.COURSES_PROJECTION,
                    selection,
                    selectionArgs,
                    null
                 );

            case LOADER_CURSOR_AFFILIATES_COURSE :
                return new CursorLoader(
                        context,
                        CoursesContract.AffiliatesTable.CONTENT_URI,
                        DatabaseController.AFFILIATES_PROJECTION,
                        selection,
                        selectionArgs,
                        null
                );

            case LOADER_CURSOR_INSTRUCTOR_COURSE :
                return new CursorLoader(
                        context,
                        CoursesContract.InstructorsTable.CONTENT_URI,
                        DatabaseController.INSTRUCTORS_PROJECTION,
                        selection,
                        selectionArgs,
                        null
                );
        }

       return null;
    }

    private static CursorLoader getCursorLoaderNewRelease(Context context){

        String selection = CoursesContract.CoursesTable.NEW_RELEASE_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(CoursesContract.CoursesTable.IS_NEW_RELEASE)};

        return new CursorLoader(
                context,
                CoursesContract.CoursesTable.CONTENT_URI,
                DatabaseController.COURSES_PROJECTION,
                selection,
                selectionArgs,
                null
        );
    }

    private static CursorLoader getCursorLoaderSpecificCourses(Context context, String coursesType){
        String selection = CoursesContract.CoursesTable.TRACKS_COLUMN + "=?";
        String[] selectionArgs = {"[\""+coursesType+"\"]"};

        return new CursorLoader(
                context,
                CoursesContract.CoursesTable.CONTENT_URI,
                DatabaseController.COURSES_PROJECTION,
                selection,
                selectionArgs,
                null
        );
    }

    public static Cursor getAffiliateCursorFromCourseId(int courseId,Context context){

        String selection = CoursesContract.AffiliatesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(courseId)};

        return context.getContentResolver().query(
                CoursesContract.AffiliatesTable.CONTENT_URI,
                DatabaseController.AFFILIATES_PROJECTION,
                selection,
                selectionArgs,
                null
        );
    }

    public static void clearAffiliatesInstructorsTables(Context context){
        context.getContentResolver().delete(
                CoursesContract.AffiliatesTable.CONTENT_URI,
                null,
                null
        );
        context.getContentResolver().delete(
                CoursesContract.InstructorsTable.CONTENT_URI,
                null,
                null
        );

    }

    private static int getCourseIdFromCourseTitle(String courseTitle,Context context){

        int courseId = -1;

        String selection = CoursesContract.CoursesTable.TITLE_COLUMN + "=?";
        String[] selectionArgs = {courseTitle};

        Cursor cursor = context.getContentResolver().query(
                CoursesContract.CoursesTable.CONTENT_URI,
                DatabaseController.COURSES_PROJECTION,
                selection,
                selectionArgs,
                null
        );

        if(cursor != null && cursor.moveToNext()){
            courseId = cursor.getInt(DatabaseController.COURSE_ID_COLUMN);
            cursor.close();
        }

        return courseId;
    }

    public static View affiliatesView(ViewGroup parent, Context context){
        View affiliatesView = LayoutInflater.from(context)
                .inflate(R.layout.affiliates,parent,false);

        ViewAppHolder.ViewAffiliatesHolder viewAffiliatesHolder =
                new ViewAppHolder.ViewAffiliatesHolder(affiliatesView);

        affiliatesView.setTag(viewAffiliatesHolder);

        return affiliatesView;
    }

    public static View instructorsView(ViewGroup parent, Context context){
        View instructorsView = LayoutInflater.from(context)
                .inflate(R.layout.instructors,parent,false);

        ViewAppHolder.ViewInstructorsHolder viewInstructorsHolder =
                new ViewAppHolder.ViewInstructorsHolder(instructorsView);

        instructorsView.setTag(viewInstructorsHolder);

        return instructorsView;
    }

    public static void affiliatesBindView(View view,Cursor cursor,Context context){

        ViewAppHolder.ViewAffiliatesHolder viewAffiliatesHolder =
                (ViewAppHolder.ViewAffiliatesHolder) view.getTag();
            final String AFFILIATE_NAME =
                    cursor.getString(DatabaseController.AFFILIATES_NAME_COLUMN);
            final String AFFILIATE_IMAGE_URL =
                    cursor.getString(DatabaseController.AFFILIATES_IMAGE_COLUMN);

            viewAffiliatesHolder.AFFILIATES_NAME.setText(AFFILIATE_NAME);
            if (!AFFILIATE_IMAGE_URL.isEmpty())
                Picasso.with(context).load(AFFILIATE_IMAGE_URL).into(viewAffiliatesHolder.AFFILIATES_IMAGE);

    }

    public static void instructorsBindView(View view, Cursor cursor, Context context){

        ViewAppHolder.ViewInstructorsHolder viewInstructorsHolder =
                (ViewAppHolder.ViewInstructorsHolder) view.getTag();

            final String INSTRUCTOR_NAME = cursor.getString(DatabaseController.INSTRUCTORS_NAME_COLUMN);
            final String INSTRUCTOR_BIO = cursor.getString(DatabaseController.INSTRUCTORS_BIO_COLUMN);
            final String INSTRUCTOR_IMAGE = cursor.getString(DatabaseController.INSTRUCTORS_IMAGE_COLUMN);

            viewInstructorsHolder.INSTRUCTOR_NAME.setText(INSTRUCTOR_NAME);
            viewInstructorsHolder.INSTRUCTOR_BIO.setText(INSTRUCTOR_BIO);
            if (!INSTRUCTOR_IMAGE.isEmpty())
                Picasso.with(context).load(INSTRUCTOR_IMAGE).into(viewInstructorsHolder.INSTRUCTOR_IMAGE);

    }

}
