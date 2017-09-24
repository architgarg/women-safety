package in.college.safety247;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charmy Garg on 29-Sep-16.
 */
public class ContactsDatabaseAdapter {

    static final int DATABASE_VERSION = 4;
    static final String DATABASE_NAME ="contactsDB.db";
    static final String TABLE_NAME = "AlertTB";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_NUMBER = "number";

    static final String TABLE_CREATE_QUERY = "create table "+TABLE_NAME+" (" +COLUMN_ID+ " INTEGER PRIMARY KEY, "
            +COLUMN_NAME+ " TEXT NOT NULL," +COLUMN_NUMBER+ " TEXT NOT NULL)";

    public SQLiteDatabase db;
    private final Context context;

    private ContactsDatabaseHelper dbHelper;
    public  ContactsDatabaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new ContactsDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  ContactsDatabaseAdapter open() throws SQLException
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

    public void insertEntry(String name, String number)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_NUMBER,number);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<DatabaseModel> getDataFromDB()
    {
        List<DatabaseModel> listItems=new ArrayList<DatabaseModel>();
        String query = "select * from "+TABLE_NAME;

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                DatabaseModel model = new DatabaseModel();
                model.setName(cursor.getString(1));
                model.setNumber(cursor.getString(2));

                listItems.add(model);
            }while (cursor.moveToNext());
        }

        return listItems;
    }

    public List<Number> getNumDB()
    {
        List<Number> listItem=new ArrayList<Number>();
        String query = "select "+COLUMN_NUMBER+" from "+TABLE_NAME;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Number num = new Number();
                num.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER)));

                listItem.add(num);
            }while (cursor.moveToNext());
        }
        return listItem;
    }

    /*delete a row from database*/
    public void deleteARow(String pos){
        db= dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { pos });
        db.close();
    }
}

