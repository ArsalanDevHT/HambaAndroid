package net.hamba.android.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import net.hamba.android.R;
import net.hamba.android.Utils.PrefsUtils;

public class OwnerAuthEmailActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_done;
    private TextView text1,text2,text3;
    private FirebaseDatabase database;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_auth_email);
        database = FirebaseDatabase.getInstance();
        auth     = FirebaseAuth.getInstance();
        btn_done = findViewById(R.id.btn_done);
        text1    = findViewById(R.id.text1);
        text2    = findViewById(R.id.text2);
        text3    = findViewById(R.id.text3);
        btn_done.setOnClickListener(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String emailLink="";
        if(intent.getData()!=null){
            emailLink = intent.getData().toString();
            Log.e( "onCreate: ", "dads "+emailLink);
            // result.getAdditionalUserInfo().isNewUser()
            text1.setText("Email verification successful!");
            btn_done.setVisibility(View.VISIBLE);
            text2.setVisibility(View.GONE);
            text3.setVisibility(View.GONE);
            WebView webView;
            webView = (WebView)findViewById(R.id.help_webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(emailLink);

        }


    }
    public void gotoSetupprofileOwner(){
        startActivity(new Intent(OwnerAuthEmailActivity.this,OwnerSetupProfileActivity.class));

    }
    public void gotoSetupprofileEmployee(){
        startActivity(new Intent(OwnerAuthEmailActivity.this,EmployeeSetupProfileActivity.class));

    }

    @Override
    public void onClick(View v) {
        if(PrefsUtils.getLoggedinUserType()==1){
            showProgressDialog();
            setProgressDialogMessage("Updating profile...");

            database.getReference("users/shop/employeer")
                    .child(auth.getUid())
                    .child("profilePercent")
                    .setValue("10% Complete")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            setProgressDialogMessage("Finalizing...");
                            if(task.isSuccessful()){
                                hideProgressDialog();
                                gotoSetupprofileOwner();

                            }
                        }
                    });
        }
        if(PrefsUtils.getLoggedinUserType()==2) {
            showProgressDialog();
            setProgressDialogMessage("Updating profile...");

            database.getReference("users/shop/employeer")
                    .child(PrefsUtils.getPreSharedKey())
                    .child("employee")
                    .child(auth.getUid())
                    .child("profilePercent")
                    .setValue("10% Complete")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            setProgressDialogMessage("Finalizing...");
                            if(task.isSuccessful()){
                                hideProgressDialog();
                                gotoSetupprofileEmployee();

                            }
                        }
                    });

        }

    }
}
