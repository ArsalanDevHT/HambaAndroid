package net.hamba.android.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DiscoveryRules {
    public boolean isAnonymousMode = false;
    public int discoverGender = 0;
    public int ageRange = 0;
    public int discoveryMode = 0; //Range or Country
    public String countryFilter;
    public int onlineFilter = 0;
    public int serviceFilter = 0;

    public int discoveryRange = 0;
    public String discoverNationality;
    public String discoveryInterest;
    public boolean discoverableMap = true;
    public boolean pushNotification = true;
    public boolean notifications = true;
    public boolean showPhoneNumber = false;
    public boolean canSeeMyAge = false;

    public DiscoveryRules() {
    }
}
