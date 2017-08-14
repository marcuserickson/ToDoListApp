package com.thundergodsoftware.todolistapp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Marcus on 1/3/2017.
 */

public class Category {

    public long id;
    public String name = "";

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.id = -1;
        this.name = name;
    }
}
