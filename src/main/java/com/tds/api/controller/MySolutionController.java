package com.tds.api.controller;


import com.tds.api.entity.ResturantAvaliabilty;
import com.tds.api.service.ResturantService;
import com.tds.api.vto.Avaliability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.util.*;

@RestController
public class MySolutionController {

    private final String url = "https://postman-echo.com/get";

    @Autowired
    private ResturantService resturantService;

    /**
     *
     * @param foo1
     * @param foo2
     * @return
     */
    @GetMapping("/tds/api/v1/")
    public ResponseEntity<?> consumeApi(@RequestParam(value="foo1", required=false, defaultValue="bar1") String foo1,
                                      @RequestParam(value="foo2", required=false, defaultValue="bar2") String foo2){
        String requestURL = url+"?foo1="+foo1+"&foo2="+foo2;
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(requestURL, Object.class);
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @param avaliability
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/tds/api/v1/resturant/avaliability")
    public ResponseEntity<?> searchForAvaliaiblity(@RequestBody Avaliability avaliability) throws IOException, ParseException {
        resturantService.saveCSVOfResturantAndOpenigTimesToDatabaseTable("/static/schedule.csv");
        List<ResturantAvaliabilty> resturantsOpenedForBusiness = resturantService.getAvaliableResturantByDate(avaliability.getDate());
        return ResponseEntity.ok(resturantsOpenedForBusiness);
    }

}
