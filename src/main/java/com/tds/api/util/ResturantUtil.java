package com.tds.api.util;

import com.tds.api.entity.Resturant;
import com.tds.api.entity.ResturantAvaliabilty;
import com.tds.api.service.ResturantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ResturantUtil {

    @Autowired
    private ResturantService resturantService;

    /**
     *
     * @param openingAndClosingTimeRange
     * @param time
     * @return
     */
    public boolean isResturantOpenForToday(String openingAndClosingTimeRange, String time){
        boolean isOpen = false;
        try {
            String[] openingAndClosingTime = openingAndClosingTimeRange.trim().split("-");
            String parseOpeningTime =  deleteTheLastTwoStringInAString(openingAndClosingTime[0]);
            parseOpeningTime = parseOpeningTime.length() == 2 || parseOpeningTime.length() == 1 ? parseOpeningTime+":00" : parseOpeningTime;
            parseOpeningTime = parseOpeningTime+" "+ getTheLastTwoStringInAString(openingAndClosingTime[0]).toUpperCase();
            String parseClosingTime =  deleteTheLastTwoStringInAString(openingAndClosingTime[1]);
            parseClosingTime = parseClosingTime.length() == 2 || parseClosingTime.length() == 1 ? parseClosingTime+":00" : parseClosingTime;
            parseClosingTime = parseClosingTime+" "+ getTheLastTwoStringInAString(openingAndClosingTime[1]).toUpperCase();
            Date openingTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(parseOpeningTime);
            Calendar openingTimeCalendar = convertTimeToCalendar(parseOpeningTime);
            openingTimeCalendar.setTime(openingTime);
            Date closingTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(parseClosingTime);
            Calendar closingTimeCalendar = Calendar.getInstance();
            closingTimeCalendar.setTime(closingTime);
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
            final Date dateObject = simpleDateFormat.parse(time);
            Calendar clientCalendar = Calendar.getInstance();
            clientCalendar.setTime(dateObject);
            Date clientQueryTime = clientCalendar.getTime();
            if (clientQueryTime.after(openingTimeCalendar.getTime()) && clientQueryTime.before(closingTimeCalendar.getTime())) {
                isOpen = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isOpen;
    }


    /**
     * @description Time in hh:mm:ss AM/PM
     * @param time
     * @return
     */
    private Calendar convertTimeToCalendar(String time) throws ParseException {
        Date date = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    public void parseCSVInTable(String pathToCsvFileInResourceDirectory) throws IOException {
        List<List<String>> resturants = new ArrayList<>();
        String csvFileContents = "";
        try (InputStream inputStream = getClass().getResourceAsStream(pathToCsvFileInResourceDirectory);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((csvFileContents = reader.readLine()) != null) {
                String[] csvLineContentSplitedByComma = csvFileContents.split(",");
                String avaliableDaysandTime="";
                //String manipulation on the avaliable days and Time
                for(int i = 0; i < csvLineContentSplitedByComma.length; i++){
                    if(i!=0){
                        avaliableDaysandTime+=csvLineContentSplitedByComma[i].replace("\"", "")+",";
                    }
                }
                avaliableDaysandTime = deleteLastTextInAString(avaliableDaysandTime);
                String[] info = {csvLineContentSplitedByComma[0],avaliableDaysandTime};
                resturants.add(Arrays.asList(info));
                Map<String,Map<String, String>> resturantAndOpeningTimeMap = new HashMap<>();
                Map<String, String> avaliableDayAndTimeMap = parseAvaliableDateMap(avaliableDaysandTime);
                if(!resturantService.isResturantExist(csvLineContentSplitedByComma[0])){
                    Resturant resturant = new Resturant();
                    resturant.setName(csvLineContentSplitedByComma[0]);
                    resturant.setRawavaliability(avaliableDaysandTime);
                    resturant = resturantService.saveResturant(resturant);
                    Iterator<Map.Entry<String, String>> iterator = avaliableDayAndTimeMap.entrySet().iterator();
                    while(iterator.hasNext())
                    {
                        Map.Entry<String, String> entry = iterator.next();
                        List listOfDaysOfTheWeek =  getDaysOfTheWeekFromDayRange(entry.getKey());
                        for (int i = 0; i < listOfDaysOfTheWeek.size(); i++) {
                            ResturantAvaliabilty resturantAvaliabilty = new ResturantAvaliabilty();
                            resturantAvaliabilty.setDay((String) listOfDaysOfTheWeek.get(i));
                            resturantAvaliabilty.setTime(entry.getValue());
                            resturantAvaliabilty.setResturant(resturant);
                            resturantService.saveAvalabilityResturant(resturantAvaliabilty);
                        }
                    }
                }
            }

        }

    }


    /**
     *
     * @param avaliableDates
     * @return
     */
    private Map<String,String> parseAvaliableDateMap(String avaliableDates){
        //Sample Avalable Date Parttern
        //Mon-Thu, Sun 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm
        //Split the date by backward slash
        String[] diffrentDaysAndTimeArray = avaliableDates.split("/");
        Map<String,String> avaliableDaysAndTimeMap = new HashMap<>();
        for(String differentDayAndTime : diffrentDaysAndTimeArray){
            String openAndClosingTime = "";
            String[] singleDayOftheWeek;
            if(differentDayAndTime.contains(",")){
                //Check if Avaliable Days and Time Matches these patterns (Mon-Thu, Sun 11 am - 10:30 pm)
                String[] daysOftheWeekRange = differentDayAndTime.split(",");
                singleDayOftheWeek = daysOftheWeekRange[1].trim().split(" ");
                for(int i=0; i<singleDayOftheWeek.length; i++){
                    if(i!=0){
                        openAndClosingTime+=singleDayOftheWeek[i];

                    }
                }
                avaliableDaysAndTimeMap.put(singleDayOftheWeek[0],openAndClosingTime);
                avaliableDaysAndTimeMap.put(daysOftheWeekRange[0],openAndClosingTime);
            }else{
                singleDayOftheWeek = differentDayAndTime.trim().split(" ");
                for(int i=0; i<singleDayOftheWeek.length; i++){
                    if(i!=0){
                        openAndClosingTime+=singleDayOftheWeek[i];
                    }
                }
                avaliableDaysAndTimeMap.put(singleDayOftheWeek[0],openAndClosingTime);
            }
        }
        return avaliableDaysAndTimeMap;
    }

    /**
     *
     * @param stringOftext
     * @return
     */
    public String deleteLastTextInAString(String stringOftext) {
        if (stringOftext != null && stringOftext.length() > 0 && stringOftext.charAt(stringOftext.length() - 1) == ',') {
            stringOftext = stringOftext.substring(0, stringOftext.length() - 1);
        }
        return stringOftext;
    }


    /**
     *
     * @param stringOftext
     * @return
     */
    public String getTheLastTwoStringInAString(String stringOftext) {
        String substring = stringOftext.substring(Math.max(stringOftext.length() - 2, 0));
        return substring;
    }

    /**
     *
     * @param stringOftext
     * @return
     */
    public String deleteTheLastTwoStringInAString(String stringOftext) {
        stringOftext = stringOftext.substring(0, stringOftext.length() - 2);
        return stringOftext;
    }

    /**
     *
     * @param date
     * @return
     */
    public String getDayFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String[] stringDate = calendar.getTime().toString().split(" ");
        return stringDate[0];
    }

    /**
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public String getTimeFromDate(Date date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH).parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        //calendar.add(Calendar.HOUR, -1);
        String[] stringDate = calendar.getTime().toString().split(" ");
        return stringDate[3];
    }


    /**
     *
     * @param dayRange
     * @return
     */
    private List<String> getDaysOfTheWeekFromDayRange(String dayRange){

        String[] daysOfTheWeek;
        if(dayRange.equals("Mon-Sun")) {
            daysOfTheWeek = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        }else if(dayRange.equals("Mon-Sat")) {
            daysOfTheWeek = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        }
        else if(dayRange.equals("Mon-Fri")) {
            daysOfTheWeek= new String[]{"Mon","Tue","Wed","Thu","Fri"};
        }
        else if(dayRange.equals("Mon-Thu")) {
            daysOfTheWeek= new String[]{"Mon","Tue","Wed","Thu"};
        }
        else if(dayRange.equals("Mon-Wed")) {
            daysOfTheWeek= new String[]{"Mon","Tue","Wed"};
        }
        else if(dayRange.equals("Mon-Tue")) {
            daysOfTheWeek= new String[]{"Mon","Tue"};
        }
        else if(dayRange.equals("Tue-Sun")) {
            daysOfTheWeek= new String[]{"Tue","Wed","Thu","Fri","Sat","Sun"};
        }
        else if(dayRange.equals("Tue-Sat")) {
            daysOfTheWeek= new String[]{"Tue","Wed","Thu","Fri","Sat"};
        }
        else if(dayRange.equals("Tue-Fri")) {
            daysOfTheWeek= new String[]{"Tue","Wed","Thu","Fri"};
        }
        else if(dayRange.equals("Tue-Thu")) {
            daysOfTheWeek= new String[]{"Tue","Wed","Thu"};
        }
        else if(dayRange.equals("Tue-Wed")) {
            daysOfTheWeek= new String[]{"Tue","Wed"};
        }
        else if(dayRange.equals("Wed-Sun")) {
            daysOfTheWeek= new String[]{"Wed","Thu","Fri","Sat","Sun"};
        }
        else if(dayRange.equals("Wed-Sat")) {
            daysOfTheWeek= new String[]{"Wed","Thu","Fri","Sat"};
        }
        else if(dayRange.equals("Wed-Fri")) {
            daysOfTheWeek= new String[]{"Wed","Thu","Fri"};
        }
        else if(dayRange.equals("Wed-Thu")) {
            daysOfTheWeek= new String[]{"Wed","Thu"};
        }
        else if(dayRange.equals("Thu-Fri")) {
            daysOfTheWeek= new String[]{"Thu","Fri"};
        }
        else if(dayRange.equals("Thu-Sat")) {
            daysOfTheWeek= new String[]{"Thu","Fri","Sat"};
        }
        else if(dayRange.equals("Thu-Sun")) {
            daysOfTheWeek= new String[]{"Thu","Fri","Sat","Sun"};
        }
        else if(dayRange.equals("Fri-Sun") ){
            daysOfTheWeek= new String[]{"Fri","Sat","Sun"};
        }
        else if(dayRange.equals("Fri-Sat")) {
            daysOfTheWeek= new String[]{"Fri","Sat"};
        }
        else if(dayRange.equals("Sat-Sun")) {
            daysOfTheWeek= new String[]{"Sat","Sun"};
        }
        else if(dayRange.equals("Mon")) {
            daysOfTheWeek= new String[]{"Mon"};
        }
        else if(dayRange.equals("Tue")) {
            daysOfTheWeek= new String[]{"Tue"};
        }
        else if(dayRange.equals("Wed")) {
            daysOfTheWeek= new String[]{"Wed"};
        }
        else if(dayRange.equals("Thu")) {
            daysOfTheWeek= new String[]{"Thu"};
        }
        else if(dayRange.equals("Fri")) {
            daysOfTheWeek= new String[]{"Fri"};
        }
        else{
            daysOfTheWeek= new String[]{};
        }
        return Arrays.asList(daysOfTheWeek);
    }
}
