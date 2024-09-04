package com.vapps.medi.reminder.services;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.R;

public class Report extends AppCompatActivity  {


    RecyclerView recyclerView;
    DatabaseHelper recordData;
   // reportdietlist customAdapter;
    NotificationManager nm;
    LinearLayout header,footer;
    SharedPreferences prefs ;
    String date,time;
    String alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        recyclerView =  findViewById(R.id.main_recycler);
        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);
        recordData = new DatabaseHelper(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(Report.this);
        if (prefs.getInt("color",0) != 0) {
            int value = prefs.getInt("color",0);
            footer.setBackgroundColor(value);
            header.setBackgroundColor(value);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(value);
            }

        }






        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String Pending = bundle.getString("Report");
             date = bundle.getString("date");
             time = bundle.getString("time");

              alert = getIntent().getStringExtra("alert");
             if (alert.equals("alert")){

             }
             else{
                 nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                 nm.cancel(Integer.parseInt(Pending));
             }


        }
    }
   /* ArrayList<reportModel> arr = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
        arr.clear();
        ArrayList<String> list = recordData.getdiet("1");
        ////Log.e("list size","pos :"+list);
        if (list.size() > 0){

            for (int i = 0;i<list.size();i++){
                String title = list.get(i);
                reportModel model = new reportModel();
                model.setTitle(title);
                model.setTemp(0);
                arr.add(model);
            }


            customAdapter = new reportdietlist(this,arr,date,time);
            recyclerView.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();

        }
        else{
            Toast.makeText(getApplicationContext(),"No data found!",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (alert.equals("alert")){
            Intent startMain = new Intent(this, MainActivity.class);
            startActivity(startMain);
            finish();

        }
        else{
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finishAffinity();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alert.equals("alert")){
//            Intent startMain = new Intent(this, MainActivity.class);
//            startActivity(startMain);
//            finish();

        }
        else{
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finishAffinity();
        }

    }*/
}