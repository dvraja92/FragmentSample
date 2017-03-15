package com.anhad.fleetgaurd.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.adapter.WSRAdapter;
import com.anhad.fleetgaurd.fragments.LoginFragment;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.SimpleDividerItemDecoration;
import com.anhad.fleetgaurd.utility.Utility;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import java.util.Collections;

public class FilterActivity extends BaseActivity {

    private String WsrData;
    private AdminWorkSheetReportModel wsrModel;
    private RecyclerView mFilter;
    private TextView noData;
    private CheckBox mQueryType,mEmployeeName;
    private boolean isQuery = false;
    private boolean isEmployeeName = false;
    private WSRFilterAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        WsrData = SavedPreferences.getInstance().getStringValue("wsr_data");
        Gson gson = new Gson();
        wsrModel = gson.fromJson(WsrData, AdminWorkSheetReportModel.class);

        findViewById();
        mFilter = (RecyclerView) findViewById(R.id.recycler_view_filter);






        try {
            getSupportActionBar().setTitle("Filter data");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findViewById() {
        noData = (TextView)findViewById(R.id.no_data);
        mFilter = (RecyclerView)findViewById(R.id.recycler_view_filter);
        mFilter.setHasFixedSize(true);
        mFilter.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        mFilter.setLayoutManager(new LinearLayoutManager(FilterActivity.this));

        mQueryType = (CheckBox)findViewById(R.id.checkbox_query_type);
        mEmployeeName = (CheckBox)findViewById(R.id.checkbox_employee_name);

        mQueryType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked){
                    isQuery = true;
                }else{

                    isQuery = false;
                }

                getIntentData();
            }
        });

        mEmployeeName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if(isChecked){

                    isEmployeeName = true;
                }else{
                    isEmployeeName = false;
                }

                getIntentData();
            }
        });

    }

    private void getIntentData() {


         mAdapter = new WSRFilterAdapter(FilterActivity.this,wsrModel);

        mFilter.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();







    }

   /* @Override
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
    }*/

    private void logout() {
        new MaterialDialog.Builder(FilterActivity.this)
                .title("LOGOUT")
                .content("Are you sure do you want to logout ?")
                .positiveText("CONFIRM")
                .negativeText("CANCEL")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        SavedPreferences.getInstance().clearAll();
                        Utility.clearBackStack(FilterActivity.this);
                        Utility.addFragment(FilterActivity.this,new LoginFragment(), LoginFragment.class.getSimpleName(),false);
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
        super.onBackPressed();


        finish();
    }


    class WSRFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final LayoutInflater layoutInflater;
        private final AdminWorkSheetReportModel model;
        private Context mContext;

        public WSRFilterAdapter(Context context, AdminWorkSheetReportModel adminWorkSheetReportModel) {
            mContext = context;
            layoutInflater = LayoutInflater.from(context);
            model = adminWorkSheetReportModel;
            Collections.reverse(model.getWorkSheetData());
        }







        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.work_sheet_report_filter_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder = (ViewHolder)viewHolder;
            final AdminWorkSheetReportModel.WorkSheetDatum tempModel = model.getWorkSheetData().get(position);

            if (tempModel != null && tempModel.getWorkSheet() != null)
            {

                if (tempModel.getWorkSheet().getQueryType() != null) {

                    String string = new String("Query Type:\n" + tempModel.getWorkSheet().getQueryType());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);


                    Log.d("llllllllllll-","kp------"+string);


                    if(isQuery){
                        holder.tvQueryType.setText(spannableString);
                        holder.tvQueryType.setVisibility(View.VISIBLE);
                        holder.tvEmpName.setVisibility(View.GONE);

                    }else{
                        holder.tvQueryType.setVisibility(View.GONE);

                    }

                } else {
                }

            }
            if (tempModel.getAdmin() != null
                    && !TextUtils.isEmpty(tempModel.getAdmin().getFirstName())
                    && !TextUtils.isEmpty(tempModel.getAdmin().getLastName())) {

                String string = new String("Employee Name:\n" + tempModel.getCompanyName().getName());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                if(isEmployeeName){
                    holder.tvEmpName.setText(spannableString);
                    holder.tvEmpName.setVisibility(View.VISIBLE);
                    holder.tvQueryType.setVisibility(View.GONE);
                }else{
                    holder.tvEmpName.setVisibility(View.GONE);
                }


            } else {
            }



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Gson gson = new Gson();
                    String toJson = gson.toJson(tempModel);

                    Intent intent = new Intent(FilterActivity.this,DisplayFilterData.class);
                    intent.putExtra("filter_data",toJson);
                    startActivity(intent);


                }
            });


        }

/*

        @Override
        public long getItemId(int position) {
            return position;
        }
*/

        @Override
        public int getItemCount() {
            return model.getWorkSheetData().size();
        }

     /*
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AdminWorkSheetReportModel.WorkSheetDatum tempModel = model.getWorkSheetData().get(position);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.work_sheet_report_filter_item, null);
                holder = new ViewHolder();

                holder.textViewEmployeeNameQueryType = (TextView) convertView.findViewById(R.id.tv_filter_data);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (tempModel != null && tempModel.getWorkSheet() != null)
            {

                if (tempModel.getWorkSheet().getQueryType() != null) {

                    String string = new String("Query Type:\n" + tempModel.getWorkSheet().getQueryType());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);



                    if(isQuery){
                        holder.textViewEmployeeNameQueryType.setText(spannableString);
                    }else{
                    }

                } else {
                }

            }
            if (tempModel.getAdmin() != null
                    && !TextUtils.isEmpty(tempModel.getAdmin().getFirstName())
                    && !TextUtils.isEmpty(tempModel.getAdmin().getLastName())) {

                String string = new String("Employee Name:\n" + tempModel.getCompanyName().getName());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                if(isEmployeeName){
                    holder.textViewEmployeeNameQueryType.setText(spannableString);
                }else{
                }


            } else {
            }


            return convertView;


        }
*/

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvQueryType;
        public TextView tvEmpName;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvQueryType = (TextView)itemView.findViewById(R.id.tv_filter_query);
            this.tvEmpName = (TextView)itemView.findViewById(R.id.tv_filter_emp_name);
        }



    }



}
