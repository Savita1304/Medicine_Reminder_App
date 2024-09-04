package com.vapps.medi.reminder.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.vapps.medi.reminder.Interfaces.Addchart;
import com.vapps.medi.reminder.Fragments.MyHistory;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//public class AdapterChart extends RecyclerView.Adapter<AdapterChart.ViewHolder> {

public class AdapterChart extends RecyclerView.Adapter<AdapterChart.ViewHolder> implements View.OnClickListener {

    Context context;
    DatabaseHelper db;
    Addchart listner;
    int ndays;


    private ArrayList<DataModel2> listdata1;




    public AdapterChart(ArrayList<DataModel2> listdata,Context context,Addchart listner,int nodays) {
        this.listdata1 = listdata;
        this.context=context;
        this.listner=listner;
        this.ndays = nodays;
        db=new DatabaseHelper(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.chartitemssss, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataModel2 DataModel2 = listdata1.get(position);



        holder.textView1.setText(DataModel2.getTime());
                holder.tv.setText(addDay (DataModel2.getDate()) + ", " + DataModel2.getDate());
                if (DataModel2.getStatus().equals("Yes")) {
                    holder.tvtime1.setTextColor(Color.GREEN);
                    holder.tvtime1.setText("Taken");
                    holder.imgview1.setBackground(context.getDrawable(R.drawable.righticon));
                }
        else if (DataModel2.getStatus().equals("No")) {
            holder.tvtime1.setText("Not taken ");
                    holder.tvtime1.setTextColor(Color.RED);
                   // holder.imgview1.setColorFilter(Color.GRAY);
            holder.imgview1.setBackground(context.getDrawable(R.drawable.skipped1));

        }else {
                    holder.tvtime1.setText("Skip");
                    holder.tvtime1.setTextColor(Color.GRAY);
                    holder.tvtime1.setTextColor(context.getResources().getColor(R.color.gray));
                    holder.imgview1.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                }





        holder.lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt = holder.tvtime1.getText().toString().trim();
                ////Log.e("CVCV","tc:"+txt);

                if (txt.equals("Skip")){
                    callcontext(view,holder.getAdapterPosition());
                }



            }
        });


      /*  holder.imgview2.setOnClickListener(this);
        holder.imgview3.setOnClickListener(this);
        holder.imgview4.setOnClickListener(this);
        holder.imgview5.setOnClickListener(this);
        holder.imgview6.setOnClickListener(this);
        holder.imgview7.setOnClickListener(this);
        holder.imgview8.setOnClickListener(this);
        holder.imgview9.setOnClickListener(this);
        holder.imgview10.setOnClickListener(this);
*/


    }
    public void getData(){

    }


    @Override
    public int getItemCount() {
        return listdata1.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){



        }
    }

    public void callcontext(View v,int pos ){

        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.cnxtconfirmed, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.confirm:
                        DataModel2 DataModel2 = listdata1.get(pos);
                        int total=0;

                        int pills = db.getnoofpils(DataModel2.getId());
                        if (pills > 0){
                            total = pills-1;
                        }



                        MyHistory.changeState = true;
                        db.updateConfirm2(DataModel2.getId(),"Yes",DataModel2.getTime(),DataModel2.getDate());
                        listner.click(pos,context,0);


                        db.updateConfirm4(DataModel2.getId(),total);


                        notifyDataSetChanged();
                        return true;
                    case R.id.skip:

                        DataModel2 DataModel21 = listdata1.get(pos);




                        MyHistory.changeState = true;
                        db.updateConfirm2(DataModel21.getId(),"No",DataModel21.getTime(),DataModel21.getDate());
                        listner.click(pos,context,0);


                        notifyDataSetChanged();
                        return true;

                    default:
                        return false;
                }
            }
        });
        popup.show();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgview1,imgview2,imgview3,imgview4,imgview5,imgview6,imgview7,imgview8,imgview9,imgview10;
        public TextView tv,tv1, textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10;
        public TextView tvtime1,tvtime2,tvtime3,tvtime4,tvtime5,tvtime6,tvtime7,tvtime8,tvtime9,tvtime10;
        public LinearLayout lout,header1,header2,header3,header4,header5,header6,header7,header8,header9,header10;
        public ViewHolder(View itemView) {
            super(itemView);
            header1 = itemView.findViewById(R.id.header1);
           /* header2 = itemView.findViewById(R.id.header2);
            header3 = itemView.findViewById(R.id.header3);
            header4 = itemView.findViewById(R.id.header4);
            header5 = itemView.findViewById(R.id.header5);
            header6 = itemView.findViewById(R.id.header6);
            header7 = itemView.findViewById(R.id.header7);
            header8 = itemView.findViewById(R.id.header8);
            header9 = itemView.findViewById(R.id.header9);
            header10 = itemView.findViewById(R.id.header10);*/


            this.tv = (TextView) itemView.findViewById(R.id.tvMonth);
            this.tv1 = (TextView) itemView.findViewById(R.id.tvweek);
            this.textView1 = (TextView) itemView.findViewById(R.id.tvDay1);
          /*  this.textView2 = (TextView) itemView.findViewById(R.id.tvDay2);
            this.textView3 = (TextView) itemView.findViewById(R.id.tvDay3);
            this.textView4 = (TextView) itemView.findViewById(R.id.tvDay4);
            this.textView5 = (TextView) itemView.findViewById(R.id.tvDay5);
            this.textView6 = (TextView) itemView.findViewById(R.id.tvDay6);
            this.textView7 = (TextView) itemView.findViewById(R.id.tvDay7);
            this.textView8 = (TextView) itemView.findViewById(R.id.tvDay8);
            this.textView9 = (TextView) itemView.findViewById(R.id.tvDay9);
            this.textView10 = (TextView) itemView.findViewById(R.id.tvDay10);*/

            this.tvtime1 = (TextView) itemView.findViewById(R.id.tvT1);
            this.lout =  itemView.findViewById(R.id.lout);
         //   this.tvtime2 = (TextView) itemView.findViewById(R.id.tvT2);
         //   this.tvtime3 = (TextView) itemView.findViewById(R.id.tvT3);
         /*   this.tvtime4 = (TextView) itemView.findViewById(R.id.tvT4);
            this.tvtime5 = (TextView) itemView.findViewById(R.id.tvT5);
            this.tvtime6 = (TextView) itemView.findViewById(R.id.tvT6);
            this.tvtime7 = (TextView) itemView.findViewById(R.id.tvT7);
            this.tvtime8 = (TextView) itemView.findViewById(R.id.tvT8);
            this.tvtime9 = (TextView) itemView.findViewById(R.id.tvT9);
            this.tvtime10 = (TextView) itemView.findViewById(R.id.tvT10);*/



            this.imgview1 = (ImageView) itemView.findViewById(R.id.imgview1);
           // this.imgview2 = (ImageView) itemView.findViewById(R.id.imgview2);
           /* this.imgview3 = (ImageView) itemView.findViewById(R.id.imgview3);
            this.imgview4 = (ImageView) itemView.findViewById(R.id.imgview4);
            this.imgview5 = (ImageView) itemView.findViewById(R.id.imgview5);
            this.imgview6 = (ImageView) itemView.findViewById(R.id.imgview6);
            this.imgview7 = (ImageView) itemView.findViewById(R.id.imgview7);
            this.imgview8 = (ImageView) itemView.findViewById(R.id.imgview8);
            this.imgview9 = (ImageView) itemView.findViewById(R.id.imgview9);
            this.imgview10 = (ImageView) itemView.findViewById(R.id.imgview10);*/



        }
    }

    public String addDay(String yourDate ){

        String wkk="Mon";
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date dt1= null;
        try {
            dt1 = ss.parse(yourDate);
            Calendar c = Calendar.getInstance();
            c.setTime(dt1);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            //Log.e("dayofweek==",dayOfWeek+"");
            if (dayOfWeek==1){
                wkk="Sun";
            }else if (dayOfWeek==2){
                wkk="Mon";
            }else if (dayOfWeek==3){
                wkk="Tue";
            }else if (dayOfWeek==4){
                wkk="Wed";
            }else if (dayOfWeek==5){
                wkk="Thu";
            }else if (dayOfWeek==6){
                wkk="Fri";
            }else if (dayOfWeek==7){
                wkk="Sat";
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
        //Log.e("dayofweek==",wkk+"");
        return wkk;
    }

}
