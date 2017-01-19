package com.example.mohamednagy.udacity.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by mohamednagy on 12/1/2016.
 */
public class CoursesProvider extends ContentProvider {

    private UriMatcher uriMatcher = buildUriMatcher();
    private CoursesDbHelper coursesDbHelper;

    private static final int COURSES = 10;
    private static final int INSTRUCTORS = 11;
    private static final int AFFILIATES = 100;
    private static final int COURSES_WITH_ID = 101;
    private static final int COURSES_WITH_INSTRUCTORS = 110;
    private static final int COURSES_WITH_AFFILIATES = 111;

    private static final SQLiteQueryBuilder COURSES_JOIN_INSTRUCTORS = new SQLiteQueryBuilder();

    static {

        // courses INNER JOIN instructors ON courses.course_id = instructors.course_id
        COURSES_JOIN_INSTRUCTORS.setTables(
                CoursesContract.CoursesTable.TABLE_NAME + " INNER JOIN " +
                        CoursesContract.InstructorsTable.TABLE_NAME + " ON " +
                        CoursesContract.CoursesTable.TABLE_NAME + "." +
                        CoursesContract.CoursesTable.COURSE_ID_COLUMN + " = " +
                        CoursesContract.InstructorsTable.TABLE_NAME + "." +
                        CoursesContract.InstructorsTable.COURSE_ID_COLUMN
        );

    }

    private static final SQLiteQueryBuilder COURSES_JOIN_AFFILIATES = new SQLiteQueryBuilder();

    static {
        // courses INNER JOIN affiliates ON courses.course_id = affiliates.course_id
        COURSES_JOIN_AFFILIATES.setTables(
                CoursesContract.CoursesTable.TABLE_NAME + " INNER JOIN " +
                        CoursesContract.AffiliatesTable.TABLE_NAME + " ON " +
                        CoursesContract.CoursesTable.TABLE_NAME + "." +
                        CoursesContract.CoursesTable.COURSE_ID_COLUMN + " = " +
                        CoursesContract.AffiliatesTable.TABLE_NAME + "." +
                        CoursesContract.AffiliatesTable.COURSE_ID_COLUMN
        );
    }

    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String COURSES_PATH =
                CoursesContract.CoursesTable.TABLE_NAME ;

        final String COURSES_WITH_ID_PATH =
                CoursesContract.CoursesTable.TABLE_NAME + "/#";

        final String INSTRUCTORS_PATH =
                CoursesContract.InstructorsTable.TABLE_NAME;

        final String AFFILIATES_PATH =
                CoursesContract.AffiliatesTable.TABLE_NAME;


        // Specific format for course's instructors
        // courses/instructors/#
        final String COURSES_WITH_INSTRUCTORS_PATH =
                CoursesContract.CoursesTable.TABLE_NAME + "/" +
                        CoursesContract.InstructorsTable.TABLE_NAME + "/#";

