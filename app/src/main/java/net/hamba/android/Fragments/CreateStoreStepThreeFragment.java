package net.hamba.android.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import net.hamba.android.Animations.Animations;
import net.hamba.android.Models.StoreModel;
import net.hamba.android.Models.WeekdayModel;
import net.hamba.android.R;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;

import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CreateStoreStepThreeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout currency_head, currency_body,ll_aed,ll_eur,ll_usd,ll_add_weekdays,form;
    private LinearLayout ll_main_monday,ll_monday_head,ll_monday_body;
    private LinearLayout ll_main_tuesday,ll_tuesday_head,ll_tuesday_body;
    private LinearLayout ll_main_wednesday,ll_wednesday_head,ll_wednesday_body;
    private LinearLayout ll_main_thursday,ll_thursday_head,ll_thursday_body;
    private LinearLayout ll_main_friday,ll_friday_head,ll_friday_body;
    private LinearLayout ll_main_saturday,ll_saturday_head,ll_saturday_body;
    private LinearLayout ll_main_sunday,ll_sunday_head,ll_sunday_body;
    private RadioButton radio_aed,radio_eur,radio_usd,radio_delivery_cost,radio_delivery_cost_free;
    private TextInputLayout TI_delivery_cost;
    private EditText ET_delivery_cost,ET_min_order,ET_coupon,ET_tax_number,ET_attach;
    ImageView arrow1,monday_arrow,tuesday_arrow,wednesday_arrow,thursday_arrow,friday_arrow,saturday_arrow,sunday_arrow;
    private final int PICK_FILE_REQUEST = 751;

    Animations anim;
    private TextView save_as_draft;
    boolean isCurrencyDown       = false;
    boolean isMondayDown         = false;
    boolean isTuesdayDown        = false;
    boolean isWednesdayDown      = false;
    boolean isThursdayDown       = false;
    boolean isFridayDown         = false;
    boolean isSaturdaydayDown    = false;
    boolean isSundayDown         = false;
    boolean isMondayShow         = true;
    boolean isTuesdayShown       = false;
    boolean isWednesdayShown     = false;
    boolean isThursdayShown      = false;
    boolean isFridayShown        = false;
    boolean isSaturdaydayShown   = false;
    boolean isSundayShown        = false;
    private int weekdaysCount = 1;
    private String Currency = "";
    private TextView err_currency;
    private RadioGroup monday_group,tuesday_group,wednesday_group,thursday_group,friday_group,saturday_group,sunday_group;
    private StoreModel storeModel,model;
    private ArrayList<WeekdayModel> weekdays;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button btn_publish;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_store_step_three_fragment, parent, false);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitUI(view);
        setdata();

    }

    private void InitUI(View view) {
        currency_body               = view.findViewById(R.id.currency_body);
        currency_head               = view.findViewById(R.id.currency_head);
        arrow1                      = view.findViewById(R.id.arrow1);
        ll_aed                      = view.findViewById(R.id.ll_aed);
        ll_eur                      = view.findViewById(R.id.ll_eur);
        ll_usd                      = view.findViewById(R.id.ll_usd);
        radio_usd                   = view.findViewById(R.id.radio_usd);
        radio_eur                   = view.findViewById(R.id.radio_eur);
        radio_aed                   = view.findViewById(R.id.radio_aed);
        radio_delivery_cost         = view.findViewById(R.id.radio_delivery_cost);
        radio_delivery_cost_free    = view.findViewById(R.id.radio_delivery_cost_free);
        ET_delivery_cost            = view.findViewById(R.id.ET_delivery_cost);
        TI_delivery_cost            = view.findViewById(R.id.TI_delivery_cost);
        ll_main_monday              = view.findViewById(R.id.ll_main_monday);
        ll_monday_head              = view.findViewById(R.id.ll_monday_head);
        ll_monday_body              = view.findViewById(R.id.ll_monday_body);
        monday_arrow                = view.findViewById(R.id.monday_arrow);
        ll_main_tuesday             = view.findViewById(R.id.ll_main_tuesday);
        ll_tuesday_head             = view.findViewById(R.id.ll_tuesday_head);
        ll_tuesday_body             = view.findViewById(R.id.ll_tuesday_body);
        tuesday_arrow               = view.findViewById(R.id.tuesday_arrow);
        ll_main_wednesday           = view.findViewById(R.id.ll_main_wednesday);
        ll_wednesday_head           = view.findViewById(R.id.ll_wednesday_head);
        ll_wednesday_body           = view.findViewById(R.id.ll_wednesday_body);
        wednesday_arrow             = view.findViewById(R.id.wednesday_arrow);
        ll_main_thursday            = view.findViewById(R.id.ll_main_thursday);
        ll_thursday_head            = view.findViewById(R.id.ll_thursday_head);
        ll_thursday_body            = view.findViewById(R.id.ll_thursday_body);
        thursday_arrow              = view.findViewById(R.id.thursday_arrow);
        ll_main_friday              = view.findViewById(R.id.ll_main_friday);
        ll_friday_head              = view.findViewById(R.id.ll_friday_head);
        ll_friday_body              = view.findViewById(R.id.ll_friday_body);
        friday_arrow                = view.findViewById(R.id.friday_arrow);
        ll_main_saturday            = view.findViewById(R.id.ll_main_saturday);
        ll_saturday_head            = view.findViewById(R.id.ll_saturday_head);
        ll_saturday_body            = view.findViewById(R.id.ll_saturday_body);
        saturday_arrow              = view.findViewById(R.id.saturday_arrow);
        ll_main_sunday              = view.findViewById(R.id.ll_main_sunday);
        ll_sunday_head              = view.findViewById(R.id.ll_sunday_head);
        ll_sunday_body              = view.findViewById(R.id.ll_sunday_body);
        sunday_arrow                = view.findViewById(R.id.sunday_arrow);
        ll_add_weekdays             = view.findViewById(R.id.ll_add_weekdays);
        form                        = view.findViewById(R.id.form);
        save_as_draft               = view.findViewById(R.id.save_as_draft);
        err_currency                = view.findViewById(R.id.err_currency);
        monday_group                = view.findViewById(R.id.monday_group);
        tuesday_group               = view.findViewById(R.id.tuesday_group);
        wednesday_group             = view.findViewById(R.id.wednesday_group);
        thursday_group              = view.findViewById(R.id.thursday_group);
        friday_group                = view.findViewById(R.id.friday_group);
        saturday_group              = view.findViewById(R.id.saturday_group);
        sunday_group                = view.findViewById(R.id.sunday_group);
        ET_min_order                = view.findViewById(R.id.ET_min_order);
        ET_coupon                   = view.findViewById(R.id.ET_coupon);
        ET_tax_number               = view.findViewById(R.id.ET_tax_number);
        ET_attach                   = view.findViewById(R.id.ET_attach);
        btn_publish                   = view.findViewById(R.id.btn_publish);
        anim = new Animations();
        weekdays  = new ArrayList<>();
        currency_head.setOnClickListener(this);
        radio_usd.setOnClickListener(this);
        radio_eur.setOnClickListener(this);
        radio_aed.setOnClickListener(this);
        radio_delivery_cost_free.setOnClickListener(this);
        radio_delivery_cost.setOnClickListener(this);
        ll_monday_head.setOnClickListener(this);
        ll_tuesday_head.setOnClickListener(this);
        ll_wednesday_head.setOnClickListener(this);
        ll_thursday_head.setOnClickListener(this);
        ll_friday_head.setOnClickListener(this);
        ll_saturday_head.setOnClickListener(this);
        ll_sunday_head.setOnClickListener(this);
        ll_add_weekdays.setOnClickListener(this);
        save_as_draft.setOnClickListener(this);
        ET_attach.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference  = storage.getReference();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.currency_head:
                if(!isCurrencyDown){
                    anim.CollapsMenuAnimation(isCurrencyDown,getContext(),currency_body,arrow1);
                    isCurrencyDown = !isCurrencyDown;

                }else{
                    anim.CollapsMenuAnimation(isCurrencyDown,getContext(),currency_body,arrow1);
                    isCurrencyDown = !isCurrencyDown;

                }
                break;
            case R.id.ll_monday_head:
                if(!isMondayDown){
                    anim.CollapsMenuAnimation(isMondayDown,getContext(),ll_monday_body,monday_arrow);
                    isMondayDown = !isMondayDown;

                }else{
                    anim.CollapsMenuAnimation(isMondayDown,getContext(),ll_monday_body,monday_arrow);
                    isMondayDown = !isMondayDown;

                }
                break;
            case R.id.ll_tuesday_head:
                if(!isTuesdayDown){
                    anim.CollapsMenuAnimation(isTuesdayDown,getContext(),ll_tuesday_body,tuesday_arrow);
                    isTuesdayDown = !isTuesdayDown;

                }else{
                    anim.CollapsMenuAnimation(isTuesdayDown,getContext(),ll_tuesday_body,tuesday_arrow);
                    isTuesdayDown = !isTuesdayDown;

                }
                break;
            case R.id.ll_wednesday_head:
                if(!isWednesdayDown){
                    anim.CollapsMenuAnimation(isWednesdayDown,getContext(),ll_wednesday_body,wednesday_arrow);
                    isWednesdayDown = !isWednesdayDown;

                }else{
                    anim.CollapsMenuAnimation(isWednesdayDown,getContext(),ll_wednesday_body,wednesday_arrow);
                    isWednesdayDown = !isWednesdayDown;

                }
                break;
            case R.id.ll_thursday_head:
                if(!isThursdayDown){
                    anim.CollapsMenuAnimation(isThursdayDown,getContext(),ll_thursday_body,thursday_arrow);
                    isThursdayDown = !isThursdayDown;

                }else{
                    anim.CollapsMenuAnimation(isThursdayDown,getContext(),ll_thursday_body,thursday_arrow);
                    isThursdayDown = !isThursdayDown;

                }
                break;
            case R.id.ll_friday_head:
                if(!isFridayDown){
                    anim.CollapsMenuAnimation(isFridayDown,getContext(),ll_friday_body,friday_arrow);
                    isFridayDown = !isFridayDown;

                }else{
                    anim.CollapsMenuAnimation(isFridayDown,getContext(),ll_friday_body,friday_arrow);
                    isFridayDown = !isFridayDown;

                }
                break;
            case R.id.ll_saturday_head:
                if(!isSaturdaydayDown){
                    anim.CollapsMenuAnimation(isSaturdaydayDown,getContext(),ll_saturday_body,saturday_arrow);
                    isSaturdaydayDown = !isSaturdaydayDown;

                }else{
                    anim.CollapsMenuAnimation(isSaturdaydayDown,getContext(),ll_saturday_body,saturday_arrow);
                    isSaturdaydayDown = !isSaturdaydayDown;
                }
                break;
            case R.id.ll_sunday_head:
                if(!isSundayDown){
                    anim.CollapsMenuAnimation(isSundayDown,getContext(),ll_sunday_body,sunday_arrow);
                    isSundayDown = !isSundayDown;

                }else{
                    anim.CollapsMenuAnimation(isSundayDown,getContext(),ll_sunday_body,sunday_arrow);
                    isSundayDown = !isSundayDown;

                }
                break;
            case R.id.radio_aed:
                radio_aed.setChecked(true);
                radio_usd.setChecked(false);
                radio_eur.setChecked(false);
                ll_aed.setBackground(getResources().getDrawable(R.drawable.white_rounded_border_bg));
                ll_usd.setBackground(null);
                ll_eur.setBackground(null);
                Currency = "AED";

                break;
            case R.id.radio_usd:
                radio_aed.setChecked(false);
                radio_usd.setChecked(true);
                radio_eur.setChecked(false);
                ll_aed.setBackground(null);
                ll_usd.setBackground(getResources().getDrawable(R.drawable.white_rounded_border_bg));
                ll_eur.setBackground(null);
                Currency = "USD";
                break;
            case R.id.radio_eur:
                radio_aed.setChecked(false);
                radio_usd.setChecked(false);
                radio_eur.setChecked(true);
                ll_aed.setBackground(null);
                ll_usd.setBackground(null);
                ll_eur.setBackground(getResources().getDrawable(R.drawable.white_rounded_border_bg));
                Currency = "EUR";
                break;
            case R.id.radio_delivery_cost:
                radio_delivery_cost.setChecked(true);
                radio_delivery_cost_free.setChecked(false);
                TI_delivery_cost.setEnabled(true);

                break;
            case R.id.radio_delivery_cost_free:
                radio_delivery_cost.setChecked(false);
                radio_delivery_cost_free.setChecked(true);
                TI_delivery_cost.setEnabled(false);
                TI_delivery_cost.setErrorEnabled(false);
                break;
            case R.id.ll_add_weekdays:
                weekdaysCount++;

                switch (weekdaysCount){
                    case 1:
                        if(!isMondayShow){
                            isMondayShow = true;
                            ll_main_monday.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        if(!isTuesdayShown){
                            isTuesdayShown = true;
                            ll_main_tuesday.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 3:
                        if(!isWednesdayShown){
                            isWednesdayShown = true;
                            ll_main_wednesday.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 4:
                        if(!isThursdayShown){
                            isThursdayShown = true;
                            ll_main_thursday.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 5:
                        if(!isFridayShown){
                            isFridayShown = true;
                            ll_main_friday.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 6:
                        if(!isSaturdaydayShown){
                            isSaturdaydayShown = true;
                            ll_main_saturday.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 7:
                        if(!isSundayShown){
                            isSundayShown = true;
                            ll_main_sunday.setVisibility(View.VISIBLE);
                        }
                        break;
                }

                break;
            case R.id.save_as_draft:
                boolean isvalid = true;

                if(monday_group.getCheckedRadioButtonId()==R.id.monday_open&&isMondayShow){
                    if(!FormUtils.validateWeekdays(ll_monday_body)&& !isMondayDown){
                        ll_monday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }else{
                    FormUtils.clearError(ll_monday_body);
                    isvalid = (isvalid && true);
                }

                if(tuesday_group.getCheckedRadioButtonId()==R.id.tuesday_open &&isTuesdayShown){

                    if(!FormUtils.validateWeekdays(ll_tuesday_body)&&!isTuesdayDown){
                        ll_tuesday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }else{
                    FormUtils.clearError(ll_tuesday_body);
                    isvalid = (isvalid && true);
                }

                if(wednesday_group.getCheckedRadioButtonId()==R.id.wednesday_open&&isWednesdayShown){
                    if(!FormUtils.validateWeekdays(ll_wednesday_body)&&!isWednesdayDown){
                        ll_wednesday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }else{
                    FormUtils.clearError(ll_wednesday_body);
                    isvalid = (isvalid && true);
                }
                if(thursday_group.getCheckedRadioButtonId()==R.id.thursday_open&&isThursdayShown){
                    if(!FormUtils.validateWeekdays(ll_thursday_body)&& !isThursdayDown){
                        ll_thursday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }else{
                    FormUtils.clearError(ll_thursday_body);
                    isvalid = (isvalid && true);
                }
                if(friday_group.getCheckedRadioButtonId()==R.id.friday_open&&isFridayShown){
                    if(!FormUtils.validateWeekdays(ll_friday_body)&&!isFridayDown){
                        ll_friday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }
                else{
                    FormUtils.clearError(ll_friday_body);
                    isvalid = (isvalid && true);
                }
                if(saturday_group.getCheckedRadioButtonId()==R.id.saturday_open&&isSaturdaydayShown){
                    if(!FormUtils.validateWeekdays(ll_saturday_body)&&!isSaturdaydayDown){
                        ll_saturday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }
                else{
                    FormUtils.clearError(ll_saturday_body);
                    isvalid = (isvalid && true);
                }
                if(sunday_group.getCheckedRadioButtonId()==R.id.sunday_open&&isSundayShown){
                    if(!FormUtils.validateWeekdays(ll_sunday_body)&&!isSundayDown){
                        ll_sunday_head.performClick();
                        isvalid = (isvalid && false);
                    }
                }
                else{
                    FormUtils.clearError(ll_sunday_body);
                    isvalid = (isvalid && true);
                }
                if(!FormUtils.isValidTextInputParent(form)){
                    isvalid = (isvalid && false);

                }else {
                    isvalid = (isvalid && true);

                }

                if(Currency.isEmpty()){
                    err_currency.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);
                }else{
                    err_currency.setVisibility(View.GONE);
                    isvalid = (isvalid && true);
                }
                if(radio_delivery_cost.isChecked()){
                    if(ET_delivery_cost.getText().toString().isEmpty()){
                        TI_delivery_cost.setErrorEnabled(true);
                        TI_delivery_cost.setError("Required");
                        isvalid = (isvalid && false);
                    }else{
                        TI_delivery_cost.setErrorEnabled(false);
                        isvalid = (isvalid && true);
                    }
                }else{
                    TI_delivery_cost.setErrorEnabled(false);
                    isvalid = (isvalid && true);
                }
                if(isvalid){

                    saveStore();

                }
                break;
            case R.id.ET_attach:
                chooseFile();

                break;
            case R.id.btn_publish:
                save_as_draft.performClick();
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            ET_attach.setText(data.getData().toString());

        }
    }

    private void setdata() {
        String json = PrefsUtils.getStore();
        Log.e("setdata: ",json );
        if (json != null) {
            storeModel = new Gson().fromJson(json, StoreModel.class);
            if(storeModel.getStoreMinOrder()!=null)
                ET_min_order.setText(storeModel.getStoreMinOrder());
            if(storeModel.getCurrency()!= null){
                Currency = storeModel.getCurrency();
                if(Currency.equals("AED")){
                    radio_aed.performClick();
                }else if(Currency.equals("USD")){
                    radio_usd.performClick();

                }else if(Currency.equals("EUR")){
                    radio_eur.performClick();

                }
            }
            Log.e( "setdatadfad: ", new Gson().toJson(storeModel.getWeekdays()));
            if(storeModel.getWeekdays()!=null){
                for (WeekdayModel item:storeModel.getWeekdays()) {
                    Log.e( "setdata: ",item.getDayName() );
                    if(item.getDayName().equals("Monday")&& item.isOpen()){
                        FormUtils.setTime(ll_monday_body,item);
                        ll_add_weekdays.performClick();
                        ll_monday_head.performClick();
                        monday_group.check(R.id.monday_open);

                    }
                    if(item.getDayName().equals("Tuesday")&& item.isOpen()){
                        FormUtils.setTime(ll_tuesday_body,item);
                        ll_add_weekdays.performClick();
                        ll_tuesday_head.performClick();
                        tuesday_group.check(R.id.tuesday_open);
                    }
                    if(item.getDayName().equals("Wednesday")&& item.isOpen()){
                        FormUtils.setTime(ll_wednesday_body,item);
                        ll_add_weekdays.performClick();
                        ll_wednesday_head.performClick();
                        wednesday_group.check(R.id.wednesday_open);
                    }
                    if(item.getDayName().equals("Thursday")&& item.isOpen()){
                        FormUtils.setTime(ll_thursday_body,item);
                        ll_add_weekdays.performClick();
                        ll_thursday_head.performClick();
                        thursday_group.check(R.id.thursday_open);
                    }
                    if(item.getDayName().equals("Friday")&& item.isOpen()){
                        FormUtils.setTime(ll_friday_body,item);
                        ll_add_weekdays.performClick();
                        ll_friday_head.performClick();
                        friday_group.check(R.id.friday_open);

                    } if(item.getDayName().equals("Saturday")&& item.isOpen()){
                        FormUtils.setTime(ll_saturday_body,item);
                        ll_add_weekdays.performClick();
                        ll_saturday_head.performClick();
                        saturday_group.check(R.id.saturday_open);
                    }
                    if(item.getDayName().equals("Sunday")&& item.isOpen()){
                        FormUtils.setTime(ll_sunday_body,item);
                        ll_add_weekdays.performClick();
                        ll_sunday_head.performClick();
                        saturday_group.check(R.id.sunday_open);
                    }

                }

            }
            if(storeModel.getDeliveryCost()!=null){
                if(storeModel.getDeliveryCost().equals("Free")){
                    radio_delivery_cost_free.performClick();

                }else{
                    radio_delivery_cost.performClick();
                    ET_delivery_cost.setText(storeModel.getDeliveryCost());
                }
            }
            if(storeModel.getDiscountCode()!=null){
                ET_coupon.setText(storeModel.getDiscountCode());

            }
            if(storeModel.getTaxNumber()!=null){
                ET_tax_number.setText(storeModel.getTaxNumber());

            }


        }

    }
    public void saveStore(){
        String json = PrefsUtils.getStore();
        model = new Gson().fromJson(json, StoreModel.class);
        model.setStoreMinOrder(ET_min_order.getText().toString());
        model.setCurrency(Currency);
        if(radio_delivery_cost_free.isChecked()){
            model.setDeliveryCost("Free");

        }if(radio_delivery_cost.isChecked()){
            model.setDeliveryCost(ET_delivery_cost.getText().toString());

        }
        if(isMondayShow&& monday_group.getCheckedRadioButtonId()==R.id.monday_open){
            weekdays.add(FormUtils.getTime(ll_monday_body,"Monday",true));
        }
        if(isTuesdayShown&& tuesday_group.getCheckedRadioButtonId()==R.id.tuesday_open){
            weekdays.add(FormUtils.getTime(ll_tuesday_body,"Tuesday",true));
        }
        if(isWednesdayShown&& wednesday_group.getCheckedRadioButtonId()==R.id.wednesday_open){
            weekdays.add(FormUtils.getTime(ll_wednesday_body,"Wednesday",true));
        }
        if(isThursdayShown&& thursday_group.getCheckedRadioButtonId()==R.id.thursday_open){
            weekdays.add(FormUtils.getTime(ll_thursday_body,"Thursday",true));
        }
        if(isFridayShown&& friday_group.getCheckedRadioButtonId()==R.id.friday_open){
            weekdays.add(FormUtils.getTime(ll_friday_body,"Friday",true));
        }
        if(isSaturdaydayShown&& saturday_group.getCheckedRadioButtonId()==R.id.saturday_open){
            weekdays.add(FormUtils.getTime(ll_saturday_body,"Saturday",true));
        }
        if(isSaturdaydayShown&& sunday_group.getCheckedRadioButtonId()==R.id.sunday_open){
            weekdays.add(FormUtils.getTime(ll_sunday_body,"Monday",true));
        }
        model.setWeekdays(weekdays);
        model.setDiscountPercent(ET_coupon.getText().toString());
        model.setDiscountCode(ET_coupon.getText().toString());
        model.setTaxNumber(FormUtils.getText(ET_tax_number));
        model.setLicence(FormUtils.getText(ET_attach));
        PrefsUtils.SaveStore(new Gson().toJson(model));
        uploadImage(model);

    }
    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
    }
    private void uploadImage(StoreModel model) {

        if(model.getStoreImage() != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Creating Store...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(Uri.parse(model.getStoreImage()))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("setting up the things...");
                            model.setStoreImage(ref.getDownloadUrl().toString());
                            StorageReference ref1 = storageReference.child("files/"+ UUID.randomUUID().toString());
                            if(model.getLicence().isEmpty()){
                                database.getReference("new_stores").child(auth.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        CreateStoreMainFragment.stepThreeComplete();
                                        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getContext());
                                        alert.setTitle("Success");
                                        alert.setMessage("Store created");
                                        alert.setPositiveButton(R.string.ok, null);
                                        alert.show();
                                    }
                                });

                            }else{
                                ref1.putFile(Uri.parse(model.getLicence())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        progressDialog.setTitle("finalizing...");

                                        model.setLicence(ref1.getDownloadUrl().toString());
                                        database.getReference("new_stores").child(auth.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                CreateStoreMainFragment.stepThreeComplete();
                                                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getContext());
                                                alert.setTitle("Success");
                                                alert.setMessage("Store created");
                                                alert.setPositiveButton(R.string.ok, null);
                                                alert.show();
                                            }
                                        });

                                    }
                                });
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            // progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }
}
