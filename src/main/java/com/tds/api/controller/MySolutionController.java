package com.tds.api.controller;


import com.tds.api.entity.ResturantAvaliabilty;
import com.tds.api.service.ResturantService;
import com.tds.api.vto.Avaliability;
import io.swagger.annotations.ApiOperation;
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
     * @description 1.    You have the following API endpoint:
     * https://postman-echo.com/get?foo1=bar1&foo2=bar2
     * Please set up a quick page using any language. Call the API endpoint and display results
     * @param foo1
     * @param foo2
     * @return
     */
    @GetMapping("/tds/api/v1/")
    @ApiOperation(value = "Solution to the first question to consume and API and display the data", response = ResponseEntity.class)
    public ResponseEntity<?> consumeApi(@RequestParam(value="foo1", required=false, defaultValue="bar1") String foo1,
                                      @RequestParam(value="foo2", required=false, defaultValue="bar2") String foo2){
        String requestURL = url+"?foo1="+foo1+"&foo2="+foo2;
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(requestURL, Object.class);
        return ResponseEntity.ok(result);
    }

    /**
     * @description
     * @param avaliability
     * @return
     * @throws IOException
     * @throws ParseException
     * Assumptions:
     * * If a day of the week is not listed, the restaurant is closed on that day
     * * All times are local — don’t worry about timezone-awareness
     * * The CSV file will be well-formed
     */
    @PostMapping("/tds/api/v1/resturant/avaliability")
    @ApiOperation(value = "Solution to the second question", response = ResponseEntity.class)
    public ResponseEntity<?> searchForAvaliaiblity(@RequestBody Avaliability avaliability) throws IOException, ParseException {
        //a.	Write a function that parses the data into a table
        //This service saves the CSV data into a database table. Please note that the service also checks for dublicates data
        resturantService.saveCSVOfResturantAndOpenigTimesToDatabaseTable("/static/schedule.csv");
        //b.	Write a function that receives a native to your language of choice and uses a sql query to return a list of restaurant names which are open on that date and time.
        List<ResturantAvaliabilty> resturantsOpenedForBusiness = resturantService.getAvaliableResturantByDate(avaliability.getDate());
        return ResponseEntity.ok(resturantsOpenedForBusiness);
    }


    /**
     * How many tennis balls fit into a Boeing 787 Dreamliner?
     *
     *
     */

}
