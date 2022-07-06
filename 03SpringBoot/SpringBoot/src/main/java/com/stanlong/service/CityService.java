package com.stanlong.service;

import com.stanlong.bean.City;
import com.stanlong.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public City getCityById(Long id){
        return cityMapper.getById(id);
    }

    public void insert(City city){
        cityMapper.insert(city);
    }
}
