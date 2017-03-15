package com.anhad.fleetgaurd.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Anhad on 05-01-2017.
 */
public class BaseActivity extends AppCompatActivity {

    public void showSimpleDialog(Context mContext, String message){
        new MaterialDialog.Builder(mContext)
                .content(message)
                .positiveText(mContext.getString(android.R.string.ok))
                .show();
    }
    public void showSimpleDialog(Context mContext, String title, String message){
        new MaterialDialog.Builder(mContext)
                .title(title)
                .content(message)
                .positiveText(mContext.getString(android.R.string.ok))
                .show();
    }

}
