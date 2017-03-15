package com.anhad.fleetgaurd.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.BaseActivity;
import com.anhad.fleetgaurd.activity.MainActivity;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.api.Util;
import com.anhad.fleetgaurd.model.ForgotPasswordRequestModel;
import com.anhad.fleetgaurd.model.ForgotPasswordResponseModel;
import com.anhad.fleetgaurd.model.LoginRequestModel;
import com.anhad.fleetgaurd.model.LoginResponseModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 07-01-2017.
 */
public class LoginFragment extends BaseFragment {

    private EditText username;
    private EditText password;
    private Button login;
    private LoginResponseModel loginResponseModel;
    private TextView mForgotPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().hide();


        View convertView = inflater.inflate(R.layout.login_fragment,container,false);
        username = (EditText) convertView.findViewById(R.id.user_name);
        password = (EditText) convertView.findViewById(R.id.password);
        login = (Button) convertView.findViewById(R.id.login);

        initForgotPassword(convertView);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.hideKeyboard(getActivity(),v);
                if (validData()){
                    final LoginRequestModel model = new LoginRequestModel();
                    String token = FirebaseInstanceId.getInstance().getToken();
                    try {
                        model.setEmail(username.getText().toString());
                        model.setPassword(password.getText().toString());
                        model.setFcmId(""+token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ApiClient.ApiManagement.doLogin(getActivity(), Utility.getJsonObject(getActivity(), model), true, new ApiInterface.OnComplete() {
                        @Override
                        public void onSuccess(Object object) {
                            if (object instanceof LoginResponseModel){
                                loginResponseModel = (LoginResponseModel)object;
                                if(loginResponseModel.getStatus()){
                                SavedPreferences.getInstance().saveStringValue(loginResponseModel.getResponse().getUser().getFullName(),SavedPreferences.FULL_NAME);
                                SavedPreferences.getInstance().saveStringValue(loginResponseModel.getResponse().getUser().getId(),SavedPreferences.ID);
                                SavedPreferences.getInstance().saveStringValue(loginResponseModel.getResponse().getUser().getRole(),SavedPreferences.ROLE);
                                ((BaseActivity)getActivity()).getSupportFragmentManager().popBackStack(LoginFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                clearFields();
                                Utility.removeFragment((BaseActivity)getActivity(),LoginFragment.this);
                                Utility.replaceFragment((BaseActivity)getActivity(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(),true);
                                }
                                else{
                                    ((MainActivity)getActivity()).showSimpleDialog(getContext(),"Server Error",loginResponseModel.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onError(RetrofitError error) {
                            Utility.showDialog(getActivity(),error.getMessage());
                        }
                    });
                }
            }
        });
        return convertView;
    }

    private void initForgotPassword(View convertView) {

        mForgotPassword = (TextView)convertView.findViewById(R.id.tv_forgot_password);

        String mystring=new String("Forgot Password ?");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        mForgotPassword.setText(content);

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openDialogForgotPassword();
            }
        });
    }

    private void openDialogForgotPassword() {

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  LayoutInflater inflater = context.getLayoutInflater(getArguments());
        View alertLayout = inflater.inflate(R.layout.dialog_forgot_password, null);
        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        final TextInputLayout tilEmail = (TextInputLayout) alertLayout.findViewById(R.id.til_forgot_password);
        final EditText etEmail = (EditText) alertLayout.findViewById(R.id.et_email);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.Animations_SmileWindow;
        dialog.show();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etEmail.getText().toString().trim())){

                    tilEmail.setError("Please enter email");
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()){

                    tilEmail.setError("Please enter valid email");
                }
                else {
                    tilEmail.setError(null);
                    tilEmail.setErrorEnabled(false);


                    final ForgotPasswordRequestModel model = new ForgotPasswordRequestModel();
                    try {
                        model.setEmail(etEmail.getText().toString().trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ApiClient.ApiManagement.doForgotPassword(getActivity(), Utility.getJsonObject(getActivity(), model), true, new ApiInterface.OnComplete() {
                        @Override
                        public void onSuccess(Object object) {
                            if (object instanceof ForgotPasswordResponseModel){
                                ForgotPasswordResponseModel passwordResponseModel = (ForgotPasswordResponseModel)object;

                                String message = passwordResponseModel.getMessage();
                                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                                etEmail.setText("");
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onError(RetrofitError error) {
                            etEmail.setText("");
                            dialog.dismiss();
                            Utility.showDialog(getActivity(),error.getMessage());

                        }
                    });


                }

            }
        });


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() != 0){
                    tilEmail.setError(null);
                    tilEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void clearFields() {
        username.setText(null);
        password.setText(null);

        username.requestFocus();
    }

    private boolean validData() {
        boolean is_valid = true;

        if(TextUtils.isEmpty(username.getText().toString().trim())){
            username.setError(getString(R.string.required));
            is_valid = false;
        }

        else if(TextUtils.isEmpty(password.getText().toString().trim())){
            password.setError(getString(R.string.required));
            is_valid = false;
        }

        return is_valid;
    }


    @Override
    public String getTitle() {
        return "Login";
    }
}
