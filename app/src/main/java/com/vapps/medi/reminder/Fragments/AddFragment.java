package com.vapps.medi.reminder.Fragments;


import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t1;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t2;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t3;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t4;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t5;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t6;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t7;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t8;
import static com.vapps.medi.reminder.Adapters.customListviewAdapter.t9;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.UpdateTime;
import com.vapps.medi.reminder.Adapters.SpinAdapter;
import com.vapps.medi.reminder.Adapters.customListviewAdapter;
import com.vapps.medi.reminder.Models.DataModel;
import com.vapps.medi.reminder.Models.Times;
import com.vapps.medi.reminder.Interfaces.RemoveListner;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.databinding.FragmentAddBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class AddFragment extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener, RemoveListner, UpdateTime {

    customListviewAdapter adapter1s;
    Spinner sp;
    Spinner sp1;
    Spinner spother;
    LinearLayout L1, L11, L12, L13, lin_btn;
    String temp="",temp1="",temp2="",temp3="",temp4="",temp5="",temp6="";
    String freq = "Single time a day", etSingletime, etTime1, etTime2, etTime3, Shape="0",alarmType="Notification", weeklyTime="", custom="",hours="8",  pills,notify;
    public static String  time="";
    // TextInputEditText Singletime,etT1,etT2,etT3,medcoName,decName;
    boolean ispillsValid,isnotifyValid, isNameValid, isDecValid, isMonday,isTuesday,isWednesday,isThursday,isFriday,isSaturday,isSunday;
    DatabaseHelper db;
    String freString="single";
    int  counterDays=0;
    Button btn;
    ArrayList<Times>linearArray;
    AlertDialog.Builder builder;
    FragmentAddBinding binding;
    View myview ;
    NumberPicker numberPicker, numPicker;
    int noofDays,leftpills ,noovDay2;

    String medicineName,descriptionName;
    TextView etXHours;
    private  static final String CHANNEL_ID ="my channel";
    private  static final int NOTIFICATION_ID = 100;
    private  static final int mNotificationId =  200;
    Calendar c;
    public int height=0;
    int percent=0;
    int themeColor=0;
    ImageView img1;
    SharedPreferences prefs ;
    String username;
    LinearLayout linearLayout;

    int checkcount = 1;


    String spiSelectedId = "";
int cnt=0;

    ArrayList<DataModel> spdata; //= {"Myself",  "Other"};

    ArrayList<DataModel> userlist = new ArrayList<>();

    boolean changing = false;
    boolean changing1 = false;
    boolean state = false;

    String prevtime1="";
    String prevtime2=  "";
    CardView refillcard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        ImageView imageView = getActivity().findViewById(R.id.setting);
        imageView.setVisibility(View.GONE);


         linearLayout = getActivity().findViewById(R.id.toolbar);
        linearLayout.setVisibility(View.VISIBLE);

        height=0;

        refillcard = view.findViewById(R.id.cardRefill);



        LinearLayout lay = getActivity().findViewById(R.id.chartheader);
        lay.setVisibility(View.GONE);



        ImageView logo = getActivity().findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);

        TextView header = getActivity().findViewById(R.id.header);
        header.setVisibility(View.VISIBLE);


        AdView adView = getActivity().findViewById(R.id.adView);
        adView.setVisibility(View.VISIBLE);
        // enableAutoStart();

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {
            askPermission();
        }*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getActivity().getPackageName();
            PowerManager pm = (PowerManager) getActivity().getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }


       // //Log.e("000","kk :"+checktimings(" 4:01 pm","4:00 pm"));
        //binding=FragmentAddBinding.inflate(inflater,container,false);
        binding = FragmentAddBinding.bind(view);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        setThemecolor();


        SharedPreferences shared = getActivity().getSharedPreferences("medicine", MODE_PRIVATE);


        myview=view.findViewById(R.id.ll1);
        //  changeTime12();

        db = new DatabaseHelper(getActivity());
        spdata=new ArrayList<>();

        btn = view.findViewById(R.id.btn);
        //Log.e("trimmrr",Calendar.getInstance().getTime()+"");

        //etXHours=view.findViewById(R.id.etXHours);
        sp = view.findViewById(R.id.spinner);
        sp1 = view.findViewById(R.id.spinnerAlarm);
        spother = view.findViewById(R.id.spother);
        L1 = view.findViewById(R.id.L1);
        L11 = view.findViewById(R.id.L11);
        L12 = view.findViewById(R.id.L12);
        L13 = view.findViewById(R.id.L13);

        linearArray=new ArrayList<>();

        //  custSpinner = view.findViewById(R.id.custSpinner);

        binding.cardV11.setVisibility(View.GONE);


      /*  Singletime = view.findViewById(R.id.Singletime);
        etT1 = view.findViewById(R.id.etT1);
        etT2 = view.findViewById(R.id.etT2);
        etT3 = view.findViewById(R.id.etT3);*/

        lin_btn = view.findViewById(R.id.linear);
        binding.Sl1.setOnClickListener(this);
        binding.Sl2.setOnClickListener(this);
        binding.Sl3.setOnClickListener(this);
        binding.Sl4.setOnClickListener(this);
        binding.Sl5.setOnClickListener(this);
        binding.Sl6.setOnClickListener(this);
        binding.Sl7.setOnClickListener(this);

        // set time staticly in timepicker..
        // this is for single time..
        Calendar cd = Calendar.getInstance();
        etSingletime = DateFormat.getTimeInstance(DateFormat.SHORT).format(cd.getTime());
        binding.Singletime.setText(etSingletime.toLowerCase());

        //2 time..
        noofDays=2;
        binding.noofDays.setText(noofDays+"");
        counterDays=noofDays;
        binding.etpills.setText(counterDays+"");

        etTime1 = DateFormat.getTimeInstance(DateFormat.SHORT).format(cd.getTime());
        binding.etT1.setText(etTime1.toLowerCase());
        prevtime1  =etTime1;



        //add 5 min more here
        etTime2 = addTime(5).toLowerCase();
      //  etTime2 = DateFormat.getTimeInstance(DateFormat.SHORT).format(cd.getTime());
        binding.etT2.setText(etTime2.toLowerCase());
        prevtime2 = etTime2;

        noofDays=2;
        binding.noofDays2.setText(noofDays+"");

        //custom..
        noofDays=2;
        binding.tvXdays.setText(noofDays+"");

        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(cd.getTime()).toLowerCase();
        binding.ettimeeCustom.setText(time.toLowerCase());
        prevcustom = time;
        Times times = new Times();
        times.setTime(time);
        linearArray.add(times);



        binding.Singletime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker(binding.Singletime);

            }
        });

        binding.etT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepickernew(binding.etT1);

            }
        });

        binding.etT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker2(binding.etT2);

            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.frequencyitem, R.layout.simple_item);
        adapter.setDropDownViewResource(R.layout.drop_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                freq = adapterView.getItemAtPosition(i).toString();
                if (freq.equals("Single time a day")) {
                    freString="single";
                    L1.setVisibility(View.VISIBLE);
                    L11.setVisibility(View.GONE);
                    L12.setVisibility(View.GONE);
                    L13.setVisibility(View.GONE);
                   /* binding.etpills.setEnabled(false);
                    binding.etpills.setClickable(false);*/
                    binding.cardV11.setVisibility(View.GONE);
                    noofDays=Integer.parseInt(binding.noofDays.getText().toString());
                    counterDays=noofDays;
                    binding.etpills.setText(counterDays+"");


                } else if (freq.equals("2 time a day")) {
                    freString="2time";
                    L1.setVisibility(View.GONE);
                    L11.setVisibility(View.VISIBLE);
                    L12.setVisibility(View.VISIBLE);
                    L13.setVisibility(View.VISIBLE);
                   /* binding.etpills.setEnabled(false);
                    binding.etpills.setClickable(false);*/
                    binding.cardV11.setVisibility(View.GONE);
                    noofDays=Integer.parseInt(binding.noofDays2.getText().toString());
                    //Log.e("noofDays666",noofDays+"");
                    counterDays=2*noofDays;
                    binding.etpills.setText(counterDays+"");


                }
                else if (freq.equals("Custom")) {
                    // custom();

                    freString="custom";
                    L1.setVisibility(View.GONE);
                    L11.setVisibility(View.GONE);
                    L12.setVisibility(View.GONE);
                    L13.setVisibility(View.GONE);
//                    binding.etpills.setEnabled(false);
//                    binding.etpills.setClickable(false);
                    binding.cardV11.setVisibility(View.VISIBLE);

                    noofDays=Integer.parseInt(binding.tvXdays.getText().toString());
                    //Log.e("noofDays666",noofDays+"");
                    //counterDays=(linearArray.size()+1)*noofDays;
                    counterDays=(linearArray.size())*noofDays;
                    binding.etpills.setText(counterDays+"");


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.alarmType, R.layout.simple_item);
        adapter1.setDropDownViewResource(R.layout.drop_item);
        sp1.setAdapter(adapter1);
        sp1.setClickable(false);
        sp1.setEnabled(false);



     /*   sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                alarmType = adapterView.getItemAtPosition(i).toString();
                //calling a function for notification

                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getNotification();
                }*//*
                // }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });*/


        //spother spinner array...

        //spdata.add("Others");



        userlist = db.getMemberName();




            for (int i=0; i<userlist.size(); i++){

                DataModel dm = userlist.get(i);
                spdata.add(dm);

            }







        SpinAdapter adapter2 = new SpinAdapter(this.getActivity(), R.layout.simple_item, spdata);
        adapter2.setDropDownViewResource(R.layout.drop_item);
        spother.setAdapter(adapter2);

        spother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                username = spdata.get(i).getName();


                spiSelectedId = spdata.get(i).getId();


                //Log.e("IDD","NAme :"+username+":"+spiSelectedId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });






        binding.noofDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Days=customNumberPicker(binding.noofDays);
                //   binding.noofDays.setText(Days);
               //   noofDays = Integer.parseInt(Days);

                //Log.e("NBNB",":"+Days);

                // //Log.e("Days",Days);
            }
        });

        binding.noofDays2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customNumberPicker(binding.noofDays2) ;
            }
        });


        binding.tvtemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getActivity(), "78 registered", Toast.LENGTH_SHORT).show();

                custom1();
            }
        });

        binding.tvXdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(), "78 registered", Toast.LENGTH_SHORT).show();
             String str=   customNumberPicker1(binding.tvXdays);
             //Log.e("str3333",str);

            }
        });

        binding.tvW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!state){
                    binding.LLForweek.setVisibility(View.VISIBLE);
                }
                else{
                    binding.LLForweek.setVisibility(View.GONE);
                }

                state = !state;


            }
        });

        binding.ettimeeCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepickerCust(binding.ettimeeCustom);

            }
        });

        // linearArray.add("abc");
  /*      customListviewAdapter adapter1s = new customListviewAdapter(getActivity(),R.layout.itemcustom,linearArray,this,time);
        binding.listview.setAdapter(adapter1s);*/

        binding.addrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //int percent= getHeightofscreen(getActivity());
                percent= getScreenResolution(getActivity());



                //Log.e("percenthh",percent+""); // int percent= getScreenResolution(getActivity());
                if (linearArray.size()<10)
                {


                    //Log.e("linearArray size 11", linearArray.size() + "");

                    if (linearArray.size() == 1){
                    /*    Times times = new Times();
                        time = time.toLowerCase();
                        //Log.e("linearArray size ch", time + "");
                        times.setTime(time);
                        linearArray.add(times);*/



                        Times times1 = new Times();
                        t1 = addTime(5).toLowerCase();
                    //    //Log.e("linearArray size ch1", linearArray.size() + "");
                        // t1 = "9:00 AM".toLowerCase();
                        times1.setTime(t1);
                        linearArray.add(times1);

                       percent= getScreenResolution(getActivity())*2;
                    }



                 /*       if (linearArray.size() == 1) {
                            t1 = addTime(5).toLowerCase();
                            //Log.e("linearArray size ch1", linearArray.size() + "");
                           // t1 = "9:00 AM".toLowerCase();
                            times.setTime(t1);
                            linearArray.add(times);


                        } */

                        else if (linearArray.size() == 2) {
                        Times times = new Times();
                          //  //Log.e("linearArray size ch2", linearArray.size() + "");
                            t2 = addTime(6).toLowerCase();
                            times.setTime(t2);
                            linearArray.add(times);

                        } else if (linearArray.size() == 3) {
                        Times times = new Times();
                           // //Log.e("linearArray size ch4", linearArray.size() + "");
                            t3 = addTime(7).toLowerCase();
                            times.setTime(t3);
                            linearArray.add(times);

                        } else if (linearArray.size() == 4) {
                        Times times = new Times();
                            t4 = addTime(8).toLowerCase();
                            times.setTime(t4);
                            linearArray.add(times);

                        } else if (linearArray.size() == 5) {
                        Times times = new Times();
                            t5 = addTime(9).toLowerCase();
                            times.setTime(t5);
                            linearArray.add(times);

                        } else if (linearArray.size() == 6) {
                        Times times = new Times();
                            t6 = addTime(10).toLowerCase();
                            times.setTime(t6);
                            linearArray.add(times);

                        } else if (linearArray.size() == 7) {
                        Times times = new Times();
                            t7 = addTime(11).toLowerCase();
                            times.setTime(t7);
                            linearArray.add(times);

                        } else if (linearArray.size() == 8) {
                        Times times = new Times();
                            t8 = addTime(12).toLowerCase();
                            times.setTime(t8);
                            linearArray.add(times);

                        } else if (linearArray.size() == 9) {
                        Times times = new Times();
                            t9 = addTime(13).toLowerCase();
                            times.setTime(t9);
                            linearArray.add(times);

                        }
                    //Log.e("linearArray size", linearArray.size() + "");
                        height += percent;
                        binding.listview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));


