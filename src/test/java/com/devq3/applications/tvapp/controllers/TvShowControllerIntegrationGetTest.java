package com.devq3.applications.tvapp.controllers;

import com.devq3.applications.tvapp.Application;
import com.devq3.applications.tvapp.model.PageResponse;
import com.devq3.applications.tvapp.repository.TvShowRepository;
import com.devq3.applications.tvapp.resources.TvShow;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by alvaro on 10/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TvShowControllerIntegrationGetTest {

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
    public void get_existent_tvshow() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/tvshows/" + tvShow.getId()).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();

        TvShow returnedShow = objectMapper.readValue(result.getResponse().getContentAsString(), TvShow.class);

        Assert.assertTrue("Incorrect show id", returnedShow.getId().equals(tvShow.getId()));
        Assert.assertTrue("Incorrect show name", returnedShow.getName().equals(tvShow.getName()));
        Assert.assertTrue("Incorrect show description", returnedShow.getDescription().equals(tvShow.getDescription()));
    }

    @Test
    public void get_non_existent_tvshow() throws Exception {

        mockMvc.perform(
                get("/api/tvshows/" + "bad id").
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    public void get_tvshows_with_pagination() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/tvshows?page=0&size=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Page<TvShow> returnedPage = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PageResponse<TvShow>>(){});

        Assert.assertEquals("Incorrect number of pages", 1, returnedPage.getTotalPages());
        Assert.assertEquals("Incorrect number of elements", 1, returnedPage.getTotalElements());

        Assert.assertTrue("Incorrect show id", returnedPage.getContent().get(0).getId().equals(tvShow.getId()));
        Assert.assertTrue("Incorrect show name", returnedPage.getContent().get(0).getName().equals(tvShow.getName()));
        Assert.assertTrue("Incorrect show description", returnedPage.getContent().get(0).getDescription().equals(tvShow.getDescription()));
    }

    @After
    public void tearDown() {

        tvShowRepository.deleteAll();
    }
}
