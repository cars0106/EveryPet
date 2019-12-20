package com.everypet.everypet.data;

import io.realm.RealmObject;

public class ToDoData extends RealmObject {
    public String toDoPetType;
    public String toDoDate;
    public String toDoWhat;
    public String toDoTime;
    public boolean toDoAlarm;
}
