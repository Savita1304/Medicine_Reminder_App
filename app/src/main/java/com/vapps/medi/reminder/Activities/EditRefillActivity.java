package com.vapps.medi.reminder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.databinding.ActivityEditRefillBinding;


import java.util.ArrayList;

public class EditRefillActivity extends AppCompatActivity {
    ActivityEditRefillBinding binding;
    DatabaseHelper db;
    ArrayList<DataModelHomeF> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditRefillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DatabaseHelper(this);

        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getInt("color", 0) != 0) {
            int themeColor = prefs.getInt("color", 0);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(themeColor);
            }


        }
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("id");
        int leftpill = getIntent().getIntExtra("lmed",0);
        //Log.e("name=", name);
        //Log.e("name==", id);
        arrayList = new ArrayList<>();

        if (id.contains(",")) {
            String[] ids = id.split(",");
            String[] medicineName = name.split(",");
            //Log.e("ids", ids.length + ":" + medicineName.length);
            for (int i = 0; i < ids.length; i++) {
                if (!ids[i].isEmpty() && !medicineName[i].isEmpty()) {
                    String mid = ids[i];
                    String medi1 = medicineName[i];
                    //Log.e("idsnnn", mid + ":" + medi1);
                    String userName = db.getName(mid);
                    //Log.e("userName18", userName + ":" + medi1);
                    DataModelHomeF modal = new DataModelHomeF(mid, medi1, userName,leftpill);

                    arrayList.add(modal);
                }

            }

        } else {
            String userName = db.getName(id);
            DataModelHomeF modal = new DataModelHomeF(id, name, userName,leftpill);

            arrayList.add(modal);
        }

        RefillAdapter customAdapter = new RefillAdapter(this, R.layout.refill_item, arrayList);
        binding.listview.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();



    }


}
//this code is above   return view;
 /* binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pills=binding.etpills.getText().toString();
                notify=binding.etnotify.getText().toString();



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
                if (ispillsValid && isnotifyValid ) {

                    boolean Avi = isvalied(pills);
                    if (Avi) {
                        Toast.makeText(EditRefillActivity.this, "Already registered", Toast.LENGTH_SHORT).show();
                    } else {

                        DatabaseHelper db = new DatabaseHelper(EditRefillActivity.this.getApplicationContext());
                        String id = db.getUid();

                        Boolean res = db.insertrefillRecord(id, pills, notify);
                        if (res == true) {
                            Toast.makeText(EditRefillActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditRefillActivity.this, "not added", Toast.LENGTH_SHORT).show();

                        }
                    }
                }



            }
        });*/