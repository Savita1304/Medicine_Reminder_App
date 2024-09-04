package com.vapps.medi.reminder.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.RemoveListner;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class customHomeF  extends  ArrayAdapter<DataModelHomeF>  {

    private  int resocursLayout;
    Context context;
    ArrayList<DataModelHomeF> arrayList;
    DatabaseHelper db;
    RemoveListner removeListner;

    int noofDays;


    public customHomeF(@NonNull Context context, int resource, @NonNull ArrayList<DataModelHomeF> objects,RemoveListner removeListner) {
        super(context, resource, objects);
        this.context=context;
        this.arrayList=objects;
        this.resocursLayout=resource;
        this.removeListner=removeListner;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View view=convertView;
        if (view==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(context);
            view=vi.inflate(resocursLayout,null);
        }
        ImageView imageView = view.findViewById(R.id.hmimg1);
        TextView textView=view.findViewById(R.id.tvHmName);
        TextView textView1=view.findViewById(R.id.tvHmDec);
        TextView textView2=view.findViewById(R.id.tvHmTimer);
        ImageView imageView2 = view.findViewById(R.id.hmimg2);
        TextView textView3=view.findViewById(R.id.leftpill);
         ImageView delete = view.findViewById(R.id.delete);
         TextView  totald = view.findViewById(R.id.totalDays);
         CardView cardView = view.findViewById(R.id.hmcardV1);

        TextView  resume = view.findViewById(R.id.resume);
        db=new DatabaseHelper(context);




        DataModelHomeF model=arrayList.get(position);



            textView.setText(model.getMedicationName());
            textView1.setText(model.getDecName());
            String fre = model.getFrequency();
            if (fre.equals("Single time a day")) {
                textView2.setText(model.getTime1());
            } else if (fre.equals("2 time a day")) {
                textView2.setText(model.getTime1() + "," + model.getTime2());
            } else if (fre.equals("Custom")) {
                textView2.setText(model.getTime() + ",");
            }







            ////Log.e("leftpills22==",model.getLeftpills()+"");
            textView3.setText("left pills  " + model.getLeftpills());
           noofDays = model.getNoofDays();

            //check difference more then on days then munually update

        //get medication entry date
       /* String mdate = model.getMeddate();
        String cdate = getCurrentDate();
        ArrayList<String> allDates = getDatesBetween(mdate,cdate);
     //   db.updateConfirm3(DataModel21.getId(),total1,ndays);

        if (allDates.size() > 2){
            noofDays = model.getNoofDays()-allDates.size();
            db.updateConfirm3(model.getId(), model.getLeftpills(), noofDays);
        }
        else{
            //total = 10;   18-28
            noofDays = model.getNoofDays();


        }
*/


        if (noofDays == 0){
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.offWhite2));
            totald.setText("Days :Completed");
            totald.setTextColor(context.getResources().getColor(R.color.darkGreen));
            resume.setVisibility(View.VISIBLE);
        }
        else{
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.offWhite));
            totald.setText("Days left :"+noofDays);
            totald.setTextColor(context.getResources().getColor(R.color.darkred));
            resume.setVisibility(View.GONE);
        }




            String Shape = model.getShape();
            String AlarmType = model.getAlarmType();
            //Log.e("AlarmType==", AlarmType + Shape);
            //Log.e("Shape==", Shape);


            //  imageView.setColorFilter(context.getResources().getColor(R.color.blue));

            if (Shape.equals("0")) {
                imageView.setImageResource(R.drawable.pillsicon);
                //  imageView.setBackground(context.getDrawable(R.drawable.iii));
            } else if (Shape.equals("1")) {
                imageView.setImageResource(R.drawable.fff);

                //imageView.setBackground(context.getDrawable(R.drawable.fff));
            } else if (Shape.equals("2")) {
                imageView.setImageResource(R.drawable.hhh);

            } else if (Shape.equals("3")) {
                imageView.setImageResource(R.drawable.eyedrop);

            } else if (Shape.equals("4")) {
                imageView.setImageResource(R.drawable.iii);

            } else if (Shape.equals("5")) {
                imageView.setImageResource(R.drawable.ggg);

            } else if (Shape.equals("6")) {
                imageView.setBackground(context.getDrawable(R.drawable.ccc));
            }
            if (AlarmType.equals("Alarm")) {
                imageView2.setImageResource(R.drawable.bbbb);
            } else {
                imageView2.setImageResource(R.drawable.aaa);
            }
            setThemecolor(imageView, imageView2, delete);




            resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update database here reload list again

                 /*   db.updateResume(model.getId(), model.getFdays(), model.getFpills(),getCurrentDate1());
                    db.deletemonthRecord(model.getId());*/
                    //Log.e("POPO",":"+position);

                    removeListner.update(position);

                    //delete all previous record of this medicine from dataset month table
                }
            });






        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeListner.remove(position);
            }
        });

        return view;

    }


    public String getCurrentDate1() {
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

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();


        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;
    }
    int themeColor;
    public void setThemecolor(ImageView imageView,ImageView imageView2,ImageView delete){
        SharedPreferences prefs ;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.getInt("color",0) != 0) {
            themeColor = prefs.getInt("color",0);
            //Log.e("themeColor",themeColor+"");
            imageView.setColorFilter(themeColor);
            imageView2.setColorFilter(themeColor);
            delete.setColorFilter(themeColor);

            //textView.setTextColor(themeColor);

          //  imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.blue)));

            setNotifyOnChange(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window =((Activity) getContext()).getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(themeColor);
            }
        }


    }


    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}






