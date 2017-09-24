package in.college.safety247;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Charmy Garg on 18-Sep-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(LoginDataBaseAdapter.TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query ="DROP TABLE IF EXISTS " + "TEMPLATE";
        db.execSQL(query);
        this.onCreate(db);
    }
}
