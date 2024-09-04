package com.vapps.medi.reminder.Activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vapps.medi.reminder.Models.DataModel;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.R;
import com.vapps.medi.reminder.databinding.ActivityMainBinding;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    CardView card1, card2, card3, card4, card5;
    Button btn;
    ImageView google_btn,ph_btn,avatar;
    GoogleSignInOptions gso;
    //GoogleSignInClient gsc;

    String picturePath;
    //RadioGroup rg;
    String Name, Age, Height, Weight, Gender = "male";
    TextInputEditText etname, etage, etheight, etweight;
    TextInputLayout nameError, ageError, heightError, WeightError;
    boolean isNaneValid, isAgeValid, isHeight, isWeight, isGenderValid;
    int age = 0;
    DatabaseHelper db;
    Spinner s1,s2;
    ActivityMainBinding binding;
    SharedPreferences prf;
    boolean mboolean = false;


    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;

    public static final int PERMISSION_REQUEST_CODE= 7;

 /*   String[] permissions=new String[]{
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,

    };*/
    String[] permissions = new String[]{
         Manifest.permission.CAMERA,
         WRITE_EXTERNAL_STORAGE,
         Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     //   Toast.makeText(getApplicationContext(),"Granted"+requestCode,Toast.LENGTH_SHORT).show();
      /*  switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied",+ Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }*/

        if (requestCode == 100) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something




              /*  SharedPreferences settings = MainActivity.this.getSharedPreferences("PREFS_NAME", 0);
                mboolean = settings.getBoolean("FIRST_RUN", false);
                if (!mboolean) {
                    // do the thing for the first time
                    settings = MainActivity.this.getSharedPreferences("PREFS_NAME", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("FIRST_RUN", true);
                    editor.commit();
                    Utils.startPowerSaverIntent(MainActivity.this);
                } else {
                    // other time your app loads
                }*/



//            }
            return;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    private void showMessageCamera(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("Gallery", okListener)
                .setNegativeButton("Camera", okListener)
                .create()
                .show();
    }


    boolean isConnected;
    public  void checknet(Context context_checknet){

        ConnectivityManager cm =
                (ConnectivityManager)context_checknet.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }


    AdView adView;

    boolean changing = false;
    boolean changing1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        db = new DatabaseHelper(MainActivity.this);
        ArrayList<DataModel> list = db.getData();


    //    //Log.e("RERE","time :"+addTime());





        if (list.size()>0){
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }else
        {
           // setContentView(R.layout.activity_main);
            binding=ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());



           checkPermissions();




           /* if (checkPermission()){

            }
            else{
                requestPermission();
            }
*/


            checknet(this);
            adView = findViewById(R.id.adView);

            MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {}
            });

            AdRequest adRequest = new AdRequest.Builder().build();



            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    adView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    adView.setVisibility(View.GONE);

                }

                @Override
                public void onAdOpened() {

                }

                @Override
                public void onAdClicked() {
                }

                @Override
                public void onAdClosed() {

                }
            });



            adView.loadAd(adRequest);



            s1 = findViewById(R.id.s1);
            s2 = findViewById(R.id.s2);


            avatar=findViewById(R.id.avatar);
            View bottomsheet = findViewById(R.id.bottomSheetLayout);
            behavior = BottomSheetBehavior.from(bottomsheet);
        btn = findViewById(R.id.btn);
            google_btn = findViewById(R.id.google_btn);
            ph_btn = findViewById(R.id.ph_btn);
        etname = findViewById(R.id.name);
        nameError = findViewById(R.id.nameError);


        etage = findViewById(R.id.age);
        ageError = findViewById(R.id.ageError);

        etheight = findViewById(R.id.height);
        heightError = findViewById(R.id.heightError);

        etweight = findViewById(R.id.weight);
        WeightError = findViewById(R.id.WeightError);
        //    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
         //   gsc = GoogleSignIn.getClient(MainActivity.this, gso);



            String[] feet = {"1","2","3","4","5","6","7","8","9","10"};
            String[] inch = {"0","1","2","3","4","5","6","7","8","9","10","11","12"};


            ArrayAdapter adapter1 = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item,feet);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s1.setAdapter(adapter1);
            s1.setSelection(4);




            ArrayAdapter adapter2 = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item,inch);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s2.setAdapter(adapter2);
            s2.setSelection(4);



            etname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }




                @Override
                public void afterTextChanged(Editable s) {
                    if( etname.getText().length()>0)
                    {

//
                        nameError.setError(null);

                    }
                    else {
                        nameError = findViewById(R.id.nameError);
                    }
                }
            });



            etage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }




                @Override
                public void afterTextChanged(Editable s) {

                    if (!changing && etage.getText().toString().startsWith("0")){
                        changing = true;
                        etage.setText(etage.getText().toString().replace("0", ""));
                    }
                    changing = false;
                    if( etage.getText().length()>0)
                    {

//
                        ageError.setError(null);

                    }
                    else {
                        ageError = findViewById(R.id.ageError);
                    }
                }
            });


            etweight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }




                @Override
                public void afterTextChanged(Editable s) {

                    if (!changing1 && etweight.getText().toString().startsWith("0")){
                        changing1 = true;
                        etweight.setText(etweight.getText().toString().replace("0", ""));
                    }
                    changing1 = false;
                    if( etweight.getText().length()>0)
                    {

//
                        WeightError.setError(null);

                    }
                    else {
                        WeightError = findViewById(R.id.WeightError);
                    }
                }
            });

            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        ((TextView) view).setTextColor(Color.BLACK); //Change selected text color

                    }catch (Exception e ){
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            final SharedPreferences sharedPreferences=getSharedPreferences("Data", Context.MODE_PRIVATE);


    Animation mm = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
    binding.Mcard1.startAnimation(mm);
    Animation mm1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move1);
    binding.Mcard2.startAnimation(mm1);
    Animation mm2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move3);
    binding.Mcard3.startAnimation(mm2);
    Animation mm3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move4);
    binding.Mcard4.startAnimation(mm3);
    binding.Mcard5.startAnimation(mm);




        binding.Rgrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.Male:
                        Gender = "Male";
                        break;
                    case R.id.Female:
                        Gender = "Female";
                        break;
                }
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = etname.getText().toString();
                Age = etage.getText().toString();
                Height = s1.getSelectedItem().toString()+" feet "+s2.getSelectedItem().toString()+" inch";
                Weight = etweight.getText().toString();
                //Log.e("Height===", Height + "");
                if (!Age.isEmpty()) {
                    age = Integer.parseInt(Age);
                    //Log.e("Age===", age + "");
                }


                if (Name == null || Name.isEmpty()) {
                    isNaneValid = false;
                    nameError.setError("please enter name");
                } else {
                    isNaneValid = true;
                    nameError.setErrorEnabled(false);
                }
                if (Age.isEmpty()) {
                    isAgeValid = false;
                    ageError.setError("please enter age");

                }
                else if (age <= 0 || age > 100) {

                    isAgeValid = false;
                    ageError.setError("Age should be greater then 0 and less then 100");

                } else {
                    isAgeValid = true;
                    ageError.setErrorEnabled(false);
                }
                if (Height == null || Height.isEmpty()) {
                    isHeight = false;
                    heightError.setError("please enter age");
                } else {
                    isHeight = true;
                    heightError.setErrorEnabled(false);
                }
                if (Weight == null || Weight.isEmpty()) {
                    isWeight = false;
                    WeightError.setError("please enter weight");
                } else {
                    isWeight = true;
                    WeightError.setErrorEnabled(false);
                }


                if (isNaneValid && isAgeValid && isHeight && isWeight) {
                    Bitmap icon = null;
                    String tag = binding.avatar.getTag().toString();
                    if (tag.equals("cam")) {
////Log.e("picturePath",picturePath);

                            icon = photo;

                    }
                    else if(tag.equals("gal")){
                        try {
                          //  icon = MediaStore.Images.Media.getBitmap(getContentResolver(), selecteduri);
                            icon = photo;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {

                        Bitmap def = BitmapFactory.decodeResource(getResources(),
                                R.drawable.avatar);

                        photo = Bitmap.createScaledBitmap(def,80, 80, true);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 40, baos);

                        icon = photo;

                    }

                    byte[] bytes = getBytes(icon);

                        DataModel model = new DataModel();

                        // //Log.e("gender",gender+":" + country  + contact);
                        String id = UUID.randomUUID().toString();
                        model.setId(id);
                        model.setName(Name);
                        model.setAge(Age);
                        model.setHeight(Height);
                        model.setWeight(Weight);
                        model.setGender(Gender);
                        model.setBmp(bytes);
                        model.setAuthority("Main");


                     //  model.setProfilepic(bytes.toString());

                        //check the data if data is already having in db..
                        boolean Avi = isAvailable(Name);
                        if (Avi) {
                            Toast.makeText(MainActivity.this, "Already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            if (db.insertuserdata(model)) {

                                SharedPreferences.Editor editor = getSharedPreferences("medicine", MODE_PRIVATE).edit();
//Log.e("nameMain",Name);
                                // SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("id", id);
                                editor.putString("name", Name);
                                editor.putString("age", Age);
                                editor.putString("height", Height);
                                editor.putString("weight", Weight);
                                editor.putString("gender", Gender);
                               // editor.putString("profile", selecteduri.toString());
                                editor.commit();




                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();
                               // Toast.makeText(MainActivity.this, "Successfully  registered", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(MainActivity.this, " Not registered", Toast.LENGTH_SHORT).show();

                            }

                        }



                }

            }
        });




        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        ph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this, OTPActivity.class);
