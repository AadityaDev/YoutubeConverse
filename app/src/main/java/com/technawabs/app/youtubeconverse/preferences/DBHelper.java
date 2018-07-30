package com.technawabs.app.youtubeconverse.preferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.technawabs.app.youtubeconverse.models.Video;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "MediTrack.db";
    public static final String MEDICINE_NAME = "medicine";
    public static final String MEDICINE_COLUMN_ID = "id";
    public static final String MEDICINE_COLUMN_NAME = "name";
    public static final String MEDICINE_COLUMN_TO = "todate";
    public static final String MEDICINE_COLUMN_FROM = "fromdate";
    public static final String MEDICINE_COLUMN_IS_WEEKLY = "isWeekly";
    public static final String MEDICINE_COLUMN_IS_MONTHLY = "isMonthly";
    public static final String MEDICINE_COLUMN_IS_DAILY = "isDaily";
    public static final String MEDICINE_COLUMN_IS_TAKEN = "isTaken";
    public static final String MEDICINE_COLUMN_IS_TAKEN_TODAY = "isTakenToday";
    public static final String MEDICINE_COLUMN_PRIORITY = "medicine_priority";
    public static final String MEDICINE_DOSAGE = "medicine_dosage";

    public DBHelper(@NonNull Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table medicine "+"( id long primary key, name text , todate text, fromdate text, medicine_priority text,"+
                " isWeekly integer, isMonthly integer, isDaily integer, isTaken integer, isTakenToday integer, medicine_dosage integer )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS medicine");
        onCreate(db);
    }

    public boolean insertMedicine(@NonNull Video video){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MEDICINE_COLUMN_NAME, video.getName());
        contentValues.put(MEDICINE_COLUMN_TO, video.getTo().toString());
        contentValues.put(MEDICINE_COLUMN_FROM, video.getFrom().toString());
        contentValues.put(MEDICINE_COLUMN_IS_WEEKLY, video.isWeekly()?1:0);
        contentValues.put(MEDICINE_COLUMN_IS_DAILY, video.isDaily()?1:0);
        contentValues.put(MEDICINE_COLUMN_IS_MONTHLY, video.isMonthly()?1:0);
        contentValues.put(MEDICINE_COLUMN_IS_TAKEN, video.isTaken()?1:0);
        contentValues.put(MEDICINE_COLUMN_IS_TAKEN_TODAY, video.isTakenToday()?1:0);
        contentValues.put(MEDICINE_COLUMN_PRIORITY, video.getPriority());
        contentValues.put(MEDICINE_DOSAGE, video.getDosage());
        db.insert(MEDICINE_NAME,null,contentValues);
        db.close();
        return true;
    }

    public Integer deleteMedicine (long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MEDICINE_NAME,
                "id = ? ",
                new String[] { Long.toString(id) });
    }

    public ArrayList<Video> getAllMedicine(){
        ArrayList<Video> videos =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+MEDICINE_NAME, null );
        if(res.moveToFirst()){
        do{
            Video video =new Video();
            video.setId(res.getLong(0));
            video.setName(res.getString(1));
            video.setTo(new Date(res.getString(2)));
            video.setFrom(new Date(res.getString(3)));
            video.setPriority(res.getString(4));
            video.setWeekly(res.getInt(5)==0?Boolean.TRUE:Boolean.FALSE);
            video.setMonthly(res.getInt(6)==0?Boolean.TRUE:Boolean.FALSE);
            video.setDaily(res.getInt(7)==0?Boolean.TRUE:Boolean.FALSE);
            video.setDaily(res.getInt(8)==0?Boolean.TRUE:Boolean.FALSE);
            video.setDaily(res.getInt(9)==0?Boolean.TRUE:Boolean.FALSE);
            video.setDosage(res.getInt(10));
            videos.add(video);
        }while (res.moveToNext());
        }
        return videos;
    }


    public ArrayList<Video> getMedicinesForToday(){
        Date today=new Date();
        ArrayList<Video> videos =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+MEDICINE_NAME, null );
        if(res.moveToFirst()){
            do{
                Video video =new Video();
                video.setId(res.getLong(0));
                video.setName(res.getString(1));
                video.setTo(new Date(res.getString(2)));
                video.setFrom(new Date(res.getString(3)));
                video.setPriority(res.getString(4));
                video.setWeekly(res.getInt(5)==0?Boolean.TRUE:Boolean.FALSE);
                video.setMonthly(res.getInt(6)==0?Boolean.TRUE:Boolean.FALSE);
                video.setDaily(res.getInt(7)==0?Boolean.TRUE:Boolean.FALSE);
                video.setTaken(res.getInt(8)==0?Boolean.TRUE:Boolean.FALSE);
                video.setTakenToday(res.getInt(9)==0?Boolean.TRUE:Boolean.FALSE);
                video.setDosage(res.getInt(10));
                if((video.getFrom().getTime()<=today.getTime())||(today.getTime()<= video.getTo().getTime())){
                    video.setTakenToday(true);
                    videos.add(video);
                }
            }while (res.moveToNext());
        }
        return videos;
    }


}
