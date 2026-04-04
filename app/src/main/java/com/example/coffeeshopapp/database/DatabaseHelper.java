package com.example.coffeeshopapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AuthDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USER = "User";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng User
        String createTable = "CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "role TEXT)";
        db.execSQL(createTable);

        // Tạo sẵn tài khoản admin
        db.execSQL("INSERT INTO User(username,password,role) VALUES('admin','123','ADMIN')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // ================= REGISTER =================

    public boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", "CUSTOMER");

        long result = db.insert(TABLE_USER, null, cv);
        return result != -1;
    }

    // ================= LOGIN =================

    public String login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT role FROM User WHERE username=? AND password=?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            return cursor.getString(0); // trả về role
        }

        return null;
    }

    // ================= CHECK USER EXISTS =================

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM User WHERE username=?",
                new String[]{username}
        );

        return cursor.moveToFirst();
    }

    // ================= GET USER ROLE =================

    public String getRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT role FROM User WHERE username=?",
                new String[]{username}
        );

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }

        return null;
    }
}
