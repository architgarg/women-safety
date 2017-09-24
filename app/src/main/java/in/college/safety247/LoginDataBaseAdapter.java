package in.college.safety247;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Charmy Garg on 18-Sep-16.
 */
public class LoginDataBaseAdapter
{
    static final int DATABASE_VERSION = 107;
    static final String DATABASE_NAME ="loginDB.db";
    static final String TABLE_NAME = "Login";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_FNAME = "fname";
    static final String COLUMN_LNAME = "lname";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_USER = "user";
    static final String COLUMN_PASS = "pass";
    static final String COLUMN_CPASS = "cpass";
    static final String COLUMN_PHONE = "phone";

    static final String TABLE_CREATE_QUERY = "create table "+TABLE_NAME+" (" +COLUMN_ID+ " INTEGER PRIMARY KEY, "
            +COLUMN_FNAME+ " TEXT NOT NULL," +COLUMN_LNAME+ " TEXT NOT NULL," +COLUMN_EMAIL+ " TEXT NOT NULL,"
            +COLUMN_USER+ " TEXT NOT NULL," +COLUMN_PASS+ " TEXT NOT NULL," +COLUMN_CPASS+ " TEXT NOT NULL,"
            +COLUMN_PHONE+ " TEXT NOT NULL)";

    public SQLiteDatabase db;
    private final Context context;

    private DatabaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(Contact c)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FNAME,c.getFname());
        contentValues.put(COLUMN_LNAME,c.getLname());
        contentValues.put(COLUMN_EMAIL,c.getEmail());
        contentValues.put(COLUMN_USER,c.getUser());
        contentValues.put(COLUMN_PASS,c.getPass());
        contentValues.put(COLUMN_CPASS,c.getCpass());
        contentValues.put(COLUMN_PHONE,c.getPhone());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public String getSingleEntry(String userName)
    {
        Cursor cursor=db.query(TABLE_NAME, null, " user=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(COLUMN_PASS));
        cursor.close();
        return password;
    }

}
