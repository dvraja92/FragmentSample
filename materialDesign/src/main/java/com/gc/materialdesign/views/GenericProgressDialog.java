package com.gc.materialdesign.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

/**
 * Created by shristi on 25/1/16.
 */
public class GenericProgressDialog extends ProgressDialog{
    public GenericProgressDialog(Context context) {
        super(context);
    }

    public GenericProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setProgressVisibility(int i){
        if(i == View.VISIBLE){
            show();
        }else if(i == View.INVISIBLE){
            dismiss();
        }else if(i == View.GONE){
            dismiss();
        }
    }

}
