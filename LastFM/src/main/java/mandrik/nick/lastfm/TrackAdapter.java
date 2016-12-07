package mandrik.nick.lastfm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class TrackAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Track> objects;

    public TrackAdapter(Context context, ArrayList<Track> tracks) {
        ctx = context;
        objects = tracks;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.row_layout, parent, false);
        }

        Track t = getTrack(position);

        ((TextView) view.findViewById(R.id.artistName)).setText(t.artistName);
        ((TextView) view.findViewById(R.id.trackName)).setText(t.name);
        ((TextView) view.findViewById(R.id.listenersCount)).setText("Listeners: " + t.listenersCount);
        ((TextView) view.findViewById(R.id.playCount)).setText("Play count: " + t.playCount);

        return view;
    }

    Track getTrack(int position) {
        return ((Track) getItem(position));
    }

    ArrayList<Track> getAll() {
        return objects;
    }
}