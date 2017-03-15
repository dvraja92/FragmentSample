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
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.api.Util;
import com.anhad.fleetgaurd.model.AddDSRRequestModel;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.model.AddExpenseRequestModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;

import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 05-01-2017.
 */
public class AddExpenseFragment extends BaseFragment {

    private MaterialSpinner expenseSpinner;
    private Button submitBtn;
    private ArrayAdapter<CharSequence> expenseAdapter;
    private LinearLayout replacementLayout;
    private EditText amount;
    private EditText date;
    private EditText description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.add_expense_fragment, container,false);
        initViews(view);
        setDataOnView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }


    private void setDataOnView() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showDatePicker(getActivity(), new Utility.DateSelectable() {
                    @Override
                    public void onPicked(String d) {
                        date.setText(d);
                    }
                });
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()){
                    AddExpenseRequestModel model = new AddExpenseRequestModel();
                    model.setEmpId(SavedPreferences.getInstance().getStringValue(SavedPreferences.ID));
                    model.setExpenseDate(date.getText().toString());
                    model.setAmount(amount.getText().toString());
                    model.setDescription(description.getText().toString());
                    if (expenseSpinner.getSelectedItemPosition() > 0)
                        model.setExpenseCategory(String.valueOf(expenseSpinner.getItemAtPosition(expenseSpinner.getSelectedItemPosition() - 1)));
                    model.setStatus("1");

                    JSONObject jsonObject = Utility.getJsonObject(getActivity(),model);

                    if (jsonObject != null){
                        ApiClient.ApiManagement.addExpense(getActivity(), jsonObject,true, new ApiInterface.OnComplete() {
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
        amount.setText(null);
        description.setText(null);
        date.setText(null);
        expenseSpinner.setAdapter(expenseAdapter);
    }

    private void initViews(View view) {
        amount = (EditText)view.findViewById(R.id.amount);
        description = (EditText)view.findViewById(R.id.description);
        date = (EditText)view.findViewById(R.id.date);
        date.setFocusable(false);
        date.setFocusableInTouchMode(false);
        submitBtn = (Button)view.findViewById(R.id.submit);
        expenseSpinner = (MaterialSpinner)view.findViewById(R.id.expense_spinner);
        expenseAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.expense, R.layout.simple_spinner_item);
        expenseSpinner.setAdapter(expenseAdapter);
    }

    private boolean validData() {
        boolean is_valid = true;

        if(expenseSpinner.getSelectedItemPosition() == 0){
            expenseSpinner.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(date.getText().toString().trim())){
            date.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(amount.getText().toString().trim())){
            amount.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(description.getText().toString().trim())){
            description.setError(getString(R.string.required));
            is_valid = false;
        }


        return is_valid;
    }

    @Override
    public String getTitle() {
        return "Add Expense";
    }
}
