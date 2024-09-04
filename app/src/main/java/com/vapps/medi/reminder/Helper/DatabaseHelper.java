package com.vapps.medi.reminder.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vapps.medi.reminder.Models.DataModelHomeF;
import com.vapps.medi.reminder.Models.DataModel;
import com.vapps.medi.reminder.Models.DataModel2;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userRecord.db";
    public static final String userRecord = "userRecord";
    public static final String medicationRecord = "medicationRecord";
    public static final String TABLE_NAME2 = "month";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "age";
    public static final String COL_4 = "height";
    public static final String COL_5 = "weight";
    public static final String COL_6 = "gender";
    public static final String COL_7 = "profile";
    public static final String COL_8 = "authority";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  userRecord  " + "(id TEXT primary key , name TEXT, age TEXT,height TEXT,weight TEXT,  gender TEXT, profile BLOB,authority TEXT)");
//        db.execSQL("create table medicationRecord " + "(id TEXT primary key, medicationName TEXT, decName TEXT, shape TEXT,frequency TEXT,time1 DATETIME,time2 DATETIME,hours TEXT,week TEXT,time TEXT,noofDays INTEGER,alarmType TEXT,pillName TEXT,notify DATE,type TEXT,userName TEXT)");
        db.execSQL("create table medicationRecord " + "(id TEXT primary key, medicationName TEXT, decName TEXT, shape TEXT,frequency TEXT,time1 TEXT,time2 TEXT,hours TEXT,week TEXT,time TEXT,noofDays INTEGER,alarmType TEXT,pillName TEXT,notify DATE,type TEXT,userName TEXT,userid TEXT,medDate TEXT,FDays INTEGER,FPills INTEGER)");


        db.execSQL("create table month " + "(sno INTEGER PRIMARY KEY AUTOINCREMENT,id TEXT , date TEXT,time TEXT ,medicationName TEXT, Status TEXT, startTime TEXT,frequency TEXT,userName TEXT,userid TEXT )");

      //  db.execSQL("create table medicationRecord " + "(id TEXT,medicationName TEXT, decName TEXT, shape TEXT,frequency TEXT,time1 DATETIME,time2 DATETIME,time3 DATETIME,alarmType TEXT,foreign key(id) references userRecord(id))");
      //  db.execSQL("create table refillRecord " + "(id TEXT,pillName TEXT, notify DATETIME, time DATETIME, rx INTEGER,foreign key(id) references userRecord(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists userRecord");

    }

    public Boolean insertuserdata(DataModel model) {
        try {


            //if (model.getProfilepic() != null) {
            /*    FileInputStream fis = new FileInputStream(model.getProfilepic());
                byte[] image = new byte[fis.available()];

                fis.read(image);*/


                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_1, model.getId());
                contentValues.put(COL_2, model.getName());
                contentValues.put(COL_3, model.getAge());
                contentValues.put(COL_4, model.getHeight());
                contentValues.put(COL_5, model.getWeight());
                contentValues.put(COL_6, model.getGender());
                contentValues.put(COL_7, model.getBmp());
                contentValues.put(COL_8,model.getAuthority());


                long result = db.insert("userRecord", null, contentValues);
                if (result == -1) {
                    return false;
                }
//                fis.close();


           // }
        }catch (Exception e){
            //Log.e("Exception", e +"");
            return false;
        }
        return true;
    }



     public ArrayList<DataModel> getData() {

        ArrayList<DataModel> listCustomer = new ArrayList<DataModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from userRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            String id = cus.getString(0);

            String name = cus.getString(1);
            String age = cus.getString(2);
            String height = cus.getString(3);
            String weight = cus.getString(4);
            String gender = cus.getString(5);
           byte[] bmp = cus.getBlob(6);
           String authority = cus.getString(7);

           // String bmp = cus.getString(6);

           // Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
////Log.e("bmp",bmp+"");
            DataModel c = new DataModel(id, name, age, height, weight, gender,bmp,authority);
            listCustomer.add(c);

        }
        cus.close();

        return listCustomer;
    }


    public ArrayList<DataModel> getMemberName() {

        ArrayList<DataModel> listCustomer = new ArrayList<DataModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select id,name,authority from userRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {



            String id = cus.getString(0);
            String name = cus.getString(1);
            String authority = cus.getString(2);

            DataModel dm = new DataModel();

            dm.setId(id);
            dm.setName(name);
            dm.setAuthority(authority);

        //    //Log.e("name", name);


            listCustomer.add(dm);

        }
        cus.close();

        return listCustomer;
    }

   /* public ArrayList<String> getMemberName() {

        ArrayList<String> listCustomer = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from userRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {



            String name = cus.getString(1);

            //Log.e("name", name);


            listCustomer.add(name);

        }
        cus.close();

        return listCustomer;
    }*/

    public ArrayList<String> getAllName() {

        ArrayList<String> listCustomer = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from userRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            String name = cus.getString(1);

            listCustomer.add(name);

        }
        cus.close();

        return listCustomer;
    }


    public ArrayList<DataModel> getAllName1() {

        ArrayList<DataModel> listCustomer = new ArrayList<DataModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select id,name,authority  from userRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            String id = cus.getString(0);
            String name = cus.getString(1);
            String authority = cus.getString(2);

            DataModel dm = new DataModel();

            dm.setId(id);
            dm.setName(name);
            dm.setAuthority(authority);

            listCustomer.add(dm);

        }
        cus.close();

        return listCustomer;
    }

    public Boolean insertMedicationdata(String check, String id, String Name, String Decname, String Shapename, String Frequencyname, String T1, String T2, String Hours, String Week, String Time, int noDays, String Alarm, String Pillname, String Notify,String userName,String userid,String medate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("userName", userName);
        contentValues.put("medicationName", Name);
        contentValues.put("decName", Decname);
        contentValues.put("shape", Shapename);

        contentValues.put("frequency", Frequencyname);
        if (check.equals("single")){
            contentValues.put("time1", T1);
        }else if (check.equals("2time")){
            contentValues.put("time1", T1);
            contentValues.put("time2", T2);
        }else {
            //contentValues.put("custom", Custom);
            contentValues.put("hours", Hours);
           // contentValues.put("day", Day);
            contentValues.put("week", Week);
            contentValues.put("time", Time);
        }



        contentValues.put("noofdays", noDays);
        contentValues.put("alarmType", Alarm);
        contentValues.put("Pillname", Pillname);
        contentValues.put("Notify", Notify);
        contentValues.put("type", "Days");
        contentValues.put("userid",userid);
        contentValues.put("medDate",medate);
        contentValues.put("FDays",noDays);

        contentValues.put("FPills",Pillname);

        long result = db.insert("medicationRecord", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

public boolean updateDays(String id,int day,int pill){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues data=new ContentValues();
    data.put("noofDays",day);
    data.put("Pillname",pill);
    //Log.e("value==ConfirmACTIONss",day+"");
  int res = db.update("medicationRecord", data, "id" +"= '"+ id+"' " , null);


    if (res == 1) {
        return true;
    } else {
        return false;
    }
    }




    public boolean RefillUpdate(String id,int quan,int noti){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("pillName",quan);

        data.put("notify",noti);
String pill= quan+"";
        String notifyo= noti+"";
        String query="UPDATE medicationRecord SET pillName='"+pill+"' , notify='"+notifyo+"' WHERE ID='"+id.trim()+"'";
        //Log.e("query---",query);
        db.execSQL(query);
       // db.execSQL("UPDATE medicationRecord SET pillName='9' , notify='3' WHERE ID=id");
       //int res = db.update("medicationRecord", data, "id" +"= '"+ id.trim()+"' " , null);
     //   db.execSQL("UPDATE medicationRecord SET pillName = "+"'"+pills+"' "+ "WHERE Id = "+"'"+id+"'");

     /*   if (res == 1) {
            return true;
        } else {
            return false;
        }*/
        return true;
    }

    public boolean updateDays(String id,int day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("noofDays",day);

        int res = db.update("medicationRecord", data, "id" +"= '"+ id+"' " , null);


        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean insertrefillRecord(String id, String PillName, String Notify) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("pillName", PillName);
        contentValues.put("notify", Notify);
        //contentValues.put("time", Time);

        long result = db.insert("refillRecord", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;

        }

    }


    public ArrayList<DataModelHomeF> getDataMedication() {

        ArrayList<DataModelHomeF> listCustomer = new ArrayList<DataModelHomeF>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from medicationRecord";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(0);
            String mediName = cus.getString(1);
            String decName = cus.getString(2);
            String Shape = cus.getString(3);
            String Freq = cus.getString(4);
            String time1 = cus.getString(5);
            String time2 = cus.getString(6);
            String hours = cus.getString(7);
            //  String day = cus.getString(8);
            String week = cus.getString(8);
            String time = cus.getString(9);
            int noofDays = cus.getInt(10);


            String alarmType = cus.getString(11);
            String leftpill = cus.getString(12);
            String notify = cus.getString(13);
            //Log.e("leftpills77==", leftpill+""+notify);

            int leftpills = Integer.parseInt(leftpill);
            int notifs = Integer.parseInt(notify);
            //Log.e("leftpills77ss==", leftpill+"");
            String type = cus.getString(14);
            String userName = cus.getString(15);
            String userid = cus.getString(16);
            String meddate = cus.getString(17);
            int fdays = cus.getInt(18);
            int fpills = cus.getInt(19);
            //Log.e("type==879", type+"");
            DataModelHomeF c = new DataModelHomeF(id,mediName, decName, Shape,Freq, time1, time2, hours, week, time, noofDays, alarmType, leftpills,notifs,type,userName,false,userid,meddate,fdays,fpills);

            if (noofDays > 0) {
                listCustomer.add(c);
            }
        }
        cus.close();

        return listCustomer;
    }



    public ArrayList<DataModelHomeF> getSelectedNoData1(String uid) {

        ArrayList<DataModelHomeF> listCustomer = new ArrayList<DataModelHomeF>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from medicationRecord where userid = '"+uid+"'";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(0);
            String mediName = cus.getString(1);
            String decName = cus.getString(2);
            String Shape = cus.getString(3);
            String Freq = cus.getString(4);
            String time1 = cus.getString(5);
            String time2 = cus.getString(6);
            String hours = cus.getString(7);
            //  String day = cus.getString(8);
            String week = cus.getString(8);
            String time = cus.getString(9);
            int noofDays = cus.getInt(10);


            String alarmType = cus.getString(11);
            String leftpill = cus.getString(12);
            String notify = cus.getString(13);
            //Log.e("leftpills77==", leftpill+""+notify);

            int leftpills = Integer.parseInt(leftpill);
            int notifs = Integer.parseInt(notify);
            //Log.e("leftpills77ss==", leftpill+"");
            String type = cus.getString(14);
            String userName = cus.getString(15);
            String userid = cus.getString(16);
            String meddate = cus.getString(17);
            int fdays = cus.getInt(18);
            int fpills = cus.getInt(19);
            //Log.e("type==879", type+"");
            DataModelHomeF c = new DataModelHomeF(id,mediName, decName, Shape,Freq, time1, time2, hours, week, time, noofDays, alarmType, leftpills,notifs,type,userName,false,userid,meddate,fdays,fpills);
            listCustomer.add(c);
           /* if (noofDays > 0) {
                listCustomer.add(c);
            }*/
        }
        cus.close();

        return listCustomer;
    }

  /*  public ArrayList<DataModelHomeF> getSelectedNoData() {

        ArrayList<DataModelHomeF> listCustomer = new ArrayList<DataModelHomeF>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from medicationRecord";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(0);
            String mediName = cus.getString(1);
            String decName = cus.getString(2);
            String Shape = cus.getString(3);
            String Freq = cus.getString(4);
            String time1 = cus.getString(5);
            String time2 = cus.getString(6);
            String hours = cus.getString(7);
            //  String day = cus.getString(8);
            String week = cus.getString(8);
            String time = cus.getString(9);
            int noofDays = cus.getInt(10);


            String alarmType = cus.getString(11);
            String leftpill = cus.getString(12);
            String notify = cus.getString(13);
            //Log.e("leftpills77==", leftpill+""+notify);

            int leftpills = Integer.parseInt(leftpill);
            int notifs = Integer.parseInt(notify);
            //Log.e("leftpills77ss==", leftpill+"");
            String type = cus.getString(14);
            String userName = cus.getString(15);
            String userid = cus.getString(16);
            //Log.e("type==879", type+"");
            DataModelHomeF c = new DataModelHomeF(id,mediName, decName, Shape,Freq, time1, time2, hours, week, time, noofDays, alarmType, leftpills,notifs,type,userName,false,userid);

            if (noofDays > 0) {
                listCustomer.add(c);
            }
        }
        cus.close();

        return listCustomer;
    }*/




    public ArrayList<DataModelHomeF> getSelectedData1() {

        ArrayList<DataModelHomeF> listCustomer = new ArrayList<DataModelHomeF>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from medicationRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(0);
            String mediName = cus.getString(1);
            String decName = cus.getString(2);
            String Shape = cus.getString(3);
            String Freq = cus.getString(4);
            String time1 = cus.getString(5);
            String time2 = cus.getString(6);
            String hours = cus.getString(7);
            //  String day = cus.getString(8);
            String week = cus.getString(8);
            String time = cus.getString(9);
            int noofDays = cus.getInt(10);
            String alarmType = cus.getString(11);
            String leftpill = cus.getString(12);
            String notify = cus.getString(13);
            //Log.e("leftpills77==", leftpill+""+notify);

            int leftpills = Integer.parseInt(leftpill);
            int notifs = Integer.parseInt(notify);
            //Log.e("leftpills77ss==", leftpill+"");
            String type = cus.getString(14);
            String userName = cus.getString(15);
            String userid = cus.getString(16);
            String meddate = cus.getString(17);
            int fdays = cus.getInt(18);
            int fpills = cus.getInt(19);
            //Log.e("type==879:", userid);
            DataModelHomeF c = new DataModelHomeF(id,mediName, decName, Shape,Freq, time1, time2, hours, week, time, noofDays, alarmType, leftpills,notifs,type,userName,false,userid,meddate,fdays,fpills);
            listCustomer.add(c);
        }
        cus.close();

        return listCustomer;
    }

    public int getdateCount(String date ,String freq) {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *  from month where date = '"+date+"' and frequency = '"+freq+"'";

        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            count++;
        }
        return count;
    }


    public ArrayList<String> getcheckStatus(String mid,String date,String freq){
        ArrayList<String> temp = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select state  from month where id = '"+mid+"' date = '"+date+"' and frequency = '"+freq+"'";

        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            String ch = cus.getString(0);
            temp.add(ch);
        }
        return temp;
    }


    public int getdateCount1(String date ,String freq,String time) {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select  * from month where date = '"+date+"' and frequency = '"+freq+"' and time = '"+time+"' ";

        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            count++;
        }
        return count;
    }
    public ArrayList<DataModelHomeF> getSelectedData(String uid) {

        ArrayList<DataModelHomeF> listCustomer = new ArrayList<DataModelHomeF>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from medicationRecord where userid = '"+uid+"'";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(0);
            String mediName = cus.getString(1);
            String decName = cus.getString(2);
            String Shape = cus.getString(3);
            String Freq = cus.getString(4);
            String time1 = cus.getString(5);
            String time2 = cus.getString(6);
            String hours = cus.getString(7);
          //  String day = cus.getString(8);
            String week = cus.getString(8);
            String time = cus.getString(9);
            int noofDays = cus.getInt(10);
            String alarmType = cus.getString(11);
            String leftpill = cus.getString(12);
            String notify = cus.getString(13);
            //Log.e("leftpills77==", leftpill+""+notify);

            int leftpills = Integer.parseInt(leftpill);
            int notifs = Integer.parseInt(notify);
            //Log.e("leftpills77ss==", leftpill+"");
            String type = cus.getString(14);
            String userName = cus.getString(15);
            String userid = cus.getString(16);
            String meddate = cus.getString(17);
            int fdays = cus.getInt(18);
            int fpills = cus.getInt(19);
            //Log.e("type==879", type+"");
            DataModelHomeF c = new DataModelHomeF(id,mediName, decName, Shape,Freq, time1, time2, hours, week, time, noofDays, alarmType, leftpills,notifs,type,userName,false,userid,meddate,fdays,fpills);
            listCustomer.add(c);
        }
        cus.close();

        return listCustomer;
    }

    @SuppressLint("Range")
    public String getName(String id){
        //Log.e("getName---",id);
        String st="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select userName from medicationRecord where Id = '"+id.trim()+"' ";
        //Log.e("selectQuery---",selectQuery+":");
        Cursor res = db.rawQuery(selectQuery, null);
        //Log.e("res---",res+":");
        if( res != null && res.moveToFirst() ){
            st = res.getString(res.getColumnIndex("userName"));
            //Log.e("getNamessss---",st+":");
            res.close();
        }

        /*while(res.moveToNext()) {
            st = res.getString(15);
            //Log.e("getNamessss---",st+":");
        }*/
        return st;
    }


    public String gettime(String id){
        String st="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select time1 from medicationRecord where Id = '"+id+"'  ";
        Cursor res = db.rawQuery(selectQuery, null);
        while(res.moveToNext()) {
            st = res.getString(0);
            //  st = Time.split("-")[0];
        }
        return st;
    }
    public String gettime2(String id){
        String st="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select time2 from medicationRecord where Id = '"+id+"'  ";
        Cursor res = db.rawQuery(selectQuery, null);
        while(res.moveToNext()) {
            st = res.getString(0);
            //  st = Time.split("-")[0];
        }
        return st;
    }

    public String getcustomtime(String id){
        String st="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select time from medicationRecord where Id = '"+id+"'  ";
        Cursor res = db.rawQuery(selectQuery, null);
        while(res.moveToNext()) {
            st = res.getString(0);
            //  st = Time.split("-")[0];
        }
        return st;
    }
    public boolean SnoozeTimeUpdate(String id,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time1", value);
        db.update("medicationRecord", contentValues, "Id = ?", new String[]{id});
        return true;
    }
    public boolean SnoozeTimeUpdate2(String id,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time2", value);
        db.update("medicationRecord", contentValues, "Id = ?", new String[]{id});
        return true;
    }
    public boolean SnoozecustomTimeUpdate(String id,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", value);
        db.update("medicationRecord", contentValues, "Id = ?", new String[]{id});
        return true;
    }

    public boolean updateScheduleData(String Date,String Status,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ScheduleDate", Date);
        contentValues.put("ScheduleStatus", Status);
        contentValues.put("ScheduleTime", time);
        db.update("medicationRecord", contentValues, "ScheduleDate = ?", new String[]{Date});
        return true;
    }
    public boolean updateScheduleDefaulthourtime(String id,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time1", value);
      int res=  db.update("medicationRecord", contentValues, "Id = ?", new String[]{id});

        if (res == 1) {
            return true;
        } else {
            return false;
        }






    }


    public boolean updateScheduleDefaulthourtime2(String id,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time2", value);
        int res=  db.update("medicationRecord", contentValues, "Id = ?", new String[]{id});

        if (res == 1) {
            return true;
        } else {
            return false;
        }






    }

    public boolean updateScheduleDefaulthourcustomtime(String id,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", value);
        int res=  db.update("medicationRecord", contentValues, "Id = ?", new String[]{id});

        if (res == 1) {
            return true;
        } else {
            return false;
        }






    }
    public String getUid() {

        String id = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select id from userRecord";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            id = cus.getString(0);

        }
        return id;

    }

    public ArrayList<String> getAllRefillRec() {

        ArrayList<String> listCustomer = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from refillRecord ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {

            String id = cus.getString(0);

            listCustomer.add(id);

        }
        cus.close();

        return listCustomer;
    }



    public Boolean insertmonthdata(DataModel2 model2) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", model2.getId());
        contentValues.put("date", model2.getDate());
        contentValues.put("time", model2.getTime());
        contentValues.put("medicationName", model2.getMedicationName());
        contentValues.put("Status", model2.getStatus());
        contentValues.put("startTime", model2.getStartTime());//frequency
        contentValues.put("frequency", model2.getFrequency());
        contentValues.put("userName", model2.getUsername());
        contentValues.put("userid", model2.getUserid());



        long result = db.insert("month", null, contentValues);

        if (result == -1) {

            return false;
        }
        return true;
    }


    public ArrayList<DataModel2> getPDFData(String id){
        ArrayList<DataModel2> list = new ArrayList<DataModel2>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c1 = db.rawQuery("SELECT medicationName,date,Status,time FROM month where userid = '"+id+"'", null);

        while (c1.moveToNext()) {

            String Medicine = c1.getString(0);
            String Date = c1.getString(1);
            String status = c1.getString(2);
            String time = c1.getString(3);


            DataModel2 c = new DataModel2(Date,Medicine,status,time);

            list.add(c);


        }


        return list;
    }


    public int getnoofpils(String mid){
        int no=0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select pillName from medicationRecord where id = '"+mid+"'";
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            no = cus.getInt(0);
        }
        return no;
    }

    public int getnoofDays(String mid){
        int no=0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select noofDays from medicationRecord where id = '"+mid+"'";
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            no = cus.getInt(0);
        }
        return no;
    }

    public ArrayList<DataModel2> getListData(String myid) {

        ArrayList<DataModel2> listCustomer = new ArrayList<DataModel2>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from month where userid = '"+myid+"'";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(1);
            String date = cus.getString(2);
            String time = cus.getString(3);
            String mediName = cus.getString(4);
            String Status = cus.getString(5);
            String type = "Month";
            String startTime = cus.getString(6);
            String username = cus.getString(7);
            String uid = cus.getString(8);
            DataModel2 c = new DataModel2(id,date,time,mediName,  Status,type,startTime,username,uid);
            listCustomer.add(c);
        }
        cus.close();

        return listCustomer;
    }

    public ArrayList<DataModelHomeF> getChartData(String mid) {

        ArrayList<DataModelHomeF> listCustomer = new ArrayList<DataModelHomeF>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from medicationRecord "+ "where id='"+mid+"'";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(0);
            String mediName = cus.getString(1);
            String decName = cus.getString(2);
            String Shape = cus.getString(3);
            String Freq = cus.getString(4);
            String time1 = cus.getString(5);
            String time2 = cus.getString(6);
            String hours = cus.getString(7);
            String week = cus.getString(8);
            String time = cus.getString(9);
            int noofDays = cus.getInt(10);
            String alarmType = cus.getString(11);
            String leftpill = cus.getString(12);
            String notify = cus.getString(13);
            //Log.e("leftpills77==", leftpill+""+notify);

            int leftpills = Integer.parseInt(leftpill);
            int notifs = Integer.parseInt(notify);
            //Log.e("leftpills77ss==", leftpill+"");
            String type = cus.getString(14);
            String userName = cus.getString(15);
            String userid = cus.getString(16);
            String meddate = cus.getString(17);
            int fdays = cus.getInt(18);
            int fpills = cus.getInt(19);
            DataModelHomeF c = new DataModelHomeF(id,mediName, decName, Shape,Freq, time1, time2, hours, week, time, noofDays, alarmType, leftpills,notifs,type,userName,false,userid,meddate,fdays,fpills);
            listCustomer.add(c);




        }
        cus.close();

        return listCustomer;
    }


    public DataModel2 getmonthData(String mid) {

        DataModel2 listCustomer = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from month where id = '"+mid+"' order by sno Desc LIMIT 1 ";
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(1);
            String date = cus.getString(2);
            String time = cus.getString(3);
            String medicationName = cus.getString(4);
            String Status = cus.getString(5);
            String startTime = cus.getString(6);
            String Freq = cus.getString(7);
            String username = cus.getString(8);
            String uid = cus.getString(9);



            listCustomer = new DataModel2(id,date, time, medicationName,Status, startTime,Freq,username,uid);





        }
        cus.close();

        return listCustomer;
    }

    public ArrayList<DataModel2> getChartData1(String mid,String userid ,int no) {

        ArrayList<DataModel2> listCustomer = new ArrayList<DataModel2>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from month "+ "where id='"+mid+"' and userid = '"+userid+"' ORDER BY sno DESC LIMIT "+no;
        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(1);
            String date = cus.getString(2);
            String time = cus.getString(3);
            String medicationName = cus.getString(4);
            String Status = cus.getString(5);
            String startTime = cus.getString(6);
            String Freq = cus.getString(7);
            String username = cus.getString(8);
            String uid = cus.getString(9);



            DataModel2 c = new DataModel2(id,date, time, medicationName,Status, startTime,Freq,username,uid);
            listCustomer.add(c);




        }
        cus.close();

        return listCustomer;
    }

    public ArrayList<DataModel2> getSelectedMonthData(String mid,String userid) {

        ArrayList<DataModel2> listCustomer = new ArrayList<DataModel2>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from month "+ "where id='"+mid+"' and userid = '"+userid+"'";
        //Log.e("qQQ", query);
        Cursor cus = db.rawQuery(query, null);
        while (cus.moveToNext()) {
            String id = cus.getString(1);

            String date = cus.getString(2);
            String time = cus.getString(3);
            String mediName = cus.getString(4);
            String Status = cus.getString(5);
            String type = "Month";
            String startTime = cus.getString(6);
            String username = cus.getString(7);
            String uid = cus.getString(8);


            DataModel2 c = new DataModel2(id,date,time,mediName,  Status,type,startTime,username,uid);
            listCustomer.add(c);
        }
        cus.close();

        return listCustomer;
    }


    public boolean updateConfirm(String id,String Yes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("Status",Yes);

        //Log.e("updatevalue==",Yes+"");
        int res = db.update("month", data, "id" +"= '"+ id+"' " , null);


        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }



    public boolean updateConfirm4(String id,int pill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("pillName",pill);





        int res =  db.update("medicationRecord",
                data,
                "id" + " = ?",
                new String[]{id});
        //  int res = db.update("month", data, "id" +"= '"+ id+"' " , null);


        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }



    public boolean updateResume(String id,int days,int pill,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("noofDays",days);
        data.put("pillName",pill);
        data.put("medDate",date);
        data.put("FDays",days);
        data.put("FPills",pill);



        //Log.e("TGTG",":"+id+"/"+days+"/"+pill+"/"+date);



        int res =  db.update("medicationRecord",
                data,
                "id" + " = ?",
                new String[]{id});

        //  int res = db.update("month", data, "id" +"= '"+ id+"' " , null);


        //Log.e("PLPL",":"+res);

        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }




    public boolean updateConfirm3(String id,int days,int pill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("noofDays",days);
        data.put("pillName",pill);





        int res =  db.update("medicationRecord",
                data,
                "id" + " = ?",
                new String[]{id});
        //  int res = db.update("month", data, "id" +"= '"+ id+"' " , null);


        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateConfirm2(String id,String Yes,String time,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("Status",Yes);

        //Log.e("updatevalue==",Yes+"");


      /*  int res =  db.update("month",
                data,
                "id" + " = ? AND " + "time" + " = ?",
                new String[]{id,time,date});*/

        int res =  db.update("month",
                data,
                "id" + " = ? AND " + "time" + " = ? AND " + "date" + " = ?",
                new String[]{id,time,date});
      //  int res = db.update("month", data, "id" +"= '"+ id+"' " , null);


        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }




    public ArrayList<DataModel2> getDaysData(String id,String userid,int no){

        ArrayList<DataModel2> listCustomer = new ArrayList<DataModel2>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM month where id = ? and userid = ?  ORDER BY sno DESC LIMIT "+no;

        //Log.e("query", query);
        Cursor cus = db.rawQuery(query, new String[]{id,userid});
        while (cus.moveToNext()) {
            String ids = cus.getString(1);
            String dates = cus.getString(2);
            String time = cus.getString(3);
            String mediName = cus.getString(4);
            String Status = cus.getString(5);
            String startTime = cus.getString(6);
            String Freq = cus.getString(7);
            String username = cus.getString(8);
            String uid = cus.getString(9);
            DataModel2 c = new DataModel2(ids,dates,time,mediName,  Status,startTime,Freq,username,uid);
            listCustomer.add(c);
        }
        cus.close();

        return listCustomer;

    }



    public ArrayList<DataModel2> getDaysData2(String id,String userid,int no){
        ArrayList<DataModel2> listCustomer = new ArrayList<DataModel2>();
        SQLiteDatabase db = this.getWritableDatabase();



        String query = " SELECT * FROM month where id = ? and userid = ? ORDER BY sno DESC LIMIT "+no;


        Cursor cus = db.rawQuery(query, new String[]{id,userid});
        // //Log.e("query", cus.getString(0));
        while (cus.moveToNext()) {
            String ids = cus.getString(1);
            String dates = cus.getString(2);
            String time = cus.getString(3);
            String mediName = cus.getString(4);
            String Status = cus.getString(5);
            String startTime = cus.getString(6);
            String Freq = cus.getString(7);
            String username = cus.getString(8);
            String uid = cus.getString(9);
            //Log.e("queryRR", ids);
            DataModel2 c = new DataModel2(ids,dates,time,mediName,  Status,startTime,Freq,username,uid);
            listCustomer.add(c);
        }
        cus.close();

        return listCustomer;
    }




    public int deletemedicationRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("medicationRecord",
                "id = ? ",
                new String[] { id });
    }


    public int deletemonthRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("month",
                "id = ? ",
                new String[] { id });
    }



    public int deletemedicationRecord1(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("medicationRecord",
                "userid = ? ",
                new String[] { id });
    }


    public int deletemonthRecord1(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("month",
                "userid = ? ",
                new String[] { id });
    }



    public int deleteProfile(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("userRecord",
                "id = ? ",
                new String[] { id });
    }


}

