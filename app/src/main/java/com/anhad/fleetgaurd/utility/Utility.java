package com.anhad.fleetgaurd.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.anhad.fleetgaurd.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class used for all the common methods throughout the application
 * @author T3 Team
 * Created on 23-June-2016
 */
public class Utility {

    private static Gson gson;

    public static String getCurrentDate(){
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        return (df.format(dateobj));
    }

    public static Fragment getCurrentFragment(AppCompatActivity activity){
        android.support.v4.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    public static void clearBackStack(Context context){
        ((AppCompatActivity)context).getSupportFragmentManager().getBackStackEntryCount();
        int size = ((AppCompatActivity)context).getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < size; i++) {
            ((AppCompatActivity)context).getSupportFragmentManager().popBackStack();
        }
    }

    // A method to control navigation into application
    public static void navigateToActivity(Activity activity,Class targetActivity, boolean isFinish)
    {
        Intent intent = new Intent(activity,targetActivity);
        activity.startActivity(intent);

        if(isFinish)
            activity.finish();

    }

    public static void requestFocus(View view,Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static boolean isValidMobileNumber(String mobileNumber)
    {
        boolean isValidNumber = false;
        if(mobileNumber.length() > 0){
            isValidNumber = (mobileNumber.length() < 17 && mobileNumber.length() > 7);
            return isValidNumber;
        }
        return true;
    }
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }

    public static boolean isValidZipCode(String zipCode){

        boolean isZipCodevalid = false;
        if(!TextUtils.isEmpty(zipCode)) {
            isZipCodevalid = (zipCode.length() < 11 && zipCode.length() > 1);
        }

        return isZipCodevalid;
    }

    public static boolean isPasswordValid(String password){

        boolean isValidPassword = false;
        if(!TextUtils.isEmpty(password)) {
            isValidPassword = (password.length() < 31 && password.length() > 5);
        }

        return isValidPassword;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone)
    {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }



