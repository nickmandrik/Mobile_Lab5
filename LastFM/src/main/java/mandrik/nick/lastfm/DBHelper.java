package mandrik.nick.lastfm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String dnName = "lastFmDB";
    public static final String tableTracksName = "tracks";
    public static final String idColumnName = "id";
    public static final String artistNameColumnName = "artist_name";
    public static final String trackNameColumnName = "track_name";
    public static final String listenersCountColumnName = "listeners_count";
    public static final String playCountColumnName = "play_count";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, dnName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table " + tableTracksName + " ("
                + "id integer primary key autoincrement, "
                + "artist_name text, "
                + "track_name text, "
                + "listeners_count text, "
                + "play_count text);");

        // заполняем данными
       /* db.execSQL("insert into regions (regionCode, regionName, population, areaName) " +
                "values(162, 'Брест', 340122, 'Брестская обл.');");*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
    }
}
