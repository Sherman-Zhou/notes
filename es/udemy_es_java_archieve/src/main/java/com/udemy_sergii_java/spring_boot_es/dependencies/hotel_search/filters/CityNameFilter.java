package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class CityNameFilter {
    public static AbstractQueryBuilder createFilter(HotelSearchCriteria criteria) {
        MatchQueryBuilder matchQueryFirst = QueryBuilders.matchQuery("cityNameEn", criteria.getCityName())
                .fuzziness(Fuzziness.TWO);
        MatchQueryBuilder matchQuerySecond = QueryBuilders.matchQuery("cityNameEn", "London");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(matchQueryFirst)
                .should(matchQuerySecond);

        return boolQueryBuilder;
    }
}
