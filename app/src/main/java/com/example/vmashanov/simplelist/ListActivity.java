package com.example.vmashanov.simplelist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DBHelper dbHelper;
    private ItemAdapter adapter;
    private long parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        setParentId(intent.getLongExtra("parentId", 0));

        dbHelper = new DBHelper(this);

        ListView list_view = findViewById(R.id.ListView);

        setAdapter(new ItemAdapter(this, false, LoadList(dbHelper)));

        list_view.setAdapter(getAdapter());

        list_view.setOnItemLongClickListener(selectListItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_item:
                Intent new_item = new Intent(this, AddListItemActivity.class);
                new_item.putExtra("parentId", getParentId());
                startActivity(new_item);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Создает экземпляр класса AdapterView.OnItemClickListener для переопределения обработчика события onItemClick
     * пункта списка
     * */
    private AdapterView.OnItemLongClickListener selectListItem = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View itemClicked, int position, long id) {
            ItemAdapter itemAdapter = getAdapter();
            itemAdapter.remove(position);
            itemAdapter.notifyDataSetChanged();
            return true;
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainListActivity.class);
        startActivity(intent);
        finish();
    }

    private List<Item> LoadList(DBHelper dbHelper) {
        List<Item> rootList = new ArrayList<Item>();
        String[] selectArg = {getParentIdAsString()};
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_NAME,
                null,
                "parent = ?",
                selectArg,
                null,
                null,
                null
        );

        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
        int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
        int parentIndex = cursor.getColumnIndex(DBHelper.KEY_PARENT);
        int doneIndex = cursor.getColumnIndex(DBHelper.KEY_DONE);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(idIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                long parent = cursor.getLong(parentIndex);
                int done = cursor.getInt(doneIndex);

                boolean doneBool = (done == 1);

                Item item = new Item(id, title, description, parent, doneBool);
                rootList.add(item);
            } while (cursor.moveToNext());
        } else {
            Log.d("DB read debug: ", "Таблица пустая");
            cursor.close();
        }

        return rootList;
    }

    private void setAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
    }

    private ItemAdapter getAdapter() {
        return  adapter;
    }

    private long getParentId() {
        return parentId;
    }

    private String getParentIdAsString() {
        return Long.toString(parentId);
    }

    private void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
