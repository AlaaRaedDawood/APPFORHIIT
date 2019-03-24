package com.example.start;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class mydbhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1 ;
    private static final String DATABASE_NAME ="hiit.db" ;
    public static final String TABLE_LAYOUTS = "layouts" ;
    public static final String COLUMN_ID = "_id" ;
    public static final String COLUMN_NAME = "layoutname" ;
    public mydbhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     String query = "CREATE TABLE " + TABLE_LAYOUTS + "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT ," +
             COLUMN_NAME + " TEXT " + ");" ;
     db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS "+TABLE_LAYOUTS);
     onCreate(db);
    }
    //add a Layout to db
    public void addLayout(layoutDB layout) throws JSONException {
        ContentValues value = new ContentValues();
        JSONObject layout_json = new JSONObject();
        layout_json.put("uniqueArrays", new JSONArray(layout.getResultPoints()));
        String layout_string = layout_json.toString();
        value.put(COLUMN_NAME , layout_string);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LAYOUTS , null , value);
        db.close();
    }
    //delete layout from database
    public void deleteLayout(layoutDB layout)throws JSONException{
        SQLiteDatabase db = getWritableDatabase();
        JSONObject layout_json = new JSONObject();
        layout_json.put("uniqueArrays", new JSONArray(layout.getResultPoints()));
        String layout_string = layout_json.toString();
        db.execSQL("DELETE FROM " + TABLE_LAYOUTS + " WHERE " + COLUMN_NAME + " =\"" +layout_string + "\";");


    }
    //read db
    public String getLayoutName(){
        SQLiteDatabase db = getWritableDatabase();
        //getting all data from table
        String query = "SELECT * FROM " + TABLE_LAYOUTS + "WHERE 1 ;";
         //cursorPoint a pointer to our results
        Cursor cursor = db.rawQuery(query,null);
        //cursor moves to the first row of data
        cursor.moveToFirst();
        //loop until the last row using cursor
        while (!cursor.isAfterLast()){
               if(cursor.getColumnIndex("columnName")!= null){

           }
        }
        return "" ;
    }
}
