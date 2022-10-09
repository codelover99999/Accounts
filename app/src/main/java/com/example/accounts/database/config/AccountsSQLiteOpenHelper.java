package com.example.accounts.database.config;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.accounts.bean.Account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Singleton Pattern
 */
public class AccountsSQLiteOpenHelper extends SQLiteOpenHelper {

    private static AccountsSQLiteOpenHelper aInstance;

    // Database Info
    private static final String DATABASE_NAME = "accounts";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ACCOUNTS = "accounts";

    // Table Columns
    private static final String KEY_ACCOUNTS_ID = "_id";
    private static final String KEY_ACCOUNTS_ACCOUNT_NAME = "accountname";
    private static final String KEY_ACCOUNTS_USERNAME = "username";
    private static final String KEY_ACCOUNTS_PASSWORD = "password";
    private static final String KEY_ACCOUNTS_CREATE_TIME = "createtime";

    private AccountsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized AccountsSQLiteOpenHelper getaInstance(Context context) {
        if (aInstance == null) {
            aInstance = new AccountsSQLiteOpenHelper(context);
        }
        return aInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS +
                "(" +
                KEY_ACCOUNTS_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_ACCOUNTS_ACCOUNT_NAME + " TEXT, " +
                KEY_ACCOUNTS_USERNAME + " TEXT, " +
                KEY_ACCOUNTS_PASSWORD + " TEXT, " +
                KEY_ACCOUNTS_CREATE_TIME + " TEXT " +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
            onCreate(db);
        }
    }

    public int insertAccount(Account account) {
        long insert = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(KEY_ACCOUNTS_ACCOUNT_NAME, account.getAccountname());
            values.put(KEY_ACCOUNTS_USERNAME, account.getUsername());
            values.put(KEY_ACCOUNTS_PASSWORD, account.getPassword());
            values.put(KEY_ACCOUNTS_CREATE_TIME,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:SS")));
            insert = db.insertOrThrow(TABLE_ACCOUNTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return Long.valueOf(insert).intValue();
    }

    public int updateAccount(Account account) {
        int update = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(KEY_ACCOUNTS_ACCOUNT_NAME, account.getAccountname());
            values.put(KEY_ACCOUNTS_USERNAME, account.getUsername());
            values.put(KEY_ACCOUNTS_PASSWORD, account.getPassword());
            values.put(KEY_ACCOUNTS_CREATE_TIME,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:SS")));
            update = db.update(TABLE_ACCOUNTS, values, KEY_ACCOUNTS_ID + "= ?", new String[]{account.get_id().toString()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return update;
    }

    public int deleteAccount(Integer id) {
        int delete = 0;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            delete = db.delete(TABLE_ACCOUNTS, KEY_ACCOUNTS_ID + "= ?", new String[]{id.toString()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return delete;
    }

    @SuppressLint("Range")
    public List<Account> getAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s ", TABLE_ACCOUNTS);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNTS_ID));
                    String accountname = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNTS_ACCOUNT_NAME));
                    String username = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNTS_USERNAME));
                    String password = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNTS_PASSWORD));
                    String createtime = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNTS_CREATE_TIME));
                    accounts.add(new Account(id, accountname, username, password, createtime));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db.isOpen()) {
                db.close();
            }
        }
        return accounts;
    }
}
