package net.hamba.android.Utils;

import android.location.Location;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String getUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;
        
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static String getConversationId(String firstUserId, String secondUserId) {
        return firstUserId + "_" + secondUserId;
    }

    /**
     * Get conversation id.
     * @param secondUserId
     * @return
     */
    public static String getConversationId(String secondUserId) {
        return getUserId().compareTo(secondUserId) > 0 ?
                getUserId() + "_" + secondUserId : secondUserId + "_" + getUserId();
    }

    /**
     * Update achievement!
     * @param achievementType
     * @param context
     */
//    public static void updateAchievements(String achievementType, Context context) {
//        String myId = FirebaseUtils.getUserId();
//        Map<String, Object> upgradeData = new HashMap<>();
//        String childKey = "1";
//        switch (achievementType) {
//            case AppConfig.Achievement.ACHIEVEMENT_START_CONVERSATION:
//                upgradeData.put("type", AppConfig.Achievement.ACHIEVEMENT_START_CONVERSATION);
//                upgradeData.put("name", context.getString(R.string.start_conversation));
//                upgradeData.put("value", 25);
//                childKey = "1";
//                break;
//            case AppConfig.Achievement.ACHIEVEMENT_USE_APP_TWO_WEEKS:
//                upgradeData.put("type", AppConfig.Achievement.ACHIEVEMENT_USE_APP_TWO_WEEKS);
//                upgradeData.put("name", context.getString(R.string.use_the_app));
//                upgradeData.put("value", 25);
//                childKey = "2";
//                break;
//            case AppConfig.Achievement.ACHIEVEMENT_IMPORT_CONTACTS:
//                upgradeData.put("type", AppConfig.Achievement.ACHIEVEMENT_IMPORT_CONTACTS);
//                upgradeData.put("name", context.getString(R.string.import_your_contacts));
//                upgradeData.put("value", 25);
//                childKey = "3";
//                break;
//        }
//
//        upgradeData.put("timestamp", System.currentTimeMillis());
//
//        FirebaseDatabase.getInstance().getReference().child("achievements")
//                .child(myId).child(childKey).updateChildren(upgradeData);
//
//    }

    /**
     * Send Notification
     * @param userId Receiver Id
     * @param notificationType the kind of notification
     */
//    public static void sendNotification(String userId, int notificationType) {
//        User mine = BaseDataManager.getInstance().getUser();
//        Map<String, Object> notification = new HashMap<>();
//        notification.put("userId", mine.key);
//        notification.put("timestamp", ServerValue.TIMESTAMP);
//        notification.put("isRead", Boolean.FALSE);
//        notification.put("notificationType", notificationType);
//
//        FirebaseUtils.getDatabaseReference().child("notifications").child(userId).push().setValue(notification);
//    }

    /**
     * Get geo fire instance.
     * @return GeoFire
     */
    public static GeoFire getGeoFire() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("positions");
        GeoFire geoFire = new GeoFire(ref);

        return geoFire;
    }

    /**
     * Get GeoLocation instance from current location.
     * @return GeoLocation
     */
    public static GeoLocation getGeoLocation(Location location) {
        return new GeoLocation(location.getLatitude(), location.getLongitude());
    }

    public static String HandelException(String errorCode){
        String error = "";

        switch (errorCode) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                error =   "The custom token format is incorrect. Please check the documentation.";
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
              error =  "The custom token corresponds to a different audience.";
                break;

            case "ERROR_INVALID_CREDENTIAL":
                error = "The supplied auth credential is malformed or has expired.";
                break;

            case "ERROR_INVALID_EMAIL":
               error = "The email address is badly formatted.";
                break;

            case "ERROR_WRONG_PASSWORD":
                error = "The password is invalid or the user does not have a password.";
                break;

            case "ERROR_USER_MISMATCH":
                error = "The supplied credentials do not correspond to the previously signed in user.";
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                error = "This operation is sensitive and requires recent authentication. Log in again before retrying this request.";
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                error =  "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.";
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                error = "The email address is already registered";
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                error = "This credential is already associated with a different user account.";
                break;

            case "ERROR_USER_DISABLED":
               error = "The user account has been disabled by an administrator.";
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                error = "The user\\'s credential is no longer valid. The user must sign in again.";
                break;

            case "ERROR_USER_NOT_FOUND":
               error = "There is no user record corresponding to this identifier. The user may have been deleted.";
                break;

            case "ERROR_INVALID_USER_TOKEN":
                error =  "The user\\'s credential is no longer valid. The user must sign in again.";
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                error =  "This operation is not allowed. You must enable this service in the console.";
                break;

            case "ERROR_WEAK_PASSWORD":
                error =  "The given password is invalid.";
                break;

        }
        return  error;
    }

}
