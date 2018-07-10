package com.example.jitendrakumar.incometracker.fragments;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.TobePaidDatabaseHelper;

public class TobePaidFragment extends Fragment {

    EditText etPersonName, etPayingAmount, etPayingReason, etPayingDate;
    Button btnPayingSubmit, btnViewAllPayingData;
    TobePaidDatabaseHelper tobePaidDatabaseHelper;
    public static final String TAG = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_tobe_paid, container, false );
        tobePaidDatabaseHelper = new TobePaidDatabaseHelper( getContext());

        etPersonName = (EditText) view.findViewById( R.id.etPersonName );
        etPayingAmount = (EditText) view.findViewById( R.id.etPayingAmount );
        etPayingReason = (EditText) view.findViewById( R.id.etPayingReason );
        etPayingDate = (EditText) view.findViewById( R.id.etPayingDate );
        btnPayingSubmit = (Button) view.findViewById( R.id.btnPayingSubmit );
        btnViewAllPayingData = (Button) view.findViewById( R.id.btnViewAllPayingData );

        etPersonName.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingReason.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingDate.setHintTextColor(getResources().getColor(R.color.colorTexts));

        etPersonName.setTextColor( Color.parseColor("#00ff00"));
        etPayingDate.setTextColor( Color.parseColor("#00ff00"));
        etPayingAmount.setTextColor( Color.parseColor("#00ff00"));
        etPayingReason.setTextColor( Color.parseColor("#00ff00"));



        btnPayingSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String personName = etPersonName.getText().toString();
                    Log.d( TAG, "onClick: "+personName );
                    String payingAmount = etPayingAmount.getText().toString();
                    String payingDate =  etPayingDate.getText().toString();
                    String payingReason = etPayingReason.getText().toString();

                        boolean isInserted = tobePaidDatabaseHelper.insertPayingData( personName, payingAmount ,payingReason,payingDate);
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();

                        } else {
                            Toast.makeText( getActivity(), "Data is not Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();
                        }
                    }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }
        } );

        showAllPayingData();
        return  view;
    }

    public void showAllPayingData(){
        btnViewAllPayingData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = tobePaidDatabaseHelper.getAllPayingData();
                if(res.getCount() == 0)
                {
                    // Show message
                    showMessage( "Error", "Nothing Found" );

                    return;
                }
                else
                {
                    StringBuffer buffer = new StringBuffer(  );
                    while (res.moveToNext()){
                        buffer.append( "Paying Id : "+ res.getString( 0 )+"\n" );
                        buffer.append( "Person Name : "+ res.getString( 1 )+"\n" );
                        buffer.append( "Paying Amount : "+ res.getString( 2 )+"\n" );
                        buffer.append( "Paying Reason : "+ res.getString( 3 )+"\n" );
                        buffer.append( "Paying Date : "+ res.getString( 4 )+"\n\n" );

                    }
                    // Show all data
                    showMessage( "Data", buffer.toString() );
                }
            }

        } );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( Message );
        builder.show();
    }


}