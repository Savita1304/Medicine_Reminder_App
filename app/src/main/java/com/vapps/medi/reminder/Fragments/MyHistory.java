package com.vapps.medi.reminder.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Models.DataModel2;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.ReportActivity;
import com.vapps.medi.reminder.Adapters.SpinAdapter;
import com.vapps.medi.reminder.Models.DataModel;

import java.util.ArrayList;
import java.util.List;

public class MyHistory extends Fragment implements ViewPager.OnPageChangeListener  {

    public Spinner spother;
    ArrayList<DataModel> spdata = new ArrayList<>();
   public static String uid,uname;

    public static  int tmp = 0;
    DatabaseHelper databaseHelper;
    Bundle bundle;

    ViewPager viewPager;


    String temp = "";

   public static boolean changeState = false;


    public static MyHistory newInstance() {

        return new MyHistory();
    }

    public MyHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);






    }
  //  ViewPager viewPager;


    TabLayout tabs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history,container, false);

        databaseHelper = new DatabaseHelper(getActivity());

     /*   LinearLayout linearLayout = getActivity().findViewById(R.id.toolbar);
        linearLayout.setVisibility(View.GONE);*/

        ImageView imageView = getActivity().findViewById(R.id.setting);
        imageView.setVisibility(View.GONE);

        ImageView logo = getActivity().findViewById(R.id.logo);
        logo.setVisibility(View.GONE);
        LinearLayout lay = getActivity().findViewById(R.id.chartheader);
        lay.setVisibility(View.VISIBLE);


        TextView header = getActivity().findViewById(R.id.header);
        header.setVisibility(View.GONE);

        //Log.e("CALL","FG :"+tmp);

        /*if (Admob.mInterstitialAd!=null){
            Admob.mInterstitialAd.show(getActivity());
        }*/



        spother = getActivity().findViewById(R.id.spother);

        spdata = databaseHelper.getMemberName();

        uid = spdata.get(0).getId();
        uname = spdata.get(0).getName();





        SpinAdapter adapter2 = new SpinAdapter(this.getActivity(), R.layout.simple_item, spdata);
        adapter2.setDropDownViewResource(R.layout.drop_item);
        spother.setAdapter(adapter2);


        spother.setSelection(0,false);





        //  ChartFragment fragment_1 = new ChartFragment(uname,uid);





        spother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                tmp = i;
                uname = spdata.get(i).getName();
                uid = spdata.get(i).getId();






                new ChartFragment().changeData(uname,uid,getActivity(),getView());
                new ListFragment1().changeData1(uname,uid,getActivity(),getView());







            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });






        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabs = (TabLayout) view.findViewById(R.id.tabLayout);


        tabs.setupWithViewPager(viewPager);

       //Toolbar toolbar = view.findViewById(R.id.toolbar);
        LinearLayout LL = getActivity().findViewById(R.id.LL);




        SharedPreferences prefs ;
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.getInt("color",0) != 0) {
           int  themeColor = prefs.getInt("color", 0);
           tabs.setBackgroundColor(themeColor);
           // toolbar.setBackgroundColor(themeColor);
        }

        LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseHelper db = new DatabaseHelper(getActivity());
                //Log.e("MYID","/"+uid);
                ArrayList<DataModel2> list = db.getListData(uid);
                if (list.size() > 0){
                    Intent intent = new Intent(getActivity(), ReportActivity.class);
                    startActivity(intent);


                }
                else{
                    Toast.makeText(getActivity(),"Data not found",Toast.LENGTH_SHORT).show();
                }


            }
        });

        //setThemecolor(imageView,imageView2);






        viewPager.setOnPageChangeListener(this);



        return view;

    }



    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new ChartFragment(uname,uid), "Weekly Progress");
        adapter.addFragment(new ListFragment1(uname,uid), "All History");

        viewPager.setAdapter(adapter);



    }

    private int currentPosition;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      //  Toast.makeText(getActivity(),"onPageScrolled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {
        //Log.e("POS","change");
        if(currentPosition < position) {
            // handle swipe LEFT
           // Toast.makeText(getActivity(),"Left",Toast.LENGTH_SHORT).show();



            if (changeState) {
                new ListFragment1().changeData1(uname, uid, getActivity(), getView());
                changeState = false;
            }
        } else if(currentPosition > position){
            // handle swipe RIGHT

            if (changeState) {
                new ChartFragment().changeData(uname, uid, getActivity(), getView());
                changeState = false;
            }
           // Toast.makeText(getActivity(),"Right",Toast.LENGTH_SHORT).show();
           //new ChartFragment();
        }
        currentPosition = position; // Update current position
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }



    }

}