    public static void showDialog(Context context, String str)
    {
        if(context != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(str);
            builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().dismiss();
                }
            });
            builder.show();
        }
    }



    /*  public static void showDialogForPreLollipop(final Context context,boolean cancelable, String title, String message,
                                                String positiveButtonName, String negativeButtonName, int alertIcon,
                                                final DialogButtonListener mDialogButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(alertIcon);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(positiveButtonName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                if (mDialogButtonClickListener != null) {
                    mDialogButtonClickListener.onPositiveClick(dialog);

                }
            }
        })
                .setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        if (mDialogButtonClickListener != null) {
                            mDialogButtonClickListener.onNegativeClick(dialog);
                        }
                    }
                });
        builder.show();
    }
*/
  /*  public static void showTwoButtonAlertDialog(final Context context,boolean cancelable, String title, String message,
                                         String positiveButtonName, String negativeButtonName, int alertIcon,
                                         final DialogButtonListener mDialogButtonClickListener) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        //builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(alertIcon);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(positiveButtonName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                if (mDialogButtonClickListener != null) {
                    mDialogButtonClickListener.onPositiveClick(dialog);

                }
            }
        })
                .setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        if (mDialogButtonClickListener != null) {
                            mDialogButtonClickListener.onNegativeClick(dialog);
                        }
                    }
                });
        builder.show();

    }

    public static void showOneButtonAlertDialog(final Context context, String title, String message,
                                         String buttonName, int alertIcon,
                                         final DialogButtonListener mDialogButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        //builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(alertIcon);
        builder.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                if (mDialogButtonClickListener != null) {
                    mDialogButtonClickListener.onPositiveClick(dialog);

                }
            }
        });
        builder.show();
    }
*/
    public static ProgressDialog getProgressDialog(Context context,String message)
    {
        ProgressDialog dialog  = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        return dialog;
    }

    public static void hideKeypad(Activity activity){
        View view = activity.getCurrentFocus();
        if (null != view) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, String fragmentId, boolean isBackStackNeeded){
        android.support.v4.app.FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, fragment, fragmentId);
        if(isBackStackNeeded)
            ft.addToBackStack(fragmentId);
        ft.commit();
    }

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, String fragmentId, boolean isBackStackNeeded, Bundle data){
        final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        fragment.setArguments(data);
        ft.replace(R.id.layout_container, fragment, fragmentId);
        if(isBackStackNeeded)
            ft.addToBackStack(fragmentId);
        ft.commit();
    }

    public static void removeFragment(AppCompatActivity activity, Fragment fragment){
        final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    public static JSONObject getJsonObject(Context context,Object model){
        if (gson == null) {
            gson = new Gson();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(model));
        } catch (JSONException e) {
            e.printStackTrace();
            Utility.showDialog(context,"Something went wrong.");
        }

        return jsonObject;
    }

    public static void addFragment(AppCompatActivity activity, Fragment fragment, String fragmentId, boolean isBackStackNeeded){
        final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_container, fragment, fragmentId);
        if(isBackStackNeeded)
            ft.addToBackStack(fragmentId);
        ft.commit();
    }
    public static String getUniqeIdentification(Context context){
        String IMEI = "";
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();
        Log.d("IMEI", ""+IMEI);
        if(!(TextUtils.isEmpty(IMEI))){
            return IMEI;
        }
        else {
            return getSerialNo();
        }
    }

    public static String getSerialNo(){
        String serialNo = "";
        serialNo = Build.SERIAL;
        if (!(TextUtils.isEmpty(serialNo)))
            return serialNo;
        else
            return "0000000000000000";

    }
    public static void handleDrawer (DrawerLayout mDrawerLayout) {

        if ( mDrawerLayout.isDrawerOpen ( Gravity.LEFT ) ) {
            mDrawerLayout.closeDrawer ( Gravity.LEFT );
        } else {
            mDrawerLayout.openDrawer ( Gravity.LEFT );
        }
    }
    public static void hideKeyboard (final Context context, View focusView) {

        if ( null == focusView )
            return;
        InputMethodManager im = ( InputMethodManager ) context.getSystemService ( Context.INPUT_METHOD_SERVICE );
        im.hideSoftInputFromWindow(focusView.getWindowToken(), 0);

    }


    /**
     * To hide the soft key pad if open
     */
    public static void hideSoftKeypad(Context context) {
        Activity activity = (Activity) context;
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }
   /* public static String toDOBDateString(Date date) {
        if (date == null) return "";

        try{
            return format.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/

    public static String convertDateToServerFormat(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = format.parse(date);
            SimpleDateFormat serverFormat = new SimpleDateFormat("MMM dd, yyyy");
            return serverFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

   /* public static String getRandomFlatColor(){
        String arr[] = Constants.COLOR_ARRAY.split(",");
        int rnd = new Random().nextInt(arr.length);

        return arr[rnd];
    }
*/
  /*  public static Toolbar initToolbar(AppCompatActivity activity){
        Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
        if(null != toolbar)
        activity.setSupportActionBar(toolbar);
        return toolbar;
    }
*/
//    public static void setDate(String date){
//        mDate = date;
//    }

