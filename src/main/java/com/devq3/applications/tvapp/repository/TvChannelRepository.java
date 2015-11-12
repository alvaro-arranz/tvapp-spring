package com.devq3.applications.tvapp.repository;

import com.devq3.applications.tvapp.resources.TvChannel;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by alvaro on 11/11/15.
 */
public interface TvChannelRepository extends PagingAndSortingRepository<TvChannel, String> {

    TvChannel findById(String id);

}
