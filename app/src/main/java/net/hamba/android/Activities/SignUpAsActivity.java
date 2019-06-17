package net.hamba.android.Activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.hamba.android.Animations.Animations;
import net.hamba.android.R;


public class SignUpAsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView arrow1;
    private ImageView arrow2;
    private boolean is1up = false;
    private boolean is2up = false;
    private LinearLayout business_owner_head;
    private LinearLayout business_owner_body;
    private LinearLayout employee_head;
    private LinearLayout employee_body;
    private LinearLayout new_business;
    private LinearLayout new_employee;
    private LinearLayout owner_login;
    private LinearLayout employee_login;
    private Animations animation;
    ValueAnimator slideAnimator =null;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as);
        InitUI();



    }

    private void InitUI() {
        arrow1              = findViewById(R.id.arrow1);
        arrow2              = findViewById(R.id.arrow2);
        new_employee        = findViewById(R.id.new_employee);
        employee_head       = findViewById(R.id.employee_head);
        employee_body       = findViewById(R.id.employee_body);
        owner_login       = findViewById(R.id.owner_login);
        employee_login       = findViewById(R.id.employee_login);
        business_owner_head = findViewById(R.id.business_owner_head);
        business_owner_body = findViewById(R.id.business_owner_body);
        new_business        = findViewById(R.id.new_business);
        animation = new Animations();
        business_owner_head.setOnClickListener(this);
        employee_head.setOnClickListener(this);
        new_business.setOnClickListener(this);
        new_employee.setOnClickListener(this);
        owner_login.setOnClickListener(this);
        employee_login.setOnClickListener(this);
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(this, up ? R.anim.rotate_up_anim : R.anim.rotate_down_anim);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.business_owner_head:
                if(!is1up){
                    animation.CollapsMenuAnimation(is1up,getApplicationContext(),business_owner_body,arrow1);
                    is1up = !is1up;

                }else{
                    animation.CollapsMenuAnimation(is1up,getApplicationContext(),business_owner_body,arrow1);
                    is1up = !is1up;

                }
//                if (!is1up) {
//                    is1up = true;
//                    arrow1.startAnimation(animate(is1up));
//                    slideAnimator = ValueAnimator
//                            .ofInt(250,0 )
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            business_owner_body.getLayoutParams().height = value.intValue();
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            business_owner_body.requestLayout();
//
//
//
//                        }
//                    });
//
//                } else {
//                    is1up = false;
//                    arrow1.startAnimation(animate(is1up));
//                    // animation.SlideInFromTop(getApplicationContext(),business_owner_body);
//                    slideAnimator = ValueAnimator
//                            .ofInt(0,250 )
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            business_owner_body.getLayoutParams().height = value.intValue();
//                            Log.e( "onClick: ",""+is1up);
//
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            business_owner_body.requestLayout();
//                            business_owner_body.setVisibility(View.VISIBLE);
//
//                        }
//                    });
//
//
//                }
//                // create a new animationset
//                AnimatorSet set = new AnimatorSet();
//// since this is the only animation we are going to run we just use
//// play
//                set.play(slideAnimator);
//// this is how you set the parabola which controls acceleration
//                set.setInterpolator(new AccelerateDecelerateInterpolator());
//// start the animation
//                set.start();
                break;
            case R.id.employee_head:
                if(!is2up){
                    animation.CollapsMenuAnimation(is2up,getApplicationContext(),employee_body,arrow2);
                    is2up = !is2up;

                }else{
                    animation.CollapsMenuAnimation(is2up,getApplicationContext(),employee_body,arrow2);
                    is2up = !is2up;

                }

//                if (!is2up) {
//                    is2up = true;
//                    arrow2.startAnimation(animate(is2up));
//                    slideAnimator = ValueAnimator
//                            .ofInt(250,0 )
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            employee_body.getLayoutParams().height = value.intValue();
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            employee_body.requestLayout();
//
//
//
//                        }
//                    });
//
//                } else {
//                    is2up = false;
//                    arrow2.startAnimation(animate(is2up));
//                    // animation.SlideInFromTop(getApplicationContext(),business_owner_body);
//                    slideAnimator = ValueAnimator
//                            .ofInt(0,250 )
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            employee_body.getLayoutParams().height = value.intValue();
//                            Log.e( "onClick: ",""+is1up);
//
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            employee_body.requestLayout();
//                            employee_body.setVisibility(View.VISIBLE);
//                        }
//                    });
//
//                }
//                // create a new animationset
//                AnimatorSet set1 = new AnimatorSet();
//// since this is the only animation we are going to run we just use
//// play
//                set1.play(slideAnimator);
//// this is how you set the parabola which controls acceleration
//                set1.setInterpolator(new AccelerateDecelerateInterpolator());
//// start the animation
//                set1.start();
                break;
            case R.id.new_business:
              //  Toast.makeText(this, "Button Disable", Toast.LENGTH_SHORT).show();
              gotoOwnerRegister(v);
                break;
            case R.id.new_employee:
               // Toast.makeText(this, "Button Disable", Toast.LENGTH_SHORT).show();
                gotoEmployeeRegister(v);
                break;
            case R.id.owner_login:
                gotoLogin(v);
                break;
            case R.id.employee_login:
                gotoLogin(v);
                break;

        }

    }
    public void gotoOwnerRegister(View v){
        startActivity(new Intent(SignUpAsActivity.this,OwnerSignUpActivity.class));

    }
    public void gotoEmployeeRegister(View v){
        startActivity(new Intent(SignUpAsActivity.this,EmployeeSignUpActivity.class));

    }
    public void gotoLogin(View v){
        startActivity(new Intent(SignUpAsActivity.this,OwnerEmployeeLoginActivity.class));

    }
}
