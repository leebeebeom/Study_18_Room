package com.beebeom.a18_room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Room.AppDatabase;

public class MemoActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContents;
    private int mID = -1;
    private AppDatabase mDB;
    private String mTitle;
    private String mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        mDB = AppDatabase.getInstance(this);
        mEtTitle = findViewById(R.id.et_title);
        mEtContents = findViewById(R.id.et_contents);

        //기존 아이템 클릭 시 메모 표시
        Intent intent = getIntent();
        mID = intent.getIntExtra("id", -1);
        mTitle = intent.getStringExtra("title");
        mContents = intent.getStringExtra("contents");
        mEtTitle.setText(mTitle);
        mEtContents.setText(mContents);
    }

    @Override
    public void onBackPressed() {
        String title = mEtTitle.getText().toString();
        String contents = mEtContents.getText().toString();
        //최초 생성
        if (mID == -1) {
            //제목, 내용 안 비어있으면 저장
            if (!title.isEmpty() || !contents.isEmpty()) {
                InsertTask insertTask = new InsertTask(mDB, title, contents);
                insertTask.doInsert();
                Toast.makeText(this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        //기존 메모 클릭시
        else {
            //기존 메모와 다르면 수정
            if (!mTitle.equals(title) || !mContents.equals(contents)) {
                UpdateTask updateTask = new UpdateTask(mDB, mID, title, contents);
                updateTask.doUpdate();
                Toast.makeText(this, "수정 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}