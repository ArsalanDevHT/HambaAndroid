package net.hamba.android.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import net.hamba.android.R;
import net.hamba.android.Utils.FormUtils;

public class ForgotPassActivity extends BaseActivity implements View.OnClickListener {

    private EditText ET_email;
    private Button btn_continue;
    private FirebaseAuth mAuth;
    private LinearLayout form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        mAuth = FirebaseAuth.getInstance();
        InitUI();
    }

    private void InitUI() {
        form            = findViewById(R.id.form);
        ET_email        = findViewById(R.id.ET_email);
        btn_continue    = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);
    }
    private boolean editTextEmptyValidation(){
        if(ET_email.getText().toString().isEmpty()){
            ET_email.setError("Please Enter Email Address");
            return false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(ET_email.getText().toString()).matches()){
            ET_email.setError("Please Enter Valid Email Address");
            return false;

        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                if(editTextEmptyValidation()){
                    Intent intent=new Intent(this,ForgotPass2Activity.class);
                    startActivity(intent);
               /* if(FormUtils.isValidTextInputParent(form)){
                        showProgressDialog();
                        setProgressDialogMessage("Authenticating...");


                        FirebaseAuth.getInstance().sendPasswordResetEmail(FormUtils.getText(ET_email))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        setProgressDialogMessage("Sending email...");

                                        if (task.isSuccessful()) {
                                            setProgressDialogMessage("Email sent...");

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                setProgressDialogMessage(e.getMessage());
                            }
                        });

                }*/
                    break;

                }
        }
    }
}
