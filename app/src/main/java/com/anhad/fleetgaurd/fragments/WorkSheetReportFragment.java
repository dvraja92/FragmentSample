package com.anhad.fleetgaurd.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.BaseActivity;
import com.anhad.fleetgaurd.activity.FilterActivity;
import com.anhad.fleetgaurd.activity.SetFilterOptionActivity;
import com.anhad.fleetgaurd.adapter.WSRAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.api.Util;
import com.anhad.fleetgaurd.dilog.UpdateWorkReportDialog;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.model.UserListingModel;
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
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 10-01-2017.
 */
public class WorkSheetReportFragment extends BaseFragment {

    private SwipeMenuListView wsrListView;
    private TextView noData;
    private UserListingModel userListing;
    private AdminWorkSheetReportModel wsrModel;
    private FloatingActionButton mFilter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.work_sheet_report_fragment, container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wsrListView = (SwipeMenuListView)view.findViewById(R.id.wsr_list);
        noData = (TextView)view.findViewById(R.id.no_data);


        getWorkSheetData();
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


        mFilter = (FloatingActionButton) view.findViewById(R.id.fab_filter);
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Gson gson = new Gson();
                String toJson = gson.toJson(wsrModel);
                SavedPreferences.getInstance().saveStringValue(toJson,"wsr_data");

                Intent intent = new Intent(getActivity(), SetFilterOptionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);



            }
        });

        addSwipeMenu();
        return view;
    }

    private void getWorkSheetData() {
        ApiClient.ApiManagement.getWSR(getActivity(), true, new ApiInterface.OnComplete() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof AdminWorkSheetReportModel && ((AdminWorkSheetReportModel)object).getWorkSheetData().size()>0) {
                    wsrModel = (AdminWorkSheetReportModel)object;

                    wsrListView.setAdapter(new WSRAdapter(getActivity(),(AdminWorkSheetReportModel)object));
                    getUserListing();


                    mFilter.setVisibility(View.VISIBLE);
                }
                else {
                    wsrListView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    mFilter.setVisibility(View.GONE);

                }
            }

            @Override
            public void onError(RetrofitError error) {
                Utility.showDialog(getActivity(),error.getMessage());
            }
        });
    }

    private void getUserListing() {
        ApiClient.ApiManagement.getAllUserListing(getActivity(), true, new ApiInterface.OnComplete() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof UserListingModel) {
                    userListing = (UserListingModel) object;
                }
            }

            @Override
            public void onError(RetrofitError error) {
                Utility.showDialog(getActivity(),error.getMessage());
            }
        });
    }

    private void addSwipeMenu(){
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                openItem.setWidth(Utility.convertDpToPixel(90,getActivity()));
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
                switch (index){
                    case 0:
                        UpdateWorkReportDialog.showDialog(getActivity(),userListing,wsrModel,position);
                        break;
                }
                return false;
            }
        });

        wsrListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        wsrListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateWorkReportDialog.showDialog(getActivity(),userListing,wsrModel,position);
                return true;
            }
        });

    }

    @Override
    public String getTitle() {
        return "Work Sheet Reports";
    }
}
