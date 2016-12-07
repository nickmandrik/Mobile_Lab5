package mandrik.nick.lastfm;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvMain;
    EditText etArtistName;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        lvMain = (ListView) findViewById(R.id.lvMain);
        etArtistName = (EditText) findViewById(R.id.etArtistName);

        if (isConnected()) {
            LoadTask loadTask = new LoadTask(this, this);
            loadTask.execute("Frank Sinatra", "Bing Crospy", "Nat King Cole");
        } else {
            Toast.makeText(this, "Check internet connection.", Toast.LENGTH_LONG).show();
        }
    }
    public void onSearch(View view) {
        String artistName = etArtistName.getText().toString();
        updateList(artistName);
    }
    public void updateList(String artistName) {
        TrackAdapter  trackAdapter;

        // создаем объект для создания и управления версиями БД
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c;
        if(artistName.length() == 0)
            c = db.query(DBHelper.tableTracksName, null, null, null, null, null, null);
        else
            c = db.query(DBHelper.tableTracksName, null, "lower(" + DBHelper.artistNameColumnName
                    + ") = ?", new String[]{artistName.toLowerCase()}, null, null, null, null);

        ArrayList<Track> tracks = new ArrayList<>();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int trackNameColumnIndex = c.getColumnIndex(DBHelper.trackNameColumnName);
            int artistNameColumnIndex = c.getColumnIndex(DBHelper.artistNameColumnName);
            int listenersCountColumnIndex = c.getColumnIndex(DBHelper.listenersCountColumnName);
            int playCountColumnIndex = c.getColumnIndex(DBHelper.playCountColumnName);

            do {
                // получаем значения по номерам столбцов
                Track track = new Track(c.getString(trackNameColumnIndex), c.getString(artistNameColumnIndex),
                        c.getInt(listenersCountColumnIndex), c.getInt(playCountColumnIndex));
                tracks.add(track);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
        c.close();
        if(tracks.size() == 0){
            LoadTask loadTask = new LoadTask(this, this);
            loadTask.execute(artistName);
        }
        else {
            trackAdapter = new TrackAdapter(this, tracks);
            lvMain.setAdapter(trackAdapter);
        }
    }
    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
