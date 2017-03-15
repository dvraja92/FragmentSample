package com.anhad.fleetgaurd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;
import com.anhad.fleetgaurd.model.UserListingModel;

import java.util.ArrayList;

/**
 * Created by Anhad on 07-01-2017.
 */
public class UserAdapter extends BaseAdapter {

    private final UserListingModel info;
    private final LayoutInflater layoutInflater;

    public UserAdapter(Context context, UserListingModel info) {
        this.info = info;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return info.getUserNameList().size();
    }

    @Override
    public Object getItem(int position) {
        return info.getUserNameList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.simple_spinner_item, null);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        if (null != info.getUserNameList().get(position) && null != info.getUserNameList().get(position).getName()) {
            holder.name.setText(info.getUserNameList().get(position).getName());
        }
        return convertView;
    }
    public static class ViewHolder{
        TextView name;
    }
}
