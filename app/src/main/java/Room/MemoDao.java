package Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MemoDao {
    @Query("SELECT * FROM Memo")
    LiveData<List<Memo>> getAll();

    @Insert
    void Insert(Memo memo);

    @Query("UPDATE Memo SET title = :title, contents = :contents WHERE id = :id")
    void update(int id, String title, String contents);

    @Delete
    void delete(Memo memo);

}
