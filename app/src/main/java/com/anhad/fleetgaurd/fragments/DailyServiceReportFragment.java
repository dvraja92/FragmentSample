package com.anhad.fleetgaurd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.adapter.LocationAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.api.Util;
import com.anhad.fleetgaurd.model.AddDSRRequestModel;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.model.AddWorkSheetRequestModel;
import com.anhad.fleetgaurd.model.AddWorkSheetResponseModel;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;

import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 05-01-2017.
 */
public class DailyServiceReportFragment extends BaseFragment {
    private EditText customerName;
    private EditText location;
    private EditText deviceType;
    private EditText imei;
    private EditText vehicleNo;
    private MaterialSpinner serviceTypeSpinner;
    private Button submitBtn;
    private ArrayAdapter<CharSequence> serviceAdapter;
    private LinearLayout replacementLayout;
    private EditText partyName;
    private EditText vehicleNoReplacement;
    private EditText oldModel;
    private EditText newModel;
    private EditText oldDeviceImei;
    private EditText newDeviceImei;
    private EditText amount;
    private EditText specialRemark;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.daily_service_report_fragment, container,false);
        initViews(view);
        setDataOnView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }


    private void setDataOnView() {
        serviceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2){
                    replacementLayout.setVisibility(View.VISIBLE);
                }
                else {
                    replacementLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                replacementLayout.setVisibility(View.GONE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()){
                    AddDSRRequestModel model = new AddDSRRequestModel();
                    model.setEmpId(SavedPreferences.getInstance().getStringValue(SavedPreferences.ID));
                    model.setReportDate(Utility.getCurrentDate());
                    model.setCustomerName(customerName.getText().toString());
                    model.setLocation(location.getText().toString());
                    model.setDeviceType(deviceType.getText().toString());
                    model.setImeiNo(imei.getText().toString());
                    model.setOldVehicleNo(vehicleNo.getText().toString());
                    if (serviceTypeSpinner.getSelectedItemPosition() > 0)
                        model.setServiceType(String.valueOf(serviceTypeSpinner.getItemAtPosition(serviceTypeSpinner.getSelectedItemPosition() - 1)));

                  //  model.setServiceType(String.valueOf(serviceTypeSpinner.getSelectedItemPosition()));
                    if (serviceTypeSpinner.getSelectedItemPosition() == 3){
                        model.setPartyName(partyName.getText().toString());
                        model.setNewVehicleNo(vehicleNoReplacement.getText().toString());
                        model.setOldModel(oldModel.getText().toString());
                        model.setNewModel(newModel.getText().toString());
                        model.setOldDeviceImeiNo(oldDeviceImei.getText().toString());
                        model.setNewDeviceImeiNo(newDeviceImei.getText().toString());
                    }else{
                        model.setPartyName("");
                        model.setNewVehicleNo("");
                        model.setOldModel("");
                        model.setNewModel("");
                        model.setOldDeviceImeiNo("");
                        model.setNewDeviceImeiNo("");
                    }
                    model.setAmountRecive(amount.getText().toString());
                    model.setSpecialRemark(specialRemark.getText().toString());
                    model.setStatus("1");

                    JSONObject jsonObject = Utility.getJsonObject(getActivity(),model);

                    if (jsonObject != null){
                        ApiClient.ApiManagement.addDSR(getActivity(), jsonObject,true, new ApiInterface.OnComplete() {
                            @Override
                            public void onSuccess(Object object) {
                                AddDSRResponseModel responseModel = (AddDSRResponseModel)object;
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
        customerName.setText(null);
        location.setText(null);
        deviceType.setText(null);
        imei.setText(null);
        vehicleNo.setText(null);
        partyName.setText(null);
        vehicleNoReplacement.setText(null);
        oldModel.setText(null);
        newModel.setText(null);
        oldDeviceImei.setText(null);
        newDeviceImei.setText(null);
        amount.setText(null);
        specialRemark.setText(null);
        serviceTypeSpinner.setAdapter(serviceAdapter);

        customerName.requestFocus();
    }

    private void initViews(View view) {
        customerName = (EditText)view.findViewById(R.id.customer_name);
        location = (EditText)view.findViewById(R.id.location);
        deviceType = (EditText)view.findViewById(R.id.device_type);
        imei = (EditText)view.findViewById(R.id.imei);
        vehicleNo = (EditText)view.findViewById(R.id.vehicle_no);
        partyName = (EditText)view.findViewById(R.id.party_name);
        vehicleNoReplacement = (EditText)view.findViewById(R.id.vehicle_no_replacement);
        oldModel = (EditText)view.findViewById(R.id.old_model);
        newModel = (EditText)view.findViewById(R.id.new_model);
        oldDeviceImei = (EditText)view.findViewById(R.id.old_device_imei);
        newDeviceImei = (EditText)view.findViewById(R.id.new_device_imei);
        amount = (EditText)view.findViewById(R.id.amount);
        specialRemark = (EditText)view.findViewById(R.id.special_remark);

        submitBtn = (Button)view.findViewById(R.id.submit);
        replacementLayout = (LinearLayout)view.findViewById(R.id.replacement_layout);
        serviceTypeSpinner = (MaterialSpinner)view.findViewById(R.id.service_type);
        serviceAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.service_type, R.layout.simple_spinner_item);
        serviceTypeSpinner.setAdapter(serviceAdapter);
    }

    private boolean validData() {
        boolean is_valid = true;

        if(TextUtils.isEmpty(customerName.getText().toString().trim())){
            customerName.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(location.getText().toString().trim())){
            location.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(deviceType.getText().toString().trim())){
            deviceType.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(imei.getText().toString().trim())){
            imei.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(vehicleNo.getText().toString().trim())){
            vehicleNo.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(serviceTypeSpinner.getSelectedItemPosition() == 0){
            serviceTypeSpinner.setError(getString(R.string.required));
            is_valid = false;
        }

        else if (serviceTypeSpinner.getSelectedItemPosition() == 3) {
            if (TextUtils.isEmpty(partyName.getText().toString().trim())) {
                partyName.setError(getString(R.string.required));
                is_valid = false;
            }
            else if(TextUtils.isEmpty(vehicleNoReplacement.getText().toString().trim())){
                vehicleNoReplacement.setError(getString(R.string.required));
                is_valid = false;
            }
            else if(TextUtils.isEmpty(oldModel.getText().toString().trim())){
                oldModel.setError(getString(R.string.required));
                is_valid = false;
            }
            else if(TextUtils.isEmpty(newModel.getText().toString().trim())){
                newModel.setError(getString(R.string.required));
                is_valid = false;
            }
            else if(TextUtils.isEmpty(oldDeviceImei.getText().toString().trim())){
                oldDeviceImei.setError(getString(R.string.required));
                is_valid = false;
            }
            else if(TextUtils.isEmpty(newDeviceImei.getText().toString().trim())){
                newDeviceImei.setError(getString(R.string.required));
                is_valid = false;
            }
            else if(TextUtils.isEmpty(amount.getText().toString().trim())){
                newDeviceImei.setError(getString(R.string.required));
                is_valid = false;
            }

            else if(TextUtils.isEmpty(specialRemark.getText().toString().trim())){
                specialRemark.setError(getString(R.string.required));
                is_valid = false;
            }
        }

        else if(TextUtils.isEmpty(amount.getText().toString().trim())){
            newDeviceImei.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(specialRemark.getText().toString().trim())){
            specialRemark.setError(getString(R.string.required));
            is_valid = false;
        }
        return is_valid;
    }

    @Override
    public String getTitle() {
        return "Daily Service Report";
    }
}
