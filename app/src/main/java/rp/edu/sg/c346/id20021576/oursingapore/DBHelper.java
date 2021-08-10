package rp.edu.sg.c346.id20021576.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sgislands.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ISLAND = "Island";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_AREA = "area";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Island
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,
        // desc TEXT, stars INTEGER, area INTEGER );
        String createIslandTableSql = "CREATE TABLE " + TABLE_ISLAND + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESC + " TEXT, "
                + COLUMN_AREA + " INTEGER, "
                + COLUMN_STARS + " REAL )";
        db.execSQL(createIslandTableSql);
        Log.i("info", createIslandTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLAND);
        onCreate(db);
    }

    public long insertIsland(String name, String desc, int area, float stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_AREA, area);
        values.put(COLUMN_STARS, stars);

        long result = db.insert(TABLE_ISLAND, null, values);

        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Island> getAllIslands() {
        ArrayList<Island> Islandslist = new ArrayList<Island>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DESC + ","
                + COLUMN_AREA + ","
                + COLUMN_STARS + " FROM " + TABLE_ISLAND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int area = cursor.getInt(3);
                float stars = cursor.getFloat(4);

                Island newIsland = new Island(id, name, desc, area, stars);
                Islandslist.add(newIsland);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Islandslist;
    }

    public ArrayList<Island> getAllIslandsByStars(int starsFilter) {
        ArrayList<Island> Islandslist = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESC, COLUMN_AREA, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_ISLAND, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int area = cursor.getInt(3);
                float stars = cursor.getFloat(4);

                Island newIsland = new Island(id, name, desc, area, stars);
                Islandslist.add(newIsland);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Islandslist;
    }

    public int updateIsland(Island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESC, data.getDesc());
        values.put(COLUMN_AREA, data.getislandArea());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLAND, values, condition, args);
        db.close();
        return result;
    }


    public int deleteIsland(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLAND, condition, args);
        db.close();
        return result;
    }

}