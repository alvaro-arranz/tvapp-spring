package com.devq3.applications.tvapp.resources;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Created by alvaro on 10/11/15.
 */
public class ScheduledShow {

    @Id
    private String id;

    @NotNull
    private ZonedDateTime begin;

    @NotNull
    private ZonedDateTime end;

    @NotNull
    @DBRef
    private TvChannel channel;

    @NotNull
    @DBRef
    private TvShow tvShow;
}
