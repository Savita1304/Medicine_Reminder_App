package com.vapps.medi.reminder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.vapps.medi.reminder.R;

import java.util.ArrayList;
import java.util.List;

public class ContainerFragment extends Fragment {

    private TabLayoutSetupCallback mToolbarSetupCallback;
    private List<String> mTabNamesList;

    public static  ContainerFragment newInstance() {
        // Required empty public constructor
        return new ContainerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.e("CCC","con:"+context);

      /* if (context instanceof MyHistory) {

            mToolbarSetupCallback = (TabLayoutSetupCallback) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement TabLayoutSetupCallback");
        }*/
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabNamesList = new ArrayList<>();
        mTabNamesList.add("Chart");
        mTabNamesList.add("List");






    }
    static ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_container1, container, false);
         viewPager = (ViewPager) view.findViewById(R.id.viewPager);



      //  Bundle b  = new Bundle();
        //int pos = getArguments().getInt("pos");
        ////Log.e("pos","po"+pos);


        viewPager.setAdapter(new ItemsPagerAdapter(getChildFragmentManager(), mTabNamesList));
        /*if (pos == 1){
            viewPager.setCurrentItem(1);
        }
        else if(pos == 2){
            viewPager.setCurrentItem(2);
        }
        else{
            viewPager.setCurrentItem(0);
        }*/
        //viewPager.setCurrentItem(0);
        mToolbarSetupCallback.setupTabLayout(viewPager);

        return view;
    }


    public static class ItemsPagerAdapter extends FragmentStatePagerAdapter {

        private List<String> mTabs = new ArrayList<>();

        public ItemsPagerAdapter(FragmentManager fm, List<String> tabNames) {
            super(fm);

            mTabs = tabNames;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0){
           // return ChartFragment.newInstance();
                return null;

            }
            else {
               return ListFragment1.newInstance();

            }

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position);
        }
    }

    public interface TabLayoutSetupCallback {
        void setupTabLayout(ViewPager viewPager);
    }
}
