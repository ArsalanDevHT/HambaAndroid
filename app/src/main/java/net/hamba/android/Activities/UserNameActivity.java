package net.hamba.android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import net.hamba.android.Animations.MyBounceInterpolator;
import net.hamba.android.Models.User;
import net.hamba.android.R;
import net.hamba.android.Utils.PrefsUtils;

public class UserNameActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ImageView img_robot;
    private ImageView img_hello;
    private TextView counter;
    private EditText username;
    private Button btn_enter;
    private LinearLayout ll_please;
    private LinearLayout ll_username;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    Animation RobotSlideInLeft = null;
    Animation HelloSlideInTop  = null;
    Animation PleaseSlideInBottom   = null;
    Animation UsernameSlideInBottom   = null;
    Animation BtnFadeIn   = null;
    Animation BtnFadeOut   = null;
    SharedPreferences sharedpreferences;
    private boolean isBtnVisible =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        InitUI();
    }

    private void InitUI(){

        img_hello = (ImageView) findViewById(R.id.img_hello);
        img_robot = (ImageView) findViewById(R.id.img_robot);
        counter   = (TextView) findViewById(R.id.counter);
        username  = (EditText) findViewById(R.id.username);
        btn_enter = (Button) findViewById(R.id.btn_enter);
        ll_please = (LinearLayout) findViewById(R.id.ll_please);
        ll_username = (LinearLayout) findViewById(R.id.ll_username);
        btn_enter.setOnClickListener(this);
        img_hello.setVisibility(View.INVISIBLE);
        ll_please.setVisibility(View.INVISIBLE);
        username.addTextChangedListener(this);

        RobotSlideInLeft     = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
        HelloSlideInTop      = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_top);
        BtnFadeIn      = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        BtnFadeOut   = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        PleaseSlideInBottom   = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_bottom);
        UsernameSlideInBottom   = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_bottom);
        img_robot.startAnimation(RobotSlideInLeft);

      /*  final Animation bounceAnomation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.4, 20);
        bounceAnomation.setInterpolator(interpolator);
        img_robot.startAnimation(bounceAnomation);*/
        RobotSlideInLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_hello.setVisibility(View.VISIBLE);
                img_hello.startAnimation(HelloSlideInTop);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        HelloSlideInTop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_please.setVisibility(View.VISIBLE);
                ll_please.startAnimation(PleaseSlideInBottom);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        PleaseSlideInBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_username.setVisibility(View.VISIBLE);
                ll_username.startAnimation(UsernameSlideInBottom);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enter:
                /// TODO Remove this Code
               /* startActivity(new Intent(UserNameActivity.this,IndividualHomeActivity.class));
                finish();
*/
               Toast.makeText(this, "Button Disable", Toast.LENGTH_SHORT).show();
                /*startActivity(new Intent(UserNameActivity.this, SetupProfileActivity.class));
                finish();*/

             /*   if(username.getText().toString().isEmpty() ){
                    username.setError("Please enter username");
                }
                if(username.getText().toString().length()<6 ){
                    username.setError("Username must contain at least 3 characters");

                }else{
                    User user =  new User();
                    user.userProfile.username =username.getText().toString();
                    user.phoneNumber = PrefsUtils.getPhoneNumber();
                    user.interests = "";
                    showProgressDialog();
                    saveUserData(user);

                }*/
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
      /*  if(PrefsUtils.getLoggedinUserType()==3){

        }
       else if(PrefsUtils.hasUserCompletedRegistration()){
            Log.e( "onStart: ","here"+PrefsUtils.hasUserCompletedRegistration() );
            startActivity(new Intent(UserNameActivity.this,IndividualHomeActivity.class));
            finish();
        }else{

        }
*/

    }
    private void saveUserData(User user) {
        setProgressDialogMessage("Saving...");
        hideProgressDialog();
        startActivity(new Intent(UserNameActivity.this,IndividualHomeActivity.class));
        finish();
/*
        FirebaseDatabase.getInstance().getReference().child("users").child(getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    PrefsUtils.userCompletedRegistered(true);
                    hideProgressDialog();
                    startActivity(new Intent(UserNameActivity.this,IndividualHomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Saving failed.", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        counter.setText(username.getText().toString().length()+"/6");
        if(username.getText().toString().length()<6){
            if(isBtnVisible){
                btn_enter.startAnimation(BtnFadeOut);
                btn_enter.setVisibility(View.INVISIBLE);
                isBtnVisible = false;

            }



        }else{
            if(!isBtnVisible)
            {
                isBtnVisible = true;
                btn_enter.setVisibility(View.VISIBLE);
                btn_enter.startAnimation(BtnFadeIn);

            }

        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
