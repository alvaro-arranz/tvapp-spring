package com.devq3.applications.tvapp.services;

import com.devq3.applications.tvapp.repository.TvShowRepository;
import com.devq3.applications.tvapp.resources.TvShow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alvaro on 11/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TvShowServiceTest {

    @InjectMocks
    private TvShowService tvShowService;

    @Mock
    private TvShowRepository tvShowRepository;

    @Mock
    private TvShow tvShow;

    @Mock
    private TvShow savedTvShow;

    @Before
    public void setUp() {

        when(tvShowRepository.save(any(TvShow.class))).thenReturn(savedTvShow);
    }

    @Test
    public void save_correct_tvshow() {

        TvShow returnedTvShow = tvShowService.add(tvShow);

        Assert.assertEquals("Wrong returned tvshow", savedTvShow, returnedTvShow);
        verify(tvShowRepository).save(tvShow);
    }

}