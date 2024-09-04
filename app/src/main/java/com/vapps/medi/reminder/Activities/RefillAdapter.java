package com.vapps.medi.reminder.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.Interfaces.GalleryClick;
import com.vapps.medi.reminder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RefillAdapter extends ArrayAdapter<DataModelHomeF> {
    private int resocursLayout;
    Context context;
    ArrayList<DataModelHomeF> arrayList;
    GalleryClick listner;
    DatabaseHelper db;

    boolean changing = false;
    boolean changing1 = false;


    public RefillAdapter(Context context, int res, ArrayList<DataModelHomeF> data) {
        super(context, res, data);
        this.context = context;
        this.arrayList = data;
        this.resocursLayout = res;
        db=new DatabaseHelper(context);


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(resocursLayout, null);
        }

        LinearLayout refill_layout = view.findViewById(R.id.refill_layout);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        TextView title = view.findViewById(R.id.refill_title);
        TextView textView = view.findViewById(R.id.tvtxt);
        EditText editText = view.findViewById(R.id.etpills);
        EditText editText1 = view.findViewById(R.id.etnotify);
        Button button = view.findViewById(R.id.btnSave);

        ImageView imgR1 = view.findViewById(R.id.imgR1);
        ImageView imgR2 = view.findViewById(R.id.imgR2);
        ImageView imgR3 = view.findViewById(R.id.imgR3);

        TextInputLayout pillsNameError =view.findViewById(R.id.pillsNameError);
        TextInputLayout notifyNameError =view.findViewById(R.id.notifyNameError);


        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.getInt("color", 0) != 0) {
            int themeColor = prefs.getInt("color", 0);
            //Log.e("themeColor", themeColor + "");
            imgR1.setColorFilter(themeColor);
            imgR2.setColorFilter(themeColor);
            imgR3.setColorFilter(themeColor);
            button.setBackgroundColor(themeColor);
            btnCancel.setBackgroundColor(themeColor);

            GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(context, R.drawable.custom_button);
            shape.setColor(themeColor);
        button.setBackground(shape);
        btnCancel.setBackground(shape);




        }


        DataModelHomeF model=arrayList.get(position);
        //Log.e("imgShow",model.getMedicationName()+":"+model.getUserName());
        title.setText("Refill "+model.getMedicationName());
          textView.setText(model.getUserName()+ "'s medicine " + model.getMedicationName().substring(0,model.getMedicationName().indexOf(':')));


        //  textView.setText(model.getUserName()+ "'s medicine " + model.getMedicationName().substring(0,model.getMedicationName().indexOf(':')));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }




            @Override
            public void afterTextChanged(Editable s) {

                if (!changing && editText.getText().toString().startsWith("0")){
                    changing = true;
                    editText.setText(editText.getText().toString().replace("0", ""));
                }
                changing = false;
                if( editText.getText().length()>0)
                {

//
                    pillsNameError.setError(null);

                }
                else {
                    pillsNameError.setError("How many pills do you currently have?");
                }
            }
        });

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }




            @Override
            public void afterTextChanged(Editable s) {

                if (!changing1 && editText1.getText().toString().startsWith("0")){
                    changing1 = true;
                    editText1.setText(editText1.getText().toString().replace("0", ""));
                }
                changing1 = false;
                if( editText1.getText().length()>0)
                {

//
                    notifyNameError.setError(null);

                }
                else {
                    notifyNameError.setError("How many days do you want to take pills?");
                }
            }
        });


  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String pills=editText.getText().toString();
               String notify=editText1.getText().toString();
                boolean ispillsValid,isnotifyValid;




                if (pills == null || pills.isEmpty()) {
                    ispillsValid = false;
                   pillsNameError.setError("How many pills do you currently have?");
                } else {
                    ispillsValid = true;
                  pillsNameError.setErrorEnabled(false);
                }if (notify == null || notify.isEmpty()){
                    isnotifyValid = false;
                   notifyNameError.setError("How many days do you want to take pills?");
                }else {
                    isnotifyValid = true;
                  notifyNameError.setErrorEnabled(false);
                }
                if (ispillsValid && isnotifyValid ) {
//Log.e("idsss",arrayList.get(position).getId()+":"+position+":"+pills+":"+model.getLeftpills());

                    int pill = Integer.parseInt(pills) + model.getLeftpills();
                   // boolean Avi = db.RefillUpdate(model.getId(),pill,Integer.parseInt(notify));

                    boolean Avi = db.updateResume(model.getId().trim(),Integer.parseInt(notify),pill,getCurrentDate1());
                    if (Avi) {

                        Toast.makeText(context,"Medicine resume notification starts from tomorrow",Toast.LENGTH_SHORT).show();
                        db.deletemonthRecord(model.getId().trim());

                        if (position == getPosition(model)){
                            arrayList.remove(position);
                            notifyDataSetChanged();


                            if (arrayList.size() < 1){
                                Intent intent= new Intent(context,HomeActivity.class);
                                context.startActivity(intent);

                                ((Activity)view.getContext()).finish();
                            }
                        }






                       /* Intent intent= new Intent(context,HomeActivity.class);
                        context.startActivity(intent);

                       ((Activity)view.getContext()).finish();*/

                    } else {

                        Toast.makeText(context, "not updated"+model.getId(), Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });



  btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if (position == getPosition(model)){
              arrayList.remove(position);
              notifyDataSetChanged();

              if (arrayList.size() < 1){
                  Intent intent= new Intent(context,HomeActivity.class);
                  context.startActivity(intent);

                  ((Activity)v.getContext()).finish();
              }
          }
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

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable DataModelHomeF item) {
        return super.getPosition(item);
    }
}
