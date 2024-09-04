package com.vapps.medi.reminder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DaysAdapter  extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    private ArrayList<DataModel2> listdata;
    Context context;



    public DaysAdapter(ArrayList<DataModel2> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
      //  checkDays(listdata);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.days_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       DataModel2 model = listdata.get(position);

        String Status = model.getStatus();
        String date = model.getDate();
      //  String time = model.getTime();



        //Log.e(position+"Size===", listdata.get(1).getDate()+"");
        //Log.e("daychaeck11", model.getMedicationName() + "Status" + Status);
        String day = addDay(date);
        //Log.e("TestCheck", day);


        if (day.equals("Sun")) {
            holder.sun1.setVisibility(View.GONE);
            holder.tvText.setText("Sun");

            if (Status.equals("Yes")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }
       /* else if (!day.equals("Sun")) {
            holder.sun1.setVisibility(View.VISIBLE);
        }*/

        //Mon
        else if (day.equals("Mon")) {
            holder.Mon1.setVisibility(View.GONE);
            holder.tvText.setText("Mon");
            if (Status.equals("Yes")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }/* else if (!day.equals("Mon")) {
            holder.Mon1.setVisibility(View.VISIBLE);
        }*/

        // Tue
        else if (day.equals("Tue")) {
            holder.Tue1.setVisibility(View.GONE);
            holder.tvText.setText("Tue");
            if (Status.equals("Yes")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }/*else if (!day.equals("Tue")) {
            holder.Tue1.setVisibility(View.VISIBLE);
        }*/


        //Wed
        else if (day.equals("Wed")) {
            holder.Wed1.setVisibility(View.GONE);
            holder.tvText.setText("Wed");
            if (Status.equals("Yes")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }/*else if (!day.equals("Wed")) {
            holder.Wed1.setVisibility(View.VISIBLE);
        }*/

        // Thrus
        else if (day.equals("Thu")) {
            holder.Thu1.setVisibility(View.GONE);

            holder.tvText.setText("Thu");
            if (Status.equals("Yes")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.linear.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }/*else if (!day.equals("Thu")) {
            holder.Thu1.setVisibility(View.VISIBLE);
        }
*/

        // Fri

        else if (day.equals("Fri")) {
           // holder.Fri1.setVisibility(View.GONE);

        //    holder.tvText.setText("Fri");
            //Log.e("Status==", Status.equals("Yes")+"");
            if (Status.equals("Yes")) {
                holder.Fri1.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.Fri1.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.Fri1.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }/*else if (!day.equals("Fri")) {
            holder.Fri1.setVisibility(View.VISIBLE);
        }*/


        //Sat

        else if (day.equals("Sat")) {
           // holder.Sat1.setVisibility(View.GONE);

           // holder.tvText.setText("Sat");
            if (Status.equals("Yes")) {
                holder.Sat1.setBackground(context.getDrawable(R.drawable.confirm));
            } else if (Status.equals("No")) {
                holder.Sat1.setBackground(context.getDrawable(R.drawable.notconfirm));
            } else if (Status.equals("Skip")) {
                holder.Sat1.setBackground(context.getDrawable(R.drawable.skippedicon));
            }
        }/*else if (!day.equals("Sat")) {
            holder.Sat1.setVisibility(View.VISIBLE);
        }*/

    }

    @Override
    public int getItemCount() {
        //Log.e("getItemCount",listdata.size()+"");
        return listdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {

    TextView tvText;
    LinearLayout linear,sun1,Mon1,Tue1,Wed1,Thu1,Fri1,Sat1;
    public ViewHolder(View itemView) {
        super(itemView);
         //   this.linear = itemView.findViewById(R.id.stLinear);
         //   this.tvText = (TextView) itemView.findViewById(R.id.tvText);
        this.sun1= itemView.findViewById(R.id.llw1);
        this.Mon1= itemView.findViewById(R.id.llw2);
        this.Tue1= itemView.findViewById(R.id.llw3);
        this.Wed1= itemView.findViewById(R.id.llw4);
        this.Thu1= itemView.findViewById(R.id.llw5);
        this.Fri1= itemView.findViewById(R.id.llw6);
        this.Sat1= itemView.findViewById(R.id.llw7);




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

        return wkk;
    }




void checkDays(ArrayList<DataModel2> listdata)
{
    //Log.e("TestCheck99", listdata.size()+"");
    boolean sun,mon,tue,wed,thu,fri,sat;
    for(int i=0; i<listdata.size();i++)
    {
        String date = listdata.get(i).getDate();
        String day = addDay(date);
        //Log.e("TestCheck", day);

        if (!day.equals("Sun")) {
            sun=true;
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
    //Log.e("TestCheck999", listdata.size()+"");

    //        else {
//            holder.linear.setBackground(context.getDrawable(R.drawable.customday));
//        }
}



}
        
        
        
        
        
