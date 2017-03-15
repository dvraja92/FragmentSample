package com.anhad.fleetgaurd.dilog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.BaseActivity;
import com.anhad.fleetgaurd.adapter.UserAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.api.Util;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.model.AdminStatusRequestModel;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.model.AssignUserRequestModel;
import com.anhad.fleetgaurd.model.UserListingModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;

import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 11-01-2017.
 */
public class UpdateWorkReportDialog {
    private static ArrayAdapter<CharSequence> adminStatusAdapter;
    private static ArrayAdapter<CharSequence> employeeStatusAdapter;
    private static boolean assignCalled, adminStatusCalled, empStatusCalled;
    public static Dialog showDialog(final Context context, UserListingModel userListing, final AdminWorkSheetReportModel data, final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.work_sheet_report_dialog_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final MaterialSpinner assignTo = (MaterialSpinner) dialog.findViewById(R.id.assign_to_spinner);
        final MaterialSpinner adminStatus = (MaterialSpinner) dialog.findViewById(R.id.admin_status_spinner);
        final MaterialSpinner employeeStatus = (MaterialSpinner) dialog.findViewById(R.id.employee_status_spinner);
        if (SavedPreferences.getInstance().getStringValue(SavedPreferences.ROLE).equalsIgnoreCase("admin")){
            employeeStatus.setVisibility(View.GONE);
        }
        else {
            assignTo.setVisibility(View.GONE);
            adminStatus.setVisibility(View.GONE);
        }

        final Button submit = (Button) dialog.findViewById(R.id.submit);
        adminStatusAdapter = ArrayAdapter.createFromResource(
                context, R.array.admin_status, R.layout.simple_spinner_item);
        employeeStatusAdapter = ArrayAdapter.createFromResource(
                context, R.array.emp_status, R.layout.simple_spinner_item);

        adminStatus.setAdapter(adminStatusAdapter);
        employeeStatus.setAdapter(employeeStatusAdapter);
        assignTo.setAdapter(new UserAdapter(context,userListing));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assignTo.getSelectedItemPosition() > 0 && data.getWorkSheetData().get(position).getWorkSheet().getUserId() != null && !data.getWorkSheetData().get(position).getWorkSheet().getUserId().equalsIgnoreCase(String.valueOf(((UserListingModel.UserNameList)assignTo.getItemAtPosition(assignTo.getSelectedItemPosition()-1)).getId()))){
                    AssignUserRequestModel model = new AssignUserRequestModel();
                    model.setHandoverId((String.valueOf(((UserListingModel.UserNameList)assignTo.getItemAtPosition(assignTo.getSelectedItemPosition()-1)).getId())));
                    model.setWorkSheetId(data.getWorkSheetData().get(position).getWorkSheet().getId());
                    JSONObject object = Utility.getJsonObject(context,model);
                    if (null != object) {
                        assignCalled = true;
                        ApiClient.ApiManagement.assignUser(context, object, true, new ApiInterface.OnComplete() {
                            @Override
                            public void onSuccess(Object object) {
                                assignCalled = false;
                                if (!(empStatusCalled && adminStatusCalled && assignCalled)){
                                    ApiClient.dialog.dismiss();
                                    Utility.showDialog(context,((AddDSRResponseModel)object).getMessage());
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(RetrofitError error) {
                                ApiClient.dialog.dismiss();
                            }
                        });
                    }
                }
                if (adminStatus.getSelectedItemPosition() > 0 && !data.getWorkSheetData().get(position).getWorkSheet().getStatus().equalsIgnoreCase((String) adminStatus.getItemAtPosition(adminStatus.getSelectedItemPosition()-1))){
                    AdminStatusRequestModel model = new AdminStatusRequestModel();
                    model.setReportId(data.getWorkSheetData().get(position).getWorkSheet().getId());
                    model.setStatus((String) adminStatus.getItemAtPosition(adminStatus.getSelectedItemPosition()-1));

                    JSONObject object = Utility.getJsonObject(context,model);
                    if (null != object){
                        adminStatusCalled = true;
                        ApiClient.ApiManagement.adminStatus(context, object, true, new ApiInterface.OnComplete() {
                            @Override
                            public void onSuccess(Object object) {
                                adminStatusCalled = false;
                                if (!(empStatusCalled && adminStatusCalled && assignCalled)){
                                    ApiClient.dialog.dismiss();
                                    Utility.showDialog(context,((AddDSRResponseModel)object).getMessage());
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(RetrofitError error) {
                                ApiClient.dialog.dismiss();
                            }
                        });
                    }
                }
                if (employeeStatus.getSelectedItemPosition() > 0 && !data.getWorkSheetData().get(position).getWorkSheet().getEmployeStatus().equalsIgnoreCase((String) employeeStatus.getItemAtPosition(employeeStatus.getSelectedItemPosition()-1))){
                    AdminStatusRequestModel model = new AdminStatusRequestModel();
                    model.setReportId(data.getWorkSheetData().get(position).getWorkSheet().getId());
                    model.setStatus((String) employeeStatus.getItemAtPosition(employeeStatus.getSelectedItemPosition()-1));
                    JSONObject object = Utility.getJsonObject(context,model);
                    if (null != object) {
                        empStatusCalled = true;
                        ApiClient.ApiManagement.employeeStatusUpdate(context, object, true, new ApiInterface.OnComplete() {
                            @Override
                            public void onSuccess(Object object) {
                                empStatusCalled = false;
                                if (!(empStatusCalled && adminStatusCalled && assignCalled)){
                                    ApiClient.dialog.dismiss();
                                    Utility.showDialog(context,((AddDSRResponseModel)object).getMessage());
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(RetrofitError error) {
                                ApiClient.dialog.dismiss();
                            }
                        });
                    }
                }

            }
        });
        dialog.show();
        return dialog;
    }

}
