package com.devq3.applications.tvapp.services;

import com.devq3.applications.tvapp.exceptions.TvChannelNotFoundException;
import com.devq3.applications.tvapp.repository.TvChannelRepository;
import com.devq3.applications.tvapp.resources.TvChannel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.security.RunAs;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alvaro on 11/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TvChannelServiceTest {

    @InjectMocks
    private TvChannelService tvChannelService;

    @Mock
    private TvChannelRepository tvChannelRepository;

    @Mock
    private TvChannel tvChannel;

    private String tvChannelId = "tvChannelTestId";

    @Mock
    private TvChannel savedChannel;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<TvChannel> channelsPage;

    @Before
    public void setUp() throws Exception {

        when(tvChannel.getId()).thenReturn(tvChannelId);

        when(tvChannelRepository.save(any(TvChannel.class))).thenReturn(savedChannel);

        when(tvChannelRepository.exists(tvChannelId)).thenReturn(true);
        when(tvChannelRepository.exists(not(eq(tvChannelId)))).thenReturn(false);

        when(tvChannelRepository.findById(tvChannelId)).thenReturn(tvChannel);
        when(tvChannelRepository.findById(not(eq(tvChannelId)))).thenReturn(null);

        when(tvChannelRepository.findAll(pageable)).thenReturn(channelsPage);
    }

    @Test
    public void save_channel() {

        TvChannel returnedTvChannel = tvChannelService.add(tvChannel);

        Assert.assertEquals("Returned channel was different from the one returned from the repository", savedChannel, returnedTvChannel);
        verify(tvChannelRepository).save(tvChannel);
    }

    @Test
    public void delete_channel_correct_id() throws TvChannelNotFoundException {

        tvChannelService.delete(tvChannel);

        verify(tvChannelRepository).delete(tvChannel);
    }

    @Test(expected = TvChannelNotFoundException.class)
    public void delete_channel_incorrect_id() throws TvChannelNotFoundException {

        when(tvChannel.getId()).thenReturn("incorrect Id");
        tvChannelService.delete(tvChannel);
    }

    @Test
    public void get_channel_correct_id() {

        Optional<TvChannel> returnedTvChannel = tvChannelService.findById(tvChannelId);

        Assert.assertTrue("Non existent element returned", returnedTvChannel.isPresent());
        Assert.assertEquals("Returned channel was different from the one returned from the repository", tvChannel, returnedTvChannel.get());
        verify(tvChannelRepository).findById(tvChannelId);
    }

    @Test
    public void get_channel_incorrect_id() {

        Optional<TvChannel> returnedTvChannel = tvChannelService.findById("incorrect tvshow id");

        Assert.assertFalse("Existent element returned", returnedTvChannel.isPresent());
    }

    @Test
    public void modify_channel_correct_id() throws TvChannelNotFoundException {

        tvChannelService.modify(tvChannel);

        verify(tvChannelRepository).exists(tvChannel.getId());
        verify(tvChannelRepository).save(tvChannel);
    }

    @Test(expected = TvChannelNotFoundException.class)
    public void modify_channel_incorrect_id() throws TvChannelNotFoundException {

        when(tvChannel.getId()).thenReturn("incorrect channel id");

        tvChannelService.modify(tvChannel);
    }

    @Test
    public void get_all_channels_with_pagination() {

        Page<TvChannel> returnedPage = tvChannelService.getAll(pageable);

        verify(tvChannelRepository).findAll(pageable);
        Assert.assertEquals("Wrong returned page", channelsPage, returnedPage);
    }

    @After
    public void tearDown() throws Exception {

    }
}