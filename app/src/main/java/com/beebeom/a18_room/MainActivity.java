package com.beebeom.a18_room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Room.AppDatabase;
import Room.Memo;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db 객체 초기화
        AppDatabase db = AppDatabase.getInstance(this);

        //fab Click -> MemoActivity
        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoActivity.class);
            startActivity(intent);
        });


        ListView listView = findViewById(R.id.listView);
        //라이브 데이터 이용
        db.memoDao().getAll().observe(this, memos -> {
            mAdapter = new MyAdapter();
            mAdapter.setItems(memos);
            listView.setAdapter(mAdapter);
        });

        //ListItem Click
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //get Clicked Item
            Memo memo = (Memo) mAdapter.getItem(position);
            Intent intent = new Intent(this, MemoActivity.class);
            intent.putExtra("id", memo.getId());
            intent.putExtra("title", memo.getTitle());
            intent.putExtra("contents", memo.getContents());
            startActivity(intent);
        });
        //롱 클릭시 삭제 다이얼로그 출력
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Memo memo = (Memo) mAdapter.getItem(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("삭제 확인");
            builder.setMessage("삭제 하시겠습니까?");
            builder.setPositiveButton("넹", (dialog, which) -> {
                DeleteTask deleteTask = new DeleteTask(db);
                deleteTask.doDelete(memo);
            });
            builder.setNegativeButton("아니용", null);
            builder.show();
            return true;
        });

    }

    private static class MyAdapter extends BaseAdapter {
        private List<Memo> mItems = new ArrayList<>();

        public void setItems(List<Memo> items) {
            mItems = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MYVH viewHolder = new MYVH();
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder.title = convertView.findViewById(android.R.id.text1);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MYVH) convertView.getTag();
            }
            Memo memo = mItems.get(position);
            viewHolder.title.setText(memo.getTitle());


            return convertView;
        }

        private static class MYVH {
            TextView title;
        }
    }

}