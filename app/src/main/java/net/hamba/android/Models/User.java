package net.hamba.android.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {
    @Exclude
    public String key;

    public UserProfile userProfile;

    public int gender = 0;
    public String logInSocialWith;
    public String countryCode;
    public String phoneNumber;
    public String dateOfBirth;
    public String nationality;
    public String emailAddress;
    public String interests;

    //Service
    public String serviceTitle;
    public String serviceDetails;

    //Position
    public Position position;

    //Social
    public Social social;

    //Upgrade
    public boolean isUpgraded = false;
    public boolean isAddedSocial = false;

    //Discoveries rules.
    public DiscoveryRules discoveryRules;

    public Status status;

    public String token;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        userProfile = new UserProfile();
        social = new Social();
        status = new Status();
        discoveryRules = new DiscoveryRules();
    }

}
// [END blog_user_class]