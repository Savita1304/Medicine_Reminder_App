package com.vapps.medi.reminder.Adapters;

import static android.app.PendingIntent.getActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vapps.medi.reminder.Interfaces.UpdateTime;
import com.vapps.medi.reminder.Models.Times;
import com.vapps.medi.reminder.Interfaces.RemoveListner;
import com.vapps.medi.reminder.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class customListviewAdapter extends ArrayAdapter<Times>
{

    private  int resocursLayout;
    Context context;
    ArrayList<Times> list;
    Calendar c;
    RemoveListner removeListner;
    UpdateTime updatelistener;
    public static String  t1="",t2="",t3="",t4="",t5="",t6="",t7="",t8="",t9="";

    String etSingletime,Time;
    public String timeing;


    public customListviewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Times> objects, RemoveListner removeListner,String ftime,UpdateTime listener) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
        this.resocursLayout=resource;
        this.removeListner=removeListner;
        this.timeing = ftime;
        this.updatelistener = listener;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View view=convertView;

        if (view==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(context);
            view=vi.inflate(resocursLayout,null);
        }
        ImageView imageView = view.findViewById(R.id.imgCustom);
        TextView textView=view.findViewById(R.id.etCustomTime);
        Times times = list.get(position);
        if (position != 0) {
            textView.setText(times.getTime());
        }
        else{
            view.setVisibility(View.GONE);
        }
        //Log.e("postionlist77",t1+":"+t2+":"+t3);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.e("postionlist",position+":"+list.get(position));
                list.remove(position);
                removeListner.remove(position);
                if (position==0)
                    t1="";
               else if (position==1){
                    t1="";
                    t2="";}
                else if (position==2)
                    t3="";
                else  if (position==3)
                    t4="";
                else  if (position==4)
                    t5="";
                else if (position==5)
                    t6="";
                else if (position==6)
                    t7="";
                else if (position==7)
                    t8="";
                else if (position==8)
                    t9="";


              //  //Log.e("postionlist",t1+":"+t2+":"+t3);
                notifyDataSetChanged();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker(textView,position);



            }
        });


        return view;

    }

    @Override
    public int getCount() {

        return super.getCount();
    }


    public void timepicker(TextView view1, int  position) {

        // instance of our calendar.
        final Calendar[] c1 = {Calendar.getInstance()};

        // on below line we are getting our hour, minute.
        int hour = c1[0].get(Calendar.HOUR_OF_DAY);
        int minute = c1[0].get(Calendar.MINUTE);



        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // on below line we are setting selected time
                // in our text view.
                c = Calendar.getInstance();
                Date date = c.getTime();


                SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
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

                //Log.e("LKLK:","timing :"+timeing);

                etSingletime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());


                //set time from picker..............


                //Log.e("ERER:","time :"+position);


                String prev = list.get(position).getTime();

                updatelistener.settime(etSingletime,position);


            /*    if (position==1) {
                    t1 = etSingletime.trim();
                    list.get(position).setTime(t1);
                }
                if (position==2){
                    t2=etSingletime.trim();
                    list.get(position).setTime(t2);
                }
                if (position==3){
                    t3=etSingletime.trim();
                    list.get(position).setTime(t3);
                }
                if (position==4){
                    t4=etSingletime.trim();
                    list.get(position).setTime(t4);
                }
                if (position==5){
                    t5=etSingletime.trim();
                    list.get(position).setTime(t5);
                }
                if (position==6){
                    t6=etSingletime.trim();
                    list.get(position).setTime(t6);
                }
                if (position==7){
                    t7=etSingletime.trim();
                    list.get(position).setTime(t7);
                }
                if (position==8){
                    t8=etSingletime.trim();
                    list.get(position).setTime(t8);
                }
                if (position==9){
                    t9=etSingletime.trim();
                    list.get(position).setTime(t9);
                }*/






              /*  //Log.e("position==",timeing+"") ;
                String temp =  ","+etSingletime;
                if (timeing==null){
                    timeing=temp;
                    view1.setText(etSingletime);
                }else if (timeing!=null && timeing.contains(etSingletime)|| AddFragment.time!=null && AddFragment.time.contains(etSingletime)){
                    Toast.makeText(context, "Conflicting time", Toast.LENGTH_SHORT).show();

                }
                else if (timeing!=null && !timeing.contains(etSingletime)){
                    timeing= ","+t1+","+t2+","+t3+","+t4+","+t5+","+t6+","+t7+","+t8+","+t9;

                    view1.setText(etSingletime);

                }*/


                 //Log.e("timeing",    ""+checkClock());

                if (!checkClock()) {

                    view1.setText(etSingletime);
                }
                else{
                    Toast.makeText(context,"Time should be different",Toast.LENGTH_SHORT).show();
                    updatelistener.settime(prev,position);
                }




            }
        }, hour, minute, false);
        // at last we are calling show to
        // dispinnerAlarmlay our time picker dialog.
        timePickerDialog.show();
    }

    public boolean checkClock(){
        boolean correct = false;


      //  //Log.e("LKLK:","sz:"+list.size());

        for (int i = 0; i < list.size(); i++) {
            Times times = list.get(i);
            String ctime = times.getTime();

            //Log.e("LKLK:","ctime:"+i+"/"+ctime);

            for (int j = i+1; j < list.size(); j++) {
                Times times1 = list.get(j);
                String ctime1 = times1.getTime();
              //  //Log.e("LKLK:","ctime1:"+ctime1);

                if (ctime.equals(ctime1)){
                   // Toast.makeText(context,"Conflicting time22",Toast.LENGTH_SHORT).show();
                    correct = true;
                    break;
                }
               /* else{
                    correct = true;
                }*/
            }


        }
        return correct;
    }

}