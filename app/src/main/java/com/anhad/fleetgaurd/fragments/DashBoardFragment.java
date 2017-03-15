package com.anhad.fleetgaurd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.BaseActivity;
import com.anhad.fleetgaurd.activity.MainActivity;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;

/**
 * Created by Anhad on 07-01-2017.
 */
public class DashBoardFragment extends BaseFragment {

    private TextView complaintForm;
    private TextView dsrForm;
    private TextView feedbackForm;
    private TextView expenseForm;
    private TextView wsr;
    private TextView dcr;
    private TextView assignedWsr;
    private TextView tele_calling_report;

    public static DashBoardFragment newInstance() {
        DashBoardFragment fragment = new DashBoardFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().show();

        View view = null;
        TextView tele_calling_report = null;

        System.out.println("Role : "+SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE));
        if (SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("admin")
                ||SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("employee")
                ||SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("employe")) {
            view = inflater.inflate(R.layout.dashboard_admin, container, false);
            complaintForm = (TextView) view.findViewById(R.id.complaint_form);
            dsrForm = (TextView) view.findViewById(R.id.dsr);
            feedbackForm = (TextView) view.findViewById(R.id.feedback_form);
            expenseForm = (TextView) view.findViewById(R.id.expense_form);
            assignedWsr = (TextView) view.findViewById(R.id.assigned_work_sheet_report);
            dcr = (TextView) view.findViewById(R.id.daily_call_report_second);
            wsr = (TextView) view.findViewById(R.id.work_sheet_report);
            tele_calling_report = (TextView) view.findViewById(R.id.tele_calling_report);

        } else if (SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("Technician")) {
            view = inflater.inflate(R.layout.dashboard_techinician, container, false);
            complaintForm = (TextView) view.findViewById(R.id.complaint_form);
            dsrForm = (TextView) view.findViewById(R.id.dsr);
            feedbackForm = (TextView) view.findViewById(R.id.feedback_form);
            expenseForm = (TextView) view.findViewById(R.id.expense_form);
            assignedWsr = (TextView) view.findViewById(R.id.assigned_work_sheet_report);

        } else if (SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("Sales")) {
            view = inflater.inflate(R.layout.dashboard_admin, container, false);
            complaintForm = (TextView) view.findViewById(R.id.complaint_form);
            dcr = (TextView) view.findViewById(R.id.daily_call_report_first);
            feedbackForm = (TextView) view.findViewById(R.id.feedback_form);
            expenseForm = (TextView) view.findViewById(R.id.expense_form);
            assignedWsr = (TextView) view.findViewById(R.id.assigned_work_sheet_report);
        } else if (SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("Backoffice")) {
            view = inflater.inflate(R.layout.dashboard_backoffice, container, false);
            complaintForm = (TextView) view.findViewById(R.id.complaint_form);
            feedbackForm = (TextView) view.findViewById(R.id.feedback_form);
            assignedWsr = (TextView) view.findViewById(R.id.assigned_work_sheet_report);
        } else if (SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("Telecalling")) {
            view = inflater.inflate(R.layout.dashboard_telecalling, container, false);
            tele_calling_report = (TextView) view.findViewById(R.id.tele_calling_report);
            complaintForm = (TextView) view.findViewById(R.id.complaint_form);
        }

        if (complaintForm != null)
            complaintForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new DailyWorkReportFragment(), DailyWorkReportFragment.class.getSimpleName(), true);
                }
            });

        if (tele_calling_report != null)
            tele_calling_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new TeleCallingFragment(), TeleCallingFragment.class.getSimpleName(), true);
                }
            });
        if (dsrForm != null)
            dsrForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new DailyServiceReportFragment(), DailyServiceReportFragment.class.getSimpleName(), true);
                }
            });
        if (dcr != null)
            dcr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new DailyCallReportFragment(), DailyCallReportFragment.class.getSimpleName(), true);
                }
            });
        if (feedbackForm != null)
            feedbackForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new AddFeedbackFragment(), AddFeedbackFragment.class.getSimpleName(), true);
                }
            });
        if (expenseForm != null)
            expenseForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new AddExpenseFragment(), AddExpenseFragment.class.getSimpleName(), true);
                }
            });
        if (wsr != null)
            wsr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new WorkSheetReportFragment(), WorkSheetReportFragment.class.getSimpleName(), true);
                }
            });
        if (assignedWsr != null)
            assignedWsr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.replaceFragment((BaseActivity) getActivity(), new AssignedWorkSheetReportFragment(), AssignedWorkSheetReportFragment.class.getSimpleName(), true);
                }
            });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return view;
    }

    @Override
    public String getTitle() {
        return "Dashboard - Admin";
    }
}
