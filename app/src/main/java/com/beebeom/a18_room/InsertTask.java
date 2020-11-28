package com.beebeom.a18_room;

import Room.AppDatabase;
import Room.Memo;

public class InsertTask {
    private AppDatabase db;
    private String title;
    private String contents;

    public InsertTask(AppDatabase db, String title, String contents) {
        this.db = db;
        this.title = title;
        this.contents = contents;
    }

    public void doInsert() {
        new Thread(() -> {
            db.memoDao().Insert(new Memo(title, contents));
        }).start();
    }
}
