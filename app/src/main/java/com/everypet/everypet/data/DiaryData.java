package com.everypet.everypet.data;

import io.realm.RealmObject;

public class DiaryData extends RealmObject {
    public String petName;
    public String diaryTitle;
    public String diaryContent;
    public String diaryDate;
    public String diaryUri;
}
