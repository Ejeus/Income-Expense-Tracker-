package com.example.jitendrakumar.incometracker.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.MainActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class IncomeReportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    TextView etIncomeFrom, etIncomeTo;
    IncomeDatabaseHelper myIncomeDB;
    Button btnViewIncomeReport;
    private String id;
    public static final String TAG = "res";
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_income_report, container, false );

        etIncomeFrom = (TextView) view.findViewById( R.id.etIncomeFrom);
        etIncomeTo = (TextView) view.findViewById( R.id.etIncomeTo);
        btnViewIncomeReport = (Button)view.findViewById( R.id.btnViewIncomeReport );
        myIncomeDB = new IncomeDatabaseHelper( getContext());

        etIncomeFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeFrom.setTextColor( Color.parseColor("#00ff00"));
        etIncomeTo.setTextColor( Color.parseColor("#00ff00"));

        showAllIncomeData();
        etIncomeTo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );



        return view;
    }


    public Dialog onCreateDialog() {
        Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(getActivity(),
                mDateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }




    public void showAllIncomeData(){
        btnViewIncomeReport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateFrom = etIncomeFrom.getText().toString();
                String dateTo = etIncomeTo.getText().toString();

                    Log.d( TAG, "onClick: before "+dateFrom + dateTo );
                    Cursor res = myIncomeDB.getAllIncomeReport(dateFrom,dateTo);
                    Log.d( TAG, "onClick: getAllIncome REport Function Run" );
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
                            buffer.append( "Income Id : "+ res.getString( 0 )+"\n" );
                            buffer.append( "Income Type : "+ res.getString( 1 )+"\n" );
                            buffer.append( "Income Amount : "+ res.getString( 2 )+"\n" );
                            buffer.append( "Date : "+ res.getString( 3 )+"\n" );
                            buffer.append( "Time : "+ res.getString( 4 )+"\n\n" );

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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "You picked the following date: "+dayOfMonth+"/"+(month+1)+"/"+year;
                etIncomeTo.setText(date);
            }
        };
    }
}
