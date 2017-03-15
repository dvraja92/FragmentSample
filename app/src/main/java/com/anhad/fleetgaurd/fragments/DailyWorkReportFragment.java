package com.anhad.fleetgaurd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.adapter.LocationAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.model.AddWorkSheetRequestModel;
import com.anhad.fleetgaurd.model.AddWorkSheetResponseModel;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 05-01-2017.
 */
public class DailyWorkReportFragment extends BaseFragment {
    private EditText usernameET;
    private EditText contactPersonET;
    private EditText contactNumberET;
    private EditText vehicleNumberET;
    private MaterialSpinner queryTypeSpinner;
    private EditText remarksET;
    private MaterialSpinner locationSpinner;
    private Button submitBtn;
    private LocationAdapter locationAdapter;
    private ArrayAdapter<CharSequence> queryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.daily_work_report_fragment, container,false);
        initViews(view);
        getAllLocation();
        setDataOnView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }

    private void getAllLocation() {
        ApiClient.ApiManagement.getAllLocations(getActivity(), true, new ApiInterface.OnComplete() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof GetAllLocationResponseModel){
                    GetAllLocationResponseModel model = (GetAllLocationResponseModel) object;
                    locationAdapter = new LocationAdapter(getActivity(),model.getLocationData());
                    locationSpinner.setAdapter(locationAdapter);
                }
            }

            @Override
            public void onError(RetrofitError error) {
                Utility.showDialog(getActivity(),error.getMessage());
            }
        });
    }

    private void setDataOnView() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()){
                    AddWorkSheetRequestModel model = new AddWorkSheetRequestModel();
                    model.setCompanyNameText(usernameET.getText().toString());
                    model.setContactPerson(contactPersonET.getText().toString());
                    model.setContactNumber(contactNumberET.getText().toString());
                    model.setMyVehicleNumber(vehicleNumberET.getText().toString());
                    if(locationSpinner.getSelectedItemPosition()>=0)
                        model.setLocationId(((GetAllLocationResponseModel.LocationDatum)locationSpinner.getItemAtPosition(locationSpinner.getSelectedItemPosition()-1)).getId());

                    if (queryTypeSpinner.getSelectedItemPosition() > 0)
                        model.setQueryType(String.valueOf(queryTypeSpinner.getItemAtPosition(queryTypeSpinner.getSelectedItemPosition() - 1)));

                    model.setReportType("customer_query");
                    model.setCompamyType("new");

                    JSONObject jsonObject = Utility.getJsonObject(getActivity(),model);

                    if (jsonObject != null){
                        ApiClient.ApiManagement.addWorkSheet(getActivity(), jsonObject,true, new ApiInterface.OnComplete() {
                            @Override
                            public void onSuccess(Object object) {
                                AddWorkSheetResponseModel responseModel = (AddWorkSheetResponseModel)object;
                                Utility.showDialog(getActivity(),responseModel.getMessage());
                                clearData();
                            }

                            @Override
                            public void onError(RetrofitError error) {
                                Utility.showDialog(getActivity(),error.getMessage());
                            }
                        });
                    }
                    else {
                        Utility.showDialog(getActivity(),"Something went wrong!");
                    }
                }
            }
        });
    }

    private void clearData() {
        usernameET.setText(null);
        contactPersonET.setText(null);
        contactNumberET.setText(null);
        vehicleNumberET.setText(null);
        remarksET.setText(null);
        locationSpinner.setAdapter(locationAdapter);
        queryTypeSpinner.setAdapter(queryAdapter);

        usernameET.requestFocus();
    }

    private void initViews(View view) {
        usernameET = (EditText)view.findViewById(R.id.input_name);
        contactPersonET = (EditText)view.findViewById(R.id.input_contact_person);
        contactNumberET = (EditText)view.findViewById(R.id.input_contact_number);
        vehicleNumberET = (EditText)view.findViewById(R.id.input_vehicle_number);
        remarksET = (EditText)view.findViewById(R.id.input_remarks);
        locationSpinner = (MaterialSpinner) view.findViewById(R.id.input_location);
        submitBtn = (Button)view.findViewById(R.id.submit);
        queryTypeSpinner = (MaterialSpinner)view.findViewById(R.id.query_type_spinner);
        queryAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.query_type, R.layout.simple_spinner_item);
        queryTypeSpinner.setAdapter(queryAdapter);
    }

    private boolean validData() {
        boolean is_valid = true;

        if(TextUtils.isEmpty(usernameET.getText().toString().trim())){
            usernameET.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(contactPersonET.getText().toString().trim())){
            contactPersonET.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(contactNumberET.getText().toString().trim())){
            contactNumberET.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(queryTypeSpinner.getSelectedItemPosition() < 0){
            queryTypeSpinner.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(locationSpinner.getSelectedItemPosition() < 0){
            locationSpinner.setError(getString(R.string.required));
            is_valid = false;
        }


        return is_valid;
    }

    @Override
    public String getTitle() {
        return "Daily Work Report";
    }
}
