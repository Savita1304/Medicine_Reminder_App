package com.vapps.medi.reminder.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.Addchart;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.databinding.ListItemBinding;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyListAdapterR extends RecyclerView.Adapter<MyListAdapterR.ViewHolder> {
    private ArrayList<DataModelHomeF> listdata;
    ListItemBinding binding;
    Context context;
    DatabaseHelper db;
    Addchart addchart;

    ArrayList<DataModel2> listC;

    ArrayList<DataModel2> demo;
    Date d;


    public MyListAdapterR(ArrayList<DataModelHomeF> listdata, Context context, Addchart addchart) {
        this.listdata = listdata;
        this.context = context;
        db = new DatabaseHelper(context);
        this.addchart = addchart;
        demo = new ArrayList<>();


    }


    public String[] getWeekDays(int week) {
        String[] cweek = new String[7];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, week);
        //calendar.set(Calendar.WEEK_OF_MONTH, week);
//        for (int i = 0; i < 7; i++) {

        for (int i = 6; i >= 0; i--) {
            // cweek[i] = String.valueOf(calendar.getTime().getDay());

            String ff = new SimpleDateFormat("EE").format(calendar.getTime());
            cweek[i] = ff;
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            //Log.e("CCC", "WEEK :" + cweek[i]);
        }

        return cweek;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    int num=14;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        DataModelHomeF myRecListData = listdata.get(position);





        if (myRecListData.getType().equals("Month")) {
            holder.tvTxtmonth.setText(myRecListData.getMedicationName());
            //Log.e("myreccc22", myRecListData.getType() + position);
            holder.card1.setVisibility(View.VISIBLE);
            holder.card2.setVisibility(View.GONE);

            String id = myRecListData.getId();
            String uid = myRecListData.getUserid();
            listC = db.getSelectedMonthData(id,uid);

            janCount = 0;
            febCount = 0;
            marCount = 0;
            aprCount = 0;
            mayCount = 0;
            juneCount = 0;
            julyCount = 0;
            augCount = 0;
            sepCount = 0;
            octCount = 0;
            novCount = 0;
            decbCount = 0;
            for (int i = 0; i < listC.size(); i++) {
                String Status = listC.get(i).getStatus();
                String date = listC.get(i).getDate();
                String time = listC.get(i).getTime();

                //Log.e("medicationName44", listC.get(i).getMedicationName() + ":" + Status);
                if (Status.equals("Yes")) {
                    dateToYear(date, holder);
                }

            }


        }

        else if (myRecListData.getType().equals("Days")) {


            // holder.daylist.setVisibility(View.GONE);
            holder.card1.setVisibility(View.GONE);
            holder.card2.setVisibility(View.VISIBLE);
            String id = myRecListData.getId();
            String Freq = myRecListData.getFrequency();
            String uid = myRecListData.getUserid();

            //Log.e("DDD","UI:"+uid+"/"+id);
            listC = db.getSelectedMonthData(id,uid);

            if (listC.size() == 0){
                holder.card1.setVisibility(View.GONE);
                holder.card2.setVisibility(View.GONE);
            }


            if (Freq.equals("Single time a day")) {
                //Log.e("myreccc", myRecListData.getMedicationName());
                holder.tvTxtdays.setText(myRecListData.getMedicationName() + " (1 time a day)");

                holder.textViewArrow.setVisibility(View.GONE);
                binding.dayslist2.setVisibility(View.GONE);
                //Log.e("MMM1111", "DD :" + listC.size());
                if (listC.size()>0)
                {
                int daysSize = listC.size() - 1;
                String date = listC.get(daysSize).getDate();


                //Log.e("MMM", "DD :" + date);

                //convert date to get week
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    d = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar c = Calendar.getInstance();
                c.setTime(d); // yourdate is an object of type Date
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // th


                String[] weeks = getWeekDays(dayOfWeek);
                //Log.e("MMM", "WW :" + weeks);

                if (weeks.length > 0) {
                    holder.binding.l1.setText(weeks[0]);
                    holder.binding.l2.setText(weeks[1]);
                    holder.binding.l3.setText(weeks[2]);
                    holder.binding.l4.setText(weeks[3]);
                    holder.binding.l5.setText(weeks[4]);
                    holder.binding.l6.setText(weeks[5]);
                    holder.binding.l7.setText(weeks[6]);


                }


                //Log.e("fffsss", listC.size() + "");
              //  String last7days = getCalculatedDate(date, "dd-MM-yyyy", -7); // It will gives you date before 10 days from current date
               // //Log.e("print7days", last7days + ":" + date);

                demo = db.getDaysData(id,uid,7);

//                demo = db.getdays(id, last7days, date);
                //Log.e("fff", demo.size() + "");
                for (int j = 0; j < demo.size(); j++) {


                    String Status = demo.get(j).getStatus();
                    //Log.e("ggg", demo.get(j).getStatus());

                    String date1 = demo.get(j).getDate();
                    //Log.e("hhh", demo.get(j).getDate());

                    String time = demo.get(j).getTime();


                    // if (Status.equals("Yes")) {
                    //Log.e("daychaeck11", demo.get(j).getMedicationName() + "Status" + Status + ":" + date1);

                    String day = addDay(date1);     //Tuesday
                    //Log.e("Dayyyy","DDD :"+day+"/"+holder.binding.l1.getText().toString());

                    if (holder.binding.l1.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw1.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw1.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw1.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l2.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw2.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw2.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw2.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l3.getText().toString().equals(day)) {



                        if (Status.equals("Yes")) {
                            binding.llw3.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw3.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw3.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l4.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw4.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw4.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw4.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l5.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw5.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw5.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw5.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l6.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw6.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw6.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw6.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l7.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw7.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw7.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw7.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    }
                }




              /*   //Log.e("daychaeck", day + ":" + date1);
                 if (day.equals("Sun"))
                 {
                     if (Status.equals("Yes")) {
                         binding.llw1.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         binding.llw1.setBackground(context.getDrawable(R.drawable.skippedicon));
                     } else if (Status.equals("Skip")) {
                         binding.llw1.setBackground(context.getDrawable(R.drawable.skippedicon));
                     }
                 } else {
                     // binding.llw1.setBackground(context.getDrawable(R.drawable.customday));
                 }


                 if (day.equals("Mon")) {
                     if (Status.equals("Yes")) {
                         binding.llw2.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         binding.llw2.setBackground(context.getDrawable(R.drawable.skippedicon));
                     } else if (Status.equals("Skip")) {
                         binding.llw2.setBackground(context.getDrawable(R.drawable.skippedicon));
                     }
                 } else {
                     //  binding.llw2.setBackground(context.getDrawable(R.drawable.customday));
                 }

                 if (day.equals("Tue")) {
                     if (Status.equals("Yes")) {
                         binding.llw3.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         binding.llw3.setBackground(context.getDrawable(R.drawable.skippedicon));
                     } else if (Status.equals("Skip")) {
                         binding.llw3.setBackground(context.getDrawable(R.drawable.skippedicon));
                     }
                 } else {
                     //binding.llw3.setBackground(context.getDrawable(R.drawable.customday));
                 }

                 if (day.equals("Wed")) {
                     if (Status.equals("Yes")) {
                         binding.llw4.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         binding.llw4.setBackground(context.getDrawable(R.drawable.skippedicon));
                     } else if (Status.equals("Skip")) {
                         binding.llw4.setBackground(context.getDrawable(R.drawable.skippedicon));
                     }
                 } else {
                     //binding.llw4.setBackground(context.getDrawable(R.drawable.customday));
                 }

                 if (day.equals("Thu")) {
                     if (Status.equals("Yes")) {
                         binding.llw5.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         binding.llw5.setBackground(context.getDrawable(R.drawable.skippedicon));
                     } else if (Status.equals("Skip")) {
                         binding.llw5.setBackground(context.getDrawable(R.drawable.skippedicon));
                     }
                 } else {
                     //binding.llw5.setBackground(context.getDrawable(R.drawable.customday));
                 }

                 if (day.equals("Fri")) {
                     if (Status.equals("Yes")) {
                         binding.llw6.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         binding.llw6.setBackground(context.getDrawable(R.drawable.skippedicon));
                     } else if (Status.equals("Skip")) {
                         binding.llw6.setBackground(context.getDrawable(R.drawable.skippedicon));
                     }
                 } else {
                     // binding.llw6.setBackground(context.getDrawable(R.drawable.customday));
                 }
                 if (day.equals("Sat")) {
                     //Log.e("satt", Status.equals("Yes") + "");
                     if (Status.equals("Yes")) {
                         binding.llw7.setBackground(context.getDrawable(R.drawable.confirm));
                     } else if (Status.equals("No")) {
                         //Log.e("satt==", Status.equals("No") + "");

                         binding.llw7.setBackground(context.getDrawable(R.drawable.skippedicon));//notconfirm
                     } else if (Status.equals("Skip")) {
                         //Log.e("satt=", Status.equals("Skip") + "");

                         binding.llw7.setBackground(context.getDrawable(R.drawable.skippedicon));//skippedicon
                     }
                 } else {
                     // binding.llw7.setBackground(context.getDrawable(R.drawable.customday));
                 }*/


                }

            }
            else if (Freq.equals("2 time a day"))
            {



                holder.tvTxtdays.setText(myRecListData.getMedicationName() + " (2 time a day)");

                binding.textViewArrow.setVisibility(View.VISIBLE);
                // binding.dayslist2.setVisibility(View.VISIBLE);
                if (listC.size()>0){

                int daysSize = listC.size() - 1;
                String date = listC.get(daysSize).getDate();



                String Freq1 = "2 time a day";


                //check two entries of same date occurs or not then add or gone view

               /*     int tp = db.getdateCount(date,Freq1);

                    //Log.e("CNCN",""+tp);

                    if (tp == 2){*/





                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    d = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar c = Calendar.getInstance();
                c.setTime(d); // yourdate is an object of type Date
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // th


                String[] weeks = getWeekDays(dayOfWeek);
                //Log.e("MMM", "WW :" + dayOfWeek);

                if (weeks.length >= 0) {
                    holder.binding.l1.setText(weeks[0]);
                    holder.binding.l2.setText(weeks[1]);
                    holder.binding.l3.setText(weeks[2]);
                    holder.binding.l4.setText(weeks[3]);
                    holder.binding.l5.setText(weeks[4]);
                    holder.binding.l6.setText(weeks[5]);
                    holder.binding.l7.setText(weeks[6]);


                    holder.binding.d1.setText(weeks[0]);
                    holder.binding.d2.setText(weeks[1]);
                    holder.binding.d3.setText(weeks[2]);
                    holder.binding.d4.setText(weeks[3]);
                    holder.binding.d5.setText(weeks[4]);
                    holder.binding.d6.setText(weeks[5]);
                    holder.binding.d7.setText(weeks[6]);

                }

                //Log.e("fffsss", listC.size() + "");
               // String last7days = getCalculatedDate(date, "dd-MM-yyyy", -7); // It will gives you date before 10 days from current date
             //   //Log.e("print7days", last7days + ":" + date);
              int count = db.getdateCount(getCurrentDate(),Freq1);
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

                demo = db.getDaysData2(id,uid,num);
                //Log.e("2time", demo.size() + "");
                for (int j = 0; j < demo.size(); j++) {

                    if (j % 2 == 0) {

                        String Status = demo.get(j).getStatus();
                        //Log.e("ggg", demo.get(j).getStatus());

                        String date1 = demo.get(j).getDate();
                        //Log.e("hhhkjjkj", demo.get(j).getMedicationName() + ":" + demo.get(j).getDate());

                        String time = demo.get(j).getTime();


                        // if (Status.equals("Yes")) {
                        //Log.e("daychaeck11", demo.get(j).getMedicationName() + "Status" + Status + ":" + date1);

                        String day = addDay(date1);
                        //Log.e("DBDB", day + ":" + date1);


                        if (holder.binding.l1.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw1.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw1.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw1.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.l2.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw2.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw2.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw2.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.l3.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw3.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw3.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw3.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.l4.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw4.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw4.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw4.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.l5.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw5.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw5.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw5.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.l6.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw6.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw6.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw6.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.l7.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw7.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw7.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw7.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        }


                    }
                    else {

                        String Status = demo.get(j).getStatus();
                        //Log.e("ggg", demo.get(j).getStatus());

                        String date1 = demo.get(j).getDate();
                        //Log.e("hhhkjjkj", demo.get(j).getMedicationName() + ":" + demo.get(j).getDate());

                        String time = demo.get(j).getTime();


                        // if (Status.equals("Yes")) {
                        //Log.e("daychaeck11", demo.get(j).getMedicationName() + "Status" + Status + ":" + date1);

                        String day = addDay(date1);
                        //Log.e("DBDB22", day + ":" + date1);


                        if (holder.binding.d1.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw21.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw21.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw21.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.d2.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw22.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw22.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw22.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.d3.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw23.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw23.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw23.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.d4.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw24.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw24.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw24.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.d5.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw25.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw25.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw25.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.d6.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw26.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw26.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw26.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        } else if (holder.binding.d7.getText().toString().equals(day)) {
                            if (Status.equals("Yes")) {
                                binding.llw27.setBackground(context.getDrawable(R.drawable.confirm));
                            } else if (Status.equals("No")) {
                                binding.llw27.setBackground(context.getDrawable(R.drawable.skippedicon));
                            } else if (Status.equals("Skip")) {
                                binding.llw27.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                            }
                        }


                    }

                }//for
/*
                    }//only show when count 2

                    else{
                       // listdata.remove(position);
                      //  notifyDataSetChanged();
                    }*/
            }

            }


            //code for custom..
            else if (Freq.equals("Custom")) {
                binding.textViewArrow.setVisibility(View.GONE);
                binding.dayslist2.setVisibility(View.GONE);
                String[] timearr;
                String cc = myRecListData.getTime().toLowerCase();

                //Log.e("TIME", "Custom:" + cc);
                if (cc.contains(",")) {
                 //   //Log.e("TIME", "CCC:" + cc);
                    timearr = cc.split(",");
                    //Log.e("TIME", "length :" + timearr.length);
                    if (timearr.length >= 1) {
                        // add DataModelHomeF

                        holder.tvTxtdays.setText(myRecListData.getMedicationName() +" ("+ timearr[0] + ".....)");
                    }
                } else {
                    holder.tvTxtdays.setText(myRecListData.getMedicationName() + cc);
                }


                binding.textViewArrow.setVisibility(View.GONE);
                binding.dayslist2.setVisibility(View.GONE);

                if (listC.size()>0)
                {
                int daysSize = listC.size() - 1;

                String date = listC.get(daysSize).getDate();
              //  //Log.e("fffssskk", listC.get(daysSize).getDate() + "");


               // //Log.e("MMM", "DD :" + date);

                //convert date to get week
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    d = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar c = Calendar.getInstance();
                c.setTime(d); // yourdate is an object of type Date
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // th


                String[] weeks = getWeekDays(dayOfWeek);
                //Log.e("MMM", "WW :" + dayOfWeek);

                if (weeks.length > 0) {
                    holder.binding.l1.setText(weeks[0]);
                    holder.binding.l2.setText(weeks[1]);
                    holder.binding.l3.setText(weeks[2]);
                    holder.binding.l4.setText(weeks[3]);
                    holder.binding.l5.setText(weeks[4]);
                    holder.binding.l6.setText(weeks[5]);
                    holder.binding.l7.setText(weeks[6]);


                }



              String ct =   myRecListData.getTime();

                int mul=1;
                    if (ct.contains(",")) {
                        String time[] = ct.split(",");
                        mul = time.length;

                    }



                ////Log.e("fffsss", listC.size() + "");
               // String last7days = getCalculatedDate(date, "dd-MM-yyyy", -7); // It will gives you date before 10 days from current date
                ////Log.e("print7days", last7days + ":" + date);
                    demo = db.getDaysData(id,uid,7*mul);
               // demo = db.getdays(id, last7days, date);
                //Log.e("kkk", demo.size() + "");
                for (int j = 0; j < demo.size(); j++) {

                    //Log.e("ddtt", demo.get(j).getTime() + ":" + demo.get(j).getDate());
                    String Status = demo.get(j).getStatus();
                    //Log.e("ggg", demo.get(j).getStatus());

                    String date1 = demo.get(j).getDate();
                    //Log.e("hhh", demo.get(j).getDate());

                    String time = demo.get(j).getTime();


                    // if (Status.equals("Yes")) {
                    //Log.e("daychaeck11", demo.get(j).getMedicationName() + "Status" + Status + ":" + date1);

                    String day = addDay(date1);
                    //Log.e("daychaeck", day + ":" + date1);

                    if (holder.binding.l1.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw1.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw1.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw1.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l2.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw2.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw2.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw2.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l3.getText().toString().equals(day)) {



                        if (Status.equals("Yes")) {
                            binding.llw3.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw3.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw3.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l4.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw4.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw4.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw4.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l5.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw5.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw5.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw5.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l6.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw6.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw6.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw6.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    } else if (holder.binding.l7.getText().toString().equals(day)) {
                        if (Status.equals("Yes")) {
                            binding.llw7.setBackground(context.getDrawable(R.drawable.confirm));
                        } else if (Status.equals("No")) {
                            binding.llw7.setBackground(context.getDrawable(R.drawable.skippedicon));
                        } else if (Status.equals("Skip")) {
                            binding.llw7.setBackground(context.getDrawable(R.drawable.questionmarkicon));
                        }
                    }
                }

                }

                 // }
            }




        }

        //   }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addchart.click(holder.getAdapterPosition(),context, myRecListData.getNoofDays());

            }
        });




        binding.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view, holder.getAdapterPosition());
                //Toast.makeText(view.getContext(),"click on item: "+myRecListData.getDescription(),Toast.LENGTH_LONG).show();


            }
        });

        holder.binding.textViewArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (position == holder.getAdapterPosition()) {

                    boolean ch = myRecListData.isOpen();
                    //  Toast.makeText(view.getContext(), "pos "+holder.getAdapterPosition()+":"+ch,Toast.LENGTH_SHORT).show();

                    if (ch) {
                        holder.binding.dayslist2.setVisibility(View.GONE);
                        holder.binding.textViewArrow.setBackground(context.getResources().getDrawable(R.drawable.expand));
                        myRecListData.setOpen(false);
                    } else {

                        holder.binding.dayslist2.setVisibility(View.VISIBLE);
                        holder.binding.textViewArrow.setBackground(context.getResources().getDrawable(R.drawable.collapse));
                        myRecListData.setOpen(true);
                    }

                    //   ch = !ch;


                }

            }
        });

        binding.textViewOptions1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view, holder.getAdapterPosition());
            }
        });

       /* for (int i = 0; i < listdata.size(); i++) {
            String mid = listdata.get(i).getId();


            for (int j = 0; j < listC.size(); j++) {
                int ct = db.getdateCount(listC.get(j).getDate(),"2 time a day");

                //Log.e("GFGF","match::"+ct);
                String kid = listC.get(j).getId();
                if (ct < 2 && mid.equals(kid)){
                    //Log.e("GFGF","match");
                    listdata.remove(i);
                    notifyDataSetChanged();
                }
            }


        }*/


    }



    private void showMenu(View v, int pos) {
        DataModelHomeF myRecListData = listdata.get(pos);
        PopupMenu popup = new PopupMenu(context, v,1,1, R.style.PopupMenu);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.contextmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.month:
                        // listdata.get(pos).setType("Month");

                        myRecListData.setType("Month");
                        //Log.e("menu22", "Monthclicked");
                        notifyDataSetChanged();
                        return true;
                    case R.id.days:
                        // listdata.get(pos).setType("Days");
                        myRecListData.setType("Days");
                        //Log.e("menu", "Daysclicked");

                        notifyDataSetChanged();
                        return true;

                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date1 = ss.format(date);
        return date1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ListItemBinding binding;
        CardView card1, card2;
        TextView tvTxtmonth, tvTxtdays, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12;
        RecyclerView lv;
        TextView d1, d2, d3;
        LinearLayout daylist, daylist2;
        ImageView textViewArrow;

        LinearLayout m1;

        String s1, s2, s3, s4, s5, s6, s7;

        public ViewHolder(@NonNull ListItemBinding bindings) {

            super(bindings.getRoot());
            binding = bindings;


            // bindings.textViewOptions.setOnCreateContextMenuListener(this);
            bindings.textViewOptions1.showContextMenu();
            bindings.textViewOptions.showContextMenu();


            this.card1 = itemView.findViewById(R.id.CardView1);
            this.card2 = itemView.findViewById(R.id.CardView2);
            this.tvTxtmonth = (TextView) itemView.findViewById(R.id.tvTxtmonth);
            this.tvTxtdays = (TextView) itemView.findViewById(R.id.tvTxtdays);

            this.textView1 = (TextView) itemView.findViewById(R.id.tvimg1);
            this.textView2 = (TextView) itemView.findViewById(R.id.tvimg2);
            this.textView3 = (TextView) itemView.findViewById(R.id.tvimg3);
            this.textView4 = (TextView) itemView.findViewById(R.id.tvimg4);
            this.textView5 = (TextView) itemView.findViewById(R.id.tvimg5);
            this.textView6 = (TextView) itemView.findViewById(R.id.tvimg6);
            this.textView7 = (TextView) itemView.findViewById(R.id.tvimg7);
            this.textView8 = (TextView) itemView.findViewById(R.id.tvimg8);
            this.textView9 = (TextView) itemView.findViewById(R.id.tvimg9);
            this.textView10 = (TextView) itemView.findViewById(R.id.tvimg10);
            this.textView11 = (TextView) itemView.findViewById(R.id.tvimg11);
            this.textView12 = (TextView) itemView.findViewById(R.id.tvimg12);


            this.daylist = itemView.findViewById(R.id.dayslist);
            this.daylist2 = itemView.findViewById(R.id.dayslist2);
            this.textViewArrow = itemView.findViewById(R.id.textViewArrow);



            /*this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.LlinearLayout);*/


        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add("Month");
            menu.add("Days");
        }


    }

    public String addDay(String yourDate) {

        String wkk = "Mon";
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = ss.parse(yourDate);
            Calendar c = Calendar.getInstance();
            c.setTime(dt1);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            //Log.e("dayofweek==", dayOfWeek + "");
            if (dayOfWeek == 1) {
                wkk = "Sun";
            } else if (dayOfWeek == 2) {
                wkk = "Mon";
            } else if (dayOfWeek == 3) {
                wkk = "Tue";
            } else if (dayOfWeek == 4) {
                wkk = "Wed";
            } else if (dayOfWeek == 5) {
                wkk = "Thu";
            } else if (dayOfWeek == 6) {
                wkk = "Fri";
            } else if (dayOfWeek == 7) {
                wkk = "Sat";
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }

        return wkk;
    }

    int janCount = 0, febCount = 0, marCount = 0, aprCount = 0, mayCount = 0, juneCount = 0, julyCount = 0, augCount = 0, sepCount = 0, octCount = 0, novCount = 0, decbCount = 0;

    public void dateToYear(String date, ViewHolder holder) {

        String mm = null;
        //Log.e("dateToYear", date);
        String[] month = date.split("-");
        if (month[1] != null) {
            String mon = month[1];
            //Log.e("month=", mon);
            if (mon.equals("01"))
                mm = "January";

            else if (mon.equals("02"))
                mm = "February";
            else if (mon.equals("03"))
                mm = "March";
            else if (mon.equals("04"))
                mm = "April";
            else if (mon.equals("05"))
                mm = "May";
            else if (mon.equals("06"))
                mm = "June";
            else if (mon.equals("07"))
                mm = "July";
            else if (mon.equals("08"))
                mm = "August";
            else if (mon.equals("09"))
                mm = "September";
            else if (mon.equals("10"))
                mm = "October";
            else if (mon.equals("11"))
                mm = "November";
            else if (mon.equals("12"))
                mm = "December";
        }
        //Log.e("month=", mm);
        if (mm.equals("January")) {
            janCount++;
            holder.textView1.setText(janCount + "");
        } else if (mm.equals("February")) {
            febCount++;
            holder.textView2.setText(febCount + "");
        } else if (mm.equals("March")) {
            aprCount++;
            holder.textView3.setText(aprCount + "");
        } else if (mm.equals("April")) {
            marCount++;
            holder.textView4.setText(marCount + "");
        } else if (mm.equals("May")) {
            mayCount++;
            holder.textView5.setText(mayCount + "");
        } else if (mm.equals("June")) {
            juneCount++;
            holder.textView6.setText(juneCount + "");
        } else if (mm.equals("July")) {
            julyCount++;
            holder.textView7.setText(julyCount + "");
        } else if (mm.equals("August")) {
            augCount++;
            holder.textView8.setText(augCount + "");
        } else if (mm.equals("September")) {
            sepCount++;
            holder.textView9.setText(sepCount + "");
        } else if (mm.equals("October")) {
            octCount++;
            holder.textView10.setText(octCount + "");
        } else if (mm.equals("November")) {
            novCount++;
            holder.textView11.setText(novCount + "");
        } else if (mm.equals("December")) {
            decbCount++;
            holder.textView12.setText(decbCount + "");
        }


    }


 /*   void checkDays(ArrayList<DataModel2> listdata)
    {
        boolean sun,mon,tue,wed,thu,fri,sat;
        for(int i=0; i<listdata.size();i++)
        {
            String date = listdata.get(i).getDate();
            String day = addDay(date);
            //Log.e("TestCheck", day);

            if (!day.equals("Sun")) {
                sun=true;
              //  listdata.add(new DataModel2(listdata.get(i).getId(), date, listdata.get(i). getTime(), listdata.get(i). getMedicationName(), "No","startTime"));
            }
            else if (!day.equals("Mon")) {
                mon=true;
            }
            else if (!day.equals("Tue")) {
                tue=true;
            }
            else if (!day.equals("Wed")) {
                wed=true;
            }
            else if (!day.equals("Thu")) {
                thu=true;
            }
            else if (!day.equals("Fri")) {
                fri=true;
            }
            else if (!day.equals("Sat")) {
                sat=true;
            }

        }


        //        else {
//            holder.linear.setBackground(context.getDrawable(R.drawable.customday));
//        }
    }*/

    /*public void setData( ArrayList<DataModel2> listC) {
        for (int i = 0; i < listC.size(); i++) {

            DataModel2 model = listC.get(i);

            String Status = model.getStatus();
            String date = model.getDate();
            //  String time = model.getTime();

            //Log.e(i + "Size===", listC.get(1).getDate() + "");
            //Log.e("daychaeck11", model.getMedicationName() + "Status" + Status);
            String day = addDay(date);
            //Log.e("TestCheck", day);


            if (day.equals("Sun")) {
              sun1.
            } else if (!day.equals("Sun")) {
                holder.sun1.setVisibility(View.VISIBLE);
            }

            //Mon
            else if (day.equals("Mon")) {
                holder.tvText.setText("Mon");
                if (Status.equals("Yes")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
                } else if (Status.equals("No")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
                } else if (Status.equals("Skip")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
                }
            } else if (!day.equals("Mon")) {
                holder.Mon1.setVisibility(View.VISIBLE);
            }

            // Tue
            else if (day.equals("Tue")) {
                holder.tvText.setText("Tue");
                if (Status.equals("Yes")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
                } else if (Status.equals("No")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
                } else if (Status.equals("Skip")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
                }
            } else if (!day.equals("Tue")) {
                holder.Tue1.setVisibility(View.VISIBLE);
            }


            //Wed
            else if (day.equals("Wed")) {
                holder.tvText.setText("Wed");
                if (Status.equals("Yes")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
                } else if (Status.equals("No")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
                } else if (Status.equals("Skip")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
                }
            } else if (!day.equals("Wed")) {
                holder.Wed1.setVisibility(View.VISIBLE);
            }

            // Thrus
            else if (day.equals("Thu")) {
                holder.tvText.setText("Thu");
                if (Status.equals("Yes")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
                } else if (Status.equals("No")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
                } else if (Status.equals("Skip")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
                }
            } else if (!day.equals("Thu")) {
                holder.Thu1.setVisibility(View.VISIBLE);
            }


            // Fri

            else if (day.equals("Fri")) {
                holder.tvText.setText("Fri");
                //Log.e("Status==", Status.equals("Yes") + "");
                if (Status.equals("Yes")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
                } else if (Status.equals("No")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
                } else if (Status.equals("Skip")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
                }
            } else if (!day.equals("Fri")) {
                holder.Fri1.setVisibility(View.VISIBLE);
            }


            //Sat

            else if (day.equals("Sat")) {
                holder.tvText.setText("Sat");
                if (Status.equals("Yes")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
                } else if (Status.equals("No")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
                } else if (Status.equals("Skip")) {
                    holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
                }
            } else if (!day.equals("Sat")) {
                holder.Sat1.setVisibility(View.VISIBLE);
            }

        }
    }*/
    public static String getCalculatedDate(String date, String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        if (!date.isEmpty()) {
            try {
                //Log.e("TAG", "Error in Parsing Date : " + s.format(new Date(s.parse(date).getTime())));

                cal.setTime(s.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
//
//    public String getCalculatedDate22(String date, String dateFormat, int days) {
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat s

}