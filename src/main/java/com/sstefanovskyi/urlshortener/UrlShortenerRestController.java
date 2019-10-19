package com.sstefanovskyi.urlshortener;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerRestController {

    private Map<String, ShortenerUrl> shortenerUrlList = new HashMap<>();

    @RequestMapping(value="/shortenerurl", method=RequestMethod.POST)
    public ResponseEntity<Object> getShortenerUrl(@RequestBody ShortenerUrl shortenerUrl) throws MalformedURLException {
        String randomChar = getRandomChars();
        setShortUrl(randomChar, shortenerUrl);
        return new ResponseEntity<Object>(shortenerUrl, HttpStatus.OK);
    }

    @RequestMapping(value="/s/{randomstring}", method=RequestMethod.GET)
    public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException {
        response.sendRedirect(shortenerUrlList.get(randomString).getFull_url());
    }

    private void setShortUrl(String randomChar, ShortenerUrl shortenerUrl) throws MalformedURLException {
        shortenerUrl.setShort_url("http://localhost:8080/s/"+randomChar);
        shortenerUrlList.put(randomChar, shortenerUrl);
    }

    private String getRandomChars() {
        String randomStr = "";
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 5; i++)
            randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
        return randomStr;
    }

}