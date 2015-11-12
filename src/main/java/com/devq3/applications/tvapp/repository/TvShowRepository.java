package com.devq3.applications.tvapp.repository;

import com.devq3.applications.tvapp.resources.TvShow;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by alvaro on 29/10/15.
 */
public interface TvShowRepository extends PagingAndSortingRepository<TvShow, String> {

    TvShow findById(String id);
}
