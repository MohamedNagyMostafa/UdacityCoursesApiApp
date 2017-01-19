package com.example.mohamednagy.udacity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mohamednagy.udacity.Ui.ui_adapters.CursorListAdapter;
import com.example.mohamednagy.udacity.Ui.ui_loaders.CursorLoaderCourses;
import com.example.mohamednagy.udacity.Ui.ui_loaders.FetchDatabaseLoader;
import com.example.mohamednagy.udacity.Ui.ui_adapters.AdapterConnection;
import com.example.mohamednagy.udacity.Ui.UiHelper;
import com.example.mohamednagy.udacity.animation.AppAnimation;
import com.example.mohamednagy.udacity.helper_classes.DatabaseController;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseDetailsFragment extends Fragment
    implements CursorLoaderCourses, AdapterConnection {

    private String courseTitle;

    private ImageView courseImageView;
    private ImageView courseVideoImageView;
    private TextView courseTitleTextView;
    private TextView courseDurationTextView;
    private TextView courseLevelTextView;
    private TextView courseSummaryTextView;
    private TextView courseSummaryPanelTextView;
    private TextView courseExpectedLearningTextView;
    private TextView courseExpectedLearningPanelTextView;
    private TextView courseSyllabusTextView;
    private TextView courseSyllabusPanelTextView;
    private TextView courseRequirementsTextView;
    private TextView courseRequirementsPanelTextView;
    private ListView courseInstructorsListView;
    private ListView courseAffiliatesListView;
    private Button startCourseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_details, container, false);

        courseImageView = (ImageView) rootView.findViewById(R.id.image_course_image_view);
        courseVideoImageView = (ImageView) rootView.findViewById(R.id.course_video_Image_View);
        courseTitleTextView = (TextView) rootView.findViewById(R.id.title_course_text_view);
        courseDurationTextView = (TextView) rootView.findViewById(R.id.duration_course_text_view);
        courseLevelTextView = (TextView) rootView.findViewById(R.id.level_course_text_view);
        courseSummaryTextView = (TextView) rootView.findViewById(R.id.course_summary_text_view);
        courseSummaryPanelTextView = (TextView) rootView.findViewById(R.id.course_summary_panel);
        courseExpectedLearningTextView = (TextView) rootView.findViewById(R.id.course_expected_learning_text_view);
        courseExpectedLearningPanelTextView = (TextView) rootView.findViewById(R.id.course_expected_learning_panel);
        courseSyllabusTextView = (TextView) rootView.findViewById(R.id.course_syllabus_text_view);
        courseSyllabusPanelTextView = (TextView) rootView.findViewById(R.id.course_syllabus_panel);
        courseRequirementsTextView = (TextView) rootView.findViewById(R.id.course_requirements_text_view);
        courseRequirementsPanelTextView = (TextView) rootView.findViewById(R.id.course_requirements_panel);
        courseInstructorsListView = (ListView) rootView.findViewById(R.id.instructors_course_list_view);
        courseAffiliatesListView = (ListView) rootView.findViewById(R.id.affiliates_course_list_view);
        startCourseButton = (Button) rootView.findViewById(R.id.start_course_btn);

        Bundle courseTitleBundle = getArguments();

        courseTitle = courseTitleBundle.getString(UiHelper.COURSE_EXTRA);

        FetchDatabaseLoader fetchDatabaseLoader =
                new FetchDatabaseLoader(this);
        fetchDatabaseLoader.start(null);

        return rootView;
    }

    @Override
    public void initializeCursorLoader(LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks, @Nullable Integer id) {
        getActivity().getSupportLoaderManager().initLoader(UiHelper.LOADER_CURSOR_DETAIL_COURSE,null,loaderCallbacks);
        getActivity().getSupportLoaderManager().initLoader(UiHelper.LOADER_CURSOR_AFFILIATES_COURSE,null,loaderCallbacks);
        getActivity().getSupportLoaderManager().initLoader(UiHelper.LOADER_CURSOR_INSTRUCTOR_COURSE,null,loaderCallbacks);
    }

    @Override
    public Loader<Cursor> createCursorLoader(int id) {
        return UiHelper.createCursorLoader(courseTitle,getContext(),id);
    }

    @Override
    public void CursorLoaderFinished(Cursor cursor, Loader<Cursor> loader) {

        switch (loader.getId()){
            case UiHelper.LOADER_CURSOR_DETAIL_COURSE :
                cursorLoaderFinishedDetailCourse(cursor);
                break;

            case UiHelper.LOADER_CURSOR_AFFILIATES_COURSE :
                if(cursor != null)
                    courseAffiliatesListView.setAdapter(
                            new CursorListAdapter(
                                    getContext(),
                                    cursor,
                                    0,
                                    this)
                    );
                break;

            case UiHelper.LOADER_CURSOR_INSTRUCTOR_COURSE :
                if(cursor != null)
                    courseInstructorsListView.setAdapter(
                            new CursorListAdapter(
                                    getContext(),
                                    cursor,
                                    0,
                                    this
                            )
                    );
                break;
            default:
                Log.e("cursor ","is error");
        }

    }
    private void cursorLoaderFinishedDetailCourse(Cursor cursor){
        if(cursor != null && cursor.moveToNext()){

            final int COURSE_ID = cursor.getInt(DatabaseController.COURSE_ID_COLUMN);
            final String COURSE_TITLE = cursor.getString(DatabaseController.TITLE_COLUMN);
            final String COURSE_URL_IMAGE = cursor.getString(DatabaseController.COURSE_IMAGE_COLUMN);
            final String COURSE_VIDEO = cursor.getString(DatabaseController.YOUTUBE_VIDEO_COLUMN);
            final String COURSE_LEVEL = cursor.getString(DatabaseController.COURSE_LEVEL_COLUMN);
            final String COURSE_DURATION_UNIT = cursor.getString(DatabaseController.EXPECTED_DURATION_UNIT_COLUMN);
            final int COURSE_DURATION =  cursor.getInt(DatabaseController.EXPECTED_DURATION_COLUMN);
            final String COURSE_SUMMARY = cursor.getString(DatabaseController.SUMMARY_COLUMN);
            final String COURSE_SYLLABUS = cursor.getString(DatabaseController.SYLLABUS_COLUMN);
            final String COURSE_EXPECTED_LEARNING = cursor.getString(DatabaseController.EXPECTED_LEARNING_COLUMN);
            final String COURSE_REQUIREMENTS = cursor.getString(DatabaseController.REQUIRED_KNOWLEDGE_COLUMN);
            final String COURSE_KEY =  cursor.getString(DatabaseController.COURSE_KEY_COLUMN);

            if(!COURSE_URL_IMAGE.isEmpty())
                Picasso.with(getContext()).load(COURSE_URL_IMAGE).into(courseImageView);

            setVideoCourse(COURSE_VIDEO);

            courseTitleTextView.setText(COURSE_TITLE);

            courseDurationTextView.setText(courseDuration(COURSE_DURATION,COURSE_DURATION_UNIT));

            setCourseLevel(COURSE_LEVEL);

            courseSummaryTextView.setText(Html.fromHtml(COURSE_SUMMARY));
            courseSyllabusTextView.setText(Html.fromHtml(COURSE_SYLLABUS));
            courseExpectedLearningTextView.setText(Html.fromHtml(COURSE_EXPECTED_LEARNING));
            courseRequirementsTextView.setText(Html.fromHtml(COURSE_REQUIREMENTS));

            // Panel animation
            AppAnimation.setPanelAnimation(
                    courseSyllabusTextView,courseSyllabusPanelTextView,
                    courseSummaryTextView, courseSummaryPanelTextView,
                    courseRequirementsTextView, courseRequirementsPanelTextView,
                    courseExpectedLearningTextView, courseExpectedLearningPanelTextView,
                    getContext());

            //setCourseStartButton(COURSE_KEY);

        }
    }
    private void setVideoCourse(String courseVideoUrl){

        final String COURSE_VIDEO = courseVideoUrl;

        if(!COURSE_VIDEO.isEmpty()){
            courseVideoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent youtubeApp = new Intent(Intent.ACTION_VIEW);
                    youtubeApp.setData(Uri.parse(COURSE_VIDEO));
                    startActivity(youtubeApp);
                }
            });
        }else{
            courseVideoImageView.setVisibility(View.INVISIBLE);
        }
    }

    private String courseDuration(int courseDuration,String courseDurationUnit){

        final String DURATION = "Approx. "
                + String.valueOf(courseDuration)
                + courseDurationUnit;
        return DURATION ;
    }

    private void setCourseLevel(String courseLevel){

        final String LEVEL = courseLevel.toUpperCase();
        courseLevelTextView.setText(LEVEL);

        switch (LEVEL){
            case "BEGINNER":
                courseLevelTextView.setCompoundDrawables(
                        getActivity().getDrawable(R.drawable.beginner),
                        null,
                        null,
                        null);
                break;
            case "INTERMEDIATE":
                courseLevelTextView.setCompoundDrawables(
                        getActivity().getDrawable(R.drawable.intermediate),
                        null,
                        null,
                        null);
                break;
            case "ADVANCED":
                courseLevelTextView.setCompoundDrawables(
                        getActivity().getDrawable(R.drawable.advanced),
                        null,
                        null,
                        null);
                break;
        }
    }

    private void setCourseStartButton(String courseKey){

        final String START_URL = "https://classroom.udacity.com/courses";

        final String START_URL_COURSE =
                Uri.parse(START_URL).buildUpon().appendEncodedPath(courseKey).build().toString();

        Intent browserIntet = new Intent(Intent.ACTION_VIEW);
        browserIntet.setData(Uri.parse(START_URL_COURSE));
        startActivity(browserIntet);
    }

    @Override
    public View newListView(ViewGroup parent, Cursor cursor) {
        // To check which view exist
        Log.e("column count",String.valueOf(cursor.getColumnCount()));
        switch(cursor.getColumnCount()){
            case DatabaseController.AFFILIATES_COLUMNS_COUNT :
                return UiHelper.affiliatesView(parent,getContext());

            case DatabaseController.INSTRUCTORS_COLUMNS_COUNT :
                return UiHelper.instructorsView(parent,getContext());
        }

        return null;
    }

    @Override
    public void bindListView(View view, Cursor cursor) {
        // To check which view exist
        switch(cursor.getColumnCount()){
            case DatabaseController.AFFILIATES_COLUMNS_COUNT :
                UiHelper.affiliatesBindView(view,cursor,getContext());
                break;

            case DatabaseController.INSTRUCTORS_COLUMNS_COUNT :
                UiHelper.instructorsBindView(view,cursor,getContext());
                break;
        }
    }

}
