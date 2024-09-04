package com.vapps.medi.reminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.services.MyService;
import com.vapps.medi.reminder.Class.Admob;
import com.vapps.medi.reminder.Fragments.AHomeFragment;
import com.vapps.medi.reminder.Fragments.AddFragment;
import com.vapps.medi.reminder.Fragments.MyHistory;
import com.vapps.medi.reminder.Fragments.ProfileFragment;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.Models.DataModelHomeF;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class HomeActivity extends AppCompatActivity  {
    BottomNavigationView bnView;
    SharedPreferences prefs ;
    LinearLayout toolbar;
    int JOB_ID = 1001;

    ImageView setting;


    boolean isConnected;

    public  void checknet(Context context_checknet){

        ConnectivityManager cm =
                (ConnectivityManager)context_checknet.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }


    AdView adView;
    LinearLayout adlayout,layoutdata;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setting = findViewById(R.id.setting);

        adView = findViewById(R.id.adView);
        adlayout = findViewById(R.id.adlayoyt);
        layoutdata = findViewById(R.id.mainlayout);


      /*  Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, MyStartServiceReceiver.class);
        this.sendBroadcast(broadcastIntent);*/


     //   Utils.scheduleJob(this);



    /*    JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyJobService.class))
                .setRequiresCharging(false) // Do not require the device to be charging
                .setRequiresDeviceIdle(false) // Do not require the device to be idle
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // Require any network connectivity
                .setPersisted(true) // Persist the job across device reboots
                .setPeriodic(1 * 60 * 1000) // Set the job to repeat every 15 minutes
                .build();

*//*
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay*//*



        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
*/



        checknet(this);


        Admob.adMob1(this,adView,isConnected,adlayout,layoutdata);



      //    ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog(this);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setcolor();
//                bottomSheet.show(HomeActivity.this
//                        , "exampleBottomSheet");


            }
        });





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(new Intent(this, MyService.class));
        }
        else{
            this.startService(new Intent(this, MyService.class));
        }

        bnView = findViewById(R.id.bnView);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (prefs.getInt("color",0) != 0) {
            int value = prefs.getInt("color",0);

            bnView.setBackgroundColor(value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(value);
            }
        }

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                //Log.e("id==",id+"");

                if (id ==R.id.home){
                    loadFrag(new AHomeFragment(),false);

                }/*else if(id==R.id.refill){
                    loadFrag(new RefillFragment(),false);
                }*/else if(id==R.id.add){
                    loadFrag(new AddFragment(),false);
                }else if(id==R.id.history){
                loadFrag(new MyHistory(),false);

                  //  MyHistory.newInstance();




                }else{
                    loadFrag(new ProfileFragment(),false);

                }
                return true;
            }
        });


        bnView.setSelectedItemId(R.id.add);


      insertBulkData();








    }




    public String getExactDate(String startdate,int days){
        Calendar cal = GregorianCalendar.getInstance();
        Date d = StringTODate(startdate);
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_YEAR, days-1);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date afterdate = cal.getTime();
        return format.format(afterdate);
    }



    public boolean DateAfter(String date,String fdate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(date);
            d2 = sdf.parse(fdate);
            if (d1.after(d2)) {

                return true;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void insertBulkData(){

        DatabaseHelper dh = new DatabaseHelper(HomeActivity.this);
        ArrayList<DataModelHomeF> list = dh.getDataMedication();
        if (list.size() > 0){
            for (DataModelHomeF df : list) {
                String medication_id = df.getId();
                String medication_frequency = df.getFrequency();

                String currenttime ="";
                String medication_time1,medication_time2 ;
                String originalm_Name = df.getMedicationName();
                String originalu_name = df.getUserName();
                String originalu_id = df.getUserid();
                int noofdays = df.getFdays();




                Calendar c = Calendar.getInstance();
                String str = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);

                String[] ctime = str.split(":");
                int hr = Integer.valueOf(ctime[0]);
                int min = Integer.valueOf(ctime[1]);   //always return 24 hour time




                String meddate = df.getMeddate();

                if (medication_frequency.equals("Single time a day")){

                    medication_time1 = df.getTime1();

                    //fetch only one time..
                    String fdate = getExactDate(meddate,noofdays);


                    //if database time contians am and pm then convert currenttime like ....

                    if (medication_time1.contains("am") || medication_time1.contains("pm")){
                       // currenttime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");
                        currenttime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                      //  currenttime = currenttime;
                        //Log.e("YYY","st :"+currenttime);
                    }
                    else{
                        currenttime = hr + ":" + min;
                    }

                    DataModel2 list1 = dh.getmonthData(medication_id); //checks the given medicine record ocurs in month or not
                    if (list1 != null){
                        //entry found


                            String month_Date = list1.getDate();
                            String currentDate = getCurrentDate();


                    //    ArrayList<String> dd = getDatesBetween(month_Date,fdate);
                        //Log.e("SXSSX","dt :"+fdate);
                            ArrayList<String> dd = getDatesBetween(month_Date,currentDate);


                            //Log.e("SXSSX","SZ :"+dd.toString());


                            // 19 20 21 22 23 24



                        for (int i = 0; i < dd.size()-1; i++) {
                            int ct = dh.getdateCount(dd.get(i),medication_frequency);
                            int ndays = dh.getnoofDays(medication_id);

                            if (ct == 0){

                                //Log.e("SXSSX","ch :"+DateAfter(dd.get(i),fdate));

                                if (!DateAfter(dd.get(i),fdate)) {

                                    DataModel2 dataModel2 = new DataModel2(medication_id, dd.get(i), medication_time1, list1.getMedicationName(), "Skip", medication_time1, medication_frequency, list1.getUsername(), list1.getUserid());
                                    dh.insertmonthdata(dataModel2);

                                        dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                                }

                            }

                        }//for






                        boolean ch = checktimings(currenttime,medication_time1);


                        int count = dh.getdateCount(currentDate,medication_frequency);
                        int ndays = dh.getnoofDays(medication_id);
                        if (ch && count == 0){
                            //add first entry of same medicine
                            if (!DateAfter(currentDate,fdate)) {
                                DataModel2 dataModel2 = new DataModel2(medication_id, currentDate, medication_time1, list1.getMedicationName(), "Skip", medication_time1, medication_frequency, list1.getUsername(), list1.getUserid());
                                dh.insertmonthdata(dataModel2);

                                    dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                            }

                        }



                    }


                    else{
                        //only once


                        //get exact date for insert values.
                       // String fdate = getExactDate(meddate,noofdays);
                     //   ArrayList<String> dates = getDatesBetween(meddate,fdate);

                        ArrayList<String> dates = getDatesBetween(meddate,getCurrentDate());  //23
                        //Log.e("DBDB ::", dates.toString() );  //6




                        for (int i = 0; i < dates.size(); i++) {
                            String date = dates.get(i);
                            if (!DateAfter(date,fdate)) {
                                if (date.equals(getCurrentDate())) {
                                    boolean ch = checktimings(currenttime, medication_time1);

                                    //Log.e("MED", "ch :" + ch + "@" + currenttime + "/" + medication_time1);
                                    if (ch) {
                                        DataModel2 dataModel2 = new DataModel2(medication_id, date, medication_time1, originalm_Name, "Skip", medication_time1, medication_frequency, originalu_name, originalu_id);

                                        dh.insertmonthdata(dataModel2);


                                            int ndays = dh.getnoofDays(medication_id);
                                            dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                                    }
                                } else {

                                    DataModel2 dataModel2 = new DataModel2(medication_id, date, medication_time1, originalm_Name, "Skip", medication_time1, medication_frequency, originalu_name, originalu_id);
                                    dh.insertmonthdata(dataModel2);
                                    int ndays = dh.getnoofDays(medication_id);

                                        dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                                }

                            }
                        }//for






                    }





                }


                else if (medication_frequency.equals("2 time a day")){
                    medication_time1 = df.getTime1();
                    medication_time2 = df.getTime2();
                    String currenttime1 = "";
                    String currenttime2 = "";


                    String fdate = getExactDate(meddate,noofdays);


                    if (medication_time1.contains("am") || medication_time1.contains("pm")){
                        currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");
                    }
                    else{
                        currenttime1 = hr + ":" + min;
                    }


                    if (medication_time2.contains("am") || medication_time2.contains("pm")){
                        currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");
                    }
                    else{
                        currenttime2 = hr + ":" + min;
                    }


                    DataModel2 list1 = dh.getmonthData(medication_id); //checks the given medicine record ocurs in month or not
                    if (list1 != null){
                        String month_Date = list1.getDate();   //last date from database 17
                        String currentDate = getCurrentDate();  //current date 18




                        ArrayList<String> dd = getDatesBetween(month_Date,currentDate); // dates between


                        //all skip days

                            for (int i = 0; i < dd.size()-1; i++) {
                               // int ndays = dh.getnoofDays(medication_id);
                                if (!DateAfter(dd.get(i),fdate)) {

                                int count = dh.getdateCount1(dd.get(i), medication_frequency, medication_time1);

                                if (count == 0) {
                                    //add first entry of same medicine
                                    DataModel2 dataModel2 = new DataModel2(medication_id, dd.get(i), medication_time1, list1.getMedicationName(), "Skip", medication_time1, medication_frequency, list1.getUsername(), list1.getUserid());
                                    dh.insertmonthdata(dataModel2);

                                    int ctt  = dh.getdateCount(dd.get(i),medication_frequency);  //for current date only
                                    if (ctt==2){

                                            int ndays = dh.getnoofDays(medication_id);
                                            dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                                    }

                                }


                                //check second time

                                int count1 = dh.getdateCount1(dd.get(i), medication_frequency, medication_time2);

                                if (count1 == 0) {

                                    DataModel2 dataModel2 = new DataModel2(medication_id, dd.get(i), medication_time2, list1.getMedicationName(), "Skip", medication_time2, medication_frequency, list1.getUsername(), list1.getUserid());
                                    dh.insertmonthdata(dataModel2);
                                    //dh.updateConfirm3(medication_id,ndays-1,df.getLeftpills());

                                    int ctt  = dh.getdateCount(dd.get(i),medication_frequency);  //for current date only
                                    if (ctt==2){

                                            int ndays = dh.getnoofDays(medication_id);
                                            dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                                    }
                                }

                               /*     int ctt  = dh.getdateCount(dd.get(i),medication_frequency);  //for current date only
                                    if (ctt==2){
                                        ArrayList<String> slist = dh.getcheckStatus(medication_id,dd.get(i),medication_frequency);
                                        if (slist.contains("false")) {
                                            int ndays = dh.getnoofDays(medication_id);
                                            dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                                        }
                                    }*/

                            }//if

                            }//for






                        boolean ch = checktimings(currenttime1,medication_time1);  //check time for current date only
                        boolean ch1 = checktimings(currenttime2,medication_time2);



                        //seperate first and second time of same day


                        //check time

                        int count = dh.getdateCount1(currentDate,medication_frequency,medication_time1);
                        //Log.e("SECOND:","1:"+count+"/"+ch);

                        if (ch && count == 0){
                            //add first entry of same medicine
                            if (!DateAfter(currentDate,fdate)) {
                                DataModel2 dataModel2 = new DataModel2(medication_id, currentDate, medication_time1, list1.getMedicationName(), "Skip", medication_time1, medication_frequency, list1.getUsername(), list1.getUserid());
                                dh.insertmonthdata(dataModel2);

                                int ctt  = dh.getdateCount(currentDate,medication_frequency);  //for current date only
                                if (ctt==2){
                                    //Log.e("CFCF", "ctt:" + ctt);
                                    int ndays = dh.getnoofDays(medication_id);
                                    dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());
                                }
                            }

                        }




                        //check second time

                       // int ndays = dh.getnoofDays(medication_id);
                        int count1 = dh.getdateCount1(currentDate,medication_frequency,medication_time2);
                        //Log.e("SECOND:","2:"+count1+"/"+ch1);
                         if (ch1 && count1 == 0){
                             if (!DateAfter(currentDate,fdate)) {
                                 DataModel2 dataModel2 = new DataModel2(medication_id, currentDate, medication_time2, list1.getMedicationName(), "Skip", medication_time2, medication_frequency, list1.getUsername(), list1.getUserid());
                                 dh.insertmonthdata(dataModel2);
                                // dh.updateConfirm3(medication_id,ndays-1,df.getLeftpills());

                                 int ctt  = dh.getdateCount(currentDate,medication_frequency);  //for current date only
                                 if (ctt==2){
                                         //Log.e("CFCF", "ctt:" + ctt);
                                         int ndays = dh.getnoofDays(medication_id);
                                         dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());
                                 }

                             }
                        }



                         //get state of dates list


                      /*  int ctt  = dh.getdateCount(currentDate,medication_frequency);  //for current date only
                        if (ctt==2){

                            ArrayList<String> slist = dh.getcheckStatus(medication_id,currentDate,medication_frequency);
                            if (slist.contains("false")) {

                                //Log.e("CFCF", "ctt:" + ctt);
                                int ndays = dh.getnoofDays(medication_id);
                                dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                            }
                        }*/




                    }

                    else{
                        //only once entry

                        // check then time also here

                        // if user doesnt open the app for some days and the notification didnt come


                        ArrayList<String> dates = getDatesBetween(meddate,getCurrentDate());
                        //Log.e("DBDB ::", dates.toString() );

                        for (int i = 0; i < dates.size(); i++) {
                            String date = dates.get(i);
                            if (!DateAfter(date,fdate)) {
                            if (date.equals(getCurrentDate())) {
                                boolean ch = checktimings(currenttime1, medication_time1);

                                //Log.e("MED", "ch :" + ch + "@" + currenttime1 + "/" + medication_time1);
                                if (ch) {
                                    DataModel2 dataModel2 = new DataModel2(medication_id, date, medication_time1, originalm_Name, "Skip", medication_time1, medication_frequency, originalu_name, originalu_id);

                                    dh.insertmonthdata(dataModel2);
                                }
                                boolean ch1 = checktimings(currenttime2, medication_time2);
                                //Log.e("MED", "ch44 :" + ch1 + "@" + currenttime2 + "/" + medication_time2);
                                if (ch1) {
                                    DataModel2 dataModel22 = new DataModel2(medication_id, date, medication_time2, originalm_Name, "Skip", medication_time2, medication_frequency, originalu_name, originalu_id);

                                    dh.insertmonthdata(dataModel22);



                                }

                                //check for count is 2 then update noof days in database
                                int count  = dh.getdateCount(date,medication_frequency);  //for current date only
                                if (count==2){

                                        int ndays = dh.getnoofDays(medication_id);
                                        dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                                }
                            } else {
                                DataModel2 dataModel2 = new DataModel2(medication_id, date, medication_time1, originalm_Name, "Skip", medication_time1, medication_frequency, originalu_name, originalu_id);

                                dh.insertmonthdata(dataModel2);


                                DataModel2 dataModel22 = new DataModel2(medication_id, date, medication_time2, originalm_Name, "Skip", medication_time2, medication_frequency, originalu_name, originalu_id);

                                dh.insertmonthdata(dataModel22);


                                    int ndays = dh.getnoofDays(medication_id);
                                    dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());


                            }

                        }//if

                        }



                    }





                }//end of two time a day



                else{
                    //custom

                    String tlist[] = new String[10];
                    String clist[] = new String[10];
                    String currenttime1="";
                    String currenttime2="";
                    String currenttime3="";
                    String currenttime4="";
                    String currenttime5="";
                    String currenttime6="";
                    String currenttime7="";
                    String currenttime8="";
                    String currenttime9="";
                    String currenttime10 ="";

                    String t1="";
                    String t2="";
                    String t3="";
                    String t4="";
                    String t5="";
                    String t6="";
                    String t7="";
                    String t8="";
                    String t9="";
                    String t10 ="";

                    String week = df.getWeek();

                    medication_time1 = df.getTime();  //get time of medication
                    String time[] = null;

                    int time_count=0;

                    int total = 0;
                    String fdate;
                    int charcount =  week.length();

                    if (charcount > 3){
                        fdate = getExactDate(meddate,noofdays*7);
                    }
                    else{
                        fdate = getExactDate(meddate,noofdays);
                    }


                    //String fdate = getExactDate(meddate,noofdays);


                    if (medication_time1.contains(",")){
                        time = medication_time1.split(",");
                         time_count = time.length;

                        if (time_count == 1){
                            t1 = time[0];

                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }
                            tlist[0] = t1;

                            clist[0] = currenttime1;


                        }


                        else if (time_count == 2){
                            t1 = time[0];
                            t2 = time[1];

                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            tlist[0] = t1;
                            tlist[1] = t2;

                            clist[0] = currenttime1;
                            clist[1] = currenttime2;


                        }

                        else if (time_count == 3){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];


                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;


                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;



                        }

                        else if (time_count == 4){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];

                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }


                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;


                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;



                        }

                        else if (time_count == 5){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];
                            t5 = time[4];

                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }

                            if (t5.contains("am") || t5.contains("pm")){
                                currenttime5 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime5 = hr + ":" + min;
                            }

                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;
                            tlist[4] = t5;


                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;
                            clist[4] = currenttime5;



                        }

                        else if (time_count == 6){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];
                            t5 = time[4];
                            t6 = time[5];


                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }

                            if (t5.contains("am") || t5.contains("pm")){
                                currenttime5 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime5 = hr + ":" + min;
                            }

                            if (t6.contains("am") || t6.contains("pm")){
                                currenttime6 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime6 = hr + ":" + min;
                            }

                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;
                            tlist[4] = t5;
                            tlist[5] = t6;

                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;
                            clist[4] = currenttime5;
                            clist[5] = currenttime6;


                        }

                        else if (time_count == 7){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];
                            t5 = time[4];
                            t6 = time[5];
                            t7 = time[6];



                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }

                            if (t5.contains("am") || t5.contains("pm")){
                                currenttime5 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime5 = hr + ":" + min;
                            }

                            if (t6.contains("am") || t6.contains("pm")){
                                currenttime6 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime6 = hr + ":" + min;
                            }


                            if (t7.contains("am") || t7.contains("pm")){
                                currenttime7 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime7 = hr + ":" + min;
                            }


                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;
                            tlist[4] = t5;
                            tlist[5] = t6;
                            tlist[6] = t7;

                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;
                            clist[4] = currenttime5;
                            clist[5] = currenttime6;
                            clist[6] = currenttime7;


                        }

                        else if (time_count == 8){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];
                            t5 = time[4];
                            t6 = time[5];
                            t7 = time[6];
                            t8 = time[7];

                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }

                            if (t5.contains("am") || t5.contains("pm")){
                                currenttime5 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime5 = hr + ":" + min;
                            }

                            if (t6.contains("am") || t6.contains("pm")){
                                currenttime6 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime6 = hr + ":" + min;
                            }


                            if (t7.contains("am") || t7.contains("pm")){
                                currenttime7 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime7 = hr + ":" + min;
                            }


                            if (t8.contains("am") || t8.contains("pm")){
                                currenttime8 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime8 = hr + ":" + min;
                            }

                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;
                            tlist[4] = t5;
                            tlist[5] = t6;
                            tlist[6] = t7;
                            tlist[7] = t8;


                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;
                            clist[4] = currenttime5;
                            clist[5] = currenttime6;
                            clist[6] = currenttime7;
                            clist[7] = currenttime8;




                        }

                        else if (time_count == 9){
                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];
                            t5 = time[4];
                            t6 = time[5];
                            t7 = time[6];
                            t8 = time[7];
                            t9 = time[8];


                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }

                            if (t5.contains("am") || t5.contains("pm")){
                                currenttime5 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime5 = hr + ":" + min;
                            }

                            if (t6.contains("am") || t6.contains("pm")){
                                currenttime6 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime6 = hr + ":" + min;
                            }


                            if (t7.contains("am") || t7.contains("pm")){
                                currenttime7 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime7 = hr + ":" + min;
                            }


                            if (t8.contains("am") || t8.contains("pm")){
                                currenttime8 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime8 = hr + ":" + min;
                            }

                            if (t9.contains("am") || t9.contains("pm")){
                                currenttime9 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime9 = hr + ":" + min;
                            }


                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;
                            tlist[4] = t5;
                            tlist[5] = t6;
                            tlist[6] = t7;
                            tlist[7] = t8;
                            tlist[8] = t9;


                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;
                            clist[4] = currenttime5;
                            clist[5] = currenttime6;
                            clist[6] = currenttime7;
                            clist[7] = currenttime8;
                            clist[8] = currenttime9;



                        }

                        else if (time_count == 10){

                            t1 = time[0];
                            t2 = time[1];
                            t3 = time[2];
                            t4 = time[3];
                            t5 = time[4];
                            t6 = time[5];
                            t7 = time[6];
                            t8 = time[7];
                            t9 = time[8];
                            t10 = time[9];


                            if (t1.contains("am") || t1.contains("pm")){
                                currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime1 = hr + ":" + min;
                            }


                            if (t2.contains("am") || t2.contains("pm")){
                                currenttime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime2 = hr + ":" + min;
                            }

                            if (t3.contains("am") || t3.contains("pm")){
                                currenttime3 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime3 = hr + ":" + min;
                            }

                            if (t4.contains("am") || t4.contains("pm")){
                                currenttime4 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime4 = hr + ":" + min;
                            }

                            if (t5.contains("am") || t5.contains("pm")){
                                currenttime5 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime5 = hr + ":" + min;
                            }

                            if (t6.contains("am") || t6.contains("pm")){
                                currenttime6 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime6 = hr + ":" + min;
                            }


                            if (t7.contains("am") || t7.contains("pm")){
                                currenttime7 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime7 = hr + ":" + min;
                            }


                            if (t8.contains("am") || t8.contains("pm")){
                                currenttime8 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime8 = hr + ":" + min;
                            }

                            if (t9.contains("am") || t9.contains("pm")){
                                currenttime9 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime9 = hr + ":" + min;
                            }

                            if (t10.contains("am") || t10.contains("pm")){
                                currenttime10 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            }
                            else{
                                currenttime10 = hr + ":" + min;
                            }

                            tlist[0] = t1;
                            tlist[1] = t2;
                            tlist[2] = t3;
                            tlist[3] = t4;
                            tlist[4] = t5;
                            tlist[5] = t6;
                            tlist[6] = t7;
                            tlist[7] = t8;
                            tlist[8] = t9;
                            tlist[9] = t10;



                            clist[0] = currenttime1;
                            clist[1] = currenttime2;
                            clist[2] = currenttime3;
                            clist[3] = currenttime4;
                            clist[4] = currenttime5;
                            clist[5] = currenttime6;
                            clist[6] = currenttime7;
                            clist[7] = currenttime8;
                            clist[8] = currenttime9;
                            clist[9] = currenttime10;
                        }






                    }

                    else{
                        // go with single time
                        //if database time contians am and pm then convert currenttime like ....

                        t1 = medication_time1;

                        time_count = 1;
                        if (t1.contains("am") || t1.contains("pm")){
                            currenttime1 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");


                        }
                        else{
                            currenttime1 = hr + ":" + min;
                        }

                    }




                    DataModel2 list1 = dh.getmonthData(medication_id); //checks the given medicine record ocurs in month or not


                    if (list1 != null){
                        String month_Date = list1.getDate();   //last date from database 18
                        String currentDate = getCurrentDate();  //current date 19
                        ArrayList<String> dd = getDatesBetween(month_Date,currentDate); // dates between

                        //Log.e("HELLO","size:"+dd.size());

                        for (int i = 0; i < dd.size()-1; i++) {
                            //Log.e("HELLO", "get:" + dd.get(i));

                            if (!DateAfter(dd.get(i),fdate)) {


                            for (int j = 0; j < time_count; j++) {  //suppose time count is 2

                                // String vtime = clist[j];
                                String mtime = tlist[j].trim();

                                int cnt = dh.getdateCount1(dd.get(i), medication_frequency, mtime);
                                //Log.e("HELLO", "ct new:" + cnt + "/" + mtime);

                                if (cnt == 0) {
                                    Date d = StringTODate(dd.get(i));
                                    String weekday = getWeekday(d);

                                    if (week.contains(weekday)) {
                                        DataModel2 dataModel2 = new DataModel2(medication_id, dd.get(i), mtime, originalm_Name, "Skip", mtime, medication_frequency, originalu_name, originalu_id);

                                        dh.insertmonthdata(dataModel2);


                                        int count  = dh.getdateCount(dd.get(i),medication_frequency);  //for current date only
                                        if (count==time_count){

                                            int ndays = dh.getnoofDays(medication_id);
                                            dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                                        }

                                    }

                                }


                            }//for


                        }//if

                        }//main for


                            for (int j = 0; j < time_count; j++) {

                                String vtime = clist[j].trim();
                                String mtime = tlist[j].trim();

                                //check which time entry is to be inserted

                                int cnt = dh.getdateCount1(currentDate,medication_frequency,mtime);


                                if (cnt == 0) {
                                    boolean ch = checktimings(vtime, mtime);
                                    Date d = StringTODate(currentDate);
                                    String weekday = getWeekday(d);

                                    if (ch && week.contains(weekday)) {
                                        if (!DateAfter(currentDate,fdate)) {
                                            DataModel2 dataModel2 = new DataModel2(medication_id, currentDate, mtime, originalm_Name, "Skip", mtime, medication_frequency, originalu_name, originalu_id);

                                            dh.insertmonthdata(dataModel2);

                                            int count  = dh.getdateCount(currentDate,medication_frequency);  //for current date only
                                            if (count==time_count){

                                                int ndays = dh.getnoofDays(medication_id);
                                                dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                                            }

                                        }

                                    }
                                }
                            }//for







                        }


                    else{
                        //first time entry

                        ArrayList<String> dates = getDatesBetween(meddate,getCurrentDate());
                        //Log.e("DBDB ::", dates.toString() );



                        for (int i = 0; i < dates.size(); i++) {
                            String date = dates.get(i);
                            if (!DateAfter(date,fdate)) {
                            if (date.equals(getCurrentDate())) {

                                for (int j = 0; j < time_count; j++) {

                                    String vtime = clist[j].trim();
                                    String mtime = tlist[j].trim();
                                    //Log.e("MED", "ch :" + vtime + "/" + mtime);
                                    boolean ch = checktimings(vtime, mtime);
                                    Date d = StringTODate(date);
                                    String weekday = getWeekday(d);

                                    //Log.e("MED", "date :" + date);

                                    //Log.e("MED", "day :" + weekday);

                                    if (ch && week.contains(weekday)) {
                                        DataModel2 dataModel2 = new DataModel2(medication_id, date, mtime, originalm_Name, "Skip", mtime, medication_frequency, originalu_name, originalu_id);

                                        dh.insertmonthdata(dataModel2);

                                    }


                                    int ct = dh.getdateCount(date,medication_frequency);
                                    if (ct == time_count){

                                            int ndays = dh.getnoofDays(medication_id);
                                            dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                                    }

                                }//for loop

                            } else {
                                for (int j = 0; j < time_count; j++) {

                                    Date d = StringTODate(date);
                                    String weekday = getWeekday(d);

                                    String mtime = tlist[j].trim();
                                    if (week.contains(weekday)) {
                                        DataModel2 dataModel2 = new DataModel2(medication_id, date, mtime, originalm_Name, "Skip", mtime, medication_frequency, originalu_name, originalu_id);

                                        dh.insertmonthdata(dataModel2);

                                    }

                                }//for

                                int ct = dh.getdateCount(date,medication_frequency);

                                if (ct == time_count) {



                                        int ndays = dh.getnoofDays(medication_id);
                                        dh.updateConfirm3(medication_id, ndays - 1, df.getLeftpills());

                                }

                            }

                        }//if


                        }//for

                    }//else
                }









                //current time should after given time


            }
        }
    }

    public Date StringTODate(String date){
        Date dt = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
             dt = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }

    public String DateTOString(String date){
        Date dt = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            dt = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        return format1.format(date);


    }

    public String getWeekday(Date date){
        String day = "";
      /*  Calendar c = Calendar.getInstance();
        c.setTime(date); // yourdate is an object of type Date*/

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE");
        day = simpleDateFormat.format(date);

      return day;
    }

    public ArrayList<String> getDatesBetween(String dateString1, String dateString2) {
        ArrayList<String> dates = new ArrayList<String>();
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        Date date2 = null;
        try
        {
            date1 = input.parse(dateString1);
            date2 = input.parse(dateString2);
            //Log.e("TTT","DATE :"+date1);
        } catch (Exception e) {
            //Log.e("TTT","DATE 22:"+e.getLocalizedMessage());
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        while (!cal1.after(cal2))
        {
            SimpleDateFormat output = new  SimpleDateFormat("dd-MM-yyyy");
            dates.add(output.format(cal1.getTime()));
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


    public String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        return java.text.DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
    }

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;
    }


    private boolean checktimings(String time, String endtime) {

        String pattern = "hh:mm aa";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            //Log.e("FGFG","tt :"+date1+"/"+date2);
            if(date1.after(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }



    public void loadFrag(Fragment fragment, boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag)
        ft.add(R.id.container,fragment);
        else

         ft.replace(R.id.container,fragment);
        ft.commit();
    }





    @Override
    public void onBackPressed() {
        int seletedItemId = bnView.getSelectedItemId();
        if (R.id.home != seletedItemId) {
            loadFrag(new AHomeFragment(),false);
            bnView.setSelectedItemId(R.id.home);
        }else
        {
            super.onBackPressed();
        }
    }

    public void hideToolBar(){

        toolbar = findViewById(R.id.toolbar);
       toolbar.setVisibility(View.GONE);
    }




    public void setcolor() {
        ColorPicker colorPicker = new ColorPicker(HomeActivity.this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                // put code
                //////////Log.e("color"," cc1 :"+color);
                String hexColor = String.format("#%06X", (0xFFFFFF & color));
            //   //Log.e("color"," cc :"+hexColor);
                if (hexColor.equals("#58AEB7")) {
                    //
                    color = Color.parseColor("#008080");
                }
                SharedPreferences prefs;
                prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("color", color);
                editor.commit();

                if (prefs.getInt("color", 0) != 0) {
                    int value = prefs.getInt("color", 0);
                    //footer1.setBackgroundColor(value);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(value);
                    }
                 /*   Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();*/



                    recreate();
                    bnView.setSelectedItemId(R.id.add);

                }


            }

            @Override
            public void onCancel() {
                // put code
            }
        });

    }
}