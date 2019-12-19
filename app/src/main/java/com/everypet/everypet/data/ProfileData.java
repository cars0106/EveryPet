package com.everypet.everypet.data;

import android.net.Uri;

import io.realm.RealmObject;

public class ProfileData extends RealmObject {
    public String profileName;
    public String profileBirthday;
    public String profileGender;
    public double profileWeight;
    public double profileHeight;
    public String profileSickList;
    public String profileWatchOut;
    public Uri profileImageUri;
    public String profileType;
}
