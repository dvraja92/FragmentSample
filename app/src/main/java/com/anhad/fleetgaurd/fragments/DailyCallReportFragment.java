package com.anhad.fleetgaurd.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.anhad.fleetgaurd.BuildConfig;
import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.MainActivity;
import com.anhad.fleetgaurd.adapter.LocationAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.model.AddWorkSheetRequestModel;
import com.anhad.fleetgaurd.model.AddWorkSheetResponseModel;
import com.anhad.fleetgaurd.model.CompanyData;
import com.anhad.fleetgaurd.model.CompanyNameModel;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 05-01-2017.
 */
public class DailyCallReportFragment extends BaseFragment {

    private Button submitBtn;

    private RadioGroup customerTypeRG;
    private LinearLayout customerTypeLL;
    private EditText referenceNameET;
    private EditText companyNameET;
    private MaterialSpinner companyNameSpinner;
    private MaterialSpinner companyCategorySpinner;
    private EditText contactPersonET;
    private EditText contactNumberET;
    private RadioGroup meetingTypeRG;
    private LinearLayout meetingTypeLL;
    private EditText meetingTimeET;
    private EditText meetingDateET;
    private EditText previousMeetingDateET;
    private EditText meetingReasonET;
    private EditText meetingOutcomeET;
    private MaterialSpinner deviceSuggestionSpinner;
    private EditText priceGivenET;
    private RadioGroup meetingStatusRG;
    private LinearLayout meetingStatusLL;
    private EditText nextMeetingDateET;

    private ArrayAdapter<CharSequence> companyCategoryAdapter;
    private ArrayAdapter<CharSequence> deviceSuggestionAdapter;
    private ArrayAdapter<String> companyNameAdapter;

    private String customerType = "Old";
    private String meetingType = "Telephonic";
    private String meetingStatus = "Open";

    private void invisibleAll() {
        customerTypeRG.setVisibility(View.GONE);
        customerTypeLL.setVisibility(View.GONE);
        referenceNameET.setVisibility(View.GONE);
        companyNameET.setVisibility(View.GONE);
        companyNameSpinner.setVisibility(View.GONE);
        companyCategorySpinner.setVisibility(View.GONE);
        contactPersonET.setVisibility(View.GONE);
        contactNumberET.setVisibility(View.GONE);
        meetingTypeRG.setVisibility(View.GONE);
        meetingTypeLL.setVisibility(View.GONE);
        meetingTimeET.setVisibility(View.GONE);
        meetingDateET.setVisibility(View.GONE);
        previousMeetingDateET.setVisibility(View.GONE);
        meetingReasonET.setVisibility(View.GONE);
        meetingOutcomeET.setVisibility(View.GONE);
        deviceSuggestionSpinner.setVisibility(View.GONE);
        priceGivenET.setVisibility(View.GONE);
        meetingStatusRG.setVisibility(View.GONE);
        meetingStatusLL.setVisibility(View.GONE);
        nextMeetingDateET.setVisibility(View.GONE);

        referenceNameET.setText(null);
        companyNameET.setText(null);
        contactPersonET.setText(null);
        contactNumberET.setText(null);
        meetingTimeET.setText(null);
        meetingDateET.setText(null);
        previousMeetingDateET.setText(null);
        meetingReasonET.setText(null);
        meetingOutcomeET.setText(null);
        priceGivenET.setText(null);
        nextMeetingDateET.setText(null);

        companyCategorySpinner.setAdapter(companyCategoryAdapter);
        deviceSuggestionSpinner.setAdapter(deviceSuggestionAdapter);
        companyNameSpinner.setAdapter(companyNameAdapter);
    }

