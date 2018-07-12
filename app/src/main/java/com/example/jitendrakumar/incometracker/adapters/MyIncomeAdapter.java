package com.example.jitendrakumar.incometracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.MainActivity;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;
import java.util.Calendar;

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.BeneficiaryViewHolder>  {
    private ArrayList<IncomeData> listBeneficiary;
    private Context mContext;
    private ArrayList<IncomeData> mFilteredList;
    public static final String TAG = "res";
    private int year , month, day, hour, minute;
    TimePickerFragment timePickerFragment;

    public MyIncomeAdapter(ArrayList<IncomeData> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;
        }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvIncomeReportId;
        public AppCompatTextView tvIncomeReportType;
        public AppCompatTextView tvIncomeReportAmount;
        public AppCompatTextView tvIncomeReportDate;
        public AppCompatTextView tvIncomeReportTime;

        public BeneficiaryViewHolder(View view) {
            super(view);
            tvIncomeReportId = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportId );
            tvIncomeReportType = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportType );
            tvIncomeReportAmount = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportAmount );
            tvIncomeReportDate = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportDate );
            tvIncomeReportTime = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportTime );

        }
    }

    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_income_item, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {
        holder.tvIncomeReportId.setText(String.valueOf( listBeneficiary.get(position).getIncomeId() ));
        holder.tvIncomeReportType.setText(listBeneficiary.get(position).getInputType());
        holder.tvIncomeReportAmount.setText(String.valueOf( listBeneficiary.get(position).getInputAmount() ));

        String dateStr = listBeneficiary.get( position ).getIncomeDate().toString();

        String[]dateParts = dateStr.split("/");
        try {
            year = safeParseInt(dateParts[2]);
            month = safeParseInt(dateParts[0]);
            day = safeParseInt(dateParts[1]);
        } catch (Exception e) {
            Log.d( TAG, "onBindViewHolder: Error in Date Parsing  " );
        }
        // parsing the hour and minute int values from Time String HH:MM
        String timeStr = listBeneficiary.get( position).getIncomeTime().toString();
        String[] timeParts = timeStr.split( ":" );
        try {
            hour = safeParseInt( timeParts[0] );
            minute = safeParseInt( timeParts[1] );
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        String Date = "";
        if(month<=9 && day<=9)
        {
             Date = "0"+day +"/0"+month +"/"+year ;
        }
        if(month<=9 && day>9){
             Date = day +"/0"+month +"/"+year ;
        }
        if(month>9 && day<=9){
            Date = "0"+day +"/"+month +"/"+year ;
        }
        else {
             Date = day +"/"+month +"/"+year ;
        }
     /*   String Time ="";
        String format = timePickerFragment.checkAmPm();
        if(format.equals( "AM" ) && hour<=9){
             Time =  "0"+hour+":"+minute +format;
        }
        if(format.equals( "AM" ) && hour>9 && hour<=12){
             Time =  hour+":"+minute +format;
        }
        if(format.equals( "PM" ) && hour>12){
             Time =  "0"+hour+":"+minute +format;
        }
        if(format.equals( "AM" ) && hour==0){
             Time =  "0"+hour+":"+minute +format;
        }
*/       String Time = hour+":"+minute;
         holder.tvIncomeReportDate.setText(Date);
         holder.tvIncomeReportTime.setText(Time);

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }
}


