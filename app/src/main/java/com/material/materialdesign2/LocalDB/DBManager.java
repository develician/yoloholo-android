package com.material.materialdesign2.LocalDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.material.materialdesign2.Models.Plan;

import java.util.ArrayList;

public class DBManager {
    private static final String dbName = "yolodb.db";
    private static final String tableName = "plan";
    private static final String diaryTableName = "diary";
    public static final int dbVersion = 1;

    private SQLiteDatabase db;

    private OpenHelper opener;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();

    }

    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 생성된 DB가 없을 경우에 한번만 호출됨
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String createSQL = "CREATE TABLE " + tableName + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "title TEXT, dateJSONArray TEXT"
                   + ")";
            String createSQLDiary = "CREATE TABLE " + diaryTableName + "( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "title TEXT, content TEXT"
                    + ");";
            sqLiteDatabase.execSQL(createSQL);
            sqLiteDatabase.execSQL(createSQLDiary);
            Toast.makeText(context, "DB is opened", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public void insertData(Plan plan) {
        String sql = "INSERT INTO " + tableName + " VALUES (NULL, '" + plan.getTitle() + "', '" + plan.getDateJSONArray() + "');";
        db.execSQL(sql);

    }

    public ArrayList<Plan> selectAll() {
        String sql = "SELECT * FROM " + tableName + " ORDER BY id DESC;";

        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        ArrayList<Plan> plans = new ArrayList<>();

        while(!results.isAfterLast()) {
            Plan plan = new Plan();
            plan.setId(results.getInt(0));
            plan.setTitle(results.getString(1));
            plan.setDateJSONArray(results.getString(2));
            plans.add(plan);
            results.moveToNext();

        }
        results.close();
        return plans;
    }

    public void removeAll() {
        String sql = "DELETE FROM " + tableName + ";";
        db.execSQL(sql);
    }




}
