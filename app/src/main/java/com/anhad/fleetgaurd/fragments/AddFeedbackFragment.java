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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.adapter.LocationAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.model.AddDSRRequestModel;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.model.AddFeedbackRequestModel;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;

import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 05-01-2017.
 */
public class AddFeedbackFragment extends BaseFragment {
    private EditText customerName;
    private EditText feedback;
    private Button submitBtn;
    private LocationAdapter locationAdapter;
    private MaterialSpinner locationSpinner;
    private EditText customerNo;
    private RadioGroup callBack;
    private RadioButton radioButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.add_feedback_fragment, container,false);
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
                    AddFeedbackRequestModel model = new AddFeedbackRequestModel();
                    model.setCustomerName(customerName.getText().toString());
                    model.setCustomerNo(customerNo.getText().toString());
                    model.setCallBack(radioButton.getText().toString());
                    model.setReview(feedback.getText().toString());

                    if(locationSpinner.getSelectedItemPosition()>0)
                        model.setLocationId(((GetAllLocationResponseModel.LocationDatum)locationSpinner.getItemAtPosition(locationSpinner.getSelectedItemPosition()-1)).getId());


                    JSONObject jsonObject = Utility.getJsonObject(getActivity(),model);

                    if (jsonObject != null){
                        ApiClient.ApiManagement.addFeedback(getActivity(), jsonObject,true, new ApiInterface.OnComplete() {
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
        customerNo.setText(null);
        feedback.setText(null);
        customerName.requestFocus();
        locationSpinner.setAdapter(locationAdapter);
    }


    private void initViews(final View view) {
        customerName = (EditText)view.findViewById(R.id.customer_name);
        customerNo = (EditText)view.findViewById(R.id.customer_contact);
        callBack = (RadioGroup)view.findViewById(R.id.radiocallback);
        feedback = (EditText)view.findViewById(R.id.feedback);
        submitBtn = (Button)view.findViewById(R.id.submit);
        locationSpinner = (MaterialSpinner) view.findViewById(R.id.input_location);

        callBack.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                radioButton=(RadioButton) view.findViewById(checkedId);
            }
        });

    }
    private boolean validData() {
        boolean is_valid = true;
        int selectedId = callBack.getCheckedRadioButtonId();
        radioButton = (RadioButton) getActivity().findViewById(selectedId);
        if(TextUtils.isEmpty(customerName.getText().toString().trim())){
            customerName.setError(getString(R.string.required));
            is_valid = false;
        }
        else if(TextUtils.isEmpty(customerNo.getText().toString().trim())){
            customerNo.setError(getString(R.string.required));
            is_valid = false;
        }
        else if(locationSpinner.getSelectedItemPosition() == 0){
            locationSpinner.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(feedback.getText().toString().trim())){
            feedback.setError(getString(R.string.required));
            is_valid = false;
        }
        return is_valid;
    }

    @Override
    public String getTitle() {
        return "Add Feedback";
    }
}
