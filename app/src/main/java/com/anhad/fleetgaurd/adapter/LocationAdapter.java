package com.anhad.fleetgaurd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;

import java.util.ArrayList;

/**
 * Created by Anhad on 07-01-2017.
 */
public class LocationAdapter extends BaseAdapter {

    private final ArrayList<GetAllLocationResponseModel.LocationDatum> info;
    private final LayoutInflater layoutInflater;

    public LocationAdapter(Context context, ArrayList<GetAllLocationResponseModel.LocationDatum> info) {
        this.info = info;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);
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
        if (null != info.get(position) && null != info.get(position).getName()) {
            holder.name.setText(info.get(position).getName());
        }
        return convertView;
    }
    public static class ViewHolder{
        TextView name;
    }
}
