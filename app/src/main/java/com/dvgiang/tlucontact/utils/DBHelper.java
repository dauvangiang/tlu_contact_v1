package com.dvgiang.tlucontact.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dvgiang.tlucontact.model.Staff;
import com.dvgiang.tlucontact.model.Unit;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts";
    private static final int DATABASE_VERSION = 3;

    // Bảng Units
    private static final String TABLE_UNITS = "units";
    private static final String COLUMN_UNIT_ID = "id";
    private static final String COLUMN_UNIT_AVATAR = "avatar";
    private static final String COLUMN_UNIT_NAME = "name";
    private static final String COLUMN_UNIT_PHONE = "phone";
    private static final String COLUMN_UNIT_EMAIL = "email";
    private static final String COLUMN_UNIT_ADDRESS = "address";

    // Bảng Staffs
    private static final String TABLE_STAFFS = "staffs";
    private static final String COLUMN_STAFF_ID = "id";
    private static final String COLUMN_STAFF_AVATAR = "avatar";
    private static final String COLUMN_STAFF_NAME = "name";
    private static final String COLUMN_STAFF_PHONE = "phone";
    private static final String COLUMN_STAFF_EMAIL = "email";
    private static final String COLUMN_STAFF_CURR_UNIT = "curr_unit";
    private static final String COLUMN_STAFF_POSITION = "position";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Units
        String createUnitsTable = "CREATE TABLE " + TABLE_UNITS + " ("
                + COLUMN_UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UNIT_AVATAR + " INTEGER, "
                + COLUMN_UNIT_NAME + " TEXT, "
                + COLUMN_UNIT_PHONE + " TEXT, "
                + COLUMN_UNIT_EMAIL + " TEXT, "
                + COLUMN_UNIT_ADDRESS + " TEXT)";
        db.execSQL(createUnitsTable);

        // Tạo bảng Staffs
        String createStaffsTable = "CREATE TABLE " + TABLE_STAFFS + " ("
                + COLUMN_STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STAFF_AVATAR + " INTEGER, "
                + COLUMN_STAFF_NAME + " TEXT, "
                + COLUMN_STAFF_PHONE + " TEXT, "
                + COLUMN_STAFF_EMAIL + " TEXT, "
                + COLUMN_STAFF_CURR_UNIT + " TEXT, "
                + COLUMN_STAFF_POSITION + " TEXT)";
        db.execSQL(createStaffsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFFS);
        onCreate(db);
    }

    // ---------- CRUD CHO UNITS ----------

    public void addUnit(Unit unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNIT_AVATAR, unit.getImage());
        values.put(COLUMN_UNIT_NAME, unit.getName());
        values.put(COLUMN_UNIT_PHONE, unit.getPhone());
        values.put(COLUMN_UNIT_EMAIL, unit.getEmail());
        values.put(COLUMN_UNIT_ADDRESS, unit.getAddress());

        db.insert(TABLE_UNITS, null, values);
        db.close();
    }

    public List<Unit> getAllUnits() {
        List<Unit> unitList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_UNITS, null);

        if (cursor.moveToFirst()) {
            do {
                Unit unit = new Unit(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                unitList.add(unit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return unitList;
    }

    public void deleteUnit(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_UNITS, COLUMN_UNIT_NAME + "=?", new String[]{name});
        db.close();
    }

    public void updateUnit(Unit unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNIT_AVATAR, unit.getImage());
        values.put(COLUMN_UNIT_PHONE, unit.getPhone());
        values.put(COLUMN_UNIT_EMAIL, unit.getEmail());
        values.put(COLUMN_UNIT_ADDRESS, unit.getAddress());

        db.update(TABLE_UNITS, values, COLUMN_UNIT_NAME + "=?", new String[]{unit.getName()});
        db.close();
    }

    // ---------- CRUD CHO STAFFS ----------

    public void addStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAFF_AVATAR, staff.getImage());
        values.put(COLUMN_STAFF_NAME, staff.getName());
        values.put(COLUMN_STAFF_PHONE, staff.getPhone());
        values.put(COLUMN_STAFF_EMAIL, staff.getEmail());
        values.put(COLUMN_STAFF_CURR_UNIT, staff.getCurrUnit());
        values.put(COLUMN_STAFF_POSITION, staff.getPosition());

        db.insert(TABLE_STAFFS, null, values);
        db.close();
    }

    public List<Staff> getAllStaffs() {
        List<Staff> staffList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STAFFS, null);

        if (cursor.moveToFirst()) {
            do {
                Staff staff = new Staff(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                staffList.add(staff);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return staffList;
    }

    public void deleteStaff(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STAFFS, COLUMN_STAFF_NAME + "=?", new String[]{name});
        db.close();
    }

    public void updateStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAFF_AVATAR, staff.getImage());
        values.put(COLUMN_STAFF_PHONE, staff.getPhone());
        values.put(COLUMN_STAFF_EMAIL, staff.getEmail());
        values.put(COLUMN_STAFF_CURR_UNIT, staff.getCurrUnit());
        values.put(COLUMN_STAFF_POSITION, staff.getPosition());

        db.update(TABLE_STAFFS, values, COLUMN_STAFF_NAME + "=?", new String[]{staff.getName()});
        db.close();
    }
}