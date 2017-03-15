package com.anhad.fleetgaurd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.anhad.fleetgaurd.BuildConfig;
import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.BaseActivity;
import com.anhad.fleetgaurd.activity.MainActivity;
import com.anhad.fleetgaurd.adapter.TeleCallingAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.model.TeleCallingData;
import com.anhad.fleetgaurd.model.TeleCallingModel;
import com.anhad.fleetgaurd.utility.Utility;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Anhad on 05-01-2017.
 */
public class TeleCallingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private TeleCallingAdapter adapter;

    //"/gethistorystatus"
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tele_calling_fragment, container, false);
        initViews(view);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }


    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new TeleCallingAdapter(getActivity());
        listView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        getTeleCallingReport();
                                    }
                                }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().postSticky((TeleCallingData)adapter.getItem(position));
                Utility.replaceFragment((BaseActivity) getActivity(), new TeleCallingDetailFragment(), TeleCallingDetailFragment.class.getSimpleName(),true);

            }
        });
    }


    @Override
    public void onRefresh() {
        getTeleCallingReport();
    }


    private void getTeleCallingReport() {
        swipeRefreshLayout.setRefreshing(true);

        AQuery mAQuery = new AQuery(getActivity());

        mAQuery.ajax(ApiClient.API_URL + "/gettelecallingreport",
                String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String data, AjaxStatus status) {
                        super.callback(url, data, status);
                        swipeRefreshLayout.setRefreshing(false);
                        if (BuildConfig.DEBUG) {
                            Log.d("###$Request URL", url + "");
                            Log.d("###$Response ", data + "");
                            Log.d("###$Status Message : ", status.getMessage() + "");
                            Log.d("###$Status Code : ", status.getCode() + "");
                        }
                        if (status.getCode() == -101 || null == data) {
                            Toast.makeText(getActivity(), "Your App requires active internet connection.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (null != data && status.getCode() != -101) {
                            TeleCallingModel model = TeleCallingModel.parseResponse(data);
                            if (model.isStatus() && model.getTeleCallingDataList().size()>0) {
                                adapter.clearList();
                                adapter.addObject(model.getTeleCallingDataList());
                            } else {
                                ((MainActivity) getActivity()).showSimpleDialog(getContext(), "Server Message", model.getMessage());
                            }
                        }
                    }
                });

    }

    @Override
    public String getTitle() {
        return "Telecalling Report";
    }
}
