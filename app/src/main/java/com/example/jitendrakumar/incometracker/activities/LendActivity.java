package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyLendAdapter;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.models.LendData;

import java.util.ArrayList;

public class LendActivity extends AppCompatActivity {

    RecyclerView rvLendData;
    LendDatabaseHelper lendDatabaseHelper;
    ArrayList<LendData> arrayList = new ArrayList<>( );
    LendData lData;
    MyLendAdapter myLendAdapter;
    public static final String TAG = "date";
    private float totalLend = (float) 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lend );

            getSupportActionBar().setTitle( "Lend List" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            lendDatabaseHelper = new LendDatabaseHelper( LendActivity.this );
            rvLendData= (RecyclerView) findViewById( R.id.rvLendData );

            ArrayList<LendData> mylendlist = new ArrayList<>();
            mylendlist = getArrayList();

            myLendAdapter = new MyLendAdapter( mylendlist, this );

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
            rvLendData.setLayoutManager( mLayoutManager );
            rvLendData.setItemAnimator( new DefaultItemAnimator() );
            rvLendData.setHasFixedSize( true );
            rvLendData.setAdapter( myLendAdapter );
        }

        public ArrayList<LendData> getArrayList(){
            Cursor res = lendDatabaseHelper.getAllTakenData();
            if(res.getCount() == 0)
            {
                Toast.makeText( LendActivity.this, "Nothing Found in Databse!!!", Toast.LENGTH_SHORT ).show();
                return null;
            }
            else
            {
                while (res.moveToNext()){
                    int lId = res.getInt( 0 );
                    String lPerson =  res.getString( 1 );
                    float lAmount =  res.getFloat( 2 );
                    String lDesc = res.getString( 3 );
                    String lDate = res.getString( 4 );
                    //
                    lData = new LendData(lId, lAmount, lDesc, lPerson, lDate);
                    arrayList.add( lData);
                    totalLend = totalLend +lAmount;
                }

            }
            return arrayList;
        }
    }
