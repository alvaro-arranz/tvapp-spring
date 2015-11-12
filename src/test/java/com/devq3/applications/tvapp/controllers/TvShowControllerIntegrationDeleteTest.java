package com.devq3.applications.tvapp.controllers;

import com.devq3.applications.tvapp.Application;
import com.devq3.applications.tvapp.repository.TvShowRepository;
import com.devq3.applications.tvapp.resources.TvShow;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alvaro on 10/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TvShowControllerIntegrationDeleteTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TvShowRepository tvShowRepository;

    private TvShow tvShow;
    private String description = "test description";
    private String name = "Test name";
    private String showType = "Test show type";

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        tvShow = new TvShow();
        tvShow.setDescription(description);
        tvShow.setName(name);
        tvShow.setShowType(showType);

        tvShowRepository.save(tvShow);
    }

    @Test
    public void delete_existent_tvshow() throws Exception {

        mockMvc.perform(delete("/api/tvshows/" + tvShow.getId())).
                andExpect(status().isOk());

        Assert.assertTrue("Tv show was not removed from the database", tvShowRepository.findById(tvShow.getId()) == null);
    }

    @Test
    public void delete_non_existent_tvshow() throws Exception {

        mockMvc.perform(delete("/api/tvshows/non_existent_id")).andExpect(status().isNotFound());
    }

    @After
    public void tearDown() {

        tvShowRepository.deleteAll();
    }
}
