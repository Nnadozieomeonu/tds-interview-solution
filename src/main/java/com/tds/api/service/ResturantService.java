package com.tds.api.service;

import com.tds.api.entity.Resturant;
import com.tds.api.entity.ResturantAvaliabilty;
import com.tds.api.repository.ResturantAvaliabiltyRepository;
import com.tds.api.repository.ResturantRepository;
import com.tds.api.util.ResturantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResturantService {

    @Autowired
    private ResturantRepository resturantRepository;

    @Autowired
    private ResturantAvaliabiltyRepository resturantAvaliabiltyRepository;

    @Autowired
    private ResturantUtil resturantUtil;

    /**
     *
     * @param resturant
     * @return
     */
    public boolean isResturantExist(String resturant){
        return resturantRepository.findByName(resturant).isPresent();
    }

    /**
     *
     * @param resturant
     * @return
     */
    public Resturant saveResturant(Resturant resturant){
        return resturantRepository.save(resturant);
    }

    /**
     *
     * @param resturantAvaliabilty
     * @return
     */
    public ResturantAvaliabilty saveAvalabilityResturant(ResturantAvaliabilty resturantAvaliabilty){
        return resturantAvaliabiltyRepository.save(resturantAvaliabilty);
    }

    /**
     *
     * @param day
     * @return
     */
    public List<ResturantAvaliabilty> findResturantAvaliabiltyByDay(String day){
        return resturantAvaliabiltyRepository.findByDay(day);
    }

    /**
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public List<ResturantAvaliabilty> getAvaliableResturantByDate(Date date) throws ParseException {
        String day = resturantUtil.getDayFromDate(date);
        String time = resturantUtil.getTimeFromDate(date);
        List<ResturantAvaliabilty> resturantsOpenedForBusiness = new ArrayList<>();
        List<ResturantAvaliabilty> resturantsOpenOnAParticularDay = findResturantAvaliabiltyByDay(day);
        for(int i=0; i <resturantsOpenOnAParticularDay.size(); i++){
            if(resturantUtil.isResturantOpenForToday(resturantsOpenOnAParticularDay.get(i).getTime(), time)){
                resturantsOpenedForBusiness.add(resturantsOpenOnAParticularDay.get(i));
            }
        }
        return resturantsOpenedForBusiness;
    }


    public void saveCSVOfResturantAndOpenigTimesToDatabaseTable(String pathToCSVInResourceDirectory) throws IOException {
        resturantUtil.parseCSVInTable(pathToCSVInResourceDirectory);
    }
}
