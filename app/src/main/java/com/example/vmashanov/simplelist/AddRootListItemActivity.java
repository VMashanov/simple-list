package com.example.vmashanov.simplelist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRootListItemActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_root_list_item);

        dbHelper = new DBHelper(this);

        Button saveRootListItemBtn = findViewById(R.id.saveRootListItem);
        saveRootListItemBtn.setOnClickListener(saveListItemListener);
    }

    private Button.OnClickListener saveListItemListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (saveListItem()) {
                backToRootList();
            }
        }
    };

    private void backToRootList() {
        Intent root_activity = new Intent(this, MainListActivity.class);
        startActivity(root_activity);
    }

    private boolean saveListItem() {
        EditText title = findViewById(R.id.RootTitleField);
        String titleValue = title.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, titleValue);
        contentValues.put(DBHelper.KEY_PARENT, "");

        try {
            db.insert(DBHelper.TABLE_NAME, null, contentValues);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