//    public static String getDate(){
//        return mDate;
//    }

  /*  public static void showDatePicker(Activity activity, final DatePickerListener datePickerListener) {

        Utility.hideKeypad(activity);
        final Calendar cal = Calendar.getInstance();

        final Calendar dobThreshold = Calendar.getInstance();
        dobThreshold.add(Calendar.YEAR, -18);  // 18 years old

        final DatePickerDialog pickerDialog ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pickerDialog= new DatePickerDialog(activity,android.R.style.Theme_Material_Light_Dialog_Alert, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    if (calendar.getTime().getTime() > dobThreshold.getTime().getTime()){
                    }
                    else {
                        datePickerListener.onPickdate(Utility.toDOBDateString(calendar.getTime()));
                       // Utility.setDate(Utility.toDOBDateString(calendar.getTime()));

                    }

                }

            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


            pickerDialog.getDatePicker().setMaxDate(dobThreshold.getTimeInMillis());
            Calendar calendar = new GregorianCalendar(1900,0,1);
            pickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            pickerDialog.show();
        } else {

            pickerDialog = new DatePickerDialog(activity ,new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);


                    if (calendar.getTime().getTime() > dobThreshold.getTime().getTime()){
                    }
                    else {
                        datePickerListener.onPickdate(Utility.toDOBDateString(calendar.getTime()));
                    }

                }

            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            pickerDialog.getDatePicker().setMaxDate(dobThreshold.getTimeInMillis());
            Calendar calendar = new GregorianCalendar(1900,0,1);
            pickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            pickerDialog.show();
        }
    }
*/
   /* public static void selectImage(final Activity activity, boolean isRemoved){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final Resources resources = activity.getResources();
        final CharSequence[]  items;
        mSavePreferences = SavePreferences.getInstance(activity);

        if(!TextUtils.isEmpty(mSavePreferences.getImagePath())){
            builder.setTitle(resources.getString(R.string.add_photo));
            CharSequence[]  itemsChangePic = {resources.getString(R.string.view_photo), resources.getString(R.string.choose_from_gallery),
                    resources.getString(R.string.take_photo)  };
            items = itemsChangePic;
        }
        else {
            builder.setTitle(resources.getString(R.string.add_photo));
            CharSequence[]  itemsRemovePic = {resources.getString(R.string.choose_from_gallery), resources.getString(R.string.take_photo)  };
            items = itemsRemovePic;
        }


       *//* if(isRemoved){
            builder.setTitle(resources.getString(R.string.add_photo));
            CharSequence[]  itemsChangePic = {resources.getString(R.string.view_photo), resources.getString(R.string.choose_from_gallery),
                    resources.getString(R.string.take_photo)  };
            items = itemsChangePic;
        }
        else {
            builder.setTitle(resources.getString(R.string.change_photo));
            CharSequence[]  itemsRemovePic = {resources.getString(R.string.view_photo), resources.getString(R.string.remove_photo),
                    resources.getString(R.string.choose_from_gallery),
                    resources.getString(R.string.take_photo)  };
            items = itemsRemovePic;

        }*//*

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals(resources.getString(R.string.view_photo))) {
                   Utility.replaceFragment(activity,new ProfileImageViewFragment(),ProfileImageViewFragment.class.getSimpleName(),false);

                }

                else if (items[item].equals(resources.getString(R.string.take_photo))) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissionsForMarshmallow(activity);
                    }
                    else {
                        navigateAhead(activity);
                    }




                } else if (items[item].equals(resources.getString(R.string.choose_from_gallery))) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissionsForStorage(activity,resources);
                    }
                    else {

                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType(resources.getString(R.string.image));

                        activity.startActivityForResult(
                                Intent.createChooser(intent, resources.getString(R.string.select_file)), Constants.SELECT_FILE);
                    }


                } else if (items[item].equals(resources.getString(R.string.remove_photo))) {
                    Intent intent = new Intent();
                    activity.startActivityForResult(intent, Constants.REMOVE_PIC);

                } else if (items[item].equals(resources.getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });

        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                dialog.dismiss();
            }
        });
        builder.show();

    }*/