//                //intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
//                startActivity(intent);
            }
        });


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ActivityCompat.requestPermissions( MainActivity.this,
                        new String[]{
                                READ_EXTERNAL_STORAGE,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE
                        }, 1
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                    if (Environment.isExternalStorageManager()){

                        // If you don't have access, launch a new activity to show the user the system's dialog
                        // to allow access to the external storage
                    }else{
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }*/

                /*if (checkPermission()){

                }
                else{
                    requestPermission();
                }*/

                showBottomSheetDialog();

            }
        });

        }
    }


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


/*    String tag = vehicleBinding.vimage.getTag().toString();

            if (tag.equals("none")){
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.car1);
    }
            else{
               *//*  vehicleBinding.vimage.buildDrawingCache();
                 icon = vehicleBinding.vimage.getDrawingCache();*//*

        try {
            icon = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void signIn() {
//        Intent signInIntent = gsc.getSignInIntent();
//        startActivityForResult(signInIntent, 1000);

    }


    Uri selecteduri = null;
  Bitmap photo= null;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            vehicleBinding.vimage.setImageBitmap(photo);
        }*/

        switch(requestCode) {
            case 0:
                if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
                     photo = (Bitmap) data.getExtras().get("data");
                    // selecteduri = data.getData();
                    photo = Bitmap.createScaledBitmap(photo,80, 80, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 40, baos);

                    binding.avatar.setImageBitmap(photo);
                    binding.avatar.setTag("cam");

                    mBottomSheetDialog.dismiss();

                }

                break;
            case 1:
                if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
                    selecteduri = data.getData();




                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(
                                selecteduri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                  //  photo = BitmapFactory.decodeStream(imageStream);

                    Bitmap resized = BitmapFactory.decodeStream(imageStream);

                    photo = Bitmap.createScaledBitmap(resized,80, 80, true);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 40, baos);

                    binding.avatar.setImageBitmap(photo);



                   // binding.avatar.setImageURI(selecteduri);
                    binding.avatar.setTag("gal");
                    mBottomSheetDialog.dismiss();
                }
                break;
            case 2:

                break;

        }

    }






