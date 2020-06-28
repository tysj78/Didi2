package com.yangyong.guard;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CpTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_add;
    private Button bt_delete;
    private Button bt_update;
    private Button bt_query;
    private ContentResolver contentResolver;
    private Uri uri;
    private Button bt_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_test);
        initView();
        contentResolver = getContentResolver();
        uri = Uri.parse("content://com.yangyong.mycp/legion");
    }

    private void initView() {
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_query = (Button) findViewById(R.id.bt_query);

        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        bt_call = (Button) findViewById(R.id.bt_call);
        bt_call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "风不会停息");
                contentValues.put("level", 31);
                contentValues.put("power", 151563);
                contentResolver.insert(uri, contentValues);
                break;
            case R.id.bt_delete:

                break;
            case R.id.bt_update:

                break;
            case R.id.bt_query:
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int level = cursor.getInt(2);
                    int power = cursor.getInt(3);
                    Log.e("yy", "id:" + id + "==name:" + name + "==level:" + level + "==power:" + power);
                }
                if (cursor != null) {
                    cursor.close();
                }
                break;
            case R.id.bt_call:
                Bundle bundle = new Bundle();
                bundle.putString("key","setPer");
                contentResolver.call(uri,"","传过去的数据super",bundle);
                break;
        }
    }
}
