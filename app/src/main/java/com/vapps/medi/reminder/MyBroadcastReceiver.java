package com.vapps.medi.reminder;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModel2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyBroadcastReceiver extends BroadcastReceiver {

    NotificationManager nm;
    DatabaseHelper recordData;
    int value;
    String id,date,time,medication,status,mid,greaterTime,Freq,timeType;
    int noofDays;
    int noofpills;
    String fulltime;
    String startTime = null;


    String userid = "";
    String username = "";



    @Override
    public void onReceive(Context context, Intent intent) {
        recordData = new DatabaseHelper(context);



        String action = intent.getAction();

          if (action.equals("ConfirmACTION")){
            value = intent.getIntExtra("Confirm",0);
            id = intent.getStringExtra("id2");
            date = intent.getStringExtra("date");
            time = intent.getStringExtra("time").toLowerCase();
            medication = intent.getStringExtra("medicationName");
            status = intent.getStringExtra("Status");
            mid= intent.getStringExtra("mid");
            noofDays=intent.getIntExtra("noofDays",0);
            noofpills=intent.getIntExtra("noofpills",0);
            greaterTime=intent.getStringExtra("greaterTime");
            String original=intent.getStringExtra("ctime");
            String original1=intent.getStringExtra("ctime2");
            String cusOri=intent.getStringExtra("customtime");
            Freq=intent.getStringExtra("Freq");
            timeType=intent.getStringExtra("timeType");
            userid=intent.getStringExtra("userid");
            username = intent.getStringExtra("username");



            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(value);



            String ccdate = getCurrentDate();
            int ct = recordData.getdateCount1(ccdate,Freq,time);

            if (ct == 0){
                boolean insurt=recordData.insertmonthdata(new DataModel2(id,date,time,medication,status,startTime,Freq,username,userid));


                if (noofDays > 0){
                    int pill = 0;
                    int num = 0;
                    if (Freq.equals("Single time a day")){
                        num=noofDays-1;


                            if (noofpills>0){
                                pill=noofpills-1;
                                boolean res=recordData.updateDays(mid,num,pill);
                            }

                    }
                    else if (Freq.equals("2 time a day"))
                    {
                            if(greaterTime.trim().equals(time.trim()))
                            {
                                num=noofDays-1;

                            }
                            else {
                                num = noofDays;
                            }

                        if (noofpills>0){
                            pill=noofpills-1;
                            boolean res=recordData.updateDays(mid,num,pill);
                        }


                    }
                    else if (Freq.equals("Custom"))
                    {
                        if(greaterTime.trim().equals(time.trim())){

                            num=noofDays-1;



                        }
                        else {
                            num = noofDays;
                        }

                        if (noofpills>0){
                            pill=noofpills-1;
                            boolean res=recordData.updateDays(mid,num,pill);
                        }

                    }





                }
            }








        }





        else{
            //Log.e("dismissCalling","ddd");
            value = intent.getIntExtra("Dismiss",0);
            id = intent.getStringExtra("id1");


            date = intent.getStringExtra("date");
            time = intent.getStringExtra("time").toLowerCase();
            medication = intent.getStringExtra("medicationName");
            status = intent.getStringExtra("Status");
            mid= intent.getStringExtra("mid");
            noofDays=intent.getIntExtra("noofDays",0);
            username = intent.getStringExtra("username");
            userid = intent.getStringExtra("userid");
            greaterTime = intent.getStringExtra("greaterTime");

            Freq=intent.getStringExtra("Freq");



            //Log.e("TRTR","time :"+greaterTime.trim());



            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(value);

          /*  if (Freq!=null) {


                if (Freq.equals("Single time a day")) {
                    String startTemp = recordData.gettime(id).toLowerCase();
                    if (startTemp.contains("-")) {
                        startTime = startTemp.split("-")[0].toLowerCase();
                    } else {
                        startTime = startTemp;
                    }
                }
                else if (Freq.equals("2 time a day")) {
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
                else if (Freq.equals("Custom")) {
                    String startTemp = recordData.getcustomtime(id).toLowerCase();
                    if (startTemp.contains("-")) {
                        startTime = startTemp.split("-")[0].toLowerCase();
                    } else {
                        startTime = startTemp;
                    }
                }
            }*/
              //Log.e("FGGF:","f 22 :"+value);

            String ccdate = getCurrentDate();
            //Log.e("FGGF:","f 11 :"+ccdate);
            int ct = recordData.getdateCount1(ccdate,Freq,time);

            //Log.e("FGGF:","f :"+noofDays+"/"+time);

            if (ct == 0){
                boolean insurt=recordData.insertmonthdata(new DataModel2(id,date,time,medication,"No",time,Freq,username,userid));



                if (noofDays > 0){

                    int num = 0;
                    if(greaterTime.equals(time)){
                        //Log.e("FGGF:","eqeq :"+noofDays+"/"+mid);
                        num=noofDays-1;





                    }

                    else {
                        num=noofDays;
                    }

                    boolean res = recordData.updateDays(mid, num);




                }



            }




        }


    }




    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();


        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;
    }




}