//                        counterDays = (linearArray.size() + 1) * noofDays;
                    counterDays = (linearArray.size()) * noofDays;
                        binding.etpills.setText(counterDays + "");







                    adapter1s = new customListviewAdapter(getActivity(),R.layout.itemcustom,linearArray,AddFragment.this,time,AddFragment.this);
                    binding.listview.setAdapter(adapter1s);
                    adapter1s.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(getActivity(),"can't add more time",Toast.LENGTH_SHORT).show();
                }






            }
        });







        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());

                String mid = UUID.randomUUID().toString();


                pills = binding.etpills.getText().toString().trim();


                if (binding.medcoName.getText() == null || binding.medcoName.getText().toString().isEmpty()) {
                    isNameValid = false;
                    binding.medcoNameError.setError("Enter medicine name");
                } else {
                    isNameValid = true;
                    binding.medcoNameError.setErrorEnabled(false);
                    medicineName = binding.medcoName.getText().toString().trim();
                }
                if (descriptionName == null || descriptionName.isEmpty()) {
                    descriptionName = "fever";
                   /* isDecValid = false;
                    binding.decNameError.setError("Enter Description ");*/
                } else {
                   /* isDecValid = true;
                    binding.decNameError.setErrorEnabled(false);*/
                    descriptionName = binding.decName.getText().toString().trim();
                }
               /* if (binding.etpills == null || binding.etpills.getText().toString().isEmpty()) {
                    ispillsValid = false;
                    binding.pillsNameError.setError("please enter  current pills no");
                } else {
                    ispillsValid = true;
                    binding.pillsNameError.setErrorEnabled(false);

                }*/
                if (refillcard.getVisibility() == View.VISIBLE){
                    if (binding.etnotify == null || binding.etnotify.getText().toString().isEmpty()) {
                        isnotifyValid = false;
                        binding.notifyNameError.setError("Enter notify days");
                    } else {
                        notify = binding.etnotify.getText().toString().trim();
                        int pillno = Integer.parseInt(pills);
                        int notifyno = Integer.parseInt(notify);
                        //Log.e("Medicine55", pillno + "" + notifyno);

                        if (notifyno < pillno) {
                            isnotifyValid = true;
                            binding.notifyNameError.setErrorEnabled(false);
                        } else {

                            isnotifyValid = false;
                            binding.notifyNameError.setError("Notify no should be less then current pills no.(" + pills + ")");


                        }
                    }

            }

                else{
                    //put direct entries here
                    isnotifyValid = true;
                    if (freq.equals("Single time a day")){
                        pills = String.valueOf(noofDays);
                    }
                    else if (freq.equals("2 time a day")){
                        pills = String.valueOf(noofDays*2);
                    }
                    else{
                        if (!getSelectedTime().isEmpty() || getSelectedTime() != ""){
                            time= getSelectedTime();
                        }
                        String ntime11 = time.toLowerCase();
                        String ctime11[] = ntime11.split(",");
                        pills = String.valueOf(noofDays*ctime11.length);
                    }

                    notify = String.valueOf(noofDays);
                }





               /* boolean Avi = isAvailable(medicineName);
                if (Avi) {
                    Toast.makeText(getActivity(), "Already registered", Toast.LENGTH_SHORT).show();

                } else {*/
                if (isNameValid && isnotifyValid) {
                    etTime1=binding.etT1.getText().toString().trim();
                    etTime2=binding.etT2.getText().toString().trim();




                    // //Log.e("freString==",freString+"");
                    boolean db1 = false;
                    /*if (timeing!=null){
                       // time= time+timeing;
                        time= time+","+getSelectedTime();
                    }*/

                   if (!getSelectedTime().isEmpty() || getSelectedTime() != ""){
                       time= getSelectedTime();
                   }



                    //Log.e("tp1==",time);
                    if (username.equals("Myself") || username.equals("Others")){
                        username = shared.getString("name", "");
                    }



                    //Log.e("noofdaysprint",noofDays+"");

                    String meddate = getCurrentDate();
                    String net1 = etSingletime.toLowerCase();
                    String net2 = etTime2.toLowerCase();
                    String ntime = time.toLowerCase();
                    String prev = "";

                    if (freq.equals("Custom")){
                        String ctime[] = ntime.split(",");
                        for (int i = 0; i < ctime.length; ++i) {

                            String tp1 = ctime[i];  //  12:53 pm

                            //Log.e("tp1=::",i+":"+ctime[i]);

                            //6:59 am ,12:53 pm ,4:59 am ,10: pm

                            //6:59 am ,12:53 pm ,4:59 am ,10: pm

                            for (int j = i+1; j < ctime.length; ++j) {
                                String tp2 = ctime[j];
                                //Log.e("TP1 :",tp1+"/"+tp2);
                                boolean ch = checktimings(ctime[i],tp2);  //afer time  6:59 am
                               // String tmp = tp1;
                                if (ch) {


                                    prev =  ctime[i];   //6:00
                                    ctime[i] = ctime[j];   //0 4:18
                                    ctime[j] = prev;      //2 6:00
                                    //4:17


                               /* for (int k = 0; k < ctime.length; k++) {
                                //Log.e("FBFB884 :",""+ctime[k]);
                                     }*/

                                }

                                //Log.e("TP122 :",j+"/"+ctime[j]);

                            }//j





                        }//i

                        //sorting complete insert to database

                        String tc = "";

                        for (int i = 0; i < ctime.length; i++) {
                            //Log.e("FBFB","tc"+ctime[i]);
                            tc = tc+ctime[i]+",";
                        }
                        //Log.e("FBFB","tc1:"+tc);
                     /*   if (refillcard.getVisibility() == View.VISIBLE){
                            db1 = db.insertMedicationdata(freString, mid, medicineName, descriptionName, Shape, freq, net1, net2, hours, weeklyTime, tc, noofDays, alarmType, pills, notify, username, spiSelectedId, meddate);

                        }
                        else{
                            db1 = db.insertMedicationdata(freString, mid, medicineName, descriptionName, Shape, freq, net1, net2, hours, weeklyTime, tc, noofDays, alarmType, String.valueOf(noofDays), String.valueOf(noofDays), username, spiSelectedId, meddate);

                        }*/

                        db1 = db.insertMedicationdata(freString, mid, medicineName, descriptionName, Shape, freq, net1, net2, hours, weeklyTime, tc, noofDays, alarmType, pills, notify, username, spiSelectedId, meddate);




                    }

                   else if (freq.equals("2 time a day")) {
                        boolean ch = checktimings(net1, net2);
                        if (ch) {
                            db1 = db.insertMedicationdata(freString, mid, medicineName, descriptionName, Shape, freq, net2, net1, hours, weeklyTime, ntime, noofDays, alarmType, pills, notify, username, spiSelectedId, meddate);

                        } else {
                            db1 = db.insertMedicationdata(freString, mid, medicineName, descriptionName, Shape, freq, net1, net2, hours, weeklyTime, ntime, noofDays, alarmType, pills, notify, username, spiSelectedId, meddate);

                        }

                    }

                    else{
                        db1 = db.insertMedicationdata(freString, mid, medicineName, descriptionName, Shape, freq, net1, net2, hours, weeklyTime, ntime, noofDays, alarmType, pills, notify, username, spiSelectedId, meddate);

                    }

                    if (db1) {

                       // timeing="";

                        Toast.makeText(getActivity(),"Medicine Notification will start tomorrow",Toast.LENGTH_SHORT).show();


                        AHomeFragment.tmp = spother.getSelectedItemPosition();
                        AHomeFragment.newInstance();


                        BottomNavigationView bnView=getActivity().findViewById(R.id.bnView);
                        bnView.setSelectedItemId(R.id.home);
                    }


                    SimpleDateFormat ss = new SimpleDateFormat("HH:mm");
                    try {
                        Date date= ss.parse(etSingletime);
                        //Log.e("Date==",date.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        long ml = date.getTime();

                        //Log.e("Date==",ml+"");

                    }catch (Exception e)
                    {
                        //Log.e("Dateex==",e.toString());
                    }

                }else {
                 /*   if (!timecheck){
                        Toast.makeText(getActivity(), " Add time after current time", Toast.LENGTH_SHORT).show();
                    }
                    else{

                    }*/

                    Toast.makeText(getActivity(), "Fields with (*) are mandatory", Toast.LENGTH_SHORT).show();


                }
                // }
            }
        });





        binding.etpills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }




            @Override
            public void afterTextChanged(Editable s) {

                if (!changing && binding.etpills.getText().toString().startsWith("0")){
                    changing = true;
                    binding.etpills.setText(binding.etpills.getText().toString().replace("0", ""));
                }
                changing = false;
                if( binding.etpills.getText().length()>0)
                {


                    binding.pillsNameError.setError(null);

                }
                else {
                    binding.pillsNameError.setError("Enter number of pills");
                }
            }
        });






        binding.medcoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }




            @Override
            public void afterTextChanged(Editable s) {
                if( binding.medcoName.getText().length()>0)
                {


                    binding.medcoNameError.setError(null);

                }
                else {
                    binding.medcoNameError.setError("Enter medicine name");
                }
            }
        });

        binding.etnotify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }




            @Override
            public void afterTextChanged(Editable s) {

                if (!changing1 && binding.etnotify.getText().toString().startsWith("0")){
                    changing1 = true;
                    binding.etnotify.setText(binding.etnotify.getText().toString().replace("0", ""));
                }
                changing1 = false;
                if( binding.etnotify.getText().length()>0)
                {

//
                    binding.notifyNameError.setError(null);

                }
                else {
                    binding.notifyNameError.setError("Enter notify days");

                }
            }
        });

        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // if (checkcount > 1) {
                    // checked
                    isMonday = isChecked;

                    if (isMonday) {
                        //Log.e("isMonday", isMonday + "");
                        temp = "Mon";




                    } else {




                        temp = "";
                    }
                    weeklyTime = temp + temp1 + temp2 + temp3 + temp4 + temp5 + temp6;
                    binding.tvW.setText(weeklyTime);
                    isvalied();

                //}
            }
        });


        binding.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              //  if (checkcount > 1) {
                    isTuesday = b;
                    if (isTuesday) {

                        temp1 = "Tue";





                    } else {

                        temp1 = "";


                    }
                    //Log.e("temp1", "isTuesday" + isTuesday);

                    weeklyTime = temp + temp1 + temp2 + temp3 + temp4 + temp5 + temp6;
                    binding.tvW.setText(weeklyTime);
                    //Log.e("weeklyTime", weeklyTime + "");
                    isvalied();

               // }
            }
        });


        binding.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // checked
                isWednesday=isChecked;
                if (isWednesday){
                    checkcount++;
                    temp2="Wed";

                }else {
                    temp2="";
                    binding.tvW.setText(weeklyTime);
                }
                weeklyTime=temp+temp1+temp2+temp3+temp4+temp5+temp6;
                binding.tvW.setText(weeklyTime);

                isvalied();
            }

        });

        binding.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // checked
                isThursday=isChecked;
                if (isThursday){
                    checkcount++;
                    temp3="Thu";

                }else {
                    temp3="";

                }
                weeklyTime=temp+temp1+temp2+temp3+temp4+temp5+temp6;
                binding.tvW.setText(weeklyTime);

                isvalied();
            }

        });

        binding.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // checked
                isFriday=isChecked;
                if (isFriday){
                    checkcount++;
                    temp4="Fri";

                }else {
                    temp4="";


                }
                weeklyTime=temp+temp1+temp2+temp3+temp4+temp5+temp6;
                binding.tvW.setText(weeklyTime);

                isvalied();
            }

        });

        binding.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // checked
                isSaturday=isChecked;
                if (isSaturday){
                    checkcount++;
                    temp5="Sat";
                }else {
                    temp5="";
                }
                weeklyTime=temp+temp1+temp2+temp3+temp4+temp5+temp6;
                binding.tvW.setText(weeklyTime);

                isvalied();
            }

        });

        binding.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // checked
                isSunday=isChecked;
                if (isSunday){
                    checkcount++;
                    temp6="Sun";

                }else {
                    temp6="";


                }
                weeklyTime=temp+temp1+temp2+temp3+temp4+temp5+temp6;
                binding.tvW.setText(weeklyTime);

                isvalied();
            }

        });

        binding.checkBox.setChecked(true);





        return view;


    }


    public String  addTime(int temp){


        /*    Calendar c = Calendar.getInstance();
            String tt =  java.text.DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
            Log
*/

        String ttime = "";
        Date dNow = new Date( ); // Instantiate a Date object
        Calendar cal = Calendar.getInstance();
        String ctime= java.text.DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            dNow = format.parse(ctime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(dNow);
        cal.add(Calendar.MINUTE, temp);
        dNow = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("h:mm aa");
        ttime = format1.format(dNow);


        return ttime;
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

    public String getCurrentDate() {
      /*  Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;*/

        Calendar calendar = Calendar.getInstance();
       // Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(tomorrow);
        return date1;
    }




    public void setThemecolor(){
        if (prefs.getInt("color",0) != 0) {
            themeColor = prefs.getInt("color",0);
            //Log.e("themeColor",themeColor+"");
            binding.img1.setColorFilter(themeColor);
            binding.img2.setColorFilter(themeColor);
            binding.img3.setColorFilter(themeColor);
            binding.img4.setColorFilter(themeColor);
            binding.img5.setColorFilter(themeColor);
            binding.img6.setColorFilter(themeColor);

            binding.desc.setColorFilter(themeColor);
            binding.pill.setColorFilter(themeColor);//
            binding.freq.setColorFilter(themeColor);
            binding.noti.setColorFilter(themeColor);
            binding.pillkill.setColorFilter(themeColor);
            binding.onetwo.setColorFilter(themeColor);

            linearLayout.setBackgroundColor(themeColor);


            binding.otherimg.setColorFilter(themeColor);

            GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custom_button);
            shape.setColor(themeColor);
            binding.btn.setBackground(shape);

            GradientDrawable shape1=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custombackground);
            shape1.setColor(themeColor);


            binding.Sl1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custombackground));
            binding.img1.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(themeColor);
            }
        }
        else {
           /* binding.img1.setColorFilter(R.color.brown);
            binding.img2.setColorFilter(R.color.brown);
            binding.img3.setColorFilter(R.color.brown);
            binding.img5.setColorFilter(R.color.brown);
            binding.img6.setColorFilter(R.color.brown);

            binding.desc.setColorFilter(R.color.brown);
            binding.pill.setColorFilter(R.color.brown);//
            binding.freq.setColorFilter(R.color.brown);
            binding.noti.setColorFilter(R.color.brown);
            binding.pillkill.setColorFilter(R.color.brown);
            binding.onetwo.setColorFilter(R.color.brown);*/

            GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custom_button);
            shape.setColor(ContextCompat.getColor(getActivity(),R.color.brown));
            binding.btn.setBackground(shape);

            GradientDrawable shape1=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custombackground);
            shape1.setColor(ContextCompat.getColor(getActivity(),R.color.brown));

            binding.Sl1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custombackground));
            binding.img1.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor( ContextCompat.getColor(getActivity(),R.color.brown));
            }
        }


    }






    public void custom1() {
        //Log.e("custom1 calling","custom1 calling");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Medicine time");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.activity_custom, null);
        builder.setView(customLayout);
        //  EditText etcaln = customLayout.findViewById(R.id.etcaln);
        // EditText ettimee = customLayout.findViewById(R.id.ettimee);
        // EditText endbtn1 = customLayout.findViewById(R.id.endbtn1);
       /* etcaln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(view);
            }
        });*/
       /* ettimee.setOnClickListener(view ->
        {
            timepicker(ettimee);

        });*/

     /*   endbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(endbtn1);
            }
        });*/

        numberPicker = customLayout.findViewById(R.id.numberpicker);

        numberPicker.setMaxValue(12);
        numberPicker.setMinValue(0);
        String[] pickerVals;
        pickerVals = new String[] {"0.5 hours", "1 hours ", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours", "9 hours", "10 hours", "11 hours", "12 hours"};
        numberPicker.setDisplayedValues(pickerVals);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), "test"+numberPicker.getValue(), Toast.LENGTH_SHORT).show();
                binding.tvtemp.setText(pickerVals[numberPicker.getValue()]);

                hours=pickerVals[numberPicker.getValue()];


                // send data from the
                // AlertDialog to the Activity
                // EditText editText = customLayout.findViewById(R.id.editText);
                //sendDialogDataToActivity(editText.getText().toString());
                // Toast.makeText(getActivity(), "Successfully added", Toast.LENGTH_SHORT).show();
            }
        });

        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String customNumberPicker(EditText v) {
        //Log.e("custom1 calling","custom1 calling");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        builder.setTitle("Select day ");


        final View customLayout = getLayoutInflater().inflate(R.layout.custom_numberpicker, null);
        builder.setView(customLayout);

        numPicker = customLayout.findViewById(R.id.numberpicker1);


        numPicker.setMaxValue(100);
        numPicker.setMinValue(1);
        String[] pickerVals = new String[100];
        //  pickerVals = new String[] {"1 day ", "2 day", "3 day", "4 day", "5 day", "6 day", "7 day", "8 day", "9 day", "10 day", "11 day", "12 day", "13 day", "14 day", "15 day", "16 day", "17 day", "18 day", "19 day", "20 day", "21 day", "22 day", "23 day", "24 day", "25 day", "26 day", "27 day", "28 day", "29 day", "30 day"};


        for (int i =0; i<100; i++){
            pickerVals[i] =( i+1)+"";
        }
        // Toast.makeText(getActivity(), "test"+pickerVals.length, Toast.LENGTH_SHORT).show();

        numPicker.setDisplayedValues(pickerVals);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tet = pickerVals[numPicker.getValue()-1];
                noofDays = Integer.parseInt(pickerVals[numPicker.getValue()-1]);

                if (noofDays == 1){
                    refillcard.setVisibility(View.GONE);
                }
                else{
                    refillcard.setVisibility(View.VISIBLE);
                }
                v.setText(tet);
                if (freq.equals("Single time a day")) {
                    counterDays = noofDays;
                }else if (freq.equals("2 time a day")) {
                    counterDays = 2*noofDays;
                }
                binding.etpills.setText(counterDays+"");
                //Log.e("Days",noofDays+"");
                //Toast.makeText(getActivity(), "test"+ noofDays, Toast.LENGTH_SHORT).show();


            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();


        return pickerVals[numPicker.getValue()-1];
    }

    public String customNumberPicker1(EditText v) {
        //Log.e("custom1 calling","custom1 calling");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        builder.setTitle("Select day ");


        final View customLayout = getLayoutInflater().inflate(R.layout.custom_numberpicker, null);
        builder.setView(customLayout);

        numPicker = customLayout.findViewById(R.id.numberpicker1);

        numPicker.setMaxValue(100);
        numPicker.setMinValue(1);
        String[] pickerVals = new String[100];
        //  pickerVals = new String[] {"1 day ", "2 day", "3 day", "4 day", "5 day", "6 day", "7 day", "8 day", "9 day", "10 day", "11 day", "12 day", "13 day", "14 day", "15 day", "16 day", "17 day", "18 day", "19 day", "20 day", "21 day", "22 day", "23 day", "24 day", "25 day", "26 day", "27 day", "28 day", "29 day", "30 day"};


        for (int i =0; i<100; i++){
            pickerVals[i] =( i+1)+"";
        }
        // Toast.makeText(getActivity(), "test"+pickerVals.length, Toast.LENGTH_SHORT).show();

        numPicker.setDisplayedValues(pickerVals);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tet = pickerVals[numPicker.getValue()-1];
                noofDays = Integer.parseInt(pickerVals[numPicker.getValue()-1]);

                if (noofDays == 1){
                    refillcard.setVisibility(View.GONE);
                }
                else{
                    refillcard.setVisibility(View.VISIBLE);
                }

//                counterDays=(linearArray.size()+1)*noofDays;
                counterDays=(linearArray.size())*noofDays;
                binding.etpills.setText(counterDays+"");
                v.setText(tet);



            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();


        return pickerVals[numPicker.getValue()-1];
    }

   /* public void customForHours() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Medicine time");

        // set the custom layout
        Spinner spforHours;
        final View customLayout = getLayoutInflater().inflate(R.layout.activity_custom, null);
        builder.setView(customLayout);

        spforHours= customLayout.findViewById(R.id.spforHours);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.remind_every, R.layout.simple_item);
        adapter2.setDropDownViewResource(R.layout.drop_item);
        spforHours.setAdapter(adapter2);
        spforHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // alarmType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
   boolean timecheck = false;
    public void timepicker(EditText view1) {

        // instance of our calendar.
        final Calendar[] c1 = {Calendar.getInstance()};

        // on below line we are getting our hour, minute.
        int hour = c1[0].get(Calendar.HOUR_OF_DAY);
        int minute = c1[0].get(Calendar.MINUTE);



        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.TimePickerTheme,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // on below line we are setting selected time
                // in our text view.
                c = Calendar.getInstance();
                Date date = c.getTime();


                SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                String date1= ss.format(date);
                String dd[] = date1.split("-");
                int d = Integer.parseInt(dd[0]);
                int m = Integer.parseInt(dd[1]);
                int y = Integer.parseInt(dd[2]);

                c.set(Calendar.DAY_OF_MONTH,d);
                c.set(Calendar.MONTH,m);
                c.set(Calendar.YEAR,y);

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);

                etSingletime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

                //Log.e("ETSIN","time :"+getCurrentTime(etSingletime));


//
//                if (etSingletime!=null && (etSingletime.contains(etTime2) || etSingletime.contains(prevtime2) )){
//                    etSingletime = prevtime1;
//                    Toast.makeText(getActivity(),"Time should be different",Toast.LENGTH_SHORT).show();
//
//                }else {
//                    view1.setText(etSingletime);
//                }


               /* timecheck = checktimings(etSingletime,getCurrentTime(etSingletime));
                if (timecheck){
                    view1.setText(etSingletime);
                }
                else{
                    etSingletime = "";
                    Toast.makeText(getActivity(),"Add time after current time",Toast.LENGTH_SHORT).show();
                }*/

                view1.setText(etSingletime);


                //Log.e("hourOfDay", etSingletime);


            }
        }, hour, minute, false);
        // at last we are calling show to
        // dispinnerAlarmlay our time picker dialog.
        timePickerDialog.show();

    }



    public void timepickernew(EditText view1) {

        // instance of our calendar.
        final Calendar[] c1 = {Calendar.getInstance()};

        // on below line we are getting our hour, minute.
        int hour = c1[0].get(Calendar.HOUR_OF_DAY);
        int minute = c1[0].get(Calendar.MINUTE);



        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.TimePickerTheme,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // on below line we are setting selected time
                // in our text view.
                c = Calendar.getInstance();
                Date date = c.getTime();


                SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                String date1= ss.format(date);
                String dd[] = date1.split("-");
                int d = Integer.parseInt(dd[0]);
                int m = Integer.parseInt(dd[1]);
                int y = Integer.parseInt(dd[2]);

                c.set(Calendar.DAY_OF_MONTH,d);
                c.set(Calendar.MONTH,m);
                c.set(Calendar.YEAR,y);

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);

                etSingletime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

                //Log.e("ETSIN","time :"+getCurrentTime(etSingletime));



                if (etSingletime!=null && (etSingletime.contains(etTime2) || etSingletime.contains(prevtime2) )){
                    etSingletime = prevtime1;
                    Toast.makeText(getActivity(),"Time should be different",Toast.LENGTH_SHORT).show();

                }else {
                    view1.setText(etSingletime);
                }


               /* timecheck = checktimings(etSingletime,getCurrentTime(etSingletime));
                if (timecheck){
                    view1.setText(etSingletime);
                }
                else{
                    etSingletime = "";
                    Toast.makeText(getActivity(),"Add time after current time",Toast.LENGTH_SHORT).show();
                }*/

                //  view1.setText(etSingletime);


                //Log.e("hourOfDay", etSingletime);


            }
        }, hour, minute, false);
        // at last we are calling show to
        // dispinnerAlarmlay our time picker dialog.
        timePickerDialog.show();

    }


    public String getCurrentTime(String time){
        Calendar c = Calendar.getInstance();
        String str = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        String currenttime = "";


        //Log.e("CVB","tm :"+str);

        String[] ctime = str.split(":");
        int hr = Integer.valueOf(ctime[0]);
        int min = Integer.valueOf(ctime[1]);   //always return 24 hour time

        if (time.contains("am") || time.contains("pm")){
            // currenttime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");
            currenttime =  ((hr > 12) ? hr % 12 : hr) + ":" + (min < 10 ? ("0" + min) : min) + " " + ((hr >= 12) ? "pm" : "am");

            //  currenttime = currenttime;
            //Log.e("YYY","st :"+currenttime);
        }
        else{
            currenttime = hr + ":" + min;
        }

        return  currenttime;
    }


    public void timepicker2(EditText view1) {

        // instance of our calendar.
        final Calendar[] c1 = {Calendar.getInstance()};

        // on below line we are getting our hour, minute.
        int hour = c1[0].get(Calendar.HOUR_OF_DAY);
        int minute = c1[0].get(Calendar.MINUTE);



        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // on below line we are setting selected time
                // in our text view.
                c = Calendar.getInstance();
                Date date = c.getTime();


                SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                String date1= ss.format(date);
                String dd[] = date1.split("-");
                int d = Integer.parseInt(dd[0]);
                int m = Integer.parseInt(dd[1]);
                int y = Integer.parseInt(dd[2]);

                c.set(Calendar.DAY_OF_MONTH,d);
                c.set(Calendar.MONTH,m);
                c.set(Calendar.YEAR,y);

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);

                etTime2 = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
                if (etTime2!=null && (etTime2.contains(etSingletime) || etTime2.contains(prevtime1))){
                    etTime2 = prevtime2;
                    Toast.makeText(getActivity(),"Time should be different",Toast.LENGTH_SHORT).show();

                }else {
                    view1.setText(etTime2);
                }






                //  //Log.e("hourOfDay", c + ":" + etSingletime);


            }
        }, hour, minute, false);
        // at last we are calling show to
        // dispinnerAlarmlay our time picker dialog.
        timePickerDialog.show();
    }

    String prevcustom = "";

    public void timepickerCust(EditText view1) {

        // instance of our calendar.
        final Calendar[] c1 = {Calendar.getInstance()};

        // on below line we are getting our hour, minute.
        int hour = c1[0].get(Calendar.HOUR_OF_DAY);
        int minute = c1[0].get(Calendar.MINUTE);



        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // on below line we are setting selected time
                // in our text view.
                c = Calendar.getInstance();
                Date date = c.getTime();


                SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                String date1= ss.format(date);
                String dd[] = date1.split("-");
                int d = Integer.parseInt(dd[0]);
                int m = Integer.parseInt(dd[1]);
                int y = Integer.parseInt(dd[2]);

                c.set(Calendar.DAY_OF_MONTH,d);
                c.set(Calendar.MONTH,m);
                c.set(Calendar.YEAR,y);

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);

                time = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

                //Log.e("pPPR :","mm :"+time);
                //Log.e("pPPR :","ll :"+prevcustom);


                //update time to linear array

                linearArray.remove(0);
                Times times = new Times();
                times.setTime(time);
                linearArray.add(0,times);


                if (linearArray.size() > 1){

                    if (!checkClock()){

                        view1.setText(time);
                    }
                    else{
                        linearArray.remove(0);
                        Times times1 = new Times();
                        times1.setTime(prevcustom);
                        linearArray.add(0,times1);
                        Toast.makeText(getActivity(),"Time should be different",Toast.LENGTH_SHORT).show();
                        time = prevcustom;
                        view1.setText(prevcustom);
                    }
                }



