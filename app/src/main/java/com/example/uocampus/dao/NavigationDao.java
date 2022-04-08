package com.example.uocampus.dao;

import com.example.uocampus.model.FacilityModel;

import java.util.List;

public interface NavigationDao {

    List<FacilityModel> queryAll();

    boolean addFacilityModel(FacilityModel facility);

}
