package net.hamba.android.Fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import net.hamba.android.Animations.Animations;
import net.hamba.android.Customes.PresetRadioGroup;
import net.hamba.android.Models.StoreModel;
import net.hamba.android.R;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;


public class CreateStoreStepTwoFragment extends Fragment implements View.OnClickListener, PresetRadioGroup.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {


    private ImageView arrow1, arrow2, arrow3;
    private boolean is1up = false;
    private boolean is2up = false;
    private boolean is3up = false;
    private LinearLayout payment_head,
            payment_body,
            shop_gender_head,
            shop_gender_body,
            delivery_support_head,
            delivery_support_body,
            btn_done, btn_back, ll_country, form;
    private Animations animation;
    ValueAnimator slideAnimator = null;
    private PresetRadioGroup specific, discount;
    private boolean isSpecific = true;
    private boolean isDiscountTag = true;
    private EditText ET_days, ET_hrs, ET_mins, ET_vat;
    private LinearLayout ll_delvery_time;
    private TextInputLayout TI_vat;
    private RadioGroup payments_group, delivey_support_group;
    private RadioButton male, female, family, gender_both, kids, other;
    private TextView err_time, err_payment, err_shop_gender, err_delivery;
    private String PaymentMethod = "";
    private String ShopGender = "";
    private String DeliverySupport = "";
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private StoreModel model = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_store_step_two_fragment, parent, false);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitUI(view);
        setdata();

    }

    public void InitUI(View v) {
        payment_head = v.findViewById(R.id.payment_head);
        btn_done = v.findViewById(R.id.btn_done);
        btn_back = v.findViewById(R.id.btn_back);
        payment_body = v.findViewById(R.id.payment_body);
        shop_gender_head = v.findViewById(R.id.shop_gender_head);
        shop_gender_body = v.findViewById(R.id.shop_gender_body);
        delivery_support_head = v.findViewById(R.id.delivery_support_head);
        delivery_support_body = v.findViewById(R.id.delivery_support_body);
        arrow1 = v.findViewById(R.id.arrow1);
        arrow2 = v.findViewById(R.id.arrow2);
        arrow3 = v.findViewById(R.id.arrow3);
        specific = v.findViewById(R.id.specific);
        discount = v.findViewById(R.id.discount);
        ll_country = v.findViewById(R.id.ll_country);
        ET_days = v.findViewById(R.id.ET_days);
        ET_hrs = v.findViewById(R.id.ET_hrs);
        ET_mins = v.findViewById(R.id.ET_mins);
        ll_delvery_time = v.findViewById(R.id.ll_delivery_time);
        TI_vat = v.findViewById(R.id.TI_vat);
        ET_vat = v.findViewById(R.id.Et_vat);
        payments_group = v.findViewById(R.id.payments_group);
        delivey_support_group = v.findViewById(R.id.delivey_support_group);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        gender_both = v.findViewById(R.id.gender_both);
        kids = v.findViewById(R.id.kids);
        family = v.findViewById(R.id.family);
        other = v.findViewById(R.id.other);
        err_time = v.findViewById(R.id.err_time);
        err_payment = v.findViewById(R.id.err_payment);
        err_shop_gender = v.findViewById(R.id.err_shop_gender);
        err_delivery = v.findViewById(R.id.err_delivery);
        form = v.findViewById(R.id.form);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        animation = new Animations();
        payment_head.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        gender_both.setOnClickListener(this);
        kids.setOnClickListener(this);
        family.setOnClickListener(this);
        other.setOnClickListener(this);
        shop_gender_head.setOnClickListener(this);
        delivery_support_head.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        specific.setOnCheckedChangeListener(this);
        discount.setOnCheckedChangeListener(this);
        payments_group.setOnCheckedChangeListener(this);
        delivey_support_group.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_head:

                if(!is1up){
                    animation.CollapsMenuAnimation(is1up,getContext(),payment_body,arrow1);
                    is1up = !is1up;

                }else{
                    animation.CollapsMenuAnimation(is1up,getContext(),payment_body,arrow1);
                    is1up = !is1up;

                }
//                if (!is1up) {
//                    is1up = true;
//                    arrow1.startAnimation(animate(is1up));
//                    slideAnimator = ValueAnimator
//                            .ofInt(200, 0)
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            payment_body.getLayoutParams().height = value.intValue();
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            payment_body.requestLayout();
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
//                            .ofInt(0, 200)
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            payment_body.getLayoutParams().height = value.intValue();
//                            Log.e("onClick: ", "" + is1up);
//
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            payment_body.requestLayout();
//                            payment_body.setVisibility(View.VISIBLE);
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
            case R.id.shop_gender_head:
                if(!is2up){
                    animation.CollapsMenuAnimation(is2up,getContext(),shop_gender_body,arrow2);
                    is2up = !is2up;

                }else{
                    animation.CollapsMenuAnimation(is2up,getContext(),shop_gender_body,arrow2);
                    is2up = !is2up;

                }

                break;

            case R.id.delivery_support_head:
                if(!is3up){
                    animation.CollapsMenuAnimation(is3up,getContext(),delivery_support_body,arrow3);
                    is3up = !is3up;

                }else{
                    animation.CollapsMenuAnimation(is3up,getContext(),delivery_support_body,arrow3);
                    is3up = !is3up;

                }

                break;
            case R.id.btn_done:
                boolean isvalid = true;
                if(!FormUtils.isValidTextInputParent(form)){
                    isvalid = (isvalid && false);

                }else{
                    isvalid = (isvalid && true);

                }
                String time = ET_days.getText().toString() + "" + ET_hrs.getText().toString() + "" + ET_mins.getText().toString();
                if (time.isEmpty()) {
                    ll_delvery_time.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                    err_time.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);
                } else {
                    ll_delvery_time.setBackground(getResources().getDrawable(R.drawable.primary_rounded_border_bg));
                    err_time.setVisibility(View.GONE);
                    isvalid = (isvalid && true);
                }
                if (PaymentMethod.isEmpty()) {
                    err_payment.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);
                } else {
                    err_payment.setVisibility(View.GONE);
                    isvalid = (isvalid && true);

                }
                if (ShopGender.isEmpty()) {
                    err_shop_gender.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);
                } else {
                    err_shop_gender.setVisibility(View.GONE);
                    isvalid = (isvalid && true);

                }
                if (DeliverySupport.isEmpty()) {
                    err_delivery.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);
                } else {
                    err_delivery.setVisibility(View.GONE);
                    isvalid = (isvalid && true);

                }
                if (isvalid) {
                    saveStore();
                }

                break;
            case R.id.btn_back:
                CreateStoreMainFragment.gotostepOne(getFragmentManager());
                break;
            case R.id.male:
                ShopGender = "Male";
                male.setChecked(true);
                female.setChecked(false);
                kids.setChecked(false);
                gender_both.setChecked(false);
                family.setChecked(false);
                other.setChecked(false);
                break;
            case R.id.female:
                ShopGender = "Female";
                male.setChecked(false);
                female.setChecked(true);
                kids.setChecked(false);
                gender_both.setChecked(false);
                family.setChecked(false);
                other.setChecked(false);
                break;
            case R.id.kids:
                ShopGender = "Kids";
                male.setChecked(false);
                female.setChecked(false);
                kids.setChecked(true);
                gender_both.setChecked(false);
                family.setChecked(false);
                other.setChecked(false);
                break;
            case R.id.gender_both:
                ShopGender = "Both";
                male.setChecked(false);
                female.setChecked(false);
                kids.setChecked(false);
                gender_both.setChecked(true);
                family.setChecked(false);
                other.setChecked(false);
                break;
            case R.id.family:
                ShopGender = "Family";
                male.setChecked(false);
                female.setChecked(false);
                kids.setChecked(false);
                gender_both.setChecked(false);
                family.setChecked(true);
                other.setChecked(false);
                break;
            case R.id.other:
                ShopGender = "Other";
                male.setChecked(false);
                female.setChecked(false);
                kids.setChecked(false);
                gender_both.setChecked(false);
                family.setChecked(false);
                other.setChecked(true);
                break;
        }
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(getContext(), up ? R.anim.rotate_up_anim : R.anim.rotate_down_anim);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }

    @Override
    public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
        switch (checkedId) {
            case R.id.specific_no:
                isSpecific = false;
                ll_country.setVisibility(View.GONE);
                break;
            case R.id.specific_yes:
                ll_country.setVisibility(View.VISIBLE);
                isSpecific = true;
                break;
            case R.id.discount_no:
                isDiscountTag = false;
                break;
            case R.id.discount_yes:
                isDiscountTag = true;
                break;

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.card:
                PaymentMethod = "Credit card (Visa/Mastercard)";
                break;
            case R.id.cod:
                PaymentMethod = "Cash on Delivery";
                break;
            case R.id.both:
                PaymentMethod = "Both";
                break;
            case R.id.pickup_delivery:
                DeliverySupport = "Pickup Delivery";
                break;
            case R.id.do_delivery:
                DeliverySupport = "Do Delivery";
                break;
            case R.id.both_delivery:
                DeliverySupport = "Both";
                break;

        }

    }

    private void saveStore() {
        String json = PrefsUtils.getStore();
        model = new Gson().fromJson(json, StoreModel.class);
        model.setSpecificCountry(isSpecific);
        model.setSpecific("");
        model.setDeliveryTime(ET_days.getText().toString() + ":" + ET_hrs.getText().toString() + ":" + ET_mins.getText().toString());
        model.setDiscountPercentage(isDiscountTag);
        model.setVatPercent(FormUtils.getText(ET_vat));
        model.setPaymentsAccepts(PaymentMethod);
        model.setShopGender(ShopGender);
        model.setDeliverySupports(DeliverySupport);
        Log.e( "saveStore: ",new Gson().toJson(model) );
        PrefsUtils.SaveStore(new Gson().toJson(model));
        CreateStoreMainFragment.stepTwoComplete(getFragmentManager());

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Updating Store...");
//        progressDialog.show();
//        database.getReference("new_stores").child(UUID.randomUUID().toString()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                progressDialog.dismiss();
//                CreateStoreMainFragment.stepTwoComplete(getFragmentManager());
//
//            }
//        });
    }

    private void setdata() {
        String json = PrefsUtils.getStore();
        Log.e("setdata: ",json );
        if (json != null) {
            StoreModel storeModel = new Gson().fromJson(json, StoreModel.class);
            if(storeModel.isSpecificCountry()){
                specific.check(R.id.specific_yes);

            }else{
                specific.check(R.id.specific_no);
            }
            if(storeModel.getDeliveryTime()!=null){
                String[] time =  storeModel.getDeliveryTime().split(":");
                ET_days.setText(time[0]);
                ET_hrs.setText(time[1]);
                ET_mins.setText(time[2]);
            }


            if(storeModel.isDiscountPercentage()){
                discount.check(R.id.discount_yes);

            }else {
                discount.check(R.id.discount_no);
            }
            ET_vat.setText(storeModel.getVatPercent());
            if(storeModel.getPaymentsAccepts()!=null){
                PaymentMethod = storeModel.getPaymentsAccepts();

                if(PaymentMethod.equals("Credit card (Visa/Mastercard)")){
                    payments_group.check(R.id.card);

                }else if(PaymentMethod.equals("Cash on Delivery")){
                    payments_group.check(R.id.cod);

                }else{
                    payments_group.check(R.id.both);
                }
            }
            if(storeModel.getShopGender()!=null) {
                ShopGender =  storeModel.getShopGender();
                if(ShopGender.equals("Male")){
                    male.setChecked(true);
                }else if(ShopGender.equals("Female")){
                    female.setChecked(true);
                }else if(ShopGender.equals("Kids")){
                    kids.setChecked(true);
                }else if(ShopGender.equals("Both")){
                    gender_both.setChecked(true);

                }else if(ShopGender.equals("Family")){
                    family.setChecked(true);
                }else{
                    other.setChecked(true);

                }
            }

            if(storeModel.getDeliverySupports()!=null) {
                DeliverySupport =  storeModel.getDeliverySupports();
                if(DeliverySupport.equals("Pickup Delivery")){
                    delivey_support_group.check(R.id.pickup_delivery);

                }else if(DeliverySupport.equals("Do Delivery")){
                    delivey_support_group.check(R.id.do_delivery);
                }else {
                    delivey_support_group.check(R.id.both_delivery);

                }


            }


        }
    }


}