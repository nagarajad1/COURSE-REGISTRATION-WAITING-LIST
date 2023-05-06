package com.example.individualproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "studentwatinglist";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "watinglist";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String Course_Name_COL = "coursename";

    // below variable id for our course duration column.
    private static final String Student_Name_COL = "studentname";

    // below variable for our course description column.
    private static final String Student_Email_COL = "studentemail";

    // below variable is for our course tracks column.
    private static final String Priority_COL = "priority";

    // creating a constructor for our database handler.
    public DBHandler(FragmentActivity context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // method for creating the database by running an SQLite query.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating an SQLite query and setting column names and their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Course_Name_COL + " TEXT,"
                + Student_Name_COL + " TEXT,"
                + Student_Email_COL + " TEXT,"
                + Priority_COL + " TEXT)";
        // executing the above SQLite query.
        db.execSQL(query);
    }

    // method for getting the count of students in the database.
    public int getStudentCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // this method is used to delete a student from our table.
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " where id='" + student.getId() + "'");
    }

    // this method is used to update a student in our table.
    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Course_Name_COL, student.getCourseName());
        values.put(Student_Name_COL, student.getStudentName());
        values.put(Student_Email_COL, student.getStudentEmail());
        values.put(Priority_COL, student.getPriority());
        db.update(TABLE_NAME, values, "id = ?", new String[]{student.getId()});
        db.close();
    }


    // this method is used to add a new student to our table.
    public void addNewStudent(Student student) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Course_Name_COL, student.getCourseName());
        values.put(Student_Name_COL, student.getStudentName());
        values.put(Student_Email_COL, student.getStudentEmail());
        values.put(Priority_COL, student.getPriority());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public List<Student> readAllStudent() {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        List<Student> studentList = new ArrayList<Student>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
