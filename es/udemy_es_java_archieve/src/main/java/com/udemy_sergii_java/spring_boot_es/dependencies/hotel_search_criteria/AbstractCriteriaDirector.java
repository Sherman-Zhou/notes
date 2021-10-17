package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search_criteria;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;

abstract class AbstractCriteriaDirector {
    protected AbstractCriteriaBuilder builder;

    public AbstractCriteriaDirector (AbstractCriteriaBuilder builder) {
        this.builder = builder;
    }

    abstract void buildCriteria();

    abstract HotelSearchCriteria getCriteria();
}
