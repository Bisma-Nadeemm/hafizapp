package com.example.hafizapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "madrasa.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_SABAQ = "sabaq";
    private static final String TABLE_SABAQI = "sabaqi";
    private static final String TABLE_MANZIL = "manzil";

    // Common column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_DATE = "date";

    // Student table columns
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_CLASS = "class_name";

    // Sabaq table columns
    private static final String COLUMN_SURAH = "surah";
    private static final String COLUMN_VERSES = "verses_range";

    // Sabaqi table columns
    private static final String COLUMN_LAST_PARA = "last_para";

    // Manzil table columns
    private static final String COLUMN_CURRENT_PARA = "current_para";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_CLASS + " TEXT"
                + ")";
        db.execSQL(createStudentsTable);

        String createSabaqTable = "CREATE TABLE " + TABLE_SABAQ + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_ID + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_SURAH + " TEXT,"
                + COLUMN_VERSES + " TEXT,"
                + " FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(createSabaqTable);

        String createSabaqiTable = "CREATE TABLE " + TABLE_SABAQI + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_ID + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_LAST_PARA + " INTEGER,"
                + " FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(createSabaqiTable);

        String createManzilTable = "CREATE TABLE " + TABLE_MANZIL + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_ID + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_CURRENT_PARA + " INTEGER,"
                + " FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(createManzilTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SABAQ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SABAQI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANZIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);

        // Create new tables
        onCreate(db);
    }

    // Student table operations

    public void insertStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_AGE, student.getAge());
        values.put(COLUMN_CLASS, student.getClassName());

        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                student.setAge(cursor.getInt(cursor.getColumnIndex(COLUMN_AGE)));
                student.setClassName(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));

                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return students;
    }

    // Add methods for updating and deleting students

    // Sabaq table operations

    public void insertSabaq(int studentId, String date, String surah, String verses) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_SURAH, surah);
        values.put(COLUMN_VERSES, verses);

        db.insert(TABLE_SABAQ, null, values);
        db.close();
    }

    public List<Sabaq> getSabaqRecords(int studentId) {
        List<Sabaq> sabaqRecords = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SABAQ +
                " WHERE " + COLUMN_STUDENT_ID + " = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Sabaq sabaq = new Sabaq();
                sabaq.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                sabaq.setStudentId(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                sabaq.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                sabaq.setSurah(cursor.getString(cursor.getColumnIndex(COLUMN_SURAH)));
                sabaq.setVerses(cursor.getString(cursor.getColumnIndex(COLUMN_VERSES)));

                sabaqRecords.add(sabaq);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sabaqRecords;
    }

    // Sabaqi table operations

    public void insertSabaqi(int studentId, String date, int lastPara) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_LAST_PARA, lastPara);

        db.insert(TABLE_SABAQI, null, values);
        db.close();
    }

    public List<Sabaqi> getSabaqiRecords(int studentId) {
        List<Sabaqi> sabaqiRecords = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SABAQI +
                " WHERE " + COLUMN_STUDENT_ID + " = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Sabaqi sabaqi = new Sabaqi();
                sabaqi.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                sabaqi.setStudentId(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                sabaqi.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                sabaqi.setLastPara(cursor.getInt(cursor.getColumnIndex(COLUMN_LAST_PARA)));

                sabaqiRecords.add(sabaqi);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sabaqiRecords;
    }

    // Manzil table operations

    public void insertManzil(int studentId, String date, int currentPara) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_CURRENT_PARA, currentPara);

        db.insert(TABLE_MANZIL, null, values);
        db.close();
    }

    public List<Manzil> getManzilRecords(int studentId) {
        List<Manzil> manzilRecords = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_MANZIL +
                " WHERE " + COLUMN_STUDENT_ID + " = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Manzil manzil = new Manzil();
                manzil.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                manzil.setStudentId(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                manzil.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                manzil.setCurrentPara(cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_PARA)));

                manzilRecords.add(manzil);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return manzilRecords;
    }
}
