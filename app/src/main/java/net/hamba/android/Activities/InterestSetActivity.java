package net.hamba.android.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import net.hamba.android.R;


public class InterestSetActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout LL_consumer,LL_services,LL_professional,LL_wholesale,LL_discoveries;
    private Animation BtnFadeIn   = null;
    private Button btn_continue;
    private boolean isButtonVisible = false;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String interests="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_set);
        InitUI();
    }

    private void InitUI() {
        LL_consumer     =  findViewById(R.id.LL_consumer);
        LL_services     = findViewById(R.id.LL_services);
        LL_professional = findViewById(R.id.LL_professional);
        LL_wholesale    = findViewById(R.id.LL_wholesale);
        LL_discoveries  = findViewById(R.id.LL_discoveries);
        btn_continue    = findViewById(R.id.btn_continue);
        BtnFadeIn       = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        auth            = FirebaseAuth.getInstance();
        database        = FirebaseDatabase.getInstance();
        LL_consumer.setOnClickListener(this);
        LL_services.setOnClickListener(this);
        LL_professional.setOnClickListener(this);
        LL_wholesale.setOnClickListener(this);
        LL_discoveries.setOnClickListener(this);
        btn_continue.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                showProgressDialog();
                setProgressDialogMessage("saving...");
                database.getReference("users").child(auth.getUid()).child("interests").setValue(interests).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(InterestSetActivity.this, SetupProfileActivity.class));
                            finish();

                        }

                    }
                });
                break;
            case R.id.LL_consumer:
                LL_consumer.setBackground(getResources().getDrawable(R.drawable.primary_border_bg));
                LL_services.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_professional.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_wholesale.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_discoveries.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                interests = "consumer";
                if(!isButtonVisible){
                    btn_continue.setVisibility(View.VISIBLE);
                    btn_continue.startAnimation(BtnFadeIn);
                    isButtonVisible = true;
                }
                break;
            case R.id.LL_services:
                LL_consumer.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_services.setBackground(getResources().getDrawable(R.drawable.primary_border_bg));
                LL_professional.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_wholesale.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_discoveries.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                interests = "services";
                if(!isButtonVisible){
                    btn_continue.setVisibility(View.VISIBLE);
                    btn_continue.startAnimation(BtnFadeIn);
                    isButtonVisible = true;
                }
                break;
            case R.id.LL_professional:
                LL_consumer.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_services.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_professional.setBackground(getResources().getDrawable(R.drawable.primary_border_bg));
                LL_wholesale.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_discoveries.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                interests = "professional";
                if(!isButtonVisible){
                    btn_continue.setVisibility(View.VISIBLE);
                    btn_continue.startAnimation(BtnFadeIn);
                    isButtonVisible = true;
                }
                break;
            case R.id.LL_wholesale:
                LL_consumer.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_services.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_professional.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_wholesale.setBackground(getResources().getDrawable(R.drawable.primary_border_bg));
                LL_discoveries.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                interests = "wholesale";
                if(!isButtonVisible){
                    btn_continue.setVisibility(View.VISIBLE);
                    btn_continue.startAnimation(BtnFadeIn);
                    isButtonVisible = true;
                }
                break;
            case R.id.LL_discoveries:
                LL_consumer.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_services.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_professional.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_wholesale.setBackground(getResources().getDrawable(R.drawable.rounded_border_bg));
                LL_discoveries.setBackground(getResources().getDrawable(R.drawable.primary_border_bg));
                interests = "discoveries";
                if(!isButtonVisible){
                    btn_continue.setVisibility(View.VISIBLE);
                    btn_continue.startAnimation(BtnFadeIn);
                    isButtonVisible = true;
                }
                break;
        }
    }
}
