package com.example.vmashanov.simplelist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        dbHelper = new DBHelper(this);

        ListView root_list_view = findViewById(R.id.RootListView);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.root_list_item, LoadRootList(dbHelper));

        root_list_view.setAdapter(adapter);

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

    private ArrayList<String> LoadRootList(DBHelper dbHelper) {
        ArrayList<String> rootList = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);

        if (cursor.moveToFirst()) {
            do {
                rootList.add(cursor.getString(titleIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("DB read debug: ", "Таблица пустая");
            cursor.close();
        }

        return rootList;
    }
}
