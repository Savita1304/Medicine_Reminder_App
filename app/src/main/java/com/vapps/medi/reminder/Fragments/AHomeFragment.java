package com.vapps.medi.reminder.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.vapps.medi.reminder.Activities.EditRefillActivity;
import com.vapps.medi.reminder.Adapters.SpinAdapter;
import com.vapps.medi.reminder.Adapters.customHomeF;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.RemoveListner;
import com.vapps.medi.reminder.Models.DataModel;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AHomeFragment extends Fragment implements RemoveListner {

    ListView homeList;
    DatabaseHelper db;
    Button bb;
    boolean isAvailable;
    ArrayList<DataModelHomeF> list ;
    LinearLayout LWAdd;
    customHomeF customAdapter;
    LinearLayout linearLayout,spl;
    Spinner spother;
    ArrayList<DataModel> spdata = new ArrayList<>();
    ImageView imageView1;

    SharedPreferences prefs;

    String name,id;
    public static int tmp = 0;

    public static AHomeFragment newInstance() {

        return new AHomeFragment();
    }

    public AHomeFragment() {
        // Required empty public constructor
    }
    BottomNavigationView bnView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a_home, container, false);
         linearLayout = getActivity().findViewById(R.id.toolbar);
         ImageView imageView = getActivity().findViewById(R.id.setting);
         imageView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        spl = view.findViewById(R.id.spl);
        imageView1 = view.findViewById(R.id.Addimg);

        spother = view.findViewById(R.id.spother);

        homeList = view.findViewById(R.id.homeList);
        LWAdd = view.findViewById(R.id.LWAdd);
        db=new DatabaseHelper(getActivity());
        //Log.e("BUN",":onCreate");

        bnView = getActivity().findViewById(R.id.bnView);


        AdView adView = getActivity().findViewById(R.id.adView);
        adView.setVisibility(View.VISIBLE);


        ImageView logo = getActivity().findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);


        TextView header = getActivity().findViewById(R.id.header);
        header.setVisibility(View.VISIBLE);

        LinearLayout lay = getActivity().findViewById(R.id.chartheader);
        lay.setVisibility(View.GONE);



        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (prefs.getInt("color",0) != 0) {
            int value = prefs.getInt("color",0);
          imageView1.setColorFilter(value);


            //spl.setBackgroundColor(value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(value);
            }
        }
        else{
            imageView1.setColorFilter(getActivity().getResources().getColor(R.color.brown));
        }

//       Bundle bundle = AHomeFragment.newInstance().getArguments();
        spdata = db.getMemberName();

        id = spdata.get(0).getId();


        list= db.getSelectedNoData1(id);

        if (list.size()>0)
        {
            LWAdd.setVisibility(View.GONE);
        }else {
            LWAdd.setVisibility(View.VISIBLE);
        }



        SpinAdapter adapter2 = new SpinAdapter(this.getActivity(), R.layout.simple_item, spdata);
        adapter2.setDropDownViewResource(R.layout.drop_item);
        spother.setAdapter(adapter2);


        spother.setSelection(0);



        spother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tmp = i;
                name = spdata.get(i).getName();


                id = spdata.get(i).getId();


                list= db.getSelectedNoData1(id);
                //Log.e("IDD","my:"+id+"/"+list.size());
                if (list.size()>0){
                    LWAdd.setVisibility(View.GONE);
                }else {
                    LWAdd.setVisibility(View.VISIBLE);
                }

                customAdapter = new customHomeF(getActivity(),R.layout.itemsss, list,AHomeFragment.this);
                homeList.setAdapter(customAdapter);

                customAdapter.notifyDataSetChanged();




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        customAdapter = new customHomeF(getActivity(),R.layout.itemsss, list,this);
        homeList.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();



        getData();  //left pill dialog







        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs1.getInt("color",0) != 0) {
            int themeColor = prefs1.getInt("color", 0);
            //Log.e("themeColor", themeColor + "");


            linearLayout.setBackgroundColor(themeColor);
        }







        //setInventry();
//        getData();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.container,new AddFragment());

                ft.commit();

                bnView.setSelectedItemId(R.id.add);

            }
        });

        return view;
    }



    public void dialogBox(String medicine,String id,int ndays,int leftpill) {
        // Create a new AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogCustom);

// Set the title and message for the dialog
        builder.setTitle("Refill reminder");
        if (ndays > leftpill){
            builder.setMessage("You have "+ndays +" days left for dose and your pills remain only "+medicine+".Please refill your medicine pills.");
        }
        else{
            builder.setMessage(medicine);
        }

        builder.setPositiveButton("Refill medicine", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), EditRefillActivity.class);
                intent.putExtra("name",medicine);
                intent.putExtra("id",id);
                intent.putExtra("lmed",leftpill);
                startActivity(intent);
            }
        });

// Set the positive and negative buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the positive button press
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);

        SharedPreferences prefs = getActivity().getSharedPreferences("dialog_prefs", MODE_PRIVATE);

// Get the timestamp of when the dialog was last shown
        long lastShownTimestamp = prefs.getLong("last_shown", 0);

// Calculate the time elapsed since the dialog was last shown
        long elapsedTime = System.currentTimeMillis() - lastShownTimestamp;
        long elapsedHours = elapsedTime / (60 * 60 * 1000);

