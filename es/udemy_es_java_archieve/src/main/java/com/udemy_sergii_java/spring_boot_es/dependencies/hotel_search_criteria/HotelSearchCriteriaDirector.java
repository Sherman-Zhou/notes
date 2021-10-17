package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search_criteria;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;

public class HotelSearchCriteriaDirector extends AbstractCriteriaDirector {

    public HotelSearchCriteriaDirector(AbstractCriteriaBuilder builder) {
        super(builder);
    }

    @Override
    public void buildCriteria() {
        this.builder.createCriteria();
    }

    @Override
    public HotelSearchCriteria getCriteria() {
        return this.builder.getCriteria();
    }
}
