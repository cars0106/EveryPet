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

    public RecyclerData() { }

    public RecyclerData(String petname, String type, String imageurl) {
        this.petname = petname;
        this.type = type;
        this.imageurl = imageurl;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("petname", petname);
        result.put("type", type);
        result.put("imageurl", imageurl);
        return result;
    }
}
