package com.example.mohamednagy.udacity.network;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.mohamednagy.udacity.Ui.UiHelper;
import com.example.mohamednagy.udacity.database.CoursesContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mohamednagy on 12/2/2016.
 */
public class FetchDataFromNetwork {

    private static final String URL_COURSES = "https://www.udacity.com/public-api/v0/courses";

    public static void FetchDataAndInsertToDatabase(Context context) throws IOException {

        URL url = null;
        try{
            url = new URL(URL_COURSES);
        }catch (MalformedURLException e){
            Log.e("error",e.toString());
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();

            String dataAsJSON = getJsonString(inputStream);

            insertCoursesDataIntoDatabase(dataAsJSON,context);

        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
    }

    private static String getJsonString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try{
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String dataAsJSON = bufferedReader.readLine();
            while(dataAsJSON != null){
                stringBuilder.append(dataAsJSON);
                dataAsJSON = bufferedReader.readLine();
            }

        }finally {
            if(inputStreamReader != null)
                inputStreamReader.close();
            if(bufferedReader != null)
                bufferedReader.close();
        }

        return stringBuilder.toString();
    }

    private static void insertCoursesDataIntoDatabase(String dataAsJSON,Context context){

        // Clear old data
        UiHelper.clearAffiliatesInstructorsTables(context);
        try {
            JSONObject jsonObject = new JSONObject(dataAsJSON);
            JSONArray courses = jsonObject.getJSONArray("courses");
            ArrayList<ContentValues> courseContentValuesArrayList = new ArrayList<>();

            for(int course = 0 ; course < courses.length() ; course++) {
                Log.e("course","is loaded " + String.valueOf(course));
                JSONObject currentCourse = courses.getJSONObject(course);
                JSONArray instructors = currentCourse.getJSONArray("instructors");

                ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
                // Get Data of instructors
                for (int i = 0; i < instructors.length(); i++) {
                    ContentValues instructorContentValues = new ContentValues();
                    final String instructorBio = instructors.getJSONObject(i).getString("bio");
                    final String instructorImage = instructors.getJSONObject(i).getString("image");
                    final String instructorName = instructors.getJSONObject(i).getString("name");

                    instructorContentValues.put(CoursesContract.InstructorsTable.BIO_COLUMN, instructorBio);
                    instructorContentValues.put(CoursesContract.InstructorsTable.IMAGE_COLUMN, instructorImage);
                    instructorContentValues.put(CoursesContract.InstructorsTable.NAME_COLUMN, instructorName);
                    instructorContentValues.put(CoursesContract.InstructorsTable.COURSE_ID_COLUMN,course);

                    contentValuesArrayList.add(instructorContentValues);
                }

                // Insert data to instructors table
                if(contentValuesArrayList.size() > 0) {
                    ContentValues[] instructorsArray = new ContentValues[contentValuesArrayList.size()];
                    contentValuesArrayList.toArray(instructorsArray);
                    context.getContentResolver().bulkInsert(
                            CoursesContract.InstructorsTable.CONTENT_URI,
                            instructorsArray
                    );
                }

                final String subtitle = currentCourse.getString("subtitle");
                final String key = currentCourse.getString("key");
                final String image = currentCourse.getString("image");
                final String expectedLearning = currentCourse.getString("expected_learning");
                final String youtubeUrl = currentCourse.getJSONObject("teaser_video").getString("youtube_url");
                final String title = currentCourse.getString("title");
                final String requiredKnowledge = currentCourse.getString("required_knowledge");
                final String syllabus = currentCourse.getString("syllabus");
                final boolean newRelease =  currentCourse.getBoolean("new_release");
                final String homePage = currentCourse.getString("homepage");
                final String tracks = currentCourse.getString("tracks");
                final String shortSummary = currentCourse.getString("short_summary");
                final String level = currentCourse.getString("level");
                final String expectedDurationUnit = currentCourse.getString("expected_duration_unit");
                final String summary = currentCourse.getString("summary");
                final int expectedDuration = currentCourse.getInt("expected_duration");

                ContentValues CoursesContentValues = new ContentValues();
                CoursesContentValues.put(CoursesContract.CoursesTable.SUBTITLE_COLUMN,subtitle);
                CoursesContentValues.put(CoursesContract.CoursesTable.COURSE_KEY_COLUMN,key);
                CoursesContentValues.put(CoursesContract.CoursesTable.COURSE_IMAGE_COLUMN,image);
                CoursesContentValues.put(CoursesContract.CoursesTable.EXPECTED_LEARNING_COLUMN,expectedLearning);
                CoursesContentValues.put(CoursesContract.CoursesTable.YOUTUBE_VIDEO_COLUMN,youtubeUrl);
                CoursesContentValues.put(CoursesContract.CoursesTable.TITLE_COLUMN,title);
                CoursesContentValues.put(CoursesContract.CoursesTable.REQUIRED_KNOWLEDGE_COLUMN,requiredKnowledge);
                CoursesContentValues.put(CoursesContract.CoursesTable.SYLLABUS_COLUMN,syllabus);
                CoursesContentValues.put(CoursesContract.CoursesTable.NEW_RELEASE_COLUMN, CoursesContract.CoursesTable.isNewRelease(newRelease));
                CoursesContentValues.put(CoursesContract.CoursesTable.HOME_PAGE_COLUMN,homePage);
                CoursesContentValues.put(CoursesContract.CoursesTable.TRACKS_COLUMN,tracks);
                CoursesContentValues.put(CoursesContract.CoursesTable.SHORT_SUMMARY_COLUMN,shortSummary);
                CoursesContentValues.put(CoursesContract.CoursesTable.COURSE_LEVEL_COLUMN,level);
                CoursesContentValues.put(CoursesContract.CoursesTable.EXPECTED_DURATION_UNIT_COLUMN,expectedDurationUnit);
                CoursesContentValues.put(CoursesContract.CoursesTable.SUMMARY_COLUMN,summary);
                CoursesContentValues.put(CoursesContract.CoursesTable.EXPECTED_DURATION_COLUMN,expectedDuration);
                CoursesContentValues.put(CoursesContract.CoursesTable.COURSE_ID_COLUMN,course);

                courseContentValuesArrayList.add(CoursesContentValues);

                // Insert affiliates data to affiliates table
                JSONArray affiliates = currentCourse.getJSONArray("affiliates");
                // Clear instructors data
                contentValuesArrayList.clear();
                for(int i = 0 ; i < affiliates.length() ; i++){
                    JSONObject affiliate = affiliates.getJSONObject(i);

                    final String affiliateImage = affiliate.getString("image");
                    final String affiliateName = affiliate.getString("name");

                    ContentValues affiliateContentValues = new ContentValues();

                    affiliateContentValues.put(CoursesContract.AffiliatesTable.COURSE_ID_COLUMN,course);
                    affiliateContentValues.put(CoursesContract.AffiliatesTable.IMAGE_COLUMN,affiliateImage);
                    affiliateContentValues.put(CoursesContract.AffiliatesTable.NAME_COLUMN,affiliateName);

                    contentValuesArrayList.add(affiliateContentValues);
                }

                if(contentValuesArrayList.size() > 0) {
                    ContentValues[] affiliatesArray = new ContentValues[contentValuesArrayList.size()];
                    contentValuesArrayList.toArray(affiliatesArray);

                    context.getContentResolver().bulkInsert(
                            CoursesContract.AffiliatesTable.CONTENT_URI,
                            affiliatesArray
                    );
                }

            }
            // Insert Remains courses data to courses table
            ContentValues[] coursesArray = new ContentValues[courseContentValuesArrayList.size()];
            courseContentValuesArrayList.toArray(coursesArray);

            context.getContentResolver().bulkInsert(
                    CoursesContract.CoursesTable.CONTENT_URI,
                    coursesArray
            );
        }catch (JSONException e){
            Log.e("error",e.toString());
        }
    }
}