        // Specific format for course's affiliates
        // courses/affiliates/#
        final String COURSES_WITH_AFFILIATES_PATH =
                CoursesContract.CoursesTable.TABLE_NAME + "/" +
                        CoursesContract.AffiliatesTable.TABLE_NAME + "/#";

        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY,
                COURSES_PATH,COURSES);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY,
                INSTRUCTORS_PATH,INSTRUCTORS);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY,
                AFFILIATES_PATH,AFFILIATES);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY,
                COURSES_WITH_ID_PATH,COURSES_WITH_ID);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY,
                COURSES_WITH_INSTRUCTORS_PATH, COURSES_WITH_INSTRUCTORS);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY
                ,COURSES_WITH_AFFILIATES_PATH, COURSES_WITH_AFFILIATES);


        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        coursesDbHelper = new CoursesDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int match = uriMatcher.match(uri);

        switch (match){

            case COURSES :
                return coursesDbHelper.getReadableDatabase().query(
                        CoursesContract.CoursesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case AFFILIATES :
                return coursesDbHelper.getReadableDatabase().query(
                        CoursesContract.AffiliatesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case INSTRUCTORS :
                return coursesDbHelper.getReadableDatabase().query(
                        CoursesContract.InstructorsTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case COURSES_WITH_ID :
                return getCoursesWithId(uri,projection,sortOrder);

            case COURSES_WITH_INSTRUCTORS :
                return getCoursesWithInstructors(uri,projection,sortOrder);

            case COURSES_WITH_AFFILIATES :
                return getCoursesWithAffiliates(uri,projection,sortOrder);
        }

        return null;
    }

    // Get Specific course from uri ( courses/# )
    private Cursor getCoursesWithId(
            Uri uri,String[] projection,String sortOrder){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.CoursesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getReadableDatabase().query(
                CoursesContract.CoursesTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }

    // Get instructors for specific course from uri ( courses/instructors/# )
    private Cursor getCoursesWithInstructors(Uri uri, String[] projection, String sortOrder){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.InstructorsTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return COURSES_JOIN_INSTRUCTORS.query(
                coursesDbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }

    // Get affiliates for specific course from uri ( courses/affiliates/# )
    private Cursor getCoursesWithAffiliates(Uri uri,String[] projection,String sortOrder){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.AffiliatesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return COURSES_JOIN_AFFILIATES.query(
                coursesDbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int matcher = uriMatcher.match(uri);

        switch (matcher){
            case COURSES :
                return CoursesContract.CoursesTable.CONTENT_TYPE;
            case INSTRUCTORS :
                return CoursesContract.InstructorsTable.CONTENT_TYPE;
            case AFFILIATES :
                return CoursesContract.AffiliatesTable.CONTENT_TYPE;
            case COURSES_WITH_ID :
                return CoursesContract.CoursesTable.CONTENT_ITEM_TYPE;
            case COURSES_WITH_INSTRUCTORS :
                return CoursesContract.InstructorsTable.CONTENT_ITEM_TYPE;
            case COURSES_WITH_AFFILIATES :
                return CoursesContract.AffiliatesTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int matcher = uriMatcher.match(uri);
        long columnId;
        switch (matcher){

            case COURSES :
                 columnId = coursesDbHelper.getWritableDatabase().insert(
                        CoursesContract.CoursesTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;
            case INSTRUCTORS :
                columnId = coursesDbHelper.getWritableDatabase().insert(
                        CoursesContract.InstructorsTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;
            case AFFILIATES :
                columnId = coursesDbHelper.getWritableDatabase().insert(
                        CoursesContract.AffiliatesTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        return ContentUris.withAppendedId(uri,columnId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int matcher = uriMatcher.match(uri);

        switch (matcher){

            case COURSES :
                return coursesDbHelper.getWritableDatabase().delete(
                        CoursesContract.CoursesTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case INSTRUCTORS :
                return coursesDbHelper.getWritableDatabase().delete(
                        CoursesContract.InstructorsTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case AFFILIATES :
                return coursesDbHelper.getWritableDatabase().delete(
                        CoursesContract.AffiliatesTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case COURSES_WITH_ID :
                return deleteCourseWithId(uri);

            case COURSES_WITH_INSTRUCTORS :
                return deleteCourseWithInstructors(uri);

            case COURSES_WITH_AFFILIATES :
                return deleteCourseWithAffiliates(uri);

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    private int deleteCourseWithId(Uri uri){
        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.CoursesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getWritableDatabase().delete(
                CoursesContract.CoursesTable.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    private int deleteCourseWithInstructors(Uri uri){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.InstructorsTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getWritableDatabase().delete(
                CoursesContract.InstructorsTable.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    private int deleteCourseWithAffiliates(Uri uri){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.AffiliatesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getWritableDatabase().delete(
                CoursesContract.AffiliatesTable.TABLE_NAME,
                selection,
                selectionArgs
        );
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        int matcher = uriMatcher.match(uri);

        switch (matcher){

            case COURSES :
                return coursesDbHelper.getWritableDatabase().update(
                        CoursesContract.CoursesTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case INSTRUCTORS :
                return coursesDbHelper.getWritableDatabase().update(
                        CoursesContract.InstructorsTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case AFFILIATES :
                return coursesDbHelper.getWritableDatabase().update(
                        CoursesContract.AffiliatesTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case COURSES_WITH_ID :
                return updateCourseWithId(uri,contentValues);

            case COURSES_WITH_INSTRUCTORS :
                return updateCourseWithInstructors(uri,contentValues);

            case COURSES_WITH_AFFILIATES :
                return updateCourseWithAffiliates(uri,contentValues);

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    private int updateCourseWithId(Uri uri,ContentValues contentValues){
        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.CoursesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getWritableDatabase().update(
                CoursesContract.CoursesTable.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        );
    }

    private int updateCourseWithInstructors(Uri uri,ContentValues contentValues){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.InstructorsTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getWritableDatabase().update(
                CoursesContract.InstructorsTable.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        );
    }

    private int updateCourseWithAffiliates(Uri uri,ContentValues contentValues){

        long id = CoursesContract.CoursesTable.getIdFromUri(uri);

        String selection = CoursesContract.AffiliatesTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return coursesDbHelper.getWritableDatabase().update(
                CoursesContract.AffiliatesTable.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        );
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int counter = 0;
        int matcher = uriMatcher.match(uri);
        long columnId;
        switch (matcher){

            case COURSES :
                for(ContentValues contentValues : values) {
                    coursesDbHelper.getWritableDatabase().insert(
                            CoursesContract.CoursesTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                    counter++;
                }
                break;
            case INSTRUCTORS :
                for(ContentValues contentValues : values) {
                    coursesDbHelper.getWritableDatabase().insert(
                            CoursesContract.InstructorsTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                    counter++;
                }
                break;
            case AFFILIATES :
                for(ContentValues contentValues : values) {
                    coursesDbHelper.getWritableDatabase().insert(
                            CoursesContract.AffiliatesTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                    counter++;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        return counter;
    }
}
