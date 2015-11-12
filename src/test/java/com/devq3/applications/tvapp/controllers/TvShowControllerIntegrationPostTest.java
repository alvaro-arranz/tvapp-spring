package com.devq3.applications.tvapp.controllers;

import com.devq3.applications.tvapp.Application;
import com.devq3.applications.tvapp.repository.TvShowRepository;
import com.devq3.applications.tvapp.resources.TvShow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by alvaro on 10/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TvShowControllerIntegrationPostTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TvShowRepository tvShowRepository;

    private TvShow tvShow;
    private String description = "test description";
    private String name = "Test name";
    private String showType = "Test show type";

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        tvShow = new TvShow();
        tvShow.setDescription(description);
        tvShow.setName(name);
        tvShow.setShowType(showType);
    }

    @Test
    public void add_new_tv_show_correct() throws Exception {

        String showJson = objectMapper.writeValueAsString(tvShow);

        mockMvc.perform(post("/api/tvshows").contentType("application/json")
                .content(showJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void add_new_tv_show_incorrect_body() throws Exception {

        mockMvc.perform(post("/api/tvshows").contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void add_new_tv_show_with_no_name() throws Exception {

        tvShow.setName(null);

        String showJson = objectMapper.writeValueAsString(tvShow);

        mockMvc.perform(post("/api/tvshows").contentType("application/json")
                .content(showJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void add_new_tv_show_with_no_description() throws Exception {

        tvShow.setDescription(null);

        String showJson = objectMapper.writeValueAsString(tvShow);

        mockMvc.perform(post("/api/tvshows").contentType("application/json")
                .content(showJson))
                .andExpect(status().isBadRequest());
    }


    @After
    public void tearDown(){
        tvShowRepository.deleteAll();
    }

}