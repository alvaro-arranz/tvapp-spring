package com.devq3.applications.tvapp.resources;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * Created by alvaro on 10/11/15.
 */
public class TvChannel {

    @Id
    private String id;

    @NotNull
    private String name;

    private String imageUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
