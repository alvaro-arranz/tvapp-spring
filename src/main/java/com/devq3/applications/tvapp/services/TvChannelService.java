package com.devq3.applications.tvapp.services;

import com.devq3.applications.tvapp.exceptions.TvChannelNotFoundException;
import com.devq3.applications.tvapp.repository.TvChannelRepository;
import com.devq3.applications.tvapp.resources.TvChannel;
import com.devq3.applications.tvapp.resources.TvShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by alvaro on 11/11/15.
 */
@Service
public class TvChannelService {

    private TvChannelRepository tvChannelRepository;

    @Autowired
    public TvChannelService(TvChannelRepository tvChannelRepository) {
        this.tvChannelRepository = tvChannelRepository;
    }

    public TvChannel add(TvChannel channel) {
        return tvChannelRepository.save(channel);
    }

    public Optional<TvChannel> findById(String id) {

        TvChannel channel = tvChannelRepository.findById(id);

        if (channel != null) {
            return Optional.of(channel);
        }

        return Optional.empty();
    }

    public void delete(TvChannel tvChannel) throws TvChannelNotFoundException {

        checkChannelIdExists(tvChannel.getId());

        tvChannelRepository.delete(tvChannel);
    }

    public void modify(TvChannel tvChannel) throws TvChannelNotFoundException {

        checkChannelIdExists(tvChannel.getId());

        tvChannelRepository.save(tvChannel);
    }

    public Page<TvChannel> getAll(Pageable pageable) {

        return tvChannelRepository.findAll(pageable);
    }

    private void checkChannelIdExists(String id) throws TvChannelNotFoundException {

        if (!tvChannelRepository.exists(id)) {
            throw new TvChannelNotFoundException();
        }
    }
}
