package com.devq3.applications.tvapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by alvaro on 10/11/15.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TvShowNotFoundException extends Exception {
}
