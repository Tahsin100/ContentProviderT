package tahsin.com.testcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Tahsin Rahman
 * on 15,October,2018
 */
public class MyProvider extends ContentProvider {

    private SqliteDatabaseManager dbManager;
    private static UriMatcher uriMatcher;

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
            case 1:
                db.query(MovieDB.MovieList.TABLE_NAME, projection, selection, selectionArgs ,null, null, null );
                break;
            case 2:

                break;
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
