package tahsin.com.testcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tahsin Rahman
 * on 15,October,2018
 */

public class SqliteDatabaseManager extends SQLiteOpenHelper {

    public SqliteDatabaseManager(Context context) {
        super(context, MovieDB.DATABASE_NAME, null, MovieDB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieDB.MovieList.TABLE_NAME + "( "
                                    + MovieDB.MovieList.ID + "INTEGER PRIMARY KEY, "
                                    + MovieDB.MovieList.NAME + " TEXT,"
                                    + MovieDB.MovieList.RELEASE_YEAR + " TEXT)";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDB.MovieList.TABLE_NAME);
        onCreate(db);
    }
}
