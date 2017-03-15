package com.anhad.fleetgaurd.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.adapter.WSRAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.dilog.UpdateWorkReportDialog;
import com.anhad.fleetgaurd.fragments.LoginFragment;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.model.UserListingModel;
import com.anhad.fleetgaurd.model.WSRFilterRequestModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit.RetrofitError;

public class DisplayFilterData extends AppCompatActivity {


    private List<String> mFilterQueryList;
    private List<String> mFilterEmployeeNameList;
    private List<String> mFilterStatusList;


    private SwipeMenuListView wsrListView;
    private TextView noData;
    private UserListingModel userListing;
    private AdminWorkSheetReportModel wsrModel;
    private WSRFilterRequestModel wsrFilterRequestModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_filter_data);
        wsrFilterRequestModel = new WSRFilterRequestModel();

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("  Filtered Data");
        }

        getIntentData();
       findViewById();


    }

    private void getIntentData() {

        String query = SavedPreferences.getInstance().getStringValue("complete_query");
        String employeeName = SavedPreferences.getInstance().getStringValue("complete_employee_name");
        String status = SavedPreferences.getInstance().getStringValue("complete_status");

        Gson gsonQuery = new Gson();
        Type typeQuery = new TypeToken<List<String>>() {}.getType();
        mFilterQueryList = gsonQuery.fromJson(query, typeQuery);

        Gson gsonEmployeeName = new Gson();
        Type typeEmployeeName = new TypeToken<List<String>>() {}.getType();
        mFilterEmployeeNameList = gsonEmployeeName.fromJson(employeeName, typeEmployeeName);

        Gson gsonStatus = new Gson();
        Type typeStatus = new TypeToken<List<String>>() {}.getType();
        mFilterStatusList = gsonStatus.fromJson(status, typeStatus);


        Log.d("display_query",mFilterQueryList.toString());
        Log.d("display_employee",mFilterEmployeeNameList.toString());
        Log.d("display_status",mFilterStatusList.toString());


        if(mFilterQueryList!=null){

            List<WSRFilterRequestModel.QueryTypeModel> queryTypeModelList = new ArrayList<>();
            for (String queryTypeName : mFilterQueryList) {
                WSRFilterRequestModel.QueryTypeModel queryTypeModel = new WSRFilterRequestModel.QueryTypeModel();
                queryTypeModel.setType(queryTypeName);

                queryTypeModelList.add(queryTypeModel);

            }

            wsrFilterRequestModel.setQuery_type(queryTypeModelList);
        }

        if(mFilterEmployeeNameList!=null){

            List<WSRFilterRequestModel.EmployeeNameModel> employeeModelList = new ArrayList<>();

            for (String employeeTypeName : mFilterEmployeeNameList) {

                WSRFilterRequestModel.EmployeeNameModel employeeNameModel = new WSRFilterRequestModel.EmployeeNameModel();
                employeeNameModel.setName(employeeTypeName);
                employeeModelList.add(employeeNameModel);

            }
            wsrFilterRequestModel.setEmployee_name(employeeModelList);
        }
        if(mFilterStatusList!=null){

            List<WSRFilterRequestModel.StatusModel> statusModelList = new ArrayList<>();

            for (String statusTypeName : mFilterStatusList) {

                WSRFilterRequestModel.StatusModel statusModel = new WSRFilterRequestModel.StatusModel();
                statusModel.setSheet_status(statusTypeName);
                statusModelList.add(statusModel);
            }

            wsrFilterRequestModel.setStatus(statusModelList);

        }

        Gson gson = new Gson();
        final String s = gson.toJson(wsrFilterRequestModel);
        Log.d("model_list",s);


    }

    private void findViewById() {

        wsrListView = (SwipeMenuListView) findViewById(R.id.wsr_list_filter);
        noData = (TextView) findViewById(R.id.no_data_filter);


        getWorkSheetDataFilter();
        wsrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout detailsLayout = (LinearLayout) view.findViewById(R.id.details_layout);
                if (detailsLayout != null) {
                    if (detailsLayout.getVisibility() == View.GONE) {
                        detailsLayout.setVisibility(View.VISIBLE);
                    } else {
                        detailsLayout.setVisibility(View.GONE);
                    }
                }
            }
        });


        wsrListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateWorkReportDialog.showDialog(DisplayFilterData.this, userListing, wsrModel, position);
                return true;
            }
        });

       addSwipeMenu();
    }


    private void getWorkSheetDataFilter() {
        ApiClient.ApiManagement.doFiltration(DisplayFilterData.this,Utility.getJsonObject(DisplayFilterData.this, wsrFilterRequestModel) ,true, new ApiInterface.OnComplete() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof AdminWorkSheetReportModel && ((AdminWorkSheetReportModel) object).getWorkSheetData().size() > 0) {
                    wsrModel = (AdminWorkSheetReportModel) object;

                    final WSRFilterAdapter wsrFilterAdapter = new WSRFilterAdapter(DisplayFilterData.this, (AdminWorkSheetReportModel) object);
                    wsrListView.setAdapter(wsrFilterAdapter);
                    wsrFilterAdapter.notifyDataSetChanged();

                    getUserListing();



                } else {
                    wsrListView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onError(RetrofitError error) {
                Utility.showDialog(DisplayFilterData.this, error.getMessage());
            }
        });
    }

    private void getUserListing() {
        ApiClient.ApiManagement.getAllUserListing(DisplayFilterData.this, true, new ApiInterface.OnComplete() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof UserListingModel) {
                    userListing = (UserListingModel) object;
                }
            }

            @Override
            public void onError(RetrofitError error) {
                Utility.showDialog(DisplayFilterData.this, error.getMessage());
            }
        });
    }

    private void addSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        DisplayFilterData.this);
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                openItem.setWidth(Utility.convertDpToPixel(90, DisplayFilterData.this));
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }

        };



