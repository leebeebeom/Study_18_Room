package com.beebeom.a18_room;

import Room.AppDatabase;

public class UpdateTask {
    private AppDatabase db;
    private int id;
    private String title;
    private String contents;

    public UpdateTask(AppDatabase db, int id, String title, String contents) {
        this.db = db;
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public void doUpdate() {
        new Thread(() -> {
            db.memoDao().update(id, title, contents);
        }).start();
    }
}