/*
    private static void requestPermissionsForStorage(final Activity activity,final Resources resources) {

        ((BaseActivity)activity).permissionCamera(new PermissionListener() {
            @Override
            public void isPermissionGranted(boolean onComplete) {
                if (onComplete) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType(resources.getString(R.string.image));

                    activity.startActivityForResult(
                            Intent.createChooser(intent, resources.getString(R.string.select_file)), Constants.SELECT_FILE);
                } else {
                    Toast.makeText(activity, "check permission in setting", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }*/

    /*private static void navigateAhead(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, Constants.REQUEST_CAMERA);

    }*/

  /*  private static void requestPermissionsForMarshmallow(final Activity activity) {
        ((BaseActivity)activity).permissionCamera(new PermissionListener() {
            @Override
            public void isPermissionGranted(boolean onComplete) {
                if (onComplete) {
                    navigateAhead(activity);
                } else {
                    Toast.makeText(activity, "check permission in setting", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
*/

    public static Bitmap pickImageFromGallery(Intent data, Activity activity){

        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(activity, selectedImageUri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(selectedImagePath, options);

    }

    public static Uri getImageUri(Activity activity, Bitmap bitmap) {

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "", null);
            return Uri.parse(path);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    public static String getRealPathFromURI(Uri uri, Activity activity) {
        try {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void getPermissions(final Activity activity, final List<String> permissionsList , final int permissioncode){

        if (permissionsList.size() > 0) {
            for(int i = 0 ; i<  permissionsList.size() ; i++) {
                int hasWriteContactsPermission = ContextCompat.checkSelfPermission(activity, permissionsList.get(i));

                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    if (!activity.shouldShowRequestPermissionRationale(permissionsList.get(i))) {

                        //showAppInfo(activity , null);

                        // showAppInfo(activity);

                        return;
                    }

                    ActivityCompat.requestPermissions(activity,
                            permissionsList.toArray(new String[permissionsList.size()]),
                            permissioncode);

                    return;
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isPermissionGranted(Context context, String permission){
        int hasWriteContactsPermission = context.checkSelfPermission(permission);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }


/*
    public static void showAppInfo(final Activity activity) {

        if(activity != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.title_appinfo_message);
            builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                    moveToSetting(activity);
                }
            });
            builder.show();
        }

    }
*/

    public static void moveToSetting(Activity activity) {

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(i);

    }

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }

    // A method to calculate total number of pages

/*
    public static void showCancelJobDialog(final Context context,final DialogButtonListener mDialogButtonClickListener){

        final Dialog dialog  = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_job);
        TextView noTv = (TextView)dialog.findViewById(R.id.tv_no);
        TextView yesTV = (TextView)dialog.findViewById(R.id.tv_yes);

        yesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogButtonClickListener.onPositiveClick(dialog);
            }
        });

        noTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogButtonClickListener.onNegativeClick(dialog);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }
*/

/*
    public static void showAcceptBidDialog(final Context context,final DialogButtonListener mDialogButtonClickListener){

        final Dialog dialog  = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_accept_bid);
        TextView rejectTv = (TextView)dialog.findViewById(R.id.tv_reject);
        TextView payTv = (TextView)dialog.findViewById(R.id.tv_pay);

        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogButtonClickListener.onPositiveClick(dialog);
            }
        });

        rejectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogButtonClickListener.onNegativeClick(dialog);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }
*/

    public void setDialogDimensions(Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
    }

    public static String getCommaSeparatedString(ArrayList<String> jobStatusArray){
        StringBuilder result = new StringBuilder();
        for(String string : jobStatusArray)
        { result.append(string); result.append(",");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";

    }

    public static void showDatePicker(Context context, final DateSelectable selectable) {
        Calendar mcurrentDate=Calendar.getInstance();
        int mYear=mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectable.onPicked(((selectedday<10)?"0"+selectedday:selectedday)+"-"+(((selectedmonth+1))<10?"0"+(selectedmonth+1):(selectedmonth+1))+"-"+selectedyear);
            }
        },mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    public static void showTimePicker(Context context, final DateSelectable selectable) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        TimePickerDialog mDatePicker = new TimePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectable.onPicked(((hourOfDay<10)?"0"+hourOfDay:hourOfDay)+":"+((minute<10)?"0"+minute:minute));
                    }
                }, hour, minute,true);

        mDatePicker.setTitle("Select Time");
        mDatePicker.show();
    }

/*
    public static String getPriceWithoutCurrencySym(@NonNull Context context, @NonNull String price){
        return price.replace(context.getString(R.string.singapore_dollar_sign), "0");
    }
*/

    public static class CurrencyFormatInputFilter implements InputFilter {

        Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");

        @Override public CharSequence filter( CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            String result = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
            Matcher matcher = mPattern.matcher(result);

            if (!matcher.matches())
                return dest.subSequence(dstart, dend);

            return null;
        }
    }

    public interface DateSelectable{
        void onPicked(String date);
    }



/*
    public static void showStatusbar(Context context){

        Window window = ((HomeContainerActivity)context).getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(context.getResources().getColor(R.color.status_bar_color));
        }

    }
*/

}
