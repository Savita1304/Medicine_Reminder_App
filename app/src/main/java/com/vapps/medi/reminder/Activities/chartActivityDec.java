package com.vapps.medi.reminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.vapps.medi.reminder.Adapters.AdapterChart;
import com.vapps.medi.reminder.Class.Admob;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.Addchart;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class chartActivityDec extends AppCompatActivity implements Addchart {
DatabaseHelper db;
    ArrayList<DataModelHomeF> listH;
    ArrayList<DataModel2> listchart;
    public AdapterChart myAdapter;
    String id,muserid,freq,type;
    int num = 14,total = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_dec);


        Admob.LoadInterestitialAd(this);


        SharedPreferences prefs ;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.getInt("color",0) != 0) {
           int themeColor = prefs.getInt("color", 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(themeColor);
            }


            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(themeColor));
        }


        db=new DatabaseHelper(this);
        id= getIntent().getStringExtra("id");
        muserid = getIntent().getStringExtra("muserid");
        String mName= getIntent().getStringExtra("mName");
        freq= getIntent().getStringExtra("freq");
        String  ts = getIntent().getStringExtra("total");
        int nodays = getIntent().getIntExtra("days",0);

        type = getIntent().getStringExtra("type");


        if (mName!=null){
            getSupportActionBar().setTitle(mName);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listH=new ArrayList<>();
        listH.clear();

        listchart=new ArrayList<>();
        listchart.clear();

        //Log.e("printid",id);

        listH =  db.getChartData(id);


        if (freq.equals("Single time a day")){
            num = 7;
        }
        else if(freq.equals("2 time a day")){


            int count = db.getdateCount(getCurrentDate(),freq);
            //  num = listC.size()*2;

            //Log.e("NUM", num + "");

            if (count == 1){
                num = 13;
            }
            else if(count == 2){
                num = 14;
            }
            else{
                num = 12;
            }
        }
        else{

            if (ts.contains(",")){
                String time[] = ts.split(",");
                total = time.length;
            }
            else{
                total = 1;

            }

            num = 7* total;


        }

        listchart =  db.getChartData1(id,muserid,num);





        RecyclerView recyclerView = findViewById(R.id.Rv);
      //  MyAdapter1 myAdapter = new MyAdapter1(listH,getApplicationContext());

         myAdapter = new AdapterChart(listchart,getApplicationContext(),this,nodays);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);

        registerForContextMenu(recyclerView);




    }
    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:


             this.finish();
//                onBackPressed();
                if (Admob.mInterstitialAd!=null){
                    Admob.mInterstitialAd.show(this);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void click(int pos, Context ctx,int total) {
        listchart.clear();

        listchart =  db.getChartData1(id,muserid,num);




        RecyclerView recyclerView = findViewById(R.id.Rv);

        myAdapter = new AdapterChart(listchart,ctx,this,total);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);

        registerForContextMenu(recyclerView);

    }
}