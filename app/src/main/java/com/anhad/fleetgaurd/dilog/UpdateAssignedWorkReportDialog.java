package com.anhad.fleetgaurd.dilog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.adapter.UserAdapter;
import com.anhad.fleetgaurd.api.ApiClient;
import com.anhad.fleetgaurd.api.ApiInterface;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.model.AdminStatusRequestModel;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.model.AssignUserRequestModel;
import com.anhad.fleetgaurd.model.AssignedWorksheetResponseModel;
import com.anhad.fleetgaurd.model.UserListingModel;
import com.anhad.fleetgaurd.utility.SavedPreferences;
import com.anhad.fleetgaurd.utility.Utility;
import com.gc.materialdesign.views.MaterialSpinner;

import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by Anhad on 11-01-2017.
 */
public class UpdateAssignedWorkReportDialog {
    private static ArrayAdapter<CharSequence> adminStatusAdapter;
    private static ArrayAdapter<CharSequence> employeeStatusAdapter;
    private static boolean assignCalled, adminStatusCalled, empStatusCalled;
    public static Dialog showDialog(final Context context, UserListingModel userListing, final AssignedWorksheetResponseModel data, final int position) {
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

        assignTo.setVisibility(View.GONE);
        adminStatus.setVisibility(View.GONE);

        final Button submit = (Button) dialog.findViewById(R.id.submit);
        employeeStatusAdapter = ArrayAdapter.createFromResource(
                context, R.array.emp_status, R.layout.simple_spinner_item);

        employeeStatus.setAdapter(employeeStatusAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (employeeStatus.getSelectedItemPosition() > 0 && !data.getAssignWorkData().get(position).getEmployeStatus().equalsIgnoreCase((String) employeeStatus.getItemAtPosition(employeeStatus.getSelectedItemPosition()-1))){
                    AdminStatusRequestModel model = new AdminStatusRequestModel();
                    model.setReportId(data.getAssignWorkData().get(position).getId());
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
