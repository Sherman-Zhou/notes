package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class HotelNameFilter {
    public static AbstractQueryBuilder createFilter(HotelSearchCriteria criteria) {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", criteria.getHotelName());

        return matchQueryBuilder;
    }
}
