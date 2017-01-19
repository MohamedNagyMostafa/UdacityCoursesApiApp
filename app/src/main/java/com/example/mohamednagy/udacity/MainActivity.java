package com.example.mohamednagy.udacity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.example.mohamednagy.udacity.Ui.UiHelper;
import com.example.mohamednagy.udacity.Ui.UiListener;

public class MainActivity extends AppCompatActivity
    implements UiListener{

    private static final String FRAGMENT_TAG = "fragment";
    private boolean twoPane = false;

    @Override
    protected void onResume() {
        super.onResume();
        MainActivityFragment mainActivityFragment =(MainActivityFragment)
                getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        mainActivityFragment.setUiListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(
                            R.id.static_main_fragment,
                            new MainActivityFragment(),
                            FRAGMENT_TAG)
                    .commit();

        // Toolbar settings
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
//        toolbar.setLogo(R.drawable.udacity_logo);
        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide App name
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        twoPane = (findViewById(R.id.dynamic_detail_frame) != null);

    }

    @Override
    public void setCourseTitle(String courseTitle) {
        if(!twoPane){
            Intent courseDetailIntent = new Intent(this,CourseDetails.class);

            courseDetailIntent.putExtra(UiHelper.COURSE_EXTRA,courseTitle);

            startActivity(courseDetailIntent);
        }else{
            CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(UiHelper.COURSE_EXTRA,courseTitle);
            courseDetailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.dynamic_detail_frame,courseDetailsFragment).commit();
        }
    }
}
