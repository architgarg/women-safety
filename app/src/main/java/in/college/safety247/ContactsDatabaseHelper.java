package in.college.safety247;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Archit Garg on 22-Oct-16.
 */
public class ContactsDatabaseHelper extends SQLiteOpenHelper {



    public ContactsDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ContactsDatabaseAdapter.TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query ="DROP TABLE IF EXISTS " + "TEMPLATE";
        db.execSQL(query);
        this.onCreate(db);
    }
}