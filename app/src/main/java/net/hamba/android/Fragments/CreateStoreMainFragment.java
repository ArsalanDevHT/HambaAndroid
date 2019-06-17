package net.hamba.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.shuhart.stepview.StepView;

import net.hamba.android.Activities.OwnerHomeActivity;
import net.hamba.android.R;

import java.util.ArrayList;


public class CreateStoreMainFragment extends Fragment {
    public static StepView stepView;
    public static FragmentTransaction ft;
    public static StateProgressBar stateProgressBar;
    private ImageView back_arrow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_store_main_frament, parent, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitUI(view);
        ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.fragment, new CreateStoreStepOneFragment());
        ft.commit();
        //stepTwoComplete(getChildFragmentManager());
    }
    public void InitUI(View v){
        stepView            = v.findViewById(R.id.step_view);
        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .animationType(StepView.ANIMATION_ALL)
                .selectedCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(getContext(), R.color.white))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>()
                {
                    {
                        add("Step 1");
                        add("Step 2");
                        add("Step 3");
                    }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp2))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.duco_headline_bold))
                // other state methods are equal to the corresponding xml attributes
                .commit();
        //  stepView.go(1,true);

        stateProgressBar = (StateProgressBar) v.findViewById(R.id.state_bar);
        ArrayList<String> des = new ArrayList<>();
        des.add("Step 1");
        des.add("Step 2");
        des.add("Step 3");
        stateProgressBar.setStateDescriptionData(des);
        stateProgressBar.setStateDescriptionTypeface("@fonts/duco_headline_reg.ttf");
        stateProgressBar.setStateNumberTypeface("@fonts/duco_headline_reg.ttf");
        back_arrow = v.findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goback();
            }
        });


    }
    public static void stepOneComplete(FragmentManager manager){
        //stepView.go(1,true);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        manager.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right).replace(R.id.fragment, new CreateStoreStepTwoFragment()).commit();
    }
    public static void stepTwoComplete(FragmentManager manager){
        //stepView.go(2,true);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        manager.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right).replace(R.id.fragment, new CreateStoreStepThreeFragment()).commit();


    }
    public static void stepThreeComplete(){
        //stepView.go(2,true);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        stateProgressBar.setAllStatesCompleted(true);

        //manager.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right).replace(R.id.fragment, new CreateStoreStepThreeFragment()).commit();
    }
    public static void gotostepOne(FragmentManager manager){
        //stepView.go(0,false);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        manager.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right).replace(R.id.fragment, new CreateStoreStepOneFragment()).commit();
    }
    public static void gotostepTwo(FragmentManager manager){
        //stepView.go(0,false);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        manager.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right).replace(R.id.fragment, new CreateStoreStepTwoFragment()).commit();
    }
    public  void goback(){
        switch (stateProgressBar.getCurrentStateNumber()){
            case 1 :
                startActivity(new Intent(getActivity(), OwnerHomeActivity.class));
                getActivity().finish();
                break;
            case 2:
                gotostepOne(getChildFragmentManager());
                break;
            case 3:
                gotostepTwo(getChildFragmentManager());
        }
    }

}

