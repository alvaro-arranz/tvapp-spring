package com.devq3.applications.tvapp.controllers;

import com.devq3.applications.tvapp.exceptions.TvShowNotFoundException;
import com.devq3.applications.tvapp.resources.TvShow;
import com.devq3.applications.tvapp.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by alvaro on 10/11/15.
 */
@RestController
@RequestMapping("/api/tvshows")
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders createTvShow(@RequestBody @Valid TvShow tvShow) {

        tvShowService.add(tvShow);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(TvShowController.class).slash(tvShow.getId()).toUri());

        return headers;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TvShow getTvShow(@PathVariable String id) throws TvShowNotFoundException {

        Optional<TvShow> tvShowOptional = tvShowService.findTvShowById(id);

        if (!tvShowOptional.isPresent()) {
            throw new TvShowNotFoundException();
        }

        return tvShowOptional.get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public HttpHeaders deleteTvShow(@PathVariable String id) throws TvShowNotFoundException {

        tvShowService.deleteById(id);

        return new HttpHeaders();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public HttpHeaders modifyTvShow(@PathVariable String id, @RequestBody @Valid TvShow tvShow) throws TvShowNotFoundException {

        tvShow.setId(id);
        tvShowService.modify(tvShow);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(TvShowController.class).slash(tvShow.getId()).toUri());

        return headers;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<TvShow> getTvShows(@RequestParam("size") int size, @RequestParam("page") int page) {

        PageRequest pageRequest = new PageRequest(page, size);
        return tvShowService.getAll(pageRequest);
    }

}
