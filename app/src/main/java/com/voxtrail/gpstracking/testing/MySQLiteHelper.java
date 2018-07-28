package com.voxtrail.gpstracking.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.voxtrail.gpstracking.util.TagUtils;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "locDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE loc ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lat TEXT, " +
                "lng TEXT, " +
                "alt TEXT, " +
                "spd TEXT, " +
                "dt TEXT )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    public void addLoc(LocPOJO locPOJO) {
        //for logging
        Log.d("addBook", locPOJO.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("lat", locPOJO.getLat()); // get title
        values.put("lng", locPOJO.getLng()); // get title
        values.put("alt", locPOJO.getAlt()); // get title
        values.put("spd", locPOJO.getSpeed()); // get title
        values.put("dt", locPOJO.getDt()); // get title

        // 3. insert
        db.insert("loc", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public List<LocPOJO> getAllLocations() {
        List<LocPOJO> locPOJOS = new LinkedList<LocPOJO>();

        // 1. build the query
        String query = "SELECT  * FROM loc";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        LocPOJO locPOJO = null;
        if (cursor.moveToFirst()) {
            do {
                locPOJO = new LocPOJO();
                locPOJO.setId(Integer.parseInt(cursor.getString(0)));
                locPOJO.setLat(cursor.getString(1));
                locPOJO.setLng(cursor.getString(2));
                locPOJO.setAlt(cursor.getString(3));
                locPOJO.setSpeed(cursor.getString(4));
                locPOJO.setDt(cursor.getString(5));

                // Add book to books
                locPOJOS.add(locPOJO);
            } while (cursor.moveToNext());
        }

//        Log.d(TagUtils.getTag(),"all locations:-"+ locPOJOS.toString());

        // return books
        return locPOJOS;
    }

}