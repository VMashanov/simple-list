package com.example.vmashanov.simplelist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainListActivity extends AppCompatActivity {

    DBHelper dbHelper;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        dbHelper = new DBHelper(this);

        ListView root_list_view = findViewById(R.id.RootListView);

        setAdapter(new ItemAdapter(this, true, LoadRootList(dbHelper)));

        root_list_view.setAdapter(getAdapter());

        root_list_view.setOnItemClickListener(selectRootListItem);

        Button addRootListItemBtn = findViewById(R.id.AddRootListItem);
        addRootListItemBtn.setOnClickListener(addRootListItemBtnClick);
    }

    /**
     * Создает экземпляр класса View.OnClickListener для переопределения обработчика события onClick
     * кнопки "Добавить"
     * */
    private View.OnClickListener addRootListItemBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavigateToAddActivity(v);
        }
    };

    /**
     * Создает экземпляр класса AdapterView.OnItemClickListener для переопределения обработчика события onItemClick
     * пункта списка
     * */
    private AdapterView.OnItemClickListener selectRootListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
            ItemAdapter itemAdapter = getAdapter();
            long itemId = itemAdapter.getItemId(position);
            openList(itemId);
        }
    };

    private void NavigateToAddActivity(View v) {
        switch (v.getId()) {
            case R.id.AddRootListItem:
                Intent new_root_item = new Intent(this, AddRootListItemActivity.class);
                startActivity(new_root_item);
                break;
            default:
                break;
        }
    }

    private void openList(long parentId) {
        Intent list = new Intent(this, ListActivity.class);
        list.putExtra("parentId", parentId);
        startActivity(list);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    private List<Item> LoadRootList(DBHelper dbHelper) {
        List<Item> rootList = new ArrayList<Item>();
        String[] selectArg = {""};
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
}
