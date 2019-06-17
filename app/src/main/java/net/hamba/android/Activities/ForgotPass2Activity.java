package net.hamba.android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;

import net.hamba.android.R;

public class ForgotPass2Activity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linear_send_me_again;
    EditText eT_code, eT_newPassword,eT_confirmPassword;
    Button continue_btn;

    // String url = "https://xxxx.page.link/resetPassword";

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
        setContentView(R.layout.activity_forgot_pass2);
        initUI();
    }
    private void initUI(){
        linear_send_me_again = (LinearLayout)findViewById(R.id.linear_send_me_again);
        eT_code = (EditText)findViewById(R.id.ET_code);
        eT_newPassword = (EditText)findViewById(R.id.ET_new_password);
        eT_confirmPassword = (EditText)findViewById(R.id.ET_confirm_password);
        continue_btn = (Button) findViewById(R.id.btn_continue);
        continue_btn.setOnClickListener(this);
        linear_send_me_again.setOnClickListener(this);
    }
    private boolean editTextEmptyValidation(){
        if(eT_code.getText().toString().isEmpty() || eT_code.getText().toString().length()<4){
            eT_code.setError("Please Enter Four Digit Code");
            return false;
        }
        else if(eT_newPassword.getText().toString().isEmpty()){
            eT_newPassword.setError("Please Enter New Password");
            return false;

        }else if(eT_confirmPassword.getText().toString().isEmpty()){
            eT_confirmPassword.setError("Please Enter Confirm Password");
            return false;


        }else if(!eT_confirmPassword.getText().toString().equals(eT_newPassword.getText().toString())){
            eT_confirmPassword.setError("Password Not Matched");
            return false;


        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                if(editTextEmptyValidation()){
                    Toast.makeText(this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,ResetSuccessfull_Activity.class);
                    startActivity(intent);
                }
                break;
            case R.id.linear_send_me_again:
        }
    }
}