/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            mBottomSheetDialog.dismiss();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(photo);


            File finalFile = new File(getRealPathFromURI(getImageUri(MainActivity.this,photo)));
            picturePath=finalFile.getAbsolutePath();


        }

       else if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    Uri uri = data.getData();
                    avatar.setImageURI(uri);

                    File finalFile = new File(FileUtils.getPath(MainActivity.this, uri));
                    picturePath=finalFile.getAbsolutePath();
                    //Log.e("photo", "uri" + finalFile);




                    mBottomSheetDialog.dismiss();

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }

      else {
            //Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }


    }*/




 /*   public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "cam", null);
        return Uri.parse(path);
    }
    public Uri getImageUri1(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "gal", null);
        return Uri.parse(path);
    }*/
    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    public boolean isAvailable(String username) {
        boolean Avi = false;
        ArrayList<String> list = db.getAllName();
        for (int i = 0; i < list.size(); i++) {
            //Log.e("list.get(i)", list.get(i));
            if (list.get(i).equals(username)) {
                Avi = true;
                break;
            }

        }
        return Avi;
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if(cursor != null && cursor.getCount() >0 && cursor.moveToFirst()){
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    //this is for profile pic
    private BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior behavior;
    ImageView closeSheet;
    Bitmap bitmap = null;
//    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int SELECT_IMAGE = 1;
    private void showBottomSheetDialog() {

        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

      mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        final LinearLayout camera = (LinearLayout) view.findViewById(R.id.imgCam);
        final LinearLayout gallery = (LinearLayout) view.findViewById(R.id.imgGall);
        closeSheet = view.findViewById(R.id.imgclose);
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               /* if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //Log.e("camera==","if");
                        requestPermissions(new String[]{ WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE},
                                MY_CAMERA_PERMISSION_CODE);
                    }



                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }*/


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        closeSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
                }*/


                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , GALLERY_REQUEST);//one can be replaced with any action code

            }
        });
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        behavior.setPeekHeight(300);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

    }



 /*   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {

               // Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }*/



    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                //Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }
 /*   private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                ;
    }*/


    private boolean checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }

        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }

        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }


        return true;
    }
/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {

                // Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }*/

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }



}