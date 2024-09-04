package com.vapps.medi.reminder.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vapps.medi.reminder.Interfaces.GalleryClick;
import com.vapps.medi.reminder.Interfaces.RemoveListner;
import com.vapps.medi.reminder.Models.DataModel;
import com.vapps.medi.reminder.R;

import java.util.ArrayList;

public class CustAdapter  extends ArrayAdapter<DataModel>
{
    private  int resocursLayout;
    Context context;
    ArrayList<DataModel> arrayList;
    GalleryClick listner;
    RemoveListner listner1;


    public CustAdapter(Context context, int res , ArrayList<DataModel> data, GalleryClick listner, RemoveListner listner1) {
        super(context,res,data);
        this.context=context;
        this.arrayList=data;
        this.resocursLayout=res;

        this.listner=listner;
        this.listner1 = listner1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(context);
            view=vi.inflate(resocursLayout,null);
        }
        TextView textView=view.findViewById(R.id.tvName);
        TextView textView1=view.findViewById(R.id.tvAge);
        TextView textView2=view.findViewById(R.id.tvHeight);
        TextView textView3=view.findViewById(R.id.tvWeight);
        TextView textView4=view.findViewById(R.id.tvGender);
        ImageView pimg1=view.findViewById(R.id.pimg1);
        ImageView pimg2=view.findViewById(R.id.pimg2);
        ImageView pimg3=view.findViewById(R.id.pimg3);
        ImageView pimg4=view.findViewById(R.id.pimg4);
        ImageView pimg5=view.findViewById(R.id.pimg5);

        ImageView profileimg=view.findViewById(R.id.profileitem);

        ImageView deleteprofile=view.findViewById(R.id.delprofile);



        SharedPreferences prefs ;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.getInt("color",0) != 0) {
           int themeColor = prefs.getInt("color", 0);
            //Log.e("themeColor", themeColor + "");
            pimg1.setColorFilter(themeColor);
            pimg2.setColorFilter(themeColor);
            pimg3.setColorFilter(themeColor);
            pimg4.setColorFilter(themeColor);
            pimg5.setColorFilter(themeColor);
            deleteprofile.setColorFilter(themeColor);
        }


        DataModel model=arrayList.get(position);
        textView.setText(model.getName());
        textView1.setText(model.getAge());
        textView2.setText(model.getHeight());
        textView3.setText(model.getWeight());
        textView4.setText(model.getGender());
       // //Log.e("imgShow",model.getBmp()+"");

        String authority = model.getAuthority();
        if (authority.equals("Member")){
            deleteprofile.setVisibility(View.VISIBLE);
        }
        else{
            deleteprofile.setVisibility(View.GONE);
        }

        byte[] bytes = model.getBmp();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        profileimg.setImageBitmap(bitmap);

       /* profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listner.click(position,view);
            }
        });*/


        deleteprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete profile dailog
                listner1.remove(position);
            }
        });

        return view;
    }



    @Override
    public int getCount() {
        return super.getCount();
    }
}
