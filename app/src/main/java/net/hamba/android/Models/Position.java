package net.hamba.android.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ShineMan on 3/29/2018.
 */

@IgnoreExtraProperties
public class Position {

    public double latitude;
    public double longitude;

    public Position() {
        latitude = 0;
        longitude = 0;
    }
}
