package com.vapps.medi.reminder.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import com.vapps.medi.reminder.Fragments.MyHistory;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.Addchart;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<DataModel2> listdata1;
    Context context;

    Addchart listner;


    // RecyclerView recyclerView;
    public MyAdapter(ArrayList<DataModel2> listdata, Addchart listner, Context context) {
        this.listdata1 = listdata;
        this.context=context;
        this.listner = listner;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.historyitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataModel2 DataModel2 = listdata1.get(position);
        holder.textView.setText(DataModel2.getDate());
      //  holder.imageView.setImageResource(Integer.parseInt(listdata1.get(position).getImgg()));
        holder.textView1.setText(DataModel2.getMedicationName());

        SharedPreferences prefs ;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.getInt("color",0) != 0) {
            int themeColor = prefs.getInt("color", 0);


            holder.imggg.setColorFilter(themeColor);
        }


        if (DataModel2.getStatus().equals("Yes")){
            holder.textView2.setText("Take time");
            holder.textView3.setText(DataModel2.getTime());
            holder.imageView.setBackground(context.getDrawable(R.drawable.confirm) );
        }else if (DataModel2.getStatus().equals("No")){
            holder.textView2.setText("Not taken");
            holder.textView3.setText("");
            holder.imageView.setBackground(context.getDrawable(R.drawable.skippedicon) );
        }
        else{
            holder.textView2.setText("Skip");
            holder.textView3.setText("");
            holder.imageView.setBackground(context.getDrawable(R.drawable.questionmarkicon) );
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.textView2.getText().equals("Skip")){
                   // Toast.makeText(context,"Click"+DataModel2.getDate(),Toast.LENGTH_SHORT).show();
                    callcontext(v,holder.getAdapterPosition());
                }
            }
        });


    }
    int ndays;

    public void callcontext(View v,int pos ){


        DatabaseHelper db = new DatabaseHelper(context);
        PopupMenu popup = new PopupMenu(context, v,1,1, R.style.PopupMenu);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.cnxtconfirmed, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.confirm:
                        DataModel2 DataModel22 = listdata1.get(pos);
                        int total=0;

                            int pills = db.getnoofpils(DataModel22.getId());
                            if (pills > 0){
                                total = pills-1;
                            }

                        db.updateConfirm2(DataModel22.getId(),"Yes",DataModel22.getTime(),DataModel22.getDate());

                            MyHistory.changeState = true;
                            DataModel22.setStatus("Yes");

                           // listdata1.add(pos,DataModel22 );
                            notifyItemInserted(pos);
                      //  listner.click(pos,context,0);
                        db.updateConfirm4(DataModel22.getId(),total);


                        notifyDataSetChanged();
                        return true;
                    case R.id.skip:   //not taken

                        DataModel2 DataModel21 = listdata1.get(pos);


                        MyHistory.changeState = true;

                        db.updateConfirm2(DataModel21.getId(),"No",DataModel21.getTime(),DataModel21.getDate());


                        DataModel21.setStatus("No");
                      //  listdata1.add(pos,DataModel21 );
                        notifyItemInserted(pos);

                       // listner.click(pos,context,0);


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
        return listdata1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView,imggg;
        public TextView textView,textView1,textView2,textView3;
        public LinearLayout linear;
        public ViewHolder(View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linear);
            this.textView = (TextView) itemView.findViewById(R.id.Date);
            this.textView1 = (TextView) itemView.findViewById(R.id.mediName);
            this.textView2 = (TextView) itemView.findViewById(R.id.decccN);
            this.textView3 = (TextView) itemView.findViewById(R.id.tvTime);
            this.imggg=(ImageView) itemView.findViewById(R.id.imggg);


            this.imageView = (ImageView) itemView.findViewById(R.id.imgT);

        }
    }
}
