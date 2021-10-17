package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search_criteria;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;

abstract class AbstractCriteriaBuilder {

    abstract HotelSearchCriteria getCriteria();

    abstract void createCriteria();
}
