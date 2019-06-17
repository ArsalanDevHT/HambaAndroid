package net.hamba.android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.CountryCodePicker;

import net.hamba.android.Instagram.AuthenticationDialog;
import net.hamba.android.Instagram.AuthenticationListener;
import net.hamba.android.Instagram.InstagramApp;
import net.hamba.android.Instagram.RequestInstagramAPI;
import net.hamba.android.Models.DiscoveryRules;
import net.hamba.android.Models.IndividualModel;
import net.hamba.android.Models.Social;
import net.hamba.android.Models.User;
import net.hamba.android.R;
import net.hamba.android.Utils.FirebaseUtils;
import net.hamba.android.Utils.PrefsUtils;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class LoginActivity extends BaseActivity implements View.OnClickListener,AuthenticationListener {

    //UI Declarations
    private EditText editTextPhone;
    private CountryCodePicker countryCodePicker;
    private Button register_btn;
    private Button verify_btn;
    private ScrollView login_view;
    private LinearLayout verify_view;
    private EditText code;
    private LinearLayout btn_fb;
    private LinearLayout btn_gmail;
    private LinearLayout btn_insta;
    private LinearLayout resend_otp;
    private static final String TAG = "VerifyPhoneActivity";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private static final int SOCIAL_FACEBOOK = 1;
    private static final int SOCIAL_TWITTER = 2;
    private static final int SOCIAL_GOOGLE = 3;

    private static final int RC_SIGN_IN = 1001;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private String otpCode;
    private boolean mVerificationInProgress = false;
    private CallbackManager mCallbackManager = null;
    //    TwitterAuthClient mTwitterAuthClient;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount mGoogleSignInAccount;

    String mCountryName;
    String mCountryCode;
    String mPhoneNumber;
    Boolean mIsHaveAccount = false;
    InstagramApp mApp = null;
    private FirebaseDatabase database;

    private Button btnConnect, btnViewInfo, btnGetAllImages;
    private LinearLayout llAfterLoginView;
    private HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == InstagramApp.WHAT_FINALIZE) {
                //  userInfoHashmap = mApp.getUserInfo();
            } else if (msg.what == InstagramApp.WHAT_FINALIZE) {
                Toast.makeText(LoginActivity.this, "Check your network.",
                        Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(editTextPhone.getText().toString());
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId("303791546968758");
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        InitUI();
        mApp = new InstagramApp(this, "",
                "", "");
        mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {
                // tvSummary.setText("Connected as " + mApp.getUserName());
//                btnConnect.setText("Disconnect");
//                llAfterLoginView.setVisibility(View.VISIBLE);
                // userInfoHashmap = mApp.
                // mApp.fetchUserName(handler);
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        PrefsUtils.setContext(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mAuth.useAppLanguage();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.e( "onVerificationComp:" , "here");

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.e("onVerificationFailed", e.getMessage());

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    editTextPhone.setError("Enter a number");
                    hideProgressDialog();


                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(getApplicationContext(),"quota "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token) {
                setProgressDialogMessage("Send verification code...");


                Log.e( "onCodeSent: ","adsfads" );

                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(STATE_CODE_SENT);

            }
        };
    }

    private void  InitUI(){
        editTextPhone       = findViewById(R.id.phone_no);
        countryCodePicker   = findViewById(R.id.ccp);
        register_btn        = findViewById(R.id.register);
        login_view          = findViewById(R.id.login_view);
        verify_view         = findViewById(R.id.verify_view);
        verify_btn          = findViewById(R.id.verify_btn);
        code                = findViewById(R.id.code);
        btn_fb              = findViewById(R.id.btn_fb);
        btn_gmail           = findViewById(R.id.btn_gmail);
        resend_otp           = findViewById(R.id.resend_otp);
        btn_insta           = findViewById(R.id.btn_insta);
        register_btn.setOnClickListener(this);
        btn_fb.setOnClickListener(this);
        btn_insta.setOnClickListener(this);
        btn_gmail.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        resend_otp.setOnClickListener(this);
        countryCodePicker.registerPhoneNumberTextView(editTextPhone);


    }

    private boolean validatePhoneNumber() {
        String phoneNumber = editTextPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 7) {
            editTextPhone.setError("Enter a number");

            return false;
        }

        return true;
    }

    /**
     * Start Phone Number verification.
     * @param phoneNumber
     */
    private void startPhoneNumberVerification(String phoneNumber) {
        mPhoneNumber = countryCodePicker.getFullNumberWithPlus();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        mVerificationInProgress = true;

    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            updateUI(STATE_SIGNIN_SUCCESS, user);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, ":failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                code.setError("Invalid code.");
                                hideProgressDialog();
                            }
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // The verification code entered was invalid
                                Log.e("onComplete: ",task.getException().getMessage() );
                                code.setError("Invalid code.");
                                hideProgressDialog();

                            }

                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                if(validatePhoneNumber()){
                    showProgressDialog();
                    setProgressDialogMessage("Verifying number...");

                    Log.e("onClick: ",countryCodePicker.getFullNumberWithPlus() );
                   // startActivity(new Intent(LoginActivity.this,UserNameActivity.class));
                    startPhoneNumberVerification(countryCodePicker.getFullNumberWithPlus());
                }
                break;
            case R.id.verify_btn:
                if(!code.getText().toString().isEmpty()&& !(code.getText().toString().length()<6)){
                    String codee = code.getText().toString();
                    String verificationC = mVerificationId;
                    showProgressDialog();
                    setProgressDialogMessage("Authenticating code...");
                    verifyPhoneNumberWithCode(mVerificationId,code.getText().toString());


                }else{
                    code.setError("please enter 6 digit code");

                }
                break;
            case R.id.btn_fb:
              //  signUpWithFacebook();
                Toast.makeText(this, "Social Media Login not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_gmail:
                signUpWithGoogle();
//                Toast.makeText(this, "Social Media Login not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_insta:
               // ReguisterWithInsta();
                Toast.makeText(this, "Social Media Login not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.resend_otp:
                showProgressDialog();
                resendVerificationCode(countryCodePicker.getFullNumberWithPlus(),mResendToken);
                break;



        }
    }
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        setProgressDialogMessage("requesting...");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Log.e(TAG, "verifyPhoneNumberWithCode: "+verificationId );
        Log.e(TAG, "verifyPhoneNumberWithCode: "+code );
       signInWithPhoneAuthCredential(credential);
    }

    /**
     * Update UI
     * @param uiState
     */
    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }
    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                login_view.setVisibility(View.GONE);
                verify_view.setVisibility(View.VISIBLE);
                hideProgressDialog();
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                // Set the verification text based on the credential
                // gotoNextPage();

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check

                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                gotoNextPage();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                // Google Sign In was successful, authenticate with Firebase
                mGoogleSignInAccount = task.getResult(ApiException.class);
                saveUserSocialAccount(SOCIAL_GOOGLE);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else {
            // facebook related handling
            mCallbackManager.onActivityResult(requestCode, responseCode, intent);

        }

    }
    private void gotoNextPage() {
        PrefsUtils.savePhoneNumber(countryCodePicker.getFullNumber());
        Log.e(TAG, "gotoNextPage: "+mIsHaveAccount );
        if (!mIsHaveAccount) {
           /* FirebaseDatabase.getInstance().getReference().child("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String userId = FirebaseUtils.getUserId();
                            if (snapshot.hasChild(userId)) {

                                PrefsUtils.userCompletedRegistered(true);
                                database.getReference("users/user_types").child(mAuth.getUid()).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideProgressDialog();
                                        PrefsUtils.setLoggedinUserType(0);
                                        startActivity(new Intent(LoginActivity.this,UserNameActivity.class));

                                    }
                                });


                            } else{
                                database.getReference("users/user_types").child(mAuth.getUid()).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideProgressDialog();
                                        PrefsUtils.setLoggedinUserType(0);
                                        startActivity(new Intent(LoginActivity.this,UserNameActivity.class));

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Can't get data!", Toast.LENGTH_LONG).show();
                        }
                    });*/
        }
        else{
            database.getReference("users/user_types").child(mAuth.getUid()).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideProgressDialog();
                    PrefsUtils.setLoggedinUserType(0);
                    startActivity(new Intent(LoginActivity.this,UserNameActivity.class));

                }
            });

        }
         //   SetupProfileActivity.start(LoginActivity.this, mCountryCode);
    }


    private void signUpWithFacebook() {
        if (mCallbackManager == null) {
            mCallbackManager = CallbackManager.Factory.create();


            LoginManager.getInstance().registerCallback(mCallbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            Log.d(TAG, "facebook:onSuccess:" + loginResult);
                            handleFacebookAccessToken(loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Log.e("onError: ",exception.getLocalizedMessage() );

                            Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }
    private void handleFacebookAccessToken(AccessToken token) {
        showProgressDialog();
        setProgressDialogMessage("Authenticating...");
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        //  ll_loader.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            gotoNextPageWithSocial(SOCIAL_FACEBOOK);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
    }

    private void saveFacebookUserInfo() {
        setProgressDialogMessage("Getting profile info...");
        Bundle params = new Bundle();
        params.putString("fields", "id, email, name, gender, birthday, picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            User user = getNewUserForSocial();
                            Log.e( "saveFacebookUserInfo: ","dafasfas" );

                            try {
                                String photoUrl = "";
                                JSONObject data = response.getJSONObject();
                                Log.e( "saveFacebookUserInfo: ",data.toString() );

                                if (data.has("picture"))
                                    photoUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                                if(data.has("gender")) {
                                    if (data.getString("gender").equals("male"))
                                        user.gender = 0;
                                    else
                                        user.gender = 1;
                                }
                                user.logInSocialWith = "facebook";
                                user.userProfile.username = data.getString("name");
                                user.userProfile.photoUrl = photoUrl;
                                user.userProfile.avatarUrl = photoUrl;
                                user.emailAddress = data.getString("email");

                                //Save user
                                IndividualModel model = new IndividualModel();
                                model.setIsfullUser(false);
                                model.setUsername(data.getString("name"));
                                model.setEmail(data.getString("email"));
                               // saveUserData(user);
                                SaveUserNew(model);
                                Log.e( "saveFacebookUserInfo: ","dafasfas" );

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
    }

    private User getNewUserForSocial() {
        User user = new User();

        user.userProfile.isValid = true;
        user.userProfile.deletedTimestamp = 0;
        user.userProfile.username = "";

        user.countryCode = mCountryCode;

        user.dateOfBirth = "";
        user.phoneNumber = "";

        user.userProfile.profession = "";
        user.nationality = "";
        user.isUpgraded = false;
        user.isAddedSocial = false;

        user.serviceTitle = "";
        user.serviceDetails = "";

        if (user.discoveryRules == null)
            user.discoveryRules = new DiscoveryRules();

        //Initialize Discovery rules
        user.discoveryRules.discoverNationality = "All";
        user.discoveryRules.discoveryInterest = "All";

        if (user.social == null)
            user.social = new Social();

        user.token = "";
//        ArrayList<String> interestList =
//                new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.interests_list)));
//
//        String interestData = "";
//        for (String interest : interestList) {
//            if (interestData.equals(""))
//                interestData = interest;
//            else
//                interestData = interestData + "/" + interest;
//        }
//
        user.interests = "";
        return user;
    }
    private void saveUserData(User user) {
        setProgressDialogMessage("Saving...");

        FirebaseDatabase.getInstance().getReference().child("users").child(getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    PrefsUtils.userCompletedRegistered(true);
                    hideProgressDialog();
                    startActivity(new Intent(LoginActivity.this,IndividualHomeActivity.class));

                    // MainActivity.start(VerifyPhoneActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), "Saving failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void SaveUserNew(IndividualModel model){
        setProgressDialogMessage("Saving...");
        hideProgressDialog();
        PrefsUtils.setLoggedinUserType(0);
        startActivity(new Intent(LoginActivity.this,InterestSetActivity.class));
        /*FirebaseDatabase.getInstance().getReference().child("users").child(getUid())
                .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    PrefsUtils.userCompletedRegistered(true);
                    database.getReference("users/user_types").child(mAuth.getUid()).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            hideProgressDialog();
                            PrefsUtils.setLoggedinUserType(0);
                            startActivity(new Intent(LoginActivity.this,InterestSetActivity.class));

                        }
                    });

                    // MainActivity.start(VerifyPhoneActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), "Saving failed.", Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }
    private void gotoNextPageWithSocial(final int socialIndex) {
        if (!mIsHaveAccount) {
            Log.e("gextPageWithSocial: ", "hjgfjgh");
            FirebaseDatabase.getInstance().getReference().child("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String userId = FirebaseUtils.getUserId();
                            if (snapshot.hasChild(userId)) {
                                PrefsUtils.userCompletedRegistered(true);
                                database.getReference("users/user_types").child(mAuth.getUid()).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideProgressDialog();
                                        PrefsUtils.setLoggedinUserType(0);
                                        startActivity(new Intent(LoginActivity.this,InterestSetActivity.class));

                                    }
                                });
                            } else

                            saveUserSocialAccount(socialIndex);
                            Log.e("gextPageWithSocial: ", "hjgfdssadjgh");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Can't get data!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else
            saveUserSocialAccount(socialIndex);
    }
    private void saveUserSocialAccount(int socialIndex) {
        if (socialIndex == SOCIAL_FACEBOOK){
            saveFacebookUserInfo();
            Log.e( "saveUserSocialAccount: ","dfasdfas" );
        }
        else
            saveUserWithGoogle();
//        else if (socialIndex == SOCIAL_TWITTER)
//          //  saveUserWithTwitter();
//
    }
    private void signUpWithGoogle() {
        showProgressDialog();
        setProgressDialogMessage("Authenticating...");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                /*.requestIdToken("469896327641-rta4rtqniqnmun3j0f8ummmngkjiivch.apps.googleusercontent.com")*/
//                .requestIdToken("527864622520-hlqadqfv5ed36avl0742artu0bnks0ti.apps.googleusercontent.com")
                .requestIdToken("814539570830-4mn2rkauk18e93aaiqi4bv1s72jh9bb1.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void saveUserWithGoogle() {
        if (mGoogleSignInAccount == null)
            return;

        Log.d(TAG, "firebaseAuthWithGoogle:" + mGoogleSignInAccount.getId());
        Log.d(TAG, "username:" +        mGoogleSignInAccount.getDisplayName());

        AuthCredential credential = GoogleAuthProvider.getCredential(mGoogleSignInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            User me = getNewUserForSocial();
                            String photoUrl =  mGoogleSignInAccount.getPhotoUrl().toString();

                            me.gender = 0; //default
                            me.logInSocialWith = "google";
                            me.userProfile.username = mGoogleSignInAccount.getDisplayName();
                            me.userProfile.photoUrl = photoUrl;
                            me.userProfile.avatarUrl = photoUrl;
                            me.emailAddress = mGoogleSignInAccount.getEmail();

                            //Save user
                            IndividualModel model = new IndividualModel();
                            model.setIsfullUser(false);
                            model.setUsername(mGoogleSignInAccount.getDisplayName());
                            model.setEmail(mGoogleSignInAccount.getEmail());
                            // saveUserData(user);
                            SaveUserNew(model);

                           // saveUserData(me);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


    @Override
    public void onTokenReceived(String auth_token) {
        if (auth_token == null)
            return;
        Log.e("onTokenReceived: ",auth_token);
        getUserInfoByAccessToken(auth_token);

    }
    private void getUserInfoByAccessToken(String token) {
        RequestInstagramAPI RIAPI =    new RequestInstagramAPI(LoginActivity.this, token, this);
        RIAPI.execute();

    }

    private void ReguisterWithInsta(){
        AuthenticationDialog authenticationDialog = new AuthenticationDialog(this, this);
        authenticationDialog.setCancelable(true);
        authenticationDialog.show();
    }
    public void OnInstaProfileCompleted(User user){
        Log.e( "OnofileCompleted: ",user.userProfile.photoUrl );
        showProgressDialog();
        setProgressDialogMessage("Authenticating...");
        creatAnonAccount(user);

    }

    private void creatAnonAccount(final User user){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user1 = mAuth.getCurrentUser();
                            saveUserData(user);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
