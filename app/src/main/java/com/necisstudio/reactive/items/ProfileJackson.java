package com.necisstudio.reactive.items;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vim on 27/09/16.
 */

public class ProfileJackson {
    private String name;

    @JsonProperty(value = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
