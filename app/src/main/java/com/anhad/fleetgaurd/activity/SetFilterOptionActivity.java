package com.anhad.fleetgaurd.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetFilterOptionActivity extends AppCompatActivity {

    private RelativeLayout mQueryTypeLayout,mEmployeeNameLayout,mStatusLayout;
    private TextView mQueryTypeTextView,mEmployeeNameTextView,mStatusTextView;
    private Button mCancelBtn,mApplyBtn;

    private String WsrData;
    private AdminWorkSheetReportModel wsrModel;
    private LinearLayout mFilterLayout;



    private List<String> queryTypeList;
    private StringBuilder strQueryType = new StringBuilder();
    private StringBuffer queryTypeNames;
    private String selectedQueryTypeNames;
    private String AllselectQueryList;


    private List<String> statusList;
    private StringBuilder strStatus = new StringBuilder();
    private StringBuffer statusNames;
    private String selectedStatusNames;
    private String AllselectStatusList;


    private List<String> employeeList;
    private StringBuilder strEmployee = new StringBuilder();
    private StringBuffer employeeNames;
    private String selectedEmployeeNames;
    private String AllselectEmployeeList;


    private List<String> applyQueryTypeList;
    private List<String> applyEmployeeNameList;
    private List<String> applyStatusList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_filter_option);

        if(getSupportActionBar()!=null){

            getSupportActionBar().setTitle("  Apply Filer");
        }

        getDataFromPreviewsActivity();



        findViewById();

    }

    private void getDataFromPreviewsActivity() {
        WsrData = SavedPreferences.getInstance().getStringValue("wsr_data");
        Gson gson = new Gson();
        wsrModel = gson.fromJson(WsrData, AdminWorkSheetReportModel.class);





        //set List

        queryTypeList = new ArrayList<>();
        queryTypeList.add("Device Not Working");
        queryTypeList.add("Software Problem");
        queryTypeList.add("Account");
        queryTypeList.add("New Inquiry");
        queryTypeList.add("Software Demonstration");
        queryTypeList.add("Other");

        statusList = new ArrayList<>();
        statusList.add("completed");
        statusList.add("pending");
        statusList.add("deleted");

        employeeList = new ArrayList<>();



        new LongOperation().execute("");


    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            for (AdminWorkSheetReportModel.WorkSheetDatum sheetDatum : wsrModel.getWorkSheetData()) {

                String contactPerson = sheetDatum.getWorkSheet().getContactPerson();
                employeeList.add(""+contactPerson);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
       //     pdia.dismiss();

            Set<String> hs = new HashSet<>();
            hs.addAll(employeeList);
            employeeList.clear();
            employeeList.addAll(hs);

        }



        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void findViewById() {


        applyEmployeeNameList = new ArrayList<>();
        applyQueryTypeList = new ArrayList<>();
        applyStatusList = new ArrayList<>();
        mFilterLayout = (LinearLayout)findViewById(R.id.ll_filter_button);


        mQueryTypeLayout = (RelativeLayout)findViewById(R.id.rl_query_list);
        mEmployeeNameLayout = (RelativeLayout)findViewById(R.id.rl_employee_name);
        mStatusLayout = (RelativeLayout)findViewById(R.id.rl_status);

        mQueryTypeTextView = (TextView)findViewById(R.id.query_list);
        mEmployeeNameTextView = (TextView)findViewById(R.id.employee_name);
        mStatusTextView = (TextView)findViewById(R.id.status);

        mCancelBtn = (Button)findViewById(R.id.btn_cancel);
        mApplyBtn = (Button)findViewById(R.id.btn_apply);


        mQueryTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogQueryType();
            }
        });

        mEmployeeNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogEmployeeName();
            }
        });

        mStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogStatus();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);

            }
        });

        mApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Gson gson = new Gson();

                if(applyQueryTypeList!=null){
                    final String completeQuery = gson.toJson(applyQueryTypeList);
                    SavedPreferences.getInstance().saveStringValue(completeQuery,"complete_query");

                }

                if(applyEmployeeNameList!=null){
                    final String completeEmployeeName = gson.toJson(applyEmployeeNameList);
                    SavedPreferences.getInstance().saveStringValue(completeEmployeeName,"complete_employee_name");

                }

                if(applyStatusList!=null){
                    final String completeStatus = gson.toJson(applyStatusList);
                    SavedPreferences.getInstance().saveStringValue(completeStatus,"complete_status");

                }




                Intent intent = new Intent(SetFilterOptionActivity.this,DisplayFilterData.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);




               /* for (String s : applyQueryTypeList) {
                    
                    Log.d("kalpesh - queryType",s);
                    Log.d("kalpesh - queryTypesize",""+applyQueryTypeList.size());
                }

                for (String s : applyEmployeeNameList) {
                    Log.d("kalpesh - Employee",s);
                    Log.d("kalpesh - Employeesize",""+applyEmployeeNameList.size());
                }

                for (String s : applyStatusList) {
                    Log.d("kalpesh - status",s);
                    Log.d("kalpesh - statussize",""+applyStatusList);
                }
*/

            }
        });

    }

    private void openDialogStatus() {


        final String trim = mStatusTextView.getText().toString().trim();
        String lines[] = trim.split("\\r?\\n");
        Integer[] integer = new Integer[0];
        if (!trim.equals("Please select")) {
            integer = new Integer[lines.length];
           /* for(int i=0;i<lines.length;i++){
                integer[i] = i;
            }*/
            for(int i=0;i<lines.length;i++){

                String lineString = lines[i];

                for(int j=0;j<statusList.size();j++){

                    String listString = statusList.get(j);

                    if(lineString.equalsIgnoreCase(listString)){
                        integer[i] = j;

                    }

                }
            }
        } else {
        }


        new MaterialDialog.Builder(SetFilterOptionActivity.this)
                .title("Select Status")
                .titleColor(getResources().getColor(R.color.colorPrimary))
                .items(statusList)
                .itemsCallbackMultiChoice(integer, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        strStatus = new StringBuilder();
                        statusNames = new StringBuffer();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0)
                                strStatus.append('\n');
                            strStatus.append(text[i]);



                        }
                        return true;
                    }

                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        applyStatusList = new ArrayList<String>();
                        if(strStatus!=null && strStatus.toString().trim().length()>0) {
                            mStatusTextView.setText(strStatus.toString());


                            Log.d("kalpesh_status",strStatus.toString());

                            String qury = new String(strStatus);
                            String lines[] = qury.split("\\r?\\n");
                            for (String s : lines) {

                                applyStatusList.add(s);
                            }




                        }
                        else {
                            mStatusTextView.setText("Please select");
                            applyStatusList = new ArrayList<String>();
                        }

                        if(applyQueryTypeList.size()>0 || applyEmployeeNameList.size()>0 || applyStatusList.size()>0){

                            mFilterLayout.setVisibility(View.VISIBLE);
                        }else{
                            mFilterLayout.setVisibility(View.GONE);

                        }
                        //strStatus = new StringBuilder();
                        dialog.dismiss();
                    }
                })
                .autoDismiss(true)
                .negativeText("Cancel")
                .show();
    }

    private void openDialogEmployeeName() {



        final String trim = mEmployeeNameTextView.getText().toString().trim();
        String lines[] = trim.split("\\r?\\n");
        Integer[] integer = new Integer[0];
        if (!trim.equals("Please select")) {
            integer = new Integer[lines.length];
            /*for(int i=0;i<lines.length;i++){
                integer[i] = i;
            }*/

            for(int i=0;i<lines.length;i++){

                String lineString = lines[i];

                for(int j=0;j<employeeList.size();j++){

                    String listString = employeeList.get(j);

                    if(lineString.equalsIgnoreCase(listString)){
                        integer[i] = j;

                    }

                }
            }
        } else {
        }





        new MaterialDialog.Builder(SetFilterOptionActivity.this)
                .title("Select Employee Name")
                .titleColor(getResources().getColor(R.color.colorPrimary))
                .items(employeeList)
                .itemsCallbackMultiChoice(integer, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        strEmployee = new StringBuilder();
                       // employeeNames = new StringBuffer();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0)

                                strEmployee.append('\n');
                            strEmployee.append(text[i]);



                        }
                        return true;
                    }

                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        applyEmployeeNameList = new ArrayList<String>();
                        if(strEmployee!=null && strEmployee.toString().trim().length()>0) {
                            mEmployeeNameTextView.setText(strEmployee.toString());


                            Log.d("kalpesh_employee",strEmployee.toString());
                            String qury = new String(strEmployee);
                            String lines[] = qury.split("\\r?\\n");
                            for (String s : lines) {
                                Log.d("kalpesh_employee -- ss",s);
                                applyEmployeeNameList.add(s);
                            }


                        }
                        else {
                            mEmployeeNameTextView.setText("Please select");
                            applyEmployeeNameList = new ArrayList<String>();
                        }

                        if(applyQueryTypeList.size()>0 || applyEmployeeNameList.size()>0 || applyStatusList.size()>0){

                            mFilterLayout.setVisibility(View.VISIBLE);
                        }else{
                            mFilterLayout.setVisibility(View.GONE);

                        }

                    //    strEmployee = new StringBuilder();
                        dialog.dismiss();
                    }
                })
                .autoDismiss(true)
                .negativeText("Cancel")
                .show();

    }

    private void openDialogQueryType() {

        final String trim = mQueryTypeTextView.getText().toString().trim();

        String lines[] = trim.split("\\r?\\n");
        Integer[] integer = new Integer[0];
        if (!trim.equals("Please select")) {
            integer = new Integer[lines.length];


            for(int i=0;i<lines.length;i++){

                String lineString = lines[i];

                for(int j=0;j<queryTypeList.size();j++){

                    String listString = queryTypeList.get(j);

                    if(lineString.equalsIgnoreCase(listString)){
                        integer[i] = j;

                    }

                }
            }
        } else {



        }






        new MaterialDialog.Builder(SetFilterOptionActivity.this)
                .title("Select Query Type")
                .titleColor(getResources().getColor(R.color.colorPrimary))
                .items(queryTypeList)
                .itemsCallbackMultiChoice(integer, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        strQueryType = new StringBuilder();
                        queryTypeNames = new StringBuffer();
                        for (int i = 0; i < which.length; i++)
                        {
                            if (i > 0)
                                strQueryType.append('\n');
                                strQueryType.append(text[i]);


                        }
                        return true;
                    }

                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        applyQueryTypeList = new ArrayList<String>();

                        if(strQueryType!=null && strQueryType.toString().trim().length()>0) {
                            mQueryTypeTextView.setText(strQueryType.toString());

                            Log.d("kalpesh_Query",strQueryType.toString());

                            String qury = new String(strQueryType);
                            String lines[] = qury.split("\\r?\\n");
                            for (String s : lines) {

                                applyQueryTypeList.add(s);
                            }


                        }
                        else {
                            mQueryTypeTextView.setText("Please select");
                            applyQueryTypeList = new ArrayList<String>();
                        }

                        if(applyQueryTypeList.size()>0 || applyEmployeeNameList.size()>0 || applyStatusList.size()>0){

                            mFilterLayout.setVisibility(View.VISIBLE);
                        }else{
                            mFilterLayout.setVisibility(View.GONE);

                        }
                      //  strQueryType = new StringBuilder();
                        dialog.dismiss();
                    }
                })
                .autoDismiss(true)
                .negativeText("Cancel")
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);


    }
}
