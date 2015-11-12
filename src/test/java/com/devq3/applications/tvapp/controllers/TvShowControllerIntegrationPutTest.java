package com.devq3.applications.tvapp.controllers;

import com.devq3.applications.tvapp.Application;
import com.devq3.applications.tvapp.repository.TvShowRepository;
import com.devq3.applications.tvapp.resources.TvShow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alvaro on 10/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TvShowControllerIntegrationPutTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TvShowRepository tvShowRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private TvShow tvShow;
    private String description = "test description";
    private String name = "Test name";
    private String showType = "Test show type";

    private TvShow newTvShow;
    private String newDescription = "new test description";
    private String newName = "new name";
    private String newShowType = "new show type";

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        tvShow = new TvShow();
        tvShow.setDescription(description);
        tvShow.setName(name);
        tvShow.setShowType(showType);

        tvShowRepository.save(tvShow);

        newTvShow = new TvShow();
        newTvShow.setName(newName);
        newTvShow.setDescription(newDescription);
        newTvShow.setShowType(newShowType);
    }

    @Test
    public void modify_existent_tvshow() throws Exception {

        String newTvShowJson = objectMapper.writeValueAsString(newTvShow);

        mockMvc.perform(put("/api/tvshows/" + tvShow.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTvShowJson))
                .andExpect(status().isOk());

        TvShow modifiedTvShow = tvShowRepository.findById(tvShow.getId());

        Assert.assertTrue("Incorrect modified name field", modifiedTvShow.getName().equals(newTvShow.getName()));
        Assert.assertTrue("Incorrect modified description field", modifiedTvShow.getDescription().equals(newTvShow.getDescription()));
        Assert.assertTrue("Incorrect modified show type field", modifiedTvShow.getShowType().equals(newTvShow.getShowType()));
    }

    @Test
    public void modify_with_incorrect_id() throws Exception {

        String newTvShowJson = objectMapper.writeValueAsString(newTvShow);

        mockMvc.perform(put("/api/tvshows/incorrect_id")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTvShowJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modify_with_incorrect_tvshow() throws Exception {

        String newTvShowJson = "{}";

        mockMvc.perform(put("/api/tvshows/" + tvShow.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTvShowJson))
                .andExpect(status().isBadRequest());
    }

    @After
    public void tearDown() {

        tvShowRepository.deleteAll();
    }
}
