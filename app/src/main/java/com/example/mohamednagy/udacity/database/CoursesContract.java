package com.example.mohamednagy.udacity.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mohamednagy on 12/1/2016.
 */
public class CoursesContract {

    public static final String CONTENT_AUTHORITY = "com.example.mohamednagy.udacity";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String AUTO_INCREMENT = " AUTOINCREMENT";

    private static final String NOT_NULL = " NOT_NULL";

    private static final String INTEGER = " INTEGER";

    private static final String TEXT = " TEXT";

    private static final String UNIQUE = " UNIQUE";

    private static final String ON_CONFLICT_REPLACE = " ON CONFLICT REPLACE";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String REFERENCES = " REFERENCES ";

    private static final String FOREIGN_KEY = " FOREIGN KEY";

    public static class CoursesTable implements BaseColumns{

        public static final String TABLE_NAME = "courses";

        public static final Uri CONTENT_URI =BASE_CONTENT_URI
                .buildUpon().appendPath(TABLE_NAME).build();

        public static final String COURSE_ID_COLUMN = "course_id";

        public static final String SUBTITLE_COLUMN = "subtitle";

        public static final String COURSE_KEY_COLUMN = "key";

        public static final String COURSE_IMAGE_COLUMN = "course_image";

        public static final String EXPECTED_LEARNING_COLUMN = "expected_learning";

        public static final String YOUTUBE_VIDEO_COLUMN = "youtube_url";

        public static final String TITLE_COLUMN = "title";

        public static final String REQUIRED_KNOWLEDGE_COLUMN = "required_knowledge";

        public static final String SYLLABUS_COLUMN = "syllabus";

        public static final String NEW_RELEASE_COLUMN = "new_release";

        public static final String HOME_PAGE_COLUMN = "homepage";

        public static final String SHORT_SUMMARY_COLUMN = "short_summary";

        public static final String COURSE_LEVEL_COLUMN = "level";

        public static final String EXPECTED_DURATION_UNIT_COLUMN = "expected_duration_unit";

        public static final String SUMMARY_COLUMN = "summary";

        public static final String EXPECTED_DURATION_COLUMN = "expected_duration";

        public static final String TRACKS_COLUMN = "tracks";

        public static final String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                _ID + INTEGER + PRIMARY_KEY  + ","+
                COURSE_ID_COLUMN + INTEGER + NOT_NULL + "," +
                SUBTITLE_COLUMN + TEXT  + "," +
                COURSE_KEY_COLUMN + TEXT  + "," +
                COURSE_IMAGE_COLUMN + TEXT  + "," +
                EXPECTED_LEARNING_COLUMN + TEXT  + "," +
                YOUTUBE_VIDEO_COLUMN + TEXT  + "," +
                TITLE_COLUMN + TEXT  + "," +
                REQUIRED_KNOWLEDGE_COLUMN + TEXT  + "," +
                SYLLABUS_COLUMN + TEXT  + "," +
                NEW_RELEASE_COLUMN + INTEGER  + "," +
                HOME_PAGE_COLUMN + TEXT  + "," +
                SHORT_SUMMARY_COLUMN + TEXT  + "," +
                COURSE_LEVEL_COLUMN + TEXT  + "," +
                EXPECTED_DURATION_UNIT_COLUMN + TEXT  + "," +
                SUMMARY_COLUMN + TEXT  + "," +
                EXPECTED_DURATION_COLUMN + INTEGER  +","+
                TRACKS_COLUMN + TEXT  + "," +
                UNIQUE + "(" + COURSE_KEY_COLUMN +")" + ON_CONFLICT_REPLACE + ");";

        public static final int IS_NEW_RELEASE = 1;
        public static final int NOT_NEW_RELEASE = 0;

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static int isNewRelease(boolean newRelease){
            return (newRelease)?IS_NEW_RELEASE:NOT_NEW_RELEASE;
        }
        public static long getIdFromUri(Uri uri){
            return ContentUris.parseId(uri);
        }
    }

    public static class InstructorsTable implements  BaseColumns{

        public static final String TABLE_NAME = "instructors";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String BIO_COLUMN = "bio";

        public static final String IMAGE_COLUMN = "image";

        public static final String NAME_COLUMN = "name";

        public static final String COURSE_ID_COLUMN = CoursesTable.COURSE_ID_COLUMN;

        public static final String CREATE_INSTRUCTORS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                _ID + INTEGER  + PRIMARY_KEY + "," +
                COURSE_ID_COLUMN + INTEGER + "," +
                BIO_COLUMN + TEXT + "," +
                IMAGE_COLUMN + TEXT + "," +
                NAME_COLUMN + TEXT  + "," +
                FOREIGN_KEY + "(" + COURSE_ID_COLUMN + ")"
                + REFERENCES + CoursesTable.TABLE_NAME + "("  + COURSE_ID_COLUMN + ") );";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +
                        CoursesTable.TABLE_NAME + "/" + TABLE_NAME;
    }

    public static class AffiliatesTable implements BaseColumns{

        public static final String TABLE_NAME = "affiliates";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String IMAGE_COLUMN = "image";

        public static final String NAME_COLUMN = "name";

        public static final String COURSE_ID_COLUMN = CoursesTable.COURSE_ID_COLUMN;

        public static final String CREATE_AFFILIATES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                _ID + INTEGER  + PRIMARY_KEY + "," +
                COURSE_ID_COLUMN + INTEGER + "," +
                IMAGE_COLUMN + TEXT + "," +
                NAME_COLUMN + TEXT  + "," +
                FOREIGN_KEY + "(" + COURSE_ID_COLUMN + ")"
                + REFERENCES + CoursesTable.TABLE_NAME + "("  + COURSE_ID_COLUMN + ") );";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +
                        CoursesTable.TABLE_NAME + "/" + TABLE_NAME;

    }

}


