package com.vapps.medi.reminder.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.databinding.FragmentRefillBinding;


import java.util.ArrayList;
import java.util.Calendar;


public class RefillFragment extends Fragment {

    FragmentRefillBinding binding;
    DatabaseHelper db;
    String pills,notify,T1,Rx;


    boolean ispillsValid,isnotifyValid,isT1Valid,isRxValid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_refill, container, false);

        binding = FragmentRefillBinding.bind(view);
        db = new DatabaseHelper(getActivity());








        return view;
    }

    public void timepicker(EditText view1) {

        // instance of our calendar.
        final Calendar c = Calendar.getInstance();

        // on below line we are getting our hour, minute.
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // on below line we are setting selected time
                // in our text view.

                view1.setText(hourOfDay + ":" + minute + " PM");
                //Log.e("hourOfDay", hourOfDay + ":" + minute);

            }
        }, hour, minute, false);
        // at last we are calling show to
        // display our time picker dialog.
        timePickerDialog.show();
    }

    public boolean isvalied(String pills) {
        boolean Avi = false;
        ArrayList<String> list = db.getAllRefillRec();
        for (int i = 0; i < list.size(); i++) {
            //Log.e("list.get(i)", list.get(i));
            if (list.get(i).equals(pills)) {
                Avi = true;
                break;
            }

        }
        return Avi;
    }



}

/* this code is above   return view;
  binding.etT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker(binding.etT1);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pills=binding.etpills.getText().toString();
                notify=binding.etnotify.getText().toString();
                T1=binding.etT1.getText().toString();
                Rx=binding.etRx.getText().toString();



                if (pills == null || pills.isEmpty()) {
                    ispillsValid = false;
                    binding.pillsNameError.setError("please enter  current pills no");
                } else {
                    ispillsValid = true;
                    binding.pillsNameError.setErrorEnabled(false);
                }if (notify == null || notify.isEmpty()){
                    isnotifyValid = false;
                    binding.notifyNameError.setError("please enter pills no");
                }else {
                    isnotifyValid = true;
                    binding.notifyNameError.setErrorEnabled(false);
                }

                if (T1 == null || T1.isEmpty()){
                    isT1Valid=false;
                    binding.timeNameError.setError("please enter time");
                }else {
                    isT1Valid=true;
                    binding.timeNameError.setErrorEnabled(false);
                }if (Rx == null || Rx.isEmpty()){
                    isRxValid=false;
                    binding.etRxNameError.setError("please enter Rx no");
                }else {
                    isRxValid=true;
                    binding.etRxNameError.setErrorEnabled(false);
                }

                if (ispillsValid && isnotifyValid && isT1Valid && isRxValid) {
                    boolean Avi = isvalied(pills);
                    if (Avi) {
                        Toast.makeText(getActivity(), "Already registered", Toast.LENGTH_SHORT).show();
                    } else {

                        DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
                        String id = db.getUid();

                        Boolean res = db.insertrefillRecord(id, pills, notify, T1, Rx);
                        if (res == true) {
                            Toast.makeText(getActivity(), "Successfully added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "not added", Toast.LENGTH_SHORT).show();

                        }
                    }
                }



            }
        });*/