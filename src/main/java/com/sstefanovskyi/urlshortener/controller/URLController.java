package com.sstefanovskyi.urlshortener.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.sstefanovskyi.urlshortener.common.URLValidator;
import com.sstefanovskyi.urlshortener.service.URLConverterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConverterService urlConverterService;

    public URLController(URLConverterService urlConverterService){
        this.urlConverterService = urlConverterService;
    }

    @RequestMapping(value = "/shortener", method = RequestMethod.POST, consumes = {"application/json"})
    public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception{
         LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
         String longUrl = shortenRequest.getUrl();
         if(URLValidator.INSTANCE.validateURL(longUrl)){
             String localURL = request.getRequestURL().toString();
             String shortenUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
             LOGGER.info("Shortn url to: " + shortenUrl);
             return shortenUrl;
         }
         throw new Exception("Please enter a valid URL");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception{
        LOGGER.info("Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        LOGGER.info("Original url: " + redirectUrlString);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + redirectUrlString);
        return redirectView;
    }

    class ShortenRequest{
        private String url;

        @JsonCreator
        public ShortenRequest(){

        }

        @JsonCreator
        public ShortenRequest(@JsonProperty("url") String url){

        }

        public String getUrl(){
            return url;
        }

        public void setUrl(){
            this.url = url;
        }
    }
}