//                view1.setText(time);



            }
        }, hour, minute, false);
        // at last we are calling show to
        // dispinnerAlarmlay our time picker dialog.
        timePickerDialog.show();
    }


    public boolean checkClock(){
        boolean correct = false;


        //  //Log.e("LKLK:","sz:"+list.size());

        for (int i = 0; i < linearArray.size(); i++) {
            Times times = linearArray.get(i);
            String ctime = times.getTime();

            //Log.e("LKLK:","NNN:"+i+"/"+ctime);

            for (int j = i+1; j < linearArray.size(); j++) {
                Times times1 = linearArray.get(j);
                String ctime1 = times1.getTime();

                if (ctime.equals(ctime1)){
                    correct = true;
                    break;
                }

            }


        }
        return correct;
    }


    public void addButton(){



        LinearLayout ll = new LinearLayout(getActivity());
        ImageView img=new ImageView(getActivity());
        Button btn = new Button(getActivity());

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(8, 8, 8, 8);

        img.setLayoutParams(params1);
        img.getLayoutParams().height=45;
        img.getLayoutParams().width=45;





        img.setImageResource(R.drawable.donotdisturb);//put image
        ll.addView(img);
       /* img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(18, 18, 18, 18);
        // params1.weight = 0.1f;



        btn.setText(" 12:00 PM");
        btn.setTextColor(getContext().getResources().getColor(R.color.gray));

        btn.setPadding(4,4,4,4);
        btn.setBackground(getContext().getDrawable(R.drawable.border));
        btn.setLayoutParams(params2);
        btn.getLayoutParams().height=60;

        btn.setGravity(Gravity.LEFT);


        ll.addView(btn);
        lin_btn.addView(ll);
        // linearArray.add(ll);
        //binding.listview.
        //Log.e("size oo linearArray",linearArray.size()+"");
        //AlertDialogExample
       /* public void custom() {
            builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("Do you want to close this application ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // finish();
                            Toast.makeText(getActivity(), "you choose yes action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getActivity(), "you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("AlertDialogExample");
            alert.show();



        }*/
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.Sl1:
                SelectCard(view);
                break;
            case R.id.Sl2:
                SelectCard(view);
                break;
            case R.id.Sl3:
                SelectCard(view);
                break;
            case R.id.Sl4:
                SelectCard(view);
                break;
            case R.id.Sl5:
                SelectCard(view);
                break;
            case R.id.Sl6:
                SelectCard(view);
                break;
            case R.id.Sl7:
                SelectCard(view);
                break;
        }
    }

    public void SelectCard(View v) {
        ArrayList<View> list = new ArrayList<>();
        list.add(binding.Sl1);
        list.add(binding.Sl2);
        list.add(binding.Sl3);
        list.add(binding.Sl4);
        list.add(binding.Sl5);
        list.add(binding.Sl6);
        list.add(binding.Sl7);

        ArrayList<View> list1 = new ArrayList<>();
        list1.add(binding.img1);
        list1.add(binding.img2);
        list1.add(binding.img3);
        list1.add(binding.img4);
        list1.add(binding.img5);
        list1.add(binding.img6);
        list1.add(binding.img7);
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = (ImageView) list1.get(i);
            //Log.e("mdmm",list.get(i).getId()+ "=="+ v.getId()+"");
            if (list.get(i).getId() == v.getId())
            {

                Shape=i+"";


                GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custombackground);
                //Log.e("themeColor1s",themeColor+"");
                //Log.e("themeColor",prefs.getInt("color",0)+"");
                if (prefs.getInt("color",0) == 0) {
                    shape.setColor(ContextCompat.getColor(getActivity(),R.color.brown));

                }else {
                    shape.setColor(themeColor);
                }

                v.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custombackground));
                imageView.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint

            } else {
                list.get(i).setBackgroundResource(0);
                //Log.e("themeColor2s",themeColor+"");
                //Log.e("themeColor",prefs.getInt("color",0)+"");
                if (prefs.getInt("color",0) == 0) {
                   // imageView.setColorFilter(R.color.brown, android.graphics.PorterDuff.Mode.MULTIPLY);
                    imageView.setColorFilter(getActivity().getResources().getColor(R.color.brown));
                }else {
                  //  imageView.setColorFilter(themeColor, android.graphics.PorterDuff.Mode.MULTIPLY);
                    imageView.setColorFilter(themeColor);

                }

            }
        }
    }

    public boolean isAvailable(String medicineName) {
        boolean Avi = false;
        ArrayList<String> list = db.getAllName();
        for (int i = 0; i < list.size(); i++) {
            //Log.e("list.get(i)", list.get(i));
            if (list.get(i).equals(medicineName)) {
                Avi = true;
                break;
            }

        }
        return Avi;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {


    }
    public void isvalied()
    {
        if(!isMonday && !isTuesday && !isWednesday && !isThursday && !isFriday && !isSaturday && !isSunday){
            binding.LLForweek.setVisibility(View.GONE);
//            binding.tvW.setText("");
            weeklyTime ="MonTueWedThuFriSatSun";
            binding.tvW.setText(weeklyTime);

        }
    }



    public void remove1(){
        percent= getScreenResolution(getActivity());
        if (linearArray.size() == 1){
            height-=percent*2;
//            linearArray.clear();
        }
        else{
            height-=percent;
        }

        binding.listview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        counterDays=(linearArray.size())*noofDays;
        binding.etpills.setText(counterDays+"");
    }


    @Override
    public void remove(int pos) {

        remove1();
    }

    @Override
    public void update(int pos) {

        //update time here


    }



/*    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE){
            if(!Settings.canDrawOverlays(getActivity())){
                askPermission();
            }
        }
    }

    private void askPermission() {
        Intent intent= new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getActivity().getPackageName()));
        startActivityForResult(intent,ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
    }*/


    private int getScreenResolution(Context context)
    {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        //Log.e("heightuuu",height+"");

        if (height>1700 && height<1800){
            height=height/12;
        }else if (height>1800 && height<2200){
            height=height/20;
        }else {
            height=height/15;
        }


        //Log.e("heightbtn",height+"");
        return height;
    }

    public static int getHeight(Context mContext){
        int height=0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if(Build.VERSION.SDK_INT>12){
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        }
        else{
            height = display.getHeight();  // Deprecated
        }
        //Log.e("heighttt",height+"");
        return height;
    }


    public  int getHeightofscreen(Context mContext) {
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        Toast.makeText(mContext.getApplicationContext(), toastMsg, Toast.LENGTH_LONG).show();
        return screenSize;
    }

    String getSelectedTime()
    {
        List<String> list = new ArrayList<>();
        for(int i=0;i<linearArray.size();i++){
            list.add(linearArray.get(i).getTime());
        }
      String  getTime = TextUtils.join(", ", list);

return getTime;
    }

    @Override
    public void settime(String time, int pos) {

        //Log.e("NNN","update :"+time);

        linearArray.remove(pos);
        Times times = new Times();
        times.setTime(time);
        linearArray.add(pos,times);
        adapter1s.notifyDataSetChanged();


    }
}









 /*  binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicineName=binding.medcoName.getText().toString();
                String descriptionName=binding.decName.getText().toString();

                if (medicineName== null || medicineName.isEmpty() ){
                    isNameValid = false;
                    binding.medcoNameError.setError("Enter medicine name");
                }else {
                    isNameValid=true;
                    binding.medcoNameError.setErrorEnabled(false);
                }
                if (descriptionName == null || descriptionName.isEmpty()){
                    isDecValid = false;
                    binding.decNameError.setError("Enter Description ");
                }else{
                    isDecValid=true;
                    binding.decNameError.setErrorEnabled(false);
                }

                if (isNameValid && isDecValid )
                {
                    db.insertMedicationdata(medicineName, descriptionName, "Shapename", "Frequencyname", "T1", "T2", "T3", "Alarm");
                }
        });

*/








