package com.vapps.medi.reminder.Fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vapps.medi.reminder.Activities.chartActivityDec;
import com.vapps.medi.reminder.Adapters.MyListAdapterR;
import com.vapps.medi.reminder.Interfaces.Addchart;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.R;

import java.util.ArrayList;
import java.util.Collections;

public class ChartFragment  extends Fragment implements Addchart {
    RecyclerView recyclerView;
    ArrayList<DataModelHomeF> listH;
    ArrayList<DataModel2> listmonth;
    DatabaseHelper db;
     MyListAdapterR adapter;
    LinearLayout showtxt;

    String userid,username;
    TextView title;
    ImageView imageView1;
    BottomNavigationView bnView;


    public ChartFragment() {
        // Required empty public constructor


    }

    public static ChartFragment newInstance() {

        return new ChartFragment();
    }

    public ChartFragment(String uname,String uid) {
        // Required empty public constructor

        this.username = uname;
        this.userid = uid;
      // //Log.e("BunD","val :"+uname+"/"+uid);
    }

   /* public static ChartFragment newInstance() {

        return new ChartFragment(newInstance().username, newInstance().userid);
    }*/

    public  void changeData(String uname, String uid, Context ctx,View view){
        this.username = uname;
        this.userid = uid;


        recyclerView = view.findViewById(R.id.recyclerView);
        showtxt = view.findViewById(R.id.showtxt);
        title = view.findViewById(R.id.title);






        db = new DatabaseHelper(ctx);
        listH = new ArrayList<>();
        listmonth = new ArrayList<>();



        listH.clear();
        listmonth.clear();
        Collections.reverse(listH);
        adapter = new MyListAdapterR(listH, ctx, this);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        registerForContextMenu(recyclerView);


        listH = db.getSelectedData(userid);   //adapter list
        listmonth = db.getListData(userid);








        if (listmonth.size()>0){
            ////Log.e("csize1",listH.size()+"");
            showtxt.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
        }else {
            showtxt.setVisibility(View.VISIBLE);
            ////Log.e("csize2",listH.size()+"");
            title.setVisibility(View.GONE);

        }


        if (listmonth.size() > 0) {
            //for ascending, descending order


            Collections.reverse(listH);
            adapter = new MyListAdapterR(listH, ctx, this);


            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            registerForContextMenu(recyclerView);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        showtxt = view.findViewById(R.id.showtxt);
        title = view.findViewById(R.id.title);


        imageView1 = view.findViewById(R.id.Addimg);
        bnView = getActivity().findViewById(R.id.bnView);


      /*  if (Admob.mInterstitialAd!=null){
            Admob.mInterstitialAd.show(getActivity());
        }*/


        //Log.e("SWSW","size:"+username+"/"+userid);

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

      //  refreshInterface.refresh_fragment();


//       refreshInterface = this;


        db = new DatabaseHelper(getActivity());
     /*   listH = new ArrayList<>();
        listmonth = new ArrayList<>();
        listH = db.getSelectedData(userid);   //adapter list
        listmonth = db.getListData(userid);





        if (listmonth.size()>0){
            ////Log.e("csize1",listH.size()+"");
            showtxt.setVisibility(View.GONE);
        }else {
            showtxt.setVisibility(View.VISIBLE);
            ////Log.e("csize2",listH.size()+"");

        }


        if (listmonth.size() > 0) {
            //for ascending, descending order


                Collections.reverse(listH);
                adapter = new MyListAdapterR(listH, getActivity(), this);


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                registerForContextMenu(recyclerView);



        }
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
        });*/
        return view;
    }


    @Override
    public void click(int pos, Context ctx, int total) {

        //  DataModelHomeF myRecListData = listH.get(pos);
        Intent intent = new Intent(ctx, chartActivityDec.class);
        intent.putExtra("id", listH.get(pos).getId());
        intent.putExtra("mName", listH.get(pos).getMedicationName());
        intent.putExtra("muserid", listH.get(pos).getUserid());
        intent.putExtra("days", listH.get(pos).getNoofDays());
        intent.putExtra("freq", listH.get(pos).getFrequency());
        intent.putExtra("total", listH.get(pos).getTime());
        intent.putExtra("type",listH.get(pos).getType());


        ctx.startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        //  adapter.notifyDataSetChanged();

        /*if (listmonth.size() > 0) {
            //for ascending, descending order
            //Collections.reverse(listH);

            adapter = new MyListAdapterR(listH, getActivity(), this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            registerForContextMenu(recyclerView);

        }*/




        listH = new ArrayList<>();
        listmonth = new ArrayList<>();

        listH = db.getSelectedData(MyHistory.uid);   //adapter list
        listmonth = db.getListData(MyHistory.uid);





        //Log.e("BunD","val333 :"+username+"/"+userid+"/"+listmonth.size());


        if (listmonth.size()>0){
            ////Log.e("csize1",listH.size()+"");
            showtxt.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
        }else {
            showtxt.setVisibility(View.VISIBLE);
            ////Log.e("csize2",listH.size()+"");
            title.setVisibility(View.GONE);

        }


        if (listmonth.size() > 0) {
            //for ascending, descending order


            Collections.reverse(listH);
            adapter = new MyListAdapterR(listH, getActivity(), this);


            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            registerForContextMenu(recyclerView);



        }
    }






  /*  private void launchChildFragment(){
        // creating the object for child fragment
        ChartFragment child = new ChartFragment(username,userid);
        // intialising the RefreshInterface object
        child.intialiseRefreshInterface(refreshInterface);
        // calling the child fragment
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, child).addToBackStack(null).commit();
    }


    @Override
    public void refresh_fragment() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        launchChildFragment();
    }*/



}