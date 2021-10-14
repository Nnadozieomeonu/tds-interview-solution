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


    //Solution 3
    /**
     * How many tennis balls fit into a Boeing 787 Dreamliner?
     *
     *First, we need to find the volume of the aeroplane and the tennis ball.
     *
     * Once we get these figures, we will divide the volume of the airplane with that of the tennis ball.
     *
     * Estimating that Boeing 787 has an approximate radius of 1 meter (assuming the aeroplane has the same height of a normal office floor) and length ~33 meters
     * (~400 seats in an aeroplane with 10 seats a row which gives us 40 rows and adding another 10 rows for pilot and toilets we get to ~50 rows * 2 feet which gives us ~100 feet or ~33 meters).
     *
     * The volume of aeroplane is 103m^3 or 33πm^3 or 33mπcm^3
     *
     * Then, estimating that tennis ball has a radius 3.3cm we can calculate the volume. In that case, the volume of the tennis ball can be calculated by the formula of a sphere.
     *
     * Volume of a sphere is = (4 πr3)/3 The volume of the tennis ball would be ~47.916πcm^3.
     *
     * Finally, after dividing the volume of the aeroplane by that to the tennis ball we arrive at apportximate figure of tennis balls that can fit a Boeing 787 dreamliner.
     *
     * Therefore, we can fit approximately 688,705 tennis balls in a Boeing 787
     */

}
