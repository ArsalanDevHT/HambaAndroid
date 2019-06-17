package net.hamba.android.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserProfile {
    public String username;
    public String photoUrl;
    public String avatarUrl;
    public String profession;
    public boolean isValid;
    public long deletedTimestamp;

    public UserProfile() {
        isValid = true;
        deletedTimestamp = 0;
    }
}
