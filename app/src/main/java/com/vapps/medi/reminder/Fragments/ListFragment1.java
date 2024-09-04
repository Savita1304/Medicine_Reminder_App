package com.vapps.medi.reminder.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vapps.medi.reminder.Adapters.MyAdapter;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Interfaces.Addchart;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment1  extends Fragment implements Addchart {

    ArrayList<DataModel2> listH;
    DatabaseHelper db;
    LinearLayout LWChart;
    String userid,username;
    ImageView imageView1;
    BottomNavigationView bnView;

    public ListFragment1() {
        // Required empty public constructor
    }

    public static ListFragment1 newInstance() {

        return new ListFragment1();
    }
    public ListFragment1(String uname,String uid) {
        // Required empty public constructor

        this.username = uname;
        this.userid = uid;
        //   //Log.e("BunD","val :"+uname);
    }



    public  void changeData1(String uname, String uid, Context ctx, View view){
        this.username = uname;
        this.userid = uid;




        db = new DatabaseHelper(ctx);
        listH=new ArrayList<>();


        listH.clear();





        listH = db.getListData(userid);

        Collections.reverse(listH);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewList);
        MyAdapter myAdapter = new MyAdapter(listH,this,ctx);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        registerForContextMenu(recyclerView);
        LWChart = view.findViewById(R.id.LWChart);
        if (listH.size()>0){
            LWChart.setVisibility(View.GONE);
        }else {
            LWChart.setVisibility(View.VISIBLE);

        }






    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        db = new DatabaseHelper(getActivity());
        listH=new ArrayList<>();

        listH = db.getListData(userid);   //only month
         LWChart = view.findViewById(R.id.LWChart);
        imageView1 = view.findViewById(R.id.Addimg);
        bnView = getActivity().findViewById(R.id.bnView);

        //Log.e("FGFG","Call");


        if (listH.size()>0){
            //Log.e("csize1",listH.size()+"");
            LWChart.setVisibility(View.GONE);
        }else {
            LWChart.setVisibility(View.VISIBLE);
            //Log.e("csize2",listH.size()+"");

        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

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


        Collections.reverse(listH);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewList);
        MyAdapter myAdapter = new MyAdapter(listH,this,getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        registerForContextMenu(recyclerView);




     /*   RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
*/


       /* imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.container,new AddFragment());

                ft.commit();

                bnView.setSelectedItemId(R.id.add);

            }
        });
*/

        return view;

    }



    @Override
    public void click(int pos, Context ctx, int total) {

        //Log.e("POS","change22"+getActivity());
        listH=new ArrayList<>();

        listH.clear();

         listH = db.getListData(userid);   //only month
         LWChart = getView().findViewById(R.id.LWChart);


        //Log.e("FGFG","Call");


        if (listH.size()>0){
            //Log.e("csize1",listH.size()+"");
            LWChart.setVisibility(View.GONE);
        }else {
            LWChart.setVisibility(View.VISIBLE);
            //Log.e("csize2",listH.size()+"");

        }


        Collections.reverse(listH);

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewList);
        MyAdapter myAdapter = new MyAdapter(listH,this,getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        registerForContextMenu(recyclerView);

    }
}
