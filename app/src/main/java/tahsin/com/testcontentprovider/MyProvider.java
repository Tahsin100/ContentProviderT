package tahsin.com.testcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Tahsin Rahman
 * on 15,October,2018
 */
public class MyProvider extends ContentProvider {

    private SqliteDatabaseManager dbManager;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int MOVIE_LIST = 1;
    private static final int FIND_MOVIE_BY_ID = 2;

    static {

        uriMatcher.addURI(MovieDB.AUTHORITY, MovieDB.PATH, 1);
        uriMatcher.addURI(MovieDB.AUTHORITY, MovieDB.PATH + "/#", 2);

    }

    @Override
    public boolean onCreate() {
        dbManager = new SqliteDatabaseManager(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = dbManager.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case MOVIE_LIST:
                cursor = db.query(MovieDB.MovieList.TABLE_NAME, projection, selection, selectionArgs ,null, null, null );
                break;
            case FIND_MOVIE_BY_ID:
                selection = MovieDB.MovieList.ID + " = " + uri.getLastPathSegment();
                cursor = db.query(MovieDB.MovieList.TABLE_NAME, projection, selection, selectionArgs ,null, null, null, null);
                break;
            default:
                Toast.makeText(getContext(), "Invalid uri", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("Unknown uri :" + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case MOVIE_LIST:
                return MovieDB.MOVIE_LIST;
            case FIND_MOVIE_BY_ID:
                return MovieDB.MOVIE_ITEM;
            default:
               throw new IllegalArgumentException("Unknown uri :" + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues initialValues) {

        if(uriMatcher.match(uri) != MOVIE_LIST) throw new IllegalArgumentException("Invalid uri : " + uri);
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        }
        else {
            values = new ContentValues();
        }

        long rowId = db.insert(MovieDB.MovieList.TABLE_NAME, null, values);

        if(rowId > 0){
            Uri movieUri = ContentUris.withAppendedId(MovieDB.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(movieUri, null);
            return movieUri;
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String initialSelection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = dbManager.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)){
            case MOVIE_LIST:
                count = db.delete(MovieDB.MovieList.TABLE_NAME, initialSelection, selectionArgs);
                break;
            case FIND_MOVIE_BY_ID:
                String rowId = uri.getPathSegments().get(1);
                selectionArgs = new String[] {TextUtils.isEmpty(initialSelection) ? " AND ( " + initialSelection + ")" : "" };
                String selection = MovieDB.MovieList.ID + " = " + rowId + "?";
                count = db.delete(MovieDB.MovieList.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed to delete from " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues initialValues, @Nullable String initialSelection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = dbManager.getWritableDatabase();
        int count = 0;

        ContentValues values;
        if(initialValues != null){
            values = new ContentValues(initialValues);
        }
        else{
            values = new ContentValues();
        }

        switch (uriMatcher.match(uri)){
            case MOVIE_LIST:
                count = db.update(MovieDB.MovieList.TABLE_NAME, values, initialSelection, selectionArgs);
                break;
            case FIND_MOVIE_BY_ID:
                String rowId = uri.getPathSegments().get(1);
                selectionArgs = new String[] {TextUtils.isEmpty(initialSelection) ? " AND ( " + initialSelection + ")" : "" };
                String selection = MovieDB.MovieList.ID + " = " + rowId + "?";
                count = db.update(MovieDB.MovieList.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed to update from " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
