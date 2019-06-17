package net.hamba.android.Instagram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.hamba.android.Activities.LoginActivity;
import net.hamba.android.Models.DiscoveryRules;
import net.hamba.android.Models.Social;
import net.hamba.android.Models.User;
import net.hamba.android.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RequestInstagramAPI extends AsyncTask<Void, String, String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String token;
    LoginActivity activity;



    public RequestInstagramAPI(Context context, String token, LoginActivity activity) {
        this.context = context;
        this.token = token;
        this.activity = activity;

    }




    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(context.getResources().getString(R.string.get_user_info_url) + token);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        String photoUrl = "";
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonData = jsonObject.getJSONObject("data");
                Log.e( "onPostExecute: ",jsonData.toString() );

                if (jsonData.has("id")) {
                    Log.e( "onPostExecute: ","here2" );
                    User user = getNewUserForSocial();

                    if (jsonData.has("profile_picture"))
                        photoUrl = jsonData.getString("profile_picture");

                    user.gender = 0;
                    user.logInSocialWith = "instagram";
                    user.userProfile.username = jsonData.getString("username");
                    user.userProfile.photoUrl = photoUrl;
                    user.userProfile.avatarUrl = photoUrl;
                    user.emailAddress = "";
                    Log.e( "onPostExecute: ","here2" );
                    //activity.OnInstaProfileCompleted(user);
                    activity.OnInstaProfileCompleted(user);
                    Log.e( "onPostExecute: ","here4" );



                }
            } catch (JSONException e) {
                Log.e( "onPostExecute: ",e.getMessage() );
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"Login error!",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private User getNewUserForSocial() {
        User user = new User();

        user.userProfile.isValid = true;
        user.userProfile.deletedTimestamp = 0;
        user.userProfile.username = "";

        user.countryCode = "";

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
        ArrayList<String> interestList =
                new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.interests_list)));

        String interestData = "";
        for (String interest : interestList) {
            if (interestData.equals(""))
                interestData = interest;
            else
                interestData = interestData + "/" + interest;
        }

        user.interests = interestData;
        return user;
    }




}

