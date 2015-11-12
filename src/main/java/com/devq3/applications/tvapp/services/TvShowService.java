package com.devq3.applications.tvapp.services;

import com.devq3.applications.tvapp.exceptions.TvShowNotFoundException;
import com.devq3.applications.tvapp.repository.TvShowRepository;
import com.devq3.applications.tvapp.resources.TvShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by alvaro on 10/11/15.
 */
@Service
public class TvShowService {

    private TvShowRepository tvShowRepository;

    @Autowired
    public TvShowService(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public TvShow add(TvShow tvShow) {
        return tvShowRepository.save(tvShow);
    }

    public Optional<TvShow> findTvShowById(String id) {

        TvShow tvShow = tvShowRepository.findById(id);

        if (tvShow != null) {
            return Optional.of(tvShow);
        }

        return Optional.empty();
    }

    public void delete(TvShow tvShow) throws TvShowNotFoundException {

        checkShowIdExists(tvShow.getId());

        tvShowRepository.delete(tvShow);
    }

    public void deleteById(String id) throws TvShowNotFoundException {

        checkShowIdExists(id);

        tvShowRepository.delete(id);
    }

    public void modify(TvShow tvShow) throws TvShowNotFoundException {

        checkShowIdExists(tvShow.getId());

        tvShowRepository.save(tvShow);
    }

    public Page<TvShow> getAll(Pageable pageable) {

        return tvShowRepository.findAll(pageable);
    }

    private void checkShowIdExists(String id) throws TvShowNotFoundException {

        if (!tvShowRepository.exists(id)) {
            throw new TvShowNotFoundException();
        }
    }
}