// set creator
        wsrListView.setMenuCreator(creator);




        wsrListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        UpdateWorkReportDialog.showDialog(DisplayFilterData.this, userListing, wsrModel, position);
                        break;
                }
                return false;
            }
        });

        wsrListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);

    }



    public class WSRFilterAdapter extends BaseAdapter {
        private final Context mContext;
        private final LayoutInflater layoutInflater;
        private final AdminWorkSheetReportModel model;
        private ViewHolder holder;

        public WSRFilterAdapter(Context context, AdminWorkSheetReportModel adminWorkSheetReportModel) {
            mContext = context;
            layoutInflater = LayoutInflater.from(context);
            model = adminWorkSheetReportModel;
            Collections.reverse(model.getWorkSheetData());

        }

        @Override
        public Object getItem(int position) {
            return model.getWorkSheetData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {

            int count =  model.getWorkSheetData().size();
            return count;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AdminWorkSheetReportModel.WorkSheetDatum tempModel = model.getWorkSheetData().get(position);

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.work_sheet_report_item, null);
                holder = new ViewHolder();
                holder.layoutWorkSpace = (LinearLayout) convertView.findViewById(R.id.ll_wsr);
                holder.mainLayoutWorkSpace = (LinearLayout) convertView.findViewById(R.id.ll_main_layout);

                holder.complaintNumber = (TextView) convertView.findViewById(R.id.complaint_number);
                holder.companyName = (TextView) convertView.findViewById(R.id.company_name);
                holder.reportType = (TextView) convertView.findViewById(R.id.report_type);
                holder.meetingStatus = (TextView) convertView.findViewById(R.id.meeting_status);
                holder.adminStatus = (TextView) convertView.findViewById(R.id.admin_status);
                holder.employeeStatus = (TextView) convertView.findViewById(R.id.employee_status);
                holder.employeeName = (TextView) convertView.findViewById(R.id.employee_name);
                holder.contactPerson = (TextView) convertView.findViewById(R.id.contact_person);
                holder.phone = (TextView) convertView.findViewById(R.id.phone);
                holder.queryType = (TextView) convertView.findViewById(R.id.query_type);
                holder.remarks = (TextView) convertView.findViewById(R.id.remarks);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.detailsLayout = (LinearLayout) convertView.findViewById(R.id.details_layout);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (tempModel != null && tempModel.getWorkSheet() != null) {
                if (tempModel.getWorkSheet().getComplainNumber() != null) {
                    holder.complaintNumber.setVisibility(View.VISIBLE);

                    String string = new String("Complain Number:\n" + tempModel.getWorkSheet().getComplainNumber());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.complaintNumber.setText(spannableString);
                } else {
                    holder.complaintNumber.setVisibility(View.GONE);
                }


                if (tempModel.getWorkSheet().getStatus().equals("pending") && tempModel.getWorkSheet().getEmployeStatus().equals("open")) {

                    holder.layoutWorkSpace.setBackgroundColor(Color.parseColor("#F1CCC4"));
                } else if (tempModel.getWorkSheet().getStatus().equals("pending") && tempModel.getWorkSheet().getEmployeStatus().equals("closed")) {
                    holder.layoutWorkSpace.setBackgroundColor(Color.parseColor("#F4F3C7"));

                } else if (tempModel.getWorkSheet().getStatus().equals("closed") && tempModel.getWorkSheet().getEmployeStatus().equals("closed")) {
                    holder.layoutWorkSpace.setBackgroundColor(Color.parseColor("#D9E9CC"));

                } else {
                    holder.layoutWorkSpace.setBackgroundColor(Color.parseColor("#ffffff"));
                }


                if (tempModel.getWorkSheet().getReportType() != null) {
                    holder.reportType.setVisibility(View.VISIBLE);

                    String string = new String("Report Type:\n" + tempModel.getWorkSheet().getReportType());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.reportType.setText(spannableString);
                } else {
                    holder.reportType.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getNextVisit() != null) {
                    holder.meetingStatus.setVisibility(View.VISIBLE);

                    String string = new String("Meeting Status:\n" + tempModel.getWorkSheet().getNextVisit().toString());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.meetingStatus.setText(spannableString);
                } else {
                    holder.meetingStatus.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getStatus() != null) {
                    holder.adminStatus.setVisibility(View.VISIBLE);

                    String string = new String("Admin Status:\n" + tempModel.getWorkSheet().getStatus());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.adminStatus.setText(spannableString);
                } else {
                    holder.adminStatus.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getEmployeStatus() != null) {
                    holder.employeeStatus.setVisibility(View.VISIBLE);

                    String string = new String("Employee Status:\n" + tempModel.getWorkSheet().getEmployeStatus());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.employeeStatus.setText(spannableString);
                } else {
                    holder.employeeStatus.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getContactPerson() != null) {
                    holder.contactPerson.setVisibility(View.VISIBLE);

                    String string = new String("Contact Person:\n" + tempModel.getWorkSheet().getContactPerson());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.contactPerson.setText(spannableString);
                } else {
                    holder.contactPerson.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getContactNumber() != null) {
                    holder.phone.setVisibility(View.VISIBLE);

                    String string = new String("Phone No:\n" + tempModel.getWorkSheet().getContactNumber());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.phone.setText(spannableString);
                } else {
                    holder.phone.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getQueryType() != null) {
                    holder.queryType.setVisibility(View.VISIBLE);

                    String string = new String("Query Type:\n" + tempModel.getWorkSheet().getQueryType());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                    holder.queryType.setText(spannableString);
                } else {
                    holder.queryType.setVisibility(View.GONE);
                }
                if (tempModel.getWorkSheet().getRemarks() != null) {
                    holder.remarks.setVisibility(View.VISIBLE);

                    String string = new String("Remarks:\n" + tempModel.getWorkSheet().getRemarks());
                    int index = string.indexOf("\n");
                    SpannableString spannableString = new SpannableString(string);
                    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);


                    holder.remarks.setText(spannableString);
                } else {
                    holder.remarks.setVisibility(View.GONE);
                }
            }
            if (tempModel.getAdmin() != null
                    && !TextUtils.isEmpty(tempModel.getAdmin().getFirstName())
                    && !TextUtils.isEmpty(tempModel.getAdmin().getLastName())) {
                holder.employeeName.setVisibility(View.VISIBLE);

                String string = new String("Employee Name:\n"+tempModel.getAdmin().getFirstName()+" "+tempModel.getAdmin().getLastName());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.employeeName.setText(spannableString);
            }else {
                holder.employeeName.setVisibility(View.GONE);
            }
            if (tempModel.getCompanyName() != null && !TextUtils.isEmpty(tempModel.getCompanyName().getName())) {
                holder.companyName.setVisibility(View.VISIBLE);

                String string = new String("User Name:\n"+tempModel.getCompanyName().getName());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.companyName.setText(spannableString);
            }
            if (tempModel.getLocation() != null && !TextUtils.isEmpty(tempModel.getLocation().getName())) {
                holder.location.setVisibility(View.VISIBLE);

                String string = new String("Location:\n" + tempModel.getLocation().getName());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.location.setText(spannableString);
            } else {
                holder.location.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    public static class ViewHolder {
        public LinearLayout layoutWorkSpace;
        public LinearLayout mainLayoutWorkSpace;
        public TextView complaintNumber;
        public TextView companyName;
        public TextView reportType;
        public TextView meetingStatus;
        public TextView adminStatus;
        public TextView employeeStatus;
        public TextView employeeName;
        public TextView contactPerson;
        public TextView phone;
        public TextView queryType;
        public TextView remarks;
        public TextView location;
        public LinearLayout detailsLayout;
    }
}
