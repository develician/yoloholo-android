package com.material.materialdesign2.LocalDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.material.materialdesign2.Models.Diary;
import com.material.materialdesign2.Models.Plan;

import java.util.ArrayList;

public class DBManagerDiary {
    private static final String dbName = "yolodb.db";
    private static final String tableName = "diary";
    public static final int dbVersion = 1;

    private SQLiteDatabase db;

    private OpenHelper opener;
    private Context context;

    public DBManagerDiary(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String createSQL = "CREATE TABLE " + tableName + "( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "title TEXT, content TEXT"
                    + ");";
            sqLiteDatabase.execSQL(createSQL);
            Toast.makeText(context, "DB is opened", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public void insertData(Diary diary) {
        String sql = "INSERT INTO " + tableName + " VALUES (NULL, '" + diary.getTitle() + "', '" + diary.getContent() + "');";
        db.execSQL(sql);

    }

    public Diary selectById(int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = " + id + ";";
        Cursor result = db.rawQuery(sql, null);

        if(result.moveToFirst()) {
            Diary diary = new Diary();
            diary.setId(result.getInt(0));
            diary.setTitle(result.getString(1));
            diary.setContent(result.getString(2));
            result.close();
            return diary;
        }
        result.close();
        return null;



    }

    public ArrayList<Diary> selectAll() {
        String sql = "SELECT * FROM " + tableName + " ORDER BY id DESC;";

        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        ArrayList<Diary> diaries = new ArrayList<>();

        while(!results.isAfterLast()) {
            Diary diary = new Diary();
            diary.setId(results.getInt(0));
            diary.setTitle(results.getString(1));
            diary.setContent(results.getString(2));
            diaries.add(diary);
            results.moveToNext();

        }
        results.close();
        return diaries;
    }
}