// Check if the elapsed time is greater than 24 hours
        //Log.e("dialog show",elapsedHours+"");
        if (elapsedHours >= 24) {

            dialog.show();

            // Update the timestamp in the shared preferences
            prefs.edit().putLong("last_shown", System.currentTimeMillis()).apply();
      }



    }
    public void getData(){
        String str = "";
        String id = "";
        int noofday = 0;
        int leftpill = 0;
        int fday = 0;

           for (int i = 0; i < list.size(); i++) {
             leftpill=list.get(i).getLeftpills();
            int notify=list.get(i).getNotify();
             noofday=list.get(i).getNoofDays();
             fday = list.get(i).getFdays();
            if (leftpill==notify && noofday >0 && fday > 1)
            {
                str+=list.get(i).getMedicationName()+" : "+notify+ " left ,";
                id+=list.get(i).getId()+" ,";
               /* if (list.size()-1>i) {
                    str+=list.get(i).getMedicationName()+" : "+notify+ " left, ";
                    id+=list.get(i).getId()+", ";
                    //Log.e("MedicationName if",list.get(i).getMedicationName());
                }
                else {
                    str+=list.get(i).getMedicationName()+" : "+notify+ " left ";
                    id+=list.get(i).getId()+" ";
                    //Log.e("MedicationName else",list.get(i).getMedicationName());
                }*/


                //Log.e("MedicationName",list.get(i).getMedicationName());

            }




        } if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        if (id.endsWith(",")) {
            id = id.substring(0, id.length() - 1);
        }

        //Log.e("MedicationNameooo",str);
        //Log.e("MedicationNameooo",id);
           if (!str.isEmpty() && !id.isEmpty()){

            dialogBox(str,id,noofday,leftpill);
        }


    }


    @Override
    public void remove(int pos) {
     //   boolean isdeleted =  db.deletedata(list.get(pos).getId());



        new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
                .setTitle("Delete Medicine")
                .setMessage("Are you sure you want to delete this record?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        db.deletemedicationRecord(list.get(pos).getId());
                        db.deletemonthRecord(list.get(pos).getId());
                        // if (isdeleted){


                        list= db.getSelectedNoData1(id);
                        if (list.size()>0){
                            LWAdd.setVisibility(View.GONE);

                        }else {
                            LWAdd.setVisibility(View.VISIBLE);

                        }
                        customAdapter = new customHomeF(getActivity(),R.layout.itemsss, list,AHomeFragment.this);
                        homeList.setAdapter(customAdapter);
                        customAdapter.notifyDataSetChanged();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


           // Toast.makeText(getActivity(),"deleted",Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(getActivity()," not deleted",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void update(int pos) {
        showDialog(pos);
    }


    boolean changing;
    boolean changing1;
    public void showDialog(int pos){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.resume);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView title = dialog.findViewById(R.id.refill_title);
        TextView textView = dialog.findViewById(R.id.tvtxt);
        EditText editText = dialog.findViewById(R.id.etpills);
        EditText editText1 = dialog.findViewById(R.id.etnotify);
        Button button = dialog.findViewById(R.id.btnSave);

        ImageView imgR1 = dialog.findViewById(R.id.imgR1);
        ImageView imgR2 = dialog.findViewById(R.id.imgR2);
        ImageView imgR3 = dialog.findViewById(R.id.imgR3);

        TextInputLayout pillsNameError =dialog.findViewById(R.id.pillsNameError);
        TextInputLayout notifyNameError =dialog.findViewById(R.id.notifyNameError);


        title.setText("Continue "+list.get(pos).getMedicationName()+" Medicine");


        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.getInt("color", 0) != 0) {
            int themeColor = prefs.getInt("color", 0);
            //Log.e("themeColor", themeColor + "");
            imgR1.setColorFilter(themeColor);
            imgR2.setColorFilter(themeColor);
            imgR3.setColorFilter(themeColor);
            button.setBackgroundColor(themeColor);
            btnCancel.setBackgroundColor(themeColor);

            GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custom_button);
            shape.setColor(themeColor);
            button.setBackground(shape);
            btnCancel.setBackground(shape);




        }




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
                    pillsNameError.setError("please enter  current pills no");
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
                    notifyNameError.setError("please enter pills no");
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
                    pillsNameError.setError("please enter  current pills no");
                } else {
                    ispillsValid = true;
                    pillsNameError.setErrorEnabled(false);
                }if (notify == null || notify.isEmpty()){
                    isnotifyValid = false;
                    notifyNameError.setError("please enter pills no");
                }else {
                    isnotifyValid = true;
                    notifyNameError.setErrorEnabled(false);
                }
                if (ispillsValid && isnotifyValid ) {

                    int pill = Integer.parseInt(pills) + list.get(pos).getLeftpills();
                   // boolean Avi = db.RefillUpdate(list.get(pos).getId(),pill,Integer.parseInt(notify));
                    boolean Avi = db.updateResume(list.get(pos).getId(),Integer.parseInt(notify),pill,getCurrentDate1());
                    if (Avi) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),"Medicine resume notification starts from tomorrow",Toast.LENGTH_SHORT).show();
                        db.deletemonthRecord(list.get(pos).getId());

                        list= db.getSelectedNoData1(id);
                        if (list.size()>0){
                            LWAdd.setVisibility(View.GONE);

                        }else {
                            LWAdd.setVisibility(View.VISIBLE);

                        }
                        customAdapter = new customHomeF(getActivity(),R.layout.itemsss, list,AHomeFragment.this);
                        homeList.setAdapter(customAdapter);
                        customAdapter.notifyDataSetChanged();

                    }


                }



            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });





        dialog.show();
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
}