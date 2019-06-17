package net.hamba.android.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionsUtil {
    public static final int PERMISSION_ALL = 1;

    public static boolean doesAppNeedPermissions() {
        return VERSION.SDK_INT > 22;
    }

    public static String[] getPermissions(Context context) throws NameNotFoundException {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
    }

    public static void askPermissions(Activity activity) {
        if (doesAppNeedPermissions()) {
            try {
                String[] permissions = getPermissions(activity);
                if (!checkPermissions(activity, permissions)) {
                    ActivityCompat.requestPermissions(activity, permissions, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkPermissions(Context context, String... permissions) {
        if (!(VERSION.SDK_INT < 23 || context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
