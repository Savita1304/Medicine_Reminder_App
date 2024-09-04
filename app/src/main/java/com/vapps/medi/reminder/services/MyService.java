package com.vapps.medi.reminder.services;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.text.format.DateFormat.is24HourFormat;
import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.vapps.medi.reminder.Activities.PendingActivity;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.MyBroadcastReceiver;
import com.vapps.medi.reminder.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;





public class MyService extends Service {
    DatabaseHelper recordData;
    String time,time2,customTime;
    String prevtime = "";
    String prevtime2="";
    String prevcustomtime="";
    int noofDays;
    String greaterTime;

    //public static Boolean serviceRunning = false;
    Handler h = null;
    Runnable r;
    int idn;
    private static final int ID_SERVICE = 101;
    DataModelHomeF u;
    String userName;


    @Override
    public void onCreate() {
        super.onCreate();
        recordData = new DatabaseHelper(getApplicationContext());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // c = Calendar.getInstance();
        //startForeground(idn,nm);
        //Toast.makeText(getApplicationContext(),"createservice",//Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground();
        }
       startservice();



    }

    @Nullable


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


     //   serviceRunning = true;
        return Service.START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
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
            ////Log.e("TTT","DATE :"+date1);
        } catch (Exception e) {
            ////Log.e("TTT","DATE 22:"+e.getLocalizedMessage());
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


    public boolean DateAfter(String date,String fdate){    //20   21
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(date);
            d2 = sdf.parse(fdate);
            if (d1.after(d2) || d1.equals(d2)) {

                return true;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void startservice() {



        try {
            h = new Handler();

            h.postDelayed(r = new Runnable() {
                public void run()
                {


                    //new fetch for dietplann
                    ArrayList<DataModelHomeF> list1 = recordData.getSelectedData1();

                    if (list1.size() > 0) {


                        //get current date
                     //   String cdate = getCurrentDate();

                        //do something
                        Calendar c = Calendar.getInstance();
                        String str = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                        ////Log.e("testttt==", str + "");
                        String[] ctime = str.split(":");
                        int hr = Integer.valueOf(ctime[0]);
                        ////Log.e("hr==", hr + "");
                        int min = Integer.valueOf(ctime[1]);   //always return 24 hour time
                        //convert time if system time is 24 hour.
                        String currentTime = "";
                        String currentTime2 = "";
                        String  currentCustomTime = "";
                        String snooze = "";

                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                        Date d = new Date();
                        String day = sdf.format(d);
                        String Fre,AlarmType;
                        for (int i = 0; i < list1.size(); i++) {
                            //Toast.makeText(getApplicationContext(),"On startservice",//Toast.LENGTH_SHORT).show();







                                    u = list1.get(i);






                            userName=u.getUserName();

                            Fre = u.getFrequency();
                            AlarmType=u.getAlarmType();
                            ////Log.e("Frepill==", u.getLeftpills()+"");


                            //this is foe single time..
                            if (Fre.equals("Single time a day")) {
                                noofDays = u.getNoofDays();
                                time = u.getTime1().toLowerCase();     //convert tolowecase
                                ////Log.e("Freq888==", time);
                              //  ////Log.e("Freq11==", time);


                          /*      if (time.contains("-")){ //this will execute when snooze function works.
                                    prevtime = time.split("-")[0].toLowerCase();//original time;

                                    time = time.split("-")[1].toLowerCase();//snooze time;
                                    snooze = "snooze";
                                    ////Log.e("prvtime",prevtime+":"+time);
                                }
                                else {
                                    prevtime = time;
                                    snooze = "";
                                    ////Log.e("prvtime22",prevtime);
                                }
                                */


                                if (time.contains("am") || time.contains("pm")) {
                                    currentTime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");


                                } else {
                                    currentTime = hr + ":" + min;
                                }
                                String medDate = u.getMeddate();
                                String currentDate = getCurrentDate();

                                boolean ch = DateAfter(currentDate,medDate);

                                if (time.equals(currentTime) && noofDays > 0 && ch) //
                                {



                                    final String id = u.getId();
                                    final String title = "Take your " + u.getMedicationName();
                                    Uri ring = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm11);
                                    final String ringtone = ring.toString();
                                    String uid = u.getUserid();


                                   ////Log.e("444", "event:" + uid);
                                    //////Log.e("Default==", "event:" + currentTime);
                                    greaterTime=time;
                                    ////Log.e("1daysss=",  greaterTime);
                                    addNotification(id, title, ringtone, i, snooze,"time1",AlarmType,time,userName,uid);

                                } else {
                                    // h.removeCallbacks(r);
                                }
                            }

                            //2 time..
                            else  if (Fre.equals("2 time a day")) {
                                ////Log.e("2daysss=",  "2 time a day");
                                time = u.getTime1().toLowerCase();;
                                time2=u.getTime2().toLowerCase();;
                                noofDays = u.getNoofDays();

                                greaterTime=time2;
                                ////Log.e("2daysss=",  greaterTime);
                                ////Log.e("Freq11==", time);

                           /*     //code for time
                                if (time.contains("-")){ //this will execute when snooze function works.
                                    prevtime = time.split("-")[0].toLowerCase();//original time;

                                    time = time.split("-")[1].toLowerCase();//snooze time;
                                    snooze = "snooze";
                                    ////Log.e("prvtime",prevtime+":"+time);
                                }
                                else {
                                    prevtime = time;
                                    snooze = "";
                                    ////Log.e("prvtime",prevtime);
                                }*/



                                 if (time.contains("am") || time.contains("pm")) {
                                     //12 hour
                                     // ////Log.e("Freq11 if ==", str);
                                    //currentTime = changeTime12(str);
                                    currentTime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                                    ////Log.e("Freq11 if ==", currentTime);


                                }
                                else {
                                    //24 hour

                                    currentTime = hr + ":" + min;

                                    ////Log.e("Freq11 else ==", currentTime);
                                }
                                ////Log.e("currentTime2==", time + ":" + currentTime);

                                String medDate = u.getMeddate();
                                String currentDate = getCurrentDate();

                                boolean ch = DateAfter(currentDate,medDate);

                                if (time.equals(currentTime) && noofDays > 0 && ch) //
                                    {
                                        final String id = u.getId();
                                    final String title = "Take your " + u.getMedicationName();
                                    Uri ring = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm11);
                                    final String ringtone = ring.toString();

                                    ////Log.e("Default==44", "event:" + ringtone);
                                    ////Log.e("Default==", "event:" + currentTime2);
                                        String uid = u.getUserid();
                                    addNotification(id, title, ringtone, i, snooze,"time1",AlarmType,time,userName,uid);

                                }


                            //end


                          //code for time2..


                           /*     if ( time2.contains("-")){ //this will execute when snooze function works.
                                    prevtime2 =  time2.split("-")[0].toLowerCase();//original time;

                                    time2 =  time2.split("-")[1].toLowerCase();//snooze time;
                                    snooze = "snooze";
                                    ////Log.e("prvtime",prevtime2+":"+ time2);
                                }
                                else {
                                    prevtime2 =  time2;
                                    snooze = "";
                                    ////Log.e("prvtime22",prevtime2);
                                }*/
                                if (time2.contains("am") || time2.contains("pm")) {
                                    //12 hour
                                    ////Log.e("time2 if ==", str);
                                   // currentTime2 = changeTime12(str);
                                    currentTime2 =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                                    ////Log.e("time22 if ==", currentTime2);


                                } else {
                                    //24 hour

                                    currentTime2 = hr + ":" + min;

                                    ////Log.e("time2 else ==", currentTime2);
                                }
                                ////Log.e("currentTime2==", time2 + ":" + currentTime2+ ":" + noofDays);

                                if (time2.equals(currentTime2) && noofDays > 0 && ch) //
                                    {
                                        final String id = u.getId();
                                    final String title = "Take your " + u.getMedicationName();
                                    Uri ring = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm11);
                                    final String ringtone = ring.toString();

                                    ////Log.e("Default==44", "event:" + ringtone);
                                    ////Log.e("Default==", "event:" + currentTime2);
                                        String uid = u.getUserid();
                                    addNotification(id, title, ringtone, i, snooze,"time2",AlarmType,time2,userName,uid);

                                }
                            }

                            //custom time......
                            else  if (Fre.equals("Custom")) {
                              String  getTime =u.getTime();


                               noofDays = u.getNoofDays();

                                ////Log.e("custom==", getTime);


                                ////Log.e("SSS","ctime:"+ currentCustomTime);

                                if (getTime.contains(","))
                                {
                                    String[] custime = getTime.split(",");
                                    ////Log.e("custom9669===",  custime.length+"");
                                    for ( int j=0; j<custime.length; j++)
                                    {
                                         customTime = custime[j].toLowerCase();;
                                      //  Cust_no=custime.length;
                                        greaterTime=custime[custime.length-1].toLowerCase().trim();

                                        ////Log.e("custom99===",  greaterTime);




                      /*                  //there is start code  for snooze
                                        if (customTime.contains("-")){ //this will execute when snooze function works.
                                            prevcustomtime = customTime.split("-")[0].toLowerCase();//original time;

                                            customTime = customTime.split("-")[1].toLowerCase();//snooze time;
                                            snooze = "snooze";
                                            ////Log.e("prvtime",prevcustomtime+":"+customTime);
                                        }
                                        else {
                                            prevcustomtime = customTime;
                                            snooze = "";
                                            ////Log.e("prvtime",prevcustomtime);
                                        }*/


                                     if (customTime.contains("am") || customTime.contains("pm")) {
                                            //12 hour

                                    //currentCustomTime = changeTime12(str);
                                    currentCustomTime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                                    ////Log.e("time22 if ==", currentCustomTime);


                                } else {
                                    //24 hour

                                    currentCustomTime = hr + ":" + min;

                                    ////Log.e("timecustom else ==", currentCustomTime);
                                }

                                        String medDate = u.getMeddate();
                                        String currentDate = getCurrentDate();

                                        boolean ch = DateAfter(currentDate,medDate);

                                        ////Log.e("timecustom else77 ==", customTime+":"+ currentCustomTime.toLowerCase().trim()+":"+noofDays);

                                        if ( customTime.trim().equals(currentCustomTime.trim()) && noofDays > 0 && ch) //
                                            {
                                                ////Log.e("timecustom elsettt ==", customTime+":"+ currentCustomTime+":"+noofDays);
                                    String day1=u.getWeek();
                                    ////Log.e("day1", day1);

                                    boolean match=functionWeek(day1);
                                    ////Log.e("matchoo", match+"");
                                    if (match) {

                                        final String id = u.getId();
                                        final String title = "Take your " + u.getMedicationName();
                                        Uri ring = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm11);
                                        final String ringtone = ring.toString();

                                        ////Log.e("customDefault==44", "event:" + ringtone);
                                        ////Log.e("customDefault==", "event:" + currentCustomTime);
                                        String uid = u.getUserid();
                                        addNotification(id, title, ringtone, i, snooze,"custom",AlarmType,customTime,userName,uid);
                                    }

                                }

                                    }




                                }


                                else {
                                 //   Cust_no = 1;
                                    customTime = getTime.toLowerCase();
                            /*        if (customTime.contains("-")){ //this will execute when snooze function works.
                                        prevcustomtime = customTime.split("-")[0].toLowerCase();//original time;

                                        customTime = customTime.split("-")[1].toLowerCase();//snooze time;
                                        snooze = "snooze";
                                        ////Log.e("prvtime",prevcustomtime+":"+customTime);
                                    }
                                    else {
                                        prevcustomtime = customTime;
                                        snooze = "";
                                        ////Log.e("prvtime",prevcustomtime);
                                    }*/
                                    if (customTime.contains("am") || customTime.contains("pm")) {

                                       // if (customTime.contains("AM") || customTime.contains("PM")) {
                                        //12 hour

                                        //currentCustomTime = changeTime12(str);
                                        currentCustomTime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

                                        ////Log.e("cctt if ==", currentCustomTime);


                                    } else {
                                        //24 hour

                                        currentCustomTime = hr + ":" + min;

                                        ////Log.e("cctt else ==", currentCustomTime);
                                    }

                                    ////Log.e("cctt else77 ==", customTime+":"+ currentCustomTime);
                                    String medDate = u.getMeddate();
                                    String currentDate = getCurrentDate();

                                    boolean ch = DateAfter(currentDate,medDate);

                                    if (customTime.trim().equals(currentCustomTime.trim()) && noofDays > 0 && ch) //

                                    //if (customTime.trim().equals(currentCustomTime.trim()) && noofDays > 0) //
                                    {
                                        greaterTime=customTime.trim();
                                        String day1=u.getWeek();
                                        boolean match=functionWeek(day1);
                                        ////Log.e("matchoo", match+"");
                                        if (match){
                                        final String id = u.getId();
                                        final String title = "Take your " + u.getMedicationName();
                                        Uri ring = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm11);
                                        final String ringtone = ring.toString();

                                        ////Log.e("cuscctt==44", "event:" + ringtone);
                                        ////Log.e("cuscctt==", "event:" + currentCustomTime);
                                            String uid = u.getUserid();
                                        addNotification(id, title, ringtone, i, snooze,"custom",AlarmType,customTime,userName,uid);

                                        }
                                    }

                                }
                            }







                        }   //end of for loop


                    }


                    h.postDelayed(r, 60000);
                }
            }, 60000);


        } catch (Exception e) {
            ////Log.e("Exception==",e.toString());
        }
    }

    public int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }

    public String changeTime12(String res) {
        boolean time = is24HourFormat(getApplicationContext());
        SimpleDateFormat code12Hours = new SimpleDateFormat("hh:mm"); // 12 hour format

        Date dateCode12 = null;

        String formatTwelve;
        String results;
        if (!time) {
            try {
                dateCode12 = code12Hours.parse(res); // 12 hour
            } catch (ParseException e) {
                e.printStackTrace();
                ////Log.e("e===", "e");
            }

            formatTwelve = code12Hours.format(dateCode12); // 12

            if (formatTwelve.equals(res)) {
                results = formatTwelve + " AM";
            } else {
                results = formatTwelve + " PM";
            }
            if (results.charAt(0) == '0') {
                return results.substring(1);
            }


            return results;

        } else {
            return res;
        }



       /* String[] date = {"01:11","23:11","13:12","07:01", "00:00", "12:00", "11:59"};

        for (String res: date) {

            try {
                dateCode12 = code12Hours.parse(res); // 12 hour
            } catch (ParseException e) {
                e.printStackTrace();
            }

            formatTwelve = code12Hours.format(dateCode12); // 12

            if (formatTwelve.equals(res)) {
                results = formatTwelve + " AM";
            } else {
                results = formatTwelve + " PM";
            }

            ////Log.e("result==",res + " : " + results);

        }*/


        // res = "00:00";
       /* ////Log.e("e===","e"+res);
            try {
                dateCode12 = code12Hours.parse(res); // 12 hour
            } catch (ParseException e) {
                e.printStackTrace();
                ////Log.e("e===","e");
            }

            formatTwelve = code12Hours.format(dateCode12); // 12

            if (formatTwelve.equals(res)) {
                results = formatTwelve + " AM";
            } else {
                results = formatTwelve + " PM";
            }

            ////Log.e("result==",res + " : " + results);

return results;*/


    }
   /* public void showreport(){
        //do something
        Calendar c = Calendar.getInstance();
        String str = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
        String[] ctime = str.split(":");
        int hr = Integer.valueOf(ctime[0]);
        int min = Integer.valueOf(ctime[1]);   //always return 24 hour time

        String currentTime1="";

        if (!DateFormat.is24HourFormat(this)) {
            currentTime1 =  hr%12 + ":" + min + " " + ((hr>=12) ? "PM" : "AM");
        }
        else{
            currentTime1 =  hr+":"+min;
        }





        if (currentTime1.equals("23:15")){
            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            showreportNotification(date,"23:15");
        }
        if (currentTime1.equals("11:15 PM")){
            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            showreportNotification(date,"11:15 PM");
        }
    }*/

   // int value;
    SharedPreferences prefs;


    private void startForeground() {


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);


        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.alllrmmm)
                .setPriority(PRIORITY_MIN)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();

        startForeground(ID_SERVICE, notification);

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager) {
        String channelId = UUID.randomUUID().toString();
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }


    private void addNotification(String id, String title, String path, int index, String snooze, String timeType,String AlarmType,String time,String userName,String userid)
    {


       /* if (AlarmType.equals("Alarm")){

            if (mView == null || mView.getVisibility()==View.INVISIBLE){
                getDialog(title,time,id,timeType,userName);
            }
            else{


                this.title.setText(title);
                this.taskTime.setText(time);
                this.pillName.setText(userName+" It's time to "+title);


                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setVisibility(View.INVISIBLE);
                        Intent notificationIntent = new Intent(getApplicationContext(), PendingActivity.class);
                        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //notification message will get at NotificationView
                        Bundle args = new Bundle();
                        args.putString("id", id);

                        args.putString("title", title);


                        args.putString("time", getCurrentTime());
                        args.putString("date", getCurrentDate());


                        args.putString("medicationName", u.getMedicationName());
                        args.putString("Status", "Yes");
                        args.putString("mid", u.getId());
                        args.putInt("noofDays", u.getNoofDays());
                        args.putString("greaterTime", greaterTime);
                        args.putInt("notify",u.getNotify());
                        args.putString("Pending", String.valueOf(idn));
                        args.putString("Freq", u.getFrequency());
                        args.putString("timeType", timeType);
                        args.putString("ctime", prevtime);
                        args.putString("ctime2", prevtime2);
                        args.putString("customtime", prevcustomtime);
                        args.putInt("noofpills", u.getLeftpills());
                      //  args.putInt("Cust_no", Cust_no);
                        args.putString("userid", userid);
                        args.putString("username", u.getUserName());
                        notificationIntent.putExtras(args);
                        getApplicationContext().startActivity(notificationIntent);



                    }
                });


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mView.setVisibility(View.INVISIBLE);
                        Toast.makeText(MyService.this, "close click 222", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        }else {*/




        //String path = "android.resource://com.example.myroutine/"+R.raw.audio;
        Uri uri = Uri.parse(path);
        String uuid = null;


        // Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.audio);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        uuid = UUID.randomUUID().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//             uuid = UUID.randomUUID().toString();

            NotificationChannel mChannel = new NotificationChannel(uuid,
                    uuid,
                    NotificationManager.IMPORTANCE_DEFAULT);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setSound(uri, attributes); // This is


            if (mNotificationManager != null)
                mNotificationManager.createNotificationChannel(mChannel);
        }

        int idn = new Random().nextInt(999999);

     /*   PendingIntent snoozePendingIntent ;
        ////////Log.e("idnval=","vl :"+idn);
        //apply if else condition here.

        Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
        snoozeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        snoozeIntent.setAction("SNOOZEACTION");
        snoozeIntent.putExtra("Snooze", idn);
        snoozeIntent.putExtra("id", id);
        snoozeIntent.putExtra("Freq", u.getFrequency());
        snoozeIntent.putExtra("timeType", timeType);
        snoozeIntent.putExtra("Cust_no", Cust_no);
            snoozeIntent.putExtra("userid", userid);
            snoozeIntent.putExtra("username", u.getUserName());

        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

             snoozePendingIntent =
                    PendingIntent.getBroadcast(this, idn, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            snoozePendingIntent =
                    PendingIntent.getBroadcast(this, idn, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }*/




        ////Log.e("prevtime=",  prevtime+"prevtime2"+prevtime2);
        PendingIntent dismissPendingIntent ;
        Intent dismissIntent = new Intent(this, MyBroadcastReceiver.class);
        dismissIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        dismissIntent.setAction("DISMISSACTION");
        dismissIntent.putExtra("Dismiss", idn);
        dismissIntent.putExtra("id1", id);
      //  dismissIntent.putExtra("temp", value);
        dismissIntent.putExtra("date", getCurrentDate());
        dismissIntent.putExtra("time", getCurrentTime());

        dismissIntent.putExtra("medicationName", u.getMedicationName());
        dismissIntent.putExtra("Status", "No");
        dismissIntent.putExtra("mid", u.getId());

        dismissIntent.putExtra("noofDays", u.getNoofDays());
        dismissIntent.putExtra("greaterTime", greaterTime);
        dismissIntent.putExtra("Freq", u.getFrequency());
        dismissIntent.putExtra("timeType", timeType);
       // dismissIntent.putExtra("Cust_no", Cust_no);
            dismissIntent.putExtra("userid", userid);
            dismissIntent.putExtra("username", u.getUserName());

        ////Log.e("greaterTime44121=",  greaterTime);

        //  dismissIntent.putExtra("time",time); // check only updated time
        dismissIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        // dismissPendingIntent =
                //PendingIntent.getBroadcast(this, idn, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

            dismissPendingIntent =
                    PendingIntent.getBroadcast(this, idn, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            dismissPendingIntent =
                    PendingIntent.getBroadcast(this, idn, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        PendingIntent ConfirmPendingIntent;
        ////Log.e("noofDaysss==", u.getNotify() + ":" + u.getLeftpills());
        Intent ConfirmIntent = new Intent(this, MyBroadcastReceiver.class);
        ConfirmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ConfirmIntent.setAction("ConfirmACTION");
        ConfirmIntent.putExtra("Confirm", idn);
        ConfirmIntent.putExtra("id2", id);
        ConfirmIntent.putExtra("date", getCurrentDate());
        ConfirmIntent.putExtra("time", getCurrentTime());
        ConfirmIntent.putExtra("medicationName", u.getMedicationName());
        ConfirmIntent.putExtra("Status", "Yes");
        ConfirmIntent.putExtra("mid", u.getId());
        ConfirmIntent.putExtra("noofDays", u.getNoofDays());
        ConfirmIntent.putExtra("greaterTime", greaterTime);
        ConfirmIntent.putExtra("ctime", prevtime);
        ConfirmIntent.putExtra("ctime2", prevtime2);
        ConfirmIntent.putExtra("customtime", prevcustomtime);
        ConfirmIntent.putExtra("noofpills", u.getLeftpills());
        ConfirmIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        ConfirmIntent.putExtra("Freq", u.getFrequency());
        ConfirmIntent.putExtra("timeType", timeType);
        //ConfirmIntent.putExtra("Cust_no", Cust_no);
            ConfirmIntent.putExtra("userid", userid);
            ConfirmIntent.putExtra("username", u.getUserName());
       // PendingIntent ConfirmPendingIntent =
               // PendingIntent.getBroadcast(this, idn, ConfirmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

            ConfirmPendingIntent =
                    PendingIntent.getBroadcast(this, idn, ConfirmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            ConfirmPendingIntent =
                    PendingIntent.getBroadcast(this, idn, ConfirmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


/*        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, uuid)
                        .setSmallIcon(R.drawable.alllrmmm)
                        .setContentTitle(userName+"'s Medication")

                        .setContentText(title).setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setDefaults(Notification.FLAG_AUTO_CANCEL)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX).addAction(R.drawable.alarm, "Snooze 5 Minutes",
                                snoozePendingIntent).addAction(R.drawable.alarm, "Dismiss", dismissPendingIntent).addAction(R.drawable.alarm, "Take", ConfirmPendingIntent);*/


            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, uuid)
                            .setSmallIcon(R.drawable.alllrmmm)
                            .setContentTitle(userName+"'s Medication")

                            .setContentText(title).setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setDefaults(Notification.FLAG_AUTO_CANCEL)
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MAX).addAction(R.drawable.alarm, "Dismiss", dismissPendingIntent).addAction(R.drawable.alarm, "Take", ConfirmPendingIntent);




        builder.setDeleteIntent(dismissPendingIntent);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = Build.VERSION.SDK_INT >= 20 ? pm.isInteractive() : pm.isScreenOn(); // check if screen is on
////Log.e("isScreenOn",isScreenOn+"");
        if (!isScreenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
            wl.acquire(3000); //set your time in milliseconds
        }



        /*    PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyApp::MyWakelockTag");
            wakeLock.acquire();
*/

        //Add


        //  dismissIntent.putExtra("time",time); // check only updated time


        Intent notificationIntent = new Intent(this, PendingActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("uuid", uuid);
        args.putString("title", title);
        args.putString("uri", path);
        args.putInt("index", index);
        args.putString("time", getCurrentTime());
        // args.putString("value",value);
        args.putString("date", getCurrentDate());

        args.putString("medicationName", u.getMedicationName());
        args.putString("Status", "Yes");
        args.putString("mid", u.getId());
        args.putInt("noofDays", u.getNoofDays());
        args.putString("greaterTime", greaterTime);
       // args.putInt("leftpill",u.getLeftpills());
        args.putInt("notify",u.getNotify());
        args.putString("Pending", String.valueOf(idn));
        args.putString("Freq", u.getFrequency());
        args.putString("timeType", timeType);
        args.putString("ctime", prevtime);
        args.putString("ctime2", prevtime2);
        args.putString("customtime", prevcustomtime);
        args.putInt("noofpills", u.getLeftpills());
     //   args.putInt("Cust_no", Cust_no);
            args.putString("userid", userid);
            args.putString("username", u.getUserName());
        notificationIntent.putExtras(args);


        // notificationIntent.putExtra("message", "This is a notification message");


        PendingIntent pendingIntent = PendingIntent.getActivity(this, idn, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);


        builder.setContentIntent(pendingIntent);


        Notification nm = builder.build();
        nm.defaults = Notification.FLAG_AUTO_CANCEL;
        nm.flags = Notification.FLAG_INSISTENT;   //this line wont remove notification after click.

        mNotificationManager.notify(idn, nm);

      
//        }//else
    }






    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();


        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;
    }

    public String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        return java.text.DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

    }

    public String setWeek(int num){


        String[] days = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

        String day = days[num-1];
        ////Log.e("setWeek",day);
        return day;
    }

    public boolean functionWeek(String db_day){
        ////Log.e("matched==44", db_day+"");
        String[] r = db_day.split("(?=\\p{Upper})");
        for (int i=0; i<r.length; i++) {
            ////Log.e("weekkk", r[i] + "");
            String db_day1 = r[i];


            Calendar c = Calendar.getInstance();


            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            ////Log.e("Mondaymatch111==", dayOfWeek + "");
            String days = setWeek(dayOfWeek);
            ////Log.e("matched==", days + "");
            if (db_day1.equals(days)) {
                return true;
            }



        }
        return false;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();



    }

  /*  private static final String TAG = MyService.class.getSimpleName();
    WindowManager mWindowManager;
    View mView;
    Animation mAnimation;

    private void registerOverlayReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(overlayReceiver, filter);
    }

    private void unregisterOverlayReceiver() {
        hideDialog();
        unregisterReceiver(overlayReceiver);
    }


    TextView title,taskTime,pillName,open;
    ImageView imageView;

    private void showDialog(String aTitle,String Medicine,String task_time,String id,String timeType,String userName){
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mView = View.inflate(getApplicationContext(), R.layout.fragment_overlay, null);
        mView.setTag(TAG);


        int top = getApplicationContext().getResources().getDisplayMetrics().heightPixels / 2;

        LinearLayout dialog = (LinearLayout) mView.findViewById(R.id.dialog);
         title = (TextView) mView.findViewById(R.id.Title);
        title.setText(aTitle);

         taskTime = (TextView) mView.findViewById(R.id.taskTime);
         pillName = (TextView) mView.findViewById(R.id.pillName);
         open = (TextView) mView.findViewById(R.id.open);

        taskTime.setText("Task for "+task_time);
        pillName.setText(userName +" It's time to "+ Medicine);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.setVisibility(View.INVISIBLE);
                Intent notificationIntent = new Intent(getApplicationContext(), PendingActivity.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //notification message will get at NotificationView
                Bundle args = new Bundle();
                args.putString("id", id);

                args.putString("title", Medicine);


                args.putString("time", getCurrentTime());
                args.putString("date", getCurrentDate());


                args.putString("medicationName", u.getMedicationName());
                args.putString("Status", "Yes");
                args.putString("mid", u.getId());
                args.putInt("noofDays", u.getNoofDays());
                args.putString("greaterTime", greaterTime);
                args.putInt("notify",u.getNotify());
                args.putString("Pending", String.valueOf(idn));
                args.putString("Freq", u.getFrequency());
                args.putString("timeType", timeType);
                args.putString("ctime", prevtime);
                args.putString("ctime2", prevtime2);
                args.putString("customtime", prevcustomtime);
                args.putInt("noofpills", u.getLeftpills());
              //  args.putInt("Cust_no", Cust_no);
                args.putString("userid", u.getUserid());
                notificationIntent.putExtras(args);
     getApplicationContext().startActivity(notificationIntent);



            }
        });


mView.setPadding(50,0,50,0);


        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dialog.getLayoutParams();
        if (lp != null) {
            lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = top;
        lp.bottomMargin = top;
        mView.setLayoutParams(lp);

         imageView =  mView.findViewById(R.id.close);
        lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
       lp.leftMargin = 50;
        lp.rightMargin = 50;


        imageView.setLayoutParams(lp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.setVisibility(View.INVISIBLE);
                Toast.makeText(MyService.this, "close click", Toast.LENGTH_SHORT).show();

            }
        });








        int layout_parms;
        final WindowManager.LayoutParams yourparams;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            layout_parms = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        }

        else {

            layout_parms = WindowManager.LayoutParams.TYPE_PHONE;

        }

        yourparams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_parms,
               WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON

              *//* WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                 WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                       | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                         | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON*//* ,
                PixelFormat.TRANSLUCENT);


      *//*  final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ,
                PixelFormat.RGBA_8888);*//*

       // WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,


                mView.setVisibility(View.VISIBLE);
        mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        mView.startAnimation(mAnimation);

        Uri ring = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm11);

        MediaPlayer mMediaPlayer=MediaPlayer.create(getApplicationContext(),ring);
        mMediaPlayer.start();
        mWindowManager.addView(mView, yourparams);




    }*/


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        ////////Log.e("Broadcast Listened", "Remove Recent");

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PendingIntent restartServicePendingIntent;
            //Toast.makeText(getApplicationContext(),"On Task Removed",//Toast.LENGTH_SHORT).show();
            Intent restartServiceIntent = new Intent(getApplicationContext(),
                    this.getClass());
            restartServiceIntent.setPackage(getPackageName());

            Intent intent = new Intent(getApplicationContext(), this.getClass());

            intent.setPackage(getPackageName());





            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                restartServicePendingIntent = PendingIntent.getService(
                        getApplicationContext(), 1, restartServiceIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
            }
            else
            {
                restartServicePendingIntent = PendingIntent.getService(
                        getApplicationContext(), 1, restartServiceIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT );
            }


            AlarmManager alarmService = (AlarmManager) getApplicationContext()
                    .getSystemService(Context.ALARM_SERVICE);
            alarmService.set(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 1000,
                    restartServicePendingIntent);
            serviceRunning = true;
            //Toast.makeText(getApplicationContext(),"On Task Removed"+serviceRunning,//Toast.LENGTH_SHORT).show();


        }*/
        super.onTaskRemoved(rootIntent);

    }

  /*  private void hideDialog(){
        if(mView != null && mWindowManager != null){
            mWindowManager.removeView(mView);
            mView = null;
        }
    }*/



   /* private BroadcastReceiver overlayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "[onReceive]" + action);
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
             //   showDialog("Medicine Reminder");
            }
            else if (action.equals(Intent.ACTION_USER_PRESENT)) {
                hideDialog();
            }
            else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                hideDialog();
            }
        }
    };*/

   /* public void getDialog(String title, String time,String id,String timeType,String userName) {
        boolean lockFlag;
        KeyguardManager.KeyguardLock mKeyguardLock;
        PowerManager powerManager;
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);

        if (km.inKeyguardRestrictedInputMode()) {
            lockFlag = true;
            ////Log.e("---popup", "lock");
            powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            mKeyguardLock = km.newKeyguardLock(getApplicationContext().getPackageName());
            mKeyguardLock.disableKeyguard();
            PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, getApplicationContext().getPackageName());
            wl.acquire();
        } else {
            ////Log.e("---popup", "unlock");
        }

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = Build.VERSION.SDK_INT >= 20 ? pm.isInteractive() : pm.isScreenOn(); // check if screen is on

        if (!isScreenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
            wl.acquire(3000); //set your time in milliseconds
        }


        showDialog(getString(R.string.app_name),title,time,id,timeType,userName);



    }
*/


}


