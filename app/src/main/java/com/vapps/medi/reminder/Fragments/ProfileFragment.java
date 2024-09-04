package com.vapps.medi.reminder.Fragments;

import static android.app.Activity.RESULT_OK;

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
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vapps.medi.reminder.Adapters.CustAdapter;
import com.vapps.medi.reminder.Interfaces.GalleryClick;
import com.vapps.medi.reminder.Interfaces.RemoveListner;
import com.vapps.medi.reminder.Models.DataModel;
import com.vapps.medi.reminder.Helper.DatabaseHelper;
import com.vapps.medi.reminder.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;


public class ProfileFragment extends Fragment  implements  GalleryClick, RemoveListner {
    DatabaseHelper db;
    ListView ListV;
    Button addbtn, btn1;
    Uri resultUri;
    CardView card1, card2, card3, card4, card5;
    Spinner adds1,adds2;

    ArrayList<DataModel> list;

    RadioGroup rg;
    String Name, Age, Height, Weight, Gender = "male";
    TextInputEditText etname, etage, etheight, etweight;
    TextInputLayout nameError, ageError, heightError, WeightError;
    boolean isNaneValid, isAgeValid, isHeight, isWeight, isGenderValid;
    int age = 0;
    ImageView setting,addimg1,addimg2,addimg3,addimg4,addimg5,addprofileimg,deleteprofile;
    int themeColor;
    String picturePath;
    SharedPreferences prefs1 ;
    LinearLayout linearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        linearLayout = getActivity().findViewById(R.id.toolbar);
        linearLayout.setVisibility(View.VISIBLE);







        ImageView logo = getActivity().findViewById(R.id.logo);
        logo.setVisibility(View.GONE);


        TextView header = getActivity().findViewById(R.id.header);
        header.setVisibility(View.GONE);

        LinearLayout lay = getActivity().findViewById(R.id.chartheader);
        lay.setVisibility(View.GONE);


        ImageView imageView = getActivity().findViewById(R.id.setting);
        imageView.setVisibility(View.VISIBLE);
        db = new DatabaseHelper(getContext());
        ListV = view.findViewById(R.id.ListV);
        addbtn = view.findViewById(R.id.addbtn);
      //  setting = view.findViewById(R.id.setting);
      //  profileimg=view.findViewById(R.id.profileimg);


        AdView adView = getActivity().findViewById(R.id.adView);
        adView.setVisibility(View.VISIBLE);




        prefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs1.getInt("color",0) != 0) {
            int themeColor = prefs1.getInt("color", 0);
            //Log.e("themeColor", themeColor + "");


            linearLayout.setBackgroundColor(themeColor);


            GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custom_button);
            shape.setColor(themeColor);
            addbtn.setBackground(shape);
        }

        get();

      /*  ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog(this);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet.show(getFragmentManager()
                        , "exampleBottomSheet");
            }
        });*/



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmember();
            }
        });





        return view;
    }
    boolean changing = false;
    boolean changing1 = false;



    public void addmember() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // builder.setTitle("Select Medicine time");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.addmember, null);
        builder.setView(customLayout);





        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        addmembercustom(customLayout, dialog);


    }


    public void addmembercustom(View view, AlertDialog alertDialog) {


        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        btn1 = view.findViewById(R.id.btn1);
        etname = view.findViewById(R.id.name);
        nameError = view.findViewById(R.id.nameError);
        addprofileimg = view.findViewById(R.id.addprofileimg);




        etname = view.findViewById(R.id.name);
        nameError = view.findViewById(R.id.nameError);

        etage = view.findViewById(R.id.age);
        ageError = view.findViewById(R.id.ageError);


        adds1 = view.findViewById(R.id.adds1);
        adds2 = view.findViewById(R.id.adds2);

        etheight = view.findViewById(R.id.height);
        heightError = view.findViewById(R.id.heightError);
        etweight = view.findViewById(R.id.weight);
        WeightError = view.findViewById(R.id.WeightError);



        View bottomsheet = view.findViewById(R.id.bottomSheetLayout);
        behavior = BottomSheetBehavior.from(bottomsheet);


        addimg1 = view.findViewById(R.id.addimg1);
        addimg2 = view.findViewById(R.id.addimg2);
        addimg3 = view.findViewById(R.id.addimg3);
        addimg4 = view.findViewById(R.id.addimg4);
        addimg5 = view.findViewById(R.id.addimg5);


        String[] feet = {"1","2","3","4","5","6","7","8","9","10"};
        String[] inch = {"0","1","2","3","4","5","6","7","8","9","10","11","12"};


        ArrayAdapter adapterAdd = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,feet);
        adapterAdd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adds1.setAdapter(adapterAdd);
        adds1.setSelection(4);




        ArrayAdapter adapterAdd2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,inch);
        adapterAdd2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adds2.setAdapter(adapterAdd2);
        adds2.setSelection(4);


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
                    nameError = view.findViewById(R.id.nameError);
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
                    ageError = view.findViewById(R.id.ageError);
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
                    WeightError = view.findViewById(R.id.WeightError);
                }
            }
        });
        adds1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



        addprofileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });


            SharedPreferences prefs ;
            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            if (prefs.getInt("color",0) != 0) {
               int themeColor = prefs.getInt("color", 0);
                //Log.e("themeColor", themeColor + "");
                addimg1.setColorFilter(themeColor);
                addimg2.setColorFilter(themeColor);
                addimg3.setColorFilter(themeColor);
                addimg4.setColorFilter(themeColor);
                addimg5.setColorFilter(themeColor);



                GradientDrawable shape=(GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.custom_button);
                shape.setColor(themeColor);
                btn1.setBackground(shape);
            }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = etname.getText().toString();
                Age = etage.getText().toString();
               // Height = etheight.getText().toString();
                Height = adds1.getSelectedItem().toString()+" feet "+adds2.getSelectedItem().toString()+" inch";

                Weight = etweight.getText().toString();

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

                } else if (age <= 0 || age > 100) {

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
                    String tag =addprofileimg.getTag().toString();
                    if (tag.equals("cam")) {
////Log.e("picturePath",picturePath);

                            icon = photo;

                    }
                    else if(tag.equals("gal")){
                        try {
                         //   icon = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selecteduri);
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

                    // if (picturePath != null) {
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
                        model.setAuthority("Member");
                      //  model.setProfilepic(bytes.toString());


                        boolean Avi = isAvailable(Name);
                        if (Avi) {
                            alertDialog.dismiss();
                            Toast.makeText(getActivity(), "Already registered", Toast.LENGTH_SHORT).show();
                        } else {
                        if (db.insertuserdata(model)) {
                            alertDialog.dismiss();
                            get();
                          //  Toast.makeText(getActivity(), "Successfully  registered", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getActivity(), " Not registered", Toast.LENGTH_SHORT).show();

                        }

                        }


                    //}

                }


            }
        });


    }


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public boolean isAvailable(String username){
        boolean Avi=false;
        ArrayList<String> list= db.getAllName();
        for (int i=0; i<list.size(); i++ )
        {
            //Log.e("list.get(i)",list.get(i));
           if (list.get(i).equals(username))
           {
              Avi=true;
              break;
           }

        }
        return Avi;
    }

    public  void get(){
         list = db.getData();
        //Log.e("list of length", list.size() + "");
        CustAdapter customAdapter = new CustAdapter(getActivity(), R.layout.item, list,this,this);
        ListV.setAdapter(customAdapter);
    }



    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }




    Uri selecteduri = null;



    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    Bitmap photo=null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            vehicleBinding.vimage.setImageBitmap(photo);
        }*/

        switch(requestCode) {
            case 0:
                if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){

                     photo = (Bitmap) data.getExtras().get("data");
                    photo = Bitmap.createScaledBitmap(photo,80, 80, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                    addprofileimg.setImageBitmap(photo);
                    addprofileimg.setTag("cam");
                    mBottomSheetDialog.dismiss();

                }

                break;
            case 1:
                if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
                    selecteduri = data.getData();



                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(
                                selecteduri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                     Bitmap resized = BitmapFactory.decodeStream(imageStream);

                     photo = Bitmap.createScaledBitmap(resized,80, 80, true);



                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 40, baos);

                    addprofileimg.setImageBitmap(photo);
                 //   addprofileimg.setImageURI(selecteduri);
                    addprofileimg.setTag("gal");
                    mBottomSheetDialog.dismiss();
                }
                break;
            case 2:

                break;

        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "mem", null);
        return Uri.parse(path);
    }




    private BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior behavior;
    ImageView closeSheet;
    Bitmap bitmap = null;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int SELECT_IMAGE = 1;
    private void showBottomSheetDialog() {

        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        final LinearLayout camera = (LinearLayout) view.findViewById(R.id.imgCam);
        final LinearLayout gallery = (LinearLayout) view.findViewById(R.id.imgGall);
        closeSheet = view.findViewById(R.id.imgclose);
        camera.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
               /* if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //Log.e("camera==","if");
                        requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_CAMERA_PERMISSION_CODE);
                    }



                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }*/


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

              /*  if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_CAMERA_PERMISSION_CODE);


                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }*/
            //}
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
             /*   Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);*/

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



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             //   Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {

              //  Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }



    @Override
    public void click(int pos, View view) {
showBottomSheetDialog();
    }

    @Override
    public void remove(int pos) {
        new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
                .setTitle("Remove User")
                .setMessage("Are you sure you want to delete this User?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        db.deletemedicationRecord1(list.get(pos).getId());
                        db.deletemonthRecord1(list.get(pos).getId());
                        db.deleteProfile(list.get(pos).getId());
                        // if (isdeleted){


                     get();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void update(int pos) {

    }
}



