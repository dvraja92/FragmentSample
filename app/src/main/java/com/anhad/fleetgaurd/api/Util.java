package com.anhad.fleetgaurd.api;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.anhad.fleetgaurd.R;


/**
 * Created by ranosys on 15/2/16.
 */
public class Util {
    private static ProgressDialog dialog;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void showDialog(Context context, String str)
    {
        if(context != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(str);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().dismiss();
                }
            });
            builder.show();
        }
    }

    public static void showToast(Context context, int id)
    {
        if(context != null)
            Toast.makeText(context,id,Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String str)
    {
        if(context != null)
            Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, int id)
    {
        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
    }
    public static ProgressDialog getProgressDialog(Context context,String message)
    {
        if (null == dialog) {
            dialog = new ProgressDialog(context);
        }
        else {
            dialog.dismiss();
            dialog = new ProgressDialog(context);
        }
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        return dialog;
    }
}
