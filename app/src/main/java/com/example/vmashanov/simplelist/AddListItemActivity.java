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

public class AddListItemActivity extends AppCompatActivity {

    DBHelper dbHelper;

    private long parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_item);

        Intent intent = getIntent();

        setParentId(intent.getLongExtra("parentId", 0));

        dbHelper = new DBHelper(this);

        Button saveListItemBtn = findViewById(R.id.saveListItem);
        saveListItemBtn.setOnClickListener(saveListItemListener);
    }

    private Button.OnClickListener saveListItemListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (saveListItem()) {
                backToRootList();
            } else {
                Log.d("--- DB INSERTING : ", "error =(");
            }
        }
    };

    private void backToRootList() {
        Intent list_activity = new Intent(this, ListActivity.class);
        list_activity.putExtra("parentId", getParentId());
        startActivity(list_activity);
    }

    private boolean saveListItem() {
        EditText title = findViewById(R.id.TitleField);
        EditText description = findViewById(R.id.DescriptionField);

        String titleValue = title.getText().toString();
        String descriptionValue = description.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, titleValue);
        contentValues.put(DBHelper.KEY_DESCRIPTION, descriptionValue);
        contentValues.put(DBHelper.KEY_PARENT, getParentId());

        try {
            db.insert(DBHelper.TABLE_NAME, null, contentValues);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    private long getParentId() {
        return parentId;
    }

    private void setParentId(long parent) {
        this.parentId = parent;
    }
}
