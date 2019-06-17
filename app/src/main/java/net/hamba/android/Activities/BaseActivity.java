package net.hamba.android.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import net.hamba.android.R;
import net.hamba.android.Utils.PrefsUtils;

public class BaseActivity extends AppCompatActivity {

    protected boolean activityDestroyed = false;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
        PrefsUtils.setContext(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDestroyed = true;
    }

    public BaseActivity setProgressDialogMessage(String message) {
        mProgressDialog.setMessage(message);
        return this;
    }

    public BaseActivity showProgressDialog(String title, String message) {
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();

        return this;
    }

    public BaseActivity showProgressDialog() {
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();

        return this;
    }

    public BaseActivity hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        return this;
    }

    public void showAlertDialogMessage(int titleID, int messageID){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(titleID));
        alert.setMessage(messageID);
        alert.setPositiveButton(R.string.ok, null);
        alert.show();
    }

    public void showAlertDialogWithoutMessage(int titleID){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(titleID));
        alert.setMessage("");
        alert.setPositiveButton(R.string.ok, null);
        alert.show();
    }

    protected void enableViews(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    protected void disableViews(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getPhoneNumber() {
        if (FirebaseAuth.getInstance() == null || FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;

        return FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    }


//    /**
//     * Get nationality list from resource string array
//     */
//    public void getNationalityList() {
//        ArrayList<String> nationalityList =
//                new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nationality_list)));
//        BaseDataManager.getInstance().setNationalityList(nationalityList);
//    }
//
//    /**
//     * Get interest list from server. +
//     */
//    public void getInterestList() {
//        ArrayList<String> interestList =
//                new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.interests_list)));
//        BaseDataManager.getInstance().setInterestList(interestList);
//    }

    public Context getActivity() {
        return this;
    }
}
