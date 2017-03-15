package com.anhad.fleetgaurd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.TeleCallInnerModel.DeviceSuggestion;
import com.anhad.fleetgaurd.model.TeleCallingData;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Anhad on 05-01-2017.
 */
public class TeleCallingDetailFragment extends BaseFragment{

    private TeleCallingData data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = EventBus.getDefault().getStickyEvent(TeleCallingData.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.telecalling_detail, container, false);
        initViews(view);
        System.out.println(data.getTeleCall().getCompany_name());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }


    private void initViews(View view) {


        TextView employeeName = (TextView)view.findViewById(R.id.employeeName);
        TextView companyName = (TextView)view.findViewById(R.id.companyName);
        TextView companyCateogry = (TextView)view.findViewById(R.id.companyCategory);
        TextView contactPerson = (TextView)view.findViewById(R.id.contactPersonName);
        TextView contactNumber = (TextView)view.findViewById(R.id.contactNumber);
        TextView callingTime = (TextView)view.findViewById(R.id.callingTime);
        TextView callingDate = (TextView)view.findViewById(R.id.callingDate);
        TextView callingRemarks = (TextView)view.findViewById(R.id.callingRemarks );
        TextView nextCallingDate = (TextView)view.findViewById(R.id.nextCallingDate );
        TextView location = (TextView)view.findViewById(R.id.location );
        LinearLayout inflateLayout = (LinearLayout)view.findViewById(R.id.inflateLayout );
        employeeName.setText(data.getAdmin().getFirst_name()+" "+data.getAdmin().getLast_name());
        companyName.setText(data.getTeleCall().getCompany_name());
        companyCateogry.setText(data.getTeleCall().getCompany_category());
        contactNumber.setText(data.getTeleCall().getContact_number());
        contactPerson.setText(data.getTeleCall().getContact_person());
        callingTime.setText(data.getTeleCall().getCalling_time());
        callingDate.setText(data.getTeleCall().getCalling_date());
        callingRemarks.setText(data.getTeleCall().getRemarks());
        nextCallingDate.setText(data.getTeleCall().getNext_calling_date());
        location.setText(data.getLocation().getName());
        inflateLayout.removeAllViews();
        try {
            int i=1;
            for(DeviceSuggestion ob: data.getDeviceSuggestionList()){
                View v = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.device_suggestion_layout, null, false);

                TextView sNo = (TextView)v.findViewById(R.id.deviceSNo);
                TextView deviceName = (TextView)v.findViewById(R.id.deviceName);
                TextView devicePrice = (TextView)v.findViewById(R.id.devicePrice);
                sNo.setText(String.valueOf(i));
                i++;
                deviceName.setText(ob.getDevice_name());
                devicePrice.setText(ob.getDevice_price());
                inflateLayout.addView(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getTitle() {
        return "Telecalling Detail";
    }
}
