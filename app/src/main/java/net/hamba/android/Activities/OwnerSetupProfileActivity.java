package net.hamba.android.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import net.hamba.android.Customes.CountryCodePicker;
import net.hamba.android.Models.OwnerModel;
import net.hamba.android.R;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OwnerSetupProfileActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener {
    private LinearLayout form;
    private Button btn_save;
    private TextInputLayout TI_dob;
    private TextInputLayout TI_city;
    private TextInputLayout TI_zipcode;
    private EditText ET_dob,ET_email,ET_fullname,ET_phone,ET_city,ET_zipcode,id_or_passport;
    private Spinner spn_country,spn_nationality,spn_id_or_pass;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private  OwnerModel model;
    private RadioGroup gender;
    private TextView gender_txt,date_error;
    private CountryCodePicker country,nationality;
    private LazyDatePicker datePicker;
    private LinearLayout LL_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_setup_profile);

        InitUI();
        setData();
    }

    private void InitUI() {
        form            = findViewById(R.id.form);
        btn_save        = findViewById(R.id.btn_save);
        TI_dob          = findViewById(R.id.TI_dob);
        ET_fullname     = findViewById(R.id.ET_fullname);
        datePicker      = findViewById(R.id.lazyDatePicker);
        LL_date         = findViewById(R.id.LL_date);
        date_error      = findViewById(R.id.date_error);
        country         = findViewById(R.id.country);
        nationality     = findViewById(R.id.nationality);
        ET_phone        = findViewById(R.id.ET_phone);
        ET_city         = findViewById(R.id.ET_city);
        ET_zipcode      = findViewById(R.id.ET_zipcode);
        id_or_passport  = findViewById(R.id.id_or_passport);
        TI_city         = findViewById(R.id.TI_city);
        TI_zipcode      = findViewById(R.id.TI_zipcode);
        ET_dob          = findViewById(R.id.ET_dob);
        ET_email        = findViewById(R.id.ET_email);
        spn_country     = findViewById(R.id.spn_country);
        spn_nationality = findViewById(R.id.spn_nationality);
        spn_id_or_pass  = findViewById(R.id.spn_id_or_pass);
        database        =  FirebaseDatabase.getInstance();
        auth            = FirebaseAuth.getInstance();
        gender = findViewById(R.id.gender);
        gender_txt = findViewById(R.id.gender_text);
        gender.setOnCheckedChangeListener(this);
        btn_save.setOnClickListener(this);
        ET_dob.setOnClickListener(this);
        datePicker.setDateFormat(LazyDatePicker.DateFormat.DD_MM_YYYY);
        datePicker.setMaxDate(Calendar.getInstance().getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                if(FormUtils.isValidTextInputParent(form)){
                    showProgressDialog();
                    setProgressDialogMessage("Updating Profile...");
                    model.setFullname(FormUtils.getText(ET_fullname));
                    model.setEmail(FormUtils.getText(ET_email));
                    model.setPhone(FormUtils.getText(ET_phone));
                    model.setNationality(nationality.getSelectedCountryName());
                    model.setCountryName(country.getSelectedCountryName());
                    if(spn_id_or_pass.getSelectedItemPosition()==0){
                        model.setNationalID(FormUtils.getText(id_or_passport));
                    }else{
                        model.setPassportID(FormUtils.getText(id_or_passport));
                    }
                    model.setZipcode(FormUtils.getText(ET_zipcode));
                    model.setCityName(FormUtils.getText(ET_city));
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(datePicker.getDate().getTime());
                    model.setDob(formattedDate);
                    model.setProfilePercent("40% Complete");
                    database.getReference("users/shop/employeer").child(auth.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            setProgressDialogMessage("Finalizing...");
                            if(task.isSuccessful()) {
                                PrefsUtils.saveFullUser(true);
                                startActivity(new Intent(OwnerSetupProfileActivity.this,OwnerHomeActivity.class));
                                finish();
                            }
                        }
                    });

                }else{
                    if(TI_dob.getEditText().getText().toString().isEmpty()){
                        TI_dob.setErrorEnabled(true);
                        TI_dob.setError("Required");

                    }else{
                        TI_dob.setErrorEnabled(false);
                    }
                    if(TI_city.getEditText().getText().toString().isEmpty()){
                        TI_city.setErrorEnabled(true);
                        TI_city.setError("Required");

                    }else{
                        TI_city.setErrorEnabled(false);
                    }
                    if(TI_zipcode.getEditText().getText().toString().isEmpty()){
                        TI_zipcode.setErrorEnabled(true);
                        TI_zipcode.setError("Required");

                    }else{
                        TI_zipcode.setErrorEnabled(false);
                    }
                    if(datePicker.getDate()==null){
                        LL_date.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                        date_error.setVisibility(View.VISIBLE);

                    }else{
                        LL_date.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out));
                        date_error.setVisibility(View.GONE);
                    }
                    
                }
                break;
            case R.id.ET_dob:
                showDateRangePicker(v);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDateRangePicker(View v) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, this, year, month, day);
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TI_dob.getEditText().setText(dayOfMonth+"/"+month+"/"+year);

    }

    public void setData(){
        database.getReference("users/shop/employeer").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    model = dataSnapshot.getValue(OwnerModel.class);
                    Log.e( "onDataChange: ",dataSnapshot.getValue().toString() );
                    TI_city.getEditText().setText(model.getCityName());
                    ET_email.setText(model.getEmail());
                    model.setGender("1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.male:
                gender_txt.setText("Male");
                model.setGender("1");
                break;
            case R.id.female:
                gender_txt.setText("Female");
                model.setGender("0");
                break;
        }
    }
}
