package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

public class HotelAgeFilter {
    public static AbstractQueryBuilder createFilter(HotelSearchCriteria criteria) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age")
                .from(criteria.getHotelAge())
                .includeLower(true);

        return rangeQueryBuilder;
    }
}
