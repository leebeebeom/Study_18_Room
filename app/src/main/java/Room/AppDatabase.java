package Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Memo.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MemoDao memoDao();
    private static AppDatabase sInstance;

    public synchronized static AppDatabase getInstance(Context context){
        if(sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "memo_db").build();
        }
        return sInstance;
    }
}
