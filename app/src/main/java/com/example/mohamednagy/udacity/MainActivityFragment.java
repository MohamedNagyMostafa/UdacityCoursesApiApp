package com.example.mohamednagy.udacity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamednagy.udacity.Ui.UiHelper;
import com.example.mohamednagy.udacity.Ui.UiListener;
import com.example.mohamednagy.udacity.Ui.ui_adapters.CoursesAdapter;
import com.example.mohamednagy.udacity.Ui.ui_adapters.MainCoursesSlidesCallback;
import com.example.mohamednagy.udacity.Ui.ui_adapters.MainRecycleViewAdapter;
import com.example.mohamednagy.udacity.Ui.ui_adapters.OnClickRecycleViewListener;
import com.example.mohamednagy.udacity.Ui.ui_loaders.CursorLoaderCourses;
import com.example.mohamednagy.udacity.Ui.ui_loaders.FetchDatabaseLoader;
import com.example.mohamednagy.udacity.async.AsyncLoaderNetwork;
import com.example.mohamednagy.udacity.async.AsyncNetworkLoader;
import com.example.mohamednagy.udacity.helper_classes.ViewAppHolder;
import com.example.mohamednagy.udacity.network.FetchDataAsyncLoader;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
    implements AsyncLoaderNetwork ,CursorLoaderCourses,
        OnClickRecycleViewListener ,MainCoursesSlidesCallback{

    private UiListener uiListener;

    private CoursesAdapter coursesAdapterNewReleaseCourses;
    private CoursesAdapter coursesAdapterALLCourses;
    private CoursesAdapter coursesAdapterAndroidCourses;
    private CoursesAdapter coursesAdapterDataScienceCourses;
    private CoursesAdapter coursesAdapterIOS;
    private CoursesAdapter coursesAdapterWebDevelopment;
    private CoursesAdapter coursesAdapterNonTouch;
    private CoursesAdapter coursesAdapterGeorgiaTechMasters;
    private CoursesAdapter coursesAdapterSoftwareEngineering;
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    private RecyclerView mainRecyclerViews;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static final int LOADER_NETWORK_COURSES_ID = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mainRecyclerViews = (RecyclerView) rootView.findViewById(R.id.main_recycle_views);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();

        mainRecyclerViews.setLayoutManager(linearLayoutManager);

        snapHelper.attachToRecyclerView(mainRecyclerViews);

        coursesAdapterALLCourses = new CoursesAdapter(null,getContext(),this);
        coursesAdapterNewReleaseCourses = new CoursesAdapter(null,getContext(),this);
        coursesAdapterAndroidCourses = new CoursesAdapter(null,getContext(),this);
        coursesAdapterDataScienceCourses = new CoursesAdapter(null,getContext(),this);
        coursesAdapterIOS = new CoursesAdapter(null,getContext(),this);
        coursesAdapterWebDevelopment = new CoursesAdapter(null,getContext(),this);
        coursesAdapterNonTouch = new CoursesAdapter(null,getContext(),this);
        coursesAdapterGeorgiaTechMasters = new CoursesAdapter(null,getContext(),this);
        coursesAdapterSoftwareEngineering = new CoursesAdapter(null,getContext(),this);
        mainRecycleViewAdapter = new MainRecycleViewAdapter(this);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_main_courses);
        mainRecyclerViews.setAdapter(mainRecycleViewAdapter);

            FetchDataAsyncLoader fetchDataAsyncLoader = new FetchDataAsyncLoader(this);

            fetchDataAsyncLoader.start();


        return rootView;
    }

    public void setUiListener(UiListener uiListener){
        this.uiListener = uiListener;
    }

    @Override
    public void initializeNetworkLoader(LoaderManager.LoaderCallbacks<Void> loaderCallbacks) {
        if(loaderCallbacks != null) {
            swipeRefreshLayout.setRefreshing(true);
            getActivity().getSupportLoaderManager().initLoader(LOADER_NETWORK_COURSES_ID, null, loaderCallbacks).forceLoad();
        }
    }

    @Override
    public AsyncNetworkLoader createNetworkLoader() {
        return new AsyncNetworkLoader(getActivity());
    }

    @Override
    public void networkLoaderIsFinished() {
        swipeRefreshLayout.setRefreshing(false);
        Log.e("network","is finished");
    }

    /**
     * Used to initialize loader of courses slide.
     * @param loaderCallbacks current loader
     * @param loaderId  id of current loader ( Android courses - Data science courses ...)
     */
    @Override
    public void initializeCursorLoader(LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks,
                                       @Nullable Integer loaderId) {
        getLoaderManager().initLoader(loaderId,null,loaderCallbacks).forceLoad();
    }

    /**
     * return the loader depend on it's id
     * @param id id of loader
     * @return specific courses loader
     */
    @Override
    public Loader<Cursor> createCursorLoader(int id) {
        return UiHelper.createCursorLoader(id,getContext());
    }

    /**
     * Used to set course which passed as parameter to it's adapter
     * (all courses adapter - android courses adapter - ..etc) .
     * @param cursor    cursor hold data for specific type of courses
     * @param loader    current loader which finished
     */
    @Override
    public void CursorLoaderFinished(Cursor cursor,Loader<Cursor> loader) {
        Log.e("slide ",String.valueOf(loader.getId()) +" is downloaded");
        if(cursor != null)
            switch (loader.getId()){
                case UiHelper.LOADER_CURSOR_ALL_COURSES :
                    coursesAdapterALLCourses.setCursor(cursor);
                    coursesAdapterALLCourses.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_NEW_RELEASE_COURSES :
                    coursesAdapterNewReleaseCourses.setCursor(cursor);
                    coursesAdapterNewReleaseCourses.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_ANDROID_COURSES :
                    coursesAdapterAndroidCourses.setCursor(cursor);
                    coursesAdapterAndroidCourses.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_DATA_SCIENCE_COURSES :
                    coursesAdapterDataScienceCourses.setCursor(cursor);
                    coursesAdapterDataScienceCourses.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_SOFTWARE_ENGINEERING:
                    coursesAdapterSoftwareEngineering.setCursor(cursor);
                    coursesAdapterSoftwareEngineering.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_IOS_COURSES:
                    coursesAdapterIOS.setCursor(cursor);
                    coursesAdapterIOS.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_NON_TOUCH_COURSES:
                    coursesAdapterNonTouch.setCursor(cursor);
                    coursesAdapterNonTouch.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_WEB_DEVELOPMENT_COURSES:
                    coursesAdapterWebDevelopment.setCursor(cursor);
                    coursesAdapterWebDevelopment.notifyDataSetChanged();
                    break;
                case UiHelper.LOADER_CURSOR_GEORGIA_TECH_MASTERS_COURSES:
                    coursesAdapterGeorgiaTechMasters.setCursor(cursor);
                    coursesAdapterGeorgiaTechMasters.notifyDataSetChanged();
                    break;
            }
    }

    /**
     * ClickListener for course which selected by user.
     * Launch CourseDetails activity depend on course title.
     * @param view  view of current course which selected by user.
     */
    @Override
    public void onClick(View view) {

//        Log.e("is","choosed");
        TextView courseTitleTextView =
                (TextView)view.findViewById(R.id.title_courses_text_view);
        String courseTitle = courseTitleTextView.getText().toString();

        if(uiListener != null)
            uiListener.setCourseTitle(courseTitle);
    }

    /**
     * Inflate main courses recycle view (which contain courses slides).
     * @param parent get group parent to inflate new view.
     * @return  new object from courses slide view.
     */
    @Override
    public ViewAppHolder.ViewMainCoursesHolder newMainSlidesView(ViewGroup parent) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.recycle_views,parent,false);

        return new ViewAppHolder.ViewMainCoursesHolder(view);
    }

    /**
     * Set adapter to it's correct recycle view (which hold courses slide).
     * Launch cursor loader to get correct data from database .
     * @param viewMainCoursesHolder current recycle view .
     * @param position recycle view position in recycle view adapter
     */
    @Override
    public void bindMainSlidesView(
            ViewAppHolder.ViewMainCoursesHolder viewMainCoursesHolder,
            int position) {

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);


        viewMainCoursesHolder.mainRecyclerViews.setLayoutManager(linearLayoutManager);
        mainRecyclerViews.setItemAnimator(new SlideInUpAnimator());
        viewMainCoursesHolder.coursesTitleImageView
                .setText(getTextFromPosition(position));

        // Assign correct adapter for current recycle view by it's position.
        // This method set adapter of all patterns sorted courses .
        setCurrentAdapter(position,viewMainCoursesHolder.mainRecyclerViews);
        // Loader is launched with specific id equals to it's position in adapter .


        new FetchDatabaseLoader(this).start(position);
    }

    String getTextFromPosition(int position){
        switch (position){
            case UiHelper.LOADER_CURSOR_ALL_COURSES:
                return UiHelper.ALL_COURSES;
            case UiHelper.LOADER_CURSOR_NEW_RELEASE_COURSES :
                return UiHelper.NEW_RELEASES;
            case UiHelper.LOADER_CURSOR_ANDROID_COURSES :
                return UiHelper.ANDROID_COURSES;
            case UiHelper.LOADER_CURSOR_DATA_SCIENCE_COURSES :
                return UiHelper.DATA_SCIENCE_COURSES;
            case UiHelper.LOADER_CURSOR_SOFTWARE_ENGINEERING:
                return UiHelper.SOFTWARE_ENGINEERING_COURSES;
            case UiHelper.LOADER_CURSOR_IOS_COURSES:
                return UiHelper.IOS_COURSES;
            case UiHelper.LOADER_CURSOR_NON_TOUCH_COURSES:
                return UiHelper.NON_TECH_COURSES;
            case UiHelper.LOADER_CURSOR_WEB_DEVELOPMENT_COURSES:
                return UiHelper.WEB_DEVELOPMENT_COURSES;
            case UiHelper.LOADER_CURSOR_GEORGIA_TECH_MASTERS_COURSES:
                return UiHelper.GEORGIA_TECH_MASTERS_COURSES;
        }
        return null;
    }

    /**
     *  Launched from bindMainSlidesView to set adapter to specific recycleView (courses slides).
     *  Both recycle views position in mainRecycleView and their loader id are equal so
     *  recycle view position to determine which loader is finished and set its correct adapter
     *  for it.
     * @param recycleViewId position of recycle view which finished in loader
     * @param recyclerView recycle view which finished
     */
    private void setCurrentAdapter(int recycleViewId, RecyclerView recyclerView){

        switch (recycleViewId) {
            case UiHelper.LOADER_CURSOR_ALL_COURSES:
                recyclerView.setAdapter(coursesAdapterALLCourses);
                break;

            case UiHelper.LOADER_CURSOR_NEW_RELEASE_COURSES:
                recyclerView.setAdapter(coursesAdapterNewReleaseCourses);
                break;

            case UiHelper.LOADER_CURSOR_ANDROID_COURSES:
                recyclerView.setAdapter(coursesAdapterAndroidCourses);
                break;

            case UiHelper.LOADER_CURSOR_DATA_SCIENCE_COURSES:
                recyclerView.setAdapter(coursesAdapterDataScienceCourses);
                break;

            case UiHelper.LOADER_CURSOR_SOFTWARE_ENGINEERING:
                recyclerView.setAdapter(coursesAdapterSoftwareEngineering);
                break;

            case UiHelper.LOADER_CURSOR_IOS_COURSES:
                recyclerView.setAdapter(coursesAdapterIOS);
                break;

            case UiHelper.LOADER_CURSOR_NON_TOUCH_COURSES:
                recyclerView.setAdapter(coursesAdapterNonTouch);
                break;

            case UiHelper.LOADER_CURSOR_WEB_DEVELOPMENT_COURSES:
                recyclerView.setAdapter(coursesAdapterWebDevelopment);
                break;

            case UiHelper.LOADER_CURSOR_GEORGIA_TECH_MASTERS_COURSES:
                recyclerView.setAdapter(coursesAdapterGeorgiaTechMasters);
                break;
        }
    }

}
