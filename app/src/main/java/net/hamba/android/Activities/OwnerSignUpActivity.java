package net.hamba.android.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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

import net.hamba.android.Customes.CountryCodePicker;
import net.hamba.android.Models.OwnerModel;
import net.hamba.android.R;
import net.hamba.android.Utils.FirebaseUtils;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;

public class OwnerSignUpActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private LinearLayout form;
    private Button btn_register;
    private CountryCodePicker ccp;
    private EditText ET_name,ET_email,ET_phone,ET_city,ET_pass;
    private Switch SW_shop_owner,SW_wholesale,SW_delivery_boy,SW_delivery_com;
    private TextView TV_shop_owner,TV_wholesale,TV_delivery_boy,TV_delivery_com;
    private TextInputLayout TI_city,TI_email,TI_name;
    private boolean isBusinessSelected = false;
    private LinearLayout field;
    private TextView error,error2;
    private FirebaseAuth mAuth;
    OwnerModel model = null;
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
        setContentView(R.layout.activity_owner_sign_up);
        mAuth = FirebaseAuth.getInstance();
        InitUI();
    }

    private void InitUI() {
        form            = findViewById(R.id.form);
        field           = findViewById(R.id.field);
        error           = findViewById(R.id.error);
        error2          = findViewById(R.id.error2);
        ET_name         = findViewById(R.id.ET_name);
        TI_name         = findViewById(R.id.TI_name);
        TI_city         = findViewById(R.id.TI_city);
        ET_email        = findViewById(R.id.ET_email);
        TI_email        = findViewById(R.id.TI_email);
        ET_city         = findViewById(R.id.ET_city);
        ET_pass         = findViewById(R.id.ET_pass);
        SW_shop_owner               = findViewById(R.id.SW_shop_owner);
        SW_wholesale                = findViewById(R.id.SW_wholesale);
        SW_delivery_boy             = findViewById(R.id.SW_delivery_boy);
        SW_delivery_com             = findViewById(R.id.SW_delivery_com);
        TV_shop_owner               = findViewById(R.id.TV_shop_owner);
        TV_wholesale                = findViewById(R.id.TV_wholesale);
        TV_delivery_boy             = findViewById(R.id.TV_delivery_boy);
        TV_delivery_com             = findViewById(R.id.TV_delivery_com);
        SW_shop_owner.setOnClickListener(this);
        SW_wholesale.setOnClickListener(this);
        SW_delivery_boy.setOnClickListener(this);
        SW_delivery_com.setOnClickListener(this);
        SW_shop_owner.setOnTouchListener(this);
        SW_wholesale.setOnTouchListener(this);
        SW_delivery_boy.setOnTouchListener(this);
        SW_delivery_com.setOnTouchListener(this);
        ccp                         = findViewById(R.id.ccp);
        btn_register                = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        ccp.getSelectedCountryName();
        model =    new OwnerModel();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                if(FormUtils.isValidTextInputParent(form)){
                    if(!TI_city.getEditText().getText().toString().isEmpty()){
                        if(TI_city.getEditText().getText().toString().length()<3) {
                            error.setText("City must contain 3 characters");
                            error.setVisibility(View.VISIBLE);
                            field.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                        }else {
                            error.setText("Required");
                            error.setVisibility(View.GONE);
                            field.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_white));
                            if(isBusinessSelected){
                                showProgressDialog();
                                setProgressDialogMessage("Authenticating...");
                                model.setBussinessName(FormUtils.getText(ET_name));
                                model.setCityName(FormUtils.getText(ET_city));
                                model.setCountryName(ccp.getSelectedCountryName());
                                model.setCountry_code(ccp.getSelectedCountryCode());
                                model.setEmail(FormUtils.getText(ET_email));
                                model.setPassword(FormUtils.getText(ET_pass));
                                model.setIsfullUser(false);
                                model.setProfilePercent("0% Complete");

                                FirebaseDatabase.getInstance().getReference("users/shop/employeer")
                                        .orderByChild("bussinessName")
                                        .equalTo(FormUtils.getText(ET_name))
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(!dataSnapshot.exists()) {
                                                   /* mAuth.createUserWithEmailAndPassword(ET_email.getText().toString(), ET_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                setProgressDialogMessage("Creating account...");
                                                                FirebaseDatabase.getInstance().getReference("users/shop/employeer")
                                                                        .child(mAuth.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("users/user_types").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                FirebaseDatabase.getInstance().getReference("users/accounts")
                                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FormUtils.getText(ET_email)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        setProgressDialogMessage("Updating Stores...");
                                                                                        FirebaseDatabase.getInstance().getReference("stores").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FormUtils.getText(ET_name)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                setProgressDialogMessage("Sending email...");
                                                                                                PrefsUtils.setLoggedinUserType(1);
                                                                                                FirebaseUser user = mAuth.getCurrentUser();
                                                                                                user.sendEmailVerification(settings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            PrefsUtils.saveFullUser(false);
                                                                                                            startActivity(new Intent(OwnerSignUpActivity.this, OwnerAuthEmailActivity.class));
                                                                                                                    finish();
                                                                                                                }else{
                                                                                                                    showAlertDialogMessage(R.string.error,R.string.email_failed);
                                                                                                                }
                                                                                                    }
                                                                                                });
//
//

                                                                                            }
                                                                                        });


                                                                                    }
                                                                                });
                                                                            }
                                                                        });


                                                                    }
                                                                });

                                                            } else {
                                                                hideProgressDialog();
                                                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                                                TI_email.setErrorEnabled(true);
                                                                TI_email.setError(FirebaseUtils.HandelException(errorCode));
                                                                TI_email.requestFocus();

                                                            }
                                                        }
                                                    });
                                               */ }else{
                                                    hideProgressDialog();
                                                    TI_email.setErrorEnabled(true);
                                                    TI_name.setError("Business name already taken");
                                                    TI_name.requestFocus();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });



                            }else{
                                error2.setVisibility(View.VISIBLE);

                            }

                        }

                    }else{
                        error.setText("Required");
                        error.setVisibility(View.VISIBLE);
                        field.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));

                    }

                }else{
                    if(TI_city.getEditText().getText().toString().isEmpty()){
                        error.setText("Required");
                        error.setVisibility(View.VISIBLE);
                        field.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                    }
                }
                break;
            case R.id.SW_delivery_boy:
                error2.setVisibility(View.GONE);
                isBusinessSelected = true;
                SW_delivery_com.setChecked(false);
                SW_shop_owner.setChecked(false);
                SW_wholesale.setChecked(false);
                SW_delivery_boy.setChecked(true);

                TV_delivery_com.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_shop_owner.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_wholesale.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_delivery_boy.setTextColor(getResources().getColor(R.color.colorWhite));
                model.setBussinessType(TV_delivery_boy.getText().toString());



                break;
            case R.id.SW_delivery_com:
                error2.setVisibility(View.GONE);
                isBusinessSelected = true;
                SW_delivery_boy.setChecked(false);
                SW_shop_owner.setChecked(false);
                SW_wholesale.setChecked(false);
                SW_delivery_com.setChecked(true);

                TV_delivery_boy.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_shop_owner.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_wholesale.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_delivery_com.setTextColor(getResources().getColor(R.color.colorWhite));
                model.setBussinessType(TV_delivery_com.getText().toString());

                break;
            case R.id.SW_shop_owner:
                error2.setVisibility(View.GONE);
                isBusinessSelected = true;
                SW_delivery_boy.setChecked(false);
                SW_delivery_com.setChecked(false);
                SW_wholesale.setChecked(false);
                SW_shop_owner.setChecked(true);

                TV_delivery_boy.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_shop_owner.setTextColor(getResources().getColor(R.color.colorWhite));
                TV_wholesale.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_delivery_com.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                model.setBussinessType(TV_shop_owner.getText().toString());
                break;
            case R.id.SW_wholesale:
                error2.setVisibility(View.GONE);
                isBusinessSelected = true;
                SW_delivery_boy.setChecked(false);
                SW_delivery_com.setChecked(false);
                SW_shop_owner.setChecked(false);
                SW_wholesale.setChecked(true);
                TV_delivery_boy.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_shop_owner.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                TV_wholesale.setTextColor(getResources().getColor(R.color.colorWhite));
                TV_delivery_com.setTextColor(getResources().getColor(R.color.colorDarkPrimary));
                model.setBussinessType(TV_wholesale.getText().toString());
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return event.getActionMasked() == MotionEvent.ACTION_MOVE;
    }
}
