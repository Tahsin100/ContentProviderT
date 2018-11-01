package tahsin.com.testcontentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by TAHSIN RAHMAN
 * on 15 ,OCTOBER, 2018
 */

public class MovieDB {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider.MovieList";
    public static final String PATH = "/movies";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    public static final String MOVIE_LIST = ContentResolver.CURSOR_DIR_BASE_TYPE + BuildConfig.APPLICATION_ID + ".movies";
    public static final String MOVIE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + BuildConfig.APPLICATION_ID + ".movies";
    public static final String DATABASE_NAME = "movie_db";
    public static final int DATABASE_VERSION = 1;

    public static final class MovieList implements BaseColumns {

        public MovieList() { }

        public static final String TABLE_NAME = "movies";
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String RELEASE_YEAR = "release_year";

    }

}
