package conect;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.DSPDao;
import dao.SongDao;
import dao.playlistDao;
import entity.DSP;
import entity.Playlist;
import entity.Song;

@Database(entities = {Song.class, DSP.class, Playlist.class}, version = 1, exportSchema = false)
public abstract class SongData extends RoomDatabase {

    private static SongData instance;

    public abstract playlistDao playlistdao();
    public abstract SongDao songDao();
    public abstract DSPDao dspdao();

    public static synchronized SongData getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            SongData.class, "music.db")
//                    .addMigrations(Migrations.MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
}
