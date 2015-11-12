package com.devq3.applications.tvapp.resources;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by alvaro on 29/10/15.
 */
public class TvShow {

    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private String imageUrl;
    private String showType;
    private Collection<String> categories;


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public Collection<String> getCategories() {
        return categories;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShowType() {
        return showType;
    }

}
