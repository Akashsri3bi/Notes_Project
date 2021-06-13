package com.example.notemaking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Model Class  .
 */

public class NotesDetails extends SQLiteOpenHelper {

    public NotesDetails(@Nullable Context context) {
        super(context ,  "NotesDb" , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Notes(Title Text primary key , Description Text, Date Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists Notes");
    }

    public boolean insertuserData(String title, String description , String date){
        //Get a Writable database .
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put("Title" , title) ;
        values.put("Description" , description) ;
        values.put("Date" , date) ;
        long results = db.insert("Notes" , null , values)  ;
        if(results==-1) {
            return false;
        }else{
            return true ;
        }
    }

    public boolean UpdateuserData(String title, String description , String date){
        //Get a Writable database .
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put("Title" , title) ;
        values.put("Description" , description) ;

        //We need to create cursor to check if data is accesed by it or not .
        Cursor cursor = db.rawQuery("Select * from Notes where Title=?" , new String[]{title}) ;
        if (cursor.getCount() > 0) {
            long results = db.update("Notes", values, "Title=?", new String[]{title});
            if (results == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false ;
        }
    }

    public boolean DeleteuserData(String title){
        //Get a Writable database .
        SQLiteDatabase db = this.getWritableDatabase() ;
        //We need to create cursor to check if data is accesed by it or not .
        Cursor cursor = db.rawQuery("Select * from Notes where Title=?" , new String[]{title}) ;
        if (cursor.getCount() > 0) {
            long results = db.delete("Notes","Title=?", new String[]{title});
            if (results == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false ;
        }
    }

    public Cursor getData(){
        //Get a Writable database .
        SQLiteDatabase db = this.getWritableDatabase() ;
        //Creating a cursor to get data .
        Cursor cursor = db.rawQuery("Select * from Notes" , null) ;
        return cursor ;
    }

}
