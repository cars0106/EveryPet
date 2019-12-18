package com.everypet.everypet.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class RecyclerData {
    public String petname;
    public String type;
    public String imageurl;
    public String useremail;

    public RecyclerData() { }

    public RecyclerData(String petname, String type, String imageurl, String useremail) {
        this.petname = petname;
        this.type = type;
        this.imageurl = imageurl;
        this.useremail = useremail;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("petname", petname);
        result.put("type", type);
        result.put("imageurl", imageurl);
        result.put("useremail", useremail);
        return result;
    }
}
