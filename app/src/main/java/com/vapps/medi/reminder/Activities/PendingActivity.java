package com.vapps.medi.reminder.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PendingActivity extends AppCompatActivity {

    ImageView clear;
    TextView alerttext, dismiss;
    String id,mid, title, path, value, uuid, time, date, medicationName, Status;
    int index, noofDays,noofpills , notify;
    String Freq,timeType,medication,status;
    DatabaseHelper recordData;
    NotificationManager nm;
    LinearLayout header, footer;
    SharedPreferences prefs;
    TextView confirm;
    String original , original1,greaterTime;
    String startTime = null;
    int themeColor=0;
    String userid = "";
    String username = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        //////Log.e("Notification","del");

        recordData = new DatabaseHelper(getApplicationContext());
      //  clear = findViewById(R.id.clear);
        confirm = findViewById(R.id.confirm);
        alerttext = findViewById(R.id.alerttext);
        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);
        dismiss = findViewById(R.id.dismiss);
        //snooze = findViewById(R.id.snooze);

        prefs = PreferenceManager.getDefaultSharedPreferences(PendingActivity.this);

        setThemecolor();







        final Bundle bundle = getIntent().getExtras();


        if (bundle != null) {


            id = bundle.getString("id");
            title = bundle.getString("title");
            path = bundle.getString("uri");
            index = bundle.getInt("index");
            value = bundle.getString("value");
            uuid = bundle.getString("uuid");
            time = bundle.getString("time");
            date = bundle.getString("date");
            medicationName = bundle.getString("medicationName");
            Status = bundle.getString("Status");
            mid = bundle.getString("mid");
            noofDays = bundle.getInt("noofDays");
            noofpills=bundle.getInt("noofpills",0);
          // leftpill = bundle.getInt("leftpill", 0);
            notify=bundle.getInt("notify",0);
             original=bundle.getString("ctime");
            original1=bundle.getString("ctime2");
            Freq = bundle.getString("Freq");
            timeType=bundle.getString("timeType");
            medication = bundle.getString("medicationName");
            status = bundle.getString("Status");
            greaterTime=bundle.getString("greaterTime");
            userid=bundle.getString("userid");
            username=bundle.getString("username");



            String Pending = bundle.getString("Pending");

            nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(Integer.parseInt(Pending));
           /* if (time.contains("-")) {
                time = time.split("-")[0].toLowerCase();
            }*/
            alerttext.setText(title);

        }


  /*      clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recordData.updateScheduleDefaulthourtime(id,time);
               *//* if (value.equals("custom")) {
                    //    new DatabaseHelper(getApplicationContext()).deleteschedule(id);
                } else {

                }*//*
                recordData.updateScheduleDefaulthourtime(id, original);
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finishAndRemoveTask();

            }
        });*/


        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


     /*               //Log.e("dismissCalling","ddd");

                if (Freq.equals("Single time a day"))
                {
                   String startTemp=recordData.gettime(id).toLowerCase();
                   if (startTemp.contains("-")){
                       startTime = startTemp.split("-")[0].toLowerCase();
                   }else {
                       startTime=startTemp;
                   }
                }
                else if (Freq.equals("2 time a day"))
                {
                    if (timeType.equals("time1")) {
                        String startTemp = recordData.gettime(id).toLowerCase();
                        if (startTemp.contains("-")) {
                            startTime = startTemp.split("-")[0].toLowerCase();
                        } else {
                            startTime = startTemp;
                        }
                    }

                    if (timeType.equals("time2")) {
                        String startTemp = recordData.gettime2(id).toLowerCase();
                        if (startTemp.contains("-")) {
                            startTime = startTemp.split("-")[0].toLowerCase();
                        } else {
                            startTime = startTemp;
                        }
                        }
                    }
                else if (Freq.equals("Custom"))
                {
                    String startTemp=recordData.getcustomtime(id).toLowerCase();
                    if (startTemp.contains("-")){
                        startTime = startTemp.split("-")[0].toLowerCase();
                    }else {
                        startTime=startTemp;
                    }
                }*/




                String ccdate = getCurrentDate();
                int ct = recordData.getdateCount1(ccdate,Freq,time);


                if (ct == 0){
                    boolean insurt=recordData.insertmonthdata(new DataModel2(id,date,time,medication,"No",time,Freq,username,userid));
                    if (insurt){
                        //Log.e("value==DismissA","Update");

                        Toast.makeText(getApplicationContext(), " insurted", Toast.LENGTH_SHORT).show();

                    }else {
                        //Log.e("value==DismissA","Update");
                        Toast.makeText(getApplicationContext(), "not  insurted", Toast.LENGTH_SHORT).show();

                    }

                    if (noofDays > 0){
                        int num=0;




                        if(greaterTime.equals(time)){

                            num=noofDays-1;





                        }
                        else{
                            num = noofDays;
                        }
                        boolean res = recordData.updateDays(mid, num);


/*

                        boolean res=recordData.updateDays(mid,num);

                        if (res){
                            Toast.makeText(getApplicationContext(), " updated", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(), "not  updated", Toast.LENGTH_SHORT).show();

                        }*/
                    }


                }



                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finishAndRemoveTask();

              //  recordData.updateScheduleDefaulthourtime(id, original);

                /*recordData.updateScheduleDefaulthourtime(id, original);
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();*/

            }
        });


