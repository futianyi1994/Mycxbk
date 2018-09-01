package org.mobiletrain.mycxbk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 天一 on 2016/10/1.
 */
public class DatabasedeHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_database.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "funny";
    public DatabasedeHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表语句
        String sql = "create table if not exists " + TABLE_NAME + "(_id integer primary key autoincrement, title varchar, wap_thumb varchar, source varchar, create_time varchar,id integer)";
        //执行语句
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
