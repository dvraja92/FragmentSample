package com.anhad.fleetgaurd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.model.AssignedWorksheetResponseModel;

import java.util.Collections;

/**
 * Created by Anhad on 10-01-2017.
 */
public class AssignedWSRAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final AssignedWorksheetResponseModel model;
    private ViewHolder holder;

    public AssignedWSRAdapter(Context context, AssignedWorksheetResponseModel adminWorkSheetReportModel) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        model = adminWorkSheetReportModel;
        Collections.reverse(model.getAssignWorkData());
    }

    @Override
    public int getCount() {
        return model.getAssignWorkData().size();
    }

    @Override
    public Object getItem(int position) {
        return model.getAssignWorkData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssignedWorksheetResponseModel.AssignWorkDatum tempModel = model.getAssignWorkData().get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.work_sheet_report_item, null);
            holder= new ViewHolder();
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
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        if (tempModel != null) {
            holder.complaintNumber.setVisibility(View.GONE);

            if (tempModel.getReportType() != null) {
                holder.reportType.setVisibility(View.VISIBLE);

                String string = new String("Report Type:\n"+tempModel.getReportType());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.reportType.setText(spannableString);
            }else {
                holder.reportType.setVisibility(View.GONE);
            }
            if (tempModel.getNextVisit() != null) {
                holder.meetingStatus.setVisibility(View.VISIBLE);

                String string = new String("Meeting Status:\n"+tempModel.getNextVisit().toString());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.meetingStatus.setText(spannableString);
            }else {
                holder.meetingStatus.setVisibility(View.GONE);
            }
            if (tempModel.getStatus() != null) {
                holder.adminStatus.setVisibility(View.VISIBLE);

                String string = new String("Admin Status:\n"+tempModel.getStatus());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.adminStatus.setText(spannableString);
            }else {
                holder.adminStatus.setVisibility(View.GONE);
            }
            if (tempModel.getEmployeStatus() != null) {
                holder.employeeStatus.setVisibility(View.VISIBLE);

                String string = new String("Employee Status:\n"+tempModel.getEmployeStatus());
                int index = string.indexOf("\n");
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

                holder.employeeStatus.setText(spannableString);
            }else {
                holder.employeeStatus.setVisibility(View.GONE);
            }

            holder.contactPerson.setVisibility(View.GONE);

            holder.phone.setVisibility(View.GONE);

            holder.queryType.setVisibility(View.GONE);

            holder.remarks.setVisibility(View.GONE);
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

            String string = new String("Company Name:\n"+tempModel.getCompanyName().getName());
            int index = string.indexOf("\n");
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

            holder.companyName.setText(spannableString);
        }
        else {
            holder.companyName.setVisibility(View.GONE);
        }
        if (tempModel.getLocation() != null && tempModel.getLocation() instanceof AssignedWorksheetResponseModel.Location && !TextUtils.isEmpty(((AssignedWorksheetResponseModel.Location)tempModel.getLocation()).getName())) {
            holder.location.setVisibility(View.VISIBLE);

            String string = new String("Location:\n"+((AssignedWorksheetResponseModel.Location)tempModel.getLocation()).getName());
            int index = string.indexOf("\n");
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, index, 0);

            holder.location.setText(spannableString);
        }
        else {
            holder.location.setVisibility(View.GONE);
        }
        return convertView;
    }

    public static class ViewHolder{
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