    private void initView(View view) {
        customerTypeRG = (RadioGroup) view.findViewById(R.id.customerTypeRG);
        meetingStatusRG = (RadioGroup) view.findViewById(R.id.meetingStatusRG);
        customerTypeLL = (LinearLayout) view.findViewById(R.id.customerTypeLL);
        meetingStatusLL = (LinearLayout) view.findViewById(R.id.meetingStatusLL);
        companyNameET = (EditText) view.findViewById(R.id.companyNameET);
        referenceNameET = (EditText) view.findViewById(R.id.referenceName);
        nextMeetingDateET = (EditText) view.findViewById(R.id.nextMeetingDate);
        companyNameSpinner = (MaterialSpinner) view.findViewById(R.id.company_name_spinner);
        companyCategorySpinner = (MaterialSpinner) view.findViewById(R.id.company_category_spinner);
        deviceSuggestionSpinner = (MaterialSpinner) view.findViewById(R.id.device_spinner);
        contactPersonET = (EditText) view.findViewById(R.id.input_contact_person);
        contactNumberET = (EditText) view.findViewById(R.id.input_contact_number);
        meetingTimeET = (EditText) view.findViewById(R.id.meetingTime);
        meetingDateET = (EditText) view.findViewById(R.id.meetingDate);
        priceGivenET = (EditText) view.findViewById(R.id.price_given_et);
        previousMeetingDateET = (EditText) view.findViewById(R.id.previousMeetingDate);
        meetingReasonET = (EditText) view.findViewById(R.id.meeting_reason);
        meetingOutcomeET = (EditText) view.findViewById(R.id.meeting_outcome);
        meetingTypeRG = (RadioGroup) view.findViewById(R.id.meetingTypeRG);
        meetingTypeLL = (LinearLayout) view.findViewById(R.id.meetingTypeLL);
        submitBtn = (Button) view.findViewById(R.id.submit);

        companyCategoryAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.company_category, R.layout.simple_spinner_item);
        companyCategorySpinner.setAdapter(companyCategoryAdapter);

        deviceSuggestionAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.device_suggestion, R.layout.simple_spinner_item);
        deviceSuggestionSpinner.setAdapter(deviceSuggestionAdapter);

        companyNameAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.simple_spinner_item);
        companyCategorySpinner.setAdapter(companyNameAdapter);

        nextMeetingDateET.setFocusable(false);
        nextMeetingDateET.setFocusableInTouchMode(false);
        meetingDateET.setFocusable(false);
        meetingDateET.setFocusableInTouchMode(false);
        previousMeetingDateET.setFocusable(false);
        previousMeetingDateET.setFocusableInTouchMode(false);

        meetingTimeET.setFocusable(false);
        meetingTimeET.setFocusableInTouchMode(false);

        meetingTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showTimePicker(getActivity(), new Utility.DateSelectable() {
                    @Override
                    public void onPicked(String d) {
                        meetingTimeET.setError(null);
                        meetingTimeET.setText(d);
                    }
                });
            }
        });

        nextMeetingDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showDatePicker(getActivity(), new Utility.DateSelectable() {
                    @Override
                    public void onPicked(String d) {
                        nextMeetingDateET.setError(null);
                        nextMeetingDateET.setText(d);
                    }
                });
            }
        });

        meetingDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showDatePicker(getActivity(), new Utility.DateSelectable() {
                    @Override
                    public void onPicked(String d) {
                        meetingDateET.setError(null);
                        meetingDateET.setText(d);
                    }
                });
            }
        });

        previousMeetingDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showDatePicker(getActivity(), new Utility.DateSelectable() {
                    @Override
                    public void onPicked(String d) {
                        previousMeetingDateET.setText(d);
                    }
                });
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                if (customerType.equals("Old")) {
                    if (validDataOld()) {
                        /*map.put("report_type", "daily_call_report");
                        map.put("customerType", customerType);
                        map.put("reference", "");
                        map.put("companyName", "");
                        if (companyNameSpinner.getSelectedItemPosition() > 0)
                            map.put("companyNamePreSelected", companyNameSpinner.getItemAtPosition(companyNameSpinner.getSelectedItemPosition() - 1));
                        if (companyCategorySpinner.getSelectedItemPosition() > 0)
                            map.put("companyCategory", companyCategorySpinner.getItemAtPosition(companyCategorySpinner.getSelectedItemPosition() - 1));
                        map.put("contactPerson", contactPersonET.getText().toString());
                        map.put("contactNumber", contactNumberET.getText().toString());
                        map.put("meetingType", meetingType);
                        map.put("meetingTime", meetingTimeET.getText().toString());
                        map.put("meetingDate", meetingDateET.getText().toString());
                        map.put("meetingOutcome", meetingOutcomeET.getText().toString());
                        map.put("deviceSuggestion", "");
                        map.put("priceGiven", "");
                        map.put("meetingStatus", "");
                        map.put("previousMeetingDate", previousMeetingDateET.getText().toString());
                        map.put("meetingReason", meetingReasonET.getText().toString());
                        map.put("nextMeetingDate", "");*/
                        map.put("report_type", "daily_call_report");
                        map.put("compamy_type", customerType);
                        map.put("reference", "");
                        map.put("company_name_text", "");
                        if (companyNameSpinner.getSelectedItemPosition() > 0)
                            map.put("company_name_select", companyNameSpinner.getItemAtPosition(companyNameSpinner.getSelectedItemPosition() - 1));
                        if (companyCategorySpinner.getSelectedItemPosition() > 0)
                            map.put("companyCategory", companyCategorySpinner.getItemAtPosition(companyCategorySpinner.getSelectedItemPosition() - 1));
                        map.put("contact_person", contactPersonET.getText().toString());
                        map.put("contactNumber", contactNumberET.getText().toString());
                        map.put("meetingType", meetingType);
                        map.put("meetingTime", meetingTimeET.getText().toString());
                        map.put("meetingDate", meetingDateET.getText().toString());
                        map.put("meetingOutcome", meetingOutcomeET.getText().toString());
                        map.put("deviceSuggestion", "");
                        map.put("priceGiven", "");
                        map.put("meetingStatus", "");
                        map.put("previousMeetingDate", previousMeetingDateET.getText().toString());
                        map.put("meetingReason", meetingReasonET.getText().toString());
                        map.put("nextMeetingDate", "");
                    } else {
                        return;
                    }
                } else if (customerType.equals("New")) {
                    if (validDataNew()) {
                        map.put("report_type", "daily_call_report");
                        map.put("customerType", customerType);
                        map.put("reference", "");
                        map.put("companyName", companyNameET.getText().toString());
                        map.put("companyNamePreSelected", "");
                        if (companyCategorySpinner.getSelectedItemPosition() > 0)
                            map.put("companyCategory", companyCategorySpinner.getItemAtPosition(companyCategorySpinner.getSelectedItemPosition() - 1));
                        map.put("contactPerson", contactPersonET.getText().toString());
                        map.put("contactNumber", contactNumberET.getText().toString());
                        map.put("meetingType", meetingType);
                        map.put("meetingTime", meetingTimeET.getText().toString());
                        map.put("meetingDate", meetingDateET.getText().toString());
                        map.put("meetingOutcome", meetingOutcomeET.getText().toString());
                        if (deviceSuggestionSpinner.getSelectedItemPosition() > 0)
                            map.put("deviceSuggestion", deviceSuggestionSpinner.getItemAtPosition(deviceSuggestionSpinner.getSelectedItemPosition() - 1));
                        map.put("priceGiven", priceGivenET.getText().toString());
                        map.put("meetingStatus", meetingStatus);
                        map.put("previousMeetingDate", "");
                        map.put("meetingReason", "");
                        map.put("nextMeetingDate", nextMeetingDateET.getText().toString());
                    } else {
                        return;
                    }

                } else {
                    if (validDataReference()) {
                        map.put("report_type", "daily_call_report");
                        map.put("customerType", customerType);
                        map.put("reference", referenceNameET.getText().toString());
                        map.put("companyName", companyNameET.getText().toString());
                        map.put("companyNamePreSelected", "");
                        if (companyCategorySpinner.getSelectedItemPosition() > 0)
                            map.put("companyCategory", companyCategorySpinner.getItemAtPosition(companyCategorySpinner.getSelectedItemPosition() - 1));
                        map.put("contactPerson", contactPersonET.getText().toString());
                        map.put("contactNumber", contactNumberET.getText().toString());
                        map.put("meetingType", meetingType);
                        map.put("meetingTime", meetingTimeET.getText().toString());
                        map.put("meetingDate", meetingDateET.getText().toString());
                        map.put("meetingOutcome", meetingOutcomeET.getText().toString());
                        if (deviceSuggestionSpinner.getSelectedItemPosition() > 0)
                            map.put("deviceSuggestion", deviceSuggestionSpinner.getItemAtPosition(deviceSuggestionSpinner.getSelectedItemPosition() - 1));

                        map.put("priceGiven", priceGivenET.getText().toString());
                        map.put("meetingStatus", meetingStatus);
                        map.put("previousMeetingDate", "");
                        map.put("meetingReason", "");
                        map.put("nextMeetingDate", nextMeetingDateET.getText().toString());
                    } else {
                        return;
                    }
                }

                AQuery mAQuery = new AQuery(getActivity());


                HashMap<String,Object> requestParams = new HashMap<String, Object>();
                requestParams.put("json",new JSONObject(map));
                System.out.println("###Request:"+requestParams);
                ProgressDialog pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage(getActivity().getString(R.string.please_wait));
                pDialog.setCancelable(false);
                mAQuery.progress(pDialog).ajax(ApiClient.API_URL+"/addWorkSheet", requestParams,
                        String.class, new AjaxCallback<String>() {
                            @Override
                            public void callback(String url, String data, AjaxStatus status) {
                                super.callback(url, data, status);
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
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getBoolean("success")) {
                                            {
                                                new MaterialDialog.Builder(getActivity())
                                                        .content(jsonObject.getString("message"))
                                                        .positiveText(getActivity().getString(android.R.string.ok))
                                                        .callback(new MaterialDialog.ButtonCallback() {
                                                            @Override
                                                            public void onPositive(MaterialDialog dialog) {
                                                                super.onPositive(dialog);
                                                                dialog.dismiss();
                                                                getActivity().onBackPressed();
                                                            }
                                                        })
                                                        .show();
                                            }
                                        } else
                                            ((MainActivity) getActivity()).showSimpleDialog(getContext(), "Server Error", jsonObject.getString("Message"));
                                    } catch (JSONException e) {
                                        ((MainActivity) getActivity()).showSimpleDialog(getContext(), "Server Error", "Something went wrong.");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                //map
            }
        });

    }

    private void customerTypeRGListener() {
        // This overrides the radiogroup onCheckListener
        customerTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    customerType = checkedRadioButton.getText().toString();
                    if (customerType.equals("Old")) {
                        oldDailyCallReport();
                    } else if (customerType.equals("New")) {
                        newDailyCallReport();
                    } else {
                        referenceDailyCallReport();
                    }
                }
            }
        });

        // This overrides the radiogroup onCheckListener
        meetingTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    meetingType = checkedRadioButton.getText().toString();
                }
            }
        });

        // This overrides the radiogroup onCheckListener
        meetingStatusRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    meetingStatus = checkedRadioButton.getText().toString();
                }
            }
        });
    }

    private void oldDailyCallReport() {
        invisibleAll();
        companyNameSpinner.setVisibility(View.VISIBLE);
        customerTypeLL.setVisibility(View.VISIBLE);
        customerTypeRG.setVisibility(View.VISIBLE);
        companyCategorySpinner.setVisibility(View.VISIBLE);
        contactPersonET.setVisibility(View.VISIBLE);
        contactNumberET.setVisibility(View.VISIBLE);
        meetingTypeLL.setVisibility(View.VISIBLE);
        meetingTypeRG.setVisibility(View.VISIBLE);
        meetingTimeET.setVisibility(View.VISIBLE);
        meetingDateET.setVisibility(View.VISIBLE);
        previousMeetingDateET.setVisibility(View.VISIBLE);
        meetingReasonET.setVisibility(View.VISIBLE);
        meetingOutcomeET.setVisibility(View.VISIBLE);
    }

    private void newDailyCallReport() {
        invisibleAll();
        customerTypeLL.setVisibility(View.VISIBLE);
        customerTypeRG.setVisibility(View.VISIBLE);
        companyNameET.setVisibility(View.VISIBLE);
        companyCategorySpinner.setVisibility(View.VISIBLE);
        contactPersonET.setVisibility(View.VISIBLE);
        contactNumberET.setVisibility(View.VISIBLE);
        meetingTypeLL.setVisibility(View.VISIBLE);
        meetingTypeRG.setVisibility(View.VISIBLE);
        meetingTimeET.setVisibility(View.VISIBLE);
        meetingDateET.setVisibility(View.VISIBLE);
        meetingOutcomeET.setVisibility(View.VISIBLE);
        deviceSuggestionSpinner.setVisibility(View.VISIBLE);
        priceGivenET.setVisibility(View.VISIBLE);
        meetingStatusLL.setVisibility(View.VISIBLE);
        meetingStatusRG.setVisibility(View.VISIBLE);
        nextMeetingDateET.setVisibility(View.VISIBLE);
    }

    private void referenceDailyCallReport() {
        invisibleAll();
        customerTypeLL.setVisibility(View.VISIBLE);
        customerTypeRG.setVisibility(View.VISIBLE);
        referenceNameET.setVisibility(View.VISIBLE);
        companyNameET.setVisibility(View.VISIBLE);
        companyCategorySpinner.setVisibility(View.VISIBLE);
        contactPersonET.setVisibility(View.VISIBLE);
        contactNumberET.setVisibility(View.VISIBLE);
        meetingTypeLL.setVisibility(View.VISIBLE);
        meetingTypeRG.setVisibility(View.VISIBLE);
        meetingTimeET.setVisibility(View.VISIBLE);
        meetingDateET.setVisibility(View.VISIBLE);
        meetingOutcomeET.setVisibility(View.VISIBLE);
        deviceSuggestionSpinner.setVisibility(View.VISIBLE);
        priceGivenET.setVisibility(View.VISIBLE);
        meetingStatusLL.setVisibility(View.VISIBLE);
        meetingStatusRG.setVisibility(View.VISIBLE);
        nextMeetingDateET.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_call_report_fragment, container, false);
        initView(view);
        customerTypeRGListener();
        oldDailyCallReport();
        getCompanyName();
        //      getAllLocation();
        //    setDataOnView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }

    private void getCompanyName() {
        AQuery mAQuery = new AQuery(getActivity());

        ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getActivity().getString(R.string.please_wait));
        pDialog.setCancelable(false);
        mAQuery.progress(pDialog).ajax(ApiClient.API_URL+"/getcompanyname",
                String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String data, AjaxStatus status) {
                        super.callback(url, data, status);
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
                            CompanyNameModel model = CompanyNameModel.parseResponse(data);
                            if (model.isStatus()) {
                                companyDataList.addAll(model.getCompanyDataList());

                                for (CompanyData companyData : companyDataList) {
                                    companyNameAdapter.add(companyData.getName());
                                }
                            } else
                                ((MainActivity) getActivity()).showSimpleDialog(getContext(), "Server Error", model.getMessage());
                        }
                    }
                });
    }

    List<CompanyData> companyDataList = new ArrayList<>();


    private boolean validDataOld() {
        boolean is_valid = true;


        if (companyNameSpinner.getSelectedItemPosition() == 0) {
            companyNameSpinner.setError(getString(R.string.required));
            is_valid = false;
        }
        if (companyCategorySpinner.getSelectedItemPosition() == 0) {
            companyCategorySpinner.setError(getString(R.string.required));
            is_valid = false;
        }

        if (TextUtils.isEmpty(contactPersonET.getText().toString().trim())) {
            contactPersonET.setError(getString(R.string.required));
            is_valid = false;
        }

        if (TextUtils.isEmpty(contactNumberET.getText().toString().trim())) {
            contactNumberET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingTimeET.getText().toString().trim())) {
            meetingTimeET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingDateET.getText().toString().trim())) {
            meetingDateET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingReasonET.getText().toString().trim())) {
            meetingReasonET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingOutcomeET.getText().toString().trim())) {
            meetingOutcomeET.setError(getString(R.string.required));
            is_valid = false;
        }

        return is_valid;
    }

    private boolean validDataNew() {
        boolean is_valid = true;


        if (TextUtils.isEmpty(companyNameET.getText().toString().trim())) {
            companyNameET.setError(getString(R.string.required));
            is_valid = false;
        }

        if (companyCategorySpinner.getSelectedItemPosition() == 0) {
            companyCategorySpinner.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(contactPersonET.getText().toString().trim())) {
            contactPersonET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(contactNumberET.getText().toString().trim())) {
            contactNumberET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingTimeET.getText().toString().trim())) {
            meetingTimeET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingDateET.getText().toString().trim())) {
            meetingDateET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingOutcomeET.getText().toString().trim())) {
            meetingOutcomeET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (deviceSuggestionSpinner.getSelectedItemPosition() == 0) {
            deviceSuggestionSpinner.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(priceGivenET.getText().toString().trim())) {
            priceGivenET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(nextMeetingDateET.getText().toString().trim())) {
            nextMeetingDateET.setError(getString(R.string.required));
            is_valid = false;
        }
        return is_valid;
    }

    private boolean validDataReference() {
        boolean is_valid = true;

        if (TextUtils.isEmpty(referenceNameET.getText().toString().trim())) {
            referenceNameET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(companyNameET.getText().toString().trim())) {
            companyNameET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (companyCategorySpinner.getSelectedItemPosition() == 0) {
            companyCategorySpinner.setError(getString(R.string.required));
            is_valid = false;
        }

        if (TextUtils.isEmpty(contactPersonET.getText().toString().trim())) {
            contactPersonET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(contactNumberET.getText().toString().trim())) {
            contactNumberET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingTimeET.getText().toString().trim())) {
            meetingTimeET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingDateET.getText().toString().trim())) {
            meetingDateET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(meetingOutcomeET.getText().toString().trim())) {
            meetingOutcomeET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (deviceSuggestionSpinner.getSelectedItemPosition() == 0) {
            deviceSuggestionSpinner.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(priceGivenET.getText().toString().trim())) {
            priceGivenET.setError(getString(R.string.required));
            is_valid = false;
        }
        if (TextUtils.isEmpty(nextMeetingDateET.getText().toString().trim())) {
            nextMeetingDateET.setError(getString(R.string.required));
            is_valid = false;
        }
        return is_valid;
    }

    @Override
    public String getTitle() {
        return "Daily Call Report";
    }
}
