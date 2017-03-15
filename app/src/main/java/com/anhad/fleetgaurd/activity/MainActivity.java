package com.anhad.fleetgaurd.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.fragments.DashBoardFragment;
import com.anhad.fleetgaurd.fragments.LoginFragment;
import com.anhad.fleetgaurd.fragments.WorkSheetReportFragment;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends BaseActivity {

    private String from = "not valid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      /*  Bundle extras = getIntent().getExtras();

        if(extras!=null){
            from = getIntent().getStringExtra("from");
        }else{
            from = "not valid";
        }
*/


        CharSequence s = "Testing ";
        int id=0;
        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            s = "error";
        }
        else
        {
            id = extras.getInt("notificationId");
        }
        NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.cancel(id);

        



        String token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(SavedPreferences.getInstance().getStringValue(SavedPreferences.ID))) {


            if(id == 111){
                Utility.replaceFragment(MainActivity.this, new WorkSheetReportFragment(), WorkSheetReportFragment.class.getSimpleName(), true);
            }else {
                Utility.addFragment(this, DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), true);
            }


        }
        else {
            Utility.addFragment(this, new LoginFragment(), LoginFragment.class.getSimpleName(),false);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.logout:
                logout();
                return true;
        }
        return false;
    }

    private void logout() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("LOGOUT")
                .content("Are you sure do you want to logout ?")
                .positiveText("CONFIRM")
                .negativeText("CANCEL")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        SavedPreferences.getInstance().clearAll();
                        Utility.clearBackStack(MainActivity.this);
                        Utility.addFragment(MainActivity.this,new LoginFragment(), LoginFragment.class.getSimpleName(),false);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
        }
        else {
            finish();
        }
    }
}
