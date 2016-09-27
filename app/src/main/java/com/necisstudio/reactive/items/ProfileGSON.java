package com.necisstudio.reactive.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vim on 27/09/16.
 */

public class ProfileGSON {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