/*        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Date dNow = new Date(); // Instantiate a Date object
                Calendar cal = Calendar.getInstance();
                cal.setTime(dNow);
                cal.add(Calendar.MINUTE, 5);
                dNow = cal.getTime();


                int hr = dNow.getHours();
                int min = dNow.getMinutes();



                if (Freq.equals("Single time a day"))
                {
                    String time = recordData.gettime(id).toLowerCase();
                    if (time.contains("-")){
                        time = time.split("-")[0].toLowerCase();
                    }
                    //suppose time is 11:0 AM
                    String str;
                    if ( !time.contains("am") && !time.contains("pm")) {
                        //Log.e("min444",min+"");
                        if (min<10)
                        {
                            str = hr+":"+"0"+min;
                        }else {
                            str = hr+":"+min;
                        }

                        //Log.e("min555",str+"");
                        //  String str = hr % 12 + ":" + min + " " + ((hr >= 12) ? "PM" : "AM");
                        recordData.SnoozeTimeUpdate(id,time+"-"+str);
                    }
                    else{

                        String currentTime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                        //Log.e("min5558",currentTime+"");

                        recordData.SnoozeTimeUpdate(id,time+"-"+currentTime);
                    }
                }
                else if (Freq.equals("2 time a day"))
                {
                    if (timeType.equals("time1")){


                        String time = recordData.gettime(id).toLowerCase();
                        if (time.contains("-")){
                            time = time.split("-")[0].toLowerCase();
                        }
                        //suppose time is 11:0 AM
                        String str;
                        if ( !time.contains("am") && !time.contains("pm")) {
                            //Log.e("min444",min+"");
                            if (min<10)
                            {
                                str = hr+":"+"0"+min;
                            }else {
                                str = hr+":"+min;
                            }

                            //Log.e("min555",str+"");
                            //  String str = hr % 12 + ":" + min + " " + ((hr >= 12) ? "PM" : "AM");
                            recordData.SnoozeTimeUpdate(id,time+"-"+str);
                        }
                        else{

                            String currentTime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                            //Log.e("min5558",currentTime+"");

                            recordData.SnoozeTimeUpdate(id,time+"-"+currentTime);
                        }
                    }
                    if (timeType.equals("time2")) {
                        String time2 = recordData.gettime2(id).toLowerCase();
                        //Log.e("time2==",time2);
                        if (time2 != null) {


                            if (time2.contains("-")) {
                                time2 = time2.split("-")[0].toLowerCase();
                            }
                            //suppose time is 11:0 AM
                            String str2;
                            if (!time2.contains("am") && !time2.contains("pm")) {
                                //Log.e("min444", min + "");
                                if (min < 10) {
                                    str2 = hr + ":" + "0" + min;
                                } else {
                                    str2 = hr + ":" + min;
                                }

                                //Log.e("min555", str2 + "");
                                //  String str = hr % 12 + ":" + min + " " + ((hr >= 12) ? "PM" : "AM");
                                recordData.SnoozeTimeUpdate2(id, time2 + "-" + str2);
                            } else {

                                String currentTime = ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                                //Log.e("min5558", currentTime + "");

                                recordData.SnoozeTimeUpdate2(id, time2 + "-" + currentTime);
                            }
                        }
                    }
                }


                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finishAndRemoveTask();


            }
        });*/

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tm = getCurrentTime();
                String dt = getCurrentDate();



                if (!id.isEmpty() && !dt.isEmpty() && !tm.isEmpty() && !medicationName.isEmpty() && !Status.isEmpty()) {

                    String ccdate = getCurrentDate();
                    int ct = recordData.getdateCount1(ccdate,Freq,time);

                    if (ct == 0){
                        boolean insurt = recordData.insertmonthdata(new DataModel2(id, dt, tm, medicationName, Status,tm,Freq,username,userid));
                        if (noofDays > 0){
                            int pill = 0;
                            int num = 0;
                            if(greaterTime.equals(time)){

                                num=noofDays-1;
                            }
                            else {
                                num = noofDays;
                            }

                            if (noofpills>0) {
                                pill=noofpills-1;
                            }

                           boolean res=recordData.updateDays(mid,num,pill);
                                    }









                        }
                    }














                Intent startMain = new Intent(PendingActivity.this,HomeActivity.class);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(startMain);
//                finishAndRemoveTask();
                startActivity(startMain);
                finish();
            }
        });


    }

    public String getCurrentTime() {
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setThemecolor(){
        if (prefs.getInt("color",0) != 0) {
            themeColor = prefs.getInt("color",0);
            //Log.e("themeColor",themeColor+"");

            alerttext.setTextColor(themeColor);
            dismiss.setBackgroundColor(themeColor);
          //  snooze.setBackgroundColor(themeColor);
            confirm.setBackgroundColor(themeColor);
            header.setBackgroundColor(themeColor);
            footer.setBackgroundColor(themeColor);





            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(themeColor);
            }
        }



    }

}




