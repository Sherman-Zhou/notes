package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

public interface QueryBuilderInterface {
    void createQuery(HotelSearchCriteria criteria);

    NativeSearchQuery getSearch();
}
