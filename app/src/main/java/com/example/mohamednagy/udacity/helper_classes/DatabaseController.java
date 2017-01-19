package com.example.mohamednagy.udacity.helper_classes;

import com.example.mohamednagy.udacity.database.CoursesContract;

/**
 * Created by mohamednagy on 12/2/2016.
 */
public class DatabaseController {

    public static final String[] COURSES_PROJECTION = {
            CoursesContract.CoursesTable.TABLE_NAME + "." + CoursesContract.CoursesTable._ID,
            CoursesContract.CoursesTable.COURSE_ID_COLUMN,
            CoursesContract.CoursesTable.COURSE_LEVEL_COLUMN,
            CoursesContract.CoursesTable.COURSE_IMAGE_COLUMN,
            CoursesContract.CoursesTable.COURSE_KEY_COLUMN,
            CoursesContract.CoursesTable.EXPECTED_DURATION_COLUMN,
            CoursesContract.CoursesTable.EXPECTED_LEARNING_COLUMN,
            CoursesContract.CoursesTable.HOME_PAGE_COLUMN,
            CoursesContract.CoursesTable.NEW_RELEASE_COLUMN,
            CoursesContract.CoursesTable.REQUIRED_KNOWLEDGE_COLUMN,
            CoursesContract.CoursesTable.SHORT_SUMMARY_COLUMN,
            CoursesContract.CoursesTable.SUBTITLE_COLUMN,
            CoursesContract.CoursesTable.SUMMARY_COLUMN,
            CoursesContract.CoursesTable.SYLLABUS_COLUMN,
            CoursesContract.CoursesTable.TITLE_COLUMN,
            CoursesContract.CoursesTable.YOUTUBE_VIDEO_COLUMN,
            CoursesContract.CoursesTable.EXPECTED_DURATION_UNIT_COLUMN,
            CoursesContract.CoursesTable.TRACKS_COLUMN
    };

    public static final int COURSE_ID_COLUMN =  1;
    public static final int COURSE_LEVEL_COLUMN = 2;
    public static final int COURSE_IMAGE_COLUMN = 3;
    public static final int COURSE_KEY_COLUMN = 4;
    public static final int EXPECTED_DURATION_COLUMN = 5;
    public static final int EXPECTED_LEARNING_COLUMN = 6;
    public static final int HOME_PAGE_COLUMN = 7;
    public static final int NEW_RELEASE_COLUMN = 8;
    public static final int REQUIRED_KNOWLEDGE_COLUMN = 9;
    public static final int SHORT_SUMMARY_COLUMN = 10;
    public static final int SUBTITLE_COLUMN = 11;
    public static final int SUMMARY_COLUMN = 12;
    public static final int SYLLABUS_COLUMN = 13;
    public static final int TITLE_COLUMN = 14;
    public static final int YOUTUBE_VIDEO_COLUMN = 15;
    public static final int EXPECTED_DURATION_UNIT_COLUMN = 16;
    public static final int TRACKS_COLUMN = 17;

    public static final String[] INSTRUCTORS_PROJECTION = {
            CoursesContract.InstructorsTable.TABLE_NAME + "." + CoursesContract.InstructorsTable._ID,
            CoursesContract.InstructorsTable.NAME_COLUMN ,
            CoursesContract.InstructorsTable.COURSE_ID_COLUMN,
            CoursesContract.InstructorsTable.BIO_COLUMN,
            CoursesContract.InstructorsTable.IMAGE_COLUMN
    };

    public static final int INSTRUCTORS_NAME_COLUMN = 1;
    public static final int INSTRUCTORS_COURSE_ID_COLUMN = 2;
    public static final int INSTRUCTORS_BIO_COLUMN = 3;
    public static final int INSTRUCTORS_IMAGE_COLUMN = 4;
    public static final int INSTRUCTORS_COLUMNS_COUNT = 5;

    public static final String[] AFFILIATES_PROJECTION = {
            CoursesContract.AffiliatesTable.TABLE_NAME + "." + CoursesContract.AffiliatesTable._ID,
            CoursesContract.AffiliatesTable.COURSE_ID_COLUMN,
            CoursesContract.AffiliatesTable.NAME_COLUMN,
            CoursesContract.AffiliatesTable.IMAGE_COLUMN
    };

    public static final int AFFILIATES_COURSE_ID_COLUMN = 1;
    public static final int AFFILIATES_NAME_COLUMN = 2;
    public static final int AFFILIATES_IMAGE_COLUMN = 3;
    public static final int AFFILIATES_COLUMNS_COUNT = 4;

}
