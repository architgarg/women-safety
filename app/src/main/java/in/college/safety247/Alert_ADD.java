package in.college.safety247;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charmy Garg on 23-Sep-16.
 */
public class Alert_ADD extends AppCompatActivity {

    static final int PICK_CONTACT= 1;

    ContactsDatabaseAdapter mContactsDatabaseAdapter;
    List<DatabaseModel> dbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_add);
        dbList= new ArrayList<DatabaseModel>();
        Button buttonPickContact = (Button)findViewById(R.id.pickcontact);
        buttonPickContact.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                final RippleView rippleView = (RippleView) findViewById(R.id.ripplePick);
                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                        startActivityForResult(intent, 1);
                    }
                });

            }});
        mContactsDatabaseAdapter =new ContactsDatabaseAdapter(this);
        mContactsDatabaseAdapter = mContactsDatabaseAdapter.open();

        Button buttonShowContact = (Button) findViewById(R.id.showcontact);
        buttonShowContact.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                final RippleView rippleView = (RippleView) findViewById(R.id.rippleShow);
                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        Intent intent = new Intent(Alert_ADD.this, RecyclerAdapter.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();

                String name1 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String number1 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                dbList= mContactsDatabaseAdapter.getDataFromDB();
                if(dbList.size()!=0) {
                    if (dbList.size() == 3) {
                        Toast.makeText(this, "Maximum 3 contacts can be there", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mContactsDatabaseAdapter.insertEntry(name1,number1);
                    }
                }
                else
                {
                    mContactsDatabaseAdapter.insertEntry(name1,number1);
                }

            }
        }
    }

}
