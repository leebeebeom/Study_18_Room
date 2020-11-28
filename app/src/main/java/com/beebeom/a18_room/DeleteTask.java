package com.beebeom.a18_room;

import Room.AppDatabase;
import Room.Memo;

public class DeleteTask {
    private AppDatabase db;

    public DeleteTask(AppDatabase db) {
        this.db = db;
    }

    public void doDelete(Memo memo) {
        new Thread(() -> {
            db.memoDao().delete(memo);
        }).start();
    }
}
