package net.hamba.android.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.hamba.android.Models.EmployeeModel;
import net.hamba.android.R;
import net.hamba.android.Utils.FirebaseUtils;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;



public class EmployeeSignUpActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout form;
    private Button btn_register;
    private EditText ET_name,ET_email,ET_email_owner,ET_pass;
    private TextInputLayout TI_email,TI_name;
    private FirebaseAuth mAuth;
    private String sharedKey = "";
    private TextInputLayout TI_email_owner;

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
        setContentView(R.layout.activity_employee_sign_up);
        mAuth = FirebaseAuth.getInstance();
        InitUI();
    }

    private void InitUI() {
        form            = findViewById(R.id.form);
        ET_name         = findViewById(R.id.ET_name);
        ET_email_owner  = findViewById(R.id.ET_email_owner);
        TI_name         = findViewById(R.id.TI_name);
        TI_email        = findViewById(R.id.TI_email);
        ET_email        = findViewById(R.id.ET_email);
        ET_pass         = findViewById(R.id.ET_pass);
        btn_register    = findViewById(R.id.btn_register);
        TI_email_owner  = findViewById(R.id.TI_email_owner);
        btn_register.setOnClickListener(this);
        ET_email_owner.setOnClickListener(this);
       // TooltipCompat.setTooltipText(ET_email_owner,"Please enter the owner's email");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ET_email_owner:
              //  showAlertDialogMessage(R.string.info,R.string.msg_owner_email);
                showCustomeToast();
                break;
            case R.id.btn_register:

                if (FormUtils.isValidTextInputParent(form)) {
                    showProgressDialog();
                    setProgressDialogMessage("Authenticating...");
//                    FirebaseDatabase.getInstance().getReference("users/shop/employeer")
//                            .orderByChild("businessName").equalTo(FormUtils.getText(ET_name).trim())
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Log.e( "onDataChange: ",dataSnapshot.toString() );
//
//                            if(dataSnapshot.exists()){
                                FirebaseDatabase.getInstance().getReference("users/shop/employeer")
                                        .orderByChild("email")
                                        .equalTo(FormUtils.getText(ET_email_owner))
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Log.e( "onDataChange: ",dataSnapshot.toString() );


                                                if(dataSnapshot.exists()){
                                                    //Owner email exists in Database

                                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                                        setProgressDialogMessage("Finding Owner...");
                                                        sharedKey = childSnapshot.getKey();
                                                    }
                                                    PrefsUtils.setPreSharedKey(sharedKey);
                                                    setProgressDialogMessage("Owner found...");
                                                    EmployeeModel model = new EmployeeModel();
                                                    model.setBussinessName(FormUtils.getText(ET_name));
                                                    model.setEmail(FormUtils.getText(ET_email));
                                                    model.setPassword(FormUtils.getText(ET_pass));
                                                    model.setIsfullUser(false);
                                                    mAuth.createUserWithEmailAndPassword(FormUtils.getText(ET_email),FormUtils.getText(ET_pass)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {

                                                                setProgressDialogMessage("Creating account...");

                                                                FirebaseDatabase.getInstance().getReference("users/shop/employeer")
                                                                        .child(sharedKey)
                                                                        .child("employees")
                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("users/user_types")
                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                .setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                FirebaseDatabase.getInstance().getReference("users/accounts")
                                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FormUtils.getText(ET_email)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        setProgressDialogMessage("Finalizing...");
                                                                                        PrefsUtils.setLoggedinUserType(2);

                                                                                      /*  FirebaseUser user = mAuth.getCurrentUser();
                                                                                        user.sendEmailVerification(settings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    PrefsUtils.saveFullUser(false);
                                                                                                    startActivity(new Intent(EmployeeSignUpActivity.this, OwnerAuthEmailActivity.class));
                                                                                                    finish();
                                                                                                }else{
                                                                                                    
                                                                                                    showAlertDialogMessage(R.string.error,R.string.email_failed);
                                                                                                }
                                                                                            }
                                                                                        });
*/
                                                                                    }
                                                                                });
                                                                            }
                                                                        });


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
                                                } else {
                                                    //Owner email doesn't exists.
                                                    TI_email_owner.setError("No Owner with this email");
                                                    hideProgressDialog();
                                                }



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

//                            }
// else {
//                                TI_name.setErrorEnabled(true);
//                                TI_name.setError("Invalid Business name");
//                                TI_name.requestFocus();
//
//                            }

//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


                }

                break;
        }
    }
    public void showCustomeToast(){
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.tooltip_textview,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.message);
        text.setText("Please enter the owner's email");
        int location[] = {0,0};

        TI_email_owner.getLocationOnScreen(location);
        Toast toast =  new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,5);
        toast.setView(layout);
        toast.show();
    }

}
