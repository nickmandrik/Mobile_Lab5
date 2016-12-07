package mandrik.nick.lastfm;

public class Track {
    String name;
    String artistName;
    int listenersCount;
    int playCount;
    public Track(){
    }
    public Track(String name, String artistName, int listenersCount, int playCount){
        this.name = name;
        this.artistName = artistName;
        this.listenersCount = listenersCount;
        this.playCount = playCount;
    }
}