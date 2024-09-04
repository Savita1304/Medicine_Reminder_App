package com.vapps.medi.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.Fragments.MyHistory;
import com.vapps.medi.reminder.Models.DataModel2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    DatabaseHelper dbs;
    PDFView pdfView;
    String currrentImageUrl = null;
    Toolbar toolbar;
    ImageView imgbtng;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dbs = new DatabaseHelper(this);
        pdfView = findViewById(R.id.pdfView);
        LinearLayout LW = findViewById(R.id.LW);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        verifyStoragePermissions(ReportActivity.this);


        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getInt("color", 0) != 0) {
            int themeColor = prefs.getInt("color", 0);

            toolbar.setBackgroundColor(themeColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(themeColor);
            }
        }

        try
         {
         if (createPdf())
         {
             showFile();
         }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        LW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "LogHistory.pdf");//File path
                Intent share = new Intent();
                Uri uri = FileProvider.getUriForFile(ReportActivity.this,getPackageName()+".provider",outputFile);
                share.setAction(Intent.ACTION_SEND);
                share.setDataAndType(uri,"application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(share);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });




        }

    public static void verifyStoragePermissions(Activity activity)
    {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static String mColor = "ff0000";
    File file;
    public boolean createPdf() throws FileNotFoundException, DocumentException
    {


        File f = createDestination(MyHistory.uid+".pdf");
         file = new File(f.getAbsolutePath());

        /*SQLiteDatabase db = dbs.getWritableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME2, null);*/
        Document document = new Document();  // create the document
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();


        Font f1 = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.RED);
        Font f2 = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.NORMAL, BaseColor.BLACK);
// you created a font, but you never used it:
        Chunk c = new Chunk(MyHistory.uname+" Medication Report \n", f1);
        Chunk c2 = new Chunk(currentDate()+"\n\n\n", f2);


        Paragraph p1 = new Paragraph(c);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p1);

        Paragraph p2 = new Paragraph(c2);
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p2);

//        Paragraph p3 = new Paragraph();
//        p3.add("Medication Report \n");
//        p3.setFont(new Font(Font.FontFamily.TIMES_ROMAN,60));
//        //p3.setFont(new Font(Font.FontFamily.TIMES_ROMAN,40));
//        document.add(p3);


       /* Paragraph p4 = new Paragraph();
        p4.add(currentDate());
        p4.add("\n\n\nScheduled Medication\n\n");
        document.add(p4);*/

        PdfPTable table = new PdfPTable(3);
        int width = getWindowManager(). getDefaultDisplay(). getWidth();
        table.setTotalWidth(width*50/100);

       // table.setTotalWidth(new float[]{ 100, 100,100 });
        table.setLockedWidth(true);
        //table.addCell("Date");

        PdfPCell cell1 = new PdfPCell(new Phrase("Medicine Name",new Font(Font.FontFamily.TIMES_ROMAN,25)));
        cell1.setBackgroundColor(new BaseColor(211,211,211,1));
        cell1.setFixedHeight(40);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("Date/Time",new Font(Font.FontFamily.TIMES_ROMAN,25)));
        cell2.setBackgroundColor(new BaseColor(211,211,211,1));
        cell2.setFixedHeight(40);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell2);


        PdfPCell cell3 = new PdfPCell(new Phrase("Status",new Font(Font.FontFamily.TIMES_ROMAN,25)));
        cell3.setBackgroundColor(new BaseColor(211,211,211,1));
        cell3.setFixedHeight(40);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell3);





     /*   table.addCell("Medicine Name");
        table.addCell("Date/Time");
        table.addCell("Status");//*/






        ArrayList<DataModel2> list = dbs.getPDFData(MyHistory.uid);

        if (list.size() > 0){
            for (DataModel2 model : list) {

                PdfPCell entry = new PdfPCell(new Phrase(model.getMedicationName(),new Font(Font.FontFamily.TIMES_ROMAN,20)));
                entry.setFixedHeight(40);
                entry.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(entry);



                PdfPCell entry1 = new PdfPCell(new Phrase(model.getDate()+" / "+model.getTime(),new Font(Font.FontFamily.TIMES_ROMAN,20)));
                entry1.setFixedHeight(40);
                entry1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(entry1);

               // table.addCell(new PdfPCell(new Phrase(model.getMedicationName(),new Font(Font.FontFamily.TIMES_ROMAN,20))));
                //table.addCell(new PdfPCell(new Phrase(model.getDate()+" / "+model.getTime(),new Font(Font.FontFamily.TIMES_ROMAN,20))));
                String st = model.getStatus();
                if (st.equals("Yes")){


                    PdfPCell entry2 = new PdfPCell(new Phrase("Taken",new Font(Font.FontFamily.TIMES_ROMAN,20)));
                    entry2.setFixedHeight(40);
                    entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    entry2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(entry2);

                   // table.addCell(new PdfPCell(new Phrase("Taken",new Font(Font.FontFamily.TIMES_ROMAN,20))));

                }
                else{
                    PdfPCell entry2 = new PdfPCell(new Phrase("Not Taken",new Font(Font.FontFamily.TIMES_ROMAN,20)));
                    entry2.setFixedHeight(40);
                    entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    entry2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(entry2);
                   // table.addCell(new PdfPCell(new Phrase("Not Taken",new Font(Font.FontFamily.TIMES_ROMAN,20))));
                }



              /*  table.addCell(model.getMedicationName());
                table.addCell(model.getDate()+" / "+model.getTime());
                String st = model.getStatus();
                if (st.equals("Yes")){

                    table.addCell("Taken");
                }
                else{
                    table.addCell("Not Taken");
                }*/


            }
        }


    /*    while (c1.moveToNext()) {
            //String date = c1.getString(3);
            String Medicine = c1.getString(3);
            String Date = c1.getString(1);
            String Dec = c1.getString(6)+", at " +c1.getString(5);


            //table.addCell(date);
            table.addCell(Medicine);
            table.addCell(Date);
            table.addCell(Dec);
         //   table.addCell(pillName);

        }*/

        document.add(table);
        document.addCreationDate();
        document.close();

        // Assuming you got your pdf file:



return true;



    }

    File direct;

    public File createDestination(String fileName) {
        File wallpaperDirectory=null;
         direct = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/MedicineReminder");

        if (!direct.exists()) {
            wallpaperDirectory = new File(direct.getAbsolutePath());
            wallpaperDirectory.mkdirs();
        }

        File file = new File(direct, fileName);


        return file ;
    }


    public void showFile(){

      /*  File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "LogHistory.pdf");//File path
        //Log.e("fileee", pdfFile + "");*/


       // File file = new File(this.getCacheDir(), "test.pdf");
      //  if (!pdfFile.exists()) {
            try {
               // InputStream asset = this.getAssets().open("test.pdf");
                pdfView.fromFile(file)
                        .pages(0, 2, 1, 3, 3, 3)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(null)
                        .enableAntialiasing(true)
                        .spacing(0)
                        .load();
            } catch (Exception e) {
                e.printStackTrace();

            }
       /* } else {
            Toast.makeText(getApplicationContext(),
                    "File not found", Toast.LENGTH_LONG).show();
        }*/

    }



    public static String currentDate() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
         String Datetime = sdf.format(c.getTime());

        return  Datetime;
    }


}