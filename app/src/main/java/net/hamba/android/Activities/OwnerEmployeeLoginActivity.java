package net.hamba.android.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.hamba.android.R;
import net.hamba.android.Utils.FirebaseUtils;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;

public class OwnerEmployeeLoginActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout form;
    private Button btn_register;
    private EditText ET_email,ET_pass;
    private FirebaseAuth mAuth;
    private TextInputLayout TI_email;
    LinearLayout lin_forgetpass;
    String url = "https://mashoor-3dc0c.firebaseapp.com";

   /* ActionCodeSettings settings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName(
                    getPackageName(),
                    true, *//* install if not available? *//*
                    null   *//* minimum app version *//*)
            .setHandleCodeInApp(true)
            .setUrl(url)
            .build();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_employee_login);
        mAuth = FirebaseAuth.getInstance();
        InitUI();
    }

    private void InitUI() {
        form            = findViewById(R.id.form);
        ET_email        = findViewById(R.id.ET_email);
        ET_pass         = findViewById(R.id.ET_pass);
        TI_email        = findViewById(R.id.TI_email);
        btn_register    = findViewById(R.id.btn_register);
        lin_forgetpass= findViewById(R.id.lin_forgetpass);
        btn_register.setOnClickListener(this);
        lin_forgetpass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                if(FormUtils.isValidTextInputParent(form)){
                    Log.e( "onClick: ",FormUtils.getText(ET_email) );
                    setProgressDialogMessage("Authenticating...");
                   /* mAuth.signInWithEmailAndPassword(FormUtils.getText(ET_email),FormUtils.getText(ET_pass)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()){
                                showProgressDialog();
                                setProgressDialogMessage("Getting things ready...");
                                FirebaseDatabase.getInstance()
                                        .getReference("users/user_types").child(task.getResult().getUser().getUid())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    Log.e("onDataChange: ", dataSnapshot.getKey());
                                                    int user_type = dataSnapshot.getValue(Integer.class);
                                                    PrefsUtils.setLoggedinUserType(user_type);
                                                    if (user_type == 1) {
                                                        //employer
                                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                                            if (PrefsUtils.isFullUser()) {
                                                                startActivity(new Intent(OwnerEmployeeLoginActivity.this, OwnerHomeActivity.class));
                                                                finish();
                                                            } else {
                                                                startActivity(new Intent(OwnerEmployeeLoginActivity.this, OwnerSetupProfileActivity.class));
                                                                finish();
                                                            }
                                                        } else {
                                                            mAuth.getCurrentUser().sendEmailVerification(settings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        startActivity(new Intent(OwnerEmployeeLoginActivity.this, OwnerAuthEmailActivity.class));
                                                                        finish();
                                                                    } else {
                                                                        showAlertDialogMessage(R.string.error, R.string.email_failed);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                    if (user_type == 2) {
                                                        //employee
                                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                                            startActivity(new Intent(OwnerEmployeeLoginActivity.this, IndividualHomeActivity.class));
                                                            finish();
                                                        } else {
                                                            mAuth.getCurrentUser().sendEmailVerification(settings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        startActivity(new Intent(OwnerEmployeeLoginActivity.this, OwnerAuthEmailActivity.class));
                                                                        finish();
                                                                    } else {
                                                                        showAlertDialogMessage(R.string.error, R.string.email_failed);
                                                                    }
                                                                }
                                                            });
                                                        }

                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                            }else{
                                hideProgressDialog();
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                TI_email.setErrorEnabled(true);
                                TI_email.setError(FirebaseUtils.HandelException(errorCode));
                                TI_email.requestFocus();

                            }
                        }
                    });
               */ }
                break;
            case R.id.lin_forgetpass:
                Intent intent=new Intent(this,ForgotPassActivity.class);
                startActivity(intent);
                break;
        }
    }
}
