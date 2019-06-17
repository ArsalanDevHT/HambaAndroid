package net.hamba.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.hamba.android.Models.IndividualModel;
import net.hamba.android.R;
import net.hamba.android.Utils.PrefsUtils;

public class ShopCustomerActivity extends BaseActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    IndividualModel user;
    @Override
    protected void onStart() {
        PrefsUtils.setContext(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        if (mAuth.getCurrentUser() != null) {
            showProgressDialog();
            setProgressDialogMessage("Getting things ready!");
            mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        database.getReference("users/user_types").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Log.e( "onDataChange: ",dataSnapshot.getValue().toString() );
                                    PrefsUtils.setLoggedinUserType(dataSnapshot.getValue(Integer.class));

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        if (PrefsUtils.getLoggedinUserType() == 1) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                if(PrefsUtils.isFullUser()){
                                    startActivity(new Intent(ShopCustomerActivity.this, OwnerHomeActivity.class));
                                    finish();
                                }else{
                                    startActivity(new Intent(ShopCustomerActivity.this, OwnerSetupProfileActivity.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(ShopCustomerActivity.this, OwnerAuthEmailActivity.class));
                                finish();
                            }
                        }
                        if (PrefsUtils.getLoggedinUserType() == 2) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {

                                if(PrefsUtils.isFullUser()){
                                    startActivity(new Intent(ShopCustomerActivity.this, IndividualHomeActivity.class));
                                    finish();
                                }else{
                                    startActivity(new Intent(ShopCustomerActivity.this, EmployeeSetupProfileActivity.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(ShopCustomerActivity.this, OwnerAuthEmailActivity.class));
                                finish();
                            }
                        }

                        if (PrefsUtils.getLoggedinUserType() == 0) {
                            database.getReference("users").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.e( "onDataChange: ", mAuth.getUid());
                                    if(dataSnapshot.exists()){
                                        user =   dataSnapshot.getValue(IndividualModel.class);
                                        if(user.username == null||user.username.equals("")){

                                            startActivity(new Intent(ShopCustomerActivity.this, UserNameActivity.class));
                                            finish();
                                        }
                                        else if(user.interests==null||user.interests.equals("")){
                                            startActivity(new Intent(ShopCustomerActivity.this, InterestSetActivity.class));
                                            finish();
                                        }
                                        else if(user.fullname==null||user.fullname.equals("")){
                                            startActivity(new Intent(ShopCustomerActivity.this, SetupProfileActivity.class));
                                            finish();
                                        }else{

                                            startActivity(new Intent(ShopCustomerActivity.this, IndividualHomeActivity.class));
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }
                        if (PrefsUtils.getLoggedinUserType() == 3) {
                            if (!PrefsUtils.hasUserCompletedRegistration()) {
                                startActivity(new Intent(ShopCustomerActivity.this, UserNameActivity.class));
                            } else {
                                startActivity(new Intent(ShopCustomerActivity.this, IndividualHomeActivity.class));

                            }
                            finish();

                        }

                    }
                }
            });
        }//


        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_customer);
    }
    public void gotoOwnerEmployeeActivity(View v){
        startActivity(new Intent(ShopCustomerActivity.this,SignUpAsActivity.class));

    }
    public void gotoIndividualActivity(View v){
       // Toast.makeText(this, "Button Disable", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ShopCustomerActivity.this,LoginActivity.class));

    }
    public void loginAsGuest(View v){
//        showProgressDialog();
  //      setProgressDialogMessage("Authenticating...");

        startActivity(new Intent(ShopCustomerActivity.this,UserNameActivity.class));
        finish();

       /* mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setProgressDialogMessage("Getting things ready...");

                        if (task.isSuccessful()) {
                            hideProgressDialog();
                            FirebaseUser user = mAuth.getCurrentUser();
                            PrefsUtils.setLoggedinUserType(3);
                            startActivity(new Intent(ShopCustomerActivity.this,UserNameActivity.class));
                            finish();
                        } else {
                            setProgressDialogMessage("Authentication failed...");
                            hideProgressDialog();


                        }

                    }
                });
*/
    }
}
