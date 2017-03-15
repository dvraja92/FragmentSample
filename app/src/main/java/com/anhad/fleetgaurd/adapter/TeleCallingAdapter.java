package com.anhad.fleetgaurd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.TeleCallingData;
import com.anhad.fleetgaurd.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL  374 on 2/16/2016.
 */
public class TeleCallingAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    public List<TeleCallingData> mList = new ArrayList<>();

    public TeleCallingAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public TeleCallingAdapter(Context context, List<TeleCallingData> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.telecalling_list_item, null);
            holder = new ViewHolder();
            holder.contactPersonName = (TextView) convertView.findViewById(R.id.contactPersonName);
            holder.contactNumber = (TextView) convertView.findViewById(R.id.contactNumber);
            holder.employeeName = (TextView) convertView.findViewById(R.id.employeeName);
            holder.callingStatus = (TextView) convertView.findViewById(R.id.callingStatus);
            holder.adminStatus = (TextView) convertView.findViewById(R.id.adminStatus);
            holder.createdDate = (TextView) convertView.findViewById(R.id.createdDate);
            holder.employeeStatus = (TextView) convertView.findViewById(R.id.employeeStatus);
            holder.sNo = (TextView) convertView.findViewById(R.id.sNo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TeleCallingData model = mList.get(position);

        holder.sNo.setText("S.No#"+(position+1));
        try {
            holder.contactPersonName.setText(model.getTeleCall().getContact_person());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.contactNumber.setText(model.getTeleCall().getContact_number());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.employeeName.setText(model.getAdmin().getFirst_name()+" "+model.getAdmin().getFirst_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.callingStatus.setText(model.getTeleCall().getNext_visit());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.adminStatus.setText(model.getTeleCall().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.employeeStatus.setText(model.getTeleCall().getEmploye_status());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.createdDate.setText(Utility.convertDateToServerFormat(model.getTeleCall().getCreated()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView contactPersonName;
        TextView contactNumber;
        TextView employeeName;
        TextView callingStatus;
        TextView adminStatus;
        TextView employeeStatus;
        TextView sNo;
        TextView createdDate;
    }

    public void clearList() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void addObject(TeleCallingData teleCallingData) {
        mList.add(teleCallingData);
        notifyDataSetChanged();
    }

    public void addObject(List<TeleCallingData> teleCallingDataList) {
        this.mList.addAll(teleCallingDataList);
        notifyDataSetChanged();
    }
}
